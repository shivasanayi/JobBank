package com.example.rs.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.rs.myapplication.Utils.CustomRequest;
import com.example.rs.myapplication.Utils.NukeSSLCerts;
import com.example.rs.myapplication.Utils.SharedPref;
import com.example.rs.myapplication.Utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText textInputEditTextusername, textInputEditTextpassword;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    Button butttonlogin;
    SharedPref sf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NukeSSLCerts.nuke();
        setContentView(R.layout.activity_login);
        sf=new SharedPref(getApplicationContext());
        if(sf.getIsLogin()){
            finish();
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
        textInputEditTextusername = findViewById(R.id.username);
        textInputEditTextpassword = findViewById(R.id.password);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        butttonlogin = findViewById(R.id.buttonlogin);

        butttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password, username;
                password = String.valueOf(textInputEditTextpassword.getText());
                username = String.valueOf(textInputEditTextusername.getText());

                if (!password.equals("") && !username.equals("")) {
                    UserLogin();
                } else {
                    Toast.makeText(getApplicationContext(), "لطفا همه ی فیلد هارا به درستی وارد کنید", Toast.LENGTH_SHORT).show();
                }
            }
        });



        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

    }

    public void UserLogin() {
        String password, username;
        password = String.valueOf(textInputEditTextpassword.getText());
        username = String.valueOf(textInputEditTextusername.getText());

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("emailAddress", username);
            jsonObject.put("password", password);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        CustomRequest customRequest = new CustomRequest(Request.Method.POST, Urls.Login, headers, jsonObject, response -> {
            String Message = "";
            String UserId = "";
            try {
                Log.e("Response",response.toString());
                Message = response.getString("message");
                UserId = response.getString("userId");
            } catch (JSONException e) {
                e.printStackTrace();
            }
                if (Message.equals("User Login Successful")) {

                    sf.setIsLogin(true);
                    sf.setUserId(UserId);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "ایمیل یا رمز شما اشتباه است", Toast.LENGTH_SHORT).show();
                }


            
        }, error -> {
            Log.e("jsonError", String.valueOf(error));
        });
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        queue.add(customRequest);
    }
}

