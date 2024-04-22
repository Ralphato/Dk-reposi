package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Class representing a whip that can be used by the player.
 */
public class Shield {
	private static final int FRAME_COUNT = 3;
    private Texture texture;
    private Rectangle bounds;
    private boolean isActive = true; //make it true when player presses x from player class
    private float damage;
    private float f_playerX, f_playerY;
    private float f_scaleX = 1;
    private float f_scaleY = 1;
    private boolean f_getBounds;
    private boolean f_active;
    private float f_stateTime;
    private boolean f_start, f_end, f_win;
    private Texture[] f_animationTexture;
	private Animation<TextureRegion> f_ShieldAnimation;
	
	private Texture Bubble_1, Bubble_2, Bubble_3;
	private float[] frameDurations = {0.2f, 0.5f, 0.4f, 0.2f, 0.2f, 0.2f, 2.5f}; // 0.5 seconds for each frame
	private float[] f_frameXOffset = {-4000, 20 , -20, 20, 20, 20, 20}; // x position for each frame
	private float[] f_frameYOffset = { -335, 40 , 40, 30, 20, 20, 20}; // y position for each frame
	private int currentFrameIndex = 0;

	
    /**
     * Constructor for the Whip.
     * @param x The initial x position of the whip.
     * @param y The initial y position of the whip.
     * @param width The width of the whip.
     * @param height The height of the whip.
     * @param texturePath The file path for the whip texture.
     */
    public Shield(float x, float y, float width, float height) {
        this.bounds = new Rectangle(x, y, width, height);
        this.isActive = true;
        f_stateTime = 0f;
        
        
        f_animationTexture = new Texture[FRAME_COUNT ];
        TextureRegion[] ShieldFrames = new TextureRegion[FRAME_COUNT];

        for (int i = 0; i < FRAME_COUNT; i++) {
        	
            String frameName = "Bubble" + (i + 1) + ".png";
            f_animationTexture[i] = new Texture(frameName);
            ShieldFrames[i] = new TextureRegion(f_animationTexture[i]);
        	
        }
        
        f_ShieldAnimation = new Animation<TextureRegion>(0.4f, ShieldFrames);
        
        
        //manual bubble texture
        Bubble_1 = new Texture("Bubble1.png");
        Bubble_2 = new Texture("Bubble2.png");
        Bubble_3 = new Texture("Bubble3.png");
        

        
    }
    public void setWStart(boolean state) {
    	f_start = state;
    }
    public void setWEnd(boolean state) {
    	f_end = state;
    }
	public void setWWin(boolean state) {
		f_win = state;
	}

	public void update(float delta) {
   	 	f_getBounds = false;
   	 	if(f_start && !(f_end||f_win)) {
   		 if (MyGdxGame.shield = true) {
   	 	//System.out.println(" x is Pressed");
    	f_active = true;
   		 }
    	else {
    		f_active = false;
    	}
   		if (f_active) { //make word shake
            f_stateTime += delta; //change here to make it faster/slower
            
            // Check if we should change to the next frame
            if(f_stateTime >= frameDurations[currentFrameIndex]) {
                f_stateTime -= frameDurations[currentFrameIndex];
                currentFrameIndex++;
                
                // Loop back to the first frame or stop the animation as needed
                if (currentFrameIndex >= FRAME_COUNT) {
                    currentFrameIndex = 0; // or set isActive to false if the animation should stop
                }
            }
   	 }
	}
	}
    /**
     * Draws the whip if it is active.
     * @param batch The SpriteBatch used to draw the whip texture.
     */
    public void draw(SpriteBatch batch) {
        	 if(f_active) {
             	//System.out.println("X is pressed");
             	//TextureRegion currentFrame = f_whipAnimation.getKeyFrame(f_stateTime / 10, true);
     		    //batch.draw(currentFrame, f_playerX, f_playerY, texture.getWidth() * f_scaleX * 3, texture.getHeight() * f_scaleY * 3);
        		 
        		 Texture currentTexture = f_animationTexture[currentFrameIndex];
        		 
        		 
        		 batch.draw(currentTexture, f_playerX + 90 , f_playerY -15, - currentTexture.getWidth() * f_scaleX, currentTexture.getHeight() * f_scaleY);
        	 
        	 } }
       
        
        
    

    /**
     * Activates the whip, enabling its use.
     */
    public void activate() {
        f_active = true;
    }

    /**
     * Deactivates the whip, disabling its use.
     */
    public void deactivate() {
        f_active = false;
    }
    
    public void getPlayerPosition(float f_x, float f_y) {
    	f_playerX = f_x;
    	f_playerY = f_y;
    }
    
    public Rectangle getBounds() {
    	if(f_getBounds) {  		
    			return new Rectangle(f_playerX, f_playerY + 60, 240, 10);		
    	}
    	else {
    		return new Rectangle();
    	}
    }
    				
    public Rectangle getBounds2() { // for left direction
    	if(f_getBounds) {	
    		return new Rectangle(f_playerX + 73, f_playerY + 60, -240, 10);		
    	}
    	else {
    		return new Rectangle();
    	}
    }
}