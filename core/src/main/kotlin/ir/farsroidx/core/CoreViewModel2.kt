@file:Suppress("MemberVisibilityCanBePrivate", "unused", "DEPRECATION")

package ir.farsroidx.core

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class CoreViewModel2 <VS: Any> : CoreViewModel() {

    protected var onViewStateChange: (VS) -> Unit = {}

    private val _liveViewStateChange = MutableLiveData<VS>()
    val liveViewStateChange: LiveData<VS> = _liveViewStateChange

    fun setOnViewStateChanged(onChange: (VS) -> Unit) {
        onViewStateChange = onChange
    }

    @CallSuper
    protected open fun setViewState(state: VS) {
        _liveViewStateChange.value = state
    }

    @CallSuper
    protected open fun postViewState(state: VS) {
        _liveViewStateChange.postValue(state)
    }
}