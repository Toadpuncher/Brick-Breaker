package com.game.src.main;


import java.awt.image.BufferedImage;

public class Textures {

    private SpriteSheet ballSprite = null, playerSprite = null, brickSprite = null;
    public BufferedImage player, ball, brick;

    private int PLAYER_TEXTURE, BRICK_TEXTURE, BALL_TEXTURE;

    public Textures(Game game) {
        ballSprite = new SpriteSheet(game.getBallSprite());
        playerSprite = new SpriteSheet(game.getPlayerSprite());
        brickSprite = new SpriteSheet(game.getBrickSprite());
        this.PLAYER_TEXTURE = 1;
        this.BALL_TEXTURE = 1;
        this.BRICK_TEXTURE = 1;
        getTextures();
    }

    private void getTextures() {
        player = playerSprite.grabPad(PLAYER_TEXTURE);
        ball = ballSprite.grabBall(BALL_TEXTURE);
        brick = brickSprite.grabBrick(1, BRICK_TEXTURE);

    }

    public void getBrickTexture(Bricks brick) {

    }


    public void setTextures(int BALL_TEXTURE, int PLAYER_TEXTURE) {
        this.PLAYER_TEXTURE = PLAYER_TEXTURE;
        this.BALL_TEXTURE = BALL_TEXTURE;
        getTextures();
    }
}

