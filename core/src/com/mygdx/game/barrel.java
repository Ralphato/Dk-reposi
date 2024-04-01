package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Barrel {
    private Texture texture; // The texture for the barrel
    private Vector2 position; // The position of the barrel
    private Vector2 velocity; // The velocity of the barrel
    private Circle bounds; // The circular bounds for collision detection
    private float radius; // The radius of the circle
    private float f_speed;
    private float gravity = -80f; // Gravity effect on the barrel
    private boolean isOnGround = false; // Is the barrel on the ground
    private float leftBound = 0; // Left boundary for the barrel's movement
    private float rightBound = 1250; // Right boundary for the barrel's movement
    private boolean moveRight = true;
    private boolean moveLeft = false;
    // Constructor
    public Barrel(Texture texture, float x, float y, float radius, float speed) {
        this.texture = texture;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0); // Initially, the barrel is not moving
        this.radius = radius;
        this.bounds = new Circle(x + radius, y + radius, radius);
        f_speed = speed;
    }
    
    // Method to update the barrel's position and bounds
    public void update(float deltaTime) {
        // Apply gravity if the barrel is not on the ground
        if (!isOnGround) {
            velocity.y += gravity * deltaTime;
        } else {
            // Reset the vertical velocity if on ground
            velocity.y = 0;
        }
        
        changeDirection();
        //checkOutOfBounds();
        System.out.println(position.x);
        
        // Update the barrel's position based on its velocity
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        
        // Update the bounds position to match the barrel's new position
        bounds.setPosition(position.x + radius, position.y + radius);
    }
    
    // Method to draw the barrel
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x , position.y , radius * 2, radius * 2);
    }
    
    // Method to make the barrel jump
    public void jump(float jumpVelocity) {
        if (isOnGround) {
            velocity.y = jumpVelocity;
            isOnGround = false;
        }
    }
    
 
    
    // Method to move the barrel in the x direction
    public void moveX(float amount) {
       
        velocity.x = amount;
        
    }
    
    public void changeDirection() {
    	 if (position.x  >= rightBound - radius) { // Assuming a screen width of 1200 for example
         	moveLeft = true;
         	moveRight = false;
            System.out.println(" moving to left triggered");
         } 
         if(position.x <= leftBound + radius) {
         	moveRight = true;
         	moveLeft = false;
             System.out.println(" moving to right");
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
    
    // Method to check and handle collisions with the ground or platforms
    public void checkCollisionWithGround(boolean lool) {
        isOnGround = lool;
    }
    
    // Method to get the bounds of the barrel
    public Circle getBounds() {
        return bounds;
    }
    
    public void checkOutOfBounds() {
	    if (position.x < leftBound + radius) {
	        position.x = leftBound + radius; // Prevent the barrel from moving beyond the boundary
	        velocity.x = -velocity.x; // Reverse direction
	    } else if (position.x > rightBound - radius) {
	        position.x = rightBound - radius; // Prevent the barrel from moving beyond the boundary
	        velocity.x = -velocity.x; // Reverse direction
	    }
    }
    
    public Vector2 getPosition() {
        return new Vector2(position.x, position.y);
    }
    
    // Dispose resources
    public void dispose() {
        texture.dispose();
    }
}
