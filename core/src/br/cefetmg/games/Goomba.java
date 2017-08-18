
package br.cefetmg.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Goomba {
    private int moveUp = 0, moveDown = 1, moveLeft = 2, moveRigt = 3, stop = 4;
    private float posX;
    private float posY;
    private int frameWidth;
    private int frameHeight;
    private float speed;
    private float time;
    private float spriteDuration;
    private int currentMove;
    private Texture texture;
    private TextureRegion[][] animationFrames;
    private TextureRegion lastKeyFrame;
    private Animation<TextureRegion>[] animations;
    
    public Goomba() {
        init();
    }
    
    public Goomba(String texture) {
        this.texture = new Texture(texture);
        init();
    }
    
    public Goomba(String spriteName, int spriteLines, int spriteCols) {
        init();
        texture = new Texture(spriteName);
        animationFrames = TextureRegion.split(texture, texture.getWidth() / spriteCols, texture.getHeight() / spriteLines);
        animations = new Animation[spriteLines];
        
        for (int i = 0; i < spriteLines; i++)
            animations[i] = new Animation<TextureRegion>(spriteDuration, animationFrames[i]);
        
        lastKeyFrame = animationFrames[0][0];
        frameWidth = lastKeyFrame.getRegionWidth();
        frameHeight = lastKeyFrame.getRegionHeight();
        
        setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }
    
    private void init() {
        posX = posY = 0;
        speed = 2;
        time = 0;
        spriteDuration = 0.1f;
        currentMove = stop;
    }
    
    public float getPosX() {
        return this.posX;
    }
    
    public float getPosY() {
        return this.posY;
    }
    
    public void setPos(float x, float y) {
        posX = x;
        posY = y;
    }
    
    public void incrementTime(float time) {
        this.time += time;
    }
    
    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
    public void setSpriteDuration(float duration) {
        this.spriteDuration = duration;
    }
    
    public void setPlayMode(Animation.PlayMode mode) {
        animations[moveUp].setPlayMode(mode);
        animations[moveDown].setPlayMode(mode);
        animations[moveLeft].setPlayMode(mode);
        animations[moveRigt].setPlayMode(mode);
    }
    
    public void spriteMoveMap(int up, int down, int left, int right) {
        moveUp = up;
        moveDown = down;
        moveLeft = left;
        moveRigt = right;
    }
    
    public void update() {
        int x = 0;
        int y = 0;
        
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            y = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            y = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            x = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            x = 1;
        
        if (posX + x < Gdx.graphics.getWidth() - frameWidth && posX + x >= 0)
            posX += x*speed;
        if (posY + y < Gdx.graphics.getHeight() - frameHeight && posY + y >= 0)
            posY += y*speed;
        
        if (y != 0)
            currentMove = (y > 0) ? moveUp : moveDown;
        else if (x != 0)
            currentMove = (x > 0) ? moveRigt : moveLeft;
    }
    
    public void render(SpriteBatch batch) {
        if (currentMove != stop)
            lastKeyFrame = animations[currentMove].getKeyFrame(time);
        
        currentMove = stop;
        batch.draw(lastKeyFrame, this.posX, this.posY);
    }
}
