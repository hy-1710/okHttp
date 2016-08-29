package com.hfad.joke.okhttp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "MainActivity";
    TextView tv;
    ImageView iv;
    Context context;
    SharedPreferences sharedpreferences;


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.hellostring);
        iv = (ImageView) findViewById(R.id.imageView1);
        Picasso.with(this).load("http://square.github.io/okhttp/static/logo-square.png").into(iv);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_APPEND);

        ServerCall s = new ServerCall();
        s.execute();
//

    }

    class ServerCall extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://raw.githubusercontent.com/square/okhttp/master/samples/guide/src/main/java/okhttp3/guide/GetExample.java")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
                // Log.i(TAG, "onCreate: " +result);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv.setText(s);

            Log.i(TAG, "onPostExecute: " + s);
        }
    }
}






