package com.example.rs.myapplication;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rs.myapplication.Adapter.GetJobsAdapter;
import com.example.rs.myapplication.Model.GetJobsObject;
import com.example.rs.myapplication.Utils.RecyclerTouchListener;
import com.example.rs.myapplication.Utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




public class GetJobs extends AppCompatActivity {

    private RecyclerView mJobList;
    private RecyclerView.Adapter mJobListAdapter;
    private RecyclerView.LayoutManager mJobListLayoutManager;
    ArrayList<GetJobsObject> jobList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_jobs);
        jobList = new ArrayList<>();
        initializeRecyclerView();
        getJobList();
        mJobList.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mJobList, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //
                GetJobsObject job = jobList.get(position);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }




    public void getJobList() {



        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.GetJobs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONArray array = null;
                            array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                //adding the product to product list
                                GetJobsObject jobsObject = new GetJobsObject(product.getString("id"),product.getString("userId"),
                                        product.getString("name"),product.getString("manager"),product.getString("city"),
                                        product.getString("address")
                                        );
                                jobList.add(jobsObject);
                            }

                            mJobListAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(GetJobs.this);
        requestQueue.add(stringRequest);

    }


    @SuppressLint("WrongConstant")
    private void initializeRecyclerView() {
        mJobList = findViewById(R.id.contactlist);
        mJobList.setNestedScrollingEnabled(false);
        mJobList.setHasFixedSize(false);
        mJobListLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mJobList.setLayoutManager(mJobListLayoutManager);
        mJobListAdapter = new GetJobsAdapter(jobList);
        mJobList.setAdapter(mJobListAdapter);
    }


}
