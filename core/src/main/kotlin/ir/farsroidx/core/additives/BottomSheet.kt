@file:Suppress("unused")

package ir.farsroidx.core.additives

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import ir.farsroidx.core.CoreActivity
import ir.farsroidx.core.CoreFragment
import ir.farsroidx.core.R

fun <T : ViewDataBinding> CoreFragment<*>.bottomSheet(invoker: (LayoutInflater) -> ViewBinding) =
    BottomSheetDialog(requireContext(), R.style.Style_Farsroidx_BottomSheetDialog)
        .apply {
            setContentView(invoker(layoutInflater).root)
        }

fun <T : ViewDataBinding> CoreActivity<*>.bottomSheet(invoker: (LayoutInflater) -> ViewBinding) =
    BottomSheetDialog(this, R.style.Style_Farsroidx_BottomSheetDialog)
        .apply {
            setContentView(invoker(layoutInflater).root)
        }

fun <T : ViewDataBinding> Context.bottomSheet(invoker: (LayoutInflater) -> ViewBinding) =
    BottomSheetDialog(this, R.style.Style_Farsroidx_BottomSheetDialog)
        .apply {
            setContentView(invoker(layoutInflater).root)
        }

fun <T : ViewDataBinding> View.bottomSheet(invoker: (LayoutInflater) -> ViewBinding) =
    BottomSheetDialog(this.context, R.style.Style_Farsroidx_BottomSheetDialog)
        .apply {
            setContentView(invoker(layoutInflater).root)
        }
