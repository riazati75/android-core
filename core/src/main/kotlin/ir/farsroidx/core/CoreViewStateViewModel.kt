@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ir.farsroidx.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class CoreViewStateViewModel <VS: Any> : CoreViewModel() {

    private var _onViewStateChange: (VS) -> Unit = {}

    private var _lifecycleOwner: LifecycleOwner? = null

    private val _liveViewStateChange = MutableLiveData<VS>()
    val liveViewStateChange: LiveData<VS> = _liveViewStateChange

    fun setOnViewStateChanged(lifecycleOwner: LifecycleOwner, onChange: (VS) -> Unit) {
        _lifecycleOwner    = lifecycleOwner
        _onViewStateChange = onChange
    }

    protected suspend fun setViewState(viewState: VS) {
        _lifecycleOwner?.let {
            doInMainThread {
                if (it.lifecycle.currentState != Lifecycle.State.DESTROYED) {
                    _onViewStateChange( viewState )
                } else {
                // TODO: Nothing to change
                }
            }
        }
    }

    protected fun setLiveViewState(viewState: VS) {
        _liveViewStateChange.value = viewState
    }

    protected fun postLiveViewState(viewState: VS) {
        _liveViewStateChange.postValue(viewState)
    }
}