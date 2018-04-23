package com.travellingsalesmangame.Views.Game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.travellingsalesmangame.Controllers.Game.ButtonCreater;
import com.travellingsalesmangame.Controllers.Login.Encode;
import com.travellingsalesmangame.Models.Game.Examples;
import com.travellingsalesmangame.Models.Game.ScreenSettings;
import com.travellingsalesmangame.Models.Login.User;
import com.travellingsalesmangame.R;
import com.travellingsalesmangame.Views.Login.LoginActivity;


public class LevelMenuActivity extends Activity{

    private RelativeLayout levelMenuActivity;
    private SharedPreferences prefs;
    private ScreenSettings screenSettings;
    private int level;
    private User user;

    ValueEventListener listenerCookie;
    private final DatabaseReference users = FirebaseDatabase.getInstance().getReference("User");

    private void init(){
        screenSettings=new ScreenSettings(this);
        levelMenuActivity=findViewById(R.id.levelMenuActivity);
        level=prefs.getInt("level",0); // Kayıtlı level
    }

    private void initListener(){

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Read user from the database
        listenerCookie = new ValueEventListener() {       //veri tabanı dinleyicisi
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists() || !dataSnapshot.child("password").exists() || !dataSnapshot.child("password").getValue().equals(user.getPassword())) {

                    SharedPreferences  prefs = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = prefs.edit();
                    prefsEditor.putString("User", "");
                    prefsEditor.apply();

                    Intent intent = new Intent(LevelMenuActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        };
    }

    private void readIfAlreadyLogin(){

        Gson gson = new Gson();
        String json = prefs.getString("User","");

        if(json.equals("")){

            Intent intent = new Intent(LevelMenuActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            user = new User(gson.fromJson(json, User.class));
            users.child(Encode.encode(user.getEmail())).addValueEventListener(listenerCookie);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_menu);
        initListener();
        readIfAlreadyLogin();
        init();
        setMenu();
    }

    private void setMenu() {

        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LevelMenuActivity.this,StateMenuActivity.class);
                intent.putExtra("levelSaved",level);//kayıtlı level, kullanıcının en son geldigi level
                intent.putExtra("levelClicked",v.getId());
                //tiklanan level, kullanıcının oynamak için seçtiği level,
                // kayıtlı ve secili level esit ise 2. aktivite ona gore olusturulacak.
                startActivity(intent);
                finish();
            }
        };

        ButtonCreater buttonCreater=new ButtonCreater(this,
                                                       levelMenuActivity,
                                                       onClickListener,
                                                       screenSettings);

        buttonCreater.create(Examples.getCores().length,level);
    }

}
