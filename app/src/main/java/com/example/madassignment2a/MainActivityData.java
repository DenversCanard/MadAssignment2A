package com.example.madassignment2a;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityData extends ViewModel {
    public MutableLiveData<String> DisplayScreen;

    public MainActivityData(){
        DisplayScreen = new MediatorLiveData<String>();
        DisplayScreen.setValue("Contacts");
    }
    public String getDisplayScreen(){
        return DisplayScreen.getValue();
    }
    public void setDisplayScreen(String value){
        DisplayScreen.setValue(value);
    }
}
