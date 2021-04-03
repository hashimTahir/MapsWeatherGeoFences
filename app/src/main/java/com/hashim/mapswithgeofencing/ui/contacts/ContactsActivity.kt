/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.contacts

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.ActivityContactsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsActivity : AppCompatActivity() {
    lateinit var hActivityContactsBinding: ActivityContactsBinding
    private lateinit var hNavHostFragments: NavHostFragment
    private lateinit var hNavController: NavController

    val hContactsSharedViewModel: ContactsSharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hActivityContactsBinding = ActivityContactsBinding.inflate(
                layoutInflater
        )
        setContentView(hActivityContactsBinding.root)

        hInitNavView()

    }

    private fun hInitNavView() {

        setSupportActionBar(hActivityContactsBinding.toolbar)


        hNavHostFragments = supportFragmentManager
                .findFragmentById(R.id.hContactsFragmentContainer)
                as NavHostFragment

        hNavController = hNavHostFragments.navController

        hNavController.setGraph(R.navigation.contacts_nav_graph)


    }

}

/*
*
*
*   /*
}
        implements OnItemClickListener, DeleteCallBack,
        RecyclerInterface, DialogResponseInterface,
        EasyPermissions.PermissionCallbacks, onActionModeListener {


    private static final String hTag = LogToastSnackHelper.hMakeTag(ContactsActivity.class);
    private static final int H_SHOW_SAVE_CONTACTS_LAYOUT = 222;
    private static final int H_SHOW_RECYCLER_LAYOUT = 123;
    private static final int H_SHOW_ALERT_LAYOUT = 456;
    public static final int H_SAVED_LIST = 678;
    public static final int H_ALL_LIST = 910;
    private ContactsAdapter hContactsAdapter;
    private SpotsDialog hAlertDialog;
    private boolean hContactsRetrieved;
    private List<Integer> hSelectedIds = new ArrayList();
    private boolean isMultiSelect = false;
    private boolean hIsSaveLayoutVisible;
    private List<ContactsEntity> hAllContactsList = new ArrayList<>();
    private SettingsPrefrences.kt hSettingsPrefrences;
    private List<ContactsEntity> hSavedContacatsList;
    private int hWhatLoaded;
    private final int H_CONTACTS_PERMISSION_CODE = 222;
    private PrimaryActionMode hPrimaryActionMode;
    private String hLocationId;
    private AppRepository hAppRepository;
    private boolean hIsAllSelected = false;
    //    Todo: For new location check this first
    private boolean hIsNewLocation = false;
    private Bundle savedInstanceState;
    private ActivityContactsBinding hActivityContactsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(this.savedInstanceState);
        hActivityContactsBinding = ActivityContactsBinding.inflate(getLayoutInflater());
        setContentView(hActivityContactsBinding.getRoot());

        ToolBarHelper hToolBarHelper = new ToolBarHelper(this);
        hToolBarHelper.hSetToolbar(hActivityContactsBinding.hToolbar.toolbar);
        hToolBarHelper.hSetToolbarTitle(hActivityContactsBinding.hToolbar.toolbarTitle, getString(R.string.emergency_contacts));

        hSettingsPrefrences = new SettingsPrefrences.kt(this);
        hAppRepository = AppRepository.hGetInstance(this);
        hGetIntentData();

        hSetLayout();

        if (EasyPermissions.hasPermissions(this, Constants.H_CONTACTS_PERMISSION)) {
            hFetchContacts();
        } else {
            hAskForPermissions(Constants.H_CONTACTS_PERMISSION);
        }
    }

    private void hGetIntentData() {
        Bundle hBundle = getIntent().getExtras();
        if (hBundle != null) {
            hIsNewLocation = hBundle.getBoolean(Constants.H_NEW_LOCATION);
            if (!hIsNewLocation) {
                hLocationId = hBundle.getString(Constants.H_LOCATION_ID);
            }

            String hJsonString = hBundle.getString(Constants.H_SAVED_CONTACTS_LIST);
            Type type = new TypeToken<List<ContactsEntity>>() {
            }.getType();
            Gson hGson = new Gson();
            if (hJsonString != null) {
                hSavedContacatsList = hGson.fromJson(hJsonString, type);

            } else {
                hSavedContacatsList = new ArrayList<>();
            }

        }

    }

    private void hFetchContacts() {
        ContactsAsyncTask hContactsAsyncTask = new ContactsAsyncTask();
        hContactsAsyncTask.execute();
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

    private void hSetLayout() {
//        hSavedContacatsList = hSettingsPrefrences.hGetSavedContacts();
        if (hSavedContacatsList == null) {
            hShowLayout(H_SHOW_ALERT_LAYOUT, false);
        } else {
            Collections.sort(hSavedContacatsList, new Hcomparator());
            hShowLayout(H_SHOW_RECYCLER_LAYOUT, false);
            hSetupRecyclerView(hSavedContacatsList, H_SAVED_LIST);
        }
    }

    private void hShowLoader() {
        SpotsDialog.Builder hBuilder = new SpotsDialog.Builder().setContext(ContactsActivity.this).setMessage("Loading...");
        hAlertDialog = (SpotsDialog) hBuilder.build();
        hAlertDialog.show();
    }

    private void hHideLoader() {
        hAlertDialog.dismiss();
    }

    private void hSetupRecyclerView(List<ContactsEntity> hList, int i) {
        hWhatLoaded = i;
        if (hContactsAdapter == null) {
            hContactsAdapter = new ContactsAdapter(this, hList);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(hContactsAdapter, this));
            itemTouchHelper.attachToRecyclerView(hActivityContactsBinding.fastScrollerRecycler);

            hActivityContactsBinding.fastScrollerRecycler.setLayoutManager(new LinearLayoutManager(this));
//            hFRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, hFRecyclerView, this));
            hActivityContactsBinding.fastScrollerRecycler.setAdapter(hContactsAdapter);

        } else {
            hContactsAdapter.hSetNewList(hList);
            hContactsAdapter.notifyDataSetChanged();
        }
    }

    private void hSetSideScroller() {
        hActivityContactsBinding.fastScrollerRecycler.setIndexTextSize(12);
        hActivityContactsBinding.fastScrollerRecycler.setIndexBarColor("#ff212121");
        hActivityContactsBinding.fastScrollerRecycler.setIndexBarCornerRadius(0);
        hActivityContactsBinding.fastScrollerRecycler.setIndexBarTransparentValue((float) 0.4);
        hActivityContactsBinding.fastScrollerRecycler.setIndexbarMargin(0);
        hActivityContactsBinding.fastScrollerRecycler.setIndexbarWidth(50);
        hActivityContactsBinding.fastScrollerRecycler.setPreviewPadding(0);
        hActivityContactsBinding.fastScrollerRecycler.setIndexBarTextColor("#FFFFFF");
        hActivityContactsBinding.fastScrollerRecycler.setIndexBarVisibility(true);
//        hFRecyclerView.setIndexbarHighLateTextColor("#33334c");
//        hFRecyclerView.setIndexBarHighLateTextVisibility(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void hShowLayout(int layout, boolean hIsShown) {
        switch (layout) {
            case H_SHOW_RECYCLER_LAYOUT:
                UIHelper.hMakeVisibleInVisible(hActivityContactsBinding.fastScrollerRecycler, Constants.H_VISIBLE);
                UIHelper.hMakeVisibleInVisible(hActivityContactsBinding.hAlertTextView, Constants.H_INVISIBLE);
                if (hWhatLoaded == H_ALL_LIST) {
                    UIHelper.hMakeVisibleInVisible(hActivityContactsBinding.hAddContactsFB, Constants.H_INVISIBLE);
                } else {
                    UIHelper.hMakeVisibleInVisible(hActivityContactsBinding.hAddContactsFB, Constants.H_VISIBLE);
                }
                break;
            case H_SHOW_ALERT_LAYOUT:
                UIHelper.hMakeVisibleInVisible(hActivityContactsBinding.fastScrollerRecycler, Constants.H_INVISIBLE);
                UIHelper.hMakeVisibleInVisible(hActivityContactsBinding.hAlertTextView, Constants.H_VISIBLE);
                UIHelper.hMakeVisibleInVisible(hActivityContactsBinding.hAddContactsFB, Constants.H_VISIBLE);
                break;
            case H_SHOW_SAVE_CONTACTS_LAYOUT:
                if (hIsShown) {
                    UIHelper.hMakeVisibleInVisible(hActivityContactsBinding.hSaveContactsL, Constants.H_VISIBLE);
                    hIsSaveLayoutVisible = true;
                } else {
                    UIHelper.hMakeVisibleInVisible(hActivityContactsBinding.hSaveContactsL, Constants.H_INVISIBLE);
                    hIsSaveLayoutVisible = false;
                }
                break;
        }
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
            Gson hGson = new Gson();
            Type type = new TypeToken<List<ContactsEntity>>() {
            }.getType();
            String hJsonString = hGson.toJson(hSavedContacatsList, type);
            LogToastSnackHelper.hLogField(hTag, hJsonString);

            Intent hBackToFragmentIntent = new Intent();
            hBackToFragmentIntent.putExtra(Constants.H_SAVED_CONTACTS_LIST, hJsonString);
            setResult(RESULT_OK, hBackToFragmentIntent);
            finish();


            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (hWhatLoaded) {

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
                    case H_SAVED_LIST:
                        hPrimaryActionMode = new PrimaryActionMode(this, this,
                                R.menu.menu_select, "Select Contacts", "Contacts Selected", H_SAVED_LIST);
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
                    ContactsEntity data = hContactsAdapter.getItem(position);
                    if (data != null) {
                        if (hPrimaryActionMode != null) {
                            if (hSelectedIds.contains(data.getContactsId()))
                                hSelectedIds.remove(Integer.valueOf(data.getContactsId()));
                            else
                                hSelectedIds.add(data.getContactsId());

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
            case H_SAVED_LIST:
                if (position != -1) {
                    ContactsEntity data = hContactsAdapter.getItem(position);
                    if (data != null) {
                        if (hPrimaryActionMode != null) {
                            if (hSelectedIds.contains(data.getContactsId()))
                                hSelectedIds.remove(Integer.valueOf(data.getContactsId()));
                            else
                                hSelectedIds.add(data.getContactsId());

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


//        if (position != -1) {
//            ContactsEntity data = hContactsAdapter.getItem(position);
//            if (data != null) {
//                if (hSelectedIds.contains(data.getContactsId())) {
//                    hSelectedIds.remove(Integer.valueOf(data.getContactsId()));
//                } else {
//                    hSelectedIds.add(data.getContactsId());
//                }
//                if (hSelectedIds.size() > 0) {
//                } else {
//                }
//                hContactsAdapter.sethSelectedIds(hSelectedIds);
//            }
//            hShowLayout(H_SHOW_SAVE_CONTACTS_LAYOUT, true);
//            UIHelper.hSetTextToTextView(hSelectedContactsT, hSelectedIds.size() + getString(R.string.contacts_selected));

//    }

    public void hSetupListeners() {
        hActivityContactsBinding.hAddContactsFB.setOnClickListener(v -> {
            if (hContactsRetrieved) {
                Collections.sort(hAllContactsList, new Hcomparator());
                hSetupRecyclerView(hAllContactsList, H_ALL_LIST);
                hShowLayout(H_SHOW_RECYCLER_LAYOUT, false);
            }
        });
        hActivityContactsBinding.hSaveContactsB.setOnClickListener(v -> {
            List<ContactsEntity> hContactsToSaveList = new ArrayList<>();
            for (ContactsEntity contactsModelWithIds : hAllContactsList) {
                if (hSelectedIds.contains(contactsModelWithIds.getContactsId())) {
                    hContactsToSaveList.add(contactsModelWithIds);
                }
            }
            LogToastSnackHelper.hMakeShortToast(this, getString(R.string.selected_contacts_saved));
            hShowLayout(H_SHOW_SAVE_CONTACTS_LAYOUT, false);

//                TODO:save contacts here
//                hSettingsPrefrences.hSaveContacts(hContactsToSaveList);
            if (hSavedContacatsList == null) {
                hSavedContacatsList = new ArrayList<>();
            }
            hSavedContacatsList.clear();
            isMultiSelect = false;
            hSelectedIds = new ArrayList<>();
            hContactsAdapter.sethSelectedIds(hSelectedIds);
            hWhatLoaded = 34;
            hSetLayout();
        });
    }

    @Override
    public void hDeleteItem(int position) {
        switch (hWhatLoaded) {
            case H_ALL_LIST:
                hAllContactsList.remove(position);
                break;
            case H_SAVED_LIST:
                ContactsEntity hContactsEntity = hSavedContacatsList.get(position);
                hSavedContacatsList.remove(hContactsEntity);
                if (hIsNewLocation) {
                } else {
                    hAppRepository.hDeleteContact(hContactsEntity);
                }
//                hSettingsPrefrences.hDeleteSavedContacts();
//                hSettingsPrefrences.hSaveContacts(hSavedContacatsList);
//                hSavedContacatsList.clear();
//                hSavedContacatsList = hSettingsPrefrences.hGetSavedContacts();
                Collections.sort(hSavedContacatsList, new Hcomparator());
                hSetupRecyclerView(hSavedContacatsList, H_SAVED_LIST);
                break;
        }
        hContactsAdapter.notifyDataSetChanged();
    }

    @Override
    public void hOnClickListener(View hClickedView, int hClickedPosition) {

    }

    @Override
    public void hOnClickListener(View v, int position, String hText) {

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
        String[] fields = hUserName.split(Constants.H_CHECK_STRING);
        String name = fields[0];
        String number = fields[1];
        LogToastSnackHelper.hLogField(hTag, name);
        LogToastSnackHelper.hLogField(hTag, number);
        LogToastSnackHelper.hMakeShortToast(this, "text sent to ".concat(name));
        hDialogFragment.dismiss();
    }

    @Override
    public void hSubmitCloseResponse(boolean b) {

    }

    @Override
    public void onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_delete:
                hExecuteDeleteAction();

                break;
            case R.id.action_add:
                hExecuteAddAllAction();

                break;
            case R.id.action_select_all:
                hExecuteSelectAllAction();
                break;
        }
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

    private void hExecuteAddAllAction() {
        switch (hWhatLoaded) {
            case H_ALL_LIST:
                StringBuilder stringBuilder = new StringBuilder();
                List<ContactsEntity> hContactsModelList = new ArrayList<>();
                for (ContactsEntity contactsEntity : hAllContactsList) {
                    if (hSelectedIds.contains(contactsEntity.getContactsId())) {
                        stringBuilder.append("\n").append(contactsEntity.getName());
                        if (hIsNewLocation) {
                        } else {
                            contactsEntity.setLocationId(Integer.parseInt(hLocationId));
                        }
                        hContactsModelList.add(contactsEntity);
                    }
                }
                hSavedContacatsList = ListUtils.hMergeLists(hSavedContacatsList, hContactsModelList);
                if (hIsNewLocation) {
                } else {
                    hAppRepository.hAddAllContacts(hSavedContacatsList);
                }
                hContactsAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Selected items are :" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                hPrimaryActionMode.finishActionMode();
                break;
            case H_SAVED_LIST:

        }
    }

    private void hExecuteDeleteAction() {
        switch (hWhatLoaded) {
            case H_ALL_LIST:
                break;
            case H_SAVED_LIST:
                StringBuilder stringBuilder = new StringBuilder();
                List<ContactsEntity> hContactsModelList = new ArrayList<>();
                for (ContactsEntity testContactModelWithIds : hSavedContacatsList) {
                    if (hSelectedIds.contains(testContactModelWithIds.getContactsId())) {
                        stringBuilder.append("\n").append(testContactModelWithIds.getName());
                        hContactsModelList.add(testContactModelWithIds);
                    }
                }
                ListUtils.removeAllSubList(hSavedContacatsList, hContactsModelList);
                if (hIsNewLocation) {
                } else {
                    hAppRepository.hDeleteContactList(hContactsModelList);
                }
                Toast.makeText(this, "Selected items are :" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                hPrimaryActionMode.finishActionMode();
                break;
        }
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
            case H_SAVED_LIST:
                hPrimaryActionMode = null;
                isMultiSelect = false;
                hSelectedIds = new ArrayList<>();
                hContactsAdapter.sethSelectedIds(new ArrayList<Integer>());
                break;
        }
    }


    @SuppressWarnings("unused")
    public class ContactsAsyncTask extends AsyncTask<Void, Void, List<ContactsEntity>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hShowLoader();
        }

        @Override
        protected void onPostExecute(List<ContactsEntity> contactsModelWithIds) {
            super.onPostExecute(contactsModelWithIds);
            hAllContactsList = contactsModelWithIds;
            hSetLayout();
            hHideLoader();
            hContactsRetrieved = true;
        }

        @Override
        protected List<ContactsEntity> doInBackground(Void... voids) {
            return hGetAllContacts();
        }

        private List<ContactsEntity> hGetAllContacts() {
            ContactsEntity contactsModelWithIds;
            ContentResolver hContentResolver = getContentResolver();
            List<ContactsEntity> hContactsModelWithIds = new ArrayList<>();
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
                        contactsModelWithIds = new ContactsEntity();
                        contactsModelWithIds.setName(name);
                        Cursor phoneCursor = hContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id},
                                null);
                        if (phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(
                                    phoneCursor.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                            contactsModelWithIds.setNumber(phoneNumber);
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

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == H_CONTACTS_PERMISSION_CODE) {
            hFetchContacts();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (requestCode == H_CONTACTS_PERMISSION_CODE) {
            hAskForPermissions(Constants.H_LOCATION_PERMISSION);
        }
    }*/*/