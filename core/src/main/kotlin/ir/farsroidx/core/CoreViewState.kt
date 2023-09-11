package ir.farsroidx.core

internal interface CoreViewState <VS: Any> {

    fun viewStateHandler(viewState: VS)

    fun getCoreViewStateViewModel(): CoreViewStateViewModel<VS>

}