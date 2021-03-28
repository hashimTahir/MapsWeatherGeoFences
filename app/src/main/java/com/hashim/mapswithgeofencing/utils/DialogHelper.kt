/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog


class DialogHelper {

    companion object {

        fun hCreateRadioDialog(
                hContext: Context,
                hTitle: String,
                hDialogOptions: Array<CharSequence>,
                hPositiveText: String,
                hNegativeText: String,
                hIsCancable: Boolean = true,
                hCallBack: (Int) -> Unit,
        ) {
            var hAlertDialog: AlertDialog? = null


            val hSelectedChoice = intArrayOf(0)

            val hBuilder = AlertDialog.Builder(hContext)
            hBuilder.setTitle(hTitle)
                    .setSingleChoiceItems(hDialogOptions, hSelectedChoice[0])
                    { dialog: DialogInterface?, choice: Int ->
                        hSelectedChoice[0] = choice
                    }
                    .setPositiveButton(hPositiveText)
                    { dialog: DialogInterface?, id: Int ->
                        hAlertDialog!!.dismiss()
                        hCallBack.invoke(hSelectedChoice[0])
                    }
                    .setNegativeButton(hNegativeText)
                    { dialog: DialogInterface?, id: Int ->
                        hAlertDialog?.dismiss()
                        hCallBack.invoke(hSelectedChoice[0])
                    }
                    .setCancelable(hIsCancable)
            hAlertDialog = hBuilder.create()
            hAlertDialog.show()
        }

        fun hCreateCheckBoxDialog(
                hContext: Context,
                hTitle: String,
                hDialogOptions: Array<CharSequence>,
                hPositiveText: String,
                hNegativeText: String,
                hIsCancable: Boolean = true,
                hCallBack: (Int) -> Unit
        ) {
            var hAlertDialog: AlertDialog? = null
            val hSelectedChoice = intArrayOf(0)
            val hBuilder = AlertDialog.Builder(hContext)
            hBuilder.setTitle(hTitle)
                    .setMultiChoiceItems(hDialogOptions, null)
                    { dialog: DialogInterface?, indexSelected: Int, isChecked: Boolean ->
                        if (isChecked) {
                            hSelectedChoice[0] = indexSelected
                        }
                    }
                    .setPositiveButton(hPositiveText)
                    { dialog: DialogInterface?, id: Int ->
                        hAlertDialog?.dismiss()
                        hCallBack.invoke(hSelectedChoice[0])
                    }
                    .setNegativeButton(hNegativeText)
                    { dialog: DialogInterface?, id: Int ->
                        hAlertDialog?.dismiss()
                    }
            hBuilder.setCancelable(hIsCancable)
            hAlertDialog = hBuilder.create()
            hAlertDialog.show()
        }

        fun hCreateConformationDialog(
                hContext: Context,
                hMessage: String,
                hPositiveText: String,
                hNegativeText: String,
                hIsCancable: Boolean = true,
                hCallBack: () -> Unit
        ) {

            var hAlertDialog: AlertDialog? = null
            val hBuilder = AlertDialog.Builder(hContext)
            hBuilder.setMessage(hMessage)
                    .setPositiveButton(hPositiveText)
                    { dialog: DialogInterface?, id: Int ->
                        hAlertDialog?.dismiss()
                        hCallBack.invoke()
                    }
                    .setNegativeButton(hNegativeText)
                    { dialog: DialogInterface?, id: Int ->
                        hAlertDialog?.dismiss()
                    }
                    .setCancelable(hIsCancable)
            hAlertDialog = hBuilder.create()
            hAlertDialog.show()
        }

        fun hCreateConformationTitleDialog(
                hContext: Context,
                hTitle: String,
                hMessage: String,
                hPositiveText: String,
                hNegativeText: String,
                hIsCancable: Boolean = true,
                hCallBack: () -> Unit

        ) {

            var hAlertDialog: AlertDialog? = null
            val hBuilder = AlertDialog.Builder(hContext)
            hBuilder
                    .setTitle(hTitle)
                    .setMessage(hMessage)
                    .setPositiveButton(hPositiveText)
                    { dialog: DialogInterface, id: Int ->
                        hAlertDialog!!.dismiss()
                        hCallBack.invoke()
                    }
                    .setNegativeButton(hNegativeText)
                    { dialog: DialogInterface, id: Int ->
                        hAlertDialog!!.dismiss()
                    }
            hBuilder.setCancelable(hIsCancable)

            hAlertDialog = hBuilder.create()
            hAlertDialog.show()
        }


        fun hCreateListDialog(
                hContext: Context,
                hTitle: String,
                hIsCancable: Boolean = true,
                hDialogOptions: Array<CharSequence>,
                hCallBack: (String, Int) -> Unit,
        ) {
            var hAlertDialog: AlertDialog? = null
            val hBuilder = AlertDialog.Builder(hContext)
            hBuilder.setTitle(hTitle)
                    .setItems(hDialogOptions)
                    { dialog: DialogInterface?, which: Int ->
                        hCallBack.invoke(hDialogOptions.get(which).toString(), which)
                    }

            hBuilder.setCancelable(hIsCancable)
            hAlertDialog = hBuilder.create()
            hAlertDialog.show()
        }
    }

}
