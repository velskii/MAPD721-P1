package com.zfl.mapd721_p1.ui.weatherInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeatherInfoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WeatherInfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}