@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ir.farsroidx.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class CoreViewStateViewModel <VS: Any> : CoreViewModel() {

    protected var onViewStateChange: (VS) -> Unit = {}

    private val _liveViewStateChange = MutableLiveData<VS>()
    val liveViewStateChange: LiveData<VS> = _liveViewStateChange

    fun setOnViewStateChanged(onChange: (VS) -> Unit) {
        onViewStateChange = onChange
    }

    protected fun setViewState(state: VS) {
        _liveViewStateChange.value = state
    }

    protected fun postViewState(state: VS) {
        _liveViewStateChange.postValue(state)
    }
}