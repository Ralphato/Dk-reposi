package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Barrel2 {
    private Texture texture; // The texture for the barrel
    private Vector2 position; // The position of the barrel
    private Vector2 velocity; // The velocity of the barrel
    private Circle bounds; // The circular bounds for collision detection
    private float radius; // The radius of the circle
    private int chance;
    private float f_speed = 200;
    private float gravity = -900f; // Gravity effect on the barrel
   
    private boolean isWithinLadder = false;
    private boolean isOnGround = false; // Is the barrel on the ground
    private float leftBound = 0; // Left boundary for the barrel's movement
    private float rightBound = 1250; // Right boundary for the barrel's movement
    private boolean moveRight = true;
    private boolean moveLeft = false;
    private boolean withinBounds = false;
    float deltaTime = Gdx.graphics.getDeltaTime();
    private boolean hasToggledDirection = false;
    // Constructor
    public Barrel2(Texture texture, float x, float y, float radius, int chance) {
        this.texture = texture;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0); // Initially, the barrel is not moving
        this.radius = radius;
        this.bounds = new Circle(x + radius, y + radius, radius);
        this.chance = chance;
      
    }
    
    
    
    // Method to update the barrel's position and bounds
    public void update(float deltaTime) {
        // Apply gravity if the barrel is not on the ground
        
        //checkOutOfBounds();
    
        //changeDirection();
        
    	//System.out.println(velocity.y);
    	//System.out.println("This is x postion: "+ position.x);
    	//System.out.println("This is y postion: "+ position.y);
    	 if (!isOnGround) {
    		 if(isWithinLadder) {
    			 System.out.println("Applying ladder velo");
             velocity.y = 400;
    		 }
    		 else {
    			 System.out.println("LOOLS");
    			 velocity.y += gravity * deltaTime;
    		 }
         } else {
             // Reset the vertical velocity if on ground
             velocity.y = 0;
         }
         
    	 //checkOutOfBounds();
        
        
        //System.out.println(velocity.x);
        //System.out.println(position.x);
        
        // Update the barrel's position based on its velocity
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        
        // Update the bounds position to match the barrel's new position
        bounds.setPosition(position.x + radius, position.y + radius);
    }
    
    // Method to draw the barrel
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x , position.y , radius * 2, radius * 2);
    }
    
 
    public void applyGravity() {
        System.out.println("Applying gravity");
        velocity.y += gravity * deltaTime; // deltaTime is the time since the last frame
    }
    
    // Method to move the barrel in the x direction
    public void moveX(float amount) {
       
        velocity.x = amount;
        
    }
    
    public void toggleDirection() {
    	if (!hasToggledDirection) {
            this.f_speed = -this.f_speed; // Reverse the movement direction
            this.hasToggledDirection = true; // Prevent further toggles until reset
        }
    }
    
    public void resetDirectionToggle() {
        this.hasToggledDirection = false;
    }
    
  
    
    // Method to check and handle collisions with the ground or platforms
    public void checkCollisionWithGround(boolean lool) {
        isOnGround = lool;
    }
    
    // Method to get the bounds of the barrel
    public Circle getBounds() {
        return bounds;
    }
    
    public void checkOutOfBounds() {
        // Check and correct if the barrel is beyond the left boundary
        if (position.x < leftBound) {
            setPositionX(leftBound); // Adjust the position to the left boundary
        }
        
        // Check and correct if the barrel is beyond the right boundary
        // Considering the entire width of the barrel for the right boundary check
        if (position.x + radius * 2 > rightBound) {
            setPositionX(rightBound - radius * 2); // Adjust the position to just inside the right boundary
        }
    }
    
    
    public void changeDirection() {
   	 if (position.x  >= rightBound - radius) { // Assuming a screen width of 1200 for example
        	moveLeft = true;
        	moveRight = false;
           //System.out.println(" moving to left triggered");
        } 
        if(position.x <= leftBound) {
        	moveRight = true;
        	moveLeft = false;
            //System.out.println(" moving to right");
        }
        
        if (moveRight) {
        	//System.out.println("Moving right");
        	moveX(f_speed);
        	//System.out.println("Moving:" + amount);
        }
        if(moveLeft) {
        	//System.out.println("Moving right");
        	moveX(-f_speed);
        	//System.out.println("Moving:" + -amount);
        	
        }
   }
    
    
    public float getSpeed() {
        return f_speed;
    }
    
    public int getChance() {
    	return chance;
    }
    
    public void ladderGravity(boolean laddoura) {
    	 isWithinLadder = laddoura;
    }
    
    public Vector2 getPosition() {
        return new Vector2(position.x, position.y);
    }
    
    public void setPositionX(float x) {
        position.x = x;
        // Since the position has changed, update the bounds to match the new position
        bounds.setPosition(position.x + radius, position.y);
    }

    // Method to set the barrel's Y position
    public void setPositionY(float y) {
        position.y = y;
        // Since the position has changed, update the bounds to match the new position
        bounds.setPosition(position.x, position.y + radius);
    }
   
    public void setVelocityY(float y) {
        velocity.y = y;
    }
    public void setVelocityX(float X) {
        velocity.x = X;
    }
    public float getVelocityY() {
        return velocity.y;
    }

    
    // Dispose resources
    public void dispose() {
        texture.dispose();
    }
}
