@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ir.farsroidx.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
abstract class CoreViewModel : ViewModel() {

    private var _onViewStateChange: (CoreViewState) -> Unit = {}

    private var _lifecycleOwner: LifecycleOwner? = null

    private val _liveViewState = MutableLiveData<CoreViewState>()
    val liveViewState: LiveData<CoreViewState> = _liveViewState

    internal fun setOnViewStateChanged(
        lifecycleOwner: LifecycleOwner, onChange: (CoreViewState) -> Unit
    ) {
        _lifecycleOwner    = lifecycleOwner
        _onViewStateChange = onChange
    }

    protected suspend fun doInIoScope(invoker: () -> Unit) {
        withContext(Dispatchers.IO) {
            invoker()
        }
    }

    protected suspend fun doInMainScope(invoker: () -> Unit) {
        withContext(Dispatchers.Main) {
            invoker()
        }
    }

    protected suspend fun doInDefaultScope(invoker: () -> Unit) {
        withContext(Dispatchers.Default) {
            invoker()
        }
    }

    protected fun viewModelScope(
        invoker: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        invoker()
    }

    protected suspend fun setViewState(viewState: CoreViewState) {
        _lifecycleOwner?.let { lifecycleOwner ->
            doInMainScope {
                if (lifecycleOwner.lifecycle.currentState != Lifecycle.State.DESTROYED) {
                    _onViewStateChange( viewState )
                } else {
                    // TODO: Nothing to change =====================================================
                }
            }
        }
    }

    protected suspend fun setViewState(uniqueId: Int, data: Any?) {
        _lifecycleOwner?.let { lifecycleOwner ->
            doInMainScope {
                if (lifecycleOwner.lifecycle.currentState != Lifecycle.State.DESTROYED) {
                    _onViewStateChange( CoreViewState(uniqueId, data) )
                } else {
                    // TODO: Nothing to change =====================================================
                }
            }
        }
    }

    protected fun setLiveDataValue(viewState: CoreViewState) {
        _liveViewState.value = viewState
    }

    protected fun setLiveDataValue(uniqueId: Int, data: Any?) {
        _liveViewState.value = CoreViewState(uniqueId, data)
    }

    protected fun postLiveDataValue(viewState: CoreViewState) {
        _liveViewState.postValue(viewState)
    }

    protected fun postLiveDataValue(uniqueId: Int, data: Any?) {
        _liveViewState.postValue(CoreViewState(uniqueId, data))
    }
}