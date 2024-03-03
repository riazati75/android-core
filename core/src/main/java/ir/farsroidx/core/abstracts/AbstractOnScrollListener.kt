package ir.farsroidx.core.abstracts

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractOnScrollListener : RecyclerView.OnScrollListener() {

    @CallSuper
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

        when(newState) {

            RecyclerView.SCROLL_STATE_IDLE -> {
                onStateIdle(recyclerView)
            }

            RecyclerView.SCROLL_STATE_DRAGGING -> {
                onStateDragging(recyclerView)
            }

            RecyclerView.SCROLL_STATE_SETTLING -> {
                onStateSettling(recyclerView)
            }
        }

        if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(1)) {
            onScrolledToLastItem(recyclerView)
        }
    }

    @CallSuper
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

//        if (dy > dx) {
//            onScrollToUp(recyclerView, dx, dy)
//        } else {
//            onScrollToDown(recyclerView, dx, dy)
//        }

        if (!recyclerView.canScrollVertically(1) && dy > 0) {
            onScrollToDown(recyclerView, dx, dy)
        } else if (!recyclerView.canScrollVertically(-1) && dy < 0) {
            onScrollToUp(recyclerView, dx, dy)
        }
    }

    open fun onStateIdle(recyclerView: RecyclerView) {

    }

    open fun onStateDragging(recyclerView: RecyclerView) {

    }

    open fun onStateSettling(recyclerView: RecyclerView) {

    }

    open fun onScrolledToLastItem(recyclerView: RecyclerView) {

    }

    open fun onScrollToUp(recyclerView: RecyclerView, dx: Int, dy: Int) {

    }

    open fun onScrollToDown(recyclerView: RecyclerView, dx: Int, dy: Int) {

    }
}