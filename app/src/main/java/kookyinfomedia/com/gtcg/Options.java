package kookyinfomedia.com.gtcg;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.ImageView;


public class Options extends AppCompatActivity{
    private static final String TAG = "";
    final Context context = this;

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
        doBindService();
        btnPlay=(Button) findViewById(R.id.btnPlay);
        btnHelp=(Button) findViewById(R.id.btnHelp);
        btnPrivacy=(Button) findViewById(R.id.btnPrivacy);
        imgFb = (Button) findViewById(R.id.imgFb);
        imgG = (Button) findViewById(R.id.imgG);
        txtMoreGames=(Button) findViewById(R.id.playMore);
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
        myAnim.setStartOffset(200);
        btnPlay.startAnimation(myAnim);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(800);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(1000);
        btnHelp.startAnimation(myAnim);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(900);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(1000);
        btnPrivacy.startAnimation(myAnim);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(1000);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(1000);
        imgFb.startAnimation(myAnim);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(1100);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(1000);
        imgG.startAnimation(myAnim);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(1200);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(1000);
        imgLoudspeaker.startAnimation(myAnim);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(1300);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(1000);
        txtMoreGames.startAnimation(myAnim);

    }
    public void openGPlus(View v){
        Uri uri = Uri.parse("https://plus.google.com/103871381378206728222"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void openFb(View v){
        Uri uri = Uri.parse("https://www.facebook.com/kookyinfomedia/"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void playGame(View v){
        btnPlay.setBackground(getResources().getDrawable(R.drawable.ripple_effect));
        new CountDownTimer(300,100){
            public void onTick(long ms){

            }
            public void onFinish(){
                Intent intent=new Intent(Options.this,Category.class);
                intent.putExtra("int_value", flag);
                startActivity(intent);
                finish();
            }
        }.start();

    }
    public void privacyPolicies(View v){
        btnPrivacy.setBackground(getResources().getDrawable(R.drawable.ripple_effect2));
        new CountDownTimer(300,100){
            public void onTick(long ms){

            }
            public void onFinish(){
            }
        }.start();

    }
    public void help(View v){
        btnHelp.setBackground(getResources().getDrawable(R.drawable.ripple_effect3));
        new CountDownTimer(300,100){
            public void onTick(long ms){

            }
            public void onFinish(){
            }
        }.start();

    }
    public void onBackPressed() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom2);
        //dialog.setTitle("");

        // set the custom dialog components - text, image and button

        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.wrong);
        ImageView image2 = (ImageView) dialog.findViewById(R.id.image2);
        image2.setImageResource(R.drawable.righttick);


        // if button is clicked, close the custom dialog
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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
}

