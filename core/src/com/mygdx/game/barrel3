package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Barrel3 {
    private Texture texture; // The texture for the barrel
    private Vector2 position; // The position of the barrel
    private Vector2 velocity; // The velocity of the barrel
    private Circle bounds; // The circular bounds for collision detection
    private float radius; // The radius of the circle
    private float rotation; // The rotation angle in degrees
  

    // Constructor
    public Barrel3(Texture texture, float x, float y, float radius) {
        this.texture = texture;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0); // Initially, the barrel is not moving
        this.radius = radius;
        this.bounds = new Circle(x + radius, y + radius, radius);
        this.rotation = 0; // Initial rotation angle
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, radius, radius, radius * 2, radius * 2, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public void update(float deltaTime) {
        // Update rotation based on time
        rotation += 90 * deltaTime; // Rotate 90 degrees per second

        // Update position and velocity for jumping
        position.add(velocity.x * deltaTime, velocity.y * deltaTime); // Update position based on velocity
       
        

        moveX(50);
       
    }
    
    public void moveX(float amount) {
        
        velocity.x = amount;
        
    }

    public void jump(float initialVelocityY) {
        velocity.y = initialVelocityY; // Set the initial jump velocity
    }

    public void dispose() {
        texture.dispose();
    }

    public void setPositionX(float x) {
        position.x = x;
    }

    public void setPositionY(float y) {
        position.y = y;
    }
}
