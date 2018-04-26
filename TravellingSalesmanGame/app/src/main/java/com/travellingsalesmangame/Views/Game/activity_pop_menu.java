package com.travellingsalesmangame.Views.Game;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.travellingsalesmangame.R;
import com.travellingsalesmangame.Views.Game.PopActivity;

import static android.support.constraint.Constraints.TAG;

public class activity_pop_menu extends Fragment implements View.OnClickListener {

    private View view;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    private void init(){
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(view.getContext());
        int id=prefs.getInt("konuanlatimi",-1);

        if(id==1)
            view.findViewById(R.id.btn_sub).setBackgroundColor(Color.GREEN);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.activity_pop_menu,container,false);
        view.findViewById(R.id.btn_sub).setOnClickListener(this);
        init();
        return  view;
    }

    @Override
    public void onClick(View v) {
        PopActivity pop=new PopActivity();
        manager=getFragmentManager();
        transaction=manager.beginTransaction();
        transaction.replace(R.id.context_main,pop);
        transaction.commit();
    }

    //@Override
   /* protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_menu);
    }*/
}
