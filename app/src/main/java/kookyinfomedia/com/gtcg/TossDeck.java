package kookyinfomedia.com.gtcg;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



/****************************************** Screen for tossing the coin ************************************************/

public class TossDeck extends AppCompatActivity {
    int x;
    CardView cardCoin2,cardCoin;
    private static final String TAG="";
    Animation myAnim,myAnim4;
    Timer timer = new Timer();
    TimerTask timerTask;
    MediaPlayer spinSound;
    private int flag;
    private boolean mIsBound = false;
    private MusicService mServ;


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
        setContentView(R.layout.activity_toss_deck);
        doBindService();
        Random ran = new Random();
        x = ran.nextInt(2);//// will give 0 or 1 and chooses whether the turn would be of first player's or second's

        cardCoin=(CardView)findViewById(R.id.Score);
        cardCoin2=(CardView)findViewById(R.id.cardCoin2);

        myAnim =AnimationUtils.loadAnimation(this,R.anim.coin_rotate);
        myAnim4=AnimationUtils.loadAnimation(this,R.anim.coin_rotate);
        myAnim.setStartOffset(100);
        myAnim.reset();
        cardCoin.setVisibility(View.INVISIBLE);
        cardCoin2.startAnimation(myAnim);
        spinSound = MediaPlayer.create(this,R.raw.spin);
        spinSound.setLooping(true);
        spinSound.setVolume(0.2f,0.2f);
        flag= getIntent().getIntExtra("int_value", 0);
        if(flag==1)
        {
            stopMusic();
        }
        else{
            startMusic();
        }

        myAnim.setAnimationListener(new Animation.AnimationListener() {
            int i=0;
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                final Animation myAnim2=AnimationUtils.loadAnimation(TossDeck.this,R.anim.coin_rotate);
                i++;
                if(i%4==0) {
                    cardCoin.setVisibility(View.INVISIBLE);
                    cardCoin2.setVisibility(View.VISIBLE);
                    cardCoin2.startAnimation(myAnim);


                }
                else{
                    cardCoin2.setVisibility(View.INVISIBLE);
                    cardCoin.setVisibility(View.VISIBLE);
                    cardCoin.startAnimation(myAnim4);
                }

                myAnim4.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if(i==3) {

                            if(x==0) {
                                cardCoin.setVisibility(View.INVISIBLE);
                                cardCoin2.setVisibility(View.VISIBLE);
                                cardCoin2.startAnimation(myAnim2);
                            }
                            else{
                                cardCoin2.setVisibility(View.INVISIBLE);
                                cardCoin.setVisibility(View.VISIBLE);
                                cardCoin.startAnimation(myAnim2);
                            }
                            myAnim2.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {

                                    initialize();

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });

                        }
                        else {
                            cardCoin.setVisibility(View.INVISIBLE);
                            cardCoin2.setVisibility(View.VISIBLE);
                            cardCoin2.startAnimation(myAnim);
                        }

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    public void initialize()
    {
        spinSound.stop();
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(TossDeck.this,LoadingScreen.class);
                startActivity(intent);
                doUnbindService();
                stopMusic();
                finish();
            }
        };

        timer.schedule(timerTask,2500);
    }
    @Override
    public void onBackPressed(){
        Intent intent=new Intent(this,Options.class);
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


}
