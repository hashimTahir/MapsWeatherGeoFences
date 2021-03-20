/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.TrackMe;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.SectionIndexer;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hashim.mapswithgeofencing.Contacts.ContactsModelWithIds;
import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.Interfaces.OnItemClickListener;
import com.hashim.mapswithgeofencing.Interfaces.RecyclerInterface;
import com.hashim.mapswithgeofencing.R;
import com.hashim.mapswithgeofencing.ViewHolders.ContactsVH;
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerContactsBinding;

import java.util.ArrayList;
import java.util.List;


public class ContactsAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SectionIndexer {

    private Context context;
    private List<ContactsModelWithIds> hNewList;
    private List<Integer> selectedIds = new ArrayList<>();
    private int mRecentlyDeletedItemPosition;
    private ContactsModelWithIds mRecentlyDeletedItem;
    OnItemClickListener hOnItemClickListener;
    private ArrayList<Integer> mSectionPositions;
    RecyclerInterface hRecyclerInterface;

    public ContactsAdapter1(Context context, List<ContactsModelWithIds> hNewList) {
        this.context = context;
        this.hNewList = hNewList;
        this.hOnItemClickListener = (OnItemClickListener) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ContactsVH(
                ItemRecyclerContactsBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContactsVH hContactsVH = (ContactsVH) holder;
        hBindTodaysVh(hContactsVH, position);
    }

    private void hBindTodaysVh(ContactsVH hContactsVH, int position) {
        hContactsVH.hItemRecyclerContactsBinding.rootView.setOnLongClickListener(v -> {
            hOnItemClickListener.onItemLongClick(v, position);
            return false;
        });
        hContactsVH.hItemRecyclerContactsBinding.title.setText(hNewList.get(position).getContactName());
        UIHelper.hSetTextToTextView(
                hContactsVH.hItemRecyclerContactsBinding.numberTextView, hNewList.get(position).getContactNumber());
        int id = hNewList.get(position).gethId();

        if (selectedIds.contains(id)) {
            hContactsVH.hItemRecyclerContactsBinding.rootView.setForeground(new ColorDrawable(ContextCompat.getColor(context,
                    R.color.colorControlActivated)));
        } else {
            hContactsVH.hItemRecyclerContactsBinding.rootView.setForeground(new ColorDrawable(ContextCompat.getColor(context,
                    android.R.color.transparent)));
        }

    }

    @Override
    public int getItemCount() {
        return hNewList.size();
    }

    public ContactsModelWithIds getItem(int position) {
        return hNewList.get(position);
    }


    public void sethSelectedIds(List<Integer> hSelectedIds) {
        this.selectedIds = hSelectedIds;
        notifyDataSetChanged();
    }

    public List<Integer> hGetSelectedIds() {
        return selectedIds;
    }

    public void deleteItem(int position) {
        mRecentlyDeletedItem = hNewList.get(position);
        mRecentlyDeletedItemPosition = position;
        hNewList.remove(position);
        notifyItemRemoved(position);
    }

    public Context getContext() {
        return context;
    }

    public void hSetNewList(List<ContactsModelWithIds> hList) {
        this.hNewList = hList;
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = hNewList.size(); i < size; i++) {
            String section = String.valueOf(hNewList.get(i).getContactName().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }


    public void hSelectAll() {
        selectedIds.clear();
        List<Integer> hTempSelectedIds = new ArrayList<>();
        for (ContactsModelWithIds contactsEntity : hNewList) {
            hTempSelectedIds.add(contactsEntity.gethId());
        }
        selectedIds.addAll(hTempSelectedIds);
        notifyDataSetChanged();
    }

    public void clearAll() {
        selectedIds.clear();
        notifyDataSetChanged();
    }

}
