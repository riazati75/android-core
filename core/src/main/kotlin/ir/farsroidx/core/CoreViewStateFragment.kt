@file:Suppress("unused")

package ir.farsroidx.core

import android.os.Bundle
import androidx.viewbinding.ViewBinding

abstract class CoreViewStateFragment <VDB: ViewBinding, VS: Any> :
    CoreFragment<VDB>(), CoreViewState<VS>
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup viewState
        getViewModel().setOnViewStateChanged(lifecycleOwner = this, ::viewStateHandler)
    }
}