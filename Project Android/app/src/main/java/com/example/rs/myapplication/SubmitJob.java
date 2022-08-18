package com.example.rs.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.rs.myapplication.Utils.CustomRequest;
import com.example.rs.myapplication.Utils.SharedPref;
import com.example.rs.myapplication.Utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SubmitJob extends AppCompatActivity {
    EditText City, Address, JobTitle, Manager;
    Button Submit;
    SharedPref sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_job);
        sf = new SharedPref(getApplicationContext());
        City = findViewById(R.id.City);
        Address = findViewById(R.id.Address);
        JobTitle = findViewById(R.id.JobTitle);
        Manager = findViewById(R.id.Manager);
        Submit = findViewById(R.id.Submit);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!City.getText().toString().equals("") &&
                        !Address.getText().toString().equals("") &&
                        !JobTitle.getText().toString().equals("") &&
                        !Manager.getText().toString().equals("")) {
                    AddJob();
                } else {
                    Toast.makeText(getApplicationContext(), "لطفا همه ی فیلد هارا به درستی وارد کنید", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void AddJob() {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", sf.getUserId());
            jsonObject.put("name", JobTitle.getText().toString());
            jsonObject.put("manager", Manager.getText().toString());
            jsonObject.put("city", City.getText().toString());
            jsonObject.put("address", Address.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        CustomRequest customRequest = new CustomRequest(Request.Method.POST, Urls.SubmitJobs, headers, jsonObject, response -> {
            String Message = "";
            String UserId = "";
            try {
                Log.e("Response", response.toString());
                Message = response.getString("message");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (Message.equals("Data Saved")) {
                Toast.makeText(SubmitJob.this, "شغل شما با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(SubmitJob.this, "مشکلی در ثبت اطلاعات پیش آمده لطفا مجددا بررسی نمایید", Toast.LENGTH_SHORT).show();
            }

        }, error -> {
            Log.e("jsonError", String.valueOf(error));
        });
        RequestQueue queue = Volley.newRequestQueue(SubmitJob.this);
        queue.add(customRequest);
    }

}