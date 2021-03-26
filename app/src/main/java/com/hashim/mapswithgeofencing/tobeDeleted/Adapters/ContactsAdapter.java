/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tobeDeleted.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
//    implements
//} SectionIndexer
//{
//
//    private Context context;
//    private List<ContactsEntity> hNewList;
//    private List<Integer> selectedIds = new ArrayList<>();
//    private int mRecentlyDeletedItemPosition;
//    private ContactsEntity mRecentlyDeletedItem;
//    OnItemClickListener hOnItemClickListener;
//    private ArrayList<Integer> mSectionPositions;
//    RecyclerInterface hRecyclerInterface;
//
//    public ContactsAdapter(Context context, List<ContactsEntity> hNewList) {
//        this.context = context;
//        this.hNewList = hNewList;
//        this.hOnItemClickListener = (OnItemClickListener) context;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new ContactsVH(
//                ItemRecyclerContactsBinding.inflate(
//                        LayoutInflater.from(parent.getContext()),
//                        parent,
//                        false
//                )
//        );
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        ContactsVH hContactsVH = (ContactsVH) holder;
//        hBindTodaysVh(hContactsVH, position);
//    }
//
//    private void hBindTodaysVh(ContactsVH hContactsVH, int position) {
//        hContactsVH.hItemRecyclerContactsBinding.rootView.setOnLongClickListener(v -> {
//            hOnItemClickListener.onItemLongClick(v, position);
//            return false;
//        });
//        hContactsVH.hItemRecyclerContactsBinding.title.setText(hNewList.get(position).getName());
////        UIHelper.hSetTextToTextView(hContactsVH.hItemRecyclerContactsBinding.numberTextView, hNewList.get(position).getNumber());
//        int id = hNewList.get(position).getContactsId();
//
//        if (selectedIds.contains(id)) {
//            hContactsVH.hItemRecyclerContactsBinding.rootView.setForeground(new ColorDrawable(ContextCompat.getColor(context,
//                    R.color.colorControlActivated)));
//        } else {
//            hContactsVH.hItemRecyclerContactsBinding.rootView.setForeground(new ColorDrawable(ContextCompat.getColor(context,
//                    android.R.color.transparent)));
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return hNewList.size();
//    }
//
//    public ContactsEntity getItem(int position) {
//        return hNewList.get(position);
//    }
//
//
//    public void sethSelectedIds(List<Integer> hSelectedIds) {
//        this.selectedIds = hSelectedIds;
//        notifyDataSetChanged();
//    }
//
//    public List<Integer> hGetSelectedIds() {
//        return selectedIds;
//    }
//
//    public void deleteItem(int position) {
//        mRecentlyDeletedItem = hNewList.get(position);
//        mRecentlyDeletedItemPosition = position;
//        hNewList.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    public Context getContext() {
//        return context;
//    }
//
//    public void hSetNewList(List<ContactsEntity> hList) {
//        this.hNewList = hList;
//    }
//
//    @Override
//    public Object[] getSections() {
//        List<String> sections = new ArrayList<>(26);
//        mSectionPositions = new ArrayList<>(26);
//        for (int i = 0, size = hNewList.size(); i < size; i++) {
//            String section = String.valueOf(hNewList.get(i).getName().charAt(0)).toUpperCase();
//            if (!sections.contains(section)) {
//                sections.add(section);
//                mSectionPositions.add(i);
//            }
//        }
//        return sections.toArray(new String[0]);
//    }
//
//    @Override
//    public int getPositionForSection(int sectionIndex) {
//        return mSectionPositions.get(sectionIndex);
//    }
//
//    @Override
//    public int getSectionForPosition(int position) {
//        return 0;
//    }
//
//
//    public void hSelectAll() {
//        selectedIds.clear();
//        List<Integer> hTempSelectedIds = new ArrayList<>();
//        for (ContactsEntity contactsEntity : hNewList) {
//            hTempSelectedIds.add(contactsEntity.getContactsId());
//        }
//        selectedIds.addAll(hTempSelectedIds);
//        notifyDataSetChanged();
//    }
//
//    public void clearAll() {
//        selectedIds.clear();
//        notifyDataSetChanged();
//    }

}
