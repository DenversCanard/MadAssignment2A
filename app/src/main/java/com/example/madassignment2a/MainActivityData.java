package com.example.madassignment2a;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityData extends ViewModel {
    public MutableLiveData<String> DisplayScreen;
    public MutableLiveData<Integer> ProfileID;

    public MainActivityData(){
        DisplayScreen = new MediatorLiveData<String>();
        DisplayScreen.setValue("Contacts");
        ProfileID = new MediatorLiveData<Integer>();
        ProfileID.setValue(0);
    }
    public String getDisplayScreen(){
        return DisplayScreen.getValue();
    }
    public void setDisplayScreen(String value){
        DisplayScreen.setValue(value);
    }

    public Integer getProfileID(){
        return ProfileID.getValue();
    }
    public void setProfileID(Integer value){
        ProfileID.setValue(value);
    }
}
