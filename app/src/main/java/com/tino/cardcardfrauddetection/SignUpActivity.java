package com.tino.cardcardfrauddetection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText et1, et2, et3, et4, et5;
    Button btn;

    DatabaseReference firebaseDatabase;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et1 = (EditText) findViewById(R.id.e1);
        et2 = (EditText) findViewById(R.id.e2);
        et3 = (EditText) findViewById(R.id.e6);
        et4 = (EditText) findViewById(R.id.e4);
        et5 = (EditText) findViewById(R.id.e5);
        btn = (Button) findViewById(R.id.btn_Register);

        firebaseDatabase = FirebaseDatabase.getInstance().getReference("creditcarddetails");

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNumber = et1.getText().toString();
                String password = et2.getText().toString();
                String email = et5.getText().toString();
                String contactNo = et3.getText().toString();
                String name = et4.getText().toString();

                User user = new User(cardNumber, password, email, contactNo, name);

                firebaseDatabase.child(cardNumber).setValue(user);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("creditCardNumber", cardNumber);
                editor.putString("password", password);
                editor.commit();

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
