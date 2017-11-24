package com.kmacho.labiblia3d;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.kmacho.labiblia3d.db.Configuration;
import com.kmacho.labiblia3d.db.HolyBibleDatabase;
import com.tapadoo.alerter.Alerter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public String activityTitle;
    private InterstitialAd mInterstitialAd;
    Switch speechCheck;
    private HolyBibleDatabase holyBibleDatabase;
    int configurationId = 1;
    ImageView imageView;
    Boolean speechCheckIsChecked = false;
    Boolean isFirstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        holyBibleDatabase = Room.databaseBuilder(getApplicationContext(),
                HolyBibleDatabase.class, "holyBible-db").fallbackToDestructiveMigration().build();

        // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fabNext = (FloatingActionButton) findViewById(R.id.fabNext);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (activityTitle) {
                    //Antiguo Testamento
                    case "Génesis":
                        generateChapters("Éxodo");
                        break;
                    case "Éxodo":
                        generateChapters("Levítico");
                        break;
                    case "Levítico":
                        generateChapters("Números");
                        break;

                    case "Números":
                        generateChapters("Deuteronomio");
                        break;

                    case "Deuteronomio":
                        generateChapters("Josué");
                        break;

                    case "Josué":
                        generateChapters("Jueces");
                        break;

                    case "Jueces":
                        generateChapters("Rut");
                        break;

                    case "Rut":
                        generateChapters("1 Samuel");
                        break;

                    case "1 Samuel":
                        generateChapters("2 Samuel");
                        break;
                    case "2 Samuel":
                        generateChapters("1 Reyes");
                        break;
                    case "1 Reyes":
                        generateChapters("2 Reyes");
                        break;
                    case "2 Reyes":
                        generateChapters("1 Crónicas");
                        break;
                    case "1 Crónicas":
                        generateChapters("2 Crónicas");
                        break;
                    case "2 Crónicas":
                        generateChapters("Esdras");
                        break;
                    case "Esdras":
                        generateChapters("Nehemías");
                        break;
                    case "Nehemías":
                        generateChapters("Ester");
                        break;
                    case "Ester":
                        generateChapters("Job");
                        break;
                    case "Job":
                        generateChapters("Salmos");
                        break;
                    case "Salmos":
                        generateChapters("Proverbios");
                        break;
                    case "Proverbios":
                        generateChapters("Eclesiastés");
                        break;
                    case "Eclesiastés":
                        generateChapters("Cantares");
                        break;
                    case "Cantares":
                        generateChapters("Isaías");
                        break;
                    case "Isaías":
                        generateChapters("Jeremías");
                        break;
                    case "Jeremías":
                        generateChapters("Lamentaciones");
                        break;
                    case "Lamentaciones":
                        generateChapters("Ezequiel");
                        break;
                    case "Ezequiel":
                        generateChapters("Daniel");
                        break;
                    case "Daniel":
                        generateChapters("Oseas");
                        break;
                    case "Oseas":
                        generateChapters("Joel");
                        break;
                    case "Joel":
                        generateChapters("Amós");
                        break;
                    case "Amós":
                        generateChapters("Abdías");
                        break;
                    case "Abdías":
                        generateChapters("Jonás");
                        break;
                    case "Jonás":
                        generateChapters("Miqueas");
                        break;
                    case "Miqueas":
                        generateChapters("Nahúm");
                        break;
                    case "Nahúm":
                        generateChapters("Habacuc");
                        break;
                    case "Habacuc":
                        generateChapters("Sofonías");
                        break;
                    case "Sofonías":
                        generateChapters("Hageo");
                        break;
                    case "Hageo":
                        generateChapters("Zacarías");
                        break;
                    case "Zacarías":
                        generateChapters("Malaquías");
                        break;
                    case "Malaquías":
                        generateChapters("Mateo");
                        break;
                    //Nuevo Testamento
                    case "Mateo":
                        generateChapters("Marcos");
                        break;
                    case "Marcos":
                        generateChapters("Lucas");
                        break;
                    case "Lucas":
                        generateChapters("Juan");
                        break;
                    case "Juan":
                        generateChapters("Hechos");
                        break;
                    case "Hechos":
                        generateChapters("Romanos");
                        break;
                    case "Romanos":
                        generateChapters("1 Corintios");
                        break;
                    case "1 Corintios":
                        generateChapters("2 Corintios");
                        break;
                    case "2 Corintios":
                        generateChapters("Gálatas");
                        break;
                    case "Gálatas":
                        generateChapters("Efesios");
                        break;
                    case "Efesios":
                        generateChapters("Filipenses");
                        break;
                    case "Filipenses":
                        generateChapters("Colosenses");
                        break;
                    case "Colosenses":
                        generateChapters("1 Tesalonicenses");
                        break;
                    case "1 Tesalonicenses":
                        generateChapters("2 Tesalonicenses");
                        break;
                    case "2 Tesalonicenses":
                        generateChapters("1 Timoteo");
                        break;
                    case "1 Timoteo":
                        generateChapters("2 Timoteo");
                        break;
                    case "2 Timoteo":
                        generateChapters("Tito");
                        break;
                    case "Tito":
                        generateChapters("Filemón");
                        break;
                    case "Filemón":
                        generateChapters("Hebreos");
                        break;
                    case "Hebreos":
                        generateChapters("Santiago");
                        break;
                    case "Santiago":
                        generateChapters("1 Pedro");
                        break;
                    case "1 Pedro":
                        generateChapters("2 Pedro");
                        break;
                    case "2 Pedro":
                        generateChapters("1 Juan");
                        break;
                    case "1 Juan":
                        generateChapters("2 Juan");
                        break;
                    case "2 Juan":
                        generateChapters("3 Juan");
                        break;
                    case "3 Juan":
                        generateChapters("Judas");
                        break;
                    case "Judas":
                        generateChapters("Apocalipsis");
                        break;
                    case "Apocalipsis":
                        /*Snackbar.make(view, "No hay más escrituras.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show(); */
                        break;
                }

            }
        });

        FloatingActionButton fabBack = (FloatingActionButton) findViewById(R.id.fabBack);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (activityTitle) {
                    //Antiguo Testamento
                    case "Génesis":
                        //generateChapters("Éxodo");
                        break;
                    case "Éxodo":
                        generateChapters("Génesis");
                        break;
                    case "Levítico":
                        generateChapters("Éxodo");
                        break;

                    case "Números":
                        generateChapters("Levítico");
                        break;

                    case "Deuteronomio":
                        generateChapters("Números");
                        break;

                    case "Josué":
                        generateChapters("Deuteronomio");
                        break;

                    case "Jueces":
                        generateChapters("Josué");
                        break;

                    case "Rut":
                        generateChapters("Jueces");
                        break;

                    case "1 Samuel":
                        generateChapters("Rut");
                        break;
                    case "2 Samuel":
                        generateChapters("1 Samuel");
                        break;
                    case "1 Reyes":
                        generateChapters("2 Samuel");
                        break;
                    case "2 Reyes":
                        generateChapters("1 Reyes");
                        break;
                    case "1 Crónicas":
                        generateChapters("2 Reyes");
                        break;
                    case "2 Crónicas":
                        generateChapters("1 Crónicas");
                        break;
                    case "Esdras":
                        generateChapters("2 Crónicas");
                        break;
                    case "Nehemías":
                        generateChapters("Esdras");
                        break;
                    case "Ester":
                        generateChapters("Nehemías");
                        break;
                    case "Job":
                        generateChapters("Ester");
                        break;
                    case "Salmos":
                        generateChapters("Job");

                        break;
                    case "Proverbios":
                        generateChapters("Salmos");
                        break;
                    case "Eclesiastés":
                        generateChapters("Proverbios");
                        break;
                    case "Cantares":
                        generateChapters("Eclesiastés");
                        break;
                    case "Isaías":
                        generateChapters("Cantares");
                        break;
                    case "Jeremías":
                        generateChapters("Isaías");
                        break;
                    case "Lamentaciones":
                        generateChapters("Jeremías");
                        break;
                    case "Ezequiel":
                        generateChapters("Lamentaciones");
                        break;
                    case "Daniel":
                        generateChapters("Ezequiel");
                        break;
                    case "Oseas":
                        generateChapters("Daniel");
                        break;
                    case "Joel":
                        generateChapters("Oseas");
                        break;
                    case "Amós":
                        generateChapters("Joel");
                        break;
                    case "Abdías":
                        generateChapters("Amós");
                        break;
                    case "Jonás":
                        generateChapters("Abdías");
                        break;
                    case "Miqueas":
                        generateChapters("Jonás");
                        break;
                    case "Nahúm":
                        generateChapters("Miqueas");
                        break;
                    case "Habacuc":
                        generateChapters("Nahúm");
                        break;
                    case "Sofonías":
                        generateChapters("Habacuc");
                        break;
                    case "Hageo":
                        generateChapters("Sofonías");
                        break;
                    case "Zacarías":
                        generateChapters("Hageo");
                        break;
                    case "Malaquías":
                        generateChapters("Zacarías");
                        break;
                    //Nuevo Testamento
                    case "Mateo":
                        generateChapters("Malaquías");
                        break;
                    case "Marcos":
                        generateChapters("Mateo");
                        break;
                    case "Lucas":
                        generateChapters("Marcos");
                        break;
                    case "Juan":
                        generateChapters("Lucas");
                        break;
                    case "Hechos":
                        generateChapters("Juan");
                        break;
                    case "Romanos":
                        generateChapters("Hechos");
                        break;
                    case "1 Corintios":
                        generateChapters("Romanos");
                        break;
                    case "2 Corintios":
                        generateChapters("1 Corintios");
                        break;
                    case "Gálatas":
                        generateChapters("2 Corintios");
                        break;
                    case "Efesios":
                        generateChapters("Gálatas");
                        break;
                    case "Filipenses":
                        generateChapters("Efesios");
                        break;
                    case "Colosenses":
                        generateChapters("Filipenses");
                        break;
                    case "1 Tesalonicenses":
                        generateChapters("Colosenses");
                        break;
                    case "2 Tesalonicenses":
                        generateChapters("1 Tesalonicenses");
                        break;
                    case "1 Timoteo":
                        generateChapters("2 Tesalonicenses");
                        break;
                    case "2 Timoteo":
                        generateChapters("1 Timoteo");
                        break;
                    case "Tito":
                        generateChapters("2 Timoteo");
                        break;
                    case "Filemón":
                        generateChapters("Tito");
                        break;
                    case "Hebreos":
                        generateChapters("Filemón");
                        break;
                    case "Santiago":
                        generateChapters("Hebreos");
                        break;
                    case "1 Pedro":
                        generateChapters("Santiago");
                        break;
                    case "2 Pedro":
                        generateChapters("1 Pedro");
                        break;
                    case "1 Juan":
                        generateChapters("2 Pedro");
                        break;
                    case "2 Juan":
                        generateChapters("1 Juan");
                        break;
                    case "3 Juan":
                        generateChapters("2 Juan");
                        break;
                    case "Judas":
                        generateChapters("3 Juan");
                        break;
                    case "Apocalipsis":
                        generateChapters("Judas");
                        break;
                }

            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Configuration configuration = holyBibleDatabase.daoAccess().getConfigurationById(configurationId);
                        if (configuration != null) {
                            speechCheckIsChecked = configuration.getSpeech();
                        }
                        new Thread() {
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        speechCheck.setChecked(speechCheckIsChecked);
                                    }
                                });
                            }
                        }.start();
                    }
                }).start();
            }
        });


        LinearLayout linear = (LinearLayout) navigationView.getHeaderView(0);

        LinearLayout linear2 = (LinearLayout) linear.getChildAt(0);


        speechCheck = (Switch) linear2.getChildAt(1);
        speechCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new DatabaseAsync().doInBackground();

                        if (!isFirstLoad) {
                            String title = "Configuración";
                            String text = "";
                            int color = Color.BLUE;
                            if (speechCheck.isChecked()) {
                                color = Color.parseColor("#00308F");
                                text = "Se ha activado la opción de narrar.";
                            } else {
                                color = Color.GRAY;
                                text = "Se ha desactivado la opción de narrar.";
                            }
                            Alerter.create(MainActivity.this)
                                    .setTitle(title)
                                    .setText(text)
                                    .setBackgroundColorInt(color) // or setBackgroundColorInt(Color.CYAN)
                                    .show();
                        }
                        isFirstLoad = false;
                    }
                }).start();
            }
        });

        generateChapters("Génesis");

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i=new Intent(android.content.Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(android.content.Intent.EXTRA_SUBJECT,"La biblia 3D");
            i.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.kmacho.labiblia3d");
            startActivity(Intent.createChooser(i,"Share via"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_Génesis) {
            //Intent myIntent = new Intent(MainActivity.this, BibleActivity.class);
            //myIntent.putExtra("key", "Genesis"); //Optional parameters
            //MainActivity.this.startActivity(myIntent);
            generateChapters("Génesis");
        } else if (id == R.id.nav_Exodo) {
            generateChapters("Éxodo");

        } else if (id == R.id.nav_Levítico) {
            generateChapters("Levítico");

        } else if (id == R.id.nav_Números) {
            generateChapters("Números");

        } else if (id == R.id.nav_Deuteronomio) {
            generateChapters("Deuteronomio");

        } else if (id == R.id.nav_Josué) {
            generateChapters("Josué");

        } else if (id == R.id.nav_Jueces) {
            generateChapters("Jueces");

        } else if (id == R.id.nav_Rut) {
            generateChapters("Rut");

        } else if (id == R.id.nav_1Samuel) {
            generateChapters("1 Samuel");

        } else if (id == R.id.nav_2Samuel) {
            generateChapters("2 Samuel");

        } else if (id == R.id.nav_1Reyes) {
            generateChapters("1 Reyes");

        } else if (id == R.id.nav_2Reyes) {
            generateChapters("2 Reyes");

        } else if (id == R.id.nav_1Crónicas) {
            generateChapters("1 Crónicas");

        } else if (id == R.id.nav_2Crónicas) {
            generateChapters("2 Crónicas");

        } else if (id == R.id.nav_Esdras) {
            generateChapters("Esdras");

        } else if (id == R.id.nav_Nehemías) {
            generateChapters("Nehemías");

        } else if (id == R.id.nav_Ester) {
            generateChapters("Ester");

        } else if (id == R.id.nav_Job) {
            generateChapters("Job");

        } else if (id == R.id.nav_Salmos) {
            generateChapters("Salmos");

        } else if (id == R.id.nav_Proverbios) {
            generateChapters("Proverbios");

        } else if (id == R.id.nav_Eclesiastés) {
            generateChapters("Eclesiastés");

        } else if (id == R.id.nav_Cantares) {
            generateChapters("Cantares");

        } else if (id == R.id.nav_Isaías) {
            generateChapters("Isaías");

        } else if (id == R.id.nav_Jeremías) {
            generateChapters("Jeremías");

        } else if (id == R.id.nav_Lamentaciones) {
            generateChapters("Lamentaciones");

        } else if (id == R.id.nav_Ezequiel) {
            generateChapters("Ezequiel");

        } else if (id == R.id.nav_Daniel) {
            generateChapters("Daniel");

        } else if (id == R.id.nav_Oseas) {
            generateChapters("Oseas");

        } else if (id == R.id.nav_Joel) {
            generateChapters("Joel");

        } else if (id == R.id.nav_Amós) {
            generateChapters("Amós");

        } else if (id == R.id.nav_Abdías) {
            generateChapters("Abdías");

        } else if (id == R.id.nav_Jonás) {
            generateChapters("Jonás");

        } else if (id == R.id.nav_Miqueas) {
            generateChapters("Miqueas");

        } else if (id == R.id.nav_Nahúm) {
            generateChapters("Nahúm");

        } else if (id == R.id.nav_Habacuc) {
            generateChapters("Habacuc");

        } else if (id == R.id.nav_Sofonías) {
            generateChapters("Sofonías");

        } else if (id == R.id.nav_Hageo) {
            generateChapters("Hageo");

        } else if (id == R.id.nav_Zacarías) {
            generateChapters("Zacarías");

        } else if (id == R.id.nav_Malaquías) {
            generateChapters("Malaquías");

        }
        //Nuevo Testamento
        else if (id == R.id.nav_Mateo) {
            generateChapters("Mateo");

        } else if (id == R.id.nav_Marcos) {
            generateChapters("Marcos");

        } else if (id == R.id.nav_Lucas) {
            generateChapters("Lucas");

        } else if (id == R.id.nav_Juan) {
            generateChapters("Juan");

        } else if (id == R.id.nav_Hechos) {
            generateChapters("Hechos");

        } else if (id == R.id.nav_Romanos) {
            generateChapters("Romanos");

        } else if (id == R.id.nav_1Corintios) {
            generateChapters("1 Corintios");

        } else if (id == R.id.nav_2Corintios) {
            generateChapters("2 Corintios");

        } else if (id == R.id.nav_Gálatas) {
            generateChapters("Gálatas");

        } else if (id == R.id.nav_Efesios) {
            generateChapters("Efesios");

        } else if (id == R.id.nav_Filipenses) {
            generateChapters("Filipenses");

        } else if (id == R.id.nav_Colosenses) {
            generateChapters("Colosenses");

        } else if (id == R.id.nav_1Tesalonicenses) {
            generateChapters("1 Tesalonicenses");

        } else if (id == R.id.nav_2Tesalonicenses) {
            generateChapters("2 Tesalonicenses");

        } else if (id == R.id.nav_1Timoteo) {
            generateChapters("1 Timoteo");

        } else if (id == R.id.nav_2Timoteo) {
            generateChapters("2 Timoteo");

        } else if (id == R.id.nav_Tito) {
            generateChapters("Tito");

        } else if (id == R.id.nav_Filemón) {
            generateChapters("Filemón");

        } else if (id == R.id.nav_Hebreos) {
            generateChapters("Hebreos");

        } else if (id == R.id.nav_Santiago) {
            generateChapters("Santiago");

        } else if (id == R.id.nav_1Pedro) {
            generateChapters("1 Pedro");

        } else if (id == R.id.nav_2Pedro) {
            generateChapters("2 Pedro");

        } else if (id == R.id.nav_1Juan) {
            generateChapters("1 Juan");

        } else if (id == R.id.nav_2Juan) {
            generateChapters("2 Juan");

        } else if (id == R.id.nav_3Juan) {
            generateChapters("3 Juan");

        } else if (id == R.id.nav_Judas) {
            generateChapters("Judas");

        } else if (id == R.id.nav_Apocalipsis) {
            generateChapters("Apocalipsis");

        } else {
            Toast.makeText(this, "No Disponible", Toast.LENGTH_LONG).show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private int getStringResourceByName(String aString) {
        String packageName = getPackageName();
        String val = formatChapterName(aString);
        int resId = getResources().getIdentifier(val, "string", packageName);
        return resId;
    }

    public String formatChapterName(String value) {

        return value.replace("é", "e")
                .replace("É", "E").replace("í", "i")
                .replace("ú", "u").replace("ó", "o").replace("á", "a")
                .replace("1 Samuel", "Samuel1").replace("2 Samuel", "Samuel2")
                .replace("1 Reyes", "Reyes1").replace("2 Reyes", "Reyes2")
                .replace("1 Cronicas", "Cronicas1").replace("2 Cronicas", "Cronicas2")
                .replace("1 Corintios", "Corintios1").replace("2 Corintios", "Corintios2")
                .replace("1 Tesalonicenses", "Tesalonicenses1").replace("2 Tesalonicenses", "Tesalonicenses2")
                .replace("1 Timoteo", "Timoteo1").replace("2 Timoteo", "Timoteo2")
                .replace("1 Pedro", "Pedro1").replace("2 Pedro", "Pedro2")
                .replace("1 Juan", "Juan1").replace("2 Juan", "Juan2").replace("3 Juan", "Juan3");
    }

    public void setActivityTitle(String title) {
        activityTitle = title;
        setTitle(title);
        showInterstitial();
    }

    public void generateChapters(final String book) {
        int chapters = 0;
        switch (book) {
            //Antiguo Testamento
            case "Génesis":
                setActivityTitle(book);
                chapters = 50;
                break;
            case "Éxodo":
                setActivityTitle(book);
                chapters = 40;
                break;
            case "Levítico":
                setActivityTitle(book);
                chapters = 27;
                break;

            case "Números":
                setActivityTitle(book);
                chapters = 36;
                break;

            case "Deuteronomio":
                setActivityTitle(book);
                chapters = 34;
                break;


            case "Josué":
                setActivityTitle(book);
                chapters = 24;
                break;

            case "Jueces":
                setActivityTitle(book);
                chapters = 21;
                break;

            case "Rut":
                setActivityTitle(book);
                chapters = 4;
                break;

            case "1 Samuel":
                setActivityTitle(book);
                chapters = 31;
                break;
            case "2 Samuel":
                setActivityTitle(book);
                chapters = 24;
                break;
            case "1 Reyes":
                setActivityTitle(book);
                chapters = 22;
                break;
            case "2 Reyes":
                setActivityTitle(book);
                chapters = 25;
                break;
            case "1 Crónicas":
                setActivityTitle(book);
                chapters = 29;
                break;
            case "2 Crónicas":
                setActivityTitle(book);
                chapters = 36;
                break;
            case "Esdras":
                setActivityTitle(book);
                chapters = 10;
                break;
            case "Nehemías":
                setActivityTitle(book);
                chapters = 13;
                break;
            case "Ester":
                setActivityTitle(book);
                chapters = 10;
                break;
            case "Job":
                setActivityTitle(book);
                chapters = 42;
                break;
            case "Salmos":
                setActivityTitle(book);
                chapters = 150;
                break;
            case "Proverbios":
                setActivityTitle(book);
                chapters = 31;
                break;
            case "Eclesiastés":
                setActivityTitle(book);
                chapters = 12;
                break;
            case "Cantares":
                setActivityTitle(book);
                chapters = 8;
                break;
            case "Isaías":
                setActivityTitle(book);
                chapters = 66;
                break;
            case "Jeremías":
                setActivityTitle(book);
                chapters = 52;
                break;
            case "Lamentaciones":
                setActivityTitle(book);
                chapters = 5;
                break;
            case "Ezequiel":
                setActivityTitle(book);
                chapters = 48;
                break;
            case "Daniel":
                setActivityTitle(book);
                chapters = 12;
                break;
            case "Oseas":
                setActivityTitle(book);
                chapters = 14;
                break;
            case "Joel":
                setActivityTitle(book);
                chapters = 3;
                break;
            case "Amós":
                setActivityTitle(book);
                chapters = 9;
                break;
            case "Abdías":
                setActivityTitle(book);
                chapters = 1;
                break;
            case "Jonás":
                setActivityTitle(book);
                chapters = 4;
                break;
            case "Miqueas":
                setActivityTitle(book);
                chapters = 7;
                break;
            case "Nahúm":
                setActivityTitle(book);
                chapters = 3;
                break;
            case "Habacuc":
                setActivityTitle(book);
                chapters = 3;
                break;
            case "Sofonías":
                setActivityTitle(book);
                chapters = 3;
                break;
            case "Hageo":
                setActivityTitle(book);
                chapters = 2;
                break;
            case "Zacarías":
                setActivityTitle(book);
                chapters = 14;
                break;
            case "Malaquías":
                setActivityTitle(book);
                chapters = 4;
                break;
            //Nuevo Testamento
            case "Mateo":
                setActivityTitle(book);
                chapters = 28;
                break;
            case "Marcos":
                setActivityTitle(book);
                chapters = 16;
                break;
            case "Lucas":
                setActivityTitle(book);
                chapters = 24;
                break;
            case "Juan":
                setActivityTitle(book);
                chapters = 21;
                break;
            case "Hechos":
                setActivityTitle(book);
                chapters = 28;
                break;
            case "Romanos":
                setActivityTitle(book);
                chapters = 16;
                break;
            case "1 Corintios":
                setActivityTitle(book);
                chapters = 16;
                break;
            case "2 Corintios":
                setActivityTitle(book);
                chapters = 13;
                break;
            case "Gálatas":
                setActivityTitle(book);
                chapters = 6;
                break;
            case "Efesios":
                setActivityTitle(book);
                chapters = 6;
                break;
            case "Filipenses":
                setActivityTitle(book);
                chapters = 4;
                break;
            case "Colosenses":
                setActivityTitle(book);
                chapters = 4;
                break;
            case "1 Tesalonicenses":
                setActivityTitle(book);
                chapters = 5;
                break;
            case "2 Tesalonicenses":
                setActivityTitle(book);
                chapters = 3;
                break;
            case "1 Timoteo":
                setActivityTitle(book);
                chapters = 6;
                break;
            case "2 Timoteo":
                setActivityTitle(book);
                chapters = 4;
                break;
            case "Tito":
                setActivityTitle(book);
                chapters = 3;
                break;
            case "Filemón":
                setActivityTitle(book);
                chapters = 1;
                break;
            case "Hebreos":
                setActivityTitle(book);
                chapters = 13;
                break;
            case "Santiago":
                setActivityTitle(book);
                chapters = 5;
                break;
            case "1 Pedro":
                setActivityTitle(book);
                chapters = 5;
                break;
            case "2 Pedro":
                setActivityTitle(book);
                chapters = 3;
                break;
            case "1 Juan":
                setActivityTitle(book);
                chapters = 5;
                break;
            case "2 Juan":
                setActivityTitle(book);
                chapters = 1;
                break;
            case "3 Juan":
                setActivityTitle(book);
                chapters = 1;
                break;
            case "Judas":
                setActivityTitle(book);
                chapters = 1;
                break;
            case "Apocalipsis":
                setActivityTitle(book);
                chapters = 22;
                break;


        }

        LinearLayout linear = (LinearLayout) findViewById(R.id.chapters);
        ScrollView scrollview1 = (ScrollView) findViewById(R.id.scrollview1);

        linear.removeAllViews();


        for (int i = 1; i <= chapters + 3; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            Button btn = new Button(this);
            btn.setId(i);
            final int id_ = btn.getId();
            if (i > chapters) {
                btn.setText("");
            } else {
                btn.setText("Capítulo " + id_);
            }
            btn.setTop(20 + i * 3);
            btn.setBackgroundColor(Color.WHITE);

            linear.addView(btn, params);
            btn = ((Button) findViewById(id_));
            final int finalI = i;

            if (i <= chapters) {
                btn.setText("Capítulo " + id_);

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        String chapter = book + "_" + finalI;

                        int resId = getStringResourceByName(chapter);
                        if (resId == 0) {
                            Toast.makeText(MainActivity.this, "No disponible", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent myIntent = new Intent(MainActivity.this, BibleActivity.class);
                            myIntent.putExtra("key", formatChapterName(book + "_" + finalI)); //Optional parameters
                            myIntent.putExtra("speech", String.valueOf(speechCheck.isChecked()));
                            MainActivity.this.startActivity(myIntent);
                        }
                    }
                });
            }

        }

        scrollview1.setScrollY(0);
    }

    private class DatabaseAsync extends AsyncTask<Void, Void, Void> {

        public Boolean synchronizeSpeech() {
            Configuration configuration = holyBibleDatabase.daoAccess().getConfigurationById(configurationId);
            return configuration.getSpeech();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Configuration configuration = holyBibleDatabase.daoAccess().getConfigurationById(configurationId);
            if (configuration == null) {
                configuration = new Configuration();
                configuration.setConfigurationId(configurationId);
                configuration.setSpeech(speechCheck.isChecked());
                holyBibleDatabase.daoAccess().insertConfiguration(configuration);
            } else {
                configuration.setSpeech(speechCheck.isChecked());
                holyBibleDatabase.daoAccess().updateConfiguration(configuration);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //To after addition operation here.
        }
    }

}
