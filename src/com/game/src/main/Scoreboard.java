package com.game.src.main;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Scoreboard {

    Game game;
    private static final String HIGHSCORE_FILE =  new File("res/scores.dat").getAbsolutePath();
    private ArrayList<Score> scores ;
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;
    String formNameString;
    private static final String DEFAULT_FORM_NAME_STRING = "---";
    private static final String charString = "-ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int char1 = 0;
    int char2 = 0;
    int char3 = 0;

    private enum STATE{
        SCOREBOARD,
        FORM
    }
    private STATE state;
    private int stateIndex;

    private class ScoreComparator implements Comparator<Score>{
        @Override
        public int compare(Score score, Score t1) {
           int sc1 = score.score;
           int sc2 = t1.score;

           if(sc1 > sc2){
               return -1;
           }else if(sc1 < sc2){
               return +1;
           }else
            return 0;
        }
    }

    public void Sort(){
        sort();
    }

    public Scoreboard(Game game){
        this.game = game;
        state = STATE.SCOREBOARD;
        stateIndex = 0;
        scores = new ArrayList<Score>();
    }

    public ArrayList<Score> getScores(){
        loadScoreFile();
        sort();
        return scores;
    }

    public void addScore(String name, int score){
        loadScoreFile();
        scores.add(new Score(name,score));
        updateScoreFile();
    }


    private void sort(){
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scores, comparator);
    }

    public void loadScoreFile(){
        try{
            inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
            scores = (ArrayList<Score>) inputStream.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("[Load] FNF Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Load] IO Error: " + e.getMessage());
        } catch (ClassNotFoundException e){
            System.out.println("[Load] CNF Error: " + e.getMessage());
        }
        finally {
            try {
                if(outputStream != null){
                    outputStream.flush();
                    outputStream.close();
                }
            }catch (IOException e) {
                System.out.println("[Load] IO Error: " + e.getMessage());
             }
        }
    }

    private void updateScoreFile() {
        try{
            outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
            outputStream.writeObject(scores);
        } catch (FileNotFoundException e) {
            System.out.println("[Update] FNF Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Update] IO Error: " + e.getMessage());
        }
        finally {
            try {
                if(outputStream != null){
                    outputStream.flush();
                    outputStream.close();
                }
            }catch (IOException e) {
                System.out.println("[Update] IO Error: " + e.getMessage());
            }
        }


    }

    public void incCharNum(int charNum){
        switch(charNum){
            case 1:
                if(char1 < 26)
                char1 = (char1 + 1);
                else char1 = 0;
                break;
            case 2:
                if(char2 < 26)
                char2 = (char2 + 1);
                else char2 = 0;
                break;
            case 3:
                if(char3 < 26)
                char3 = (char3 + 1);
                else char3 = 0;
                break;
        }
    }
    public void decCharNum(int charNum){
        switch(charNum){
            case 1:
                if(char1 > 0)
                char1 = (char1 - 1);
                else char1 = 26;
                break;
            case 2:
                if(char2 > 0)
                char2 = (char2 - 1);
                else char2 = 26;
                break;
            case 3:
                if(char3 > 0)
                char3 = (char3 - 1);
                else char3 = 26;
                break;
        }
    }

    public String getChars(){
        return new StringBuilder().append(charString.charAt(char1)).append(charString.charAt(char2)).append(charString.charAt(char3)).toString();
    }
    public void resetChars(){
        char1 = 0;
        char2 = 0;
        char3 = 0;
    }

    public void render(Graphics g){
        state = STATE.values()[stateIndex];
        Font fnt0 = game.mainFont.deriveFont(50f);
        Font fnt1 = game.mainFont.deriveFont(24f);
        g.setFont(fnt1);
        g.setColor(Color.white);
        switch (state) {
            case SCOREBOARD:
                int i = 0;
                for(Score s : scores){
                g.drawString((s.name +"  " + s.score), Game.WIDTH / 2, 100+25*i);
                i++;
                }
                g.drawString("Press space to continiue to main menu", Game.WIDTH / 2 + 120, 350);
                break;
            case FORM:
                g.drawString(getChars() +'\t'+ game.getScore(), Game.WIDTH / 2, 100);
                break;
        }
        g.setFont(fnt1);
        g.setColor(Color.white);
        g.drawString("Score: "+ game.getScore(), Game.WIDTH - 125,25);
    }
    public int getStateIndex() {
        return stateIndex;
    }
    public void setState(int state) {
        this.stateIndex = state;
    }

    public void clean(){
        if (scores.size() > 10)
            scores.remove(scores.size() - 1);
    }

    public STATE getState() {
        return state;
    }

    public String getHighscoreString() {
        String highscoreString = "";
	    int max = 10;

        ArrayList<Score> scores;
        scores = getScores();

        int i = 0;
        int x = scores.size();
        if (x > max) {
            x = max;
        }
        while (i < x) {
            highscoreString += (i + 1) + ".\t" + scores.get(i).name + "\t\t" + scores.get(i).score + "\n";
            i++;
        }
        return highscoreString;
    }


}
