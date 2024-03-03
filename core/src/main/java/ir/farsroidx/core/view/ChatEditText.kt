@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package ir.farsroidx.core.view

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import ir.farsroidx.core.R
import ir.farsroidx.core.additives.bottomSheet
import ir.farsroidx.core.additives.gone
import ir.farsroidx.core.additives.vibrateDevice
import ir.farsroidx.core.additives.visible
import ir.farsroidx.core.databinding.LayoutChatEditTextBinding
import ir.farsroidx.core.databinding.SheetChatEmojiBinding

class ChatEditText : RelativeLayout {

    private lateinit var layoutInflater: LayoutInflater

    private lateinit var dataBinding: LayoutChatEditTextBinding

    private lateinit var inputViewState: InputViewState

    private lateinit var animationShow: Animation
    private lateinit var animationHide: Animation
    private lateinit var animationScaleUp: Animation
    private lateinit var animationScaleDown: Animation

    private var isEditingMode = false
    private var isEmojiMode   = false

    private lateinit var emojiSheetBinding: SheetChatEmojiBinding
    private lateinit var emojiSheetDialog : BottomSheetDialog

    private lateinit var inputMethodManager: InputMethodManager

    private var onButtonsClickedListener: OnButtonsClickedListener? = null

    var conversationId: String? = null
        set(value) {
            field = "conversation-$value"
        }

    constructor(context: Context) :
        this(context, null)

    constructor(context: Context, attrs: AttributeSet?) :
        this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
        : super(context, attrs, defStyleAttr, defStyleRes)
    {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {

        inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager

        layoutInflater = LayoutInflater.from(context)

        dataBinding = DataBindingUtil.inflate<LayoutChatEditTextBinding?>(
            layoutInflater, R.layout.layout_chat_edit_text, this, true
        ).apply {

            emojiSheetBinding = SheetChatEmojiBinding.inflate(layoutInflater)

            emojiSheetDialog = bottomSheet { emojiSheetBinding }

            btnSend.setOnClickListener(
                onClickListener {
                    onButtonsClickedListener?.onSendClicked(it)
                }
            )

            btnEmoji.setOnClickListener(
                onClickListener {
                    onButtonsClickedListener?.onEmojiClicked(it)
                    if (emojiSheetDialog.isShowing) {
                        emojiSheetDialog.dismiss()
                    } else {
                        emojiSheetDialog.show()
                    }
                }
            )

            btnAttach.setOnClickListener(
                onClickListener {
                    onButtonsClickedListener?.onAttachClicked(it)
                }
            )

            btnRecord.setOnClickListener(
                onClickListener {
                    onButtonsClickedListener?.onRecordClicked(it)
                }
            )

            input.setOnClickListener(
                onClickListener {
                    emojiSheetDialog.dismiss()
                }
            )
        }

        animationShow = AnimationUtils.loadAnimation(context, R.anim.anim_show_buttons)
        animationHide = AnimationUtils.loadAnimation(context, R.anim.anim_hide_buttons)
        animationScaleUp = AnimationUtils.loadAnimation(context, R.anim.anim_scale_up)
        animationScaleDown = AnimationUtils.loadAnimation(context, R.anim.anim_scale_down)

        attrs?.let {
            initAttributes(it, defStyleAttr, defStyleRes)
        }
    }

    private fun initAttributes(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {

        context.obtainStyledAttributes(attrs, R.styleable.ChatEditText, defStyleAttr, defStyleRes)
            .apply {

                inputViewState = InputViewState(

                    backgroundColor = this.getColor(R.styleable.ChatEditText_frsx_BackgroundColor, Color.WHITE),

                    text = this.getString(R.styleable.ChatEditText_frsx_Text) ?: "",
                    textColor = this.getColor(R.styleable.ChatEditText_frsx_TextColor, Color.BLACK),
                    textSizeSp = this.getFloat(R.styleable.ChatEditText_frsx_TextSizeSp, 14f),

                    hint = this.getString(R.styleable.ChatEditText_frsx_Hint) ?: "Type here...",
                    hintColor = this.getColor(R.styleable.ChatEditText_frsx_HintColor, Color.GRAY),

                    btnSendColor = this.getColor(R.styleable.ChatEditText_frsx_BtnSendColor, Color.BLUE),
                    btnEmojiColor = this.getColor(R.styleable.ChatEditText_frsx_BtnEmojiColor, Color.BLUE),
                    btnAttachColor = this.getColor(R.styleable.ChatEditText_frsx_BtnAttachColor, Color.BLUE),
                    btnRecordColor = this.getColor(R.styleable.ChatEditText_frsx_BtnRecordColor, Color.BLUE),

                    showEmojiButton = this.getBoolean(R.styleable.ChatEditText_frsx_ShowEmojiButton, true),
                    showRecordButton = this.getBoolean(R.styleable.ChatEditText_frsx_ShowRecordButton, true),

                    animateOnClicked = this.getBoolean(R.styleable.ChatEditText_frsx_AnimateOnClicked, true),
                    vibrateClickedButton = this.getBoolean(R.styleable.ChatEditText_frsx_VibrateClickedButton, true),
                    vibrateDuration = this.getInt(R.styleable.ChatEditText_frsx_VibrateDuration, 40)

                )

                recycle()
            }

        dataBinding.input.setTextSize(TypedValue.COMPLEX_UNIT_SP, inputViewState.textSizeSp)
        dataBinding.root.setBackgroundColor(inputViewState.backgroundColor)
        dataBinding.input.setHintTextColor(inputViewState.hintColor)
        dataBinding.input.setTextColor(inputViewState.textColor)
        dataBinding.input.setText(inputViewState.text)
        dataBinding.input.hint = inputViewState.hint

        dataBinding.input.addTextChangedListener(
            object : AbstractTextWatcher() {
                override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int)
                {
                    if (
                        (
                            isEditingMode && getTextAsString().isNotEmpty()
                        ) ||
                        (
                            !isEditingMode && getTextAsString().isEmpty()
                        )
                    ) return

                    if (getTextAsString().isEmpty()) {
                        toNormalMode()
                    } else {
                        toEditingMode()
                    }
                }
            }
        )

        dataBinding.btnSend.apply {
            setColorFilter(inputViewState.btnSendColor, PorterDuff.Mode.SRC_IN)
        }
        dataBinding.btnEmoji.apply {
            setColorFilter(inputViewState.btnEmojiColor, PorterDuff.Mode.SRC_IN)
            if (inputViewState.showEmojiButton) visible() else gone()
        }
        dataBinding.btnAttach.apply {
            setColorFilter(inputViewState.btnAttachColor, PorterDuff.Mode.SRC_IN)
        }
        dataBinding.btnRecord.apply {
            setColorFilter(inputViewState.btnRecordColor, PorterDuff.Mode.SRC_IN)
            if (inputViewState.showRecordButton) visible() else gone()
        }

        isEditingMode = getTextAsString().isNotEmpty()

        if (isEditingMode) {
            toEditingMode()
        }
    }

    private fun toEditingMode() {
        dataBinding.apply {
            btnAttach.startAnimation(animationHide)
            btnAttach.gone()
            if (inputViewState.showRecordButton) {
                btnRecord.startAnimation(animationHide)
                btnRecord.gone()
            }
            btnSend.visible()
            btnSend.startAnimation(animationShow)
            isEditingMode = true
            Log.d("CentralCore", "toEditingMode")
        }
    }

    private fun toNormalMode() {
        dataBinding.apply {
            btnSend.startAnimation(animationHide)
            btnSend.gone()
            btnAttach.visible()
            btnAttach.startAnimation(animationShow)
            if (inputViewState.showRecordButton) {
                btnRecord.visible()
                btnRecord.startAnimation(animationShow)
            }
            isEditingMode = false
            Log.i("CentralCore", "toNormalMode")
        }
    }

    private fun onClickListener(listener: (View) -> Unit = {}): OnClickListener {

        return OnClickListener { view -> listener(view)

            if (view.id == dataBinding.input.id && !isEmojiMode) return@OnClickListener

            else if (view.id == dataBinding.input.id) {
                dataBinding.btnEmoji.setImageResource(R.drawable.icon_mood)
                isEmojiMode = !isEmojiMode
            }

            else {

                view.startAnimation(animationScaleDown)

                animationScaleDown.setAnimationListener(
                    object : AbstractAnimationListener() {
                        override fun onAnimationEnd(animation: Animation) {

                            if (view.id == dataBinding.btnEmoji.id) {

                                if (isEmojiMode) {

                                    dataBinding.btnEmoji.setImageResource(R.drawable.icon_mood)

                                } else dataBinding.btnEmoji.setImageResource(R.drawable.icon_keyboard)

                                isEmojiMode = !isEmojiMode

                            } else if (view.id == dataBinding.input.id) {

                                if (isEmojiMode) {

                                    dataBinding.btnEmoji.setImageResource(R.drawable.icon_mood)

                                    isEmojiMode = !isEmojiMode
                                }
                            }

                            view.startAnimation(animationScaleUp)
                        }
                    }
                )

                if (inputViewState.vibrateClickedButton) {
                    context.vibrateDevice(
                        inputViewState.vibrateDuration.toLong()
                    )
                }
            }
        }
    }

    fun getTextAsString(): String {
        return dataBinding.input.text.toString().trim()
    }

    fun clearInput() {
        dataBinding.input.setText("")
    }

    fun addOnButtonsClicked(onButtonsClickedListener: OnButtonsClickedListener?) {
        this.onButtonsClickedListener = onButtonsClickedListener
    }

    fun addOnButtonsClicked(onButtonsClicked: AbstractOnButtonsClickedListener?) {
        this.onButtonsClickedListener = onButtonsClicked
    }

    interface OnButtonsClickedListener {
        fun onSendClicked(view: View)
        fun onAttachClicked(view: View)
        fun onRecordClicked(view: View)
        fun onEmojiClicked(view: View)
    }

    abstract class AbstractOnButtonsClickedListener: OnButtonsClickedListener {
        override fun onSendClicked(view: View) {}
        override fun onAttachClicked(view: View) {}
        override fun onRecordClicked(view: View) {}
        override fun onEmojiClicked(view: View) {}
    }

    private data class InputViewState(

        val backgroundColor: Int = Color.WHITE,

        val text: String = "",
        val textColor: Int = Color.BLACK,
        val textSizeSp: Float = 14f,

        val hint: String = "",
        val hintColor: Int = Color.GRAY,

        val btnSendColor: Int = Color.BLUE,
        val btnEmojiColor: Int = Color.BLUE,
        val btnAttachColor: Int = Color.BLUE,
        val btnRecordColor: Int = Color.BLUE,

        val showEmojiButton: Boolean = true,
        val showRecordButton: Boolean = true,

        val animateOnClicked: Boolean = true,
        val vibrateClickedButton: Boolean = true,
        val vibrateDuration: Int = 40

    )

    private abstract class AbstractTextWatcher : TextWatcher {
        override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(text: Editable) {}
    }

    private abstract class AbstractAnimationListener : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationEnd(animation: Animation) {}
        override fun onAnimationRepeat(animation: Animation) {}
    }

    companion object {

        fun installEmojiKeyboard(context: Context) {

//            AXEmojiManager.install(
//                context.applicationContext, AXGoogleEmojiProvider(
//                    context.applicationContext
//                )
//            )
//
//            // Theme
//
//            AXEmojiManager.getEmojiViewTheme().apply {
//                isFooterEnabled = false
//                footerSelectedItemColor = Color.parseColor("#1E2228")
//                footerBackgroundColor = Color.parseColor("#1E2228")
//                categoryColor = Color.parseColor("#22272E")
//                backgroundColor = Color.parseColor("#1E2228")
//                selectionColor = Color.parseColor("#F47067")
//                selectedColor = Color.parseColor("#F47067")
//                dividerColor = Color.TRANSPARENT
//                titleColor = Color.parseColor("#80F47067")
//            }
//
//            AXEmojiManager.getStickerViewTheme().apply {
//                isFooterEnabled = false
//                footerSelectedItemColor = Color.parseColor("#1E2228")
//                footerBackgroundColor = Color.parseColor("#1E2228")
//                categoryColor = Color.parseColor("#22272E")
//                backgroundColor = Color.parseColor("#1E2228")
//                selectionColor = Color.parseColor("#F47067")
//                selectedColor = Color.parseColor("#F47067")
//                dividerColor = Color.TRANSPARENT
//                titleColor = Color.parseColor("#80F47067")
//            }
//
//            // Backspace
//
//            AXEmojiManager.setBackspaceCategoryEnabled(true)
//
//            // Recent
//
//            AXEmojiManager.setMaxRecentSize(32)
//            AXEmojiManager.setMaxStickerRecentSize(16)
//            AXEmojiManager.setShowEmptyRecentEnabled(true)
//
//            val recentEmojiManager = RecentEmojiManager(context.applicationContext)
//                .apply {
////                    clearRecentEmoji()
//                }
//
//            val recentStickerManager = RecentStickerManager(context.applicationContext, "")
//                .apply {
////                    clear()
//                }
//
//            AXEmojiManager.setRecentEmoji(recentEmojiManager)
//            AXEmojiManager.setRecentSticker(recentStickerManager)
//
//            AXEmojiManager.setFillRecentHistoryEnabled(true)
        }
    }
}