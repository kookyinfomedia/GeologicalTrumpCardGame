package kookyinfomedia.com.gtcg;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Category extends AppCompatActivity {
    private int flag;
    private boolean mIsBound = false;
    private MusicService mServ;
    public static String selectedContinent="";
    private Tracker mTracker;
    private FirebaseAnalytics mFirebaseAnalytics;
    LinearLayout pop;
    ImageView asia,southAmerica,northAmerica,europe,africa,australia,antarctica,asia2,img;
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
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_category);
        doBindService();

        asia=(ImageView)findViewById(R.id.asia);
        img=(ImageView)findViewById(R.id.img);
        africa=(ImageView)findViewById(R.id.africa);
        australia=(ImageView)findViewById(R.id.australia);
        antarctica=(ImageView)findViewById(R.id.antarctica);
        southAmerica=(ImageView)findViewById(R.id.southAmerica);
        northAmerica=(ImageView)findViewById(R.id.northAmerica);
        europe=(ImageView)findViewById(R.id.europe);
        pop=(LinearLayout)findViewById(R.id.pop);


        flag= getIntent().getIntExtra("int_value", 0);
        if(flag==1)
        {
            stopMusic();
        }
        else{
            startMusic();
        }
        initialize();
}
    @Override
    public void onStop(){
        super.onStop();
        doUnbindService();
        stopMusic();
    }
    @Override
    public void onResume(){
        super.onResume();
        doBindService();
        if(flag==1)
        {
            stopMusic();
        }
        else{
            startMusic();
        }
    }
    @Override
    public void onBackPressed(){
        Intent intent=new Intent(this,Options.class);
        intent.putExtra("int_value",flag);
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

    public void imageinvisible(){
        asia.setVisibility(View.INVISIBLE);
        africa.setVisibility(View.INVISIBLE);
        australia.setVisibility(View.INVISIBLE);
        europe.setVisibility(View.INVISIBLE);
        southAmerica.setVisibility(View.INVISIBLE);
        northAmerica.setVisibility(View.INVISIBLE);
        antarctica.setVisibility(View.INVISIBLE);
        pop.setVisibility(View.INVISIBLE);
    }

    public void openDeckAsia(View view){
        selectedContinent="asia";
        imageinvisible();
        img.setBackground(getResources().getDrawable(R.drawable.asia2));
        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(
                ObjectAnimator.ofFloat(img, "scaleX", 1f, 2.5f),
                ObjectAnimator.ofFloat(img, "scaleY", 1f, 2.5f)
        );
        set1.setDuration(500);
        set1.start();
        new CountDownTimer(1000,100){
            public void onTick(long ms){

            }
            public void onFinish(){
                Intent intent =new Intent(Category.this,DeckSelect.class);
                intent.putExtra("int_value",flag);
                startActivity(intent);
                finish();
            }
        }.start();


    }
    public void openDeckNorthAmerica(View view){

        selectedContinent="north_america";
        imageinvisible();
        img.setBackground(getResources().getDrawable(R.drawable.north_america2));
        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(
                ObjectAnimator.ofFloat(img, "scaleX", 1f, 2.5f),
                ObjectAnimator.ofFloat(img, "scaleY", 1f, 2.5f)
        );
        set1.setDuration(500);
        set1.start();
        new CountDownTimer(1000,100){
            public void onTick(long ms){

            }
            public void onFinish(){
                Intent intent =new Intent(Category.this,DeckSelect.class);
                intent.putExtra("int_value",flag);
                startActivity(intent);
                finish();
            }
        }.start();
    }
    public void openDeckSouthAmerica(View view){
        selectedContinent="south_america";

        imageinvisible();
        img.setBackground(getResources().getDrawable(R.drawable.south_america2));
        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(
                ObjectAnimator.ofFloat(img, "scaleX", 1f, 2.5f),
                ObjectAnimator.ofFloat(img, "scaleY", 1f, 2.5f)
        );
        set1.setDuration(500);
        set1.start();
        new CountDownTimer(1000,100){
            public void onTick(long ms){

            }
            public void onFinish(){
                Intent intent =new Intent(Category.this,DeckSelect.class);
                intent.putExtra("int_value",flag);
                startActivity(intent);
                finish();
            }
        }.start();
    }
    public void openDeckAfrica(View view){
        selectedContinent="africa";

        imageinvisible();
        img.setBackground(getResources().getDrawable(R.drawable.africa2));
        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(
                ObjectAnimator.ofFloat(img, "scaleX", 1f, 2.5f),
                ObjectAnimator.ofFloat(img, "scaleY", 1f, 2.5f)
        );
        set1.setDuration(500);
        set1.start();
        new CountDownTimer(1000,100){
            public void onTick(long ms){

            }
            public void onFinish(){
                Intent intent =new Intent(Category.this,DeckSelect.class);
                intent.putExtra("int_value",flag);
                startActivity(intent);
                finish();
            }
        }.start();
    }
    public void openDeckAntarctica(View view){
        selectedContinent="antarctica";

        imageinvisible();
        img.setBackground(getResources().getDrawable(R.drawable.antarctica2));
        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(
                ObjectAnimator.ofFloat(img, "scaleX", 1f, 2.5f),
                ObjectAnimator.ofFloat(img, "scaleY", 1f, 2.5f)
        );
        set1.setDuration(500);
        set1.start();
        new CountDownTimer(1000,100){
            public void onTick(long ms){

            }
            public void onFinish(){
                Intent intent =new Intent(Category.this,DeckSelect.class);
                intent.putExtra("int_value",flag);
                startActivity(intent);
                finish();
            }
        }.start();
    }
    public void openDeckEurope(View view){
        selectedContinent="europe";

        imageinvisible();
        img.setBackground(getResources().getDrawable(R.drawable.europe2));
        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(
                ObjectAnimator.ofFloat(img, "scaleX", 1f, 2.5f),
                ObjectAnimator.ofFloat(img, "scaleY", 1f, 2.5f)
        );
        set1.setDuration(500);
        set1.start();
        new CountDownTimer(1000,100){
            public void onTick(long ms){

            }
            public void onFinish(){
                Intent intent =new Intent(Category.this,DeckSelect.class);
                intent.putExtra("int_value",flag);
                startActivity(intent);
                finish();
            }
        }.start();
    }
    public void openDeckAustralia(View view){
        selectedContinent="australia";

        imageinvisible();
        img.setBackground(getResources().getDrawable(R.drawable.australia2));
        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(
                ObjectAnimator.ofFloat(img, "scaleX", 1f, 2.5f),
                ObjectAnimator.ofFloat(img, "scaleY", 1f, 2.5f)
        );
        set1.setDuration(500);
        set1.start();
        new CountDownTimer(1000,100){
            public void onTick(long ms){

            }
            public void onFinish(){
                Intent intent =new Intent(Category.this,DeckSelect.class);
                intent.putExtra("int_value",flag);
                startActivity(intent);
                finish();
            }
        }.start();
    }
    public void initialize() {
        final LinearLayout pop=(LinearLayout)findViewById(R.id.pop);
        final RelativeLayout map = (RelativeLayout) findViewById(R.id.map);
        pop.animate()
                .setStartDelay(1500)
                .translationY(pop.getHeight())
                .alpha(0.0f)
                .setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.animate().cancel();
                pop.animate()
                        .translationY(pop.getHeight())
                        .alpha(0.0f)
                        .setDuration(1500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                pop.setVisibility(View.GONE);
                            }
                        });
            }
        });


    }

}
