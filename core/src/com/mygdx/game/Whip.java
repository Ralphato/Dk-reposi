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
public class Whip {
	private static final int FRAME_COUNT = 7;
    private Texture texture;
    private Rectangle bounds;
    private boolean isActive = true; //make it true when player presses x from player class
    private float damage;
    private float f_playerX, f_playerY;
    private float f_scaleX = 3;
    private float f_scaleY = 3;
    private boolean f_lookingRight;
    private boolean f_xPressed;
    private boolean f_getBounds;
    private float f_stateTime;
    private boolean f_start, f_end, f_win;
    private Texture[] f_animationTexture;
	private Animation<TextureRegion> f_whipAnimation;
	
	private Texture textureWhip1, textureWhip2, textureWhip3, textureWhip4, textureWhip5, textureWhip6, textureWhip7;
	private float[] frameDurations = {0.2f, 0.5f, 0.4f, 0.2f, 0.2f, 0.2f, 2.5f}; // 0.5 seconds for each frame
	private float[] f_frameXOffset = {-4000, 20 , -20, 20, 20, 20, 20}; // x position for each frame
	private float[] f_frameYOffset = { -335, 40 , 40, 30, 20, 20, 20}; // y position for each frame
	private int currentFrameIndex = 0;

	
	private Sound f_hitSound;
    /**
     * Constructor for the Whip.
     * @param x The initial x position of the whip.
     * @param y The initial y position of the whip.
     * @param width The width of the whip.
     * @param height The height of the whip.
     * @param texturePath The file path for the whip texture.
     */
    public Whip(float x, float y, float width, float height, String texturePath) {
        this.texture = new Texture(texturePath);
        this.bounds = new Rectangle(x, y, width, height);
        this.isActive = true;
        this.damage = 10;  // Default damage value, can be adjusted.
        f_stateTime = 0f;
        
        
        f_animationTexture = new Texture[FRAME_COUNT ];
        TextureRegion[] whipFrames = new TextureRegion[FRAME_COUNT];

        for (int i = 0; i < FRAME_COUNT; i++) {
        	
            String frameName = "whip" + (i + 1) + ".png";
            f_animationTexture[i] = new Texture(frameName);
            whipFrames[i] = new TextureRegion(f_animationTexture[i]);
        	
        }
        
        f_whipAnimation = new Animation<TextureRegion>(0.1f, whipFrames);
        
        
        //manual whip texture
        textureWhip1 = new Texture("whip1.png");
        textureWhip2 = new Texture("whip2.png");
        textureWhip3 = new Texture("whip3.png");
        textureWhip4 = new Texture("whip4.png");
        textureWhip5 = new Texture("whip5.png");
        textureWhip6 = new Texture("whip6.png");
        textureWhip7 = new Texture("whip7.png");
        
        f_hitSound = Gdx.audio.newSound(Gdx.files.internal("whip-sound.mp3"));
        
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
     if (Gdx.input.isKeyPressed(Input.Keys.X)) {
    	 //System.out.println(" x is Pressed");
    	 f_xPressed = true;
     }
     else {
    	 f_xPressed = false;
     }
     
     if (f_xPressed) { //make word shake
         f_stateTime += delta * 3; //change here to make it faster/slower
         
         // Check if we should change to the next frame
         if(f_stateTime >= frameDurations[currentFrameIndex]) {
             f_stateTime -= frameDurations[currentFrameIndex];
             currentFrameIndex++;
             
             // Loop back to the first frame or stop the animation as needed
             if (currentFrameIndex >= FRAME_COUNT) {
                 currentFrameIndex = 0; // or set isActive to false if the animation should stop
             }
             
             //play sound
             if(currentFrameIndex == 2) {
            	 f_hitSound.play();
             }
             
             if(currentFrameIndex == 6 || currentFrameIndex == 7) {
            	f_getBounds = true;
             }
            
            	
             
         }
     } else {
         f_stateTime = 0;
         currentFrameIndex = 0;
     }
     
    
    	 }
     f_stateTime += delta;
    }

    /**
     * Draws the whip if it is active.
     * @param batch The SpriteBatch used to draw the whip texture.
     */
    public void draw(SpriteBatch batch) {
        if (isActive) {
        	 if(f_xPressed) {
             	//System.out.println("X is pressed");
             	//TextureRegion currentFrame = f_whipAnimation.getKeyFrame(f_stateTime / 10, true);
     		    //batch.draw(currentFrame, f_playerX, f_playerY, texture.getWidth() * f_scaleX * 3, texture.getHeight() * f_scaleY * 3);
        		 
        		 Texture currentTexture = f_animationTexture[currentFrameIndex];
        		 float f_xOffset = f_frameXOffset[currentFrameIndex];
        		 float f_yOffset = f_frameYOffset[currentFrameIndex];
        		 
        		 if(f_lookingRight) {
                 batch.draw(currentTexture, f_playerX + f_xOffset, f_playerY + f_yOffset, currentTexture.getWidth() * f_scaleX * 2, currentTexture.getHeight() * f_scaleY);
                 	//System.out.println("If statement f_xOffset:" + f_xOffset);
                 }
        		 else {
        			 batch.draw(currentTexture, f_playerX + 73 - f_xOffset, f_playerY + f_yOffset, - currentTexture.getWidth() * f_scaleX * 2, currentTexture.getHeight() * f_scaleY);
        			//System.out.println("Else statement f_xOffset:" + f_xOffset);
        		 }
        		 
             } else {
                 // Draw the idle frame
            	 if(f_lookingRight) {
 	        		//System.out.println("LOOking Right");
 	        		batch.draw(texture, f_playerX, f_playerY, texture.getWidth() * f_scaleX, texture.getHeight() * f_scaleY);
 	        	}
 	        	else {
 	        		//System.out.println("LOOking left");
 	        		batch.draw(texture, f_playerX + 73 , f_playerY, - texture.getWidth() * f_scaleX, texture.getHeight() * f_scaleY);
 	        	}
             }
        		
        		 
             }
        	 
        }
        
       
        
        
    

    /**
     * Activates the whip, enabling its use.
     */
    public void activate() {
        isActive = true;
    }

    /**
     * Deactivates the whip, disabling its use.
     */
    public void deactivate() {
        isActive = false;
    }
    
    public void getPlayerPosition(float f_x, float f_y) {
    	f_playerX = f_x;
    	f_playerY = f_y;
    }
    
    public void getPlayerRightDirection(boolean f_direction) {
    	if(f_direction) {
    		f_lookingRight = true;
    	}
    	else {
    		f_lookingRight = false;
    		
    	}
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
    			
    			
    		
    	

   
    /**
     * Updates the position and state of the whip.
     * @param x New x position.
     * @param y New y position.
     */
    public void update(float x, float y) {
        bounds.setPosition(x, y);
    }
    
 

    /**
     * Gets the damage dealt by the whip.
     * @return The damage value.
     */
    public float getDamage() {
        return damage;
    }

    /**
     * Sets the damage value of the whip.
     * @param damage New damage value.
     */
    public void setDamage(float damage) {
        this.damage = damage;
    }

    /**
     * Checks if the whip overlaps with a given rectangle.
     * @param other The rectangle to check for overlap.
     * @return true if there is an overlap; false otherwise.
     */
    public boolean overlaps(Rectangle other) {
        return bounds.overlaps(other);
    }

    /**
     * Disposes of the texture resource.
     */
    public void dispose() {
        texture.dispose();
        
        for(TextureRegion frame : f_whipAnimation.getKeyFrames()) {
        	frame.getTexture().dispose();
        }
        
        for(Texture texture : f_animationTexture) {
        	if (texture != null) {
        		texture.dispose();
        	}
        	
        	if(f_hitSound != null) {
        		f_hitSound.dispose();
        	}
        }
    }
}
