package com.travellingsalesmangame.Views.Game;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.travellingsalesmangame.Controllers.Login.Encode;
import com.travellingsalesmangame.Models.Game.Result;
import com.travellingsalesmangame.Models.Login.User;
import com.travellingsalesmangame.R;

public class Game2_Result extends Fragment {

    private final DatabaseReference games = FirebaseDatabase.getInstance().getReference("Game_eee653b64ab2ff1051e13c092396179e9d29bbc7ed6aa4a8");

    private View view;
    private ImageView imgView;
    private TextView txtYorum, txtSure_Sonuc, txtPuan_Sonuc, txtSkorGoster;
    private int levelSaved, levelClicked;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private Button btn_Oyun;
    private Result result;
    private int seviye;

    private void init() {

        getActivity().setTitle("Oyun Skorunuz");
        imgView = view.findViewById(R.id.imgView);
        btn_Oyun = view.findViewById(R.id.btn_Oyun);

        txtYorum = view.findViewById(R.id.txtYorum);
        txtPuan_Sonuc = view.findViewById(R.id.txtPuan_Sonuc);
        txtSure_Sonuc = view.findViewById(R.id.txtSure_Sonuc);
        txtSkorGoster = view.findViewById(R.id.txtSkorGoster);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        seviye = pref.getInt("seviye", -1);

        Bundle bundle = getArguments();
        result = (Result) bundle.getSerializable("result");

        writeDatabase();

        if (result.getUser_skor() > result.getPc_skor())
            txtYorum.setText("Üzgünüm! Bilgisayarın skorunu geçemediniz.\n");

        else if (result.getUser_skor() == result.getPc_skor())
            txtYorum.setText("Berabere! Bilgisayar ile aynı skoru buldunuz.\n");

        else
            txtYorum.setText("Tebrikler! Bilgisayarın skorunu geçtiniz.\n");

        txtSure_Sonuc.setText("Süre :  " + result.getSureTxt());
        txtPuan_Sonuc.setText("Kazanılan Puan : " + String.valueOf(result.getPuan()));
        txtSkorGoster.setText("Bilgisayarın Bulduğu Yol : " + result.getPc_skor() + "\nKullanıcının Bulduğu Yol : " + result.getUser_skor() + "\n");

        levelClicked = result.getLevelClicked();
        levelSaved = result.getLevelSaved();

        if (result.getUser_skor() <= result.getPc_skor())
            imgView.setImageResource(R.drawable.prize);

        else
            imgView.setImageResource(R.drawable.odul_kayip);

    }

    @Override
    public void onResume() {
        super.onResume();
        btn_Oyun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (result.isLevel_state_durum() == true) {
                    StateMenu2_Fragment state = new StateMenu2_Fragment();

                    Bundle bundle = new Bundle();
                    bundle.putInt("levelSaved", levelSaved);
                    bundle.putInt("levelClicked", levelClicked);

                    state.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.context_main, state);
                    transaction.commit();
                } else {
                    LevelMenu2_Fragment level = new LevelMenu2_Fragment();
                    fragmentManager = getFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.context_main, level);
                    transaction.commit();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_game2_result, container, false);
        init();
        return view;
    }

    private void writeDatabase() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        Gson gson = new Gson();
        String json = prefs.getString("user", "");

        User user = new User(gson.fromJson(json, User.class));

        String time = String.valueOf(result.getSure());
        games.child(Encode.encode(user.getEmail())).child("gamePcScores").child(String.valueOf(seviye)).child(String.valueOf(result.getLevelClicked()))
                .child(String.valueOf(result.getStateClicked())).child("0").setValue(Integer.valueOf(time));

        games.child(Encode.encode(user.getEmail())).child("gamePcScores").child(String.valueOf(seviye)).child(String.valueOf(result.getLevelClicked()))
                .child(String.valueOf(result.getStateClicked())).child("1").setValue(result.getPuan());
    }
}
