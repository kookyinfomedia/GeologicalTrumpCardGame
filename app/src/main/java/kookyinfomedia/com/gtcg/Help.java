package kookyinfomedia.com.gtcg;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Help extends AppCompatActivity {
    private boolean mIsBound = false;
    public MusicService mServ;
    public int flag=0;
    MediaPlayer clicksound;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_help);
        clicksound= MediaPlayer.create(this, R.raw.clicksound);
        flag= getIntent().getIntExtra("int_value", 0);
        if(flag==1)
        {
            stopMusic();
        }
        else{
            startMusic();
        }
    }
    @Override
    public void onBackPressed(){
        clicksound.start();
        Intent intent=new Intent(Help.this,Options.class);
        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();
    }
    @Override
    public void onResume(){
        super.onResume();
        doBindService();
        if(flag==1)
        {
            stopMusic();
        }
        else{
            startMusic();
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        doUnbindService();
        stopMusic();
    }

    @Override
    public  void onPause()
    {
        super.onPause();
        // If the screen is off then the device has been locked
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        boolean isScreenOn = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
            isScreenOn = powerManager.isInteractive();
        }else{
            isScreenOn = powerManager.isScreenOn();
        }
        if (!isScreenOn) {
            doUnbindService();
            stopMusic();
        }
    }
    public void backHelp(View view){
        clicksound.start();
        Intent intent=new Intent(Help.this,Options.class);
        startActivity(intent);
        finish();
    }

    public void soundOpt(View view)
    {
        Button speaker =(Button) view;
        clicksound.start();
        if(flag==0)
        {
            speaker.setBackgroundResource(R.drawable.soundoff);
            mServ.pauseMusic();
            flag=1;
        }else{
            speaker.setBackgroundResource(R.drawable.soundon);
            mServ.resumeMusic();
            flag=0;
        }
    }
    public void startMusic(){
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        startService(music);
    }
    public void stopMusic(){
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);

    }
}