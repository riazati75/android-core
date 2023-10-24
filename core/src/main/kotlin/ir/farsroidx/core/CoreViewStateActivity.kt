@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ir.farsroidx.core

import android.os.Bundle
import androidx.databinding.ViewDataBinding

abstract class CoreViewStateActivity <VDB: ViewDataBinding, VS: Any> :
    CoreActivity<VDB>(), CoreViewState<VS>
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup viewState
        getCoreViewModel().setOnViewStateChanged(lifecycleOwner = this, ::viewStateHandler)
    }
}
