package com.example.rs.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.rs.myapplication.Utils.CustomRequest;
import com.example.rs.myapplication.Utils.SharedPref;
import com.example.rs.myapplication.Utils.Urls;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    MaterialButton AddJob, UpdateJob,GetJobs;
    SharedPref sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sf = new SharedPref(getApplicationContext());
        AddJob = findViewById(R.id.AddJob);
        UpdateJob = findViewById(R.id.UpdateJob);
        GetJobs=findViewById(R.id.GetJobs);
        GetJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetJobs.class);
                startActivity(intent);
            }
        });
        AddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckForAddJob(false);
            }
        });
        UpdateJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckForAddJob(true);
            }
        });
    }

    public void CheckForAddJob(boolean IsUpdate) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", sf.getUserId());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        CustomRequest customRequest = new CustomRequest(Request.Method.POST, Urls.GetJob, headers, jsonObject, response -> {
            String Message = "";
            String UserId = "";
            try {
                Log.e("Response", response.toString());
                Message = response.getString("message");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!IsUpdate) {
                if (Message.equals("Empty")) {
                    Intent intent = new Intent(getApplicationContext(), SubmitJob.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "شما قبلا شغل خود را ثبت کرده اید برای ویرایش اطلاعات از قسمت مدیریت استفاده نمایید", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (Message.equals("Empty")) {
                    Toast.makeText(MainActivity.this, "شغلی توسط شما ثبت نشده است لطفا از قسمت افزودن شغل استفاده نمایید", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), UpdateJob.class);
                    startActivity(intent);
                }


            }

        }, error -> {
            Log.e("jsonError", String.valueOf(error));
        });
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(customRequest);
    }

    public void Exit(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("توجه");
        alertDialog.setMessage("آیا مایل به خروج از برنامه هستید؟");
        alertDialog.setPositiveButton("بله",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);

                        dialog.dismiss();

                    }
                });
        alertDialog.setNegativeButton("خیر",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.dismiss();
                    }
                });
        // Showing Alert Message
        alertDialog.show();


    }

    public void Logout(View view) {
        sf.setIsLogin(false);
        sf.setUserId("");
        Toast.makeText(MainActivity.this, "شما با موفقیت از حساب خارج شدید", Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);

    }
}