package com.mygdx.game;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;


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
    private Texture f_textureSkid;
    private Texture f_textureClimb;
    private Texture f_climbDown;
    private Texture[] f_animationTexture;
    private Texture[] f_animationTextureClimbing;
    private float f_x, f_y;
    private float f_scaleX, f_scaleY;
    private float f_scaleXClimb, f_scaleYClimb;
    private float f_xVelocity;
    private float f_yVelocity;
    private boolean f_jumping;
    private boolean f_isFalling;
    private boolean f_lookingLeft = false;
    private boolean f_lookingRight = false;
    private boolean f_idleRight = true;
    private boolean f_idleLeft = false;
    private Animation<TextureRegion> f_runAnimation;
    private Animation<TextureRegion> f_climbAnimation;
    private float f_stateTime;
    private Platform f_currentPlatform;
    private boolean checkIfColliding;
    private boolean f_stopMoving;
    private boolean f_climbing;
    private boolean f_climbingNoMove = false;
    private boolean f_climbingUp = false;
    private boolean f_climbingDown = false;
    private boolean f_finishedClimbing = false;
    private boolean f_moving;
    public Body body; 
    
    /**
     * Initializes a new instance of the Player class.
     */
    
    public Player() {
        f_texture = new Texture("mario sprite.png");
        f_textureJump = new Texture("jump2.png");
        f_textureFall = new Texture("fall.png");

        f_textureClimb = new Texture("climb1.png");
        f_climbDown = new Texture("climbDown.png");
        f_x = 100;
        f_y = 100;
        f_scaleX = 1f;
        f_scaleY = 1f;
        f_scaleXClimb = 1.3f;
        f_scaleYClimb = 1.3f;
        f_yVelocity = 0;
        f_jumping = false;
        f_lookingLeft = false;
        
        
        
        
        //running animation
        f_animationTexture = new Texture[FRAME_COUNT];
        TextureRegion[] runFrames = new TextureRegion[FRAME_COUNT];
        for (int i = 0; i < FRAME_COUNT; i++) {
            String frameName = "run" + (i + 1) + ".png";
            f_animationTexture[i] = new Texture(frameName);
            runFrames[i] = new TextureRegion(f_animationTexture[i]);
        }
        f_runAnimation = new Animation<TextureRegion>(0.1f, runFrames);
        
        
        //climbing animation
        f_animationTextureClimbing = new Texture[FRAME_COUNT]; //array of Texture
        TextureRegion[] climbFrames = new TextureRegion[FRAME_COUNT]; //array of Texture region
        for (int i = 0; i < FRAME_COUNT; i++) {
            String frameName2 = "climb" + (i + 1) + ".png";
            f_animationTextureClimbing[i] = new Texture(frameName2);
            climbFrames[i] = new TextureRegion(f_animationTextureClimbing[i]);
        }
        f_climbAnimation = new Animation<TextureRegion>(0.4f, climbFrames);
        
        f_stateTime = 0f;
    }

    
    

    
    /**
     * Causes the player to jump.
     */
    
    private void jump() {
        f_yVelocity = 14;
        if(!f_climbing) {
        f_jumping = true;
        }
       
    }
  
    public void setPosition(float x, float y) {
    	this.f_x = x;
    	this.f_y = y;
    }
    
    public float getPositionX() {
    	return f_x;
    }
    
    public float getPositionY() {
    	return f_y;
    }
    
    public Rectangle getBounds() {
    	
        return new Rectangle(f_x, f_y, f_texture.getWidth() * f_scaleX, f_texture.getHeight() * f_scaleY);
    }
    
    public Rectangle getBodyBounds() {
    	float legsHeight = f_texture.getHeight() * f_scaleY * 0.2f; // Height of the legs
        // Assuming there are x, y, width, and height fields in the Player class
        return new Rectangle(f_x, f_y + legsHeight, f_texture.getWidth() * f_scaleX, (f_texture.getHeight() - 10) * f_scaleY);
    }
    
    public Rectangle getLegsBounds() {
        // Assuming there are x, y, width, and height fields in the Player class,
        
        
        float legsHeight = f_texture.getHeight() * f_scaleY * 0.2f; // Height of the legs

        
        return new Rectangle(f_x, f_y, f_texture.getWidth() * f_scaleX, legsHeight); //f_y starts at bottom 
    }
    
    public void setCurrentPlatform(Platform platform) {
        this.f_currentPlatform = platform;
    }
    
    public void checkCollision(boolean platform) {
        this.checkIfColliding= platform;
    }
    
    public void checkLadder(boolean climb) {
    	this.f_climbing = climb;
    }
    
    public void canMove(boolean move) {
    	this.f_moving = move;
    }
    
    public void finishedClimbing(boolean finished) {
    	this.f_finishedClimbing = finished;
    }
    
    public boolean isJumping() {
    	return f_jumping ;
    }
    
    

    /**
     * Updates the player's state.
     *
     * @param delta Time since last game frame.
     */
    public void update(float delta) {
    	
    	
     	if (this.f_climbing) {
    		//System.out.println("Entered if statement");
            // The player is climbing
            // Insert logic for climbing here
            // For example, you might want to move the player up or down a ladder:
     		
     		f_climbingNoMove = true;
     		
     		f_climbingUp = false;
            f_climbingDown = false;
            f_lookingLeft = false;
            f_lookingRight = false;
            f_idleRight = false;
            f_idleLeft = false;
            
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                f_y += 5 ; // Climbing up
                f_climbingUp = true; 
                f_climbingDown = false;
                f_idleRight = false;
                f_idleLeft = false;
                f_climbingNoMove = false;
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                f_y -= 5;  // Climbing d
                f_climbingDown = true;
                f_climbingUp = false;
                f_idleRight = false;
                f_idleLeft = false;
                f_climbingNoMove = false;
            }
            
           
            
            //f_idleRight = false;
            //f_idleLeft = false;
    	}
    	
    	if(!f_climbing || f_moving) {
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
	            f_climbingNoMove = false;
	        }
	
	        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
	            f_x += 200 * delta;
	            f_lookingRight = true;
	            f_lookingLeft = false;
	            f_idleRight = false;
	            f_idleLeft = false;
	            f_climbingNoMove = false;
	        }
    	
	        if(f_finishedClimbing) {
	        	
	        	
	        	 	//f_lookingRight = false;
		            //f_lookingLeft = false;
	        		if(!f_lookingLeft && !f_idleLeft) {
		            f_idleRight = true;
	        		}
		            //f_idleLeft = false;
		            f_climbingNoMove = false;
		            f_climbingUp = false;
		            f_climbingDown = false;
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
	        }
	           
	        if(Gdx.input.isKeyJustPressed(Input.Keys.W)&&f_y+200<=1200) {
	        	f_y+=200;
	        	f_jumping=true;
	        }
	        if(Gdx.input.isKeyJustPressed(Input.Keys.S)&&f_y-200>=0) {
	        	f_y-=200;
	        } 
			if(f_x>=1200) {
		        	f_xVelocity = -4f;
		        	f_x+=f_xVelocity;
			}else if (f_x<=0) {
		        	f_xVelocity = 4f;
		        	f_x+=f_xVelocity;
		        }
		        
	        
	        
	        
	        if ((f_jumping|| f_isFalling) && f_currentPlatform != null) {
	        	//System.out.println("Player is on a platform");
	            // This ensures the player lands on top of the platform
	        	
	            float platformTopY = f_currentPlatform.getBounds().y + f_currentPlatform.getBounds().height;
	            if (f_y <= platformTopY) {
	               // f_y = platformTopY; // Make sure to land on top of the platform
	                f_yVelocity = 0;
	                f_jumping = false;
	                f_isFalling = false;
	                f_idleRight = true; // You might want to adjust this based on the player's direction before jumping
	            }
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
	    	 if(f_climbing && f_climbingUp) {
	    		 TextureRegion currentFrame = f_climbAnimation.getKeyFrame(f_stateTime, true);
			       batch.draw(currentFrame, f_x, f_y, f_texture.getWidth() * f_scaleXClimb, f_texture.getHeight() * f_scaleYClimb);
	    	 }
	    	 
	    	 if(f_climbing) {
	    		 
	    		 
	    		 if(f_climbingNoMove) {
	    		 batch.draw(f_textureClimb, f_x, f_y, f_texture.getWidth() * f_scaleXClimb, f_texture.getHeight() * f_scaleYClimb);
	    		 }
	    		 if(f_climbingUp) {
		    		 TextureRegion currentFrame = f_climbAnimation.getKeyFrame(f_stateTime, true);
				     batch.draw(currentFrame, f_x, f_y, f_texture.getWidth() * f_scaleXClimb, f_texture.getHeight() * f_scaleYClimb);
				     
		    	 }
	    		 
	    		 if(f_climbingDown) {
		    		 
				     batch.draw(f_climbDown, f_x, f_y, f_texture.getWidth() * f_scaleXClimb, f_texture.getHeight() * f_scaleYClimb);
		    	 }
	    		 
	    		 
	    		 
	    	 }
	    	 
	    
	    	
	    	
	    	 
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
	        
	        for(TextureRegion frame : f_climbAnimation.getKeyFrames()) {
	        	frame.getTexture().dispose();
	        }
	        
	        for(Texture texture : f_animationTextureClimbing) {
	        	if (texture != null) {
	        		texture.dispose();
	        	}
	        }
	    }
}
