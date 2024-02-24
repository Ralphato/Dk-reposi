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
	    private boolean lookingLeft;
	 
	    public Player() {
	        texture = new Texture("mario sprite.png"); // Ensure you have a player.png in your assets folder.
	        x = 100; // Initial X position
	        y = 100; // Initial Y position
	        scaleX = 0.1f;
	        scaleY= 0.1f;
	        yVelocity = 0;
	        jumping = false;
	        lookingLeft = false;
	    }
	    
	    private void jump() {
	    	yVelocity = 10;
	    	jumping = true;
	    }

	    public void update(float delta) {
	        // Player update logic goes here (handle input, update position, etc.)
	    	// Handle input
	        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
	            x -= 200 * delta; // Move left
	            lookingLeft = true;
	        }
	        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
	            x += 200 * delta; // Move right
	            lookingLeft = false;
	        }
	        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)&& !jumping) {
	        	jump();
	        }
	        if (jumping) {
	        	yVelocity -= 0.5f;
	        	y += yVelocity;
	        	
	        	if(y<=100) {
	        		y = 100;
	        		yVelocity = 0;
	        		jumping = false;
	        	}
	        }
	        
	    }

	    public void draw(SpriteBatch batch) {
	    	if (lookingLeft) {
		        batch.draw(texture, x + texture.getWidth() * scaleX, y, -texture.getWidth() * scaleX, texture.getHeight() * scaleY);

	    	}
	    	else {
		        batch.draw(texture, x, y, texture.getWidth() * scaleX, texture.getHeight() * scaleY);

	    	}
	    }

	    public void dispose() {
	        texture.dispose();
	    }
}
