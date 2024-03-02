package com.mygdx.game;

import com.badlogic.gdx.Gdx; // Import Gdx for accessing input, graphics, etc.
import com.badlogic.gdx.Input; // Import Input for key definitions
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;


public class Player {
	 private Texture texture;
	    private float x, y;
	    private float scaleX, scaleY;
	    private float yVelocity, xVelocity;
	    private float height;
	    private float width;
	    private boolean jumping;
	    private Texture oldTexture;
	    private Texture newTexture;
	    private Rectangle boundingBox;
	    private boolean noKeysPressed = !Gdx.input.isKeyPressed(Input.Keys.ANY_KEY);
	    public Player(float x, float y, float width, float height) {
	        texture = new Texture("mario sprite.png"); // Ensure you have a player.png in your assets folder.
	        this.x = x; // Initial X position
	        this.y = y; // Initial Y position
	        scaleX = 0.02f;
	        scaleY= 0.02f;
	        yVelocity = 0;
	        jumping = false;
	        oldTexture = new Texture("mario sprite.png");
	        newTexture = new Texture("mario sprite(L).png");
	        this.width=width;
	        this.height=height;
	        boundingBox= new Rectangle(x, y, width, height);
	    }
	    public Rectangle getBoundingBox() {
	    	return boundingBox;
	    }
	    public void setJumping(boolean flag) {
	    	jumping=flag;
	    }
	    public boolean getJumping() {
	    	return jumping;
	    }
	    public void updateBoundingBox() {
	        // Update the position of the bounding box based on the player's position
	        boundingBox.setPosition(x, y);
	    }
	    
	    public float getX() {
	    	return x;
	    }
	    public void changeX(float num) {
	    	x+=num;
	    }
	    public float getY() {
        	return y;
        }
        public float getWidth() {
        	return scaleX;
        }
        public float getHeight() {
        	return scaleY;
        }
        public float getYVelocity() {
        	return yVelocity;
        }
        public void setYVelocity(float number) {
        	yVelocity = number;
        }
        public float getXVelocity() {
        	return xVelocity;
        }
        public void setXVelocity(float number) {
        	xVelocity += number;
        }
        public void setY(float number) {
        	y=number;
        }
	    private void jump() {
	        yVelocity = 12; // Initial jump velocity
	        jumping = true;
	    }
	    public void update(float delta) {
	        // Player update logic goes here (handle input, update position, etc.)
	    	// Handle input
	    	updateBoundingBox();
	        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
	            x -= 200 * delta; // Move left
	            texture = newTexture;
	        }
	        
	        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
	            x += 200 * delta; // Move right
	            texture = oldTexture;
	        }
	        
	        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE)||Gdx.input.isKeyPressed(Input.Keys.UP)) && !jumping) {
                jump();
	        }
	        
	        if (jumping) {
	            // Apply gravity
	            yVelocity -= 0.5f;
	            
	            // Update position
	            y += yVelocity;
	            
	            // Check if the sprite has reached the ground
	            if (y <= 50) {
	                y = 50;
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
