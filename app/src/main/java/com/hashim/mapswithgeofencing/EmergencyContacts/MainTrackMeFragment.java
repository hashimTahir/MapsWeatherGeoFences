package com.hashim.mapswithgeofencing.EmergencyContacts;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hashim.mapswithgeofencing.Adapters.RecyclerAdapter;
import com.hashim.mapswithgeofencing.DataBase.AppRepository;
import com.hashim.mapswithgeofencing.DataBase.ContactsEntity;
import com.hashim.mapswithgeofencing.DataBase.LocationEntitiy;
import com.hashim.mapswithgeofencing.Helper.Constants;
import com.hashim.mapswithgeofencing.Helper.DialogHelper;
import com.hashim.mapswithgeofencing.Helper.LogToastSnackHelper;
import com.hashim.mapswithgeofencing.Interfaces.DeleteCallBack;
import com.hashim.mapswithgeofencing.Interfaces.HDialogResponseInterface;
import com.hashim.mapswithgeofencing.Interfaces.OnFragmentInteractionListener;
import com.hashim.mapswithgeofencing.Interfaces.RecyclerInterface;
import com.hashim.mapswithgeofencing.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainTrackMeFragment extends Fragment implements RecyclerInterface, DeleteCallBack, HDialogResponseInterface {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.hAddLoactionFb)
    FloatingActionButton hAddLoactionFb;

    @BindView(R.id.hContactsRV)
    RecyclerView hContactsRV;

    Unbinder unbinder;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener hOnFragmentInteractionListener;
    RecyclerAdapter hRecyclerAdapter;
    private List<String> hList = new ArrayList<>();
    private String hTag = LogToastSnackHelper.hMakeTag(MainTrackMeFragment.class);
    private AppRepository hAppRepository;
    private List<LocationEntitiy> hLocationEntitiys;
    private LocationEntitiy hLocationEntitiy;

    public MainTrackMeFragment() {

    }

    public static MainTrackMeFragment newInstance(String param1, String param2) {
        MainTrackMeFragment fragment = new MainTrackMeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        hAppRepository = AppRepository.hGetInstance(getContext());
//        hAppRepository.hNukeDataBase();
//        hAppRepository.hAddSampleData();
//        LogToastSnackHelper.hLogField(hTag, "Db sample data inserted");

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_track_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        hInitView();
        return view;
    }

    private void hInitView() {
        hLocationEntitiys = hAppRepository.hGetAllLocationsData();
        List<ContactsEntity> hContactsEntityList = hAppRepository.hGetAllContactsData();

        if (hLocationEntitiys != null) {
            LogToastSnackHelper.hLogField(hTag, String.valueOf(hLocationEntitiys.size()));
            LogToastSnackHelper.hLogField(hTag, String.valueOf(hContactsEntityList.size()));

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            hRecyclerAdapter = new RecyclerAdapter(getContext(), hLocationEntitiys,
                    RecyclerAdapter.H_TRACK_ME_ADAPTER, this);

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback1(hRecyclerAdapter, this));
            itemTouchHelper.attachToRecyclerView(hContactsRV);

            hContactsRV.setLayoutManager(layoutManager);
            hContactsRV.setAdapter(hRecyclerAdapter);
        }
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
    public void onDetach() {
        super.onDetach();
        hOnFragmentInteractionListener = null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.hAddLoactionFb)
    public void onViewClicked() {
        hOnFragmentInteractionListener.onFragmentInteraction(Constants.H_LOCATION_FRAGMENT);
    }

    @Override
    public void hOnClickListener(View hClickedView, int hClickedPosition) {
        hOnFragmentInteractionListener.onFragmentInteraction(hClickedPosition, Constants.H_LOCATION_FRAGMENT);

    }

    @Override
    public void hOnClickListener(View v, int position, String hText) {

    }

    @Override
    public void hDeleteItem(int position) {
        hLocationEntitiy = hLocationEntitiys.get(position);
        DialogHelper hDialogHelper = new DialogHelper(getContext(), this);
        hDialogHelper.hSetDialogType(Constants.H_RATE_US_DIALOG);
        hDialogHelper.hConformationDialogWithTitle("Delete Location",
                "Are you sure to delete this Location? ", "Yes",
                null, "No", true);


    }

    @Override
    public void onNegtiveResponse(int id, int dialogueType) {
        hRecyclerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onPostiveResponse(int id, int dialogueType) {
        hLocationEntitiys.remove(hLocationEntitiy);
        hRecyclerAdapter.notifyDataSetChanged();
        hAppRepository.hDeleteLocationEntity(hLocationEntitiy);
    }

    @Override
    public void onPostiveResponse(int which, int dialogueType, CharSequence charSequence) {

    }

    @Override
    public void onNeutralResponse(int which, int dialogueType) {

    }
}
