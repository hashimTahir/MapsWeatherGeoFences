/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.CustomView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.hashim.mapswithgeofencing.Helper.Constants;
import com.hashim.mapswithgeofencing.Helper.LogToastSnackHelper;
import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.Interfaces.DialogResponseInterface;
import com.hashim.mapswithgeofencing.R;
import com.hashim.mapswithgeofencing.databinding.CustomInputDialogBinding;

import java.util.Objects;

public class HcustomDialog extends DialogFragment {
    private static final String H_TITLE = "hTitle";
    private static final String H_MESSAGE = "hMessage";
    private static final String H_ICON = "hIcon";
    private static final String H_COLOR = "hColor";
    private static final String hTag = LogToastSnackHelper.hMakeTag(HcustomDialog.class);
    private static final String H_SEND_TO_ALL = "H_SEND_TO_ALL";
    private DialogResponseInterface hDialogResponseInterface;
    private String hTitle;
    private String hMessage;
    private int hColor;
    private int hIcon;


    private boolean hIsSendToAll;
    private CustomInputDialogBinding hCustomInputDialogBinding;


    public HcustomDialog() {

    }

    private void hSetListners() {
        hCustomInputDialogBinding.hOkB.setOnClickListener(view -> {
            String hNameNumber = UIHelper.hGetTextFromTextView(hCustomInputDialogBinding.hNameNumberTv).replaceAll("\\(.*?\\)", "");
            String hMessage = UIHelper.hGetTextFromTextView(hCustomInputDialogBinding.hTextTv);
            if (hIsSendToAll) {

                if (hMessage != null && !hMessage.equals("")) {
                    hDialogResponseInterface.hSubmitPositiveResponse(HcustomDialog.this, hMessage);
                } else {
                    LogToastSnackHelper.hMakeShortToast(Objects.requireNonNull(getContext()), "No text Entered");
                    dismiss();
                }

            } else {
                if (hNameNumber != null && !hNameNumber.equals("") && hMessage != null && !hMessage.equals("")) {
                    hDialogResponseInterface.hSubmitPositiveResponse(HcustomDialog.this,
                            hNameNumber.concat(Constants.H_CHECK_STRING).concat(hMessage));
                } else {
                    LogToastSnackHelper.hMakeShortToast(Objects.requireNonNull(getContext()), "No text Entered");
                    dismiss();
                }
            }

        });
        hCustomInputDialogBinding.hCancelB.setOnClickListener(view -> hDialogResponseInterface.hSubmitNegativeResponse(HcustomDialog.this));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hSetValues();

        try {
            hDialogResponseInterface = (DialogResponseInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement DialogClickListener interface");
        }
    }

    private void hSetValues() {
        if (getArguments() != null) {
            hTitle = getArguments().getString(H_TITLE);
            hMessage = getArguments().getString(H_MESSAGE);
            hIsSendToAll = getArguments().getBoolean(H_SEND_TO_ALL);
//            hColor = getArguments().getInt(H_COLOR);
            hIcon = getArguments().getInt(H_ICON);

        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return hTypeFaceDialog();
    }

    private Dialog hTypeFaceDialog() {
        AlertDialog.Builder hBuilder = new AlertDialog.Builder(getActivity());

        hCustomInputDialogBinding = CustomInputDialogBinding.inflate(getLayoutInflater());
        final LayoutInflater hDialogLayoutInflater = getActivity().getLayoutInflater();
        View hDilaogView = hDialogLayoutInflater.inflate(R.layout.custom_input_dialog, null);

        String[] fields = hTitle.split(Constants.H_CHECK_STRING);
        String name = fields[0];

        if (fields.length > 1) {
            String hNumber = fields[1];
            UIHelper.hSetTextToTextView(hCustomInputDialogBinding.hNameNumberTv, hNumber.concat(" (").concat(name).concat(")"));
        } else {
            UIHelper.hMakeVisibleInVisible(hCustomInputDialogBinding.hNameNumberTv, Constants.H_INVISIBLE);
        }

        String title = String.format(getString(R.string.send_text), name);
        UIHelper.hSetTextToTextView(hCustomInputDialogBinding.hDialogTitle, title);
        hSetListners();

        hBuilder.setView(hDilaogView);
        return hBuilder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static HcustomDialog newInstance(String param1, boolean hIsAllSendingClicked) {
        HcustomDialog hcustomDialog = new HcustomDialog();
        Bundle args = new Bundle();
        args.putString(H_TITLE, param1);
        args.putBoolean(H_SEND_TO_ALL, hIsAllSendingClicked);
        hcustomDialog.setArguments(args);
        return hcustomDialog;
    }
}
