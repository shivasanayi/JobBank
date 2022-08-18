package com.example.rs.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.rs.myapplication.Utils.CustomRequest;
import com.example.rs.myapplication.Utils.Urls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    EditText textInputEditTextname, textInputEditTextusername, textInputEditTextemail, textInputEditTextpassword;
    Button buttonsabtenam;
    TextView txt1;
    TextView txt2;
    EditText editText;

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sabtenam);

        textInputEditTextname = findViewById(R.id.name);
        textInputEditTextemail = findViewById(R.id.email);
        textInputEditTextusername = findViewById(R.id.username);
        textInputEditTextpassword = findViewById(R.id.password);
        buttonsabtenam = findViewById(R.id.buttonsabtenam);
        editText = findViewById(R.id.Editxt1);


        buttonsabtenam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, username, password, email;
                name = String.valueOf(textInputEditTextname.getText());
                username = String.valueOf(textInputEditTextusername.getText());
                password = String.valueOf(textInputEditTextpassword.getText());
                email = String.valueOf(textInputEditTextemail.getText());

                if (!name.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {
                    UserRegister();
                } else {
                    Toast.makeText(getApplicationContext(), "لطفا همه ی فیلد هارا به درستی وارد کنید", Toast.LENGTH_SHORT).show();
                }
            }
        });

        initViews();
        ArrayList<String> list = new ArrayList<>();

        list.add("آقا");
        list.add("خانم");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(adapter);
    }
    public void UserRegister(){
        String name, username, password, email;
        name = String.valueOf(textInputEditTextname.getText());
        username = String.valueOf(textInputEditTextusername.getText());
        password = String.valueOf(textInputEditTextpassword.getText());
        email = String.valueOf(textInputEditTextemail.getText());

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("fullName",name);
            jsonObject.put("username",username);
            jsonObject.put("emailAddress",email);
            jsonObject.put("password",password);
            jsonObject.put("gender","خانم");
            jsonObject.put("jobTitle","برنامه نویس");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        CustomRequest customRequest = new CustomRequest(Request.Method.POST, Urls.Register, headers, jsonObject, response -> {
            String Message="";
            try {

                Message= response.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
                if (Message.equals("User Register Successful")) {
                    finish();
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);

                    Toast.makeText(Register.this, "با موفقیت ثبت شد", Toast.LENGTH_SHORT)
                            .show();
                } else if(Message.equals("User Registered Before")){
                    Toast.makeText(Register.this, "کاربری با این نام کاربری یا ایمیل قبلا ثبت نام کرده و ثبت نام مجدد با این اطلاعات امکان پذیر نیست", Toast.LENGTH_SHORT).show();
                }



        }, error -> {
            Log.e("jsonError", String.valueOf(error));
        });
        RequestQueue queue = Volley.newRequestQueue(Register.this);
        queue.add(customRequest);
    }

    private void initViews() {
        spinner = (Spinner) findViewById(R.id.spinner);

    }

}

