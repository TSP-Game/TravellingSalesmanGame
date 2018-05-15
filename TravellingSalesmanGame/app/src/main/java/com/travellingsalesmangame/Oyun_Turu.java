package com.travellingsalesmangame;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.travellingsalesmangame.Views.Game.Hikaye;
import com.travellingsalesmangame.Views.Game.Hikaye2;

public class Oyun_Turu extends Fragment {


    private View view;
    private Button btn_bilgisayar_karsi,btn_tek_oyna;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private void init(){
        btn_bilgisayar_karsi=view.findViewById(R.id.btn_bilgisayar_karsi);
        btn_tek_oyna=view.findViewById(R.id.btn_tek_oyna);

        btn_tek_oyna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hikaye hikaye=new Hikaye();
                fragmentManager=getFragmentManager();
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.context_main,hikaye);
                transaction.commit();
            }
        });

        btn_bilgisayar_karsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final CharSequence[] choice = {"Kolay","Orta","Zor"};

                final int[] from = new int[1]; //This must be declared as global !

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Seviye Seçiniz ");
                alert.setSingleChoiceItems(choice, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (choice[which] == "Kolay") {
                            from[0] = 0;
                        } else if (choice[which] == "Orta") {
                            from[0] = 1;
                        }
                         else if (choice[which] == "Zor") {
                            from[0] = 2;
                        }
                    }
                });
                alert.setPositiveButton("Başla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(view.getContext());
                        SharedPreferences.Editor prefsEditor=prefs.edit();
                        prefsEditor.putInt("seviye", from[0]);
                        prefsEditor.apply();

                        Hikaye2 hikaye2=new Hikaye2();
                        fragmentManager=getFragmentManager();
                        transaction=fragmentManager.beginTransaction();
                        transaction.replace(R.id.context_main,hikaye2);
                        transaction.commit();
                    }
                });
                alert.show();


            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_oyun_turu,container,false);
        init();
        return view;
    }
}
