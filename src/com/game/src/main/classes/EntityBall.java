package com.game.src.main.classes;

import java.awt.*;

public interface EntityBall {

    public void tick();
    public void render(Graphics g);

    public double getX();
    public double getY();
    public Rectangle getBounds();

    void setVelX(double i);
    void setVelY(double i);
    void setX(double i);
    void setY(double i);

    public double getVelX();
    public double getVelY();
}
