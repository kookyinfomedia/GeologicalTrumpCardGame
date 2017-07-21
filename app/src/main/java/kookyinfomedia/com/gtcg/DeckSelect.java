package kookyinfomedia.com.gtcg;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

/*************************************** DECK SELECTION PAGE ********************************************/

/// Setting the static variable "deck"  to be used in the Play class for setting initial score and cards.


public class DeckSelect extends AppCompatActivity {
    private static final String TAG ="" ;
    int flag=0;
    public static int deck;
    Button deck16,deck32,deck52;
    //------------------------------------------Service Binding------------------------------------//
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
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBindService();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_deck_select);
        playMenuAnimation();
        deck16=(Button)findViewById(R.id.deck16);
        deck32=(Button)findViewById(R.id.deck32);
        deck52=(Button)findViewById(R.id.deck52);
        Button speaker =(Button) findViewById(R.id.sound);
        flag= getIntent().getIntExtra("int_value", 0);
        if(flag==1)
        {
            speaker.setBackgroundResource(R.drawable.soundoff);
            stopMusic();
        }
        else{
            speaker.setBackgroundResource(R.drawable.soundon);
            startMusic();
        }
    }
    public void soundDeck(View view)
    {
        Button speaker =(Button) view;

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
    public void back(View view){
        Intent intent = new Intent(DeckSelect.this,Options.class);
        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed(){
        Intent intent=new Intent(this,Options.class);
        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();
    }




    public void deckSelected16(View v){
        deck16.setAlpha(0.4f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(

                ObjectAnimator.ofFloat(deck16, "scaleX", 1f, 0.5f),
                ObjectAnimator.ofFloat(deck16, "scaleY", 1f, 0.5f)
        );
        set.start();
        new CountDownTimer(400,100){
            public void onTick(long ms){

            }
            public void onFinish(){
                Intent intent = new Intent(DeckSelect.this,Toss.class);
                deck=16;
                intent.putExtra("int_value",flag);
                startActivity(intent);
                finish();
            }
        }.start();

    }
    public void deckSelected32(View v){
        deck32.setAlpha(0.4f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(

                ObjectAnimator.ofFloat(deck32, "scaleX", 1f, 0.5f),
                ObjectAnimator.ofFloat(deck32, "scaleY", 1f, 0.5f)
        );
        set.start();
        new CountDownTimer(400,100){
            public void onTick(long ms){

            }
            public void onFinish(){
                Intent intent = new Intent(DeckSelect.this,Toss.class);
                deck=32;
                intent.putExtra("int_value",flag);
                startActivity(intent);
                finish();
            }
        }.start();

    }
    public void deckSelected52(View v){
        deck52.setAlpha(0.4f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(

                ObjectAnimator.ofFloat(deck52, "scaleX", 1f, 0.5f),
                ObjectAnimator.ofFloat(deck52, "scaleY", 1f, 0.5f)
        );
        set.start();
        new CountDownTimer(400,100){
            public void onTick(long ms){

            }
            public void onFinish(){
                Intent intent = new Intent(DeckSelect.this,Toss.class);
                deck=52;
                intent.putExtra("int_value",flag);
                startActivity(intent);
                finish();
            }
        }.start();
    }
    @Override
    public void onResume(){
        super.onResume();
        doBindService();
        playMenuAnimation();
        Button speaker =(Button) findViewById(R.id.sound);
        if(flag==1)
        {
            speaker.setBackgroundResource(R.drawable.soundoff);
            stopMusic();
        }
        else{
            speaker.setBackgroundResource(R.drawable.soundon);
            startMusic();
        }

    }

    @Override
    public void onStop(){
        super.onStop();
        doUnbindService();
        stopMusic();
    }


    private void playMenuAnimation() {
        Button btnBack = (Button)findViewById(R.id.back);
        final Button btnSound = (Button)findViewById(R.id.sound);
        Button btnDeck16 = (Button)findViewById(R.id.deck16);
        Button btnDeck32 = (Button)findViewById(R.id.deck32);
        Button btnDeck52 = (Button)findViewById(R.id.deck52);
        RelativeLayout rel=(RelativeLayout)findViewById(R.id.rel);

        // relative layout bounce
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.3, 20);
        myAnim.setInterpolator(interpolator);
        rel.startAnimation(myAnim);

        //  Deck 16 bounce
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(1000);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);
        btnDeck16.startAnimation(myAnim);

        //  Deck 32 bounce
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        btnDeck32.startAnimation(myAnim);
        myAnim.setStartOffset(1100);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);

        // Deck 52 bounce
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        btnDeck52.startAnimation(myAnim);
        myAnim.setStartOffset(1200);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);

        // Back button bounce
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        btnBack.startAnimation(myAnim);
        myAnim.setStartOffset(1400);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);

        // Sound Button bounce
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        btnSound.startAnimation(myAnim);
        myAnim.setStartOffset(1400);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);

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
