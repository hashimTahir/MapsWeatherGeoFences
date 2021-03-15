package com.hashim.mapswithgeofencing.Helper;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;


public class UIHelper {
    public static void hSetTextToTextView(TextView hTextView, String hString) {
        hTextView.setText(hString);
    }

    public static void hSetTextToTextView(TextView hTextView, Spanned hString) {
        hTextView.setText(hString);
    }

    public static String hGetTextFromTextView(TextView hTextView) {
        return hTextView.getText().toString();
    }

    public static void hMakeVisibleInVisible(View view, short i) {
        switch (i) {
            case Constants.H_VISIBLE:
                view.setVisibility(View.VISIBLE);
                break;
            case Constants.H_INVISIBLE:
                view.setVisibility(View.GONE);
                break;

        }
    }

    public static void hSetImageToImageView(ImageView hImageView, int icon) {
        hImageView.setImageResource(icon);

    }

    public static void hHideSoftKeyBoard(FragmentActivity activity, View view) {
        InputMethodManager hInputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        hInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hOreoOrientationCheck(AppCompatActivity appCompatActivity) {
        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            appCompatActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public static void hChangeColor(int color, View... views) {
        for (View view : views) {
            TextView hTextView = (TextView) view;
            hTextView.setTextColor(color);

        }

    }
}
