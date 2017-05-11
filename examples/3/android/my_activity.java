package com.example.a11x256.frida_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Base64;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class my_activity extends AppCompatActivity {
    private String total = "@@@###@@@";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activity);
        while (true){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            fun(50,30);
            Log.d("string" , fun("LoWeRcAsE Me!!!!!!!!!"));
        }
    }

    void fun(int x , int y ){

        Log.d("Sum" , String.valueOf(x+y));
    }

    String fun(String x){
        total +=x;
        return x.toLowerCase();
    }

    String secret(){
        return total;
    }


}
