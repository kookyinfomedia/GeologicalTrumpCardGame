package kookyinfomedia.com.gtcg;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class Category extends AppCompatActivity {
	private int flag;
	public int t=10;
    MediaPlayer clicksound;
	private boolean mIsBound = false;
	private MusicService mServ;
	public static int deck;
	public static String selectedContinent="";
	LinearLayout pop;
	TextView txt;
	boolean backPressed=false;
	ImageView asia,southAmerica,northAmerica,europe,africa,australia,img,india,world;
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
		int screenHeight;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_category);
		doBindService();
        clicksound= MediaPlayer.create(this, R.raw.clicksound);
		txt=(TextView)findViewById(R.id.txt);
		Display display = getWindowManager().getDefaultDisplay();
		screenHeight = display.getHeight();
		ViewGroup.LayoutParams layoutParams = txt.getLayoutParams();
		layoutParams.height = screenHeight / 10;

		asia=(ImageView)findViewById(R.id.asia);
		img=(ImageView)findViewById(R.id.img);
		africa=(ImageView)findViewById(R.id.africa);
		australia=(ImageView)findViewById(R.id.australia);
		southAmerica=(ImageView)findViewById(R.id.southAmerica);
		northAmerica=(ImageView)findViewById(R.id.northAmerica);
		europe=(ImageView)findViewById(R.id.europe);
        india=(ImageView)findViewById(R.id.india);
        world=(ImageView)findViewById(R.id.world);
		pop=(LinearLayout)findViewById(R.id.pop);

        Glide.with(Category.this).load(R.drawable.asia)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
				.crossFade()
                .into(asia);
        Glide.with(Category.this).load(R.drawable.africa)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
				.crossFade()
                .into(africa);
        Glide.with(Category.this).load(R.drawable.australia)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
				.crossFade()
                .into(australia);
        Glide.with(Category.this).load(R.drawable.south_america)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
				.crossFade()
                .into(southAmerica);
        Glide.with(Category.this).load(R.drawable.north_america)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
				.crossFade()
                .into(northAmerica);
        Glide.with(Category.this).load(R.drawable.europe)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
				.crossFade()
                .into(europe);
        Glide.with(Category.this).load(R.drawable.earth_hov)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
				.crossFade()
                .into(world);

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
		backPressed=true;
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
		backPressed=true;
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
		pop.setVisibility(View.GONE);
        world.setVisibility(View.INVISIBLE);
        india.setVisibility(View.INVISIBLE);
	}
	public void openDeckIndia(View view){
        clicksound.start();
		selectedContinent="india";
		deck=36;
		imageinvisible();
		img.setBackground(getResources().getDrawable(R.drawable.india));
		AnimatorSet set1 = new AnimatorSet();
		set1.playTogether(
				ObjectAnimator.ofFloat(img, "scaleX", 1f, 2.5f),
				ObjectAnimator.ofFloat(img, "scaleY", 1f, 2.5f)
		);
		set1.setDuration(500);
		set1.start();
		new CountDownTimer(1000,t){
			public void onTick(long ms){
			}
			public void onFinish(){
				if(!backPressed) {
					Intent intent = new Intent(Category.this, DeckSelect.class);
					intent.putExtra("int_value", flag);
					startActivity(intent);
					finish();
				}
			}
		}.start();


	}

	public void openDeckWorld(View view){
        clicksound.start();
		selectedContinent="world";
		deck=195;
		imageinvisible();
		img.setBackground(getResources().getDrawable(R.drawable.earth_hov));
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
				if(!backPressed) {
					Intent intent = new Intent(Category.this, DeckSelect.class);
					intent.putExtra("int_value", flag);
					startActivity(intent);
					finish();
				}
			}
		}.start();


	}

	public void openDeckAsia(View view){
        clicksound.start();
		selectedContinent="asia";
		deck=48;
		imageinvisible();
		img.setBackground(getResources().getDrawable(R.drawable.asia));
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
				if(!backPressed) {
					Intent intent = new Intent(Category.this, DeckSelect.class);
					intent.putExtra("int_value", flag);
					startActivity(intent);
					finish();
				}
			}
		}.start();


	}
	public void openDeckNorthAmerica(View view){
        clicksound.start();
		selectedContinent="north_america";
		deck=23;
		imageinvisible();
		img.setBackground(getResources().getDrawable(R.drawable.north_america));
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
				if(!backPressed) {
					Intent intent = new Intent(Category.this, DeckSelect.class);
					intent.putExtra("int_value", flag);
					startActivity(intent);
					finish();
				}
			}
		}.start();
	}
	public void openDeckSouthAmerica(View view){
        clicksound.start();
		selectedContinent="south_america";
		deck=12;
		imageinvisible();
		img.setBackground(getResources().getDrawable(R.drawable.south_america));
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
				if(!backPressed) {
					Intent intent = new Intent(Category.this, DeckSelect.class);
					intent.putExtra("int_value", flag);
					startActivity(intent);
					finish();
				}
			}
		}.start();
	}
	public void openDeckAfrica(View view){
        clicksound.start();
		selectedContinent="africa";
		deck=54;
		imageinvisible();
		img.setBackground(getResources().getDrawable(R.drawable.africa));
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
				if(!backPressed) {
					Intent intent = new Intent(Category.this, DeckSelect.class);
					intent.putExtra("int_value", flag);
					startActivity(intent);
					finish();
				}
			}
		}.start();
	}
	public void openDeckEurope(View view){
        clicksound.start();
		selectedContinent="europe";
		deck=51;
		imageinvisible();
		img.setBackground(getResources().getDrawable(R.drawable.europe));
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
				if(!backPressed) {
					Intent intent = new Intent(Category.this, DeckSelect.class);
					intent.putExtra("int_value", flag);
					startActivity(intent);
					finish();
				}
			}
		}.start();
	}
	public void openDeckAustralia(View view){
        clicksound.start();
		selectedContinent="australia";
		deck=14;
		imageinvisible();
		img.setBackground(getResources().getDrawable(R.drawable.australia));
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
				if(!backPressed) {
					Intent intent = new Intent(Category.this, DeckSelect.class);
					intent.putExtra("int_value", flag);
					startActivity(intent);
					finish();
				}
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
