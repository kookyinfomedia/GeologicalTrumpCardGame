package kookyinfomedia.com.gtcg;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Toss extends AppCompatActivity {
    private SensorManager sm;
    int res;
    private static final String TAG="";
    Timer timer = new Timer();
    TimerTask timerTask;
    MediaPlayer spinSound;
    private int flag;
    private boolean mIsBound = false;
    public static int toss;
    private MusicService mServ;
    private Animation animation1;
    private Animation animation2;
    private  float acelVal,acelLast,shake;
    int i=0;
    private boolean isBackOfCardShowing = true;

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
        setContentView(R.layout.activity_toss);
        doBindService();
        Random ran = new Random();
        res = ran.nextInt(2);//// will give 0 or 1 and chooses whether the turn would be of first player's or second's
        spinSound=new MediaPlayer();
        flag= getIntent().getIntExtra("int_value", 0);


        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

        acelVal =SensorManager.GRAVITY_EARTH;
        acelLast =SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
        if(flag==1)
        {
            stopMusic();
        }
        else{
            startMusic();
        }


    }
    public void initialize()
    {
        spinSound.stop();
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(Toss.this,LoadingScreen.class);
                intent.putExtra("int_value",flag);
                startActivity(intent);
                doUnbindService();
                stopMusic();
                finish();
            }
        };

        timer.schedule(timerTask,3500);
    }
    @Override
    public void onBackPressed(){
        Intent intent=new Intent(this,Options.class);
        intent.putExtra("int_value",flag);
        startActivity(intent);
        spinSound.stop();
        timer.cancel();
        finish();
    }
    @Override
    public void onStop(){
        super.onStop();
        doUnbindService();
        spinSound.stop();
        stopMusic();
    }
    @Override
    public void onResume(){
        super.onResume();
        doBindService();
        if(flag==1)
        {
            spinSound.stop();
            stopMusic();
        }
        else{
            spinSound.start();
            startMusic();
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
    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            acelLast=acelVal;
            acelVal=(float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelVal - acelLast;
            shake = shake * 0.980f + delta;

            //on Device shake
            if(shake > 3  ) {
                spinSound = MediaPlayer.create(Toss.this,R.raw.spin);
                spinSound.setLooping(true);
                spinSound.start();
                spinSound.setVolume(0,0.1f);
                sm.unregisterListener(sensorListener);

                animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_middle);
                animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_middle);
                new CountDownTimer(500,100){
                    public void onTick(long ms){

                    }
                    public void onFinish(){
                        ((ImageView)findViewById(R.id.imgCoinFront)).clearAnimation();
                        ((ImageView)findViewById(R.id.imgCoinFront)).setAnimation(animation1);
                        ((ImageView)findViewById(R.id.imgCoinFront)).startAnimation(animation1);
                        animation1.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                i++;
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (animation == animation1) {
                                    if (isBackOfCardShowing) {
                                        ((ImageView) findViewById(R.id.imgCoinFront)).setImageResource(R.drawable.coin_front);
                                    } else {
                                        ((ImageView) findViewById(R.id.imgCoinFront)).setImageResource(R.drawable.coin_back);
                                    }
                                    ((ImageView) findViewById(R.id.imgCoinFront)).clearAnimation();
                                    ((ImageView) findViewById(R.id.imgCoinFront)).setAnimation(animation2);
                                    ((ImageView) findViewById(R.id.imgCoinFront)).startAnimation(animation2);
                                    animation2.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            isBackOfCardShowing = !isBackOfCardShowing;
                                            ((ImageView)findViewById(R.id.imgCoinFront)).clearAnimation();
                                            ((ImageView)findViewById(R.id.imgCoinFront)).setAnimation(animation1);
                                            ((ImageView)findViewById(R.id.imgCoinFront)).startAnimation(animation1);
                                            if(i>10) {
                                                if(res==0) { // opponents turn
                                                    Animation animation3 = AnimationUtils.loadAnimation(Toss.this, R.anim.from_middle);
                                                    ((ImageView) findViewById(R.id.imgCoinFront)).setImageResource(R.drawable.coin_back);
                                                    ((ImageView) findViewById(R.id.imgCoinFront)).clearAnimation();
                                                    ((ImageView) findViewById(R.id.imgCoinFront)).setAnimation(animation3);
                                                    ((ImageView) findViewById(R.id.imgCoinFront)).startAnimation(animation3);
                                                }
                                                else{  // your turn
                                                    Animation animation3 = AnimationUtils.loadAnimation(Toss.this, R.anim.to_middle);
                                                    ((ImageView) findViewById(R.id.imgCoinFront)).clearAnimation();
                                                    ((ImageView) findViewById(R.id.imgCoinFront)).setAnimation(animation3);
                                                    ((ImageView) findViewById(R.id.imgCoinFront)).startAnimation(animation3);
                                                }
                                                initialize();
                                                spinSound.setLooping(false);
                                                spinSound.reset();
                                                toss=res;
                                            }
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });
                                } else {
                                    isBackOfCardShowing = !isBackOfCardShowing;
                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                }.start();
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    };
    public void next(View v){
        Intent intent=new Intent(Toss.this,LoadingScreen.class);
        startActivity(intent);
        finish();
    }
}

