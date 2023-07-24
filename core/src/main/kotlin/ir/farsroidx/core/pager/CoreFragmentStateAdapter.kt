@file:Suppress("unused")

package ir.farsroidx.core.pager

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

@Suppress("unused")
class CoreFragmentStateAdapter(
    private val fragmentActivity: FragmentActivity
) : FragmentStateAdapter(
    fragmentActivity.supportFragmentManager,
    fragmentActivity.lifecycle
) {

    private val mFragments = mutableListOf<CoreFragmentModel>()

    override fun getItemCount(): Int = mFragments.size

    override fun createFragment(position: Int): Fragment = mFragments[position].fragment

    fun addFragment(vararg fragments: CoreFragmentModel) {
        synchronized(mFragments) {
            fragments.forEach {
                mFragments.add(it)
                notifyItemInserted(itemCount - 1)
            }
        }
    }

    fun getByPosition(position: Int): CoreFragmentModel {
        return mFragments[position]
    }

    fun getFragmentByPosition(position: Int): Fragment {
        return mFragments[position].fragment
    }

    fun getTitleByPosition(position: Int): String {
        return mFragments[position].title
    }

    fun getIconByPosition(position: Int): Drawable? {
        val icon = mFragments[position].icon ?: return null
        return ContextCompat.getDrawable(
            fragmentActivity, icon
        )
    }
}