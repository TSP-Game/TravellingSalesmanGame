package com.travellingsalesmangame;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


public class Master_layout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager manager;
    private FragmentTransaction transaction;

    private DrawerLayout master_layout;
    private NavigationView nav_view;
    private ActionBarDrawerToggle toggle;


    private void init(){

        manager=getFragmentManager();

        nav_view=findViewById(R.id.nav_view);
        master_layout=findViewById(R.id.drawer_master);

        toggle=new ActionBarDrawerToggle(this,master_layout,R.string.open,R.string.close);
        master_layout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nav_view.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_layout);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id=item.getItemId();

        if(id==R.id.oyun){
            LevelMenu_Fragment fragmentA= new LevelMenu_Fragment();
            transaction=manager.beginTransaction();

            transaction.replace(R.id.context_main,fragmentA,"Fragment A");
            transaction.commit();

            Toast.makeText(this,"deneme",Toast.LENGTH_SHORT).show();
        }


        master_layout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        master_layout.closeDrawer(GravityCompat.START);
    }
}
