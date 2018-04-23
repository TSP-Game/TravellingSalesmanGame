package com.travellingsalesmangame.Views.Game;

import com.travellingsalesmangame.Models.Game.Core;
import com.travellingsalesmangame.Models.Game.Examples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ComputerPlay {

    private Core core;
    private List<Integer> cities,citiesBackup;
    private double[][] q;


    private void init() {

        cities = new ArrayList<>();
        citiesBackup = new ArrayList<>();

        int[] cities1 = core.getCities();
        for (int i1 = 1, cities1Length = cities1.length; i1 < cities1Length; i1++) {
            Integer i = cities1[i1];
            cities.add(i);
            citiesBackup.add(i);
        }

        citiesBackup.add(0,core.getCities()[0]);
        citiesBackup.add(core.getCities()[0]);

        int size = cities.size()+1;
        q = new double[size][size];
    }

    ComputerPlay(Core core){

        this.core = core;
        init();
    }

    //gelen değer kadar iterasyon ile öğretme işlemi yapan fonksiyon
    void learn(long times){

        for(int i=0;i<times;i++){

            cities = new ArrayList<>(citiesBackup);
            cities.remove(0);
            cities.remove(cities.size()-1);
            Collections.shuffle(cities);
            cities.add(0,citiesBackup.get(0));
            cities.add(citiesBackup.get(0));

            learnOneTime();
        }
    }

    //bir iterasyonluk öğrenme
    private void learnOneTime(){

        int cost = 0,upper=cities.size()-1;

        for(int i=0;i<upper;i++)
            cost += Examples.PathCosts(core.getCosts(),cities.get(i),cities.get(i+1));

        for(int i=0;i<upper;i++)
            q[citiesBackup.indexOf(cities.get(i))][citiesBackup.indexOf(cities.get(i+1))] += cost;
    }
}
