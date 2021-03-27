/*
 * Copyright (c) 2021/  3/ 27.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hashim.mapswithgeofencing.databinding.SendMessageBottomDialogBinding
import com.hashim.mapswithgeofencing.utils.KeyboardUtils
import java.util.*

class SendMessageDialog : BottomSheetDialogFragment() {

    private var hType: String? = null
    private var hToDisplay: String? = null
    private lateinit var hSendMessageBottomDialogBinding: SendMessageBottomDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (arguments != null) {
//            hType = arguments!!.getString(H_TYPE)
//            hToDisplay = arguments!!.getString(H_TO_DISPLAY)
//        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {

        hSendMessageBottomDialogBinding = SendMessageBottomDialogBinding.inflate(
                layoutInflater,
                container,
                false
        )
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        Objects.requireNonNull(dialog)!!.setCanceledOnTouchOutside(true)
        KeyboardUtils(
                dialog!!.window!!.decorView,
                hSendMessageBottomDialogBinding.getRoot()
        )
        return hSendMessageBottomDialogBinding.getRoot()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hSetupView()
        hSetupListeners()
    }

    private fun hSetupListeners() {
        hSendMessageBottomDialogBinding.hCancelB.setOnClickListener { v -> dismiss() }
        hSendMessageBottomDialogBinding.hSaveB.setOnClickListener { v ->
            hSendMessageCallback.invoke()
            dismiss()
        }
    }

    private fun hSetupView() {
//        hDialogEditBottomSheetBinding.hTitleTv.setText(hType)
//        if (hToDisplay != null) {
//            hDialogEditBottomSheetBinding.hTextInputET.setText(hToDisplay)
//        }
//        hDialogEditBottomSheetBinding.hTextInputET.requestFocus()
    }


    companion object {
        private lateinit var hSendMessageCallback: () -> Unit
        const val H_TITLE = "hTitle"
        const val H_MESSAGE = "hMessage"
        fun newInstance(type: String?, toDisplay: String?, callback: () -> Unit): SendMessageDialog {
            val hEditBottomSheetDialog = SendMessageDialog()
            hSendMessageCallback = callback
            val args = Bundle()
            args.putString(H_TITLE, type)
            args.putString(H_MESSAGE, toDisplay)
            hEditBottomSheetDialog.arguments = args
            return hEditBottomSheetDialog
        }
    }
}

