package kookyinfomedia.com.gtcg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class LoadingScreen extends AppCompatActivity {
    Timer timer;
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
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
                Intent homeIntent = new Intent(LoadingScreen.this,Play.class);
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
        Intent intent= new Intent(this,Options.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onStop(){
        super.onStop();
        timer.cancel();
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

    }

}
