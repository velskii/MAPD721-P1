package com.zfl.mapd721_p1.ui.home;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zfl.mapd721_p1.SearchResultModel;
import com.zfl.mapd721_p1.YLPRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> mBtnWeather;
    private final MutableLiveData<ArrayAdapter> mLocation;

    private final MutableLiveData<ArrayAdapter> mList;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mBtnWeather = new MutableLiveData<>();
        mBtnWeather.setValue("Weather API");
        mLocation = new MutableLiveData<>();
        mList = new MutableLiveData<>();

        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getBtnWeatherText() {
        return mBtnWeather;
    }

    public LiveData<ArrayAdapter> searchLocation(String input, Context context) {

        YLPRepository ylp = new YLPRepository(context);
        ylp.search( input, new YLPRepository.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                List empty = new ArrayList<> ();
                ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, empty);
                mLocation.setValue(arrayAdapter);
            }
            @Override
            public void onResponse(List<SearchResultModel> searchResultModelList) {

                ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, searchResultModelList);
                mLocation.setValue(arrayAdapter);
            }
        });

        return mLocation;
    }

    public LiveData<ArrayAdapter> getList(String input, Context context) {

        YLPRepository ylp = new YLPRepository(context);
        ylp.search(input, new YLPRepository.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                List empty = new ArrayList<> ();
                ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, empty);
                mList.setValue(arrayAdapter);
            }
            @Override
            public void onResponse(List<SearchResultModel> searchResultModelList) {
//                mText.setValue( searchResultModelList.get(0).toString() );

                ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, searchResultModelList);
                mList.setValue(arrayAdapter);
            }
        });

        return mList;
    }
}