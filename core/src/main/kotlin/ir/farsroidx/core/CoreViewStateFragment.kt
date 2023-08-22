@file:Suppress("unused")

package ir.farsroidx.core

import androidx.viewbinding.ViewBinding

abstract class CoreViewStateFragment <VDB: ViewBinding, VS: Any> :
    CoreFragment<VDB>(), CoreViewState<VS>
{

    fun initViewState(viewModel: CoreViewStateViewModel<VS>) {
        viewModel.setOnViewStateChanged(lifecycleOwner = this, ::viewStateHandler)
    }

    override fun viewStateHandler(viewState: VS) {
         // Nothing to change
    }
}