/*
 * Copyright (c) 2021/  4/ 4.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.others.dialogs

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

    private lateinit var hAddMessageTemplateDialogBinding: AddMessageTemplateDialogBinding

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
                hAddMessageTemplateDialogBinding.root
        )
        return hAddMessageTemplateDialogBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hSetupListeners()
    }

    private fun hSetupListeners() {
        hAddMessageTemplateDialogBinding.hCancelB.setOnClickListener { v ->
            dismiss()
        }
        hAddMessageTemplateDialogBinding.hSaveB.setOnClickListener { v ->
            hCallBack.invoke(
                    hAddMessageTemplateDialogBinding.hTextInputET.text.toString()
            )
            dismiss()
        }
    }


    companion object {
        private lateinit var hCallBack: (message: String) -> Unit
        const val H_MESSAGE = "hMessage"
        fun newInstance(message: String?, callback: (message: String) -> Unit): AddMessageTemplateDialog {
            val hAddMessageTemplateDialog = AddMessageTemplateDialog()
            val args = Bundle()
            hCallBack = callback
            args.putString(H_MESSAGE, message)
            hAddMessageTemplateDialog.arguments = args
            return hAddMessageTemplateDialog
        }
    }
}

