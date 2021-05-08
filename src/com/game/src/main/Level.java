package com.game.src.main;

public class Level {
    private int[] startingBlockCount = {16,20,20};
    private int currentLevelIndex;
    int[][] level1 = {{
             1,1,1,1,1,1,1,1,1,1},
            {1,1,1,0,0,0,0,1,1,1},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0}};
    int[][] level2 = {{
             1,1,1,1,0,0,1,1,1,1},
            {1,1,1,0,1,1,0,1,1,1},
            {0,0,1,1,0,0,1,1,0,0},
            {0,0,0,0,0,0,0,0,0,0}};
    int[][] level3 = {{
            1,1,1,0,0,0,0,1,1,1},
            {1,1,1,1,0,0,1,1,1,1},
            {1,1,1,0,0,0,0,1,1,1},
            {0,0,0,0,0,0,0,0,0,0}};
    int[][] currentLevel = new int[4][10];

    Game game;

    Level(Game game)
    {
        this.game = game;
        currentLevelIndex = 1;
        changeLevel(currentLevelIndex);
    }

    public void changeLevel(int n){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 10; j++){
                switch (n){
                    case 1:
                        currentLevel[i][j] = level1[i][j];
                        break;
                    case 2:
                        currentLevel[i][j] = level2[i][j];
                        break;
                    case 3:
                        currentLevel[i][j] = level3[i][j];
                        break;


                }
            }
        }
    }
    public int getBrickCount(){
        return startingBlockCount[currentLevelIndex-1];
    }
    public void incIndex(){
        if(currentLevelIndex < 3)
            currentLevelIndex++;
    }
    public void resetIndex(){
        currentLevelIndex = 1;
    }
    public boolean isLastLevel(){
        if (currentLevelIndex == 3)
            return true;
        return false;
    }
    public void setCurrentLevelIndex(int currentLevelIndex) {
        this.currentLevelIndex = currentLevelIndex;
    }

    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }
}
