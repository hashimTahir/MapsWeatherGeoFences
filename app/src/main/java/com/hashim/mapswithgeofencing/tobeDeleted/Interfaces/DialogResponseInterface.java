/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tobeDeleted.Interfaces;


import androidx.fragment.app.DialogFragment;

public interface DialogResponseInterface {

    void hSubmitText(String hText);

    void hSubmitNegativeResponse(DialogFragment hDialogFragment);

    void hSubmitNeutralResponse(DialogFragment hDialogFragment);

    void hSubmitPositiveResponse(DialogFragment hDialogFragment, String hUserName);


    void hSubmitCloseResponse(boolean b);


}