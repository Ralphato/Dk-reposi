package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


/**
 * Represents the player character in the game.
 */
public class Player {
    // Constants
    private static final int FRAME_COUNT = 2;

    // Data members
    private Texture f_texture;
    private Texture f_textureJump;
    private Texture f_textureFall;
    private Texture[] f_animationTexture;
    private float f_x, f_y;
    private float f_scaleX, f_scaleY;
    private float f_xVelocity;
    private float f_yVelocity;
    private boolean f_jumping;
    private boolean f_isFalling;
    private boolean f_lookingLeft = false;
    private boolean f_lookingRight = false;
    private boolean f_idleRight = true;
    private boolean f_idleLeft = false;
    private Animation<TextureRegion> f_runAnimation;
    private float f_stateTime;
    private Platform f_currentPlatform;

    /**
     * Initializes a new instance of the Player class.
     */
    public Player() {
        f_texture = new Texture("mario sprite.png");
        f_textureJump = new Texture("jump2.png");
        f_textureFall = new Texture("fall.png");
        f_x = 100;
        f_y = 100;
        f_scaleX = 1f;
        f_scaleY = 1f;
        f_yVelocity = 0;
        f_jumping = false;
        f_lookingLeft = false;

        f_animationTexture = new Texture[FRAME_COUNT];
        TextureRegion[] runFrames = new TextureRegion[FRAME_COUNT];
        for (int i = 0; i < FRAME_COUNT; i++) {
            String frameName = "run" + (i + 1) + ".png";
            f_animationTexture[i] = new Texture(frameName);
            runFrames[i] = new TextureRegion(f_animationTexture[i]);
        }
        f_runAnimation = new Animation<TextureRegion>(0.1f, runFrames);
        f_stateTime = 0f;
    }

    /**
     * Causes the player to jump.
     */
    private void jump() {
        f_yVelocity = 15;
        f_jumping = true;
    }
    
    public void setPosition(float x, float y) {
    	this.f_x = x;
    	this.f_y = y;
    }
    
    public float getPositionX() {
    	return f_x;
    }
    
    public Rectangle getBounds() {
        // Assuming there are x, y, width, and height fields in the Player class
        return new Rectangle(f_x, f_y, f_texture.getWidth() * f_scaleX, f_texture.getHeight() * f_scaleY);
    }
    
    public void setCurrentPlatform(Platform platform) {
        this.f_currentPlatform = platform;
    }

    /**
     * Updates the player's state.
     *
     * @param delta Time since last game frame.
     */
    public void update(float delta) {
        if (f_lookingLeft) {
            f_idleLeft = true;
            f_lookingLeft = false;
        } else if (f_lookingRight) {
            f_idleRight = true;
            f_lookingRight = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            f_x -= 200 * delta;
            f_lookingLeft = true;
            f_lookingRight = false;
            f_idleRight = false;
            f_idleLeft = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            f_x += 200 * delta;
            f_lookingRight = true;
            f_lookingLeft = false;
            f_idleRight = false;
            f_idleLeft = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !f_jumping) {
            jump();
        }
        if (f_jumping) {
            f_yVelocity -= 0.5f;
            f_y += f_yVelocity;

            if (f_yVelocity <= 0) {
                f_isFalling = true;
                f_idleRight = false;
                f_idleLeft = false;
                f_lookingRight = false;
                f_lookingLeft = false;
            }

            if (f_jumping && f_currentPlatform != null) {
                // This ensures the player lands on top of the platform
                float platformTopY = f_currentPlatform.getBounds().y + f_currentPlatform.getBounds().height;
                if (f_y <= platformTopY) {
                    f_y = platformTopY; // Make sure to land on top of the platform
                    f_yVelocity = 0;
                    f_jumping = false;
                    f_isFalling = false;
                    f_idleRight = true; // You might want to adjust this based on the player's direction before jumping
                }
            }

		if(f_x>=600) {
	        	f_xVelocity = -4f;
	        	f_x+=f_xVelocity;
		}else if (f_x<=0) {
	        	f_xVelocity = 4f;
	        	f_x+=f_xVelocity;
	        }
        }
        f_stateTime += delta;
    }

    /**
     * Draws the player on the screen.
     *
     * @param batch The SpriteBatch used for drawing.
     */
	    public void draw(SpriteBatch batch) {
	    	
	    	
	    	
	    	 if (f_idleRight) {
	    		 batch.draw(f_texture, f_x, f_y, f_texture.getWidth() * f_scaleX, f_texture.getHeight() * f_scaleY);
	    	 }
	    	 else if(f_idleLeft) {
	    		 batch.draw(f_texture, f_x + f_texture.getWidth() * f_scaleX, f_y, -f_texture.getWidth() * f_scaleX, f_texture.getHeight() * f_scaleY);
	    	 }
	    	 
	    	 if(f_isFalling) {
	    		 batch.draw(f_textureFall, f_x, f_y, f_texture.getWidth() * f_scaleX, f_texture.getHeight() * f_scaleY);
	    	 }
	    	 
	    	 /* implement later, use if (lokingleft/right to detect when to what side the player will face after jumping)
	    	 if(jumping) {
	    		 batch.draw(textureJump, x, y, texture.getWidth() * scaleX, texture.getHeight() * scaleY);
	    	 }
	    	 */
	    	 
	    	 if (f_lookingLeft) {
	    		//switching the starting point to the right of the asset instead of the left, then drawing from the point to the left direction
		        
		        TextureRegion currentFrame = f_runAnimation.getKeyFrame(f_stateTime, true);
		        batch.draw(currentFrame, f_x, f_y, f_texture.getWidth() * f_scaleX, f_texture.getHeight() * f_scaleY);
		        f_idleLeft = true;

	    	}
	    	
	    	 else if(f_lookingRight) {
		        
		     // Get the current frame of the animation
		    	TextureRegion currentFrame = f_runAnimation.getKeyFrame(f_stateTime, true);
		    	
		    	batch.draw(currentFrame, f_x + f_texture.getWidth() * f_scaleX, f_y, -f_texture.getWidth() * f_scaleX, f_texture.getHeight() * f_scaleY);
		    	f_idleRight = true;

	    	 }  	
	    	
	    }
	    
	    // Disposes resources including texture
	    public void dispose() {
	        f_texture.dispose();
	        for(TextureRegion frame : f_runAnimation.getKeyFrames()) {
	        	frame.getTexture().dispose();
	        }
	        
	        for(Texture texture : f_animationTexture) {
	        	if (texture != null) {
	        		texture.dispose();
	        	}
	        }
	    }
}
