package com.travellingsalesmangame.Models.Game;


import java.util.ArrayList;
import java.util.List;

public class GameInfo{

    private int level,state;

    private List<Integer> testScores;
    private List<List<List<Integer>>> gameSingleScores;
    private List<List<List<List<Integer>>>> gamePcScores;

    private void init() {

        level = 0;
        state = 0;
        testScores = new ArrayList<>();
        gameSingleScores = new ArrayList<>();
        gamePcScores = new ArrayList<>();


        for(int i=0; i<Examples.getCores().length; i++)
            gameSingleScores.add(new ArrayList<List<Integer>>());

        for(int i=0; i<3; i++)                              //3 zorluk derecesi olacak
            gamePcScores.add(new ArrayList<>(gameSingleScores));

        List<Integer> temp;
        for(int i=0; i<gameSingleScores.size(); i++)
            for(int j=0; j<Examples.getCores()[i].length; j++){

                temp = new ArrayList<>();
                temp.add(0);
                temp.add(0);
                gameSingleScores.get(i).add(new ArrayList<>(temp));
            }
    }

    public GameInfo() {
        init();
    }


    public GameInfo(GameInfo gameInfo) {

        level = gameInfo.getLevel();
        state = gameInfo.getState();
        testScores = gameInfo.getTestScores();
        gameSingleScores = gameInfo.getGameSingleScores();
        gamePcScores = gameInfo.getGamePcScores();

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

    public List<Integer> getTestScores() {
        return testScores;
    }

    public void setTestScores(List<Integer> testScores) {
        this.testScores = testScores;
    }

    public List<List<List<Integer>>> getGameSingleScores() {
        return gameSingleScores;
    }

    public void setGameSingleScores(List<List<List<Integer>>> gameSingleScores) {
        this.gameSingleScores = gameSingleScores;
    }

    public List<List<List<List<Integer>>>> getGamePcScores() {
        return gamePcScores;
    }

    public void setGamePcScores(List<List<List<List<Integer>>>> gamePcScores) {
        this.gamePcScores = gamePcScores;
    }
}
