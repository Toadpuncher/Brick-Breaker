package com.game.src.main;

import com.game.src.main.classes.EntityBall;
import com.game.src.main.classes.EntityBricks;
import com.game.src.main.classes.EntityPlayer;

import java.util.LinkedList;

public class Physics {

    public static boolean Collision(EntityBall entityBall, LinkedList<EntityBricks> entityBricks){

        for(int i = 0; i < entityBricks.size(); i++)
        if(entityBall.getBounds().intersects(entityBricks.get(i).getBounds())){
            return true;
        }

        return false;
    }
    public static boolean Collision(EntityBricks entityBricks, EntityBall entityBall){

            if(entityBricks.getBounds().intersects(entityBall.getBounds())){
                return true;
            }

        return false;
    }

    public static boolean Collision(EntityBall entityBall, EntityPlayer entityPlayer) {
        if(entityPlayer.getBounds().intersects(entityBall.getBounds())){
            return true;
        }

        return false;
    }
}
