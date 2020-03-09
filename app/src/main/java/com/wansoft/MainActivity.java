package com.wansoft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Time;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int time;
    private Timer timer;
    private TextView secondsView, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindElements();
        new TokenTask().execute("");
    }

    private void bindElements() {
        secondsView = findViewById(R.id.seconds);
        token = findViewById(R.id.token);
    }

    private void startTimer() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        secondsView.setText(" " + String.format(Locale.getDefault(), "%d", time) + " Segundos");
                        if (time > 0)
                            time -= 1;
                        else {
                            /**
                             * Execute asyncTask WS to reset de timer
                             */
                            timer.cancel();
                            timer.purge();
                            new TokenTask().execute("");
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public class TokenTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = java.util.UUID.randomUUID().toString();
            result.replaceAll("-", "");

            token.setText(result.substring(0, 8));

            time = 20;
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            startTimer();
        }
    }
}
