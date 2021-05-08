package com.game.src.main;

import com.game.src.main.classes.EntityBall;
import com.game.src.main.classes.EntityBricks;
import com.game.src.main.classes.EntityPlayer;

import java.awt.*;
import java.util.LinkedList;

public class Controller {

    private LinkedList<EntityBricks> bricksEntity = new LinkedList<EntityBricks>();
    private boolean gameStarted = false;

    EntityBall entityBall;
    EntityBricks entityBrick;
    EntityPlayer entityPlayer;
    Game game;
    Textures tex;
//    int[][] level1 = {{1,1,1,1,1,1,1,1,1,1,1},{1,1,1,0,0,0,0,1,1,1}};

    Level level;

    public Controller(Game game,Textures tex,Level level){
        this.game = game;
        this.tex = tex;
        this.level = level;
        addLevel();
    }

    public void tick(){

        if(!gameStarted){
            this.entityBall.setX(entityPlayer.getX()+154/2);
            this.entityBall.setY(entityPlayer.getY()-33);
        }

        for(int i = 0; i < bricksEntity.size(); i++){
            entityBrick = bricksEntity.get(i);
            entityBrick.tick();
        }
        entityBall.tick();
        entityPlayer.tick();
    }

    public void render(Graphics g){
        for(int i = 0; i < bricksEntity.size();i++){
            entityBrick = bricksEntity.get(i);

            entityBrick.render(g);
        }

        entityBall.render(g);
        entityPlayer.render(g);
    }

    public void addLevel(){
        for(int y = 0; y < 4;y++){
            for(int x = 0; x < 10; x++ ) {
                if(level.currentLevel[y][x] == 1)
                    addBrick(new Bricks(x*85, y*29+58, tex, game, this));
            }
        }

    }

    public void clearBricks(){
        bricksEntity.clear();
    }

    public void addBall(EntityBall entityBall){
        this.entityBall = entityBall;
    }
    public void resetBall(){
        gameStarted = false;
        this.entityBall.setVelY(0);
        this.entityBall.setVelX(0);
        this.entityBall.setX(entityPlayer.getX()+154/2);
        this.entityBall.setY(entityPlayer.getY()-33);

    }
    public void addPlayer(EntityPlayer entityPlayer){
        this.entityPlayer = entityPlayer;
    }
    public void addBrick(EntityBricks brick){
        bricksEntity.add(brick);
    }
    public void removeBrick(EntityBricks brick){
        bricksEntity.remove(brick);
    }

    public void ballStart(int velocity) {
      if(!gameStarted) {
          entityBall.setVelX(-velocity);
          entityBall.setVelY(-velocity);
          gameStarted = true;
      }
    }

    public void ballStop(){
        gameStarted = false;
        entityBall.setVelX(0);
        entityBall.setVelY(0);
    }
    public LinkedList<EntityBricks> getBricksEntity() {
        return bricksEntity;
    }

    public EntityBall getEntityBall() {
        return entityBall;
    }

    public EntityPlayer getEntityPlayer() {
        return entityPlayer;
    }
}
