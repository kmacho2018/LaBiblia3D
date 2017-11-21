package com.kmacho.labiblia3d;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class BibleActivity extends Activity implements GestureDetector.OnGestureListener {

    PageFlipView mPageFlipView;
    GestureDetector mGestureDetector;
    int cnt = 0;

    private InterstitialAd mInterstitialAd;

    private int getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString.replace("é", "e")
                .replace("É", "E").replace("í", "i")
                .replace("ú", "u").replace("ó", "o").replace("á", "a"), "string", packageName);
        return resId;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String id = intent.getStringExtra("key");


        // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
        String texto = "";

        mPageFlipView = new PageFlipView(this, getString(getStringResourceByName(id)), getWindowManager().getDefaultDisplay());
/*
        switch (id) {

            case "Genesis":
                mPageFlipView = new PageFlipView(this, getString(getStringResourceByName(id)));

                break;
            case "Exodo":
                mPageFlipView = new PageFlipView(this, getString(R.string.exodo));
                break;
        }*/

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        setContentView(mPageFlipView);
        mGestureDetector = new GestureDetector(this, this);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            mPageFlipView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        }
    }


    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            //Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            //goToNextLevel();
        }
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LoadBitmapTask.get(this).start();
        mPageFlipView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mPageFlipView.onPause();
        LoadBitmapTask.get(this).stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        int duration = mPageFlipView.getAnimateDuration();
        if (duration == 1000) {
            menu.findItem(R.id.animation_1s).setChecked(true);
        } else if (duration == 2000) {
            menu.findItem(R.id.animation_2s).setChecked(true);
        } else if (duration == 5000) {
            menu.findItem(R.id.animation_5s).setChecked(true);
        }

        if (mPageFlipView.isAutoPageEnabled()) {
            menu.findItem(R.id.auoto_page).setChecked(true);
        } else {
            menu.findItem(R.id.single_page).setChecked(true);
        }

        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(this);
        int pixels = pref.getInt("MeshPixels", mPageFlipView.getPixelsOfMesh());
        switch (pixels) {
            case 2:
                menu.findItem(R.id.mesh_2p).setChecked(true);
                break;
            case 5:
                menu.findItem(R.id.mesh_5p).setChecked(true);
                break;
            case 10:
                menu.findItem(R.id.mesh_10p).setChecked(true);
                break;
            case 20:
                menu.findItem(R.id.mesh_20p).setChecked(true);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean isHandled = true;
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(this);

        Display display = getWindowManager().getDefaultDisplay();

        SharedPreferences.Editor editor = pref.edit();
        switch (item.getItemId()) {
            case R.id.animation_1s:
                mPageFlipView.setAnimateDuration(1000);
                editor.putInt(Constants.PREF_DURATION, 1000);
                break;
            case R.id.animation_2s:
                mPageFlipView.setAnimateDuration(2000);
                editor.putInt(Constants.PREF_DURATION, 2000);
                break;
            case R.id.animation_5s:
                mPageFlipView.setAnimateDuration(5000);
                editor.putInt(Constants.PREF_DURATION, 5000);
                break;
            case R.id.auoto_page:
                mPageFlipView.enableAutoPage(true, display);
                editor.putBoolean(Constants.PREF_PAGE_MODE, true);
                break;
            case R.id.single_page:
                mPageFlipView.enableAutoPage(false, display);
                editor.putBoolean(Constants.PREF_PAGE_MODE, false);
                break;
            case R.id.mesh_2p:
                editor.putInt(Constants.PREF_MESH_PIXELS, 2);
                break;
            case R.id.mesh_5p:
                editor.putInt(Constants.PREF_MESH_PIXELS, 5);
                break;
            case R.id.mesh_10p:
                editor.putInt(Constants.PREF_MESH_PIXELS, 10);
                break;
            case R.id.mesh_20p:
                editor.putInt(Constants.PREF_MESH_PIXELS, 20);
                break;
            case R.id.about_menu:
                //showAbout();
                return true;
            default:
                isHandled = false;
                break;
        }

        if (isHandled) {
            item.setChecked(true);
            editor.apply();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mPageFlipView.onFingerUp(event.getX(), event.getY());

           /* if (cnt == 10) {
               // showInterstitial();
                cnt = 0;
            }
            cnt = cnt + 1;
            mInterstitialAd = newInterstitialAd();
            loadInterstitial();*/

            return true;
        }

        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mPageFlipView.onFingerDown(e.getX(), e.getY());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        return false;
    }


    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        mPageFlipView.onFingerMove(e2.getX(), e2.getY());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /*private void showAbout() {
        View aboutView = getLayoutInflater().inflate(R.layout.about, null,
                false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.app_name);
        builder.setView(aboutView);
        builder.create();
        builder.show();
    }*/

}
