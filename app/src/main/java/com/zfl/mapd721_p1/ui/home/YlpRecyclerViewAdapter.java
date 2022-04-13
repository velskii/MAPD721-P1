package com.zfl.mapd721_p1.ui.home;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.zfl.mapd721_p1.R;
import com.zfl.mapd721_p1.SearchResultModel;
import com.zfl.mapd721_p1.VolleySingleton;

import java.util.List;
import java.util.Map;

public class YlpRecyclerViewAdapter extends RecyclerView.Adapter<YlpRecyclerViewAdapter.ViewHolder>{

    private List<SearchResultModel> mDataSet;
    private static final String TAG = "YlpRecyclerViewAdapter";

    public YlpRecyclerViewAdapter(List<SearchResultModel> dataSet) {
        mDataSet = dataSet;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView displayName;
        private final TextView displayLocation;
        private final TextView displayReviewCount;
        private final TextView displayAlias;
        private final TextView displayRating;
        private final TextView displayPrice;
        private final ImageView displayImage;
        private static final String TAG = "YlpRecyclerViewAdapter";

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                private final TextView textView = null;
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            displayName = (TextView) v.findViewById(R.id.item_name);
            displayLocation = (TextView) v.findViewById(R.id.item_location);
            displayReviewCount = (TextView) v.findViewById(R.id.item_rating_count);
            displayPrice = (TextView) v.findViewById(R.id.item_price);
            displayAlias = (TextView) v.findViewById(R.id.item_range);
            displayRating = (TextView) v.findViewById(R.id.item_rating);
            displayImage = (ImageView) v.findViewById(R.id.item_image);

        }
        public TextView getDisplayName() {
            return displayName;
        }
        public TextView getDisplayLocation() {
            return displayLocation;
        }
        public TextView getDisplayPrice() {
            return displayPrice;
        }
        public TextView getDisplayReviewCount() {
            return displayReviewCount;
        }
        public TextView getDisplayAlias() {
            return displayAlias;
        }
        public TextView getDisplayRating() {
            return displayRating;
        }
        public ImageView getDisplayImage() {
            return displayImage;
        }
    }

    @Override
    public YlpRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.yelp_item, viewGroup, false);
        return new YlpRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(YlpRecyclerViewAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        ImageLoader imageLoader = VolleySingleton.getInstance(viewHolder.displayAlias.getContext()).getImageLoader();
        imageLoader.get(mDataSet.get(position).getImage_url(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                viewHolder.getDisplayImage().setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Log.d(TAG, "Element " + position + " set.");
        viewHolder.getDisplayName().setText( mDataSet.get(position).getName() );
        viewHolder.getDisplayPrice().setText( mDataSet.get(position).getPrice() );
        viewHolder.getDisplayLocation().setText( mDataSet.get(position).getLocation() );
//        viewHolder.getDisplayReviewCount().setText( mDataSet.get(position).getCoordinates().toString() );
        viewHolder.getDisplayAlias().setText( mDataSet.get(position).getAlias() );
        viewHolder.getDisplayRating().setText( mDataSet.get(position).getRating().toString() );

    }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
