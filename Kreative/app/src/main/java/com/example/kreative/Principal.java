package com.example.kreative;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.login.LoginManager;

public class Principal extends AppCompatActivity {
private ProgressBar mProgressbar;
private TextView MLoading;
private int mProgressStatus= 0;
private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    mProgressbar=findViewById(R.id.progressBar);
            MLoading= findViewById(R.id.loading);

        new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mProgressStatus < 100){
                        mProgressStatus++;
                                android.os.SystemClock.sleep(50);
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgressbar.setProgress(mProgressStatus);

                                    }
                                });
                    }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        MLoading.setVisibility(View.VISIBLE);
                        Intent i= new Intent(getApplicationContext(), MainActivity2.class);
                        getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    }
                });
                }
            }).start();

    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Deseas salir?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

                        LoginManager.getInstance().logOut();
                        finish(); System.exit(0);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

}