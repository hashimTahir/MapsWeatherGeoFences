/*
 * Copyright (c) 2021/  3/ 25.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.SettingsPrefrences
import com.hashim.mapswithgeofencing.databinding.FragmentSettingsBinding
import com.hashim.mapswithgeofencing.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_settings_add_remove_contacts.view.*
import kotlinx.android.synthetic.main.item_settings_add_remove_locations.view.*
import kotlinx.android.synthetic.main.item_settings_test_notification.view.*
import javax.inject.Inject


@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var hSettingsPrefrences: SettingsPrefrences

    lateinit var hFragmentSettingsBinding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        hFragmentSettingsBinding = FragmentSettingsBinding.inflate(
                layoutInflater,
                container,
                false
        )
        return hFragmentSettingsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hInitView()

        hSetupListeners()

        hSubscribeObservers()
    }

    private fun hSetupListeners() {
        hFragmentSettingsBinding.hDistanceSpinner.onItemSelectedListener =
                object : OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        hSettingsPrefrences.hSaveDistanceUnit(position);
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
        hFragmentSettingsBinding.hLanguageSpinner.onItemSelectedListener =
                object : OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        hSettingsPrefrences.hSaveLanguage(position);
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
        hFragmentSettingsBinding.hTempratureSpinner.onItemSelectedListener =
                object : OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        hSettingsPrefrences.hSaveTempUnit(position);
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
        hFragmentSettingsBinding.hEnableDisableELayout.hEnableDisableSwitch.setOnClickListener {
            hEnableDisableEmergencySettings();
        }
        hFragmentSettingsBinding.hAddRemoveContactsELayout.hAddRemoveContactsELayout.setOnClickListener {
//            startActivity(new Intent (SettingsActivity.this, TrackMeActivity.class));
        }
        hFragmentSettingsBinding.hEditMessageELayout.hEditMessageELayout.setOnClickListener {
//            hEditEmergencyTrackeMeMessage(Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS);
        }
        hFragmentSettingsBinding.hTestNotificationELayout.hTestNotificationELayout.setOnClickListener {
//            if (!hIsTimerRunning) {
//                hCountDownTimer.start();
//                hSendMessageAndNotification();
//
//            } else {
//                String hTimeRemaing = String . format (Locale.getDefault(), "%d min, %d sec",
//                TimeUnit.MILLISECONDS.toMinutes(hTimeRemaining),
//                TimeUnit.MILLISECONDS.toSeconds(hTimeRemaining) -
//                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(hTimeRemaining))
//                );
//                LogToastSnackHelper.hMakeShortToast(this,
//                        "Text can be sent again after ".concat(hTimeRemaing));
//
//            }
        }
        hFragmentSettingsBinding.hEditMessageTLayout.hEditMessageELayout.setOnClickListener {
//            hEditEmergencyTrackeMeMessage(Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS);
        }
        hFragmentSettingsBinding.hTestNotificationTLayout.hTestNotificationELayout.setOnClickListener {
//            LogToastSnackHelper.hLogField(hTag, "Test Message T");
        }
        hFragmentSettingsBinding.hAddRemoveLocationsTLayout.setOnClickListener {
//            startActivity(new Intent (this, EmergencyContactsActivity.class));
        }
    }

    private fun hInitView() {
        val hDistanceAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.unit_spinner_array,
                android.R.layout.simple_spinner_dropdown_item
        )
        hDistanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        hFragmentSettingsBinding.hDistanceSpinner.setAdapter(hDistanceAdapter)
        hFragmentSettingsBinding.hDistanceSpinner.setSelection(hSettingsPrefrences.hGetDistanceUnit())


        val hLanguageAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.language_spinner_array,
                android.R.layout.simple_spinner_dropdown_item
        )
        hLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hFragmentSettingsBinding.hLanguageSpinner.setAdapter(hLanguageAdapter);
        hFragmentSettingsBinding.hLanguageSpinner.setSelection(hSettingsPrefrences.hGetLanguage())


        val hTempAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.temprature_spinner_array,
                android.R.layout.simple_spinner_dropdown_item
        )
        hFragmentSettingsBinding.hTempratureSpinner.setAdapter(hTempAdapter);
        hFragmentSettingsBinding.hTempratureSpinner.setSelection(hSettingsPrefrences.hGetTempUnit())


        val hIsEnableDisableEmergencySettings = hSettingsPrefrences.hGetEnableDisableEmergencySettings();
        hFragmentSettingsBinding.hEnableDisableELayout.hEnableDisableSwitch.setChecked(hIsEnableDisableEmergencySettings);
        hEnableDisableButtons(hIsEnableDisableEmergencySettings, Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS);

    }

    fun hEnableDisableButtons(hEnableDisableSettings: Boolean, hEnableDisableTrackMeSettings: String) {
        when (hEnableDisableTrackMeSettings) {
            Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS -> {
                hFragmentSettingsBinding.hAddRemoveContactsELayout.hAddRemoveContactsTv
                        .isEnabled = hEnableDisableSettings
                hFragmentSettingsBinding.hEditMessageELayout.hEditMessageTv
                        .isEnabled = hEnableDisableSettings
                hFragmentSettingsBinding.hTestNotificationELayout.hTestNotificationTv
                        .isEnabled = hEnableDisableSettings

                hFragmentSettingsBinding.hAddRemoveContactsELayout.hAddRemoveContactsELayout
                        .isEnabled = hEnableDisableSettings
                hFragmentSettingsBinding.hEditMessageELayout.hEditMessageTv
                        .isEnabled = hEnableDisableSettings
                hFragmentSettingsBinding.hTestNotificationELayout.hTestNotificationELayout
                        .isEnabled = hEnableDisableSettings

                hChangeEmergencyTextColors(hEnableDisableSettings)
            }

            Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS -> {

                hFragmentSettingsBinding.hAddRemoveLocationsTLayout
                        .isEnabled = hEnableDisableSettings
                hFragmentSettingsBinding.hEditMessageTLayout.hEditMessageELayout
                        .isEnabled = hEnableDisableSettings
                hFragmentSettingsBinding.hTestNotificationELayout.hTestNotificationELayout
                        .isEnabled = hEnableDisableSettings

                hChangeTrackmeTextColors(hEnableDisableSettings)
            }
        }
    }

    private fun hChangeTrackmeTextColors(hEnableDisableSettings: Boolean) {
        if (hEnableDisableSettings) {
            hFragmentSettingsBinding.hAddRemoveLocationsTLayout.hAddRemoveLocationsTV.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.black)
            )
            hFragmentSettingsBinding.hEditMessageTLayout.hEditMessageTv.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.black)
            )
            hFragmentSettingsBinding.hTestNotificationTLayout.hTestNotificationTv.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.black)
            )

        } else {
            hFragmentSettingsBinding.hAddRemoveLocationsTLayout.hAddRemoveLocationsTV.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.light_gray)
            )
            hFragmentSettingsBinding.hEditMessageTLayout.hEditMessageTv.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.light_gray)
            )
            hFragmentSettingsBinding.hTestNotificationTLayout.hTestNotificationTv.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.light_gray)
            )
        }
    }

    private fun hChangeEmergencyTextColors(hEnableDisableSettings: Boolean) {
        if (hEnableDisableSettings) {
            hFragmentSettingsBinding.hAddRemoveContactsELayout.hAddRemoveContactsELayout.hAddRemoveContactsTv
                    .setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.black)
                    )
            hFragmentSettingsBinding.hEditMessageELayout.hEditMessageTv.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.black)
            )
            hFragmentSettingsBinding.hTestNotificationELayout.hTestNotificationELayout.hTestNotificationTv.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.black)
            )
        } else {
            hFragmentSettingsBinding.hAddRemoveContactsELayout.hAddRemoveContactsELayout.hAddRemoveContactsTv
                    .setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.light_gray)
                    )
            hFragmentSettingsBinding.hEditMessageELayout.hEditMessageTv.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.light_gray)
            )
            hFragmentSettingsBinding.hTestNotificationELayout.hTestNotificationELayout.hTestNotificationTv
                    .setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.light_gray)
                    )
        }
    }

    private fun hEnableDisableEmergencySettings() {
//        if (EasyPermissions.hasPermissions(this, Constants.H_CONTACTS_PERMISSION)) {
            val hIsEnableDisableEmergencySettings = hFragmentSettingsBinding.hEnableDisableELayout.hEnableDisableSwitch.isChecked();
            hSettingsPrefrences.hSetEnableDisableEmergencySettings(hIsEnableDisableEmergencySettings);
            hEnableDisableButtons(hIsEnableDisableEmergencySettings, Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS);
//        } else {
//            hAskForPermissions(Constants.H_CONTACTS_PERMISSION);
//        }
    }

    private fun hSubscribeObservers() {

    }

    /*
    *
public class SettingsActivity extends AppCompatActivity /*implements
        AdapterView.OnItemSelectedListener, DialogResponseInterface,
        SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener*/ {


    private boolean hIsFromTrackMe = false;
    private int hCounter = 1;
    private SettingsPrefrences hSettingsPrefrences;
    //    private String hTag = LogToastSnackHelper.hMakeTag(SettingsActivity.class);
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


//
/
//
//        //Track Me Settings
//        hIsEnableDisableTrackMeSettings = hSettingsPrefrences.hGetEnableDisableTrackMeSettings();
//
////
////        hEnableDisableSwitchT.setChecked(hIsEnableDisableTrackMeSettings);
////        hEnableDisableSwitchT.setOnClickListener(v -> {
////            hEnableDisableTrackMeSettings();
////
////        });
//
//
//        hEnableDisableButtons(hIsEnableDisableTrackMeSettings, Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS);
//
//
//    }
//
//    private void hEnableDisableTrackMeSettings() {
//        if (EasyPermissions.hasPermissions(this, Constants.H_CONTACTS_PERMISSION)) {
//            hIsEnableDisableTrackMeSettings = hFragmentSettingsBinding.hEnableDisableELayout.hEnableDisableSwitch.isChecked();
//            hSettingsPrefrences.hSetEnableDisableTrackMeSettings(hIsEnableDisableTrackMeSettings);
//            hEnableDisableButtons(hIsEnableDisableTrackMeSettings, Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS);
//        } else {
//            hAskForPermissions(Constants.H_CONTACTS_PERMISSION);
//        }
//    }

//    private void hGetIntentData() {
//        Bundle hBundle = getIntent().getExtras();
//        if (hBundle != null) {
//            if (hBundle.getString(Constants.H_SETTINGS_IC).equals(Constants.H_TRACK_ME)) {
//                hIsFromTrackMe = true;
//            }
//
//        }
//    }
//

//    @Override
//    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//        hSettingsPrefrences.hSaveRadius(progress);
////        LogToastSnackHelper.hMakeShortToast(this, "Radius saved");
//    }
//
//    @Override
//    public void onStartTrackingTouch(SeekBar seekBar) {
//    }
//
//    @Override
//    public void onStopTrackingTouch(SeekBar seekBar) {
//    }
//
//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////        LogToastSnackHelper.hLogField(hTag, String.valueOf(hTrackSwitch.isChecked()));
//        hSettingsPrefrences.hSaveTrackingValue(isChecked);
//        if (isChecked) {
////            LogToastSnackHelper.hMakeShortToast(this, "Tracking Enabled ");
//        } else {
////            LogToastSnackHelper.hMakeShortToast(this, "Tracking Disabled ");
//
//        }
//    }

//
//    private void hSendMessageAndNotification() {
//        List<ContactsModelWithIds> hContactsModelWithIdsList = hSettingsPrefrences.hGetSavedContacts();
//        String message = hSettingsPrefrences.hGetEmergencyMessage();
//
//
//        if (hContactsModelWithIdsList == null || hContactsModelWithIdsList.size() == 0) {
//            LogToastSnackHelper.hMakeShortToast(this, getString(R.string.no_saved_contacts_plz));
//        }
//        if (message == null) {
//            LogToastSnackHelper.hMakeShortToast(this, getString(R.string.no_saved_message));
//        }
//
//        StringBuilder hStringBuilder = new StringBuilder();
//        boolean hIsTextSent = false;
//        if (hContactsModelWithIdsList != null && hContactsModelWithIdsList.size() != 0 && message != null) {
//            for (ContactsModelWithIds contactsModelWithIds : hContactsModelWithIdsList) {
//                //                    Todo: send Message
//                hSendSmsMessage(contactsModelWithIds.getContactNumber(), message);
//                hStringBuilder.append(contactsModelWithIds.getContactName().concat("\n"));
//            }
//            hIsTextSent = true;
//        }
//        if (hIsTextSent) {
//            hCreateNotificationChannel();
//            hCreateSendNotification(hStringBuilder.toString());
//        } else {
//            LogToastSnackHelper.hMakeShortToast(this, getString(R.string.unable_to_send_text_message));
//        }
//    }
//
//    private void hCreateSendNotification(String names) {
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(getString(R.string.text_message_sent_to_following))
//                .setContentText(names)
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText(names))
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//
//        notificationManager.notify(H_NOTIFICATION_ID, mBuilder.build());
//
//    }
//
//    public void hCreateNotificationChannel() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            channel.enableLights(true);
//            channel.setLightColor(Color.GREEN);
//
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//
//    private void hEditEmergencyTrackeMeMessage(String hEnableDisableTrackMeSettings) {
//        HLatLngModel hLatLngModel = hSettingsPrefrences.hGetCurrentLocation();
////        String hMessage = String.format(getString(R.string.emergency_contact_message),
////                hLatLngModel.getLatitude().concat(" ,").concat(hLatLngModel.getLongitude()));
////        LogToastSnackHelper.hLogField(hTag, hMessage);
//
//        switch (hEnableDisableTrackMeSettings) {
//            case Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS:
//                if (hLatLngModel != null) {
////                    HcustomDialog1 hcustomDialog = HcustomDialog1.newInstance(hMessage, true);
////                    hcustomDialog.show(getSupportFragmentManager(), "H_Dialog");
//                } else {
//
//                }
//                break;
//            case Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS:
//                if (hLatLngModel != null) {
////                    HcustomDialog1 hcustomDialog = HcustomDialog1.newInstance(hMessage, false);
////                    hcustomDialog.show(getSupportFragmentManager(), "H_Dialog");
//                } else {
//
//                }
//                break;
//
//        }
//
//
//    }
//

//
//
//    public void hSendSmsMessage(String number, String messager) {
//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage(number, null, messager, null, null);
//    }
//

//
//    @Override
//    public void hSubmitText(String hText) {
//
//    }
//
//    @Override
//    public void hSubmitNegativeResponse(DialogFragment hDialogFragment) {
//        hDialogFragment.dismiss();
//
//    }
//
//    @Override
//    public void hSubmitNeutralResponse(DialogFragment hDialogFragment) {
//
//    }
//
//    @Override
//    public void hSubmitPositiveResponse(DialogFragment hDialogFragment, String hUserName) {
//        HcustomDialog1 hcustomDialog = (HcustomDialog1) hDialogFragment;
//        boolean hIsEmergency = hcustomDialog.ishIsSendToAll();
//        if (hIsEmergency) {
//            hSettingsPrefrences.hSaveEmergencyMessage(hUserName);
//        } else {
//            hSettingsPrefrences.hSaveTrackMeMessage(hUserName);
//        }
//
//        hcustomDialog.dismiss();
//
//    }
//
//    @Override
//    public void hSubmitCloseResponse(boolean b) {
//
//    }


}

    * */

}