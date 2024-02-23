package com.mygdx.game;

import com.badlogic.gdx.Gdx; // Import Gdx for accessing input, graphics, etc.
import com.badlogic.gdx.Input; // Import Input for key definitions
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Player {
	 private Texture texture;
	    private float x, y;
	    private float scaleX, scaleY;
	    private float yVelocity;
	    private boolean jumping;
	    public Player() {
	        texture = new Texture("mario sprite.png"); // Ensure you have a player.png in your assets folder.
	        x = 100; // Initial X position
	        y = 100; // Initial Y position
	        scaleX = 0.02f;
	        scaleY= 0.02f;
	        yVelocity = 0;
	        jumping = false;
	    }
	    private void jump() {
	        yVelocity = 10; // Initial jump velocity
	        jumping = true;
	    }
	    public void update(float delta) {
	        // Player update logic goes here (handle input, update position, etc.)
	    	// Handle input
	        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
	            x -= 200 * delta; // Move left
	        }
	        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
	            x += 200 * delta; // Move right
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
	            if (y <= 0) {
	                y = 0;
	                yVelocity = 0;
	                jumping = false;
	            }
	        }
	    }

	    public void draw(SpriteBatch batch) {
	        batch.draw(texture, x, y, texture.getWidth() * scaleX, texture.getHeight() * scaleY);
	    }

	    public void dispose() {
	        texture.dispose();
	    }
}
