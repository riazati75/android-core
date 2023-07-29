@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ir.farsroidx.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Deprecated(
    message = "Please replace it and use of CoreViewModel2.",
    replaceWith = ReplaceWith(
        expression = "CoreViewModel2",
        imports = [
            "ir.farsroidx.core.CoreViewModel2"
        ]
    ),
    level = DeprecationLevel.WARNING
)
abstract class CoreViewModel : ViewModel() {

    suspend fun doInIOThread(block: () -> Unit) {
        withContext(Dispatchers.IO) {
            block()
        }
    }

    suspend fun doInMainThread(block: () -> Unit) {
        withContext(Dispatchers.Main) {
            block()
        }
    }

    fun viewModelScope(
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        block()
    }
}