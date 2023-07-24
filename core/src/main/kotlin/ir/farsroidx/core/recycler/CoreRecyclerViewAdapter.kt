@file:Suppress("MemberVisibilityCanBePrivate")

package ir.farsroidx.core.recycler

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ir.farsroidx.core.additives.autoViewDataBinding

abstract class CoreRecyclerViewAdapter<VDB : ViewDataBinding, M : Any>
    : RecyclerView.Adapter<CoreRecyclerViewAdapter.CoreViewHolder<VDB>>()
{
    protected lateinit var context: Context
    protected lateinit var recyclerView: RecyclerView

    private lateinit var layoutInflater: LayoutInflater

    protected var mOnActionClicked: (item: M) -> Unit = {}

    protected var itemClicked: (item: M) -> Unit = {}

    protected var itemLongClicked: (item: M) -> Unit = {}

    class CoreViewHolder<DB : ViewDataBinding>(val dataBinding: DB) :
        RecyclerView.ViewHolder(dataBinding.root)

    private val mItems = mutableListOf<M>()

    private var mItemsBackup: List<M>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoreViewHolder<VDB> {
        return CoreViewHolder(
            autoViewDataBinding(
                context, layoutInflater, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CoreViewHolder<VDB>, position: Int) {
        onBindViewHolder(holder.dataBinding, mItems[position], position)
    }

    @CallSuper
    open fun addItem(item: M) {
        synchronized(mItems) {
            this.mItems.add(item)
            notifyItemInserted(itemCount - 1)
        }
    }

    @CallSuper
    open fun addItems(vararg items: M) {
        synchronized(mItems) {
            val iCount = itemCount
            this.mItems.addAll(items)
            notifyItemRangeInserted(iCount, itemCount)
        }
    }

    @CallSuper
    open fun addItems(items: List<M>) {
        synchronized(mItems) {
            val iCount = itemCount
            this.mItems.addAll(items)
            notifyItemRangeInserted(iCount, itemCount)
        }
    }

    @CallSuper
    open fun addItemAt(position: Int, item: M) {
        synchronized(mItems) {
            this.mItems.add(position, item)
            notifyItemInserted(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }

    @CallSuper
    open fun addItemsAt(position: Int, vararg items: M) {
        synchronized(mItems) {
            this.mItems.addAll(position, items.toList())
            notifyItemRangeInserted(position, items.size)
            notifyItemRangeChanged(position, itemCount)
        }
    }

    @CallSuper
    open fun addItemsAt(position: Int, items: List<M>) {
        synchronized(mItems) {
            this.mItems.addAll(position, items)
            notifyItemRangeInserted(position, items.size)
            notifyItemRangeChanged(position, itemCount)
        }
    }

    @CallSuper
    open fun updateItem(item: M) {
        synchronized(mItems) {
            val position = this.mItems.indexOf(item)
            this.mItems[position] = item
            notifyItemChanged(position)
        }
    }

    @CallSuper
    open fun updateItemAt(position: Int, items: M) {
        synchronized(mItems) {
            this.mItems[position] = items
            notifyItemChanged(position)
        }
    }

    @CallSuper
    open fun removeItem(item: M) {
        synchronized(mItems) {
            val position = this.mItems.indexOf(item)
            this.mItems.remove(item)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }

    @CallSuper
    open fun removeItemAt(position: Int) {
        synchronized(mItems) {
            this.mItems.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }

    @CallSuper
    open fun clear() {
        synchronized(mItems) {
            if (itemCount > 0) {
                val lastItemCount = itemCount
                this.mItems.clear()
                notifyItemRangeRemoved(0, lastItemCount)
                notifyItemChanged(0, itemCount)
                this.mItemsBackup = null
            }
        }
    }

    fun setOnDeleteActionClicked(onClicked: (item: M) -> Unit) {
        this.mOnActionClicked = onClicked
    }

    fun setOnItemClicked(onClicked: (item: M) -> Unit) {
        this.itemClicked = onClicked
    }

    fun setOnItemLongClicked(onLongClicked: (item: M) -> Unit) {
        this.itemLongClicked = onLongClicked
    }

    protected abstract fun onBindViewHolder(dataBinding: VDB, item: M, position: Int)

    protected abstract fun onViewRecycled(dataBinding: VDB)

    @CallSuper
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        this.context = recyclerView.context
        this.layoutInflater = LayoutInflater.from(context)
    }

    @CallSuper
    override fun onViewRecycled(holder: CoreViewHolder<VDB>) {
        super.onViewRecycled(holder)
        onViewRecycled(holder.dataBinding)
    }

    @CallSuper
    override fun getItemCount(): Int = mItems.size

    protected fun getColorById(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }

    protected fun getStringById(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    protected fun getDrawableById(@DrawableRes resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }

    protected fun getItems(): List<M> = mItems

    fun clearFilters() {
        if (mItemsBackup != null) {
            notifyItemRangeRemoved(0, itemCount)
            mItems.clear()
            mItems.addAll(mItemsBackup!!)
            notifyItemRangeInserted(0, itemCount)
            mItemsBackup = null
        }
    }

    @Synchronized
    fun filterItems(filterBlock: (M) -> Boolean) {
        if (mItemsBackup == null) {
            mItemsBackup = mutableListOf<M>().apply {
                addAll(mItems)
            }
        }
        mItemsBackup!!.filter(filterBlock).apply {
            notifyItemRangeRemoved(0, itemCount)
            mItems.clear()
            mItems.addAll(this)
            notifyItemRangeInserted(0, itemCount)
        }
    }
}
