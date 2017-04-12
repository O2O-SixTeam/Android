package com.jspark.android.kardoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LobbyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btnEnroll;
    ImageView caseFender, caseBumper, caseAudi, caseBenz, caseBmw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        setWidgets();

        setBtnEnroll();

        setImageClick();
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
        getMenuInflater().inflate(R.menu.lobby, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        Log.w("drawer item", String.valueOf(item));

        if (id == R.id.nav_estimation) {
            // Handle the camera action
        } else if (id == R.id.nav_myPage) {

        } else if (id == R.id.nav_requestShop) {
            intent = new Intent(LobbyActivity.this, RepairShopMainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.createShop) {
            intent = new Intent(LobbyActivity.this, SignupRepairShopActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setWidgets() {
        btnEnroll = (Button)findViewById(R.id.btnEnroll);
        caseFender = (ImageView)findViewById(R.id.caseFender);
        caseBumper = (ImageView)findViewById(R.id.caseBumper);
        caseAudi = (ImageView)findViewById(R.id.caseAudi);
        caseBenz = (ImageView)findViewById(R.id.caseBenz);
        caseBmw = (ImageView)findViewById(R.id.caseBmw);
    }

    private void setBtnEnroll() {
        btnEnroll.setOnClickListener(v -> {
            Intent intent = new Intent(LobbyActivity.this, WriteEstimationActivity.class);
            startActivity(intent);
        });
    }

    private void setImageClick() {
        View.OnClickListener imageClick = v -> {
            Intent i = new Intent(LobbyActivity.this, CaseActivity.class);
            switch(v.getId()) {
                case R.id.caseFender :
                    i.putExtra("case", "fender");
                    break;
                case R.id.caseBumper :
                    i.putExtra("case", "bumper");
                    break;
                case R.id.caseAudi :
                    i.putExtra("case", "audi");
                    break;
                case R.id.caseBenz :
                    i.putExtra("case", "benz");
                    break;
                case R.id.caseBmw :
                    i.putExtra("case", "bmw");
                    break;
            }
            startActivity(i);
        };
        caseFender.setOnClickListener(imageClick);
        caseBumper.setOnClickListener(imageClick);
        caseAudi.setOnClickListener(imageClick);
        caseBenz.setOnClickListener(imageClick);
        caseBmw.setOnClickListener(imageClick);
    }


}
