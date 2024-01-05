package ir.farsroidx.core.pager

import androidx.fragment.app.Fragment

data class CoreFragmentModel(
    val fragment: Fragment,
    val title: String = "",
    val icon: Int? = null
)