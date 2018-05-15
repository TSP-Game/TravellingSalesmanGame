package com.travellingsalesmangame.Models.Game;

public class Examples {
    //Örnekler, her level bir core listesi, her listeye eklenen eleman o levelin sorusu(state-durum değeri) oluyor.

    private static Core[][] cores;

    static {

        cores = new Core[][]{

                new Core[]{ //1. level // 3 tepeli
                        new Core(new int[]{6, 14, 25}, new int[][]{{6, 14, 15}, {6, 25, 19}, {14, 25, 24}}, 58),
                        new Core(new int[]{4, 10, 33}, new int[][]{{4, 10, 21}, {4, 33, 26}, {10, 33, 24}}, 71),
                },

                new Core[]{ //2. level // 4 tepeli
                        new Core(new int[]{2, 17, 25, 29}, new int[][]{{2, 17, 10}, {2, 25, 29}, {2, 29, 24}, {17, 25, 15}, {17, 29, 12}, {25, 29, 19}}, 68),
                        new Core(new int[]{0, 14, 15, 27}, new int[][]{{0, 14, 23}, {0, 15, 17}, {0, 27, 42}, {14, 15, 15}, {14, 27, 9}, {15, 27, 8}}, 57),
                },

                new Core[]{ //3.level // 5 tepeli
                        new Core(new int[]{1, 3, 15, 19, 27}, new int[][]{{1, 3, 7}, {1, 15, 9}, {1, 19, 16}, {1, 27, 21}, {3, 15, 23}, {3, 19, 13}, {3, 27, 25}, {15, 19, 24}, {15, 27, 13}, {19, 27, 9}}, 51),
                        new Core(new int[]{3, 5, 14, 25, 33}, new int[][]{{3, 5, 12}, {3, 14, 6}, {3, 25, 27}, {3, 33, 28}, {5, 14, 17}, {5, 25, 22}, {5, 33, 17}, {14, 25, 20}, {14, 33, 15}, {25, 33, 14}}, 69),
                        new Core(new int[]{3, 5, 19, 30, 33}, new int[][]{{3, 5, 11}, {3, 19, 12}, {3, 30, 19}, {3, 33, 23}, {5, 19, 14}, {5, 30, 15}, {5, 33, 23}, {19, 30, 14}, {19, 33, 12}, {30, 33, 10}}, 60),
                        new Core(new int[]{0, 3, 14, 16, 30}, new int[][]{{0, 3, 27}, {0, 14, 41}, {0, 16, 34}, {0, 30, 66}, {3, 14, 14}, {3, 16, 30}, {3, 30, 78}, {14, 16, 25}, {14, 30, 56}, {16, 30, 21}}, 153),
                },

                new Core[]{ // 4. Level //6 tepeli
                        new Core(new int[]{1, 3, 10, 14, 31, 34}, new int[][]{{1, 3, 12}, {1, 10, 15}, {1, 14, 22}, {1, 31, 37}, {1, 34, 44}, {3, 10, 19}, {3, 14, 12}, {3, 31, 40}, {3, 34, 38}, {10, 14, 20}, {10, 31, 19}, {10, 34, 35}, {14, 31, 22}, {14, 34, 18}, {31, 34, 18}}, 94),
                        new Core(new int[]{0, 3, 10, 22, 24, 32}, new int[][]{{0, 3, 37}, {0, 10, 23}, {0, 22, 45}, {0, 24, 53}, {0, 32, 61}, {3, 10, 42}, {3, 22, 43}, {3, 24, 42}, {3, 32, 63}, {10, 22, 19}, {10, 24, 29}, {10, 32, 28}, {22, 24, 16}, {22, 32, 17}, {24, 32, 22}}, 160),
                        new Core(new int[]{1, 3, 12, 20, 24, 32}, new int[][]{{1, 3, 26}, {1, 12, 24}, {1, 20, 37}, {1, 24, 40}, {1, 32, 46}, {3, 12, 23}, {3, 20, 35}, {3, 24, 32}, {3, 32, 49}, {12, 20, 30}, {12, 24, 29}, {12, 32, 33}, {20, 24, 32}, {20, 32, 28}, {24, 32, 20}}, 162),
                },

                new Core[]{ // 5. level // 7 tepeli
                        new Core(new int[]{1, 4, 13, 15, 25, 29, 33}, new int[][]{{1, 4, 35}, {1, 13, 25}, {1, 15, 35}, {1, 25, 40}, {1, 29, 53}, {1, 33, 57}, {4, 13, 21}, {4, 15, 47}, {4, 25, 54}, {4, 29, 40}, {4, 33, 50}, {13, 15, 33}, {13, 25, 38}, {13, 29, 31}, {13, 33, 30}, {15, 25, 25}, {15, 29, 43}, {15, 33, 42}, {25, 29, 41}, {25, 33, 36}, {29, 33, 18}}, 200),
                        new Core(new int[]{2, 10, 19, 21, 23, 26, 29}, new int[][]{{2, 10, 41}, {2, 19, 56}, {2, 21, 61}, {2, 23, 65}, {2, 26, 74}, {2, 29, 82}, {10, 19, 76}, {10, 21, 32}, {10, 23, 45}, {10, 26, 37}, {10, 29, 55}, {19, 21, 40}, {19, 23, 21}, {19, 26, 52}, {19, 29, 28}, {21, 23, 34}, {21, 26, 16}, {21, 29, 35}, {23, 26, 23}, {23, 29, 18}, {26, 29, 28}}, 212),
                        new Core(new int[]{1, 5, 7, 14, 21, 29, 30}, new int[][]{{1, 5, 7}, {1, 7, 9}, {1, 14, 32}, {1, 21, 36}, {1, 29, 54}, {1, 30, 58}, {5, 7, 11}, {5, 14, 40}, {5, 21, 34}, {5, 29, 52}, {5, 30, 63}, {7, 14, 25}, {7, 21, 31}, {7, 29, 46}, {7, 30, 50}, {14, 21, 30}, {14, 29, 25}, {14, 30, 45}, {21, 29, 26}, {21, 30, 22}, {29, 30, 38}}, 160),
                },

                new Core[]{ // 6. level // 8 tepeli
                        new Core(new int[]{1, 4, 6, 13, 15, 25, 29, 33}, new int[][]{{1, 4, 40}, {1, 6, 11}, {1, 13, 32}, {1, 15, 36}, {1, 25, 51}, {1, 29, 58}, {1, 33, 63},
                                {4, 6, 42}, {4, 13, 30}, {4, 15, 50}, {4, 25, 69}, {4, 29, 60}, {4, 33, 56},
                                {6, 13, 29}, {6, 15, 30}, {6, 25, 48}, {6, 29, 55}, {6, 33, 56},
                                {13, 15, 40}, {13, 25, 49}, {13, 29, 42}, {13, 33, 50},
                                {15, 25, 27}, {15, 29, 52}, {15, 33, 50},
                                {25, 29, 53}, {25, 33, 42},
                                {29, 33, 20}},
                                242),
                },
        };
    }

    //örnekleri çekme
    public static Core[][] getCores() {
        return cores;
    }

    //Her sorunun maliyetlere eişiyorum.
    public static int PathCosts(int[][] costs, int start, int finish) {
        for (int i = 0; i < costs.length; i++) {
            if ((costs[i][0] == start && costs[i][1] == finish) || (costs[i][1] == start && costs[i][0] == finish))
                return costs[i][2];
        }
        return 0;
    }

}