package com.imam.pi.skripsi.siaterapps;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.imam.pi.skripsi.siaterapps.adapter.CardItemString;
import com.imam.pi.skripsi.siaterapps.adapter.CardPagerAdapterS;
import com.imam.pi.skripsi.siaterapps.adapter.DataString;
import com.imam.pi.skripsi.siaterapps.adapter.ShadowTransformer;
import com.imam.pi.skripsi.siaterapps.session.SessionManager;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuUtama extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager session;
    private ProgressDialog pDialog;
    SharedPreferences prefs;

    private ViewPager mViewPager;

    private CardPagerAdapterS mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    private Context context;



    CarouselView carouselView;
    int[] sampleImages = {R.drawable.gambar1, R.drawable.gambar2, R.drawable.gambar3, R.drawable.gambarr4, R.drawable.gambar5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                     ///   .setAction("Action", null).show();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:info@ubl.ac.id");
                intent.setData(data);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        context = this;


        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mCardAdapter = new CardPagerAdapterS(context);


        for (int i = 0; i< DataString.titlesText.length; i++){

            mCardAdapter.addCardItemS(new CardItemString( DataString.titlesText[i], DataString.detailsArray[i]));
        }


        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

        NavigationView nnavigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  nnavigationView.getHeaderView(0);
        TextView npmMHS = (TextView)hView.findViewById(R.id.npmmhs);
        TextView emailMHS = (TextView)hView.findViewById(R.id.emailmhs);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        //Session Login
        if(session.isLoggedIn()) {
            prefs = getSharedPreferences("UserDetails",
                    Context.MODE_PRIVATE);
        String id  = prefs.getString("id","");
            String tahunAktif  = prefs.getString("tahunAktif","");
            String  programStudi  = prefs.getString("programStudi", "");
            String  npm  = prefs.getString("npm", "");
            String  email  = prefs.getString("email","");
            String  telepon  = prefs.getString("telepon", "");
            String  angkatan  = prefs.getString("angkatan", "");

        npmMHS.setText(npm);
           emailMHS.setText(email);


        }

        }


    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuUtama.this);
        builder.setMessage("Apakah anda ingin keluar dari App?");
        builder.setCancelable(true);
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_utama, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            info.showAbout(MenuUtama.this);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_krs) {
            // Handle the camera action
        } else if (id == R.id.nav_khs) {

        } else if (id == R.id.nav_frs) {

        } else if (id == R.id.nav_jk) {

        } else if (id == R.id.nav_rekapkelas) {

        }else if (id == R.id.nav_spp) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_instagram) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("https://www.instagram.com/kampusubl/");
            intent.setData(data);
            startActivity(intent);

        }else if (id == R.id.nav_fb) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("https://www.facebook.com/informasi.UBL");
            intent.setData(data);
            startActivity(intent);

        }else if (id == R.id.nav_twitter) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("https://twitter.com/ublkampus");
            intent.setData(data);
            startActivity(intent);
        }else if (id == R.id.nav_tv) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("https://www.youtube.com/channel/UCzN0bTDrkD8Luh9WECAlJ9w");
            intent.setData(data);
            startActivity(intent);
        }else if (id == R.id.nav_gantipsw) {

        }else if (id == R.id.nav_logout) {


            session.setLogin(false);
            session.setSkip(false);
            session.setSessid(0);

            // Launching the login activity
            Intent intent = new Intent(MenuUtama.this, LoginActivity.class);
            startActivity(intent);
            finish();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
