package kookyinfomedia.com.gtcg;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

import static kookyinfomedia.com.gtcg.Category.deck;
import static kookyinfomedia.com.gtcg.Toss.toss;

public class GamePlayIndia extends AppCompatActivity {
    RelativeLayout fullrel, relLoad,rel1,relPopup;
    public static int player;
    int pause=0;
    public static Bitmap bitmap;
    public int flag=0,x,y;
    int xprev=-1,yprev=-1;
    ArrayList<Integer>arrA=new ArrayList<>();
    ArrayList<Integer>arrB=new ArrayList<>();
    String riverName, cropName, mineralName;
    String[] arrRivers, arrMinerals, arrCrops, arrRivers2, arrMinerals2, arrCrops2;
    int flagInt, flagBack = 0;
    Animation animation2, animation;
    DBAdapter obj;
    ArrayList<ModelClassIndia> arr = new ArrayList<>();
    ControllerIndia controllerIndia;
    //ImageView imgCard1_flag, imgCard2_flag;
    RelativeLayout imgCard1_State,imgCard2_State;
    ModelClassIndia modelClassIndia, modelClass1India;
    Button cardLoudspeaker, cardBack, loudspeaker;
    CardView cardP1, cardP2, Score, cardP3;
    int screenHeight;
    ImageView cardBigLeft, cardBigRight, cardSmallLeft, cardSmallRight, toast, cardCoinLeft, cardCoinRight;
    TextView txtArea, txtPopulation, txtDistrict, txtNationalPark, txtRiver, txtCrop, txtMineral,txtState1,txtFest,txtCapital;
    TextView valArea, valPopulation, valDistrict, valNationalPark,txtState2,txtFest2,txtCapital2;
    Spinner spinRiver, spinCrop,spinMineral,spinRiver2, spinCrop2,spinMineral2;
    TextView txtArea2, txtPopulation2, txtDistrict2, txtNationalPark2, txtRiver2, txtCrop2, txtMineral2;
    TextView valArea2, valPopulation2, valDistrict2, valNationalPark2;
    TextView txtMyCards, txtMyVal, txtOppCards, txtOppVal, txtScore, txtScoreVal;
    RelativeLayout relUpper,relP2,relP1;
    LinearLayout lin1back,lin2back;
    Dialog dialog;
    LinearLayout l1, l2, l3, l4, l5, l6,l7, l11, l12, l13, l14, l15, l16,l17, linLayBottomLeft, linLayBottomRight;
    int betField, playerNum, updatedScore;
    Animation myAnim, myAnimFinal;
    MediaPlayer cardTap,cardWoosh,clicksound,soundYouWon,soundYouLose;

    public static int gameOverFlag = 0;
    int score = 0, myCard = deck, oppCard =deck;

    private boolean mIsBound = false;
    public MusicService mServ;
    private ServiceConnection Scon = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService() {
        bindService(new Intent(this, MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
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
        setContentView(R.layout.activity_game_play_india);
        Chartboost.startWithAppId(this,"588b1cf1f6cd4501525f3d12","2c46739a486f6196854ecdd781eaef425ce72ce9");
        Chartboost.onCreate(this);
        flagInt = getIntent().getIntExtra("int_value", 0);
        if(flagInt==1)
        {
            stopMusic();
        }
        else{
            startMusic();
        }

        cardTap = MediaPlayer.create(GamePlayIndia.this,R.raw.card_flip);
        cardWoosh = cardTap;
        soundYouLose= MediaPlayer.create(this, R.raw.you_lose);
        soundYouLose.setVolume(200,200);
        soundYouWon= MediaPlayer.create(this, R.raw.you_win);
        soundYouWon.setVolume(1.5f,1.5f);
        cardWoosh.setVolume(1.5f,1.5f);
        cardTap.setVolume(1f,1f);
        clicksound= MediaPlayer.create(this, R.raw.clicksound);
        if(deck%2==0){
            myCard=deck/2;
            oppCard=deck/2;
        }
        else{
            myCard=deck/2+1;
            oppCard=deck/2;
        }
        fullrel = (RelativeLayout) findViewById(R.id.fullrel);
        relLoad = (RelativeLayout) findViewById(R.id.relLoad);
        relPopup=(RelativeLayout)findViewById(R.id.relPopup);
        rel1=(RelativeLayout)findViewById(R.id.rel1);
        relLoad.setVisibility(View.VISIBLE);
        relPopup.setVisibility(View.INVISIBLE);
        fullrel.setVisibility(View.INVISIBLE);
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    @Override
    public  void onPause()
    {
        super.onPause();
        Chartboost.onPause(this);
        // If the screen is off then the device has been locked
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        boolean isScreenOn;
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

    private class MyAsyncTask extends AsyncTask<Void, Void, ArrayList> {
        protected ArrayList doInBackground(Void... voids) {
            try {
                obj = DBAdapter.getDBAdapter(getApplicationContext());
                if (!obj.checkDatabase())
                    obj.createDatabase(getApplicationContext());
                obj.openDatabase();
                arr = obj.getDataIndia();
            } catch (Exception ignored) {
            } finally {
                DBAdapter.sdb.close();
                obj.close();
            }


            doBindService();
            loudspeaker = (Button) findViewById(R.id.cardLoudspeaker);
            relUpper = (RelativeLayout) findViewById(R.id.relUpper);
            Display display = getWindowManager().getDefaultDisplay();
            screenHeight = display.getHeight();
            controllerIndia = new ControllerIndia();

            ////// acessing elements for animations: bottom linear layouts,back button, sound button
            cardLoudspeaker = (Button) findViewById(R.id.cardLoudspeaker);
            //imgLoudspeaker=(ImageView)findViewById(R.id.imgLoudspeaker);
            cardBack = (Button) findViewById(R.id.cardBack);
            cardCoinLeft = (ImageView) findViewById(R.id.cardCoinLeft);
            cardCoinRight = (ImageView) findViewById(R.id.cardCoinRight);
            linLayBottomLeft = (LinearLayout) findViewById(R.id.linLayBottomLeft);
            linLayBottomRight = (LinearLayout) findViewById(R.id.linLayBottomRight);
            cardSmallLeft = (ImageView) findViewById(R.id.cardSmallLeft);
            cardSmallRight = (ImageView) findViewById(R.id.cardSmallRight);
            Score = (CardView) findViewById(R.id.Score);

            ////accessing everything ! ////for first player
            cardP1 = (CardView) findViewById(R.id.cardP1);
            relP1=(RelativeLayout)findViewById(R.id.relP1);
            lin1back=(LinearLayout)findViewById(R.id.lin1back);
            cardBigLeft = (ImageView) findViewById(R.id.cardBigLeft);

            imgCard1_State = (RelativeLayout) findViewById(R.id.imgCard1_State);
            //imgCard1_flag = (ImageView) findViewById(R.id.imgCard1_flag);

            txtState1=(TextView)findViewById(R.id.txtStateName);
            txtArea = (TextView) findViewById(R.id.txtArea);
            txtPopulation = (TextView) findViewById(R.id.txtPopulation);
            txtDistrict = (TextView) findViewById(R.id.txtDistrict);
            txtNationalPark = (TextView) findViewById(R.id.txtNationalPark);
            txtRiver = (TextView) findViewById(R.id.txtRiver);
            txtCrop = (TextView) findViewById(R.id.txtCrop);
            txtMineral=(TextView)findViewById(R.id.txtMineral);
            txtFest=(TextView)findViewById(R.id.txtFest);
            txtCapital=(TextView)findViewById(R.id.txtCapital);

            valArea = (TextView) findViewById(R.id.valArea);
            valPopulation = (TextView) findViewById(R.id.valPopulation);
            valDistrict = (TextView) findViewById(R.id.valDistrict);
            valNationalPark = (TextView) findViewById(R.id.valNationalPark);
            spinRiver = (Spinner) findViewById(R.id.spinRiver);
            spinCrop = (Spinner) findViewById(R.id.spinCrop);
            spinMineral=(Spinner)findViewById(R.id.spinMineral);

            l1 = (LinearLayout) findViewById(R.id.linLay1);
            l2 = (LinearLayout) findViewById(R.id.linLay2);
            l3 = (LinearLayout) findViewById(R.id.linLay3);
            l4 = (LinearLayout) findViewById(R.id.linLay4);
            l5 = (LinearLayout) findViewById(R.id.linLay5);
            l6 = (LinearLayout) findViewById(R.id.linLay6);
            l7=(LinearLayout)findViewById(R.id.linLay7);

            cardP3 = (CardView) findViewById(R.id.cardP3);

            /////////////////  for second player
            cardP2 = (CardView) findViewById(R.id.cardP2);
            relP2=(RelativeLayout)findViewById(R.id.relP2);
            lin2back=(LinearLayout)findViewById(R.id.lin2back);
            cardBigRight = (ImageView) findViewById(R.id.cardBigRight);

            imgCard2_State = (RelativeLayout) findViewById(R.id.imgCard2_State);
           // imgCard2_flag = (ImageView) findViewById(R.id.imgCard2_flag);

            txtState2=(TextView)findViewById(R.id.txtStateName2);
            txtArea2 = (TextView) findViewById(R.id.txtArea2);
            txtPopulation2 = (TextView) findViewById(R.id.txtPopulation2);
            txtDistrict2 = (TextView) findViewById(R.id.txtDistrict2);
            txtNationalPark2 = (TextView) findViewById(R.id.txtNationalPark2);
            txtRiver2 = (TextView) findViewById(R.id.txtRiver2);
            txtCrop2 = (TextView) findViewById(R.id.txtCrop2);
            txtMineral2=(TextView)findViewById(R.id.txtMineral2);
            txtFest2=(TextView)findViewById(R.id.txtFest2);
            txtCapital2=(TextView)findViewById(R.id.txtCapital2);

            valArea2 = (TextView) findViewById(R.id.valArea2);
            valPopulation2 = (TextView) findViewById(R.id.valPopulation2);
            valDistrict2 = (TextView) findViewById(R.id.valDistrict2);
            valNationalPark2 = (TextView) findViewById(R.id.valNationalPark2);
            spinRiver2 = (Spinner) findViewById(R.id.spinRiver2);
            spinCrop2 = (Spinner) findViewById(R.id.spinCrop2);
            spinMineral2=(Spinner)findViewById(R.id.spinMineral2);

            l11 = (LinearLayout) findViewById(R.id.linLay11);
            l12 = (LinearLayout) findViewById(R.id.linLay12);
            l13 = (LinearLayout) findViewById(R.id.linLay13);
            l14 = (LinearLayout) findViewById(R.id.linLay14);
            l15 = (LinearLayout) findViewById(R.id.linLay15);
            l16 = (LinearLayout) findViewById(R.id.linLay16);
            l17=(LinearLayout)findViewById(R.id.linLay17);

            ////////// acessing scores and cards for both players
            txtMyCards = (TextView) findViewById(R.id.txtMyCards);
            txtMyVal = (TextView) findViewById(R.id.txtMyVal);
            txtOppCards = (TextView) findViewById(R.id.txtOppCards);
            txtOppVal = (TextView) findViewById(R.id.txtOppVal);
            txtScore = (TextView) findViewById(R.id.txtScore);
            txtScoreVal = (TextView) findViewById(R.id.txtScoreVal);
            toast = (ImageView) findViewById(R.id.toast);
            if (toss == 0)
                playerNum = 2;
            else
                playerNum = 1;
            return arr;
        }

        protected void onPostExecute(ArrayList arr) {
            new CountDownTimer(500,100){
                public void onTick(long ms){
                }
                public void onFinish(){
                    txtScoreVal.setVisibility(View.INVISIBLE);
                    fullrel.setVisibility(View.VISIBLE);
                    relLoad.setVisibility(View.INVISIBLE);
                    final Dialog dialog = new Dialog(getApplicationContext());
                    cardP1.setVisibility(View.INVISIBLE);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    cardBack = (Button) findViewById(R.id.cardBack);


                    if (flagInt == 1) {
                        loudspeaker.setBackgroundResource(R.drawable.soundoff);
                        stopMusic();
                    } else {
                        loudspeaker.setBackgroundResource(R.drawable.soundon);
                        startMusic();
                    }
                    ViewGroup.LayoutParams layoutParams = relUpper.getLayoutParams();
                    layoutParams.height = screenHeight / 10;
                    relUpper.setLayoutParams(layoutParams);
                    cardP2.setVisibility(View.INVISIBLE);
                    cardBigRight.setEnabled(false);
                    cardBigRight.setClickable(false);
                    cardP3.setVisibility(View.INVISIBLE);
                    cardBigLeft.setVisibility(View.INVISIBLE);
                    cardBigRight.setVisibility(View.INVISIBLE);
                    playAnimation();
                    myAnimFinal.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            txtScoreVal.setVisibility(View.VISIBLE);
                            cardBigLeft.setVisibility(View.VISIBLE);
                            cardBigRight.setVisibility(View.VISIBLE);
                            txtMyVal.setText("" + myCard);
                            txtOppVal.setText("" + oppCard);
                            if(score!=0)
                                txtScoreVal.setText("" + score);
                            else
                                txtScoreVal.setText("0000");
                            clickoff();
                            ShowRecord();


                            if (playerNum == 1) {
                                player = 1;
                            } else {
                                flag = 1;
                                player = 2;
                                new CountDownTimer(1500, 100) {
                                    public void onTick(long ms) {
                                    }

                                    public void onFinish() {
                                        Animation animation1 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.to_middle2);
                                        animation2 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.from_middle2);
                                        cardBigRight.startAnimation(animation1);
                                        animation1.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {
                                                if(flagInt==0)
                                                cardTap.start();
                                                
                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                cardBigRight.setVisibility(View.INVISIBLE);
                                                cardP2.setVisibility(View.VISIBLE);
                                                cardP2.startAnimation(animation2);
                                                cardBigLeft.setEnabled(true);
                                                cardBigLeft.setClickable(true);
                                                flag = 0;
                                                getBlinkAnimation();
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }
                                        });

                                        int betField = controllerIndia.betDecisionComputer(modelClass1India);
                                        switch (betField) {
                                            case 1: {
                                                areaSelectI(cardP2);
                                                break;
                                            }
                                            case 2: {
                                                populationSelectI(cardP2);
                                                break;
                                            }
                                            case 3: {
                                                districtSelectI(cardP2);
                                                break;
                                            }
                                            case 4: {
                                                nationalParkSelectI(cardP2);
                                                break;
                                            }
                                            case 5: {
                                                riverSelectI(cardP2);
                                                break;
                                            }
                                            case 6: {
                                                cropSelectI(cardP2);
                                                break;
                                            }
                                            case 7: {
                                                mineralSelectI(cardP2);
                                                break;
                                            }
                                        }
                                        flag = 0;
                                        cardBigLeft.setEnabled(true);
                                        cardBigLeft.setClickable(true);
                                    }
                                }.start();
                            }
                            // Animation : Cards coming from both sides.
                            if(flagInt==0)
                               cardWoosh.start();
                            AnimatorSet set = new AnimatorSet();
                            set.playTogether(

                                    ObjectAnimator.ofFloat(cardBigLeft, "scaleX", 0.1f, 1f),
                                    ObjectAnimator.ofFloat(cardBigLeft, "scaleY", 0.1f, 1f)
                            );
                            set.setDuration(1000).start();
                            
                            
                            Animation myAnim2 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.animation2);
                            cardBigLeft.startAnimation(myAnim2);

                            AnimatorSet set1 = new AnimatorSet();
                            set1.playTogether(
                                    ObjectAnimator.ofFloat(cardBigRight, "scaleX", 0.1f, 1f),
                                    ObjectAnimator.ofFloat(cardBigRight, "scaleY", 0.1f, 1f)
                            );
                            set1.setDuration(1000).start();
                            
                            Animation myAnim1 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.animation1);
                            cardBigRight.startAnimation(myAnim1);
                            myAnim1.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    cardP3.setVisibility(View.VISIBLE);
                                    cardP3.setBackground(getResources().getDrawable(R.drawable.cardborder));
                                    getBlinkAnimation();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            }.start();

        }

    }
    public void touchOff(){
        l1.setEnabled(false);
        l2.setEnabled(false);
        l3.setEnabled(false);
        l4.setEnabled(false);
        l5.setEnabled(false);
        l6.setEnabled(false);
        l7.setEnabled(false);
    }

    public void touchOn(){
        l1.setEnabled(true);
        l2.setEnabled(true);
        l3.setEnabled(true);
        l4.setEnabled(true);
        l5.setEnabled(true);
        l6.setEnabled(true);
        l7.setEnabled(true);
    }

    public void playAnimation() {
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.3, 15);
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnimFinal = AnimationUtils.loadAnimation(this, R.anim.bounce);
        // back button
        myAnim.setStartOffset(1000);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);
        cardBack.startAnimation(myAnim);
        // sound button
        myAnim.setStartOffset(1100);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);
        cardLoudspeaker.startAnimation(myAnim);
        // bottom coin left
        myAnim.setStartOffset(1200);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);
        cardCoinLeft.startAnimation(myAnim);
        // bottom coin right
        myAnim.setStartOffset(1300);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);
        cardCoinRight.startAnimation(myAnim);
        // card small left
        myAnim.setStartOffset(1200);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);
        cardSmallLeft.startAnimation(myAnim);
        // card small right
        myAnim.setStartOffset(1200);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);
        cardSmallRight.startAnimation(myAnim);
        //Score
        myAnim.setStartOffset(1400);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);
        Score.startAnimation(myAnim);
        // linear layout bottom left
        myAnim.setStartOffset(1400);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);
        linLayBottomLeft.startAnimation(myAnim);
        // linear layout bottom right
        myAnimFinal.setStartOffset(1500);
        myAnimFinal.setDuration(1000);
        myAnimFinal.setInterpolator(interpolator);
        linLayBottomRight.startAnimation(myAnimFinal);
    }

    /////////// Method for choosing the record and showing data over the cards. ///////////////////////
    public void ShowRecord() {
        do {
            Random ran = new Random();
            x = ran.nextInt(arr.size());
            y = ran.nextInt(arr.size());
            } while ((x == y) || arrB.contains(x) || arrA.contains(y)||(x==xprev)||(y==xprev)||(x==yprev)||(y==yprev));
        xprev=x;
        yprev=y;
        if(arrA.isEmpty()&& arrB.isEmpty())
        {
            arrA.add(x);
            arrB.add(y);
        }
        else{
            arrA.add(x);
            arrB.add(y);
        }

        txtState1.setText(arr.get(x).getState());
        valArea.setText(arr.get(x).getArea());
        valPopulation.setText(arr.get(x).getPopulation());
        valDistrict.setText(arr.get(x).getDistricts());
        valNationalPark.setText(arr.get(x).getNational_parks());
        txtCapital.setText(arr.get(x).getCapital());
        txtFest.setText(arr.get(x).getFestival());
        String rivers = arr.get(x).getRiver().toUpperCase();
        arrRivers = rivers.split(",");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(GamePlayIndia.this,
                R.layout.spinner_item, arrRivers);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinRiver.setAdapter(adapter);
        spinRiver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                riverName = arrRivers[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                riverName = arrRivers[0];
            }
        });
        String crops = arr.get(x).getCrop().toUpperCase();
        arrCrops = crops.split(",");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(GamePlayIndia.this,
                R.layout.spinner_item, arrCrops);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown);
        spinCrop.setAdapter(adapter2);
        spinCrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cropName = arrCrops[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                cropName = arrCrops[0];
            }
        });
        String minerals = arr.get(x).getMineral().toUpperCase();
        arrMinerals = minerals.split(",");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(GamePlayIndia.this,
                R.layout.spinner_item, arrMinerals);
        adapter3.setDropDownViewResource(R.layout.spinner_dropdown);
        spinMineral.setAdapter(adapter3);
        spinMineral.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mineralName = arrMinerals[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mineralName = arrMinerals[0];
            }
        });

        Drawable image = new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(arr.get(x).getMap(), 0, arr.get(x).getMap().length));
        image.setAlpha(100);
        lin1back.setBackground(image);
        modelClassIndia = new ModelClassIndia();
        modelClass1India = new ModelClassIndia();
        modelClassIndia.setState(arr.get(x).getState());
        modelClassIndia.setArea(arr.get(x).getArea());
        modelClassIndia.setPopulation(arr.get(x).getPopulation());
        modelClassIndia.setDistricts(arr.get(x).getDistricts());
        modelClassIndia.setNational_parks(arr.get(x).getNational_parks());
        modelClassIndia.setRiver(arr.get(x).getRiver());
        modelClassIndia.setCrop(arr.get(x).getCrop());
        modelClassIndia.setMineral(arr.get(x).getMineral());
        txtState2.setText(arr.get(y).getState());
        valArea2.setText(arr.get(y).getArea());
        valPopulation2.setText(arr.get(y).getPopulation());
        valDistrict2.setText(arr.get(y).getDistricts());
        valNationalPark2.setText(arr.get(y).getNational_parks());
        txtCapital2.setText(arr.get(y).getCapital());
        txtFest2.setText(arr.get(y).getFestival());
        String rivers2 = arr.get(y).getRiver().toUpperCase();
        arrRivers2 = rivers2.split(",");
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(GamePlayIndia.this,
                R.layout.spinner_item, arrRivers2);
        adapter4.setDropDownViewResource(R.layout.spinner_dropdown);
        spinRiver2.setClickable(false);
        spinRiver2.setEnabled(false);
        spinRiver2.setAdapter(adapter4);
        String crops2 = arr.get(y).getCrop().toUpperCase();
        arrCrops2 = crops2.split(",");
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(GamePlayIndia.this, R.layout.spinner_item, arrCrops2);
        adapter5.setDropDownViewResource(R.layout.spinner_dropdown);
        spinCrop2.setClickable(false);
        spinCrop2.setEnabled(false);
        spinCrop2.setAdapter(adapter5);
        String minerals2 = arr.get(y).getMineral().toUpperCase();
        arrMinerals2 = minerals2.split(",");
        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(GamePlayIndia.this,
                R.layout.spinner_item, arrMinerals2);
        adapter6.setDropDownViewResource(R.layout.spinner_dropdown);
        spinMineral2.setClickable(false);
        spinMineral2.setEnabled(false);
        spinMineral2.setAdapter(adapter6);

        Drawable image2 = new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(arr.get(y).getMap(), 0, arr.get(y).getMap().length));
        image2.setAlpha(100);
        lin2back.setBackground(image2);
        modelClass1India.setState(arr.get(y).getState());
        modelClass1India.setArea(arr.get(y).getArea());
        modelClass1India.setPopulation(arr.get(y).getPopulation());
        modelClass1India.setDistricts(arr.get(y).getDistricts());
        modelClass1India.setNational_parks(arr.get(y).getNational_parks());
        modelClass1India.setRiver(arr.get(y).getRiver());
        modelClass1India.setCrop(arr.get(y).getCrop());
	    modelClass1India.setMineral(arr.get(y).getMineral());

    }

    public void clickoff() {
        l1.setClickable(false);
        l2.setClickable(false);
        l3.setClickable(false);
        l4.setClickable(false);
        l5.setClickable(false);
        l6.setClickable(false);
        l7.setClickable(false);
        l11.setClickable(false);
        l12.setClickable(false);
        l13.setClickable(false);
        l14.setClickable(false);
        l15.setClickable(false);
        l16.setClickable(false);
        l17.setClickable(false);
        spinMineral.setEnabled(false);
        spinRiver.setEnabled(false);
        spinCrop.setEnabled(false);

    }

    public void clickon() {
        l1.setClickable(true);
        l2.setClickable(true);
        l3.setClickable(true);
        l4.setClickable(true);
        l5.setClickable(true);
        l6.setClickable(true);
        l7.setClickable(true);
        spinCrop.setEnabled(true);
        spinRiver.setEnabled(true);
        spinMineral.setEnabled(true);
    }

    /////// Method to be called when user clicks his card ////////
    public void showCardI(View v) {
        if (flag == 0) {
            if(flagInt==0)
            cardTap.start();
            touchOn();
            flag = 1;
            cardBigLeft.setClickable(false);
            cardBigLeft.setEnabled(false);
            cardP1.setVisibility(View.INVISIBLE);
            cardBigLeft.setVisibility(View.VISIBLE);
            cardP3.setVisibility(View.INVISIBLE);
            Animation animation1 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.to_middle2);
            animation2 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.from_middle2);
            cardBigLeft.startAnimation(animation1);
            animation1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    cardP3.setVisibility(View.INVISIBLE);
                    cardBigLeft.setVisibility(View.INVISIBLE);
                    cardP1.setVisibility(View.VISIBLE);
                    cardP1.startAnimation(animation2);
                    clickon();
                    if (playerNum == 2) {
                        clickoff();
                        touchOff();
                        flag = 1;
                        new CountDownTimer(1500, 100) {
                            public void onTick(long ms) {
                            }
                            public void onFinish() {
                                updates();
                            }
                        }.start();
                    }
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
    }


    /////////// Method to be called if user selects area for betting. ///////////////////////
    public void areaSelectI(View v) {
        touchOff();
        //Toast.makeText(this,"Area",Toast.LENGTH_LONG).show();
        betField = 1;
        cardBigLeft.setEnabled(false);
        flag = 1;
        clickoff();
        setColorWhite();
        getBlinkAnimation(l1, "BLUE");
        getBlinkAnimation(l11, "BLUE");
        if (playerNum == 1) {
            flipCardRight();
        }
    }

    /////////// Method to be called if user selects population for betting. //////////////////////
    public void populationSelectI(View v) {
        touchOff();
        betField = 2;
        cardBigLeft.setEnabled(false);
        flag = 1;
        clickoff();
        setColorWhite();
        getBlinkAnimation(l2, "BLUE");
        getBlinkAnimation(l12, "BLUE");
        if (playerNum == 1) {
            flipCardRight();
        }
    }

    /////////// Method to be called if user selects district for betting. ////////////////////////////
    public void districtSelectI(View v) {
        touchOff();
        betField = 3;
        cardBigLeft.setEnabled(false);
        flag = 1;
        clickoff();
        setColorWhite();
        getBlinkAnimation(l3, "BLUE");
        getBlinkAnimation(l13, "BLUE");
        if (playerNum == 1) {
            flipCardRight();
        }
    }

    /////////// Method to be called if user selects administrative units for betting. //////////////////////////
    public void nationalParkSelectI(View v) {
        touchOff();
        betField = 4;
        cardBigLeft.setEnabled(false);
        flag = 1;
        clickoff();
        setColorWhite();
        getBlinkAnimation(l4, "BLUE");
        getBlinkAnimation(l14, "BLUE");
        if (playerNum == 1) {
            flipCardRight();
        }
    }

    /////////// Method to be called if user selects Bordering Countries for betting. //////////////////////////
    public void riverSelectI(View v) {
        if(playerNum==2){
            Random random=new Random();
            int r = random.nextInt(arrRivers2.length);
            spinRiver2.setSelection(r);
	    riverName = arrRivers2[r];
        }
        touchOff();
        betField = 5;
        cardBigLeft.setEnabled(false);
        flag = 1;
        clickoff();
        setColorWhite();
        getBlinkAnimation(l5, "BLUE");
        getBlinkAnimation(l15, "BLUE");
        if (playerNum == 1) {
            flipCardRight();
        }
    }


    /////////// Method to be called if user selects highest point for betting. ////////////////////
    public void cropSelectI(View v) {
        if(playerNum==2){
            Random random=new Random();
            int r = random.nextInt(arrCrops2.length);
            Log.i("R",""+r);
            Log.i("ARR_CROPS2[r]",""+arrCrops2[r]);
            spinCrop2.setSelection(r);
            cropName = arrCrops2[r];

        }
        touchOff();
        betField = 6;
        flag = 1;
        clickoff();
        l6.setBackgroundColor(Color.BLUE);
        setColorWhite();
        getBlinkAnimation(l6, "BLUE");
        getBlinkAnimation(l16, "BLUE");
        if (playerNum == 1) {
            flipCardRight();
        }
    }

    public void mineralSelectI(View v) {
        if(playerNum==2){
            Random random=new Random();
            int r = random.nextInt(arrMinerals2.length);
            spinMineral2.setSelection(r);
            mineralName = arrMinerals2[r];
        }
        touchOff();
        betField = 7;
        flag = 1;
        clickoff();
        l7.setBackgroundColor(Color.BLUE);
        setColorWhite();
        getBlinkAnimation(l7, "BLUE");
        getBlinkAnimation(l17, "BLUE");
        if (playerNum == 1) {
            flipCardRight();
        }
    }


    public void flipCardRight() {
        if(flagInt==0)
        cardTap.start();
        Animation animation1 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.to_middle2);
        animation2 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.from_middle2);
        cardBigRight.startAnimation(animation1);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardBigRight.setVisibility(View.INVISIBLE);
                cardP2.setVisibility(View.VISIBLE);
                cardP2.startAnimation(animation2);
                animation2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        
                        
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        clickoff();
                        new CountDownTimer(1000, 100) {
                            public void onTick(long ms) {

                            }

                            public void onFinish() {
                                cardP1.setClickable(false);
                                cardBigLeft.setClickable(false);
                                updates();
                            }
                        }.start();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    ///////////// Method for implementing the game logic. //////////////////
    public void updates() {
        if (myCard != 0 && oppCard != 0) {
            playerNum = 0;
            if (player == 1)
                playerNum = controllerIndia.checkWin(modelClassIndia, modelClass1India, betField,riverName, cropName, mineralName, arrRivers2, arrCrops2, arrMinerals2);
            else
                playerNum = controllerIndia.checkWin(modelClass1India, modelClassIndia, betField,riverName, cropName, mineralName, arrRivers, arrCrops, arrMinerals);
            updatedScore = controllerIndia.updateScore(playerNum);
            if (updatedScore == 1) {
                score = score + 100;
                arrB.remove(arrB.indexOf(y));
                arrA.add(y);
                myCard = myCard + 1;
                oppCard = oppCard - 1;
                switch (betField) {
                    case 1: {
                        getBlinkAnimation(l1, "GREEN");
                        getBlinkAnimation(l11, "RED");
                        break;
                    }
                    case 2: {
                        getBlinkAnimation(l2, "GREEN");
                        getBlinkAnimation(l12, "RED");
                        break;
                    }
                    case 3: {
                        getBlinkAnimation(l3, "GREEN");
                        getBlinkAnimation(l13, "RED");
                        break;
                    }
                    case 4: {
                        getBlinkAnimation(l4, "GREEN");
                        getBlinkAnimation(l14, "RED");
                        break;
                    }
                    case 5: {
                        getBlinkAnimation(l5, "GREEN");
                        getBlinkAnimation(l15, "RED");
                        break;
                    }
                    case 6: {
                        getBlinkAnimation(l6, "GREEN");
                        getBlinkAnimation(l16, "RED");
                        break;
                    }
                    case 7: {
                        getBlinkAnimation(l7, "GREEN");
                        getBlinkAnimation(l17, "RED");
                        break;
                    }
                }
                toast.setVisibility(View.VISIBLE);
                toast.setImageResource(R.drawable.youwon);
                new CountDownTimer(1200, 500) {
                    public void onTick(long ms) {

                    }

                    public void onFinish() {
                        toast.setVisibility(View.INVISIBLE);
                    }
                }.start();
            } else if (updatedScore == 0) {
                if(score!=0)
                    score = score - 100;
                myCard = myCard - 1;
                arrA.remove(arrA.indexOf(x));
                arrB.add(x);
                oppCard = oppCard + 1;
                switch (betField) {
                    case 1: {
                        getBlinkAnimation(l11, "GREEN");
                        getBlinkAnimation(l1, "RED");
                        break;
                    }
                    case 2: {
                        getBlinkAnimation(l12, "GREEN");
                        getBlinkAnimation(l2, "RED");
                        break;
                    }
                    case 3: {
                        getBlinkAnimation(l13, "GREEN");
                        getBlinkAnimation(l3, "RED");
                        break;
                    }
                    case 4: {
                        getBlinkAnimation(l14, "GREEN");
                        getBlinkAnimation(l4, "RED");
                        break;
                    }
                    case 5: {
                        getBlinkAnimation(l15, "GREEN");
                        getBlinkAnimation(l5, "RED");
                        break;
                    }
                    case 6: {
                        getBlinkAnimation(l16, "GREEN");
                        getBlinkAnimation(l6, "RED");
                        break;
                    }
                    case 7: {
                        getBlinkAnimation(l17, "GREEN");
                        getBlinkAnimation(l7, "RED");
                        break;
                    }
                }
                toast.setVisibility(View.VISIBLE);
                toast.setImageResource(R.drawable.youlose);
                new CountDownTimer(1200, 500) {
                    public void onTick(long ms) {

                    }

                    public void onFinish() {
                        toast.setVisibility(View.INVISIBLE);
                    }
                }.start();
            }
            if(score!=0)
                txtScoreVal.setText("" + score);
            else
                txtScoreVal.setText("0000");
            txtMyVal.setText("" + myCard);
            txtOppVal.setText("" + oppCard);
            flag = 1;

            /////// Timer to hold the cards for few seconds before animation.
            new CountDownTimer(2000, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    // After one round ..send cards back to the respective places.
                    if(flagInt==0)
                    cardTap.start();
                    Animation animation1 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.to_middle2);
                    animation2 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.from_middle2);
                    cardP1.startAnimation(animation1);
                    cardP2.startAnimation(animation1);
                    cardP3.setVisibility(View.INVISIBLE);
                    cardP3.setBackground(getResources().getDrawable(R.drawable.layout_white2));
                    animation1.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            cardP1.setVisibility(View.INVISIBLE);
                            cardP2.setVisibility(View.INVISIBLE);
                            cardBigLeft.setVisibility(View.VISIBLE);
                            cardP3.setVisibility(View.INVISIBLE);
                            cardBigRight.setVisibility(View.VISIBLE);
                            cardBigLeft.startAnimation(animation2);

                            cardBigRight.startAnimation(animation2);
                            animation2.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    Animation animation3 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.animation3);
                                    Animation animation4 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.animation4);
                                    // animation4.setStartOffset(3000);
                                    animation3.setDuration(1000);
                                    animation3.setStartOffset(1000);
                                    animation4.setDuration(1000);
                                    animation4.setStartOffset(1000);
                                    AnimatorSet set = new AnimatorSet();
                                    set.playTogether(

                                            ObjectAnimator.ofFloat(cardBigLeft, "scaleX", 1f, 0.1f),
                                            ObjectAnimator.ofFloat(cardBigLeft, "scaleY", 1f, 0.1f)
                                    );
                                    
                                    
                                    set.setDuration(1000);
                                    set.setStartDelay(1000);
                                    set.start();
                                    Animation myAnim2 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.animation2);
                                    cardBigLeft.startAnimation(myAnim2);
                                    AnimatorSet set1 = new AnimatorSet();
                                    set1.playTogether(
                                            ObjectAnimator.ofFloat(cardBigRight, "scaleX", 1f, 0.1f),
                                            ObjectAnimator.ofFloat(cardBigRight, "scaleY", 1f, 0.1f)
                                    );

                                    set1.setDuration(1000);
                                    set1.setStartDelay(1000);
                                    set1.start();
                                    new CountDownTimer(1000,100){
                                        public void onTick(long ms){

                                        }
                                        public void onFinish(){
                                            if(flagInt==0)
                                            cardWoosh.start();
                                        }
                                    }.start();
                                    if (playerNum == 2) {
                                        player = 2;
                                        Animation animation5 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.animation5);
                                        animation5.setDuration(1000);
                                        animation5.setStartOffset(1000);
                                        cardBigLeft.startAnimation(animation4);
                                        cardBigRight.startAnimation(animation5);
                                        new CountDownTimer(1000,100){
                                            public void onTick(long ms){

                                            }
                                            public void onFinish(){
                                                if(flagInt==0)
                                                cardWoosh.start();
                                            }
                                        }.start();
                                        animation4.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {
                                                flag = 1;
                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                if (myCard != 0 && oppCard != 0) {// Game not over then start next round
                                                    repeatGame();
                                                } else if (myCard == 0 || oppCard == 0) {
                                                    if (myCard != 0) {
                                                        showGameWonDialog();
                                                    } else {
                                                        showGameLoseDialog();

                                                    }
                                                }
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }
                                        });
                                    } else {
                                        player = 1;
                                        Animation animation6 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.animation6);
                                        animation6.setDuration(1000);
                                        animation6.setStartOffset(1000);
                                        
                                        
                                        cardBigLeft.startAnimation(animation6);
                                        cardBigRight.startAnimation(animation3);
                                        animation3.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {
                                                flag = 1;
                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                if (myCard != 0 && oppCard != 0) {
                                                    repeatGame();
                                                } else if (myCard == 0 || oppCard == 0) {
                                                    if (myCard != 0) {
                                                        showGameWonDialog();
                                                    } else {
                                                        showGameLoseDialog();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            }.start();
        } else if (myCard == 0 || oppCard == 0) {
            if (myCard != 0) {
                showGameWonDialog();
            } else {
                showGameLoseDialog();
            }
        }
    }

    public void showGameWonDialog() {
        soundYouWon.start();
        gameOverFlag = 1;
        final Dialog dialog = new Dialog(getApplicationContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.game_won_popup);

        // set the pause_popup dialog components - text, image and button
        ImageView image2 = dialog.findViewById(R.id.image2);
        image2.setImageResource(R.drawable.retry);
        ImageView image3 = dialog.findViewById(R.id.image3);
        image3.setImageResource(R.drawable.home);

        // Load the game again
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound.start();
                Intent abc = new Intent(GamePlayIndia.this, DeckSelect.class);
                abc.putExtra("int_value",flagInt);
                startActivity(abc);
            }
        });
        // Show main menu
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound.start();
                Intent abc = new Intent(GamePlayIndia.this, Options.class);
                abc.putExtra("int_value",flagInt);
                startActivity(abc);
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void showGameLoseDialog() {
        soundYouLose.start();
        gameOverFlag = 1;
        final Dialog dialog = new Dialog(getApplicationContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.game_lose_popup);

        // set the pause_popup dialog components - text, image and button
        ImageView image2 = dialog.findViewById(R.id.image2);
        image2.setImageResource(R.drawable.retry);
        ImageView image3 = dialog.findViewById(R.id.image3);
        image3.setImageResource(R.drawable.home);

        // Load the game again
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound.start();
                Intent abc = new Intent(GamePlayIndia.this, DeckSelect.class);
                abc.putExtra("int_value",flagInt);
                startActivity(abc);
            }
        });
        // Show main menu
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound.start();
                Intent abc = new Intent(GamePlayIndia.this, Options.class);
                abc.putExtra("int_value",flagInt);
                startActivity(abc);
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void repeatGame() {
        if (playerNum == 1) {
            setColorWhite();
            flag = 1;
            ShowRecord();
            AnimatorSet set = new AnimatorSet();
            set.playTogether(

                    ObjectAnimator.ofFloat(cardBigLeft, "scaleX", 0.1f, 1f),
                    ObjectAnimator.ofFloat(cardBigLeft, "scaleY", 0.1f, 1f)
            );
            set.setDuration(1000).start();
            
            

            Animation myAnim2 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.animation2);
            cardBigLeft.startAnimation(myAnim2);


            AnimatorSet set1 = new AnimatorSet();
            set1.playTogether(
                    ObjectAnimator.ofFloat(cardBigRight, "scaleX", 0.1f, 1f),
                    ObjectAnimator.ofFloat(cardBigRight, "scaleY", 0.1f, 1f)
            );
            set1.setDuration(1000).start();
            new CountDownTimer(1000,100){
                public void onTick(long ms){

                }
                public void onFinish(){
                    if(flagInt==0)
                    cardWoosh.start();
                }
            }.start();
            Animation myAnim1 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.animation1);
            cardBigRight.startAnimation(myAnim1);
            myAnim1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    flag = 1;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    cardP3.setVisibility(View.VISIBLE);
                    cardP3.setBackground(getResources().getDrawable(R.drawable.cardborder));
                    getBlinkAnimation();
                    cardBigLeft.setEnabled(true);
                    cardBigLeft.setClickable(true);
                    flag = 0;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else {
            ShowRecord();
            setColorWhite();
            new CountDownTimer(1500, 100) {
                public void onTick(long ms) {
                }

                public void onFinish() {
                    Animation animation1 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.to_middle2);
                    animation2 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.from_middle2);
                    cardBigRight.startAnimation(animation1);
                    
                    
                    animation1.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            cardBigRight.setVisibility(View.INVISIBLE);
                            cardP2.setVisibility(View.VISIBLE);
                            
                            
                            cardP2.startAnimation(animation2);
                            animation2.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    flag = 0;
                                    cardP3.setVisibility(View.VISIBLE);
                                    getBlinkAnimation();
                                    cardBigLeft.setEnabled(true);
                                    cardBigLeft.setClickable(true);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    int betField = controllerIndia.betDecisionComputer(modelClass1India);
                    switch (betField) {
                        case 1: {
                            areaSelectI(cardP2);
                            break;
                        }
                        case 2: {
                            populationSelectI(cardP2);
                            break;
                        }
                        case 3: {
                            districtSelectI(cardP2);
                            break;
                        }
                        case 4: {
                            nationalParkSelectI(cardP2);
                            break;
                        }
                        case 5: {
                            riverSelectI(cardP2);
                            break;
                        }
                        case 6: {
                            cropSelectI(cardP2);
                            break;
                        }
                        case 7: {
                            mineralSelectI(cardP2);
                            break;
                        }
                    }
                }
            }.start();
            // Animation : Cards coming from both sides.
            if(flagInt==0)
            cardWoosh.start();
            AnimatorSet set = new AnimatorSet();
            set.playTogether(

                    ObjectAnimator.ofFloat(cardBigLeft, "scaleX", 0.1f, 1f),
                    ObjectAnimator.ofFloat(cardBigLeft, "scaleY", 0.1f, 1f)
            );
            set.setDuration(1000).start();
            
            
            Animation myAnim2 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.animation2);
            cardBigLeft.startAnimation(myAnim2);


            AnimatorSet set1 = new AnimatorSet();
            set1.playTogether(
                    ObjectAnimator.ofFloat(cardBigRight, "scaleX", 0.1f, 1f),
                    ObjectAnimator.ofFloat(cardBigRight, "scaleY", 0.1f, 1f)
            );
            set1.setDuration(1000).start();
            Animation myAnim1 = AnimationUtils.loadAnimation(GamePlayIndia.this, R.anim.animation1);
            cardBigRight.startAnimation(myAnim1);
            myAnim1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    cardP3.setVisibility(View.VISIBLE);
                    cardP3.setBackground(getResources().getDrawable(R.drawable.cardborder));
                    getBlinkAnimation();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }


    //// Method for applying blinking border animation.
    public Animation getBlinkAnimation() {
        animation = new AlphaAnimation(1, 0);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(0);
        final AnimationDrawable drawable = new AnimationDrawable();
        final Handler handler = new Handler();
        drawable.addFrame(getResources().getDrawable(R.drawable.layout_white2), 200);
        drawable.addFrame(getResources().getDrawable(R.drawable.cardborder), 200);
        //drawable.addFrame(new ColorDrawable(Color.RED), 400);
        drawable.setOneShot(false);
        cardP3.setBackgroundDrawable(drawable);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawable.start();
            }
        }, 200);
        return animation;
    }

    public Animation getBlinkAnimation(LinearLayout l, String c1) {
        animation = new AlphaAnimation(1, 0);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(0);
        final AnimationDrawable drawable = new AnimationDrawable();
        final Handler handler = new Handler();
        drawable.addFrame(getResources().getDrawable(R.drawable.layout_white), 100);
        if (c1.equals("RED"))
            drawable.addFrame(getResources().getDrawable(R.drawable.layout_red), 200);
        else if (c1.equals("BLUE"))
            drawable.addFrame(getResources().getDrawable(R.drawable.layout_blue), 200);
        else
            drawable.addFrame(getResources().getDrawable(R.drawable.layout_green), 200);
        drawable.setOneShot(false);
        l.setBackgroundDrawable(drawable);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawable.start();
            }
        }, 0);
        return animation;
    }


    public void setColorWhite() {
        l1.setBackground(getResources().getDrawable(R.drawable.layout_white));
        l2.setBackground(getResources().getDrawable(R.drawable.layout_white));
        l3.setBackground(getResources().getDrawable(R.drawable.layout_white));
        l4.setBackground(getResources().getDrawable(R.drawable.layout_white));
        l5.setBackground(getResources().getDrawable(R.drawable.layout_white));
        l6.setBackground(getResources().getDrawable(R.drawable.layout_white));
        l7.setBackground(getResources().getDrawable(R.drawable.layout_white));

        l11.setBackground(getResources().getDrawable(R.drawable.layout_white));
        l12.setBackground(getResources().getDrawable(R.drawable.layout_white));
        l13.setBackground(getResources().getDrawable(R.drawable.layout_white));
        l14.setBackground(getResources().getDrawable(R.drawable.layout_white));
        l15.setBackground(getResources().getDrawable(R.drawable.layout_white));
        l16.setBackground(getResources().getDrawable(R.drawable.layout_white));
        l17.setBackground(getResources().getDrawable(R.drawable.layout_white));



    }

    @Override
    public void onBackPressed() {
        if(Chartboost.onBackPressed())
            return;
        else {
            pause = 1;
            clicksound.start();
            if (flagBack == 0) {
                bitmap = captureScreen(rel1);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                fullrel.setVisibility(View.INVISIBLE);
                relPopup.setVisibility(View.VISIBLE);
                Drawable d = new BitmapDrawable(getResources(), bitmap);
                relPopup.setBackground(d);
                flagBack = 1;
                Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
                pause=0;
            } else {
                relPopup.setVisibility(View.INVISIBLE);
                fullrel.setVisibility(View.VISIBLE);
                flagBack = 0;
            }

            if (gameOverFlag == 0) {
                dialog = new Dialog(GamePlayIndia.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.pause_popup);

                // set the pause_popup dialog components - text, image and button
                ImageView image = dialog.findViewById(R.id.image);
                image.setImageResource(R.drawable.backp);
                ImageView image2 = dialog.findViewById(R.id.image2);
                image2.setImageResource(R.drawable.retry);
                ImageView image3 = dialog.findViewById(R.id.image3);
                image3.setImageResource(R.drawable.home);


                // Close the pause_popup dialog
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clicksound.start();
                        dialog.dismiss();
                        relPopup.setVisibility(View.INVISIBLE);
                        fullrel.setVisibility(View.VISIBLE);
                        flagBack = 0;
                    }
                });
                // Load the game again
                image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clicksound.start();
                        Intent abc = new Intent(GamePlayIndia.this, Toss.class);
                        abc.putExtra("int_value", flagInt);
                        startActivity(abc);
                        finish();
                    }
                });
                // Show main menu
                image3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clicksound.start();
                        Intent abc = new Intent(GamePlayIndia.this, Options.class);
                        abc.putExtra("int_value", flagInt);
                        startActivity(abc);
                        finish();
                        Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            } else {
                Intent intent = new Intent(GamePlayIndia.this, Options.class);
                intent.putExtra("int_value", flagInt);
                startActivity(intent);
                finish();
            }
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    relPopup.setVisibility(View.INVISIBLE);
                    fullrel.setVisibility(View.VISIBLE);
                    flagBack = 0;
                }
            });
        }
    }

    public void backLI(View v){
        clicksound.start();
        if(flagBack==0) {
            bitmap = captureScreen(rel1);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            fullrel.setVisibility(View.INVISIBLE);
            relPopup.setVisibility(View.VISIBLE);
            Drawable d=new BitmapDrawable(getResources(),bitmap);
            relPopup.setBackground(d);
            flagBack=1;
            Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
            pause=0;
        }
        else{
            relPopup.setVisibility(View.INVISIBLE);
            fullrel.setVisibility(View.VISIBLE);
            flagBack=0;
        }

        if (gameOverFlag == 0) {
            dialog = new Dialog(GamePlayIndia.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pause_popup);

            // set the pause_popup dialog components - text, image and button
            ImageView image = dialog.findViewById(R.id.image);
            image.setImageResource(R.drawable.backp);
            ImageView image2 = dialog.findViewById(R.id.image2);
            image2.setImageResource(R.drawable.retry);
            ImageView image3 = dialog.findViewById(R.id.image3);
            image3.setImageResource(R.drawable.home);


            // Close the pause_popup dialog
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicksound.start();
                    dialog.dismiss();
                    relPopup.setVisibility(View.INVISIBLE);
                    fullrel.setVisibility(View.VISIBLE);
                    flagBack=0;
                }
            });
            // Load the game again
            image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicksound.start();
                    Intent abc = new Intent(GamePlayIndia.this, Toss.class);
                    abc.putExtra("int_value",flagInt);
                    startActivity(abc);
                    finish();
                }
            });
            // Show main menu
            image3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicksound.start();
                    Intent abc = new Intent(GamePlayIndia.this, Options.class);
                    abc.putExtra("int_value",flagInt);
                    startActivity(abc);
                    finish();
                    Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        } else {
            Intent intent = new Intent(GamePlayIndia.this, Options.class);
            intent.putExtra("int_value",flagInt);
            startActivity(intent);
            finish();
        }
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                relPopup.setVisibility(View.INVISIBLE);
                fullrel.setVisibility(View.VISIBLE);
                flagBack=0;
            }
        });
    }

    public static Bitmap captureScreen(View v) {
        Bitmap screenshot = null;
        try {
            if (v != null) {

                screenshot = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(screenshot);
                v.draw(canvas);
            }
        } catch (Exception ignored) {

        }
        return screenshot;
    }


    @Override
    public void onStop() {
        super.onStop();
        Chartboost.onStop(this);
        pause=1;
        doUnbindService();
        stopMusic();
    }

    @Override
    public void onResume() {
        super.onResume();
        Chartboost.onResume(this);
        if(pause==1) {
            Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
            pause=0;
        }
        doBindService();
        Button loudspeaker = (Button) findViewById(R.id.cardLoudspeaker);

        if (flagInt == 1) {
            loudspeaker.setBackgroundResource(R.drawable.soundoff);
            stopMusic();
        } else {
            loudspeaker.setBackgroundResource(R.drawable.soundon);
            startMusic();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Chartboost.onStart(this);
        Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
    }

    public void startMusic() {
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);
    }

    public void stopMusic() {
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        stopService(music);
    }

    public void soundOptI(View view) {
        Button speaker = (Button) view;
        clicksound.start();
        if (flagInt == 0) {
            speaker.setBackgroundResource(R.drawable.soundoff);
            mServ.pauseMusic();
            flagInt = 1;
        } else {
            speaker.setBackgroundResource(R.drawable.soundon);
            mServ.resumeMusic();
            flagInt = 0;
        }
    }
}
