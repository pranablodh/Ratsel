package com.pranab.messageencoder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class decodeMessage extends AppCompatActivity
{

    private Button decode;
    private EditText encryptionKey;
    private EditText encryptedMessage;
    private EditText decryptedMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode_message);

        decode = (Button) findViewById(R.id.decode);
        encryptionKey = (EditText) findViewById(R.id.encryptionKey);
        encryptedMessage = (EditText) findViewById(R.id.encryptedMessage);
        decryptedMessage = (EditText) findViewById(R.id.decryptedMessage);

        decode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                decryptMessage(encryptionKey.getText().toString().trim().replace("\n", ""),
                        encryptedMessage.getText().toString().trim().replace("\n", ""));
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        goToHomePage();
        super.onBackPressed();
    }

    private void goToHomePage()
    {
        Intent homePage = new Intent(decodeMessage.this, homePage.class);
        startActivity(homePage);
        finish();
    }

    //Message Decryption
    private void decryptMessage(String key, String message)
    {
        try
        {
            //Converting Base64 Key Into Secret Key
            byte[] decodedKey = Base64.decode(key.getBytes("UTF-8"), Base64.DEFAULT);
            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            //Converting Base64 Message Into Bytes
            byte[] decryptedBase64Message = Base64.decode(message.getBytes("UTF-8"), Base64.DEFAULT);

            //Decrypting Message
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            byte[] decrypted = cipher.doFinal(decryptedBase64Message);

            //Converting Decrypted Message Into String
            String originalMessage = new String(decrypted, "UTF-8");
            decryptedMessage.setText(originalMessage);
            Log.d("KeyValue", originalKey.toString());
        }

        catch (Exception e)
        {
            Log.d("KeyValue", e.toString());
        }
    }
}
