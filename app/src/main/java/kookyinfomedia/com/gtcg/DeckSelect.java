package kookyinfomedia.com.gtcg;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static kookyinfomedia.com.gtcg.Category.deck;

//Setting the static variable "deck"  to be used in the Loading class for setting initial score and cards.


public class DeckSelect extends AppCompatActivity {
    int flag=0;
    MediaPlayer clicksound;
    ImageView btnDeck;
    ImageButton speaker,btnBack;
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
        btnDeck = (ImageView)findViewById(R.id.deckCard);
        clicksound= MediaPlayer.create(this, R.raw.clicksound);
        speaker =(ImageButton) findViewById(R.id.sound);
        btnBack = (ImageButton)findViewById(R.id.back);
        TextView txt1=(TextView)findViewById(R.id.txt1);
        txt1.setText(""+deck);
        playMenuAnimation();

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
    public void back(View view){
        clicksound.start();
        Intent intent = new Intent(DeckSelect.this,Category.class);
        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed(){
        clicksound.start();
        Intent intent=new Intent(this,Category.class);
        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();
    }

public void clickOff(){
    btnDeck.setClickable(false);
}


    public void deckSelected(View v){
        clicksound.start();
        clickOff();
        RelativeLayout rel=(RelativeLayout)findViewById(R.id.rel);
        rel.setAlpha(0.4f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(

                ObjectAnimator.ofFloat(rel, "scaleX", 1f, 0.8f),
                ObjectAnimator.ofFloat(rel, "scaleY", 1f, 0.8f)
        );
        set.start();
        Intent intent = new Intent(DeckSelect.this,Toss.class);

        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();

    }

    @Override
    public void onResume(){
        super.onResume();
        doBindService();
        playMenuAnimation();
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
    @Override
    public  void onPause()
    {
        super.onPause();
        // If the screen is off then the device has been locked
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        boolean isScreenOn=false;
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

    private void playMenuAnimation() {

        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.3, 20);
        myAnim.setInterpolator(interpolator);


        // Deck bounce
        RelativeLayout rel=(RelativeLayout)findViewById(R.id.rel);
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        rel.startAnimation(myAnim);
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
        speaker.startAnimation(myAnim);
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
