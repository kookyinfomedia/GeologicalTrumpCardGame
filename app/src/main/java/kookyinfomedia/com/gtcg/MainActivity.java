//Splash Screen Code

package kookyinfomedia.com.gtcg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.Libraries.CBLogging;
import com.crashlytics.android.Crashlytics;

import java.util.Timer;
import java.util.TimerTask;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
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
        //Chartboost.startWithAppId(this,"588b1cf1f6cd4501525f3d12","2c46739a486f6196854ecdd781eaef425ce72ce9");
        //Chartboost.onCreate(this);
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
    public void onStart() {
        super.onStart();
        //Chartboost.onStart(this);
       // Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Chartboost.onResume(this);
    }
    @Override
    public void onBackPressed(){
        //if (Chartboost.onBackPressed())
          //  return;
       // else {
            super.onBackPressed();
            timer.cancel();
            finish();
       // }
    }
    @Override
    public void onStop(){
        super.onStop();
        //Chartboost.onStop(this);
        timer.cancel();
        finish();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        //Chartboost.onDestroy(this);
        timer.cancel();
        finish();
    }
    @Override
    public void onPause(){
        super.onPause();
        //Chartboost.onPause(this);
        timer.cancel();
        finish();
    }
}
