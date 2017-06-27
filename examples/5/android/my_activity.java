package com.example.a11x256.frida_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class my_activity extends AppCompatActivity {
    EditText username_et;
    EditText password_et;
    TextView message_tv;
    HttpURLConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activity);
        message_tv = ((TextView) findViewById(R.id.textView));
        username_et = (EditText) findViewById(R.id.editText);
        password_et = (EditText) findViewById(R.id.editText2);
        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_data(username_et.getText() + ":" + password_et.getText());
            }
        });

    }

    void send_data(final String data) {
        URL url = null;
        try {
            url = new URL("http://192.168.18.134");
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                        out.writeBytes(enc(data));
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        final String text = in.readLine();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((TextView) findViewById(R.id.textView)).setText(text);
                                dec(text);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    String enc(String data) {
        try {
            String pre_shared_key = "aaaaaaaaaaaaaaaa"; //assume that this key was not hardcoded
            String generated_iv = "bbbbbbbbbbbbbbbb";
            Cipher my_cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            my_cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(pre_shared_key.getBytes("UTF-8"), "AES"), new IvParameterSpec(generated_iv.getBytes("UTF-8")));
            byte[] x = my_cipher.doFinal(data.getBytes());

            System.out.println(new String(Base64.encode(x, Base64.DEFAULT)));
            return new String(Base64.encode(x, Base64.DEFAULT));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    String dec(String data) {
        try {
            byte[] decoded_data = Base64.decode(data.getBytes(), Base64.DEFAULT);
            String pre_shared_key = "aaaaaaaaaaaaaaaa"; //assume that this key was not hardcoded
            String generated_iv = "bbbbbbbbbbbbbbbb";
            Cipher my_cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            my_cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(pre_shared_key.getBytes("UTF-8"), "AES"), new IvParameterSpec(generated_iv.getBytes("UTF-8")));
            String plain = new String(my_cipher.doFinal(decoded_data));
            return plain;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "";
    }
}