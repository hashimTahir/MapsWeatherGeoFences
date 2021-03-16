package com.hashim.mapswithgeofencing.TrackMe;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hashim.mapswithgeofencing.Contacts.ContactsModelWithIds;
import com.hashim.mapswithgeofencing.Contacts.Hcomparator1;
import com.hashim.mapswithgeofencing.Contacts.RecyclerItemClickListener;
import com.hashim.mapswithgeofencing.CustomView.HcustomDialog;
import com.hashim.mapswithgeofencing.Helper.Constants;
import com.hashim.mapswithgeofencing.Helper.ListUtils;
import com.hashim.mapswithgeofencing.Helper.PrimaryActionMode;
import com.hashim.mapswithgeofencing.Helper.ToolBarHelper;
import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.Interfaces.DeleteCallBack;
import com.hashim.mapswithgeofencing.Interfaces.DialogResponseInterface;
import com.hashim.mapswithgeofencing.Interfaces.OnItemClickListener;
import com.hashim.mapswithgeofencing.Interfaces.onActionModeListener;
import com.hashim.mapswithgeofencing.Prefrences.SettingsPrefrences;
import com.hashim.mapswithgeofencing.R;

import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrackMeActivity extends AppCompatActivity implements
        OnItemClickListener, DialogResponseInterface, DeleteCallBack, onActionModeListener {

    @BindView(R.id.toolbar_title)
    TextView hToolbarTitle;

    @BindView(R.id.hATmAppBar)
    Toolbar hToolbar;

    @BindView(R.id.hTrackMeRV)
    RecyclerView hTrackMeRV;

    @BindView(R.id.hAddContactsFB)
    FloatingActionButton hAddContactsFB;

    @BindView(R.id.hAlertTextView)
    AppCompatTextView hAlertTextView;

    @BindView(R.id.hSendMessage)
    Button hSendMessage;

    private ContactsAdapter1 hContactsAdapter;
    private List<ContactsModelWithIds> hSavedContacatsList;
    private List<ContactsModelWithIds> hAllContactsList = new ArrayList<>();
    private int hWhatLoaded;
    private static final int H_SHOW_SAVE_CONTACTS_LAYOUT = 222;
    private static final int H_SHOW_RECYCLER_LAYOUT = 123;
    private static final int H_SHOW_ALERT_LAYOUT = 456;
    public static final int H_T_SAVED_LIST = 673;
    public static final int H_ALL_LIST = 910;
    private boolean hIsSaveLayoutVisible;
    private boolean hContactsRetrieved = false;
    private PrimaryActionMode hPrimaryActionMode;
    private boolean isMultiSelect = false;
    private List<Integer> hSelectedIds = new ArrayList();
    private boolean hIsAllSelected = false;
    SettingsPrefrences hSettingsPrefrences;
    private boolean hIsAllSendingClicked = false;
    private boolean hIsSelectedList = false;
    public static final int MENU_ITEM_MESSAGE = 34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_me);
        ButterKnife.bind(this);

        ToolBarHelper hToolBarHelper = new ToolBarHelper(this);
        hToolBarHelper.hSetToolbar(hToolbar);
        hToolBarHelper.hSetToolbarTitle(hToolbarTitle, getString(R.string.add_remove_contacts));

        String title = String.format(getString(R.string.send_text), "All");
        UIHelper.hSetTextToTextView(hSendMessage, title);

        hSettingsPrefrences = new SettingsPrefrences(this);

        hSetLayout();
        hFetchContacts();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @OnClick({R.id.hAddContactsFB, R.id.hSendMessage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hAddContactsFB:
                if (hContactsRetrieved) {
                    Collections.sort(hAllContactsList, new Hcomparator1());
                    hSetupRecyclerView(hAllContactsList, H_ALL_LIST);
                    hShowLayout(H_SHOW_RECYCLER_LAYOUT, false);
                }
                break;
            case R.id.hSendMessage:
                hIsAllSendingClicked = true;
                String hName = "All".
                        concat(Constants.H_CHECK_STRING);
                HcustomDialog hcustomDialog = HcustomDialog.newInstance(hName, hIsAllSendingClicked);
                hcustomDialog.show(getSupportFragmentManager(), "H_Dialog");
                break;
        }
    }


    private void hFetchContacts() {
        ContactsAsyncTask hContactsAsyncTask = new ContactsAsyncTask();
        hContactsAsyncTask.execute();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("unused")
    @SuppressLint("StaticFieldLeak")
    public class ContactsAsyncTask extends AsyncTask<Void, Void, List<ContactsModelWithIds>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            hShowLoader();
        }

        @Override
        protected void onPostExecute(List<ContactsModelWithIds> contactsModelWithIds) {
            super.onPostExecute(contactsModelWithIds);
            hAllContactsList = contactsModelWithIds;
            hContactsRetrieved = true;
            hSetLayout();


        }

        @Override
        protected List<ContactsModelWithIds> doInBackground(Void... voids) {
            return hGetAllContacts();
        }

        private List<ContactsModelWithIds> hGetAllContacts() {
            ContactsModelWithIds contactsModelWithIds;
            ContentResolver hContentResolver = getContentResolver();
            List<ContactsModelWithIds> hContactsModelWithIds = new ArrayList<>();
            Cursor cursor = hContentResolver.query(ContactsContract.Contacts.CONTENT_URI, null,
                    null, null,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    int hasPhoneNumber = Integer.parseInt
                            (cursor.getString(cursor.getColumnIndex(
                                    ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                    if (hasPhoneNumber > 0) {
                        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        contactsModelWithIds = new ContactsModelWithIds();
                        contactsModelWithIds.setContactName(name);
                        Cursor phoneCursor = hContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id},
                                null);
                        if (phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(
                                    phoneCursor.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                            contactsModelWithIds.setContactNumber(phoneNumber);
                        }
                        phoneCursor.close();
                        Cursor emailCursor = hContentResolver.query(
                                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (emailCursor.moveToNext()) {
                            String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        }
                        emailCursor.close();
                        hContactsModelWithIds.add(contactsModelWithIds);
                    }
                }
            }
            cursor.close();
            return hContactsModelWithIds;
        }
    }

    private void hSetupRecyclerView(List<ContactsModelWithIds> hList, int i) {
        hWhatLoaded = i;
        if (hContactsAdapter == null) {
            hContactsAdapter = new ContactsAdapter1(this, hList);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback1(hContactsAdapter, TrackMeActivity.this));
            itemTouchHelper.attachToRecyclerView(hTrackMeRV);

            hTrackMeRV.setLayoutManager(new LinearLayoutManager(this));
            hTrackMeRV.addOnItemTouchListener(new RecyclerItemClickListener(this, hTrackMeRV, this));
            hTrackMeRV.setAdapter(hContactsAdapter);

//            hSetSideScroller();

        } else {
            hContactsAdapter.hSetNewList(hList);
            hContactsAdapter.notifyDataSetChanged();
        }
    }

    private void hSetLayout() {
        hSavedContacatsList = hSettingsPrefrences.hGetSavedContacts();

        if (hSavedContacatsList == null || hSavedContacatsList.size() == 0) {
            hShowLayout(H_SHOW_ALERT_LAYOUT, false);
        } else {
            Collections.sort(hSavedContacatsList, new Hcomparator1());
            hShowLayout(H_SHOW_RECYCLER_LAYOUT, false);
            hSetupRecyclerView(hSavedContacatsList, H_T_SAVED_LIST);
        }
    }


    private void hShowLayout(int layout, boolean hIsShown) {
        switch (layout) {
            case H_SHOW_RECYCLER_LAYOUT:
                UIHelper.hMakeVisibleInVisible(hTrackMeRV, Constants.H_VISIBLE);
                UIHelper.hMakeVisibleInVisible(hAlertTextView, Constants.H_INVISIBLE);
                if (hWhatLoaded == H_ALL_LIST) {
                    UIHelper.hMakeVisibleInVisible(hAddContactsFB, Constants.H_INVISIBLE);
                    UIHelper.hMakeVisibleInVisible(hSendMessage, Constants.H_INVISIBLE);
                } else {
                    UIHelper.hMakeVisibleInVisible(hAddContactsFB, Constants.H_VISIBLE);
                    if (hSavedContacatsList != null && hSavedContacatsList.size() == 0) {
                        UIHelper.hMakeVisibleInVisible(hSendMessage, Constants.H_INVISIBLE);

                    } else {
                        UIHelper.hMakeVisibleInVisible(hSendMessage, Constants.H_VISIBLE);
                    }
                }
                break;
            case H_SHOW_ALERT_LAYOUT:
                UIHelper.hMakeVisibleInVisible(hTrackMeRV, Constants.H_INVISIBLE);
                UIHelper.hMakeVisibleInVisible(hSendMessage, Constants.H_INVISIBLE);
                UIHelper.hMakeVisibleInVisible(hAlertTextView, Constants.H_VISIBLE);
                UIHelper.hMakeVisibleInVisible(hAddContactsFB, Constants.H_VISIBLE);
                break;
            case H_SHOW_SAVE_CONTACTS_LAYOUT:
                if (hIsShown) {
//                    UIHelper.hMakeVisibleInVisible(hSaveContactsL, Constants.H_VISIBLE);
                    hIsSaveLayoutVisible = true;
                } else {
//                    UIHelper.hMakeVisibleInVisible(hSaveContactsL, Constants.H_INVISIBLE);
                    hIsSaveLayoutVisible = false;
                }
                break;
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        hIsAllSendingClicked = false;
        switch (hWhatLoaded) {
            case H_T_SAVED_LIST:
                if (hPrimaryActionMode == null) {


                    String hName = hSavedContacatsList.get(position).getContactName().
                            concat(Constants.H_CHECK_STRING).concat(hSavedContacatsList.get(position).getContactNumber());

                    HcustomDialog hcustomDialog = HcustomDialog.newInstance(hName, hIsAllSendingClicked);
                    hcustomDialog.show(getSupportFragmentManager(), "H_Dialog");
                } else {
                    if (isMultiSelect) {
                        multiSelect(position);
                    }
                }
                break;
            default:
                if (isMultiSelect) {
                    multiSelect(position);
                }
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        if (!isMultiSelect) {
            hSelectedIds = new ArrayList<>();
            isMultiSelect = true;
            if (hPrimaryActionMode == null) {
                switch (hWhatLoaded) {
                    case H_T_SAVED_LIST:
                        hPrimaryActionMode = new PrimaryActionMode(this, this,
                                R.menu.menu_select, "Select Contacts", "Contacts Selected", H_T_SAVED_LIST);
                        break;
                    case H_ALL_LIST:
                        hPrimaryActionMode = new PrimaryActionMode(this, this,
                                R.menu.menu_select, "Select Contacts", "Contacts Selected", H_ALL_LIST);
                        break;
                }


                hPrimaryActionMode.hStartActionMode();
            }
        }
        multiSelect(position);

    }

    private void multiSelect(int position) {
        switch (hWhatLoaded) {
            case H_ALL_LIST:
                if (position != -1) {
                    ContactsModelWithIds data = hContactsAdapter.getItem(position);
                    if (data != null) {
                        if (hPrimaryActionMode != null) {
                            if (hSelectedIds.contains(data.gethId()))
                                hSelectedIds.remove(Integer.valueOf(data.gethId()));
                            else
                                hSelectedIds.add(data.gethId());

                            if (hSelectedIds.size() > 0)
                                hPrimaryActionMode.setTitle(String.valueOf(hSelectedIds.size())); //show
                                // selected item count
                                // on action mode.
                            else {
                                hPrimaryActionMode.setTitle(""); //remove item count from action mode.
                                hPrimaryActionMode.finishActionMode(); //hide action mode.
                            }
                            hContactsAdapter.sethSelectedIds(hSelectedIds);
                        }
                    }
                }
                break;
            case H_T_SAVED_LIST:
                if (position != -1) {
                    ContactsModelWithIds data = hContactsAdapter.getItem(position);
                    if (data != null) {
                        if (hPrimaryActionMode != null) {
                            if (hSelectedIds.contains(data.gethId()))
                                hSelectedIds.remove(Integer.valueOf(data.gethId()));
                            else
                                hSelectedIds.add(data.gethId());

                            if (hSelectedIds.size() > 0)
                                hPrimaryActionMode.setTitle(String.valueOf(hSelectedIds.size())); //show
                                // selected item count
                                // on action mode.
                            else {
                                hPrimaryActionMode.setTitle(""); //remove item count from action mode.
                                hPrimaryActionMode.finishActionMode(); //hide action mode.
                            }
                            hContactsAdapter.sethSelectedIds(hSelectedIds);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void hDeleteItem(int position) {
        switch (hWhatLoaded) {
            case H_ALL_LIST:
                hAllContactsList.remove(position);
                break;
            case H_T_SAVED_LIST:
                ContactsModelWithIds hContactsEntity = hSavedContacatsList.get(position);
                hSavedContacatsList.remove(hContactsEntity);

                hSettingsPrefrences.hDeleteSavedContacts();
                hSettingsPrefrences.hSaveContacts(hSavedContacatsList);
                hSavedContacatsList.clear();
                hSavedContacatsList = hSettingsPrefrences.hGetSavedContacts();
                Collections.sort(hSavedContacatsList, new Hcomparator1());

                if (hSavedContacatsList.size() == 0) {
                    hShowLayout(H_SHOW_ALERT_LAYOUT, false);
                } else {
                    hShowLayout(H_SHOW_RECYCLER_LAYOUT, false);
                    hSetupRecyclerView(hSavedContacatsList, H_T_SAVED_LIST);
                }

                break;
        }
        hContactsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        int hMenuId = menuItem.getItemId();
        switch (hMenuId) {
            case R.id.action_delete:
                hExecuteDeleteAction();
                break;
            case R.id.action_add:
                hExecuteAddAllAction();
                break;
            case R.id.action_select_all:
                hExecuteSelectAllAction();
                break;
            case MENU_ITEM_MESSAGE:
                hExecuteMessageAction();
                break;

        }
    }

    private void hExecuteMessageAction() {
        hIsAllSendingClicked = true;
        hIsSelectedList = true;
        String hName = "Selected Contacats".concat(Constants.H_CHECK_STRING);
        HcustomDialog hcustomDialog = HcustomDialog.newInstance(hName, hIsAllSendingClicked);
        hcustomDialog.show(getSupportFragmentManager(), "H_Dialog");

        hPrimaryActionMode.finishActionMode();

    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        switch (hWhatLoaded) {
            case H_ALL_LIST:
                hPrimaryActionMode = null;
                isMultiSelect = false;
                hSelectedIds = new ArrayList<>();
                hContactsAdapter.sethSelectedIds(new ArrayList<Integer>());
                break;
            case H_T_SAVED_LIST:
                hPrimaryActionMode = null;
                isMultiSelect = false;
                hSelectedIds = new ArrayList<>();
                hContactsAdapter.sethSelectedIds(new ArrayList<Integer>());
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
        if (hIsAllSendingClicked) {
            if (hIsSelectedList) {
                List<ContactsModelWithIds> hListToSendText = new ArrayList<>();
                for (ContactsModelWithIds contactsEntity : hSavedContacatsList) {
                    if (hSelectedIds.contains(contactsEntity.gethId())) {
//                        contactsEntity.setLocationId(Integer.parseInt(hLocationId));
                        hListToSendText.add(contactsEntity);
                    }
                }
                for (ContactsModelWithIds contactsModelWithIds : hListToSendText) {
                    hSendSmsMessager(contactsModelWithIds.getContactNumber(), hUserName);
                }
            } else {
                for (ContactsModelWithIds contactsModelWithIds : hSavedContacatsList) {
                    hSendSmsMessager(contactsModelWithIds.getContactNumber(), hUserName);
                }
            }

        } else {
            String[] fields = hUserName.split(Constants.H_CHECK_STRING);
            String hNumber = fields[0];
            String hMessage = fields[1];
            hSendSmsMessager(hNumber, hMessage);

        }
        hDialogFragment.dismiss();

    }

    @Override
    public void hSubmitCloseResponse(boolean b) {

    }

    private void hExecuteDeleteAction() {
        switch (hWhatLoaded) {
            case H_ALL_LIST:
                break;
            case H_T_SAVED_LIST:
                List<ContactsModelWithIds> hContactsModelList = new ArrayList<>();
                for (ContactsModelWithIds testContactModelWithIds : hSavedContacatsList) {
                    if (hSelectedIds.contains(testContactModelWithIds.gethId())) {
                        hContactsModelList.add(testContactModelWithIds);
                    }
                }
                ListUtils.removeAllSubList(hSavedContacatsList, hContactsModelList);
                hSettingsPrefrences.hDeleteSavedContacts();
                hPrimaryActionMode.finishActionMode();
                break;
        }
    }

    private void hExecuteAddAllAction() {
        switch (hWhatLoaded) {
            case H_ALL_LIST:
                List<ContactsModelWithIds> hContactsModelList = new ArrayList<>();
                for (ContactsModelWithIds contactsEntity : hAllContactsList) {
                    if (hSelectedIds.contains(contactsEntity.gethId())) {
//                        contactsEntity.setLocationId(Integer.parseInt(hLocationId));
                        hContactsModelList.add(contactsEntity);
                    }
                }
                if (hSavedContacatsList == null) {
                    hSavedContacatsList = new ArrayList<>();
                }
                hSavedContacatsList = ListUtils.hMergeLists(hSavedContacatsList, hContactsModelList);
                hSettingsPrefrences.hSaveContacts(hSavedContacatsList);
//                hAppRepository.hAddAllContacts(hSavedContacatsList);
                hWhatLoaded = H_T_SAVED_LIST;
                hSetLayout();
                hPrimaryActionMode.finishActionMode();
                break;
            case H_T_SAVED_LIST:

        }
    }

    public void hSendSmsMessager(String number, String messager) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, messager, null, null);
    }

    private void hExecuteSelectAllAction() {
        if (hIsAllSelected) {
            hContactsAdapter.clearAll();
            hIsAllSelected = false;
        } else {
            hContactsAdapter.hSelectAll();
            hIsAllSelected = true;
        }
        hSelectedIds = hContactsAdapter.hGetSelectedIds();
        hPrimaryActionMode.setTitle(String.valueOf(hSelectedIds.size())); //show


    }

    @Override
    public void onBackPressed() {
        if (hIsSaveLayoutVisible) {
            hShowLayout(H_SHOW_RECYCLER_LAYOUT, false);
            hShowLayout(H_SHOW_SAVE_CONTACTS_LAYOUT, false);
            isMultiSelect = false;
            hSelectedIds = new ArrayList<>();
            hContactsAdapter.sethSelectedIds(hSelectedIds);
        } else if (hWhatLoaded == H_ALL_LIST) {
            hWhatLoaded = 34;
            hSetLayout();
        } else {
            super.onBackPressed();
        }
    }
}
