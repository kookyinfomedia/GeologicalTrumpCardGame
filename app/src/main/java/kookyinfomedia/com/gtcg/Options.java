package kookyinfomedia.com.gtcg;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;


public class Options extends AppCompatActivity{
    final Context context = this;
    public ImageView wave1,wave2,wave3,wave4,wave5,waveTitle;
    public Animation wave_anim, wave_anim_2, wave_anim_3, wave_anim_4, wave_anim_5,wave_anim_title;
    ImageView pisa,tower,liberty,taj,colosseum;
    public int flag=0;
    //Service Binding
    private boolean mIsBound = false;
    public MusicService mServ;
    ImageButton btnPlay,btnHelp,imgLoudspeaker;
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
    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Chartboost.startWithAppId(this,"588b1cf1f6cd4501525f3d12","2c46739a486f6196854ecdd781eaef425ce72ce9");
        //Chartboost.onCreate(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_options);
        clicksound= MediaPlayer.create(this, R.raw.clicksound);
        wave1 = (ImageView)findViewById(R.id.pisa);
        wave2 = (ImageView)findViewById(R.id.tower);
        wave3 = (ImageView)findViewById(R.id.taj);
        wave4 = (ImageView)findViewById(R.id.liberty);
        wave5 = (ImageView)findViewById(R.id.colosseum);
        waveTitle=(ImageView)findViewById(R.id.title);

        wave_anim = new TranslateAnimation(0, 0, 0, 9);
        wave_anim.setDuration(850);
        wave_anim.setFillAfter(true);
        wave_anim.setRepeatCount(-1);
        wave_anim.setRepeatMode(Animation.REVERSE);

        wave_anim_2 = new TranslateAnimation(0, 0, 0, 8);
        wave_anim_2.setDuration(830);
        wave_anim_2.setFillAfter(true);
        wave_anim_2.setRepeatCount(-1);
        wave_anim_2.setRepeatMode(Animation.REVERSE);

        wave_anim_3 = new TranslateAnimation(0, 0, 0, 9);
        wave_anim_3.setDuration(800);
        wave_anim_3.setFillAfter(true);
        wave_anim_3.setRepeatCount(-1);
        wave_anim_3.setRepeatMode(Animation.REVERSE);

        wave_anim_4 = new TranslateAnimation(0, 0, 0, 6);
        wave_anim_4.setDuration(800);
        wave_anim_4.setFillAfter(true);
        wave_anim_4.setRepeatCount(-1);
        wave_anim_4.setRepeatMode(Animation.REVERSE);

        wave_anim_5 = new TranslateAnimation(0, 0, 0, 10);
        wave_anim_5.setDuration(800);
        wave_anim_5.setFillAfter(true);
        wave_anim_5.setRepeatCount(-1);
        wave_anim_5.setRepeatMode(Animation.REVERSE);

        wave_anim_title= new TranslateAnimation(0, 0, 0,6);
        wave_anim_title.setDuration(2000);
        wave_anim_title.setFillAfter(true);
        wave_anim_title.setRepeatCount(-1);
        wave_anim_title.setRepeatMode(Animation.REVERSE);

        wave1.setAnimation(wave_anim);
        wave2.setAnimation(wave_anim_2);
        wave3.setAnimation(wave_anim_3);
        wave4.setAnimation(wave_anim_4);
        wave5.setAnimation(wave_anim_5);
        waveTitle.setAnimation(wave_anim_title);

        wave1.setVisibility(View.VISIBLE);
        wave2.setVisibility(View.VISIBLE);
        wave3.setVisibility(View.VISIBLE);
        wave4.setVisibility(View.VISIBLE);
        wave5.setVisibility(View.VISIBLE);
        waveTitle.setVisibility(View.VISIBLE);
        ImageView image = (ImageView)findViewById(R.id.imageView2);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        image.startAnimation(animation);
        doBindService();
        btnPlay=(ImageButton) findViewById(R.id.btnPlay);
        btnHelp=(ImageButton) findViewById(R.id.btnHelp);
        imgLoudspeaker=(ImageButton) findViewById(R.id.imgLoudspeaker);
        pisa=(ImageView) findViewById(R.id.pisa);
        tower=(ImageView)findViewById(R.id.tower);
        taj=(ImageView)findViewById(R.id.taj);
        colosseum=(ImageView)findViewById(R.id.colosseum);
        liberty=(ImageView)findViewById(R.id.liberty);

       /* Glide.with(Options.this).load(R.drawable.title)
                .thumbnail(0.1f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(waveTitle);*/
        playMenuAnimation();
        flag= getIntent().getIntExtra("int_value", 0);
        if(flag==1)
        {
            imgLoudspeaker.setBackgroundResource(R.drawable.soundoff);
            stopMusic();
        }
        else{
            imgLoudspeaker.setBackgroundResource(R.drawable.soundon);
            startMusic();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
       // Chartboost.onStart(this);
        //Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);

    }
    @Override
    public void onResume(){
        super.onResume();
        //Chartboost.onResume(this);
        doBindService();
        if(flag==1)
        {
            imgLoudspeaker.setBackgroundResource(R.drawable.soundoff);
            stopMusic();
        }
        else{
            imgLoudspeaker.setBackgroundResource(R.drawable.soundon);
            startMusic();
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        //Chartboost.onStop(this);
        doUnbindService();
        stopMusic();
    }

    @Override
    public  void onPause()
    {
        super.onPause();
        //Chartboost.onPause(this);
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

    public void soundOpt(View view)
    {
        ImageButton speaker =(ImageButton) view;
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

    public void playMenuAnimation(){
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);


        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.4,20);
        myAnim.setInterpolator(interpolator);
        myAnim.setStartOffset(700);
        btnPlay.startAnimation(myAnim);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(800);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(1000);
        btnHelp.startAnimation(myAnim);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(1200);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(1000);
        imgLoudspeaker.startAnimation(myAnim);

    }

    public void playGame(View v){
        clicksound.start();
        clickOff();
        btnPlay.setAlpha(0.4f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(

                ObjectAnimator.ofFloat(btnPlay, "scaleX", 1f, 0.8f),
                ObjectAnimator.ofFloat(btnPlay, "scaleY", 1f, 0.8f)
        );
        set.start();
        Intent intent = new Intent(Options.this, Category.class);
        intent.putExtra("int_value", flag);
        startActivity(intent);
        finish();

    }

    public void help(View v){
        clicksound.start();
        clickOff();
        btnHelp.setAlpha(0.4f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(btnHelp, "scaleX", 1f, 0.8f),
                ObjectAnimator.ofFloat(btnHelp, "scaleY", 1f, 0.8f)
        );
        set.start();
        Intent intent = new Intent(Options.this, Help.class);
        intent.putExtra("int_value", flag);
        startActivity(intent);
        finish();

    }
    public void onBackPressed() {
       // if(Chartboost.onBackPressed())
           // return;
        //else {
            clicksound.start();
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.quit_popup);
            // set the pause_popup dialog components - text, image and button
            ImageView image = dialog.findViewById(R.id.image);
            image.setImageResource(R.drawable.wrong);
            ImageView image2 = dialog.findViewById(R.id.image2);
            image2.setImageResource(R.drawable.righttick);

            // if button is clicked, close the pause_popup dialog
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicksound.start();
                    dialog.dismiss();
                }
            });

            //quit the game
            image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicksound.start();
                    finishAffinity();
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        //}
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
    public void clickOff(){
        btnHelp.setClickable(false);
        btnPlay.setClickable(false);
    }
}