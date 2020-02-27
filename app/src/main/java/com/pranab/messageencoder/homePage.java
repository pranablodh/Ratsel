package com.pranab.messageencoder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homePage extends AppCompatActivity
{

    private Button encode;
    private Button decode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        encode = (Button) findViewById(R.id.encode);
        decode = (Button) findViewById(R.id.decode);

        encode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                goToEncoding();
            }
        });

        decode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                goToDecoding();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();
    }

    private void goToEncoding()
    {
        Intent encoding = new Intent(homePage.this, encodeMessage.class);
        startActivity(encoding);
        finish();
    }

    private void goToDecoding()
    {
        Intent decoding = new Intent(homePage.this, decodeMessage.class);
        startActivity(decoding);
        finish();
    }
}
