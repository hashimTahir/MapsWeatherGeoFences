/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Helper;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;

import com.hashim.mapswithgeofencing.Interfaces.HDialogResponseInterface;


@SuppressWarnings("HardCodedStringLiteral")
public class DialogHelper {

    private final Context hContext;
    private HDialogResponseInterface hDialogResponseInterface;
    private int hDialogType;
    private AlertDialog hAlertDialog;


    public DialogHelper(Context context, HDialogResponseInterface dialogResponseInterface) {
        hContext = context;
        hDialogResponseInterface = dialogResponseInterface;

    }

    public void hRadioDialog(String title, CharSequence[] charSequences, boolean hIsCancable) {

//        SettingsPrefrences hSettingsPrefrences = new SettingsPrefrences(hContext);
        final int[] hSelectedChoice = {0};
//                = {hSettingsPrefrences.hGetServerType()};

        AlertDialog.Builder hBuilder = new AlertDialog.Builder(hContext);
        hBuilder.setTitle(title)
                .setSingleChoiceItems(charSequences, hSelectedChoice[0], (dialog, which) -> hSelectedChoice[0] = which)
                .setPositiveButton("OK", (dialog, id) -> {
                    hAlertDialog.dismiss();
                    hDialogResponseInterface.onPostiveResponse(id, hSelectedChoice[0]);

                }).setNegativeButton("Cancel", (dialog, id) -> {
            hAlertDialog.dismiss();
            hDialogResponseInterface.onNegtiveResponse(id, hSelectedChoice[0]);
            //  Your code when user clicked on Cancel
        });
        if (hIsCancable) {
            hBuilder.setCancelable(true);
        } else {
            hBuilder.setCancelable(false);
        }

        hAlertDialog = hBuilder.create();
        hAlertDialog.show();
    }


    public void hCheckBoxDialog(String title, CharSequence[] charSequences, boolean hIsCancable) {
        final int[] hSelectedChoice = {0};
        AlertDialog.Builder hBuilder = new AlertDialog.Builder(hContext);
        hBuilder.setTitle(title)
                .setMultiChoiceItems(charSequences, null, (dialog, indexSelected, isChecked) -> {
                    if (isChecked) {
                        hSelectedChoice[0] = indexSelected;
                    }
                })
                .setPositiveButton("OK", (dialog, id) -> {
                    hAlertDialog.dismiss();
                    hDialogResponseInterface.onPostiveResponse(id, hSelectedChoice[0]);
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    hAlertDialog.dismiss();
                    hDialogResponseInterface.onNegtiveResponse(id, hSelectedChoice[0]);
                });
        if (hIsCancable) {
            hBuilder.setCancelable(true);
        } else {
            hBuilder.setCancelable(false);
        }

        hAlertDialog = hBuilder.create();
        hAlertDialog.show();
    }

    public void hConformationDialog(String message, String positiveText, String neutralText, String negativeText, boolean hIsCancable) {

        AlertDialog.Builder hBuilder = new AlertDialog.Builder(hContext);
        hBuilder.setMessage(message)
                .setPositiveButton(positiveText, (dialog, id) -> {
                    hAlertDialog.dismiss();
                    hDialogResponseInterface.onPostiveResponse(id, hDialogType);
                });
        if (negativeText != null) {
            hBuilder.setNegativeButton(negativeText, (dialog, id) -> {
                hAlertDialog.dismiss();
                hDialogResponseInterface.onNegtiveResponse(id, hDialogType);
            });
        }

        if (neutralText != null) {
            hBuilder.setNeutralButton(neutralText, (dialog, which) -> {
                hAlertDialog.dismiss();
                hDialogResponseInterface.onNeutralResponse(which, hDialogType);
            });
        }

        if (hIsCancable) {
            hBuilder.setCancelable(true);
        } else {
            hBuilder.setCancelable(false);
        }

        hAlertDialog = hBuilder.create();
        hAlertDialog.show();


    }

    public void hConformationDialogWithTitle(String title, String message,
                                             String positiveText, String neutralText, String negativeText,
                                             boolean hIsCancable) {

        AlertDialog.Builder hBuilder = new AlertDialog.Builder(hContext);

        hBuilder.setMessage(message).setTitle(title)
                .setPositiveButton(positiveText, (dialog, id) -> {
                    hAlertDialog.dismiss();
                    hDialogResponseInterface.onPostiveResponse(id, hDialogType);
                })
                .setNegativeButton(negativeText, (dialog, id) -> {
                    hAlertDialog.dismiss();
                    hDialogResponseInterface.onNegtiveResponse(id, hDialogType);
                })
        ;
        if (neutralText != null) {
            hBuilder.setNeutralButton(neutralText, (dialog, which) -> {
                hAlertDialog.dismiss();
                hDialogResponseInterface.onNeutralResponse(which, hDialogType);
            });
        }
        if (hIsCancable) {
            hBuilder.setCancelable(true);
        } else {
            hBuilder.setCancelable(false);
        }

        hAlertDialog = hBuilder.create();
        hAlertDialog.show();
    }


    public void hConformationDialogWithTitleNeutral(String title, String message,
                                                    String positiveText, String negativeText, String neutralText,
                                                    boolean hIsCancable) {

        AlertDialog.Builder hBuilder = new AlertDialog.Builder(hContext);

        hBuilder.setMessage(message).setTitle(title)
                .setPositiveButton(positiveText, (dialog, id) -> {
                    hAlertDialog.dismiss();
                    hDialogResponseInterface.onPostiveResponse(id, hDialogType);
                })
                .setNegativeButton(negativeText, (dialog, id) -> {
                    hAlertDialog.dismiss();
                    hDialogResponseInterface.onNegtiveResponse(id, hDialogType);
                })
                .setNeutralButton(neutralText, (dialog, which) -> hAlertDialog.dismiss());
        if (hIsCancable) {
            hBuilder.setCancelable(true);
        } else {
            hBuilder.setCancelable(false);
        }

        hAlertDialog = hBuilder.create();
        hAlertDialog.show();
    }


    public void hListDialog(String title, final CharSequence[] charSequences, boolean hIsCancable) {
        AlertDialog.Builder hBuilder = new AlertDialog.Builder(hContext);
        hBuilder.setTitle(title)
                .setItems(charSequences, (dialog, which) -> {
                    hAlertDialog.dismiss();
                    hDialogResponseInterface.onPostiveResponse(which, hDialogType, charSequences[which]);
                });
        if (hIsCancable) {
            hBuilder.setCancelable(true);
        } else {
            hBuilder.setCancelable(false);
        }

        hAlertDialog = hBuilder.create();
        hAlertDialog.show();
    }

    public void hSetDialogType(int dialogtype) {
        this.hDialogType = dialogtype;
    }
}
