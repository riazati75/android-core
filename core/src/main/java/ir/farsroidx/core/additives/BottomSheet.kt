@file:Suppress("unused")

package ir.farsroidx.core.additives

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import ir.farsroidx.core.AbstractActivity
import ir.farsroidx.core.AbstractFragment
import ir.farsroidx.core.R

fun <T : ViewDataBinding> AbstractActivity<*, *, *>.bottomSheet(
    invoker: (layoutInflater: LayoutInflater) -> T
) = applicationContext.bottomSheet(invoker)

fun <T : ViewDataBinding> AbstractFragment<*, *, *>.bottomSheet(
    invoker: (layoutInflater: LayoutInflater) -> T
) = requireContext().bottomSheet(invoker)

fun <T : ViewDataBinding> View.bottomSheet(
    invoker: (layoutInflater: LayoutInflater) -> T
) = context.bottomSheet(invoker)

fun <T : ViewDataBinding> Context.bottomSheet(
    invoker: (layoutInflater: LayoutInflater) -> T
) = BottomSheetDialog(this, R.style.Style_Farsroidx_BottomSheetDialog)
    .apply {
        setContentView(invoker(layoutInflater).root)
    }