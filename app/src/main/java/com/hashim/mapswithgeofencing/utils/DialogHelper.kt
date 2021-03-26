/*
 * Copyright (c) 2021/  3/ 26.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.hashim.mapswithgeofencing.tobeDeleted.Interfaces.HDialogResponseInterface


class DialogHelper(private val hContext: Context, private val hDialogResponseInterface: HDialogResponseInterface) {
    private var hDialogType = 0
    private var hAlertDialog: AlertDialog? = null
    fun hRadioDialog(title: String?, charSequences: Array<CharSequence?>?, hIsCancable: Boolean) {

//        SettingsPrefrences hSettingsPrefrences = new SettingsPrefrences(hContext);
        val hSelectedChoice = intArrayOf(0)
        //                = {hSettingsPrefrences.hGetServerType()};
        val hBuilder = AlertDialog.Builder(hContext)
        hBuilder.setTitle(title)
                .setSingleChoiceItems(charSequences, hSelectedChoice[0]) { dialog: DialogInterface?, which: Int -> hSelectedChoice[0] = which }
                .setPositiveButton("OK") { dialog: DialogInterface?, id: Int ->
                    hAlertDialog!!.dismiss()
                    hDialogResponseInterface.onPostiveResponse(id, hSelectedChoice[0])
                }.setNegativeButton("Cancel") { dialog: DialogInterface?, id: Int ->
                    hAlertDialog!!.dismiss()
                    hDialogResponseInterface.onNegtiveResponse(id, hSelectedChoice[0])
                }
        if (hIsCancable) {
            hBuilder.setCancelable(true)
        } else {
            hBuilder.setCancelable(false)
        }
        hAlertDialog = hBuilder.create()
        hAlertDialog!!.show()
    }

    fun hCheckBoxDialog(title: String?, charSequences: Array<CharSequence?>?, hIsCancable: Boolean) {
        val hSelectedChoice = intArrayOf(0)
        val hBuilder = AlertDialog.Builder(hContext)
        hBuilder.setTitle(title)
                .setMultiChoiceItems(charSequences, null) { dialog: DialogInterface?, indexSelected: Int, isChecked: Boolean ->
                    if (isChecked) {
                        hSelectedChoice[0] = indexSelected
                    }
                }
                .setPositiveButton("OK") { dialog: DialogInterface?, id: Int ->
                    hAlertDialog!!.dismiss()
                    hDialogResponseInterface.onPostiveResponse(id, hSelectedChoice[0])
                }
                .setNegativeButton("Cancel") { dialog: DialogInterface?, id: Int ->
                    hAlertDialog!!.dismiss()
                    hDialogResponseInterface.onNegtiveResponse(id, hSelectedChoice[0])
                }
        if (hIsCancable) {
            hBuilder.setCancelable(true)
        } else {
            hBuilder.setCancelable(false)
        }
        hAlertDialog = hBuilder.create()
        hAlertDialog!!.show()
    }

    fun hConformationDialog(message: String?, positiveText: String?, neutralText: String?, negativeText: String?, hIsCancable: Boolean) {
        val hBuilder = AlertDialog.Builder(hContext)
        hBuilder.setMessage(message)
                .setPositiveButton(positiveText) { dialog: DialogInterface?, id: Int ->
                    hAlertDialog!!.dismiss()
                    hDialogResponseInterface.onPostiveResponse(id, hDialogType)
                }
        if (negativeText != null) {
            hBuilder.setNegativeButton(negativeText) { dialog: DialogInterface?, id: Int ->
                hAlertDialog!!.dismiss()
                hDialogResponseInterface.onNegtiveResponse(id, hDialogType)
            }
        }
        if (neutralText != null) {
            hBuilder.setNeutralButton(neutralText) { dialog: DialogInterface?, which: Int ->
                hAlertDialog!!.dismiss()
                hDialogResponseInterface.onNeutralResponse(which, hDialogType)
            }
        }
        if (hIsCancable) {
            hBuilder.setCancelable(true)
        } else {
            hBuilder.setCancelable(false)
        }
        hAlertDialog = hBuilder.create()
        hAlertDialog!!.show()
    }

    fun hConformationDialogWithTitle(title: String?, message: String?,
                                     positiveText: String?, neutralText: String?, negativeText: String?,
                                     hIsCancable: Boolean) {
        val hBuilder = AlertDialog.Builder(hContext)
        hBuilder.setMessage(message).setTitle(title)
                .setPositiveButton(positiveText) { dialog: DialogInterface?, id: Int ->
                    hAlertDialog!!.dismiss()
                    hDialogResponseInterface.onPostiveResponse(id, hDialogType)
                }
                .setNegativeButton(negativeText) { dialog: DialogInterface?, id: Int ->
                    hAlertDialog!!.dismiss()
                    hDialogResponseInterface.onNegtiveResponse(id, hDialogType)
                }
        if (neutralText != null) {
            hBuilder.setNeutralButton(neutralText) { dialog: DialogInterface?, which: Int ->
                hAlertDialog!!.dismiss()
                hDialogResponseInterface.onNeutralResponse(which, hDialogType)
            }
        }
        if (hIsCancable) {
            hBuilder.setCancelable(true)
        } else {
            hBuilder.setCancelable(false)
        }
        hAlertDialog = hBuilder.create()
        hAlertDialog!!.show()
    }

    fun hConformationDialogWithTitleNeutral(title: String?, message: String?,
                                            positiveText: String?, negativeText: String?, neutralText: String?,
                                            hIsCancable: Boolean) {
        val hBuilder = AlertDialog.Builder(hContext)
        hBuilder.setMessage(message).setTitle(title)
                .setPositiveButton(positiveText) { dialog: DialogInterface?, id: Int ->
                    hAlertDialog!!.dismiss()
                    hDialogResponseInterface.onPostiveResponse(id, hDialogType)
                }
                .setNegativeButton(negativeText) { dialog: DialogInterface?, id: Int ->
                    hAlertDialog!!.dismiss()
                    hDialogResponseInterface.onNegtiveResponse(id, hDialogType)
                }
                .setNeutralButton(neutralText) { dialog: DialogInterface?, which: Int -> hAlertDialog!!.dismiss() }
        if (hIsCancable) {
            hBuilder.setCancelable(true)
        } else {
            hBuilder.setCancelable(false)
        }
        hAlertDialog = hBuilder.create()
        hAlertDialog!!.show()
    }

    fun hListDialog(title: String?, charSequences: Array<CharSequence?>, hIsCancable: Boolean) {
        val hBuilder = AlertDialog.Builder(hContext)
        hBuilder.setTitle(title)
                .setItems(charSequences) { dialog: DialogInterface?, which: Int ->
                    hAlertDialog!!.dismiss()
                    hDialogResponseInterface.onPostiveResponse(which, hDialogType, charSequences[which])
                }
        if (hIsCancable) {
            hBuilder.setCancelable(true)
        } else {
            hBuilder.setCancelable(false)
        }
        hAlertDialog = hBuilder.create()
        hAlertDialog!!.show()
    }

    fun hSetDialogType(dialogtype: Int) {
        hDialogType = dialogtype
    }
}
