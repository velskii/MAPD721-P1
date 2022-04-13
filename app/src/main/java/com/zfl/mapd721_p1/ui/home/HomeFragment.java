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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zfl.mapd721_p1.SearchResultModel;
import com.zfl.mapd721_p1.YLPRepository;
import com.zfl.mapd721_p1.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private YlpRecyclerViewAdapter YlpRVAdapter;
    private  RecyclerView businessList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        businessList = binding.businessList;
        businessList.setLayoutManager( new LinearLayoutManager(getActivity()) );

        getDefaultDataForBusiness("Toronto");

        final EditText searchLocation = binding.searchLocation;

        final ImageView searchBtn = binding.searchButton;
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.getList( searchLocation.getText().toString(), getContext() ).observe(getViewLifecycleOwner(), businessList::setAdapter);

            }
        });
        return root;
    }

    public void getDefaultDataForBusiness(String input) {
        YLPRepository ylp = new YLPRepository(getContext());
        ylp.search( input, new YLPRepository.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                List empty = new ArrayList<>();

                YlpRVAdapter = new YlpRecyclerViewAdapter( empty );
                businessList.setAdapter(YlpRVAdapter);
            }
            @Override
            public void onResponse(List<SearchResultModel> searchResultModelList) {
                YlpRVAdapter = new YlpRecyclerViewAdapter( searchResultModelList );
                businessList.setAdapter(YlpRVAdapter);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}