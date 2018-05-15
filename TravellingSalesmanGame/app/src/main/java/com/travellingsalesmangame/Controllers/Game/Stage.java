package com.travellingsalesmangame.Controllers.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.travellingsalesmangame.Controllers.Login.Encode;
import com.travellingsalesmangame.Models.Game.Examples;
import com.travellingsalesmangame.Models.Game.GameInfo;
import com.travellingsalesmangame.Models.Login.User;

public class Stage {


    private final DatabaseReference games = FirebaseDatabase.getInstance().getReference("Game_eee653b64ab2ff1051e13c092396179e9d29bbc7ed6aa4a8");

    private static int level, state;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    private GameInfo gameInfo;
    private boolean activity;

    public Stage(Context context, boolean activity) {
        super();
        this.activity = activity;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();
        String json = prefs.getString("gameinfo", "");
        gameInfo = new GameInfo(gson.fromJson(json, GameInfo.class));

        if (activity == true) {
            level = gameInfo.getLevelSingle();
            state = gameInfo.getStateSingle();
        } else {
            level = gameInfo.getLevelPc();
            state = gameInfo.getStatePc();
        }
    }

    public void up() {                  //seviye atlama işlevi,

        prefsEditor = prefs.edit();

        if (Examples.getCores()[level].length - 1 == state)  //bulunan levelde baska soru yoksa, yani geçilen soru son soru ise level atla
            levelUp();

        else {//değil ise state arttır, sıradaki state-soru sonraki soru olsun, level aynı kalsın.

            if (activity)
                gameInfo.setStateSingle(gameInfo.getStateSingle() + 1);

            else
                gameInfo.setStatePc(gameInfo.getStatePc() + 1);
        }

        Gson gson = new Gson();
        String json = gson.toJson(gameInfo);
        prefsEditor.putString("gameinfo", json);
        prefsEditor.apply();
        writeDatabase(activity);
    }

    private void levelUp() {

        if (level < ((Examples.getCores().length))) {    //level, olusturulan örneklerdeki level sayısı kadar artabilsin,level artınca state 0 lancak, yeni level yeni sorular***
            //Normalde length-1 olması lazım yoksa Examples.getCores(level).length sınırı aşar ancak 1 - bu kod kullanılmıyor, 2 - son state veya son level sarı kalmasın diye böyle.

            if (activity) {
                gameInfo.setStateSingle(0);
                gameInfo.setLevelSingle(gameInfo.getLevelSingle() + 1);
            } else {
                gameInfo.setStatePc(0);
                gameInfo.setLevelPc(gameInfo.getLevelPc() + 1);
            }
        }

    }

    private void writeDatabase(boolean activity) {

        Gson gson = new Gson();
        String json = prefs.getString("user", "");
        User user = new User(gson.fromJson(json, User.class));

        if (activity) {
            games.child(Encode.encode(user.getEmail())).child("levelSingle").setValue(gameInfo.getLevelSingle());
            games.child(Encode.encode(user.getEmail())).child("stateSingle").setValue(gameInfo.getStateSingle());
        } else {
            games.child(Encode.encode(user.getEmail())).child("levelPc").setValue(gameInfo.getLevelPc());
            games.child(Encode.encode(user.getEmail())).child("statePc").setValue(gameInfo.getStatePc());
        }

    }
}
