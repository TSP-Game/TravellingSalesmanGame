package com.travellingsalesmangame.Models.Game;

import java.io.Serializable;

public class Result implements Serializable {

    private int puan;            // Kullanıcının kazandığı puan
    private long sure;           //Kullanıcı ne kadar sureden çözdü? (milisaniye)
    private String sureTxt;      //Kullanıcı ne kadar sureden çözdü? (Text formatında) (0 : 0: 0)
    private int levelSaved;      //Kayıtlı level
    private int levelClicked;    //Tıklanan level
    private boolean level_state_durum;  //Level mi State mi gitsin.
    private int pc_skor;         //Bilgisayarın skoru
    private int user_skor;       //Kullanıcının skoru
    private int stateClicked;    //Tıklanan level

    public int getStateClicked() {
        return stateClicked;
    }

    public void setStateClicked(int stateClicked) {
        this.stateClicked = stateClicked;
    }

    public int getPc_skor() {
        return pc_skor;
    }

    public void setPc_skor(int pc_skor) {
        this.pc_skor = pc_skor;
    }

    public int getUser_skor() {
        return user_skor;
    }

    public void setUser_skor(int user_skor) {
        this.user_skor = user_skor;
    }

    public boolean isLevel_state_durum() {
        return level_state_durum;
    }

    public void setLevel_state_durum(boolean level_state_durum) {
        this.level_state_durum = level_state_durum;
    }

    public int getLevelSaved() {
        return levelSaved;
    }

    public void setLevelSaved(int levelSaved) {
        this.levelSaved = levelSaved;
    }

    public int getLevelClicked() {
        return levelClicked;
    }

    public void setLevelClicked(int levelClicked) {
        this.levelClicked = levelClicked;
    }

    public int getPuan() {
        return puan;
    }

    public void setPuan(int puan) {
        this.puan = puan;
    }

    public long getSure() {
        return sure;
    }

    public void setSure(long sure) {
        this.sure = sure;
    }

    public String getSureTxt() {
        return sureTxt;
    }

    public void setSureTxt(String sureTxt) {
        this.sureTxt = sureTxt;
    }
}
