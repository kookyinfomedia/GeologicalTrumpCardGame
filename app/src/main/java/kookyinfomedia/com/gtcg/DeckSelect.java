package kookyinfomedia.com.gtcg;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
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
        Intent intent = new Intent(DeckSelect.this,TossDeck.class);
        deck=16;
        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();

    }
    public void deckSelected32(View v){
        Intent intent = new Intent(DeckSelect.this,TossDeck.class);
        deck=32;
        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();

    }
    public void deckSelected52(View v){
        Intent intent = new Intent(DeckSelect.this,TossDeck.class);
        deck=52;
        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();

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
        Button b1 = (Button)findViewById(R.id.play);
        final Button b2 = (Button)findViewById(R.id.sound);
        Button b3 = (Button)findViewById(R.id.deck16);
        Button b4 = (Button)findViewById(R.id.deck32);
        Button b5 = (Button)findViewById(R.id.deck52);
        RelativeLayout rel=(RelativeLayout)findViewById(R.id.rel);


        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.3, 15);
        myAnim.setInterpolator(interpolator);
        rel.startAnimation(myAnim);


        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(1000);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);
        b3.startAnimation(myAnim);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        b4.startAnimation(myAnim);
        myAnim.setStartOffset(1100);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        b5.startAnimation(myAnim);
        myAnim.setStartOffset(1200);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        b1.startAnimation(myAnim);
        myAnim.setStartOffset(1400);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        b2.startAnimation(myAnim);
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
