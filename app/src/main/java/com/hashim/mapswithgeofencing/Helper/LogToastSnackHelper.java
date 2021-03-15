package com.hashim.mapswithgeofencing.Helper;

import android.content.Context;
import com.google.android.material.snackbar.Snackbar;
import com.hashim.mapswithgeofencing.R;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;




public class LogToastSnackHelper {
    public static String hMakeTag(Class hClass) {
        return hClass.getSimpleName() + "H_TAG";
    }
    public static void hLogField(String hTag, String hMsg) {
        Log.d(hTag, hMsg);
    }

    public static void hMakeShortToast(Context hContext, String hToast) {
        int hToastOffSet = hContext.getResources().getInteger(R.integer.toast_offset);
        final Toast toast = Toast.makeText(hContext, hToast, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, hToastOffSet);
        toast.show();
    }

    public static void hMakeLongToast(Context hContext, String hToast) {
        int hToastOffSet = hContext.getResources().getInteger(R.integer.toast_offset);
        final Toast toast = Toast.makeText(hContext, hToast, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, hToastOffSet);
        toast.show();
    }

    public static void hMakeSnack(View view, String hSnackString) {
        Snackbar.make(view, hSnackString, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


//    public static void hMakeCustomToast(String message, int hOffset, Context context) {
//        int hToastOffSet = context.getResources().getInteger(R.integer.toast_offset);
//        final Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.BOTTOM, 0, hToastOffSet);
//        toast.show();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                toast.cancel();
//            }
//        }, 10000);
//
//    }


}
