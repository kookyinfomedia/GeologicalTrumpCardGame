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
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;


public class Options extends AppCompatActivity{
    final Context context = this;
    public ImageView wave1,wave2,wave3,wave4,wave5;
    public Animation wave_anim, wave_anim_2, wave_anim_3, wave_anim_4, wave_anim_5;

    public int flag=0;
    //--------------------------------------Service Binding------------------------------------//
    private boolean mIsBound = false;
    public MusicService mServ;
    Button btnPlay,btnHelp,btnPrivacy,imgFb,imgG,txtMoreGames,imgLoudspeaker;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_options);
        wave1 = (ImageView)findViewById(R.id.pisa);
        wave2 = (ImageView)findViewById(R.id.tower);
        wave3 = (ImageView)findViewById(R.id.taj);
        wave4 = (ImageView)findViewById(R.id.liberty);
        wave5 = (ImageView)findViewById(R.id.colosseum);

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

        wave1.setAnimation(wave_anim);
        wave2.setAnimation(wave_anim_2);
        wave3.setAnimation(wave_anim_3);
        wave4.setAnimation(wave_anim_4);
        wave5.setAnimation(wave_anim_5);
        wave1.setVisibility(View.VISIBLE);
        wave2.setVisibility(View.VISIBLE);
        wave3.setVisibility(View.VISIBLE);
        wave4.setVisibility(View.VISIBLE);
        wave5.setVisibility(View.VISIBLE);

        ImageView image = (ImageView)findViewById(R.id.imageView2);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        image.startAnimation(animation);
        doBindService();
        btnPlay=(Button) findViewById(R.id.btnPlay);
        btnHelp=(Button) findViewById(R.id.btnHelp);
        imgLoudspeaker=(Button) findViewById(R.id.imgLoudspeaker);
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
    public void onResume(){
        super.onResume();
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
        doUnbindService();
        stopMusic();
    }

    public void soundOpt(View view)
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
        clickOff();
        btnPlay.setAlpha(0.5f);
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
        clickOff();
        btnHelp.setAlpha(0.5f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(btnHelp, "scaleX", 1f, 0.8f),
                ObjectAnimator.ofFloat(btnHelp, "scaleY", 1f, 0.8f)
        );
        set.start();

    }
    public void onBackPressed() {
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
                dialog.dismiss();
            }
        });

        //quit the game
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

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

