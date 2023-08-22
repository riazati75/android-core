@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ir.farsroidx.core

import androidx.databinding.ViewDataBinding

abstract class CoreViewStateActivity <VDB: ViewDataBinding, VS: Any> :
    CoreActivity<VDB>(), CoreViewState<VS>
{

    fun initViewState(viewModel: CoreViewStateViewModel<VS>) {
        viewModel.setOnViewStateChanged(lifecycleOwner = this, ::viewStateHandler)
    }

    override fun viewStateHandler(viewState: VS) {
        // Nothing to change
    }
}
