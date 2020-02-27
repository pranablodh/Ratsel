package com.pranab.messageencoder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class encodeMessage extends AppCompatActivity
{

    private Button generateKey;
    private Button encode;
    private Button shareKey;
    private Button shareMessage;
    private Spinner encryptionBit;
    private EditText message;
    private EditText encryptedMessage;
    private EditText encryptionKey;

    SecretKey secretKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode_message);

        generateKey = (Button) findViewById(R.id.generateKey);
        encode = (Button) findViewById(R.id.encode);
        shareKey = (Button) findViewById(R.id.shareKey);
        shareMessage = (Button) findViewById(R.id.shareMessage);
        encryptionBit = (Spinner) findViewById(R.id.encryptionBit);
        message = (EditText) findViewById(R.id.message);
        encryptedMessage = (EditText) findViewById(R.id.encryptedMessage);
        encryptionKey = (EditText) findViewById(R.id.encryptionKey);

        generateKey.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!validateKeyLength())
                {
                    return;
                }
                generateKey();
            }
        });

        encode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                encodeText(message.getText().toString());
            }
        });

        shareKey.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!validateKeyLength())
                {
                    return;
                }

                shareSecretKeys();
            }
        });

        shareMessage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                shareEncodedMessage();
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
        Intent homePage = new Intent(encodeMessage.this, homePage.class);
        startActivity(homePage);
        finish();
    }

    //Validating Spinner Selection
    private Boolean validateKeyLength()
    {
        if(encryptionBit.getSelectedItem().toString().equalsIgnoreCase("Key Length"))
        {
            encryptionKey.setText("Please Select A Valid Key Length");
            return false;
        }

        else
        {
            return true;
        }
    }

    //Getting Bit Length From Spinner Items
    private int selectedKeyLength()
    {
        String[] item = encryptionBit.getSelectedItem().toString().split(" ");
        return Integer.parseInt(item[0]);
    }

    //Generating Secret Keys
    private void generateKey()
    {
        try
        {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(selectedKeyLength());
            secretKey = keyGenerator.generateKey();
            String s = Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);
            encryptionKey.setText(s);
            Log.d("KeyValue", secretKey.toString());
            Log.d("KeyValue", s);
        }

        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    //Encrypt Message
    private void encodeText(String message)
    {
        try
        {
            byte[] plaintTextByteArray = message.getBytes("UTF8");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherText = cipher.doFinal(plaintTextByteArray);
            String s = Base64.encodeToString(cipherText, Base64.DEFAULT);
            encryptedMessage.setText(s);
            Log.d("KeyValue", s);
        }

        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    //Share Secret Keys
    private void shareSecretKeys()
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, encryptionKey.getText().toString().replace("\n",""));
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
    }

    //Share Encoded Message
    private void shareEncodedMessage()
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, encryptedMessage.getText().toString().replace("\n",""));
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
    }
}
