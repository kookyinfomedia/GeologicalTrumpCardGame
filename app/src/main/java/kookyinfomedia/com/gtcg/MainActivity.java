package kookyinfomedia.com.gtcg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "";
    Timer timer;
    TimerTask timerTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize()
    {
        timer = new Timer();
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                Intent homeIntent = new Intent(MainActivity.this,Options.class);
                startActivity(homeIntent);
                finish();
            }
        };

        timer.schedule(timerTask,2500);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        timer.cancel();
        finish();
    }
    @Override
    public void onStop(){
        super.onStop();
        timer.cancel();
        finish();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        timer.cancel();
        finish();
    }
    @Override
    public void onPause(){
        super.onPause();
        timer.cancel();
        finish();
    }
}
