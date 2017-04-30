package com.example.abdullahhashmi.passwordmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView login1 = (TextView)findViewById(R.id.textView);
        final EditText password = (EditText)findViewById(R.id.editText);

        Button key1 =(Button)findViewById(R.id.key1) ;
        key1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append("1");
            }
        } );

        Button key2 =(Button)findViewById(R.id.key2) ;
        key2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append("2");
            }
        } );

        Button key3 =(Button)findViewById(R.id.key3) ;
        key3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append("3");
            }
        } );

        Button key4 =(Button)findViewById(R.id.key4) ;
        key4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append("4");
            }
        } );

        Button key5 =(Button)findViewById(R.id.key5) ;
        key5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append("5");
            }
        } );

        Button key6 =(Button)findViewById(R.id.key6) ;
        key6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append("6");
            }
        } );

        Button key7 =(Button)findViewById(R.id.key7) ;
        key7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append("7");
            }
        } );

        Button key8 =(Button)findViewById(R.id.key8) ;
        key8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append("8");
            }
        } );

        Button key9 =(Button)findViewById(R.id.key9) ;
        key9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append("9");
            }
        } );

        Button key0 =(Button)findViewById(R.id.key0) ;
        key0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append("0");
            }
        } );

        Button clear =(Button)findViewById(R.id.keyClear) ;
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setText(null);
            }
        } );

        Button backSpace =(Button)findViewById(R.id.backSpace) ;
        backSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordStr = password.getText().toString();
                if (passwordStr.length() > 0) {
                    String newPasswordStr = new StringBuilder(passwordStr)
                            .deleteCharAt(passwordStr.length() - 1).toString();
                    password.setText(newPasswordStr);
                }

            }
        } );

        Button submit =(Button)findViewById(R.id.submit) ;
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                if(password.getText().toString().equals("0000")) {
                    Intent intent = new Intent(MainActivity.this, ServicesActivity.class);
                    startActivity(intent);
                }
                else
                {

                }

            }
        } );

    }
}

