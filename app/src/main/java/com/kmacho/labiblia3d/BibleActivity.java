package com.kmacho.labiblia3d;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
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
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BibleActivity extends Activity implements GestureDetector.OnGestureListener, TextToSpeech.OnInitListener {

    PageFlipView mPageFlipView;
    GestureDetector mGestureDetector;
    int cnt = 0;
    TextToSpeech textToSpeech1;
    int MY_DATA_CHECK_CODE = 1000;

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

        Intent intent2 = new Intent();
        intent2.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(intent2, MY_DATA_CHECK_CODE);


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                textToSpeech1 = new TextToSpeech(this, this);
                Intent intent = getIntent();

                String id = intent.getStringExtra("key");


                // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
                mInterstitialAd = newInterstitialAd();
                loadInterstitial();
                String texto = "";

                mPageFlipView = new PageFlipView(this, getString(getStringResourceByName(id)), getWindowManager().getDefaultDisplay(), textToSpeech1);

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
            } else {
                Intent intent = new Intent();
                intent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(intent);
            }
        }
    }


    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            Intent intent = getIntent();
            String id = intent.getStringExtra("key");
            String text = getString(getStringResourceByName(id));

            List<String> paragraphList = new ArrayList<String>();
            String[] splitText = text.split(" ");

            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            int width = size.x;
            int height = size.y;

            int lettersPerLine = 26;
            int LimitRows = 24;

            //Tablets
            if ((width >= 1190 && width <= 1210) && (height >= 1420 && height <= 1470)) { // 1920 x 1080
                lettersPerLine = 21;
                LimitRows = 23;
            } else if ((width >= 580 && width <= 620) && (height >= 900 && height <= 1024)) { // 600 - 1024
                lettersPerLine = 28;
                LimitRows = 19;
            } else if ((width >= 790 && width <= 820) && (height >= 1100 && height <= 1290)) { // 800 - 1280
                lettersPerLine = 28;
                LimitRows = 25;
                if ((width >= 800 && width <= 810) && (height >= 1100 && height <= 1190)) { //1280 - 800)
                    lettersPerLine = 22;
                    LimitRows = 23;
                }
                if ((width >= 800 && width <= 810) && (height >= 1210 && height <= 1220)) { //nexus 7 2012 (800 - 1280)
                    lettersPerLine = 19;
                    LimitRows = 19;
                }
            } else if ((width >= 1100 && width <= 1210) && (height >= 1800 && height <= 1940)) { // 1200 - 1920
                lettersPerLine = 18;
                LimitRows = 24;
                if ((width >= 1200 && width <= 1210) && (height >= 1810 && height <= 1830)) {
                    lettersPerLine = 19;
                    LimitRows = 30;
                }
            } else if ((width >= 1500 && width <= 2048) && (height >= 1500 && height <= 2000)) { // 2048 - 1530
                lettersPerLine = 25;
                LimitRows = 26;
                if ((width >= 1530 && width <= 1545) && (height >= 1900 && height <= 1910)) { //1536 x 2048
                    lettersPerLine = 20;
                    LimitRows = 24;
                }
            } else if ((width >= 1500 && width <= 2048) && (height >= 2000 && height <= 2600)) { // 2560 - 1600
                lettersPerLine = 25;
                LimitRows = 26;
                if ((width >= 1790 && width <= 1810) && (height >= 2450 && height <= 2470)) { //2560  - 1800
                    lettersPerLine = 32;
                    LimitRows = 32;
                }
                if ((width >= 1590 && width <= 1610) && (height >= 2454 && height <= 2474)) { //1600-2464
                    lettersPerLine = 30;
                    LimitRows = 32;
                }
            }

            //Cellphones
            else if ((width >= 450 && width <= 490) && (height >= 750 && height <= 810)) { // 480 x 800
                lettersPerLine = 23;
                LimitRows = 15;
            } else if ((width >= 470 && width <= 490) && (height >= 840 && height <= 860)) { // 480x854
                lettersPerLine = 23;
                LimitRows = 16;
            } else if ((width >= 720 && width <= 750) && (height >= 1100 && height <= 1290)) { // 720 x 1280
                lettersPerLine = 28;
                LimitRows = 24;
            } else if ((width >= 750 && width <= 780) && (height >= 1100 && height <= 1290)) { // 768 x 1280
                lettersPerLine = 27;
                LimitRows = 24;
            } else if ((width >= 1000 && width <= 1100) && (height >= 1600 && height <= 1950)) { // 1080 x 1920
                lettersPerLine = 27;
                LimitRows = 36;
            } else if ((width >= 1000 && width <= 1460) && (height >= 1600 && height <= 2580)) { // 1440 x 2560
                lettersPerLine = 24;
                LimitRows = 41;
                if ((width >= 1190 && width <= 1210) && (height >= 1760 && height <= 1780)) { // 1920  x 1200 ->Tablet
                    lettersPerLine = 24;
                    LimitRows = 30;
                }
            }


            int cnt = 0, cnt2 = 0, cnt3 = 0;
            String paragraph = "";
            for (String word : splitText
                    ) {
                int wordLength = word.length();
                if (word.replace("\n\n", "\n").contains("\n")) {
                    word = word.replace("\n\n", "\n");
                    String[] words = word.split("\n");
                    if (words.length > 0) {
                        word = words[0];
                    }
                    if (words.length == 0) {
                        paragraphList.add(paragraph);
                        paragraph = String.format(" %s", word);
                        paragraphList.add(paragraph);
                        cnt = 0;
                    } else {
                        if (cnt + wordLength <= lettersPerLine) {
                            paragraph = paragraph + String.format(" %s", word);
                            cnt = cnt + wordLength;
                        } else {
                            paragraphList.add(paragraph);
                            paragraph = String.format(" %s", word);
                            cnt = 0;
                        }
                        cnt = lettersPerLine + 1;
                        if (words.length > 1) {
                            word = words[1];
                        } else {
                            word = words[0];
                        }
                        if (cnt + wordLength <= lettersPerLine) {
                            paragraph = paragraph + String.format(" %s", word);
                            cnt = cnt + wordLength;
                        } else {
                            paragraphList.add(paragraph);
                            if (paragraph.replace(" ", "").equals("\n")) {
                                paragraph = String.format(" %s", word);
                                cnt = 0;
                            } else {

                                if (paragraph.substring((paragraph.length() - word.length()), paragraph.length()).equals(word)) {
                                    paragraph = String.format(" %s", "\n");
                                    cnt = 0;
                                } else {
                                    paragraph = String.format(" %s", word);
                                    cnt = 0;
                                }
                            }
                        }
                    }
                } else {
                    if (cnt + wordLength <= lettersPerLine) {
                        paragraph = paragraph + String.format(" %s", word);
                        cnt = cnt + wordLength;
                    } else {
                        paragraphList.add(paragraph);
                        paragraph = String.format(" %s", word);
                        cnt = 0;
                    }
                }
                cnt2++;
                if (cnt2 == splitText.length && (paragraph.length() <= lettersPerLine)) {
                    paragraphList.add(paragraph);
                } else if (cnt2 == splitText.length && (paragraph.length() > lettersPerLine)) {
                    cnt = 0;
                    String paragraph2 = "";
                    int count = 0;
                    cnt3 = paragraph.split(" ").length;
                    for (String SubWord : paragraph.split(" ")
                            ) {
                        count++;
                        wordLength = SubWord.length();

                        if (cnt + wordLength <= lettersPerLine) {
                            paragraph2 = paragraph2 + String.format(" %s", SubWord);
                            cnt = paragraph2.length();
                        } else {
                            paragraphList.add(paragraph2);
                            paragraph2 = String.format(" %s", SubWord);
                            if (count == cnt3) {
                                paragraphList.add(paragraph2);
                            }
                            cnt = 0;
                        }

                    }
                }
            }
            //Creando las paginas con sus respectivos parrafos

            int rowsCount = 0;
            int PageNumber = 1;
            cnt = 0;
            int totalParrafos = paragraphList.size();
            Map<Integer, List<String>> Pages = new HashMap<Integer, List<String>>();
            List<String> parrafosAgrupadosPorPagina = new ArrayList<String>();
            for (String element : paragraphList
                    ) {
                if (rowsCount >= LimitRows) {
                    Pages.put(PageNumber, parrafosAgrupadosPorPagina);
                    parrafosAgrupadosPorPagina = new ArrayList<String>();
                    PageNumber++;
                    rowsCount = 1;
                    parrafosAgrupadosPorPagina.add(element);
                } else {
                    parrafosAgrupadosPorPagina.add(element);
                    rowsCount++;
                    if (cnt == (totalParrafos - 1)) {
                        Pages.put(PageNumber, parrafosAgrupadosPorPagina);
                    }

                }
                cnt++;

            }


            List<String> ParrafosToShow = Pages.get(1);
            String parrafo = "";
            if (ParrafosToShow != null) {
                for (String parrafoToShow : ParrafosToShow) {
                    parrafo = parrafo + parrafoToShow;
                }
            }


            //Toast.makeText(this, "Success!!", Toast.LENGTH_SHORT).show();
            textToSpeech1.stop();
            Locale locSpanish = new Locale("spa", "MEX");
            textToSpeech1.setLanguage(locSpanish);
            textToSpeech1.speak(parrafo, TextToSpeech.QUEUE_ADD, null);

        } else if (i == TextToSpeech.ERROR) {
            Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show();
        }
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
        // mPageFlipView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //  mPageFlipView.onPause();
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

    @Override
    public void onBackPressed() {
        textToSpeech1.stop();

        super.onBackPressed();
        //codigo adicional
        this.finish();
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
