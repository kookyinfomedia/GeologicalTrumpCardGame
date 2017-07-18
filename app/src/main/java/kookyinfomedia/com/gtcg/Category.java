package kookyinfomedia.com.gtcg;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Category extends AppCompatActivity {
    private int flag;
    private boolean mIsBound = false;
    private MusicService mServ;
    public static String selectedContinent="";
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_category);
        doBindService();
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
    public void openDeckAsia(View view){
        selectedContinent="asia";
        Intent intent =new Intent(this,DeckSelect.class);
        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();
    }
    public void openDeckNorthAmerica(View view){
        selectedContinent="north_america";
        Intent intent =new Intent(this,DeckSelect.class);
        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();
    }
    public void openDeckSouthAmerica(View view){
        selectedContinent="south_america";
        Intent intent =new Intent(this,DeckSelect.class);
        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();
    }
    public void openDeckAfrica(View view){
        selectedContinent="africa";
        Intent intent =new Intent(this,DeckSelect.class);
        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();
    }
    public void openDeckAntarctica(View view){
        selectedContinent="antarctica";
        Intent intent =new Intent(this,DeckSelect.class);
        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();
    }
    public void openDeckEurope(View view){
        selectedContinent="europe";
        Intent intent =new Intent(this,DeckSelect.class);
        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();
    }
    public void openDeckAustralia(View view){
        selectedContinent="australia";
        Intent intent =new Intent(this,DeckSelect.class);
        intent.putExtra("int_value",flag);
        startActivity(intent);
        finish();
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
