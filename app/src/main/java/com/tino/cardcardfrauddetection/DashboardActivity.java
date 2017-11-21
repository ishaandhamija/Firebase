package com.tino.cardcardfrauddetection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kairos.Kairos;
import com.kairos.KairosListener;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class DashboardActivity extends AppCompatActivity {

    Button b1,b2,b3;

    Bitmap picture1 = null;
    Bitmap picture2 = null;

    String app_id = "e776e8f2";
    String api_key = "2bc7aa89d086fcf36bf3243ddc027526";
    Kairos myKairos;
    KairosListener listener;

    Integer k = 1;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        b1= (Button) findViewById(R.id.addmember);
        b2= (Button) findViewById(R.id.card);
        b3= (Button) findViewById(R.id.logout);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        myKairos = new Kairos();
        myKairos.setAuthentication(this, app_id, api_key);

        listener = new KairosListener() {
            @Override
            public void onSuccess(String response) {
                // your code here!
                Log.d("KAIROS DEMO success", response);
                if (k == 1) {
                    Toast.makeText(DashboardActivity.this, "Member Added", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (response.contains(sharedpreferences.getString("creditCardNumber", null))) {
                        Toast.makeText(DashboardActivity.this, "You are eligible to use the Credit Card", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DashboardActivity.this, "You are not eligible to access this Credit Card", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFail(String response) {
                // your code here!
                Log.d("KAIROS DEMO", response);
                Toast.makeText(DashboardActivity.this, "You are not eligible to access this Credit Card", Toast.LENGTH_LONG).show();
            }
        };

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 12221);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1221);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("creditCardNumber", null);
                editor.putString("password", null);
                editor.commit();

                Intent intent = new Intent(DashboardActivity.this, SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 12221 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            k = 1;
            picture1 = photo;
            String subjectId = sharedpreferences.getString("creditCardNumber", null);
            String galleryId = "People";
            try {
                myKairos.enroll(picture1, subjectId, galleryId, null, null, null, listener);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            img.setImageBitmap(photo);
        }
        if (requestCode == 1221 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            k = 2;
            detectFace(photo);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void detectFace(Bitmap photo) {

        String galleryId = "People";
        String selector = "FULL";
        String threshold = "0.75";
        String minHeadScale = "0.25";
        String maxNumResults = "25";

        try {
            myKairos.recognize(photo,
                    galleryId,
                    selector,
                    threshold,
                    minHeadScale,
                    maxNumResults,
                    listener);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}