package com.zfl.mapd721_p1.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.zfl.mapd721_p1.databinding.FragmentHomeBinding;
import com.zfl.mapd721_p1.ui.weather.WeatherActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button btnWeather = binding.btnWeather;
        homeViewModel.getBtnWeatherText().observe(getViewLifecycleOwner(), btnWeather::setText);
//        Button btnLogin = findViewById(R.id.btn_login);
        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WeatherActivity.class));
            }
        });

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final ListView lv = binding.lvReports;
//        homeViewModel.getList(getContext()).observe(getViewLifecycleOwner(), lv::setAdapter);

        final EditText searchLocation = binding.searchLocation;


        final ImageView searchBtn = binding.searchButton;
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeViewModel.searchLocation( searchLocation.getText().toString(), getContext() ).observe(getViewLifecycleOwner(), lv::setAdapter);

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}