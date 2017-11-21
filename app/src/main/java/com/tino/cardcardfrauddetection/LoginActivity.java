package com.tino.cardcardfrauddetection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText et1, et2;
    Button btn;
    TextView tv;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        et1 = (EditText) findViewById(R.id.creditnumber);
        et2 = (EditText) findViewById(R.id.et_VLoginPassword);
        btn = (Button) findViewById(R.id.btn_LogIn);
        tv = (TextView) findViewById(R.id.tv_SignUp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et1.getText().toString().equals(sharedpreferences.getString("creditCardNumber", null))) {
                    if (et2.getText().toString().equals(sharedpreferences.getString("password", null))) {
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        intent.putExtra("creditNo", et1.getText().toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }
}