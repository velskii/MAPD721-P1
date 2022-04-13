package com.zfl.mapd721_p1.ui.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.zfl.mapd721_p1.R;
import com.zfl.mapd721_p1.databinding.FragmentDashboardBinding;
import com.zfl.mapd721_p1.ui.weather.WeatherActivity;
import com.zfl.mapd721_p1.ui.weather.WeatherRVAdapter;
import com.zfl.mapd721_p1.ui.weather.WeatherRVModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private RelativeLayout homeRL;
    private ProgressBar loadingPB;
    private TextView cityNameTV, temperatureTV, conditionTV;
    private RecyclerView weatherRV;
    private TextInputEditText cityEdt;
    private ImageView backIV, iconIV, searchIV;
    private ArrayList<WeatherRVModel> weatherRVModelArrayList;
    private WeatherRVAdapter weatherRVAdapter;
    private LocationManager locationManager;
    private int PERMISSON_CODE = 1;
    private String cityName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final RecyclerView weatherRV = binding.idRvWeather;
        loadingPB = binding.idPBLoading;
        homeRL = binding.idRLHome;
        cityNameTV = binding.idTVCityName;
        searchIV = binding.idIVSearch;
        temperatureTV = binding.idTVTemperature;
        conditionTV = binding.idTVCondition;
        cityEdt = binding.idEdtCity;
        backIV = binding.idIVBack;
        iconIV = binding.idIVIcon;
//
//
////        getActivity().getActionBar().hide();
//
//        backIV.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
//        backIV.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
//        backIV.setAdjustViewBounds(false);
//        backIV.setScaleType(ImageView.ScaleType.FIT_XY);

        weatherRVModelArrayList = new ArrayList<>();
        weatherRVAdapter = new WeatherRVAdapter(getContext(), weatherRVModelArrayList);
        weatherRV.setAdapter(weatherRVAdapter);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSON_CODE);
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        cityName = "Toronto";//getCityName(location.getLongitude(), location.getLatitude());
        getWeatherInfo(cityName);

        searchIV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String city = cityEdt.getText().toString();
                if(city.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter city name", Toast.LENGTH_LONG).show();
                } else {
                    cityNameTV.setText(cityName);
                    getWeatherInfo(city);
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getWeatherInfo(String cityName) {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=e2282bd84cf348ca97d40220221004&q="+cityName+"&days=1&aqi=yes&alerts=yes";
        cityNameTV.setText(cityName);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
                homeRL.setVisibility(View.VISIBLE);
                weatherRVModelArrayList.clear();
                try {
                    String temperature = response.getJSONObject("current").getString("temp_c");
                    temperatureTV.setText(temperature + "°C");
                    int isDay = response.getJSONObject("current").getInt("is_day");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(conditionIcon)).into(iconIV);
                    conditionTV.setText(condition);
                    if(isDay == 1) {
                        // morning
                        Picasso.get().load("https://townsquare.media/site/385/files/2021/04/duo1ze-xtgONQzGgOE-unsplash.jpg?w=980&q=75").into(backIV);
                    } else {
                        Picasso.get().load("https://images.unsplash.com/photo-1613425293967-16ae72140466?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8c25vdyUyMG5pZ2h0fGVufDB8fDB8fA%3D%3D&w=1000&q=80").into(backIV);
                    }
                    JSONObject forecastObj = response.getJSONObject("forecast");
                    JSONObject forecastO = forecastObj.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray hourArray = forecastO.getJSONArray("hour");

                    for(int i =0; i<hourArray.length(); i++) {
                        JSONObject hourObj = hourArray.getJSONObject(i);
                        String time = hourObj.getString("time");
                        String temper = hourObj.getString("temp_c");
                        String img = hourObj.getJSONObject("condition").getString("icon");
                        String wind = hourObj.getString("wind_kph");
                        weatherRVModelArrayList.add(new WeatherRVModel(time, temper, img, wind));
                    }
                    weatherRVAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Please enter valid city name...", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}