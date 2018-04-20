package com.travellingsalesmangame.Models.Game;

public class Examples {
    //Örnekler, her level bir core listesi, her listeye eklenen eleman o levelin sorusu(state-durum değeri) oluyor.

    private static Core[][] cores;

    static {

        cores = new Core[][]{

                new Core[]{ //0. level
                        new Core(new int[]{6, 14, 25},new int[][]{{6,14,29},{6,25,19},{14,25,34}},82),
                },

                new Core[]{ //1. level
                        new Core(new int[]{2, 17, 25, 29},new int[][]{{2,17,25},{2,25,10},{2,29,15},{17,25,25},{17,29,30},{25,29,35}},80),
                },

                new Core[]{ //2.level
                        new Core(new int[]{1, 3, 15, 19, 27},new int[][]{{1,3,7},{1,15,9},{1,19,36},{1,27,11},{3,15,23},{3,19,13},{3,27,19},{15,19,42},{15,27,10},{19,27,8}},47),
                },

                new Core[]{
                        new Core(new int[]{1, 3, 10, 14, 31, 34},new int[][]{{1,3,35},{1,10,31},{1,14,31},{1,31,37},{1,34,35},{3,10,31},{3,14,21},{3,31,25},{3,34,40},{10,14,25},{10,31,31},{10,34,34},{14,31,35},{14,34,31},{31,34,25}},162),
                        new Core(new int[]{1, 3, 10, 14, 31, 34},new int[][]{{1,3,35},{1,10,31},{1,14,31},{1,31,37},{1,34,35},{3,10,31},{3,14,21},{3,31,25},{3,34,40},{10,14,25},{10,31,31},{10,34,34},{14,31,35},{14,34,31},{31,34,25}},162),
                },

                new Core[]{
                        new Core(new int[]{4, 1, 13, 15, 25, 29, 33},new int[][]{{4,1,35},{4,13,30},{4,15,30},{4,25,37},{4,29,35},{4,33,20},{1,13,25},{1,15,25},{1,25,40},{1,29,25},{1,33,30},{13,15,29},{13,25,35},{13,29,20},{13,33,25},{15,25,25},{15,29,25},{15,33,25},{25,29,25},{25,33,25}},135),
                        new Core(new int[]{4, 1, 13, 15, 25, 29, 33},new int[][]{{4,1,35},{4,13,30},{4,15,30},{4,25,37},{4,29,35},{4,33,20},{1,13,25},{1,15,25},{1,25,40},{1,29,25},{1,33,30},{13,15,29},{13,25,35},{13,29,20},{13,33,25},{15,25,25},{15,29,25},{15,33,25},{25,29,25},{25,33,25}},135),
                },
                new Core[]{
                        new Core(new int[]{0, 3, 6, 8, 14, 17, 20, 24, 31, 34},new int[][]{{0,3,15},{0,6,23},{0,8,6},{0,14,20},{0,17,45},{0,20,96},{0,24,56},{0,31,22},{0,34,141}
                                ,{3,6,73},{3,8,98},{3,14,65},{3,17,70},{3,20,24},{3,24,54},{3,31,58},{3,34,44}
                                ,{6,8,33},{6,14,166},{6,17,23},{6,20,123},{6,24,36},{6,31,64},{6,34,25}
                                ,{8,14,12},{8,17,83},{8,20,24},{8,24,64},{8,31,211},{8,34,23}
                                ,{14,17,93},{14,20,36},{14,24,28},{14,31,28},{14,34,76}
                                ,{17,20,34},{17,24,8},{17,31,42},{17,34,123}
                                ,{20,24,22},{20,31,46},{20,34,332}
                                ,{24,31,32},{24,34,22}
                                ,{31,34,38}}
                                ,135),
                },

        };
    }

    //örnekleri çekme
    public static Core[][] getCores() {
        return cores;
    }

    //Her sorunun maliyetlere eişiyorum.
    public static int PathCosts(int [][] costs,int start,int finish){
        for (int i = 0; i < costs.length; i++) {
            if ((costs[i][0]==start && costs[i][1]==finish) || (costs[i][1]==start && costs[i][0]==finish))
                return costs[i][2];
        }
        return 0;
    }
}
