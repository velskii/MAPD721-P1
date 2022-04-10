package com.zfl.mapd721_p1;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YLPRepository {

    public static final String QUERY_SEARCH = "https://api.yelp.com/v3/businesses/search";
    private String clientId = "oyREd_CuwJJoMlAGVE0Xgw";
    private String APIKey = "8MGOgG6KR_ZI_AoFUZ0yOqpBx0Tb3LWCSOmrMQb9z9_w10OJxsYzBHvh6UmBWyjim262HjzXj3_zmCrW8m5rERgBJnc1QAk7jsAHzT5hURliFu_Qgk4J3iNTd7VMYnYx";

    Context ctx;

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(List<SearchResultModel> searchResultModels);
    }

    public YLPRepository(Context context) {
        this.ctx = context;
    }

    public void search  (String keyword, VolleyResponseListener volleyResponseListener) {

        String url = QUERY_SEARCH + "?term=food&location=" + keyword;

        List<SearchResultModel> searchResultModels = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray business =  response.getJSONArray("businesses");

                            for (int i=0; i< business.length(); i++){
                                SearchResultModel searchResultModel = new SearchResultModel();
                                JSONObject item = (JSONObject) business.get(i);

                                searchResultModel.setId( item.getString("id") );
                                searchResultModel.setAlias( item.getString("alias") );
                                searchResultModel.setName( item.getString("name") );
                                searchResultModel.setImage_url( item.getString("image_url") );
                                searchResultModel.setIs_closed( item.getBoolean("is_closed") );
                                searchResultModel.setUrl( item.getString("url") );
                                searchResultModel.setReview_count( item.getInt("review_count") );
                                searchResultModel.setRating((float) item.getLong("rating"));
                                searchResultModel.setCoordinates( item.getJSONObject("coordinates") );
                                searchResultModel.setLocation( item.getString("location") );
                                searchResultModel.setPrice( "$$" );

                                searchResultModels.add(searchResultModel);
                            }
                            volleyResponseListener.onResponse( searchResultModels );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyResponseListener.onError(error.toString());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + APIKey);
                return headers;
            }
            //Pass Your Parameters here
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("term", "food");
                params.put("location", "NYC");
                return params;
            }
        };

        VolleySingleton.getInstance(this.ctx).addToRequestQueue(jsonObjectRequest);
    }





}
