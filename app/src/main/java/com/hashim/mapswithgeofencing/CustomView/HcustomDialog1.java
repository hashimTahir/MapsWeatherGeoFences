package com.hashim.mapswithgeofencing.CustomView;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.hashim.mapswithgeofencing.Helper.LogToastSnackHelper;
import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.Interfaces.DialogResponseInterface;
import com.hashim.mapswithgeofencing.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HcustomDialog1 extends DialogFragment {
    private static final String H_TITLE = "hTitle";
    private static final String H_MESSAGE = "hMessage";
    private static final String H_ICON = "hIcon";
    private static final String H_COLOR = "hColor";
    private static final String hTag = LogToastSnackHelper.hMakeTag(HcustomDialog1.class);
    private static final String H_SEND_TO_ALL = "H_SEND_TO_ALL";
    private DialogResponseInterface hDialogResponseInterface;
    private String hTitle;
    private String hMessage;
    private int hColor;
    private int hIcon;
    Unbinder unbinder;

    @BindView(R.id.hDialogTitle)
    TextView hDialogTitleTv;

    @BindView(R.id.hNameNumberTv)
    EditText hNameNumberTv;

    @BindView(R.id.hTextTv)
    EditText hMessageTv;

    @BindView(R.id.hOkB)
    Button hOkB;

    @BindView(R.id.hCancelB)
    Button hCancelB;

    public boolean ishIsSendToAll() {
        return hIsSendToAll;
    }

    public void sethIsSendToAll(boolean hIsSendToAll) {
        this.hIsSendToAll = hIsSendToAll;
    }

    private boolean hIsSendToAll;


    public HcustomDialog1() {

    }

    private void hSetListners() {
        hOkB.setOnClickListener(view -> {
            String hMessage = UIHelper.hGetTextFromTextView(hMessageTv);

            if (hMessage != null && !hMessage.equals("")) {
                hDialogResponseInterface.hSubmitPositiveResponse(HcustomDialog1.this, hMessage);
            } else {
                LogToastSnackHelper.hMakeShortToast(Objects.requireNonNull(getContext()), "No text Entered");
                dismiss();
            }


        });
        hCancelB.setOnClickListener(view -> hDialogResponseInterface.hSubmitNegativeResponse(HcustomDialog1.this));
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
            sethIsSendToAll(getArguments().getBoolean(H_SEND_TO_ALL));
//            hColor = getArguments().getInt(H_COLOR);
            hIcon = getArguments().getInt(H_ICON);

        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return hCreateDialog();
    }

    private Dialog hCreateDialog() {
        AlertDialog.Builder hBuilder = new AlertDialog.Builder(getActivity());

        final LayoutInflater hDialogLayoutInflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View hDilaogView = hDialogLayoutInflater.inflate(R.layout.custom_input_dialog_1, null);
        unbinder = ButterKnife.bind(this, hDilaogView);


        UIHelper.hSetTextToTextView(hMessageTv, hMessage);


        hSetListners();

        hBuilder.setView(hDilaogView);
        return hBuilder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static HcustomDialog1 newInstance(String param1, boolean hIsAllSendingClicked) {
        HcustomDialog1 hcustomDialog = new HcustomDialog1();
        Bundle args = new Bundle();
        args.putString(H_MESSAGE, param1);
        args.putBoolean(H_SEND_TO_ALL, hIsAllSendingClicked);
        hcustomDialog.setArguments(args);
        return hcustomDialog;
    }
}
