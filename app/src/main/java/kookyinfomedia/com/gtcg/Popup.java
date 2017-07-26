package kookyinfomedia.com.gtcg;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;

import static kookyinfomedia.com.gtcg.LoadingScreen.bitmap;
import static kookyinfomedia.com.gtcg.LoadingScreen.gameOverFlag;
import static kookyinfomedia.com.gtcg.LoadingScreen.mPath;

public class Popup extends AppCompatActivity {
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        RelativeLayout rel=(RelativeLayout)findViewById(R.id.rel);


        if (gameOverFlag == 0) {
           dialog = new Dialog(Popup.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom);

            // set the custom dialog components - text, image and button
            ImageView image = (ImageView) dialog.findViewById(R.id.image);
            image.setImageResource(R.drawable.backp);
            ImageView image2 = (ImageView) dialog.findViewById(R.id.image2);
            image2.setImageResource(R.drawable.retry);
            ImageView image3 = (ImageView) dialog.findViewById(R.id.image3);
            image3.setImageResource(R.drawable.home);


            // Close the custom dialog
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    finish();
                }
            });
            // Load the game again
            image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent abc = new Intent(Popup.this, DeckSelect.class);
                    startActivity(abc);
                    finish();
                }
            });
            // Show main menu
            image3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent abc = new Intent(Popup.this, Options.class);
                    startActivity(abc);
                    finish();
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        } else {
            Intent intent = new Intent(Popup.this, Options.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(Popup.this,"HHHHHHHHHHHHH",Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(Popup.this,"HHHHHHHHHHHHH",Toast.LENGTH_LONG).show();
        dialog.dismiss();
        finish();
    }
    private void openScreenshot(Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }
}
