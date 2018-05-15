package com.travellingsalesmangame.Views.Game;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.travellingsalesmangame.Controllers.Login.Encode;
import com.travellingsalesmangame.Models.Game.GameInfo;
import com.travellingsalesmangame.Models.Login.User;
import com.travellingsalesmangame.R;

public class Istatistik extends Fragment {

    private final DatabaseReference games = FirebaseDatabase.getInstance().getReference("Game_eee653b64ab2ff1051e13c092396179e9d29bbc7ed6aa4a8");
    private ValueEventListener listenerGameInfo;
    private GameInfo gameInfo;
    private User user;
    private SharedPreferences prefs;
    private View view;

    private void init() {
        getActivity().setTitle("İstatistikler");

        Gson gson = new Gson();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String json = prefs.getString("user", "");
        user = new User(gson.fromJson(json, User.class));

        listenerGameInfo = new ValueEventListener() {       //veri tabanı dinleyicisi
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    gameInfo = new GameInfo();
                    gameInfo = dataSnapshot.getValue(GameInfo.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        };
        games.child(Encode.encode(user.getEmail())).addValueEventListener(listenerGameInfo);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_istatistik, container, false);
        init();
        return view;
    }
}
