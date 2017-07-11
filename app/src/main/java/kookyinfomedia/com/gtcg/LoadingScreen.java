package kookyinfomedia.com.gtcg;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import static android.R.id.input;


public class LoadingScreen extends AppCompatActivity {
    Timer timer;
    TimerTask timerTask;
    int flag;
    DBAdapter obj;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading_screen);
        flag=getIntent().getIntExtra("int_value",0);
        initialize();
    }
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type:
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }
   private void initialize()
    {
        timer = new Timer();
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                Intent homeIntent = new Intent(LoadingScreen.this,Play.class);
                homeIntent.putExtra("int_value",flag);
                startActivity(homeIntent);
                finish();
            }
        };

        timer.schedule(timerTask,2500);
    }

    class Asyncnew extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                obj = DBAdapter.getDBAdapter(getApplicationContext());
                if (obj.checkDatabase() == false)
                    obj.createDatabase(getApplicationContext());
                obj.openDatabase();


                Intent abc = new Intent(LoadingScreen.this, Play.class);
                startActivity(abc);


            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));


            Intent abc = new Intent(LoadingScreen.this, Play.class);
            startActivity(abc);

        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);

            // Displaying downloaded image into image view
            // Reading image path from sdcard

        }

    }



        @Override
    public void onBackPressed(){
        super.onBackPressed();
        timer.cancel();
        Intent intent= new Intent(this,Options.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onStop(){
        super.onStop();
        timer.cancel();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        timer.cancel();
        finish();
    }
    @Override
    public void onPause(){
        super.onPause();
        timer.cancel();

    }

}
