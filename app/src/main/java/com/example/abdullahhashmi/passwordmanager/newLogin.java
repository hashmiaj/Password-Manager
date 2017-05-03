package com.example.abdullahhashmi.passwordmanager;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class newLogin extends AppCompatActivity
{
    private Context context;
    private DBHelper dbHandler;
    private Crypter crypter;
    private SQLiteDatabase database;
    private SharedPreferences data;
    private SharedPreferences.Editor editData;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        context = newLogin.this;
        dbHandler = new DBHelper(context);
        data = getSharedPreferences("allValues", Context.MODE_PRIVATE);
        editData = data.edit();

        final String master = data.getString("masterPass", "");

        setMasterPass(master);


        crypter = new Crypter(master + "ABCDEFGHIJKLMNOPQRST");

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

                String passEntered = password.getText().toString();

                if(passEntered.length() >= 6) {

                    if (passEntered.equals(master)) {
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.set_new_login);
                        final EditText login = (EditText)dialog.findViewById(R.id.editText);
                        Button submitBtn = (Button)dialog.findViewById(R.id.submit);
                        dialog.show();

                        submitBtn.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                String newLogin = login.getText().toString();
                                setMasterPass(newLogin);
                                editData.putString("masterPass", newLogin);
                                editData.commit();
                                Intent intent = new Intent(newLogin.this, MainActivity.class);

                                startActivity(intent);
                            }
                        });


                    } else {
                        Toast.makeText(context, "Wrong login! Try again.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "Login must be at least 6 numbers!", Toast.LENGTH_SHORT).show();
                }
            }
        } );

    }


    private String getMasterPass() {
        DBHelper dbHandler = new DBHelper(newLogin.this);
        SQLiteDatabase database = dbHandler.openDB(false);
        String masterPass = "";
        String password = "";
        try {
            Cursor x = database.query("APP_SETTINGS",new String[] {"MASTERPASS"},null,null,null,null,null);
            if(x.moveToFirst()) {
                masterPass = x.getString(0);
            }
            else {
                masterPass = null;
            }
            x.close();
        } catch (SQLiteException e) {
            Toast.makeText(newLogin.this, "Error getting masterPass" , Toast.LENGTH_SHORT).show();
        }
        dbHandler.closeDB();

        try
        {
            password = crypter.decrypt(masterPass);
        }
        catch(Exception e)
        {
            Toast.makeText(newLogin.this, "Error decrypting" , Toast.LENGTH_SHORT).show();
        }

        return password;
    }

    public boolean setMasterPass(String password)
    {
        Crypter crypter;
        Context context = this;
        long success = -1;

        if(password == null)
        {
            password = "000000ABCDEFGHIJKLMNOPQRST";
            crypter = new Crypter(password);
            try {
                ContentValues pushData = new ContentValues();
                pushData.put("MASTERPASS", crypter.encrypt(password));
                database = dbHandler.openDB(true);
                try
                {
                    success = database.insertOrThrow("APP_SETTINGS", null, pushData);
                }
                catch(SQLiteException e)
                {
                    for(int i = 0; i < 100; i++) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
                dbHandler.closeDB();

            }
            catch (Exception e)
            {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            password = password + "ABCDEFGHIJKLMNOPQRST";
            crypter = new Crypter(password);
            try {
                ContentValues pushData = new ContentValues();
                pushData.put("MASTERPASS", crypter.encrypt(password));
                database = dbHandler.openDB(true);
                try
                {
                    success = database.insertOrThrow("APP_SETTINGS", null, pushData);
                }
                catch(SQLiteException e)
                {
                    for(int i = 0; i < 100; i++) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
                dbHandler.closeDB();

            }
            catch (Exception e)
            {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            }
        }

        return success != -1;
    }

}
