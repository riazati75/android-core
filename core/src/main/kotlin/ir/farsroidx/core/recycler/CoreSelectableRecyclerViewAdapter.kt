@file:Suppress("unused")

package ir.farsroidx.core.recycler

import androidx.databinding.ViewDataBinding

abstract class CoreSelectableRecyclerViewAdapter<VDB : ViewDataBinding, M : Any>
    : CoreRecyclerViewAdapter<VDB, M>()
{
    private val mSelectedItems = mutableListOf<Int>()

    protected fun addSelectedPosition(position: Int) {
        synchronized(mSelectedItems) {
            mSelectedItems.add(position)
            notifyItemChanged(position)
        }
    }

    protected fun removeSelectedPosition(position: Int) {
        synchronized(mSelectedItems) {
            if (mSelectedItems.contains(position)) {
                mSelectedItems.remove(position)
                notifyItemChanged(position)
            }
        }
    }

    protected fun getSelectedPositions(): List<Int> = mSelectedItems

    protected fun isSelectedPosition(position: Int): Boolean = mSelectedItems.contains(position)

    fun getSelectedItems(): List<M> {
        return getItems().filterIndexed { index, _ ->
            mSelectedItems.contains(index)
        }
    }
}
