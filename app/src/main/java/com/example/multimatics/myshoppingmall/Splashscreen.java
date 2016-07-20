package com.example.multimatics.myshoppingmall;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.Delayed;

public class Splashscreen extends AppCompatActivity {
    private AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        getSupportActionBar().hide();
        appPreferences = new AppPreferences(Splashscreen.this);

        new DelayAsync().execute();
    }

    class DelayAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            }catch (Exception e) {}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent intent = null;
            if (appPreferences.getUsername().equals("")){
                intent = new Intent(Splashscreen.this, LoginActivity.class);
            }else {
                intent = new Intent(Splashscreen.this,HomeActivity.class);
            }

            startActivity(intent);
            finish();
        }
    }


}
