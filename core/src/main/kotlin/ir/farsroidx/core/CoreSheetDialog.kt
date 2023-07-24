package ir.farsroidx.core

import android.content.Context
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

internal class CoreSheetDialog<VDB : ViewDataBinding>(
    context: Context, private val initBinding: () -> VDB
) : BottomSheetDialog(context, R.style.Style_Farsroidx_BottomSheetDialog) {

    private lateinit var _binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this::_binding.isInitialized) {
            _binding = initBinding()
        }
        setContentView(_binding.root)
    }
}