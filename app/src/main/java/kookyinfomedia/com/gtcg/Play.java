package kookyinfomedia.com.gtcg;

/************************** Main class implementing game logic using DBAdapter, Controller and ModelClass *******************/


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static kookyinfomedia.com.gtcg.Category.selectedContinent;
import static kookyinfomedia.com.gtcg.DeckSelect.deck;
import static kookyinfomedia.com.gtcg.TossDeck.toss;

public class Play extends AppCompatActivity {
    private static final String TAG = "";
    public static int player;
    int flag = 0;
    int flagInt;
    Animation animation2, animation;
    DBAdapter obj;
    ArrayList<ModelClass> arr = new ArrayList<ModelClass>();
    Controller controller;
    ImageView imgCard1_flag, imgCard2_flag, imgCard1_map, imgCard2_map,imgLoudspeaker,imgBack,imgMyCards,imgOppCards;
    ModelClass modelClass, modelClass1;
    Button cardLoudspeaker,cardBack;
    CardView cardP1, cardP2, cardBigLeft, cardBigRight,cardCoinLeft,cardCoinRight;
    TextView txtArea, txtPopulation, txtCoastline, txtAUnits, txtBcountries, txtHPoint, txtCountry1;
    TextView valArea, valPopulation, valCoastline, valAUnits, valBCountries, valHPoint, txtCountry2;
    TextView valArea2, valPopulation2, valCoastline2, valAUnits2, valBCountries2, valHPoint2;
    TextView txtArea2, txtPopulation2, txtCoastline2, txtAUnits2, txtBcountries2, txtHPoint2;
    TextView txtMyCards, txtMyVal, txtOppCards, txtOppVal, txtScore, txtScoreVal;
    TextView txtUpper;
    LinearLayout l1, l2, l3, l4, l5, l6, l11, l12, l13, l14, l15, l16,linLayBottomLeft,linLayBottomRight;
    int betField, playerNum, updatedScore;
    Animation myAnim;

    int score = deck * 100, myCard = deck, oppCard = deck;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play);
        doBindService();
        flagInt=getIntent().getIntExtra("int_value",0);
        Button loudspeaker=(Button) findViewById(R.id.cardLoudspeaker);
        if(flagInt==1)
        {
            loudspeaker.setBackgroundResource(R.drawable.soundoff);
            stopMusic();
        }
        else{
            loudspeaker.setBackgroundResource(R.drawable.soundon);
            startMusic();
        }
        int screenHeight;
        txtUpper=(TextView)findViewById(R.id.txtUpper);
        Display display=getWindowManager().getDefaultDisplay();
        screenHeight=display.getHeight();
        ViewGroup.LayoutParams layoutParams=txtUpper.getLayoutParams();
        layoutParams.height=screenHeight/10;
        txtUpper.setLayoutParams(layoutParams);
        controller = new Controller();

        ////// acessing elements for animations: bottom linear layouts,back button, sound button
        cardLoudspeaker=(Button)findViewById(R.id.cardLoudspeaker);
        //imgLoudspeaker=(ImageView)findViewById(R.id.imgLoudspeaker);
        cardBack=(Button)findViewById(R.id.cardBack);
        cardCoinLeft=(CardView)findViewById(R.id.cardCoinLeft);
        cardCoinRight=(CardView)findViewById(R.id.cardCoinRight);
        linLayBottomLeft=(LinearLayout)findViewById(R.id.linLayBottomLeft);
        linLayBottomRight=(LinearLayout)findViewById(R.id.linLayBottomRight);

        ////accessing everything ! ////for first player
        cardP1 = (CardView) findViewById(R.id.cardP1);
        cardP1.setVisibility(View.INVISIBLE);
        cardBigLeft = (CardView) findViewById(R.id.cardBigLeft);

        imgCard1_flag = (ImageView) findViewById(R.id.imgCard1_flag);
        imgCard1_map = (ImageView) findViewById(R.id.imgCard1_map);

        txtCountry1 = (TextView) findViewById(R.id.txtCountry1);
        txtArea = (TextView) findViewById(R.id.txtArea);
        txtPopulation = (TextView) findViewById(R.id.txtPopulation);
        txtCoastline = (TextView) findViewById(R.id.txtCoastline);
        txtAUnits = (TextView) findViewById(R.id.txtAUnits);
        txtBcountries = (TextView) findViewById(R.id.txtBCountries);
        txtHPoint = (TextView) findViewById(R.id.txtHPoint);

        valArea = (TextView) findViewById(R.id.valArea);
        valPopulation = (TextView) findViewById(R.id.valPopulation);
        valCoastline = (TextView) findViewById(R.id.valCoastline);
        valAUnits = (TextView) findViewById(R.id.valAUnits);
        valBCountries = (TextView) findViewById(R.id.valBCountries);
        valHPoint = (TextView) findViewById(R.id.valHPoint);

        l1 = (LinearLayout) findViewById(R.id.linLay1);
        l1.setOnTouchListener(new View.OnTouchListener() {

                                  @Override
                                  public boolean onTouch(View view, MotionEvent event) {
                                      setColorWhite();
                                      if (event.getAction() == MotionEvent.ACTION_UP) {
                                          l1.setBackgroundColor(Color.LTGRAY);

                                      } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                          l1.setBackgroundColor(Color.WHITE);
                                      }
                                      return false;
                                  }
        });


        l2 = (LinearLayout) findViewById(R.id.linLay2);
        l2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                setColorWhite();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    l2.setBackgroundColor(Color.LTGRAY);
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    l2.setBackgroundColor(Color.WHITE);
                }
                return false;
            }
        });

        l3 = (LinearLayout) findViewById(R.id.linLay3);
        l3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                setColorWhite();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    l3.setBackgroundColor(Color.LTGRAY);
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    l3.setBackgroundColor(Color.WHITE);
                }
                return false;
            }
        });

        l4 = (LinearLayout) findViewById(R.id.linLay4);
        l4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                setColorWhite();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    l4.setBackgroundColor(Color.LTGRAY);
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    l4.setBackgroundColor(Color.WHITE);
                }
                return false;
            }
        });

        l5 = (LinearLayout) findViewById(R.id.linLay5);
        l5.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                setColorWhite();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    l5.setBackgroundColor(Color.LTGRAY);
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    l5.setBackgroundColor(Color.WHITE);
                }
                return false;
            }
        });

        l6 = (LinearLayout) findViewById(R.id.linLay6);
        l6.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                setColorWhite();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    l6.setBackgroundColor(Color.LTGRAY);
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    l6.setBackgroundColor(Color.WHITE);
                }
                return false;
            }
        });



        /////////////////  for second player
        cardP2 = (CardView) findViewById(R.id.cardP2);
        cardP2.setVisibility(View.INVISIBLE);
        cardBigRight = (CardView) findViewById(R.id.cardBigRight);

        imgCard2_flag = (ImageView) findViewById(R.id.imgCard2_flag);
        imgCard2_map = (ImageView) findViewById(R.id.imgCard2_map);

        txtCountry2 = (TextView) findViewById(R.id.txtCountry2);
        txtArea2 = (TextView) findViewById(R.id.txtArea2);
        txtPopulation2 = (TextView) findViewById(R.id.txtPopulation2);
        txtCoastline2 = (TextView) findViewById(R.id.txtCoastline2);
        txtAUnits2 = (TextView) findViewById(R.id.txtAUnits2);
        txtBcountries2 = (TextView) findViewById(R.id.txtBCountries2);
        txtHPoint2 = (TextView) findViewById(R.id.txtHPoint2);

        valArea2 = (TextView) findViewById(R.id.valArea2);
        valPopulation2 = (TextView) findViewById(R.id.valPopulation2);
        valCoastline2 = (TextView) findViewById(R.id.valCoastline2);
        valAUnits2 = (TextView) findViewById(R.id.valAUnits2);
        valBCountries2 = (TextView) findViewById(R.id.valBCountries2);
        valHPoint2 = (TextView) findViewById(R.id.valHPoint2);

        l11 = (LinearLayout) findViewById(R.id.linLay11);
        l12 = (LinearLayout) findViewById(R.id.linLay12);
        l13 = (LinearLayout) findViewById(R.id.linLay13);
        l14 = (LinearLayout) findViewById(R.id.linLay14);
        l15 = (LinearLayout) findViewById(R.id.linLay15);
        l16 = (LinearLayout) findViewById(R.id.linLay16);

        ////////// acessing scores and cards for both players
        txtMyCards = (TextView) findViewById(R.id.txtMyCards);
        txtMyVal = (TextView) findViewById(R.id.txtMyVal);
        txtOppCards = (TextView) findViewById(R.id.txtOppCards);
        txtOppVal = (TextView) findViewById(R.id.txtOppVal);
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtScoreVal = (TextView) findViewById(R.id.txtScoreVal);

        cardBigLeft.setVisibility(View.INVISIBLE);
        cardBigRight.setVisibility(View.INVISIBLE);
        playAnimation();
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardBigLeft.setVisibility(View.VISIBLE);
                cardBigRight.setVisibility(View.VISIBLE);
                txtMyVal.setText("" + myCard);
                txtOppVal.setText("" + oppCard);
                txtScoreVal.setText("" + score);
                clickoff();
                //Toast.makeText(Play.this,""+selectedContinent,Toast.LENGTH_LONG).show();
                if(toss==0)
                    playerNum = 2;
                else
                    playerNum=1;


                //// Using DBAdapter class for fetching data from database ///////
                obj = DBAdapter.getDBAdapter(getApplicationContext());
                if (obj.checkDatabase() == false)
                    obj.createDatabase(getApplicationContext());
                obj.openDatabase();

                arr = obj.getData();
                ShowRecord();
                if (playerNum == 1) {
                    player=1;
                    /// Blinking animation over border of first card
                    getBlinkAnimation();
                    cardBigLeft.startAnimation(getBlinkAnimation());
                } else {
                    flag = 1;
                    player = 2;
                    new CountDownTimer(1500, 100) {
                        public void onTick(long ms) {
                        }

                        public void onFinish() {
                            cardBigRight.setVisibility(View.INVISIBLE);
                            cardP2.setVisibility(View.VISIBLE);
                            Animation animation1 = AnimationUtils.loadAnimation(Play.this, R.anim.card_rotate);
                            animation2 = AnimationUtils.loadAnimation(Play.this, R.anim.card_rotate);
                            cardP2.startAnimation(animation1);
                            int betField = controller.betDecisionComputer(modelClass1);
                            //Toast.makeText(Play.this,""+betField,Toast.LENGTH_SHORT).show();
                            switch (betField) {
                                case 1: {
                                    //Toast.makeText(Play.this,""+modelClass1.getArea(),Toast.LENGTH_SHORT).show();
                                    areaSelect(cardP2);
                                    break;
                                }
                                case 2: {
                                    //Toast.makeText(Play.this,""+modelClass1.getPopulation(),Toast.LENGTH_SHORT).show();
                                    populationSelect(cardP2);
                                    break;
                                }
                                case 3: {
                                    //Toast.makeText(Play.this,""+modelClass1.getCoastline(),Toast.LENGTH_SHORT).show();
                                    coastSelect(cardP2);
                                    break;
                                }
                                case 4: {
                                    //Toast.makeText(Play.this,""+modelClass1.getaUnits(),Toast.LENGTH_SHORT).show();
                                    aUnitSelect(cardP2);
                                    break;
                                }
                                case 5: {
                                    //Toast.makeText(Play.this,""+modelClass1.getbCountries(),Toast.LENGTH_SHORT).show();
                                    bCountriesSelect(cardP2);
                                    break;
                                }
                                case 6: {
                                    //Toast.makeText(Play.this,""+modelClass1.gethPoint(),Toast.LENGTH_SHORT).show();
                                    hPointSelect(cardP2);
                                    break;
                                }
                            }
                            getBlinkAnimation();
                            flag = 0;


                        }
                    }.start();
                }
                // Animation : Cards coming from both sides.
                AnimatorSet set = new AnimatorSet();
                set.playTogether(

                        ObjectAnimator.ofFloat(cardBigLeft, "scaleX", 0.1f, 1f),
                        ObjectAnimator.ofFloat(cardBigLeft, "scaleY", 0.1f, 1f)
                );
                set.setDuration(1000).start();

                Animation myAnim2 = AnimationUtils.loadAnimation(Play.this, R.anim.animation2);
                cardBigLeft.startAnimation(myAnim2);


                AnimatorSet set1 = new AnimatorSet();
                set1.playTogether(
                        ObjectAnimator.ofFloat(cardBigRight, "scaleX", 0.1f, 1f),
                        ObjectAnimator.ofFloat(cardBigRight, "scaleY", 0.1f, 1f)
                );
                set1.setDuration(1000).start();
                Animation myAnim1 = AnimationUtils.loadAnimation(Play.this, R.anim.animation1);
                cardBigRight.startAnimation(myAnim1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void playAnimation(){
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.3, 15);
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
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
        cardBack.startAnimation(myAnim);
        // linear layout bottom left
        myAnim.setStartOffset(1400);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);
        linLayBottomLeft.startAnimation(myAnim);
        // linear layout bottom right
        myAnim.setStartOffset(1500);
        myAnim.setDuration(1000);
        myAnim.setInterpolator(interpolator);
        linLayBottomRight.startAnimation(myAnim);
    }

    /////////// Method for choosing the record and showing data over the cards. ///////////////////////
    public void ShowRecord() {
        int x, y;
        do {
            Random ran = new Random();
            x = ran.nextInt(arr.size());
            y = ran.nextInt(arr.size());
            //valArea.setText(arr.get(count).getCountry());
        } while (x == y);

        txtCountry1.setText(arr.get(x).getCountry());
        valArea.setText(arr.get(x).getArea());
        valPopulation.setText(arr.get(x).getPopulation());
        valCoastline.setText(arr.get(x).getCoastline());
        valAUnits.setText(arr.get(x).getaUnits());
        valBCountries.setText(arr.get(x).getbCountries());
        valHPoint.setText(arr.get(x).gethPoint());
        imgCard1_flag.setImageBitmap(convertToBitmap(arr.get(x).getFlag()));
        imgCard1_map.setImageBitmap(convertToBitmap(arr.get(x).getMap()));

        modelClass = new ModelClass();
        modelClass1 = new ModelClass();

        modelClass.setCountry(arr.get(x).getCountry());
        modelClass.setArea(arr.get(x).getArea());
        modelClass.setPopulation(arr.get(x).getPopulation());
        modelClass.setCoastline(arr.get(x).getCoastline());
        modelClass.setaUnits(arr.get(x).getaUnits());
        modelClass.setbCountries(arr.get(x).getbCountries());
        modelClass.sethPoint(arr.get(x).gethPoint());
        modelClass.setFlag(arr.get(x).getFlag());
        modelClass.setMap(arr.get(x).getMap());

        txtCountry2.setText(arr.get(y).getCountry());
        valArea2.setText(arr.get(y).getArea());
        valPopulation2.setText(arr.get(y).getPopulation());
        valCoastline2.setText(arr.get(y).getCoastline());
        valAUnits2.setText(arr.get(y).getaUnits());
        valBCountries2.setText(arr.get(y).getbCountries());
        valHPoint2.setText(arr.get(y).gethPoint());
        imgCard2_flag.setImageBitmap(convertToBitmap(arr.get(y).getFlag()));
        imgCard2_map.setImageBitmap(convertToBitmap(arr.get(y).getMap()));

        modelClass1.setCountry(arr.get(y).getCountry());
        modelClass1.setArea(arr.get(y).getArea());
        modelClass1.setPopulation(arr.get(y).getPopulation());
        modelClass1.setCoastline(arr.get(y).getCoastline());
        modelClass1.setaUnits(arr.get(y).getaUnits());
        modelClass1.setbCountries(arr.get(y).getbCountries());
        modelClass1.sethPoint(arr.get(y).gethPoint());
        modelClass.setFlag(arr.get(y).getFlag());
        modelClass.setMap(arr.get(y).getMap());
    }

    private Bitmap convertToBitmap(byte[] b) {

        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }
    public void clickoff() {
        l1.setClickable(false);
        l2.setClickable(false);
        l3.setClickable(false);
        l4.setClickable(false);
        l5.setClickable(false);
        l6.setClickable(false);
    }

    public void clickon() {
        l1.setClickable(true);
        l2.setClickable(true);
        l3.setClickable(true);
        l4.setClickable(true);
        l5.setClickable(true);
        l6.setClickable(true);
    }

    /////// Method to be called when user clicks his card ////////
    public void showCard(View v) {
        cardBigLeft.setBackgroundColor(Color.WHITE);
        if (flag == 0) {
            //Toast.makeText(this, "CLICKED", Toast.LENGTH_LONG);
            cardBigLeft.setVisibility(View.INVISIBLE);
            cardP1.setVisibility(View.VISIBLE);
            Animation animation1 = AnimationUtils.loadAnimation(Play.this, R.anim.card_rotate);
            animation2 = AnimationUtils.loadAnimation(Play.this, R.anim.card_rotate);
            cardP1.startAnimation(animation1);
            clickon();
            if (playerNum == 2) {
                clickoff();
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
    }

    /////////// Method to be called if user selects area for betting. ///////////////////////
    public void areaSelect(View v) {
        //Toast.makeText(this,"Area",Toast.LENGTH_LONG).show();
        //touchOff();


        betField = 1;
        setColorWhite();
        getBlinkAnimation(l1,"GREEN");
        getBlinkAnimation(l11,"RED");
        //l1.setBackgroundColor(getResources().getColor(R.color.colorLight));
        //l11.setBackgroundColor(getResources().getColor(R.color.colorLight));
        if (playerNum == 1) {
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
                    new CountDownTimer(1000,100){
                        public void onTick(long ms){

                        }
                        public void onFinish(){
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
    }

    /////////// Method to be called if user selects population for betting. //////////////////////
    public void populationSelect(View v) {
       // touchOff();
        betField = 2;
        //Toast.makeText(this,"Pop",Toast.LENGTH_LONG).show();
        setColorWhite();
        //l2.setBackgroundColor(getResources().getColor(R.color.colorLight));
        //l12.setBackgroundColor(getResources().getColor(R.color.colorLight));
        getBlinkAnimation(l2,"GREEN");
        getBlinkAnimation(l12,"RED");
        if (playerNum == 1) {
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
    }

    /////////// Method to be called if user selects coastline for betting. ////////////////////////////
    public void coastSelect(View v) {
       // touchOff();
        //Toast.makeText(this,"Coast",Toast.LENGTH_LONG).show();
        betField = 3;
        setColorWhite();
        //l3.setBackgroundColor(getResources().getColor(R.color.colorLight));
        //l13.setBackgroundColor(getResources().getColor(R.color.colorLight));
        getBlinkAnimation(l3,"GREEN");
        getBlinkAnimation(l13,"RED");
        if (playerNum == 1) {
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
    }

    /////////// Method to be called if user selects administrative units for betting. //////////////////////////
    public void aUnitSelect(View v) {
        //touchOff();
        betField = 4;
        //Toast.makeText(this,"Aunit",Toast.LENGTH_LONG).show();
        setColorWhite();
        getBlinkAnimation(l4,"GREEN");
        getBlinkAnimation(l14,"RED");
        //l4.setBackgroundColor(getResources().getColor(R.color.colorLight));
        //l14.setBackgroundColor(getResources().getColor(R.color.colorLight));
        if (playerNum == 1) {
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
    }

    /////////// Method to be called if user selects Bordering Countries for betting. //////////////////////////
    public void bCountriesSelect(View v) {
       // touchOff();
        betField = 5;
        //Toast.makeText(this,"bCountry",Toast.LENGTH_LONG).show();
        setColorWhite();
        getBlinkAnimation(l5,"GREEN");
        getBlinkAnimation(l15,"RED");
        //l5.setBackgroundColor(getResources().getColor(R.color.colorLight));
        //l15.setBackgroundColor(getResources().getColor(R.color.colorLight));
        if (playerNum == 1) {
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
    }


    /////////// Method to be called if user selects highest point for betting. ////////////////////
    public void hPointSelect(View v) {
        //touchOff();
        betField = 6;
        //Toast.makeText(this,"hPoint",Toast.LENGTH_LONG).show();
        setColorWhite();
        getBlinkAnimation(l6,"GREEN");
        getBlinkAnimation(l16,"RED");
        //l6.setBackgroundColor(getResources().getColor(R.color.colorLight));
        //l16.setBackgroundColor(getResources().getColor(R.color.colorLight));
        if (playerNum == 1) {
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
    }
    public void touchOff(){
        l1.setOnTouchListener(null);
        l2.setOnTouchListener(null);
        l3.setOnTouchListener(null);
        l4.setOnTouchListener(null);
        l5.setOnTouchListener(null);
        l6.setOnTouchListener(null);

    }

    ///////////// Method for implementing the game logic. //////////////////
    public void updates() {
        if (myCard != 0 && oppCard != 0) {
            playerNum = 0;
            if (player == 1)
                playerNum = controller.checkWin(modelClass, modelClass1, betField);
            else
                playerNum = controller.checkWin(modelClass1, modelClass, betField);
            updatedScore = controller.updateScore(playerNum);
            if (updatedScore == 1) {
                score = score + 100;
                myCard = myCard + 1;
                oppCard = oppCard - 1;
                switch(betField){
                    case 1:{
                        getBlinkAnimation(l1,"GREEN");
                        getBlinkAnimation(l11,"RED");
                        break;
                    }
                    case 2:{
                        getBlinkAnimation(l2,"GREEN");
                        getBlinkAnimation(l12,"RED");
                        break;
                    }
                    case 3:{
                        getBlinkAnimation(l3,"GREEN");
                        getBlinkAnimation(l13,"RED");
                        break;
                    }
                    case 4:{
                        getBlinkAnimation(l4,"GREEN");
                        getBlinkAnimation(l14,"RED");
                        break;
                    }
                    case 5:{
                        getBlinkAnimation(l5,"GREEN");
                        getBlinkAnimation(l15,"RED");
                        break;
                    }
                    case 6:{
                        getBlinkAnimation(l6,"GREEN");
                        getBlinkAnimation(l16,"RED");
                        break;
                    }
                }
            } else if (updatedScore == 0) {
                score = score - 100;
                myCard = myCard - 1;
                oppCard = oppCard + 1;
                switch(betField){
                    case 1:{
                        getBlinkAnimation(l11,"GREEN");
                        getBlinkAnimation(l1,"RED");
                        break;
                    }
                    case 2:{
                        getBlinkAnimation(l12,"GREEN");
                        getBlinkAnimation(l2,"RED");
                        break;
                    }
                    case 3:{
                        getBlinkAnimation(l13,"GREEN");
                        getBlinkAnimation(l3,"RED");
                        break;
                    }
                    case 4:{
                        getBlinkAnimation(l14,"GREEN");
                        getBlinkAnimation(l4,"RED");
                        break;
                    }
                    case 5:{
                        getBlinkAnimation(l15,"GREEN");
                        getBlinkAnimation(l5,"RED");
                        break;
                    }
                    case 6:{
                        getBlinkAnimation(l16,"GREEN");
                        getBlinkAnimation(l6,"RED");
                        break;
                    }
                }
            }
            txtScoreVal.setText("" + score);
            txtMyVal.setText("" + myCard);
            txtOppVal.setText("" + oppCard);
            flag = 1;
            Toast.makeText(this, "Score and cards updated. Play again.", Toast.LENGTH_SHORT).show();

            /////// Timer to hold the cards for few seconds before animation.
            new CountDownTimer(4000, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    // After one round ..send cards back to the respective place.
                    cardP1.setVisibility(View.INVISIBLE);
                    cardP2.setVisibility(View.INVISIBLE);
                    Animation animation1 = AnimationUtils.loadAnimation(Play.this, R.anim.card_rotate);
                    animation2 = AnimationUtils.loadAnimation(Play.this, R.anim.card_rotate);
                    animation2.setStartOffset(1000);
                    animation2.setDuration(1000);
                    cardBigLeft.startAnimation(animation1);
                    cardBigRight.startAnimation(animation1);


                    Animation animation3 = AnimationUtils.loadAnimation(Play.this, R.anim.animation3);
                    Animation animation4 = AnimationUtils.loadAnimation(Play.this, R.anim.animation4);
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
                    Animation myAnim2 = AnimationUtils.loadAnimation(Play.this, R.anim.animation2);
                    cardBigLeft.startAnimation(myAnim2);
                    AnimatorSet set1 = new AnimatorSet();
                    set1.playTogether(
                            ObjectAnimator.ofFloat(cardBigRight, "scaleX", 1f, 0.1f),
                            ObjectAnimator.ofFloat(cardBigRight, "scaleY", 1f, 0.1f)
                    );
                    set1.setDuration(1000);
                    set1.setStartDelay(1000);
                    set1.start();
                    if (playerNum == 2) {
                        player = 2;
                        cardBigLeft.startAnimation(animation4);
                        cardBigRight.startAnimation(animation4);
                        animation4.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                flag = 1;
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (myCard != 0 && oppCard != 0) {// Game not over then start next round
                                    repeatGame();
                                }
                                else if (myCard == 0 || oppCard == 0) {
                                    if (myCard != 0)
                                        Toast.makeText(Play.this, "Game over, You WON !", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(Play.this, "Game over, You LOSE !", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    } else {
                        player = 1;
                        cardBigLeft.startAnimation(animation3);
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
                                }
                                else if (myCard == 0 || oppCard == 0) {
                                    if (myCard != 0)
                                        Toast.makeText(Play.this, "Game over, You WON !", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(Play.this, "Game over, You LOSE !", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                    cardBigLeft.setVisibility(View.VISIBLE);
                    cardBigRight.setVisibility(View.VISIBLE);
                }
            }.start();


        } else if (myCard == 0 || oppCard == 0) {
            if (myCard != 0)
                Toast.makeText(this, "Game over, You WON !", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Game over, You LOSE !", Toast.LENGTH_SHORT).show();
        }
    }


    public void repeatGame() {
        if (playerNum == 1) {
            setColorWhite();
            //cardBigLeft.setBackgroundColor(Color.GREEN);
            getBlinkAnimation();
            flag = 1;
            ShowRecord();
            AnimatorSet set = new AnimatorSet();
            set.playTogether(

                    ObjectAnimator.ofFloat(cardBigLeft, "scaleX", 0.1f, 1f),
                    ObjectAnimator.ofFloat(cardBigLeft, "scaleY", 0.1f, 1f)
            );
            set.setDuration(1000).start();

            Animation myAnim2 = AnimationUtils.loadAnimation(Play.this, R.anim.animation2);
            cardBigLeft.startAnimation(myAnim2);


            AnimatorSet set1 = new AnimatorSet();
            set1.playTogether(
                    ObjectAnimator.ofFloat(cardBigRight, "scaleX", 0.1f, 1f),
                    ObjectAnimator.ofFloat(cardBigRight, "scaleY", 0.1f, 1f)
            );
            set1.setDuration(1000).start();
            Animation myAnim1 = AnimationUtils.loadAnimation(Play.this, R.anim.animation1);
            cardBigRight.startAnimation(myAnim1);
            myAnim1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    flag = 1;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    cardBigLeft.setEnabled(true);
                    cardBigLeft.setClickable(true);
                    flag = 0;
                    //Toast.makeText(Play.this,"flag"+flag,Toast.LENGTH_LONG).show();
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
                    getBlinkAnimation();
                    cardBigRight.setVisibility(View.INVISIBLE);
                    cardP2.setVisibility(View.VISIBLE);
                    Animation animation1 = AnimationUtils.loadAnimation(Play.this, R.anim.card_rotate);
                    animation2 = AnimationUtils.loadAnimation(Play.this, R.anim.card_rotate);
                    cardP2.startAnimation(animation1);
                    int betField = controller.betDecisionComputer(modelClass1);
                    animation1.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            flag=0;
                            cardBigLeft.setEnabled(true);
                            cardBigLeft.setClickable(true);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    switch (betField) {
                        case 1: {
                            //Toast.makeText(Play.this,""+modelClass1.getArea(),Toast.LENGTH_SHORT).show();
                            areaSelect(cardP2);
                            break;
                        }
                        case 2: {
                            //Toast.makeText(Play.this,""+modelClass1.getPopulation(),Toast.LENGTH_SHORT).show();
                            populationSelect(cardP2);
                            break;
                        }
                        case 3: {
                            //Toast.makeText(Play.this,""+modelClass1.getCoastline(),Toast.LENGTH_SHORT).show();
                            coastSelect(cardP2);
                            break;
                        }
                        case 4: {
                            //Toast.makeText(Play.this,""+modelClass1.getaUnits(),Toast.LENGTH_SHORT).show();
                            aUnitSelect(cardP2);
                            break;
                        }
                        case 5: {
                            //Toast.makeText(Play.this,""+modelClass1.getbCountries(),Toast.LENGTH_SHORT).show();
                            bCountriesSelect(cardP2);
                            break;
                        }
                        case 6: {
                            //Toast.makeText(Play.this,""+modelClass1.gethPoint(),Toast.LENGTH_SHORT).show();
                            hPointSelect(cardP2);
                            break;
                        }
                    }
                }
            }.start();
            // Animation : Cards coming from both sides.
            AnimatorSet set = new AnimatorSet();
            set.playTogether(

                    ObjectAnimator.ofFloat(cardBigLeft, "scaleX", 0.1f, 1f),
                    ObjectAnimator.ofFloat(cardBigLeft, "scaleY", 0.1f, 1f)
            );
            set.setDuration(1000).start();

            Animation myAnim2 = AnimationUtils.loadAnimation(Play.this, R.anim.animation2);
            cardBigLeft.startAnimation(myAnim2);


            AnimatorSet set1 = new AnimatorSet();
            set1.playTogether(
                    ObjectAnimator.ofFloat(cardBigRight, "scaleX", 0.1f, 1f),
                    ObjectAnimator.ofFloat(cardBigRight, "scaleY", 0.1f, 1f)
            );
            set1.setDuration(1000).start();
            Animation myAnim1 = AnimationUtils.loadAnimation(Play.this, R.anim.animation1);
            cardBigRight.startAnimation(myAnim1);
        }
    }


    //// Method for applying blinking border animation.
    public Animation getBlinkAnimation() {
        animation = new AlphaAnimation(1, 0);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(0);
        final AnimationDrawable drawable = new AnimationDrawable();
        final Handler handler = new Handler();
        drawable.addFrame(new ColorDrawable(Color.WHITE), 200);
        drawable.addFrame(getDrawable(R.drawable.cardborder), 400);
        //drawable.addFrame(new ColorDrawable(Color.RED), 400);
        drawable.setOneShot(false);
        cardBigLeft.setBackgroundDrawable(drawable);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawable.start();
            }
        }, 500);
        return animation;
    }
    public Animation getBlinkAnimation(LinearLayout l,String c1) {
        animation = new AlphaAnimation(1, 0);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(0);
        final AnimationDrawable drawable = new AnimationDrawable();
        final Handler handler = new Handler();
        drawable.addFrame(new ColorDrawable(Color.WHITE), 200);
        //drawable.addFrame(getDrawable(R.drawable.layoutgreen), 400);
        if(c1.equals("RED"))
            drawable.addFrame(new ColorDrawable(Color.RED), 400);
        else
            drawable.addFrame(new ColorDrawable(Color.GREEN), 400);
        drawable.setOneShot(false);
        l.setBackgroundDrawable(drawable);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawable.start();
            }
        }, 300);
        return animation;
    }


    public void setColorWhite() {
        l1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        l2.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        l3.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        l4.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        l5.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        l6.setBackgroundColor(getResources().getColor(R.color.colorWhite));

        l11.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        l12.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        l13.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        l14.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        l15.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        l16.setBackgroundColor(getResources().getColor(R.color.colorWhite));


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Options.class);
        intent.putExtra("int_value",flagInt);
        startActivity(intent);
        finish();
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
        Button loudspeaker=(Button) findViewById(R.id.cardLoudspeaker);

        if(flagInt==1)
        {
            loudspeaker.setBackgroundResource(R.drawable.soundoff);
            stopMusic();
        }
        else{
            loudspeaker.setBackgroundResource(R.drawable.soundon);
            startMusic();
        }
    }

    public void back(View v) {
        Intent intent = new Intent(Play.this, Options.class);
        intent.putExtra("int_value",flagInt);
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
    public void soundOpt(View view)
    {
        Button speaker =(Button) view;
        if(flagInt==0)
        {
            speaker.setBackgroundResource(R.drawable.soundoff);
            mServ.pauseMusic();
            flagInt=1;
        }else{
            speaker.setBackgroundResource(R.drawable.soundon);
            mServ.resumeMusic();
            flagInt=0;
        }
    }
}
