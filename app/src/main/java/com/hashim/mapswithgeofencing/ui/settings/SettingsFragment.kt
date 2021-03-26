/*
 * Copyright (c) 2021/  3/ 25.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.settings

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.SettingsPrefrences
import com.hashim.mapswithgeofencing.databinding.FragmentSettingsBinding
import com.hashim.mapswithgeofencing.tobeDeleted.Contacts.ContactsModelWithIds
import com.hashim.mapswithgeofencing.utils.Constants
import com.hashim.mapswithgeofencing.utils.Constants.Companion.CHANNEL_ID
import com.hashim.mapswithgeofencing.utils.Constants.Companion.H_NOTIFICATION_ID
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
            hEditEmergencyTrackeMeMessage(Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS);
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
            hEditEmergencyTrackeMeMessage(Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS);
        }
        hFragmentSettingsBinding.hTestNotificationTLayout.hTestNotificationELayout.setOnClickListener {
//            LogToastSnackHelper.hLogField(hTag, "Test Message T");
        }
        hFragmentSettingsBinding.hAddRemoveLocationsTLayout.setOnClickListener {
//            startActivity(new Intent (this, EmergencyContactsActivity.class));
        }

        hFragmentSettingsBinding.hEnableDisableTLayout.hEnableDisableSwitch.setOnCheckedChangeListener { buttonView, isChecked ->

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

    private fun hEditEmergencyTrackeMeMessage(hEnableDisableTrackMeSettings: String) {
//        HLatLngModel hLatLngModel = hSettingsPrefrences . hGetCurrentLocation ();
//        String hMessage = String.format(getString(R.string.emergency_contact_message),
//                hLatLngModel.getLatitude().concat(" ,").concat(hLatLngModel.getLongitude()));
//        LogToastSnackHelper.hLogField(hTag, hMessage);

        when (hEnableDisableTrackMeSettings) {
            Constants.H_ENABLE_DISABLE_EMERGENCY_SETTINGS -> {
//                if (hLatLngModel != null) {
////                    HcustomDialog1 hcustomDialog = HcustomDialog1.newInstance(hMessage, true);
////                    hcustomDialog.show(getSupportFragmentManager(), "H_Dialog");
//                } else {
//
//                }
            }
            Constants.H_ENABLE_DISABLE_TRACK_ME_SETTINGS -> {
//                if (hLatLngModel != null) {
////                    HcustomDialog1 hcustomDialog = HcustomDialog1.newInstance(hMessage, false);
////                    hcustomDialog.show(getSupportFragmentManager(), "H_Dialog");
//                } else {
//
//                }
            }
        }


    }


    private fun hSendMessageAndNotification() {
        val hContactsModelWithIdsList: List<ContactsModelWithIds> = hSettingsPrefrences.hGetSavedContacts() as List<ContactsModelWithIds>;
        val message: String? = hSettingsPrefrences.hGetEmergencyMessage()


        if (hContactsModelWithIdsList == null || hContactsModelWithIdsList.size == 0) {
//            LogToastSnackHelper.hMakeShortToast(this, getString(R.string.no_saved_contacts_plz))
        }
        if (message == null) {
//            LogToastSnackHelper.hMakeShortToast(this, getString(R.string.no_saved_message));
        }

        val hStringBuilder = StringBuilder()
        var hIsTextSent: Boolean = false
        if (hContactsModelWithIdsList != null && hContactsModelWithIdsList.size != 0 && message != null) {
            for (contactsModelWithIds in hContactsModelWithIdsList) {
                //                    Todo: send Message
                hSendSmsMessage(contactsModelWithIds.getContactNumber(), message);
                hStringBuilder.append(contactsModelWithIds.getContactName() + ("\n"));
            }
            hIsTextSent = true
        }
        if (hIsTextSent) {
            hCreateNotificationChannel();
            hCreateSendNotification(hStringBuilder.toString())
        } else {
//            LogToastSnackHelper.hMakeShortToast(this, getString(R.string.unable_to_send_text_message));
        }
    }

    private fun hCreateSendNotification(names: String) {
        val hNotificationBuilder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.text_message_sent_to_following))
                .setContentText(names)
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(names))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        val hNotificationManagerCompat = NotificationManagerCompat.from(requireContext());


        hNotificationManagerCompat.notify(H_NOTIFICATION_ID, hNotificationBuilder.build());

    }

    private fun hCreateNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = getString(R.string.channel_name)
            val description: CharSequence = getString(R.string.channel_description)

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance);
            channel.description = description as String?
            channel.enableLights(true)
            channel.lightColor = Color.GREEN

            val hNotificationManager = getSystemService(requireContext(), NotificationManager::class.java)
            hNotificationManager?.createNotificationChannel(channel)
        }
    }


    fun hSendSmsMessage(number: String, messager: String) {
        val hSmsManager = SmsManager.getDefault();
        hSmsManager.sendTextMessage(number, null, messager, null, null);
    }
}