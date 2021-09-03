package com.mobdeve.s17.songlyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView tv = (TextView)findViewById(R.id.Log);
        TextView fullname = (TextView) findViewById(R.id.fullName);
        TextView birthday = (TextView) findViewById(R.id.Birthday);
        TextView email = (TextView) findViewById(R.id.EmailRegis);
        TextView password = (TextView) findViewById(R.id.PasswordRegis);
        Button regis = (Button) findViewById(R.id.RegisButton);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Text = fullname.getText().toString();
                String Text2 = birthday.getText().toString();
                String Text3 = email.getText().toString();
                String Text4  = password.getText().toString();
                if(Text.isEmpty() && Text2.isEmpty() && Text3.isEmpty() && Text4.isEmpty()){
                    alert("Please input on the missing fields.");
                }else{
                    alert("Account successfully created!");
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    private void alert(String message){
        AlertDialog dialog = new AlertDialog.Builder(Register.this)
                .setTitle("Message")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }
}
