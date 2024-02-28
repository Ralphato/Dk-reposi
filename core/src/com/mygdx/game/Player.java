package com.mygdx.game;

import com.badlogic.gdx.Gdx; // Import Gdx for accessing input, graphics, etc.
import com.badlogic.gdx.Input; // Import Input for key definitions
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Player {
	 private Texture texture;
	    private float x, y;
	    private float scaleX, scaleY;
	    private float yVelocity, xVelocity;
	    private boolean jumping;
	    private Texture oldTexture;
	    private Texture newTexture;
	    public Player() {
	        texture = new Texture("mario sprite.png"); // Ensure you have a player.png in your assets folder.
	        x = 100; // Initial X position
	        y = 100; // Initial Y position
	        scaleX = 0.02f;
	        scaleY= 0.02f;
	        yVelocity = 0;
	        jumping = false;
	        oldTexture = new Texture("mario sprite.png");
	        newTexture = new Texture("mario sprite(L).png");
	    }
	    
	    
            
         
	    private void jump() {
	        yVelocity = 12; // Initial jump velocity
	        jumping = true;
	    }
	    public void update(float delta) {
	        // Player update logic goes here (handle input, update position, etc.)
	    	// Handle input
	        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
	            x -= 200 * delta; // Move left
	            texture = newTexture;
	        }
	        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
	            x += 200 * delta; // Move right
	            texture = oldTexture;
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !jumping) {
                jump();
	        }
	        if (jumping) {
	            // Apply gravity
	            yVelocity -= 0.5f;
	            
	            // Update position
	            y += yVelocity;
	            
	            // Check if the sprite has reached the ground
	            if (y <= 100) {
	                y = 100;
	                yVelocity = 0;
	                jumping = false;
	            }
	        }
	        if(x>=600) {
	        	xVelocity = -4f;
	        	x+=xVelocity;
	        }else if (x<=0) {
	        	xVelocity = 4f;
	        	x+=xVelocity;
	        }
	    }

	    public void draw(SpriteBatch batch) {
	        batch.draw(texture, x, y, texture.getWidth() * scaleX, texture.getHeight() * scaleY);
	    }

	    public void dispose() {
	        texture.dispose();
	    }
}
