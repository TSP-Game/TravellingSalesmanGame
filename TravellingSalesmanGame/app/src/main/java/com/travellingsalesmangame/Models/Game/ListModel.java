package com.travellingsalesmangame.Models.Game;

import java.util.ArrayList;
import java.util.List;

public class ListModel {

    private int seviye,puan,level,milisaniye,state;
    private List<Object> modelList;

    public void listeyiDoldur(GameInfo gameInfo){

        modelList=new ArrayList<>();

        for (int i = 0; i < gameInfo.getGameSingleScores().size(); i++) {

            modelList.add("Level : "+(i+1));
            for (int j=0;j<gameInfo.getGameSingleScores().get(i).size();j++){
                ListModel model=new ListModel();
                model.setLevel(i);
                model.setState(j);
                model.setPuan(gameInfo.getGameSingleScores().get(i).get(j).get(1));
                model.setMilisaniye(gameInfo.getGameSingleScores().get(i).get(j).get(0));
                modelList.add(model);
            }

        }
    }

    public void pc_listeye_doldur(GameInfo gameInfo){
        modelList=new ArrayList<>();

        for (int i = 0; i < gameInfo.getGamePcScores().size(); i++) {  //Kolay

            if(i==0) modelList.add("Seviye : Kolay");
            if(i==1) modelList.add("Seviye : Orta");
            if (i==2)modelList.add("Seviye : Zor");
            //modelList.add("Seviye : "+(i+1));

            for (int j = 0; j < gameInfo.getGamePcScores().get(i).size(); j++) {  ///

                modelList.add("Level : "+(j+1));
                for (int k=0;k<gameInfo.getGamePcScores().get(i).get(j).size();k++){
                    ListModel model=new ListModel();
                    model.setLevel(j);
                    model.setState(k);
                    model.setPuan(gameInfo.getGamePcScores().get(i).get(j).get(k).get(1));
                    model.setMilisaniye(gameInfo.getGamePcScores().get(i).get(j).get(k).get(0));
                    modelList.add(model);
                }
            }

        }
    }

    public int getPuan() {
        return puan;
    }

    public void setPuan(int puan) {
        this.puan = puan;
    }

    public List<Object> getModelList() {
        return modelList;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getMilisaniye() {
        return milisaniye;
    }

    public void setMilisaniye(int milisaniye) {
        this.milisaniye = milisaniye;
    }

    public int getSeviye() {
        return seviye;
    }

    public void setSeviye(int seviye) {
        this.seviye = seviye;
    }

}
