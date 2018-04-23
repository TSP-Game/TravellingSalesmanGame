package com.travellingsalesmangame.Views.Game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.travellingsalesmangame.Controllers.Game.ButtonCreater;
import com.travellingsalesmangame.Controllers.Game.CostsSetter;
import com.travellingsalesmangame.Controllers.Game.Stage;
import com.travellingsalesmangame.Models.Game.Core;
import com.travellingsalesmangame.Models.Game.Examples;
import com.travellingsalesmangame.Models.Game.ScreenSettings;
import com.travellingsalesmangame.R;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends Activity {

    private RelativeLayout gameActivity,layoutDraw;
    private Core core;
    private Stage stage;
    private Intent intent;
    private List<ImageButton> buttons,selectedButtons;
    private List<CostsSetter.Draw> drawList;
    private ImageButton oldButton;
    private ScreenSettings screenSettings;
    private int levelSaved,levelClicked,stateSaved,stateClicked,click_count=0,totalScore=0;

    private void init(){

        screenSettings=new ScreenSettings(GameActivity.this);
        selectedButtons=new ArrayList<>();

        gameActivity=findViewById(R.id.gameActivity);
        layoutDraw=findViewById(R.id.layoutDraw);

        intent=getIntent();
        levelSaved = intent.getIntExtra("levelSaved",0);
        levelClicked = intent.getIntExtra("levelClicked",0);
        stateSaved = intent.getIntExtra("stateSaved",0);
        stateClicked = intent.getIntExtra("stateClicked",0);

        core = Examples.getCores()[levelClicked][stateClicked];

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action((ImageButton) v);
            }
        };

        ButtonCreater buttonCreater=new ButtonCreater(this,
                gameActivity,
                onClickListener,
                screenSettings);
        buttonCreater.create(35,core.getCities()); //35 tane button oluşturacak
        buttons=buttonCreater.getGameButonList();//Tüm oluşan butonları aldım.

        drawList = CostsSetter.getDrawList(this, buttons, core.getCosts(), layoutDraw);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        init();
        //createLayout();
        stage = new Stage(this); //seviye atlama işlemi için gerekli
    }

    private void action(ImageButton button) {

        if(selectedButtons.contains(button))
            DisplayMessage.show(this,"Uyarı","Seçili Şehre Tıkladınız",null);

        else {

            if(oldButton==null){
                showLayoutCosts(button);
                button.setImageResource(R.mipmap.home1);
                oldButton=button;
                selectedButtons.add(button);
                click_count++;
            }
            else {

                int pathCost=Examples.PathCosts(core.getCosts(),buttons.indexOf(oldButton),buttons.indexOf(button));
                if(pathCost>0){

                    totalScore+=pathCost;
                    click_count++;

                    DrawView drawView = new DrawView(this,oldButton,button,R.color.dark_orchid,10);
                    layoutDraw.addView(drawView);

                    if(click_count==core.getCities().length)
                        selectedButtons.remove(0);

                    removeLayoutCosts(oldButton);
                    showLayoutCosts(button);

                    oldButton=button;
                    button.setImageResource(R.mipmap.home3);
                    selectedButtons.add(button);

                    //bütün butonlara tıklanma, yani oyunun bitiş durumu
                    if (click_count==core.getCities().length+1)
                    {
                        if((levelSaved == levelClicked) && (stateSaved == stateClicked))    //son levelin son sorusu oynandı ise seviye arttır
                            stage.up();

                        if(stateClicked == Examples.getCores()[levelClicked].length-1)                          //oynanan oyun levelin son oyunu ise levelmenuye değilse statemenuye dön
                            intent = new Intent(GameActivity.this,LevelMenuActivity.class);      //levelmenuye geri dön.
                        else {
                            intent = new Intent(GameActivity.this,StateMenuActivity.class);      //statemenuye geri dön.
                            intent.putExtra("levelSaved",levelSaved);        //kayıtlı level, kullanıcının en son geldigi level
                            intent.putExtra("levelClicked",levelClicked);  //tiklanan level, kullanıcının oynamak için seçtiği level, kayıtlı ve secili level esit ise 2. aktivite ona gore olusturulacak.
                        }

                        DisplayMessage.show(this,"Oyun","Skorunuz :  "+totalScore+"  Gerçek Skor :  "+core.getSolution(),intent);  //intent = level menu olmalı, display methodunu kodla önce.
                    }
                }
                else
                    DisplayMessage.show(this,"Uyarı","Yol Yoktur",null);
            }
        }
    }

    private void removeLayoutCosts(ImageButton oldButton){

        for (CostsSetter.Draw draw : drawList)

            if (buttons.get(buttons.indexOf(oldButton)).equals(draw.drawView.getStartView())
                    || buttons.get(buttons.indexOf(oldButton)).equals(draw.drawView.getEndView())) {

                layoutDraw.removeView(draw.drawView);
                layoutDraw.removeView(draw.textView);
            }
    }

    private void showLayoutCosts(ImageButton button) {

        for (CostsSetter.Draw draw: drawList)

            if (!(!(buttons.get(buttons.indexOf(button)).equals(draw.drawView.getStartView()) || buttons.get(buttons.indexOf(button)).equals(draw.drawView.getEndView()))
                    || selectedButtons.contains(draw.drawView.getStartView()) || selectedButtons.contains(draw.drawView.getEndView()))) {

                layoutDraw.removeView(draw.drawView);
                layoutDraw.removeView(draw.textView);
                draw.drawView.setColor(getResources().getColor(R.color.deep_pink));
                draw.drawView.setWidth(10);
                draw.textView.setTextColor(getResources().getColor(R.color.black));
                draw.textView.setTextSize(20);
                draw.textView.setTypeface(null, Typeface.BOLD);
                layoutDraw.addView(draw.drawView);
                layoutDraw.addView(draw.textView);
            }
        }

    public void game_Qplay_onclick(View view) {

        ComputerPlay comPlay = new ComputerPlay(core);
        comPlay.learn(100000);

        for (Integer i: comPlay.getPath())
            action(buttons.get(core.getCities()[i]));

        /*
        List<Integer> cities = new ArrayList<>();

        int[] cities1 = core.getCities();
        for (int i1 = 1, cities1Length = cities1.length; i1 < cities1Length; i1++) {
            Integer i = cities1[i1];
            cities.add(i);
        }

        action(buttons.get(core.getCities()[0]));

        Collections.shuffle(cities);

        for (Integer i: cities) {
            action(buttons.get(i));
        }

        action(buttons.get(core.getCities()[0]));
        */
    }
}
