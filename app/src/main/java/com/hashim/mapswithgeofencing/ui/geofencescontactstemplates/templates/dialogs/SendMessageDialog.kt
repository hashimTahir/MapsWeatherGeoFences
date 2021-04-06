/*
 * Copyright (c) 2021/  4/ 4.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.templates.dialogs

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

    private lateinit var hSendMessageBottomDialogBinding: SendMessageBottomDialogBinding

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
                hSendMessageBottomDialogBinding.root
        )
        return hSendMessageBottomDialogBinding.root
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
    }


    companion object {
        private lateinit var hSendMessageCallback: () -> Unit

        fun newInstance(callback: () -> Unit): SendMessageDialog {
            val hEditBottomSheetDialog = SendMessageDialog()
            hSendMessageCallback = callback
            val args = Bundle()

            hEditBottomSheetDialog.arguments = args
            return hEditBottomSheetDialog
        }
    }
}

