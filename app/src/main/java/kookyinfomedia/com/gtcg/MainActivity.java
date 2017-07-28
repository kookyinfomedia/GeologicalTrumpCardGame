///////////// Splash Screen Code /////////////

package kookyinfomedia.com.gtcg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import java.util.Timer;
import java.util.TimerTask;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "";
    Timer timer;
    TimerTask timerTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        // To show activity in full screen.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        logUser();//To be removed after testing the crash

        setContentView(R.layout.activity_main);

        // Logic for splash screen. To make the screen wait for some time.
        initialize();
    }
    private void logUser() {
        // TODO: Use the current user's information
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("user@fabric.io");
        Crashlytics.setUserName("Test User");
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
