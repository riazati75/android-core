@file:Suppress("MemberVisibilityCanBePrivate")

package ir.farsroidx.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
}