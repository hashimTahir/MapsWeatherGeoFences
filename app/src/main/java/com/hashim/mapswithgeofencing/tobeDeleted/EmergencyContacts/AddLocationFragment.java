/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tobeDeleted.EmergencyContacts;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hashim.mapswithgeofencing.tobeDeleted.Contacts.ContactsActivity;
import com.hashim.mapswithgeofencing.tobeDeleted.DataBase.AppRepository;
import com.hashim.mapswithgeofencing.tobeDeleted.DataBase.ContactsEntity;
import com.hashim.mapswithgeofencing.tobeDeleted.DataBase.LocationEntitiy;
import com.hashim.mapswithgeofencing.tobeDeleted.Helper.GeoFenceUtil;
import com.hashim.mapswithgeofencing.tobeDeleted.Helper.ListUtils;
import com.hashim.mapswithgeofencing.tobeDeleted.Helper.PrimaryActionMode;
import com.hashim.mapswithgeofencing.tobeDeleted.Interfaces.OnFragmentInteractionListener;
import com.hashim.mapswithgeofencing.Models.HLatLngModel;
import com.hashim.mapswithgeofencing.R;
import com.hashim.mapswithgeofencing.databinding.FragmentAddLocationBinding;
import com.hashim.mapswithgeofencing.SettingsPrefrences;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_OK;


public class AddLocationFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, TextWatcher {
    //        DeleteCallBack, OnItemClickListener, onActionModeListener
    private static final String H_LOCATION_ID = "H_LOCATION_ID";


    private String hLocationId;
    private OnFragmentInteractionListener hOnFragmentInteractionListener;
    private GoogleMap hGoogleMap;
    private AppRepository hAppRepository;
    private String hTag = LogToastSnackHelper.hMakeTag(AddLocationFragment.class);
    private LocationEntitiy hLocationEntitiy;
    private List<ContactsEntity> hSavedContactsList;
    private LatLng hLatLng;

    private int hWhatLoaded;
    private static final int H_SAVED_LIST = 678;
    private static final int H_ALL_LIST = 910;
    private List<ContactsEntity> hAllContactsList;
    private boolean hContactsRetrieved;
    private PrimaryActionMode hPrimaryActionMode;
    private boolean isMultiSelect = false;

    private List<Integer> hSelectedIds = new ArrayList();
    private boolean hIsAllSelected = false;
    private boolean hIsAddNewLocation = false;
    private Type hGsonTypeToken = hGsonTypeToken = new TypeToken<List<ContactsEntity>>() {
    }.getType();
    private String hLocationName;
    private LatLng hGeoLatLng;
    private FragmentAddLocationBinding hFragmentAddLocationBinding;

    public AddLocationFragment() {

    }

    public static AddLocationFragment newInstance(String param1) {
        AddLocationFragment fragment = new AddLocationFragment();
        Bundle args = new Bundle();
        args.putString(H_LOCATION_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    private void hFetchContacts() {
        ContactsAsyncTask hContactsAsyncTask = new ContactsAsyncTask();
        hContactsAsyncTask.execute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            hLocationId = getArguments().getString(H_LOCATION_ID);
        }
        hFetchContacts();

        hInitData();

    }

    private void hInitData() {
        hAppRepository = AppRepository.hGetInstance(getContext());
        SettingsPrefrences hSettingsPrefrences = new SettingsPrefrences(getContext());
        HLatLngModel hLatLngModel = hSettingsPrefrences.hGetCurrentLocation();
        if (hLatLngModel != null) {
            hLatLng = new LatLng(Double.parseDouble(hLatLngModel.getLatitude()),
                    Double.parseDouble(hLatLngModel.getLongitude()));
        }


        if (Integer.parseInt(hLocationId) == Constants.H_ADD_NEW_LOCATION) {
            hIsAddNewLocation = true;
        } else {
            hIsAddNewLocation = false;
            hLocationEntitiy = hAppRepository.hQueryLocationDataById(hLocationId);
            hSavedContactsList = hAppRepository.hQueryContactsDataById(hLocationId);
            hGeoLatLng = new LatLng(hLocationEntitiy.getLatitude(), hLocationEntitiy.getLongitude());

        }


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_location, container, false);
        hFragmentAddLocationBinding = FragmentAddLocationBinding.inflate(
                inflater, container, false
        );
        setHasOptionsMenu(true);
        hInitView();
        return view;
    }

    private void hInitView() {
        hWhatLoaded = H_SAVED_LIST;
//        hSetupRecyclerView(hSavedContactsList, H_T_SAVED_LIST);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        hFragmentAddLocationBinding.hViewTv.setPaintFlags(hFragmentAddLocationBinding.hViewTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        UIHelper.hSetTextToTextView(hFragmentAddLocationBinding.hViewTv, getString(R.string.view));

        hFragmentAddLocationBinding.hEditTv.addTextChangedListener(this);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            hOnFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.H_REQUEST_CODE && resultCode == RESULT_OK) {
            String hJsonString = data.getExtras().getString(Constants.H_SAVED_CONTACTS_LIST);
            Gson hGson = new Gson();
            hSavedContactsList = hGson.fromJson(hJsonString, hGsonTypeToken);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hOnFragmentInteractionListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        hGoogleMap = googleMap;
        hGoogleMap.setMyLocationEnabled(true);
        hGoogleMap.setOnMapClickListener(this);
        if (hLatLng != null) {
            hGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                    (hLatLng, 10f));

        }
        if (hGeoLatLng != null) {
            hCreateMarker(hGeoLatLng);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragmenta_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onMapClick(LatLng latLng) {

        hCreateMarker(latLng);


    }

    private void hCreateMarker(LatLng latLng) {
        MarkerOptions hMarkerOptions = new MarkerOptions().position(latLng).rotation((float) -15.0)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

        hGoogleMap.clear();

        hGoogleMap.addMarker(hMarkerOptions);

        GeoFenceUtil hGeoFenceUtil = new GeoFenceUtil(getContext());

        hGoogleMap.addCircle(hGeoFenceUtil.hShowVisibleGeoFence(latLng.latitude, latLng.longitude));
        hGeoLatLng = latLng;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hLocationEntitiy == null) {
            hFragmentAddLocationBinding.hRadiusSeekBar.setProgress(0);
            UIHelper.hSetTextToTextView(hFragmentAddLocationBinding.hTitleTv, getString(R.string.add_new_location));

            hLocationName = UIHelper.hGetTextFromTextView(hFragmentAddLocationBinding.hEditTv);
            if (!hLocationName.isEmpty()) {
                if (hSavedContactsList != null) {
                    UIHelper.hSetTextToTextView(hFragmentAddLocationBinding.hContactsTv,
                            String.valueOf(hSavedContactsList.size()).concat(" contacts associated with ").concat(hLocationName));
                } else {
                    UIHelper.hSetTextToTextView(hFragmentAddLocationBinding.hContactsTv, "No contacts associated with ".concat(hLocationName));
                }
            } else {
                if (hSavedContactsList != null) {
                    UIHelper.hSetTextToTextView(hFragmentAddLocationBinding.hContactsTv,
                            String.valueOf(hSavedContactsList.size()).concat(" contacts associated with this location"));
                } else {
                    UIHelper.hSetTextToTextView(hFragmentAddLocationBinding.hContactsTv, "No contacts associated with this location");

                }

            }

        } else {
            hFragmentAddLocationBinding.hRadiusSeekBar.setProgress(hLocationEntitiy.getRadius());
            UIHelper.hSetTextToTextView(hFragmentAddLocationBinding.hEditTv, hLocationEntitiy.getLocationName());
            UIHelper.hSetTextToTextView(hFragmentAddLocationBinding.hTitleTv, hLocationEntitiy.getLocationName());
            if (hSavedContactsList != null) {
                UIHelper.hSetTextToTextView(hFragmentAddLocationBinding.hContactsTv, String.valueOf(hSavedContactsList.size()).concat(" contacts associated with ").concat(hLocationEntitiy.getLocationName()));
            } else {
                UIHelper.hSetTextToTextView(hFragmentAddLocationBinding.hContactsTv, "no contacts associated with ".concat(hLocationEntitiy.getLocationName()));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addRemoveContacts) {
            hAddRemoveContacts();
        }
        return super.onOptionsItemSelected(item);
    }

    private void hAddRemoveContacts() {
        Intent hIntent = new Intent(getContext(), ContactsActivity.class);
        if (hSavedContactsList != null) {
            Gson hGson = new Gson();
            String hJson = hGson.toJson(hSavedContactsList);
            hIntent.putExtra(Constants.H_SAVED_CONTACTS_LIST, hJson);
        }
        if (!hIsAddNewLocation) {

            hIntent.putExtra(Constants.H_LOCATION_ID, hLocationId);
            hIntent.putExtra(Constants.H_NEW_LOCATION, hIsAddNewLocation);
            startActivityForResult(hIntent, Constants.H_REQUEST_CODE);
        } else {
            hIntent.putExtra(Constants.H_NEW_LOCATION, hIsAddNewLocation);
            startActivityForResult(hIntent, Constants.H_REQUEST_CODE);
        }
    }

//
//    @OnClick({R.id.hViewTv, R.id.hSaveContactsB})
//    public void hSetupListeners(View view) {
//        switch (view.getId()) {
//
//            case R.id.hViewTv:
//                hAddRemoveContacts();
//                break;
//            case R.id.hSaveContactsB:
//                hCheckIds(hIsAddNewLocation);
//
//
//                break;
//        }
//    }

    private void hCheckIds(boolean bool) {
        StringBuilder hStringBuilder = new StringBuilder();
        for (ContactsEntity contactsEntity : hSavedContactsList) {
            hStringBuilder.append("\ncontacts id: ").
                    append(contactsEntity.getContactsId()).
                    append("  contact Location id: ").
                    append(contactsEntity.getLocationId()).append("\n\n");

        }
        LogToastSnackHelper.hLogField(hTag, hStringBuilder.toString());

        if (bool) {


            List<Integer> hLocationIds = hAppRepository.hGetAllLocationIds();
            int hNewLocationId = hGenerateNewId(hLocationIds);
            hLocationEntitiy = new LocationEntitiy(hNewLocationId, hGeoLatLng.latitude, hGeoLatLng.latitude,
                    hLocationName, hFragmentAddLocationBinding.hRadiusSeekBar.getProgress());
            hAppRepository.hAddLocation(hLocationEntitiy);

            for (ContactsEntity contactsEntity : hSavedContactsList) {
                contactsEntity.setLocationId(hNewLocationId);
            }
            hAppRepository.hAddAllContacts(hSavedContactsList);
            LogToastSnackHelper.hLogField(hTag, String.valueOf(hNewLocationId));

        } else {
            LogToastSnackHelper.hLogField(hTag, String.valueOf("Entitiy Id  " + hLocationEntitiy.getLid()));
            hLocationEntitiy.setLocationName(hLocationName);
            hLocationEntitiy.setRadius(hFragmentAddLocationBinding.hRadiusSeekBar.getProgress());
            hLocationEntitiy.setLatitude(hGeoLatLng.latitude);
            hLocationEntitiy.setLongitude(hGeoLatLng.latitude);
            hAppRepository.hAddLocation(hLocationEntitiy);
            hAppRepository.hAddAllContacts(hSavedContactsList);
            LogToastSnackHelper.hLogField(hTag, "Location Updated  ");

        }


        List<Integer> hContactsIds = hAppRepository.hGetAllContactsIds();


        LogToastSnackHelper.hLogField(hTag, String.valueOf("Simple Id  " + hLocationId));
        LogToastSnackHelper.hLogField(hTag, String.valueOf("contacts id  " + hContactsIds.toString()));
//        LogToastSnackHelper.hLogField(hTag, String.valueOf("location Id  " + hLocationIds.toString()));
    }

    private int hGenerateNewId(List<Integer> hLocationIds) {
        int hFinalId = 0;
        Random rand = new Random();
        int hNewid = rand.nextInt(1000) + 1;
        while (hLocationIds.contains(hNewid)) {
            hGenerateNewId(hLocationIds);
        }
        hFinalId = hNewid;
        return hFinalId;
    }


//    @Override
//    public void hDeleteItem(int position) {
//        switch (hWhatLoaded) {
//            case H_T_SAVED_LIST:
//                ContactsEntity hContactsEntity = hSavedContactsList.get(position);
//                hSavedContactsList.remove(hContactsEntity);
//                hAppRepository.hDeleteContact(hContactsEntity);
//                hSetupRecyclerView(hSavedContactsList, H_T_SAVED_LIST);
//                hContactsAdapter1.notifyDataSetChanged();
//                break;
//            case H_ALL_LIST:
//                hAllContactsList.remove(position);
//                hContactsAdapter2.notifyDataSetChanged();
//                break;
//        }
//    }

//    @Override
//    public void onItemClick(View view, int position) {
//        if (isMultiSelect) {
//            //if multiple selection is enabled then select item on single click else perform normal click on item.
//            multiSelect(position);
//        }
//    }
//
//    @Override
//    public void onItemLongClick(View view, int position) {
//        if (!isMultiSelect) {
//            hSelectedIds = new ArrayList<>();
//            isMultiSelect = true;
//            if (hPrimaryActionMode == null) {
//                hPrimaryActionMode = new PrimaryActionMode(getContext(), this,
//                        R.menu.menu_select, "Select Contacts", "Select Contacts");
//                hPrimaryActionMode.hStartActionMode();
//            }
//        }
//        multiSelect(position);
//    }

//    private void multiSelect(int position) {
//        switch (hWhatLoaded) {
//            case H_ALL_LIST:
//                if (position != -1) {
//                    ContactsEntity data = hContactsAdapter2.getItem(position);
//                    if (data != null) {
//                        if (hPrimaryActionMode != null) {
//                            if (hSelectedIds.contains(data.getContactsId()))
//                                hSelectedIds.remove(Integer.valueOf(data.getContactsId()));
//                            else
//                                hSelectedIds.add(data.getContactsId());
//
//                            if (hSelectedIds.size() > 0)
//                                hPrimaryActionMode.setTitle(String.valueOf(hSelectedIds.size())); //show
//                                // selected item count
//                                // on action mode.
//                            else {
//                                hPrimaryActionMode.setTitle(""); //remove item count from action mode.
//                                hPrimaryActionMode.finishActionMode(); //hide action mode.
//                            }
//                            hContactsAdapter2.sethSelectedIds(hSelectedIds);
//                        }
//                    }
//                }
//                break;
//            case H_T_SAVED_LIST:
//                if (position != -1) {
//                    ContactsEntity data = hContactsAdapter1.getItem(position);
//                    if (data != null) {
//                        if (hPrimaryActionMode != null) {
//                            if (hSelectedIds.contains(data.getContactsId()))
//                                hSelectedIds.remove(Integer.valueOf(data.getContactsId()));
//                            else
//                                hSelectedIds.add(data.getContactsId());
//
//                            if (hSelectedIds.size() > 0)
//                                hPrimaryActionMode.setTitle(String.valueOf(hSelectedIds.size())); //show
//                                // selected item count
//                                // on action mode.
//                            else {
//                                hPrimaryActionMode.setTitle(""); //remove item count from action mode.
//                                hPrimaryActionMode.finishActionMode(); //hide action mode.
//                            }
//                            hContactsAdapter1.sethSelectedIds(hSelectedIds);
//                        }
//                    }
//                }
//                break;
//        }
//    }
//
//    private void hSetupRecyclerView(List<ContactsEntity> hList, int i) {
//        hWhatLoaded = i;
//        switch (i) {
//            case H_T_SAVED_LIST:
//                if (hContactsAdapter1 == null) {
//                    hContactsAdapter1 = new ContactsAdapter1(getContext(), hSavedContactsList, this);
//                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback1(hContactsAdapter1, this));
//                    itemTouchHelper.attachToRecyclerView(hContactsRv);
//
//                    hContactsRv.setLayoutManager(new LinearLayoutManager(getContext()));
//                    hContactsRv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), hContactsRv, this));
//                    hContactsRv.setAdapter(hContactsAdapter1);
////                    hSetSideScroller();
//                } else {
//                    hContactsAdapter1.hSetNewList(hList);
//                    hContactsAdapter1.notifyDataSetChanged();
//                }
//                break;
//            case H_ALL_LIST:
//                if (hContactsAdapter2 == null) {
//                    hContactsAdapter2 = new ContactsAdapter2(getContext(), hAllContactsList, this);
//                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback2(hContactsAdapter2, this));
//                    itemTouchHelper.attachToRecyclerView(hAddRemoveContactsRV);
//
//                    hAddRemoveContactsRV.setLayoutManager(new LinearLayoutManager(getContext()));
//                    hAddRemoveContactsRV.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), hAddRemoveContactsRV, this));
//                    hAddRemoveContactsRV.setAdapter(hContactsAdapter2);
////                    hSetSideScroller();
//                } else {
//                    hContactsAdapter2.hSetNewList(hList);
//                    hContactsAdapter2.notifyDataSetChanged();
//                }
//                break;
//        }
//    }

//    @Override
//    public void onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//            case R.id.action_delete:
//                hExecuteDeleteAction();
//                UIHelper.hMakeVisibleInVisible(hAddLocationLayout, Constants.H_VISIBLE);
//                UIHelper.hMakeVisibleInVisible(hAddRemoveContactsRV, Constants.H_INVISIBLE);
//                break;
//            case R.id.action_add:
//                hExecuteAddAllAction();
//                UIHelper.hMakeVisibleInVisible(hAddLocationLayout, Constants.H_VISIBLE);
//                UIHelper.hMakeVisibleInVisible(hAddRemoveContactsRV, Constants.H_INVISIBLE);
//                break;
//            case R.id.action_select_all:
//                hExecuteSelectAllAction();
//                break;
//        }
//    }
//
//    private void hExecuteSelectAllAction() {
//        switch (hWhatLoaded) {
//            case H_ALL_LIST:
//                if (hIsAllSelected) {
//                    hContactsAdapter2.clearAll();
//                    hIsAllSelected = false;
//                } else {
//                    hContactsAdapter2.hSelectAll();
//                    hIsAllSelected = true;
//                }
//                break;
//            case H_T_SAVED_LIST:
//                break;
//        }
//    }
//
//    private void hExecuteAddAllAction() {
//        switch (hWhatLoaded) {
//            case H_ALL_LIST:
//                StringBuilder stringBuilder = new StringBuilder();
//                List<ContactsEntity> hContactsModelList = new ArrayList<>();
//                for (ContactsEntity contactsEntity : hAllContactsList) {
//                    if (hSelectedIds.contains(contactsEntity.getContactsId())) {
//                        stringBuilder.append("\n").append(contactsEntity.getName());
//                        contactsEntity.setLocationId(Integer.parseInt(hLocationId));
//                        hContactsModelList.add(contactsEntity);
//                    }
//                }
//                hSavedContactsList = ListUtils.hMergeLists(hSavedContactsList, hContactsModelList);
//                hAppRepository.hAddAllContacts(hSavedContactsList);
//                hContactsAdapter1.notifyDataSetChanged();
//                Toast.makeText(getContext(), "Selected items are :" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
//                hPrimaryActionMode.finishActionMode();
//                break;
//            case H_T_SAVED_LIST:
//                StringBuilder stringBuilder1 = new StringBuilder();
//                List<ContactsEntity> hContactsModelList1 = new ArrayList<>();
//                for (ContactsEntity contactsEntity : hSavedContactsList) {
//                    if (hSelectedIds.contains(contactsEntity.getContactsId())) {
//                        stringBuilder1.append("\n").append(contactsEntity.getName());
//                        contactsEntity.setLocationId(Integer.parseInt(hLocationId));
//                        hContactsModelList1.add(contactsEntity);
//                    }
//                }
//                ListUtils.hMergeLists(hSavedContactsList, hContactsModelList1);
//                hAppRepository.hAddAllContacts(hContactsModelList1);
//                hContactsAdapter1.notifyDataSetChanged();
//                Toast.makeText(getContext(), "Selected items are :" + stringBuilder1.toString(), Toast.LENGTH_SHORT).show();
//                hPrimaryActionMode.finishActionMode();
//                break;
//        }
//    }

    private void hExecuteDeleteAction() {
        switch (hWhatLoaded) {
            case H_ALL_LIST:
                break;
            case H_SAVED_LIST:
                StringBuilder stringBuilder = new StringBuilder();
                List<ContactsEntity> hContactsModelList = new ArrayList<>();
                for (ContactsEntity testContactModelWithIds : hSavedContactsList) {
                    if (hSelectedIds.contains(testContactModelWithIds.getContactsId())) {
                        stringBuilder.append("\n").append(testContactModelWithIds.getName());
                        hContactsModelList.add(testContactModelWithIds);
                    }
                }
                ListUtils.removeAllSubList(hSavedContactsList, hContactsModelList);
                hAppRepository.hDeleteContactList(hContactsModelList);
                Toast.makeText(getContext(), "Selected items are :" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                hPrimaryActionMode.finishActionMode();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        hLocationName = s.toString();

    }


//    @Override
//    public void onDestroyActionMode(ActionMode actionMode) {
//        switch (hWhatLoaded) {
//            case H_ALL_LIST:
//                hPrimaryActionMode = null;
//                isMultiSelect = false;
//                hSelectedIds = new ArrayList<>();
//                hContactsAdapter2.sethSelectedIds(new ArrayList<Integer>());
//                break;
//            case H_T_SAVED_LIST:
//                hPrimaryActionMode = null;
//                isMultiSelect = false;
//                hSelectedIds = new ArrayList<>();
//                hContactsAdapter1.sethSelectedIds(new ArrayList<Integer>());
//                break;
//        }
//    }


    @SuppressLint("StaticFieldLeak")
    public class ContactsAsyncTask extends AsyncTask<Void, Void, List<ContactsEntity>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<ContactsEntity> contactsModelWithIds) {
            super.onPostExecute(contactsModelWithIds);
            hAllContactsList = contactsModelWithIds;
            hContactsRetrieved = true;
        }

        @Override
        protected List<ContactsEntity> doInBackground(Void... voids) {
            return hGetAllContacts();
        }

        private List<ContactsEntity> hGetAllContacts() {
            ContactsEntity contactsModelWithIds;
            ContentResolver hContentResolver = getContext().getContentResolver();
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
}
