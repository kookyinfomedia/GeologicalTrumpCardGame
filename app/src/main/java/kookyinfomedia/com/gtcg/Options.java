package kookyinfomedia.com.gtcg;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Options extends AppCompatActivity{
    private static final String TAG = "";
    public int flag=0;
    //--------------------------------------Service Binding------------------------------------//
    private boolean mIsBound = false;
    public MusicService mServ;
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
        setContentView(R.layout.activity_options);
        doBindService();
        playMenuAnimation();
        flag= getIntent().getIntExtra("int_value", 0);
        Button imgLoudspeaker=(Button)findViewById(R.id.imgLoudspeaker);
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
        Button imgLoudspeaker=(Button)findViewById(R.id.imgLoudspeaker);

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
        final Button btnPlay=(Button) findViewById(R.id.btnPlay);
        final Button btnHelp=(Button) findViewById(R.id.btnHelp);
        final Button btnPrivacy=(Button) findViewById(R.id.btnPrivacy);
        final ImageView imgFb = (ImageView)findViewById(R.id.imgFb);
        final ImageView imgG = (ImageView)findViewById(R.id.imgG);
        final RelativeLayout txtMoreGames=(RelativeLayout)findViewById(R.id.playMore);
        final Button imgLoudspeaker=(Button)findViewById(R.id.imgLoudspeaker);
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.3,20);
        myAnim.setInterpolator(interpolator);
        myAnim.setStartOffset(0);
        btnPlay.startAnimation(myAnim);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(1000);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(1000);
        btnHelp.startAnimation(myAnim);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(1100);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(1000);
        btnPrivacy.startAnimation(myAnim);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(1200);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(1000);
        imgFb.startAnimation(myAnim);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(1300);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(1000);
        imgG.startAnimation(myAnim);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(1500);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(1000);
        imgLoudspeaker.startAnimation(myAnim);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setStartOffset(1600);
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

    public void playGame(View a){
        Intent intent=new Intent(this,Category.class);
        intent.putExtra("int_value", flag);
        startActivity(intent);
        finish();

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

