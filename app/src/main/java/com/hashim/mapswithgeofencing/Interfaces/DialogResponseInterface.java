package com.hashim.mapswithgeofencing.Interfaces;


import androidx.fragment.app.DialogFragment;

public interface DialogResponseInterface {

    void hSubmitText(String hText);

    void hSubmitNegativeResponse(DialogFragment hDialogFragment);

    void hSubmitNeutralResponse(DialogFragment hDialogFragment);

    void hSubmitPositiveResponse(DialogFragment hDialogFragment, String hUserName);


    void hSubmitCloseResponse(boolean b);


}