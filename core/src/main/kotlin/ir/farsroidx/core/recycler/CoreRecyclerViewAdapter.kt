@file:Suppress("MemberVisibilityCanBePrivate", "unused")

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
    protected lateinit var context     : Context
    protected lateinit var recyclerView: RecyclerView

    private lateinit var layoutInflater: LayoutInflater

    protected var itemClicked    : (item: M) -> Unit = {}
    protected var itemLongClicked: (item: M) -> Unit = {}

    protected val itemClickedListeners     = mutableMapOf<String, (item: M) -> Unit   >()
    protected val itemLongClickedListeners = mutableMapOf<String, (item: M) -> Boolean>()

    private var itemsFilterBackup: List<M>? = null
    private var itemsSortBackup  : List<M>? = null

    private val selectedItems = mutableListOf<M>()

    private val items = mutableListOf<M>()

    var isFiltered = false
        private set

    var isSorted = false
        private set

    class CoreViewHolder<DB : ViewDataBinding>(val dataBinding: DB) :
        RecyclerView.ViewHolder(dataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoreViewHolder<VDB> {
        return CoreViewHolder(
            autoViewDataBinding(
                context, layoutInflater, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CoreViewHolder<VDB>, position: Int) {
        onBindViewHolder(holder.dataBinding, items[position], position)
    }

    @CallSuper
    open fun addItem(item: M) {
        synchronized(this.items) {
            this.items.add(item)
            notifyItemInserted(itemCount - 1)
        }
    }

    @CallSuper
    open fun addItems(vararg items: M) {
        synchronized(this.items) {
            if (items.isNotEmpty()) {
                val iCount = itemCount
                this.items.addAll(items)
                notifyItemRangeInserted(iCount, itemCount)
            }
        }
    }

    @CallSuper
    open fun addItems(items: List<M>) {
        synchronized(this.items) {
            if (items.isNotEmpty()) {
                val iCount = itemCount
                this.items.addAll(items)
                notifyItemRangeInserted(iCount, itemCount)
            }
        }
    }

    @CallSuper
    open fun addItemAt(position: Int, item: M) {
        synchronized(this.items) {
            this.items.add(position, item)
            notifyItemInserted(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }

    @CallSuper
    open fun addItemsAt(position: Int, vararg items: M) {
        synchronized(this.items) {
            if (items.isNotEmpty()) {
                this.items.addAll(position, items.toList())
                notifyItemRangeInserted(position, items.size)
                notifyItemRangeChanged(position, itemCount)
            }
        }
    }

    @CallSuper
    open fun addItemsAt(position: Int, items: List<M>) {
        synchronized(this.items) {
            if (items.isNotEmpty()) {
                this.items.addAll(position, items)
                notifyItemRangeInserted(position, items.size)
                notifyItemRangeChanged(position, itemCount)
            }
        }
    }

    @CallSuper
    open fun updateItem(item: M) {
        synchronized(this.items) {
            val position = this.items.indexOf(item)
            if (position != -1) {
                this.items[position] = item
                notifyItemChanged(position)
            }
        }
    }

    @CallSuper
    open fun updateItemAt(position: Int, items: M) {
        synchronized(this.items) {
            this.items[position] = items
            notifyItemChanged(position)
        }
    }

    @CallSuper
    open fun removeItem(item: M) {
        synchronized(this.items) {
            val position = this.items.indexOf(item)
            if (position != -1) {
                this.items.remove(item)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount)
            }
        }
    }

    @CallSuper
    open fun removeItemAt(position: Int) {
        synchronized(this.items) {
            this.items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }

    @CallSuper
    open fun clear() {
        synchronized(this.items) {
            if (itemCount > 0) {
                val lastItemCount = itemCount
                this.items.clear()
                notifyItemRangeRemoved(0, lastItemCount)
                notifyItemRangeChanged(0, itemCount)
                this.itemsFilterBackup = null
                this.itemsSortBackup = null
            }
        }
    }

    fun setOnItemClicked(onClicked: (item: M) -> Unit) {
        this.itemClicked = onClicked
    }

    fun setOnItemLongClicked(onLongClicked: (item: M) -> Unit) {
        this.itemLongClicked = onLongClicked
    }

    fun setOnItemTagClicked(tag: String, listener: (item: M) -> Unit) {
        itemClickedListeners[tag] = listener
    }

    fun setOnItemTagLongClicked(tag: String, listener: (item: M) -> Boolean) {
        itemLongClickedListeners[tag] = listener
    }

    protected fun getClickListener(tag: String): ((M) -> Unit)? {
        if (itemClickedListeners.contains(tag)) {
            return itemClickedListeners[tag]
        }
        return null
    }

    protected fun getLongClickListener(tag: String): ((M) -> Boolean)? {
        if (itemLongClickedListeners.contains(tag)) {
            return itemLongClickedListeners[tag]
        }
        return null
    }

    @CallSuper
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.context = recyclerView.context
        this.recyclerView = recyclerView
        this.layoutInflater = LayoutInflater.from(context)
    }

    @CallSuper
    override fun onViewRecycled(holder: CoreViewHolder<VDB>) {
        super.onViewRecycled(holder)
        onViewRecycled(holder.dataBinding)
    }

    @CallSuper
    override fun getItemCount(): Int = this.items.size

    protected fun getColorById(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }

    protected fun getStringById(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    protected fun getDrawableById(@DrawableRes resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }

    protected fun getItems(): List<M> = this.items

    @Synchronized
    fun filterItems(invoker: (M) -> Boolean) {
        if (this.itemsFilterBackup == null) {
            this.itemsFilterBackup = mutableListOf<M>().apply {
                addAll(this@CoreRecyclerViewAdapter.items)
            }
        }
        this.itemsFilterBackup!!.filter(invoker).apply {
            notifyItemRangeRemoved(0, itemCount)
            this@CoreRecyclerViewAdapter.items.clear()
            this@CoreRecyclerViewAdapter.items.addAll(this)
            notifyItemRangeInserted(0, itemCount)
        }
        isFiltered = true
    }

    fun clearFilters() {
        if (this.itemsFilterBackup != null) {
            notifyItemRangeRemoved(0, itemCount)
            this.items.clear()
            this.items.addAll(this.itemsFilterBackup!!)
            notifyItemRangeInserted(0, itemCount)
            this.itemsFilterBackup = null
            isFiltered = false
        }
    }

    protected fun addSelectedItem(item: M) {
        synchronized(this.selectedItems) {
            if (!this.selectedItems.contains(item)) {
                val position = this.items.indexOf(item)
                if (position != -1) {
                    this.selectedItems.add(item)
                    notifyItemChanged(position)
                }
            }
        }
    }

    protected fun removeSelectedItem(item: M) {
        synchronized(this.selectedItems) {
            if (this.selectedItems.contains(item)) {
                val position = this.items.indexOf(item)
                if (position != -1) {
                    this.selectedItems.remove(item)
                    notifyItemChanged(position)
                }
            }
        }
    }

    protected fun isSelectedItem(item: M): Boolean = this.selectedItems.contains(item)

    fun getSelectedItems(): List<M> = selectedItems

    fun sortAscendingBy(invoker: (M) -> Boolean) {
        if (this.itemsSortBackup == null) {
            this.itemsSortBackup = mutableListOf<M>().apply {
                addAll(this@CoreRecyclerViewAdapter.items)
            }
        }
        this.itemsSortBackup!!.sortedBy(invoker).apply {
            notifyItemRangeRemoved(0, itemCount)
            this@CoreRecyclerViewAdapter.items.clear()
            this@CoreRecyclerViewAdapter.items.addAll(this)
            notifyItemRangeInserted(0, itemCount)
        }
        isSorted = true
    }

    fun sortDescendingBy(invoker: (M) -> Boolean) {
        if (this.itemsSortBackup == null) {
            this.itemsSortBackup = mutableListOf<M>().apply {
                addAll(this@CoreRecyclerViewAdapter.items)
            }
        }
        this.itemsSortBackup!!.sortedByDescending(invoker).apply {
            notifyItemRangeRemoved(0, itemCount)
            this@CoreRecyclerViewAdapter.items.clear()
            this@CoreRecyclerViewAdapter.items.addAll(this)
            notifyItemRangeInserted(0, itemCount)
        }
        isSorted = true
    }

    fun clearSort() {
        if (this.itemsSortBackup != null) {
            notifyItemRangeRemoved(0, itemCount)
            this.items.clear()
            this.items.addAll(this.itemsSortBackup!!)
            notifyItemRangeInserted(0, itemCount)
            this.itemsSortBackup = null
            isSorted = false
        }
    }

    protected abstract fun onBindViewHolder(binding: VDB, item: M, position: Int)

    protected abstract fun onViewRecycled(dataBinding: VDB)
}
