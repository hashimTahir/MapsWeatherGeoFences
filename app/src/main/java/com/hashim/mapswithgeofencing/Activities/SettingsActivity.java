/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.hashim.mapswithgeofencing.Contacts.ContactsModelWithIds;
import com.hashim.mapswithgeofencing.CustomView.HcustomDialog1;
import com.hashim.mapswithgeofencing.EmergencyContacts.EmergencyContactsActivity;
import com.hashim.mapswithgeofencing.Helper.Constants;
import com.hashim.mapswithgeofencing.Helper.LogToastSnackHelper;
import com.hashim.mapswithgeofencing.Helper.ToolBarHelper;
import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.Interfaces.DialogResponseInterface;
import com.hashim.mapswithgeofencing.Models.HLatLngModel;
import com.hashim.mapswithgeofencing.R;
import com.hashim.mapswithgeofencing.TrackMe.TrackMeActivity;
import com.hashim.mapswithgeofencing.databinding.ActivitySettingsBinding;
import com.hashim.mapswithgeofencing.tokotlin.SettingsPrefrences;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class SettingsActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, DialogResponseInterface,
        SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {


    private boolean hIsFromTrackMe = false;
    private int hCounter = 1;
    private SettingsPrefrences hSettingsPrefrences;
    private String hTag = LogToastSnackHelper.hMakeTag(SettingsActivity.class);
    private boolean hIsEnableDisableEmergencySettings;
    private boolean hIsEnableDisableTrackMeSettings;
    private String CHANNEL_ID = "Hashim_channel";
    private static final int H_NOTIFICATION_ID = 1100;
    private final int H_CONTACTS_PERMISSION_CODE = 222;
    private TextView hAddRemoveLocationsTTv;
    private TextView hEditMessageTTv;
    private TextView hTestNotificationTTv;
    private long hTimeRemaining;
    private boolean hIsTimerRunning = false;
    private CountDownTimer hCountDownTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            hIsTimerRunning = true;
            hTimeRemaining = millisUntilFinished;
        }

        @Override
        public void onFinish() {
            hIsTimerRunning = false;

        }
    };
    private ActivitySettingsBinding hActivitySettingsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hActivitySettingsBinding = ActivitySettingsBinding.inflate(getLayoutInflater());

        setContentView(hActivitySettingsBinding.getRoot());
        UIHelper.hOreoOrientationCheck(this);

        hSettingsPrefrences = new SettingsPrefrences(this);

        ToolBarHelper hToolBarHelper = new ToolBarHelper(this);
        hToolBarHelper.hSetToolbar(hActivitySettingsBinding.hSAppBar.toolbar);
        hToolBarHelper.hSetToolbarTitle(hActivitySettingsBinding.hSAppBar.toolbarTitle, "Settings");

        hGetIntentData();

        hInitView();


    }

    private void hAskForPermissions(String permission) {
        switch (permission) {
            case Constants.H_CONTACTS_PERMISSION:
                EasyPermissions.requestPermissions(
                        new PermissionRequest.Builder(this, H_CONTACTS_PERMISSION_CODE, permission)
                                .setRationale("Grant the necessary Permissions")
                                .setPositiveButtonText("ok")
                                .setNegativeButtonText("Cancel")
                                .build());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    private void hInitView() {

        //spinner listeners
        hActivitySettingsBinding.hDistanceSpinner.setOnItemSelectedListener(this);
        hActivitySettingsBinding.hLanguageSpinner.setOnItemSelectedListener(this);
        hActivitySettingsBinding.hTempratureSpinner.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> hDistanceAdapter = ArrayAdapter.createFromResource(this,
                R.array.unit_spinner_array, android.R.layout.simple_spinner_item);
        hDistanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hActivitySettingsBinding.hDistanceSpinner.setAdapter(hDistanceAdapter);
        hActivitySettingsBinding.hDistanceSpinner.setSelection(hSettingsPrefrences.hGetDistanceUnit());

        ArrayAdapter<CharSequence> hLanguageAdapter = ArrayAdapter.createFromResource(this,
                R.array.language_spinner_array, android.R.layout.simple_spinner_item);
        hLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hActivitySettingsBinding.hLanguageSpinner.setAdapter(hLanguageAdapter);

        ArrayAdapter<CharSequence> hTempAdapter = ArrayAdapter.createFromResource(this,
                R.array.temprature_spinner_array, android.R.layout.simple_spinner_item);
        hTempAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hActivitySettingsBinding.hTempratureSpinner.setAdapter(hTempAdapter);
        hActivitySettingsBinding.hTempratureSpinner.setSelection(hSettingsPrefrences.hGetTempUnit());

        //Emergency Settings
        hIsEnableDisableEmergencySettings = hSettingsPrefrences.hGetEnableDisableEmergencySettings();
        hActivitySettingsBinding.hEnableDisableELayout.hEnableDisableSwitch.setChecked(hIsEnableDisableEmergencySettings);
        hEnableDisableButtons(hIsEnableDisableEmergencySettings, Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS);

        //Track Me Settings
        hIsEnableDisableTrackMeSettings = hSettingsPrefrences.hGetEnableDisableTrackMeSettings();

//
//        hEnableDisableSwitchT.setChecked(hIsEnableDisableTrackMeSettings);
//        hEnableDisableSwitchT.setOnClickListener(v -> {
//            hEnableDisableTrackMeSettings();
//
//        });


        hEnableDisableButtons(hIsEnableDisableTrackMeSettings, Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS);


    }

    private void hEnableDisableTrackMeSettings() {
        if (EasyPermissions.hasPermissions(this, Constants.H_CONTACTS_PERMISSION)) {
            hIsEnableDisableTrackMeSettings = hActivitySettingsBinding.hEnableDisableELayout.hEnableDisableSwitch.isChecked();
            hSettingsPrefrences.hSetEnableDisableTrackMeSettings(hIsEnableDisableTrackMeSettings);
            hEnableDisableButtons(hIsEnableDisableTrackMeSettings, Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS);
        } else {
            hAskForPermissions(Constants.H_CONTACTS_PERMISSION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UIHelper.hOreoOrientationCheck(this);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private void hGetIntentData() {
        Bundle hBundle = getIntent().getExtras();
        if (hBundle != null) {
            if (hBundle.getString(Constants.H_SETTINGS_IC).equals(Constants.H_TRACK_ME)) {
                hIsFromTrackMe = true;
            }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.hDistanceSpinner:
                hSettingsPrefrences.hSaveDistanceUnit(position);
                break;
            case R.id.hLanguageSpinner:
                hSettingsPrefrences.hSaveLanguage(position);
//                LogToastSnackHelper.hMakeShortToast(this, "Language settings saved");
                break;
            case R.id.hTempratureSpinner:
                hSettingsPrefrences.hSaveTempUnit(position);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        hSettingsPrefrences.hSaveRadius(progress);
//        LogToastSnackHelper.hMakeShortToast(this, "Radius saved");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        LogToastSnackHelper.hLogField(hTag, String.valueOf(hTrackSwitch.isChecked()));
        hSettingsPrefrences.hSaveTrackingValue(isChecked);
        if (isChecked) {
//            LogToastSnackHelper.hMakeShortToast(this, "Tracking Enabled ");
        } else {
//            LogToastSnackHelper.hMakeShortToast(this, "Tracking Disabled ");

        }
    }


    public void hSetupListeners(View view) {
        hActivitySettingsBinding.hEnableDisableELayout.hEnableDisableSwitch.setOnClickListener(v -> {
            hEnableDisableEmergencySettings();
        });
        hActivitySettingsBinding.hAddRemoveContactsELayout.hAddRemoveContactsELayout.setOnClickListener(v -> {
            startActivity(new Intent(SettingsActivity.this, TrackMeActivity.class));
        });
        hActivitySettingsBinding.hEditMessageELayout.hEditMessageELayout.setOnClickListener(v -> {
            hEditEmergencyTrackeMeMessage(Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS);
        });
        hActivitySettingsBinding.hTestNotificationELayout.hTestNotificationELayout.setOnClickListener(v -> {
            if (!hIsTimerRunning) {
                hCountDownTimer.start();
                hSendMessageAndNotification();

            } else {
                String hTimeRemaing = String.format(Locale.getDefault(), "%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(hTimeRemaining),
                        TimeUnit.MILLISECONDS.toSeconds(hTimeRemaining) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(hTimeRemaining))
                );
                LogToastSnackHelper.hMakeShortToast(this,
                        "Text can be sent again after ".concat(hTimeRemaing));

            }
        });
        hActivitySettingsBinding.hEditMessageTLayout.hEditMessageELayout.setOnClickListener(v -> {
            hEditEmergencyTrackeMeMessage(Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS);
        });
        hActivitySettingsBinding.hTestNotificationTLayout.hTestNotificationELayout.setOnClickListener(v -> {
            LogToastSnackHelper.hLogField(hTag, "Test Message T");
        });
        hActivitySettingsBinding.hAddRemoveLocationsTLayout.setOnClickListener(v -> {
            startActivity(new Intent(this, EmergencyContactsActivity.class));
        });

    }

    private void hSendMessageAndNotification() {
        List<ContactsModelWithIds> hContactsModelWithIdsList = hSettingsPrefrences.hGetSavedContacts();
        String message = hSettingsPrefrences.hGetEmergencyMessage();


        if (hContactsModelWithIdsList == null || hContactsModelWithIdsList.size() == 0) {
            LogToastSnackHelper.hMakeShortToast(this, getString(R.string.no_saved_contacts_plz));
        }
        if (message == null) {
            LogToastSnackHelper.hMakeShortToast(this, getString(R.string.no_saved_message));
        }

        StringBuilder hStringBuilder = new StringBuilder();
        boolean hIsTextSent = false;
        if (hContactsModelWithIdsList != null && hContactsModelWithIdsList.size() != 0 && message != null) {
            for (ContactsModelWithIds contactsModelWithIds : hContactsModelWithIdsList) {
                //                    Todo: send Message
                hSendSmsMessage(contactsModelWithIds.getContactNumber(), message);
                hStringBuilder.append(contactsModelWithIds.getContactName().concat("\n"));
            }
            hIsTextSent = true;
        }
        if (hIsTextSent) {
            hCreateNotificationChannel();
            hCreateSendNotification(hStringBuilder.toString());
        } else {
            LogToastSnackHelper.hMakeShortToast(this, getString(R.string.unable_to_send_text_message));
        }
    }

    private void hCreateSendNotification(String names) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.text_message_sent_to_following))
                .setContentText(names)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(names))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);


        notificationManager.notify(H_NOTIFICATION_ID, mBuilder.build());

    }

    public void hCreateNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void hEditEmergencyTrackeMeMessage(String hEnableDisableTrackMeSettings) {
        HLatLngModel hLatLngModel = hSettingsPrefrences.hGetCurrentLocation();
//        String hMessage = String.format(getString(R.string.emergency_contact_message),
//                hLatLngModel.getLatitude().concat(" ,").concat(hLatLngModel.getLongitude()));
//        LogToastSnackHelper.hLogField(hTag, hMessage);

        switch (hEnableDisableTrackMeSettings) {
            case Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS:
                if (hLatLngModel != null) {
//                    HcustomDialog1 hcustomDialog = HcustomDialog1.newInstance(hMessage, true);
//                    hcustomDialog.show(getSupportFragmentManager(), "H_Dialog");
                } else {

                }
                break;
            case Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS:
                if (hLatLngModel != null) {
//                    HcustomDialog1 hcustomDialog = HcustomDialog1.newInstance(hMessage, false);
//                    hcustomDialog.show(getSupportFragmentManager(), "H_Dialog");
                } else {

                }
                break;

        }


    }

    private void hEnableDisableEmergencySettings() {
        if (EasyPermissions.hasPermissions(this, Constants.H_CONTACTS_PERMISSION)) {
            hIsEnableDisableEmergencySettings = hActivitySettingsBinding.hEnableDisableELayout.hEnableDisableSwitch.isChecked();
            hSettingsPrefrences.hSetEnableDisableEmergencySettings(hIsEnableDisableEmergencySettings);
            hEnableDisableButtons(hIsEnableDisableEmergencySettings, Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS);
        } else {
            hAskForPermissions(Constants.H_CONTACTS_PERMISSION);
        }
    }


    public void hSendSmsMessage(String number, String messager) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, messager, null, null);
    }

    private void hEnableDisableButtons(boolean hEnableDisableSettings, String hEnableDisableTrackMeSettings) {
        switch (hEnableDisableTrackMeSettings) {
            case Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS:

                hActivitySettingsBinding.hAddRemoveContactsELayout.hAddRemoveContactsTv.setEnabled(hEnableDisableSettings);
                hActivitySettingsBinding.hEditMessageELayout.hEditMessageTv.setEnabled(hEnableDisableSettings);
                hActivitySettingsBinding.hTestNotificationELayout.hTestNotificationTv.setEnabled(hEnableDisableSettings);

                hActivitySettingsBinding.hAddRemoveContactsELayout.hAddRemoveContactsELayout.setEnabled(hEnableDisableSettings);
                hActivitySettingsBinding.hEditMessageELayout.hEditMessageTv.setEnabled(hEnableDisableSettings);
                hActivitySettingsBinding.hTestNotificationELayout.hTestNotificationELayout.setEnabled(hEnableDisableSettings);

                if (hEnableDisableSettings) {
                    UIHelper.hChangeColor(ContextCompat.getColor(this, R.color.black),
                            hActivitySettingsBinding.hAddRemoveContactsELayout.hAddRemoveContactsELayout, hActivitySettingsBinding.hEditMessageELayout.hEditMessageTv,
                            hActivitySettingsBinding.hTestNotificationELayout.hTestNotificationELayout);
                } else {
                    UIHelper.hChangeColor(ContextCompat.getColor(this, R.color.light_gray),
                            hActivitySettingsBinding.hAddRemoveContactsELayout.hAddRemoveContactsELayout, hActivitySettingsBinding.hEditMessageELayout.hEditMessageTv,
                            hActivitySettingsBinding.hTestNotificationELayout.hTestNotificationELayout);
                }

                break;
            case Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS:

                hAddRemoveLocationsTTv.setEnabled(hEnableDisableSettings);
                hEditMessageTTv.setEnabled(hEnableDisableSettings);
                hTestNotificationTTv.setEnabled(hEnableDisableSettings);

                hActivitySettingsBinding.hAddRemoveLocationsTLayout.setEnabled(hEnableDisableSettings);
                hActivitySettingsBinding.hEditMessageTLayout.hEditMessageELayout.setEnabled(hEnableDisableSettings);
                hActivitySettingsBinding.hTestNotificationELayout.hTestNotificationELayout.setEnabled(hEnableDisableSettings);

                if (hEnableDisableSettings) {
                    UIHelper.hChangeColor(ContextCompat.getColor(this, R.color.black),
                            hAddRemoveLocationsTTv, hEditMessageTTv, hTestNotificationTTv);
                } else {
                    UIHelper.hChangeColor(ContextCompat.getColor(this, R.color.light_gray),
                            hAddRemoveLocationsTTv, hEditMessageTTv, hTestNotificationTTv);
                }

                break;
        }
    }


    @Override
    public void hSubmitText(String hText) {

    }

    @Override
    public void hSubmitNegativeResponse(DialogFragment hDialogFragment) {
        hDialogFragment.dismiss();

    }

    @Override
    public void hSubmitNeutralResponse(DialogFragment hDialogFragment) {

    }

    @Override
    public void hSubmitPositiveResponse(DialogFragment hDialogFragment, String hUserName) {
        HcustomDialog1 hcustomDialog = (HcustomDialog1) hDialogFragment;
        boolean hIsEmergency = hcustomDialog.ishIsSendToAll();
        if (hIsEmergency) {
            hSettingsPrefrences.hSaveEmergencyMessage(hUserName);
        } else {
            hSettingsPrefrences.hSaveTrackMeMessage(hUserName);
        }

        hcustomDialog.dismiss();

    }

    @Override
    public void hSubmitCloseResponse(boolean b) {

    }


}
