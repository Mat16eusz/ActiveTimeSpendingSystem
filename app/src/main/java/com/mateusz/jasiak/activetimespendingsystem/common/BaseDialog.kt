package com.mateusz.jasiak.activetimespendingsystem.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mateusz.jasiak.activetimespendingsystem.R
import com.mateusz.jasiak.activetimespendingsystem.databinding.DialogBaseBinding
import com.mateusz.jasiak.activetimespendingsystem.utils.EMPTY_STRING
import com.mateusz.jasiak.activetimespendingsystem.utils.ScreenUtils.Companion.getScreenWidth

class BaseDialog : DialogFragment() {
    companion object {
        fun openDialog(
            fragmentManager: FragmentManager,
            title: String = EMPTY_STRING,
            message: String = EMPTY_STRING,
            dialogType: DialogType = DialogType.INFO,
            onNoClick: (() -> Unit)? = null,
            onYesClick: (() -> Unit)? = null,
            onConfirmClick: (() -> Unit)? = null,
        ): BaseDialog {
            val dialog = BaseDialog()
            dialog.show(
                fragmentManager,
                BASE_DIALOG
            )

            return dialog.apply {
                this.title = title
                this.message = message
                this.onNoClick = onNoClick
                this.onYesClick = onYesClick
                this.onConfirmClick = onConfirmClick
                this.dialogType = dialogType
            }
        }

        private const val BASE_DIALOG = "BASE_DIALOG"
    }

    private lateinit var binding: DialogBaseBinding
    private lateinit var title: String
    private lateinit var message: String
    private var onNoClick: (() -> Unit)? = null
    private var onYesClick: (() -> Unit)? = null
    private var onConfirmClick: (() -> Unit)? = null
    private var dialogType: DialogType = DialogType.INFO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_base, container, false)
        binding.title.text = title
        binding.message.text = message

        binding.buttonNo.setOnClickListener {
            onNoClick?.let { onNoClick -> onNoClick() }
            dismiss()
        }

        binding.buttonYes.setOnClickListener {
            onYesClick?.let { onYesClick -> onYesClick() }
            dismiss()
        }

        binding.buttonConfirm.setOnClickListener {
            onConfirmClick?.let { onConfirmClick -> onConfirmClick() }
            dismiss()
        }

        when (dialogType) {
            DialogType.INFO -> {
                binding.title.gravity = Gravity.CENTER
                binding.message.visibility = View.GONE
                binding.buttonNo.visibility = View.GONE
                binding.buttonYes.visibility = View.GONE
                binding.buttonConfirm.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            (getScreenWidth(requireActivity()) * 0.9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    enum class DialogType {
        INFO
    }
}