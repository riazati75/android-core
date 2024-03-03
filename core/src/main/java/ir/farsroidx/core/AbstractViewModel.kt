@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ir.farsroidx.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//@Deprecated(
//    message = "Please replace it and use of CoreViewStateViewModel.",
//    replaceWith = ReplaceWith(
//        expression = "CoreViewStateViewModel",
//        imports = [
//            "ir.farsroidx.core.CoreViewStateViewModel"
//        ]
//    ),
//    level = DeprecationLevel.WARNING
//)
abstract class AbstractViewModel <VS: Any> : ViewModel() {

    private var _onStateChange: (VS) -> Unit = {}

    private var _lifecycleOwner: LifecycleOwner? = null

    private val _liveViewState = MutableLiveData<VS>()
    val liveViewState: LiveData<VS> = _liveViewState

    private val lifecycleEventObserver = LifecycleEventObserver { _, event ->
        when(event) {
            Lifecycle.Event.ON_CREATE  -> { onCreate()  }
            Lifecycle.Event.ON_START   -> { onStart()   }
            Lifecycle.Event.ON_RESUME  -> { onResume()  }
            Lifecycle.Event.ON_PAUSE   -> { onPause()   }
            Lifecycle.Event.ON_STOP    -> { onStop()    }
            Lifecycle.Event.ON_DESTROY -> { onDestroy() }
            Lifecycle.Event.ON_ANY     -> { onAny()     }
        }
    }

    internal fun setOnViewStateChanged(
        lifecycleOwner: LifecycleOwner, onStateChange: (VS) -> Unit
    ) {

        _lifecycleOwner = lifecycleOwner
        _onStateChange  = onStateChange

        _lifecycleOwner?.lifecycle?.addObserver(lifecycleEventObserver)

    }

    protected fun doInIoScope(
        invoker: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        invoker()
    }

    protected fun doInDefaultScope(
        invoker: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(Dispatchers.Default) {
        invoker()
    }

    protected fun doInUnconfinedScope(
        invoker: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(Dispatchers.Unconfined) {
        invoker()
    }

    protected fun doInMainScope(
        invoker: () -> Unit
    ) = viewModelScope.launch(Dispatchers.Main) {
        invoker()
    }

    protected fun viewModelScope(
        invoker: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        invoker()
    }

    protected fun setViewState(viewState: VS) {
        _lifecycleOwner?.let { lifecycleOwner ->
            if (lifecycleOwner.lifecycle.currentState != Lifecycle.State.DESTROYED) {
                doInMainScope {
                    _onStateChange( viewState )
                }
            } else {
                // TODO: Nothing to change =========================================================
            }
        }
    }

    protected fun setLiveDataValue(viewState: VS) {
        _liveViewState.value = viewState
    }

    protected fun postLiveDataValue(viewState: VS) {
        _liveViewState.postValue(viewState)
    }

    protected open fun onCreate() {}

    protected open fun onStart() {}

    protected open fun onResume() {}

    protected open fun onPause() {}

    protected open fun onStop() {}

    protected open fun onDestroy() {}

    protected open fun onAny() {}

    fun post(func: () -> Unit) = viewModelScope { doInMainScope { func() } }

    fun postDelay(mills: Long, func: () -> Unit) = viewModelScope {
        delay(mills)
        doInMainScope { func() }
    }
}