package kookyinfomedia.com.gtcg;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static kookyinfomedia.com.gtcg.Category.selectedContinent;

public class Toss extends AppCompatActivity {
    int res;
    Timer timer = new Timer();
    TimerTask timerTask;
    MediaPlayer spinSound,clicksound;
    private int flag;
    private boolean mIsBound = false;
    public static int toss;
    private Animation animation1;
    private Animation animation2;
    int i = 0;
    private boolean isBackOfCardShowing = true;

    private MusicService mServ;
    private ServiceConnection Scon = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ=((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    public Toss() {
    }

    void doBindService() {
        bindService(new Intent(this, MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
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
        clicksound= MediaPlayer.create(this, R.raw.clicksound);
        Random ran = new Random();
        res = ran.nextInt(2);//// will give 0 or 1 and chooses whether the turn would be of first player's or second's
        spinSound = new MediaPlayer();
        flag = getIntent().getIntExtra("int_value", 0);
        if (flag == 1) {
            stopMusic();
        } else {
            startMusic();
        }
        ImageView coin=(ImageView)findViewById(R.id.imgCoinFront);
        coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinSound = MediaPlayer.create(Toss.this,R.raw.spin);
                spinSound.setLooping(true);
                spinSound.start();
                spinSound.setVolume(0.2f,0.2f);
                view.setOnClickListener(null);
                animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_middle);
                animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_middle);
                new CountDownTimer(500,100){
                    public void onTick(long ms){

                    }
                    public void onFinish(){
                        findViewById(R.id.imgCoinFront).clearAnimation();
                        findViewById(R.id.imgCoinFront).setAnimation(animation1);
                        findViewById(R.id.imgCoinFront).startAnimation(animation1);
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
                                    findViewById(R.id.imgCoinFront).clearAnimation();
                                    findViewById(R.id.imgCoinFront).setAnimation(animation2);
                                    findViewById(R.id.imgCoinFront).startAnimation(animation2);
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
                                                    Animation animation3 = AnimationUtils.loadAnimation(Toss.this, R.anim.to_middle);
                                                    ((ImageView) findViewById(R.id.imgCoinFront)).setImageResource(R.drawable.coin_front);
                                                    ((ImageView) findViewById(R.id.imgCoinFront)).clearAnimation();
                                                    ((ImageView) findViewById(R.id.imgCoinFront)).setAnimation(animation3);
                                                    ((ImageView) findViewById(R.id.imgCoinFront)).startAnimation(animation3);
							                        animation3.setAnimationListener(new Animation.AnimationListener() {
                                                         @Override
                                                         public void onAnimationStart(Animation animation) {
                                                         }
                                                         @Override
                                                         public void onAnimationEnd(Animation animation) {
                                                             Animation animation4 = AnimationUtils.loadAnimation(Toss.this, R.anim.from_middle);
                                                             ((ImageView) findViewById(R.id.imgCoinFront)).setImageResource(R.drawable.coin_back);
                                                             ((ImageView) findViewById(R.id.imgCoinFront)).clearAnimation();
                                                             ((ImageView) findViewById(R.id.imgCoinFront)).setAnimation(animation4);
                                                             ((ImageView) findViewById(R.id.imgCoinFront)).startAnimation(animation4);
                                                         }
                                                         @Override
                                                         public void onAnimationRepeat(Animation animation) {
                                                         }
                                                     });
                                                }
                                                else{  // your turn
                                                    Animation animation3 = AnimationUtils.loadAnimation(Toss.this, R.anim.to_middle);
                                                    ((ImageView) findViewById(R.id.imgCoinFront)).setImageResource							(R.drawable.coin_front);
							findViewById(R.id.imgCoinFront).clearAnimation();
							findViewById(R.id.imgCoinFront).setAnimation(animation3);
                                                     findViewById(R.id.imgCoinFront).startAnimation(animation3);
                                                     animation3.setAnimationListener(new Animation.AnimationListener() {
                                                         @Override
                                                         public void onAnimationStart(Animation animation) {
                                                         }
                                                         @Override
                                                         public void onAnimationEnd(Animation animation) {
                                                             Animation animation4 = AnimationUtils.loadAnimation(Toss.this, R.anim.from_middle);
                                                             ((ImageView) findViewById(R.id.imgCoinFront)).setImageResource(R.drawable.coin_front);
                                                             findViewById(R.id.imgCoinFront).clearAnimation();
                                                             findViewById(R.id.imgCoinFront).setAnimation(animation4);
                                                             findViewById(R.id.imgCoinFront).startAnimation(animation4);
                                                         }
                                                         @Override
                                                         public void onAnimationRepeat(Animation animation) {
                                                         }
                                                     });
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
        });

    }

    public void initialize() {
        spinSound.stop();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(selectedContinent.equalsIgnoreCase("india")) {
			Intent intent = new Intent(Toss.this, GamePlayIndia.class);
			intent.putExtra("int_value", flag);
			startActivity(intent);
			doUnbindService();
			stopMusic();
			finish();
		}
                else {
                    Intent intent = new Intent(Toss.this, GamePlay.class);
                    intent.putExtra("int_value", flag);
                    startActivity(intent);
                    doUnbindService();
                    stopMusic();
                    finish();
                }
            }
        };

        timer.schedule(timerTask, 3500);
    }

    @Override
    public void onBackPressed() {
        clicksound.start();
        Intent intent = new Intent(this, Options.class);
        intent.putExtra("int_value", flag);
        startActivity(intent);
        spinSound.stop();
        timer.cancel();
        finish();
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
    @Override
    public void onStop() {
        super.onStop();
        doUnbindService();
        spinSound.stop();
        stopMusic();
    }

    @Override
    public void onResume() {
        super.onResume();
        doBindService();
        if (flag == 1) {
            stopMusic();
        } else {
            startMusic();
        }
    }

    public void startMusic() {
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);

    }

    public void stopMusic() {
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        stopService(music);

    }


}

