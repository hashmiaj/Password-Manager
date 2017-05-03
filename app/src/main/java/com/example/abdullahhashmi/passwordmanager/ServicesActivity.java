package com.example.abdullahhashmi.passwordmanager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ServicesActivity extends AppCompatActivity {

    private static List<Records> records = new ArrayList<>();
    private DBHelper dbHandler;
    private Crypter crypter;
    private Context context;
    private TableLayout table;
    private String masterPass = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        context = ServicesActivity.this;
        dbHandler = new DBHelper(context);
        crypter = new Crypter(masterPass);
        table = (TableLayout)findViewById(R.id.tableLayout);

        Button add = (Button)findViewById(R.id.addBtn);

        loadData();
        displayData();

        add.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //context.deleteDatabase("DATABASE");
                popup();

            }
        });
    }

    public void displayData()
    {
        if(records.size() > 0)
        {
            for(Records rec : records)
            {
                TableRow row = new TableRow(this);

                String password = "";
                final TextView nTitle, nUsername, nPassword;
                Button show, delete;
                nTitle = new TextView(this);
                nUsername = new TextView(this);
                nPassword = new TextView(this);
                show = new Button(this);
                delete = new Button(this);



                nTitle.setText(rec.getTitle().toString());
                final String id = rec.getId().toString();
                List<RecordData> recs = rec.getRecordData();
                for(RecordData data : recs)
                {
                    nUsername.setText(data.getUsername().toString());
                    password = data.getPassword().toString();
                    nPassword.setText(password);
                }
                show.setText("Show");
                delete.setText("Delete");

                final String pass = password;

                View view_instance = (View)findViewById(R.id.addBtn);
                ViewGroup.LayoutParams params = view_instance.getLayoutParams();
                show.setLayoutParams(params);

                row.addView(nTitle);
                row.addView(nUsername);
                row.addView(nPassword);
                //row.addView(show);
                row.addView(delete);




                /*
                show.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_UP) {
                            nPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            nPassword.setText(pass);
                            return true;
                        }
                        else {
                            nPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            nPassword.setText(pass);
                            return true;
                        }
                    }
                });
                */

                final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                if(deleteRecord(Integer.parseInt(id)))
                                {
                                    Toast.makeText(context, "Deleted service" , Toast.LENGTH_SHORT).show();
                                    loadData();
                                    finish();
                                    startActivity(getIntent());
                                }
                                else
                                {
                                    Toast.makeText(context, "Error deleting service" , Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                                finish();
                                startActivity(getIntent());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                delete.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("Cancel", dialogClickListener).show();
                    }
                });



                table.addView(row);

            }
        }
    }


    private void popup()
    {
        final SetService setFunction = new SetService(context, dbHandler, crypter);
        final EditText title,username,password;
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.set_service);
        Button addButton = (Button)dialog.findViewById(R.id.addButton);
        Button showPassword = (Button)dialog.findViewById(R.id.showBtn);
        Button cancel = (Button)dialog.findViewById(R.id.cancelBtn);
        dialog.show();

        title = (EditText)dialog.findViewById(R.id.set_title);
        username = (EditText)dialog.findViewById(R.id.set_username);
        password = (EditText)dialog.findViewById(R.id.set_password);


        addButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(setFunction.isEmpty(title.getText().toString()))
                {
                    Toast.makeText(context, "Enter title", Toast.LENGTH_SHORT).show();
                }
                else if(setFunction.isEmpty(username.getText().toString()))
                {
                    Toast.makeText(context, "Enter username", Toast.LENGTH_SHORT).show();
                }
                else if(setFunction.isEmpty(password.getText().toString()))
                {
                    Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String [] recordContent = {title.getText().toString(), username.getText().toString(), password.getText().toString()};
                    if(setFunction.addService(recordContent))
                    {
                        Toast.makeText(context, "Service added successfully" , Toast.LENGTH_SHORT).show();
                        loadData();
                        finish();
                        startActivity(getIntent());
                    }
                    else
                    {
                        Toast.makeText(context, "Error adding service" , Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        showPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    return true;
                }
                else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    return true;
                }
            }
        });




        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private boolean deleteRecord(int id)
    {
        long success = -1;
        try {
            SQLiteDatabase database = dbHandler.openDB(true);
            success = database.delete("RECORDS", "ID=" + id, null);
        } catch (SQLiteException e) {}
        dbHandler.closeDB();
        return success == 1;
    }



    private void loadData() {
        records.clear();
        ArrayList<List<String>> recs = new ArrayList<>();
        String adder[] = new String[4];
        SQLiteDatabase db = dbHandler.openDB(false);
        int j;
        try {
            Cursor x = db.query("RECORDS", new String[]{"ID", "TITLE", "USERNAME", "PASSWORD"}, null, null, null, null, null);
            if(x.moveToFirst()) {
                do {
                    recs.add(Arrays.asList(x.getString(0), x.getString(1), x.getString(2), x.getString(3)));
                } while(x.moveToNext());
                x.close();
                for (List<String> list : recs) {
                    j = 0;
                    for (String i : list) {
                        adder[j] = i;
                        j++;
                    }
                    try {
                        Records ser = createService(crypter.decrypt(adder[1]), adder[0]);
                        ser.setRecordData(createRecords(crypter.decrypt(adder[2]), crypter.decrypt(adder[3])));
                        records.add(ser);
                    } catch (InvalidKeyException | NoSuchAlgorithmException
                            | NoSuchPaddingException
                            | IllegalBlockSizeException | BadPaddingException e1) {
                        Toast.makeText(context, "Decryption Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (SQLiteException e) {
            Toast.makeText(context, "Error accessing database" , Toast.LENGTH_SHORT).show();
            dbHandler.closeDB();
        }
        dbHandler.closeDB();
    }

    private Records createService(String title, String id) {
        return new Records(title, id);
    }

    private List<RecordData> createRecords(String username, String password) {
        List<RecordData> result = new ArrayList<>();
        RecordData item = new RecordData(username, password);
        result.add(item);
        return result;
    }

}
