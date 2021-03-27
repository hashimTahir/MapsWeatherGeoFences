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
import com.hashim.mapswithgeofencing.databinding.AddMessageTemplateDialogBinding
import com.hashim.mapswithgeofencing.utils.KeyboardUtils
import java.util.*

class AddMessageTemplateDialog : BottomSheetDialogFragment() {

    private var hType: String? = null
    private var hToDisplay: String? = null
    private lateinit var hAddMessageTemplateDialogBinding: AddMessageTemplateDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (arguments != null) {
//            hType = arguments!!.getString(H_TYPE)
//            hToDisplay = arguments!!.getString(H_TO_DISPLAY)
//        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {

        hAddMessageTemplateDialogBinding = AddMessageTemplateDialogBinding.inflate(
                layoutInflater,
                container,
                false
        )
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        Objects.requireNonNull(dialog)!!.setCanceledOnTouchOutside(true)
        KeyboardUtils(
                dialog!!.window!!.decorView,
                hAddMessageTemplateDialogBinding.getRoot()
        )
        return hAddMessageTemplateDialogBinding.getRoot()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hSetupView()
        hSetupListeners()
    }

    private fun hSetupListeners() {
//        hDialogEditBottomSheetBinding.hCancelB.setOnClickListener { v -> dismiss() }
//        hDialogEditBottomSheetBinding.hSaveB.setOnClickListener { v ->
//            hEditBottomSheetDialogCallback!!.hOnSave(
//                    hType,
//                    hDialogEditBottomSheetBinding.hTextInputET.getText()
//                            .toString()
//            )
//            dismiss()
//        }
    }

    private fun hSetupView() {
//        hDialogEditBottomSheetBinding.hTitleTv.setText(hType)
//        if (hToDisplay != null) {
//            hDialogEditBottomSheetBinding.hTextInputET.setText(hToDisplay)
//        }
//        hDialogEditBottomSheetBinding.hTextInputET.requestFocus()
    }


    companion object {
        const val H_TITLE = "hTitle"
        const val H_MESSAGE = "hMessage"
        fun newInstance(type: String?, toDisplay: String?): AddMessageTemplateDialog {
            val hAddMessageTemplateDialog = AddMessageTemplateDialog()
            val args = Bundle()
            args.putString(H_TITLE, type)
            args.putString(H_MESSAGE, toDisplay)
            hAddMessageTemplateDialog.arguments = args
            return hAddMessageTemplateDialog
        }
    }
}

