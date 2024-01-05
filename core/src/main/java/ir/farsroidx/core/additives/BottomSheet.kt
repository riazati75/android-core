@file:Suppress("unused")

package ir.farsroidx.core.additives

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import ir.farsroidx.core.CoreActivity
import ir.farsroidx.core.CoreFragment
import ir.farsroidx.core.R

fun <T : ViewDataBinding> CoreActivity<*, *>.bottomSheet(
    invoker: (layoutInflater: LayoutInflater) -> T
) = BottomSheetDialog(this, R.style.Style_Farsroidx_BottomSheetDialog)
    .apply {
        setContentView(invoker(layoutInflater).root)
    }

fun <T : ViewDataBinding> CoreFragment<*, *>.bottomSheet(
    invoker: (layoutInflater: LayoutInflater) -> T
) = BottomSheetDialog(requireContext(), R.style.Style_Farsroidx_BottomSheetDialog)
    .apply {
        setContentView(invoker(layoutInflater).root)
    }

fun <T : ViewDataBinding> Context.bottomSheet(
    invoker: (layoutInflater: LayoutInflater) -> T
) = BottomSheetDialog(this, R.style.Style_Farsroidx_BottomSheetDialog)
    .apply {
        setContentView(invoker(layoutInflater).root)
    }