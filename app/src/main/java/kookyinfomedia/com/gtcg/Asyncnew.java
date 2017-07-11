package kookyinfomedia.com.gtcg;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import static android.app.Activity.RESULT_OK;
import static kookyinfomedia.com.gtcg.LoadingScreen.progress_bar_type;

/**
 * Created by shagun choudhary on 7/11/2017.
 */

public class Asyncnew extends AsyncTask<String,String,String>{
DBAdapter obj;
    Context v;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    /**
     * Before starting background thread
     * Show Progress Bar Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
     //   showDialog(progress_bar_type);
    }

    /**
     * Downloading file in background thread
     */
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            obj = DBAdapter.getDBAdapter(null);
            if (obj.checkDatabase() == false)
                obj.createDatabase(null);
            obj.openDatabase();




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



    }

    /**
     * After completing background task
     * Dismiss the progress dialog
     **/
    @Override
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after the file was downloaded
        //dismissDialog(progress_bar_type);

        // Displaying downloaded image into image view
        // Reading image path from sdcard

    }

}
