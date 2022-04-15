package com.zfl.mapd721_p1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.zfl.mapd721_p1.ui.home.YlpRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            getDetailsForBusiness(extras.getString("item_id"));
        }
        ImageButton back = (ImageButton)findViewById(R.id.backToMain);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void getDetailsForBusiness(String id) {
        YLPRepository ylp = new YLPRepository(RestaurantDetailsActivity.this);
        ylp.getDetails(id, new YLPRepository.DetailsResponseListener() {
            @Override
            public void onError(String message) {
                Log.d("Zhou", message);
            }

            @Override
            public void onResponse(SearchResultModel searchResultModel) {

                TextView item_name = findViewById(R.id.item_name);
                item_name.setText( searchResultModel.getName() );
                TextView item_price = findViewById(R.id.item_price);
                item_price.setText( searchResultModel.getPrice() );

                TextView item_alias = findViewById(R.id.item_alias);
                item_alias.setText( searchResultModel.getAlias() );

                TextView item_rating = findViewById(R.id.item_rating);
                item_rating.setText( searchResultModel.getRating().toString() );

                TextView item_location = findViewById(R.id.item_location);
                item_location.setText( searchResultModel.getLocation() );

//                TextView item_rating_count = findViewById(R.id.item_rating_count);
//                item_rating_count.setText( searchResultModel.getReview_count().toString() );


                ImageView item_image = findViewById(R.id.item_image);
                ImageLoader imageLoader = VolleySingleton.getInstance(RestaurantDetailsActivity.this).getImageLoader();

                imageLoader.get(searchResultModel.getImage_url(), new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        item_image.setImageBitmap(response.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

            }
        });
    }

}