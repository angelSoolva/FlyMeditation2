package com.soolva.flymeditation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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
import android.widget.EditText;
import android.widget.TextView;

import com.soolva.flymeditation.someClasses.MainActivityContract;
import com.soolva.flymeditation.someClasses.MainActivityPresenter;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityContract.View {
    String LOG="MainActivity";
    TextView tvAltitude;
    TextView tvVerticalSpeed;
    Button Start;
    EditText etUpStep;
    EditText etDownStep;
    Button btEnterParameters;
    TextView tvUpStep;
    TextView tvDownStep;
    DecimalFormat dfOne = new DecimalFormat("#0.0");
    MainActivityContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //Data presentor
        final Global_Data_Class globalVariable = (Global_Data_Class) getApplicationContext();
        presenter = new MainActivityPresenter(this, globalVariable);
        //Add elements

        initViews();
        // Calling Application class (see application tag in AndroidManifest.xml)







    }
    private void initViews(){
        tvAltitude = findViewById(R.id.TextViewHeight);
        tvVerticalSpeed = findViewById(R.id.TextViewVertical);
        Start = findViewById(R.id.buttonStart);

        etUpStep = findViewById(R.id.editTextUpStep);
        etDownStep = findViewById(R.id.editTextDownStep);
        btEnterParameters = findViewById(R.id.buttonEnterParameters);

        tvUpStep = findViewById(R.id.textViewUpStep);
        tvDownStep = findViewById(R.id.textViewDownStep);


        Start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(LOG,  "Click button");
                presenter.runThis();
            }
        });
        btEnterParameters.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(LOG,  "Click button");
                presenter.enterParameters();
            }
        });


    }
    //Updates text views
    @Override
    public void showElevation(Float elevation) {
      tvAltitude.setText(dfOne.format(elevation));
    }

    @Override
    public void showVerticalSpeed(Float vertical) {
        tvVerticalSpeed.setText(dfOne.format(vertical));
    }
    @Override
    public void showUpStep(Float upStep){
        tvUpStep.setText(dfOne.format(upStep));
    }
    @Override
    public void showDownStep(Float downStep){
        tvDownStep.setText(dfOne.format(downStep));
    }
    @Override
    public float getUpStepInput() {
        Float input;
        input = Float.parseFloat(etUpStep.getText().toString());

        return input;
    }
    @Override
    public float getDownStepInput() {
        Float input;
        input = Float.parseFloat(etDownStep.getText().toString());
        return input;
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.BLE_set) {
            Intent intent = new Intent(this, BLE_set.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
