package ir.farsroidx.core.additives

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

// TODO: Instance ===================================================================== Instance ===

val Activity.thisActivity get() = this

val Fragment.thisFragment get() = this

val ViewModel.thisViewModel get() = this