package com.hashim.mapswithgeofencing.Interfaces;


public interface HDialogResponseInterface {
    void onNegtiveResponse(int id, int dialogueType);

    void onPostiveResponse(int id, int dialogueType);

    void onPostiveResponse(int which, int dialogueType, CharSequence charSequence);
    void onNeutralResponse(int which, int dialogueType);
}
