package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Platform {
    private float f_x, f_y;
    private float f_width, f_height; // Size
    private Texture f_texture;
    private Rectangle f_bounds;
    public Platform(float x, float y, float width, float height, Texture texture) {
        this.f_x = x;
        this.f_y = y;
        this.f_width = width;
        this.f_height = height;
        this.f_texture = texture;
        this.f_bounds = new Rectangle(f_x + 10, f_y, f_width - 10,f_height);
        
    }
   
    public void render(SpriteBatch batch) {
    	batch.draw(f_texture,f_x, f_y, f_width, f_height);
    }
    
    public Rectangle getBounds() {
    	return f_bounds;
    }
    
    public Rectangle getUpperBounds() {
        
    	float platHeight = f_texture.getHeight() * 0.9f; // Height of the legs
        // Assuming there are x, y, width, and height fields in the Player class
        return new Rectangle(f_x + 10, f_y + platHeight+ 10, f_width - 10, f_height - 40);
    }
    
    public Rectangle getLowerBounds() {
    	float platHeight = f_texture.getHeight() * 0.1f;
    	return new Rectangle(f_x + 10, f_y, f_width - 10, platHeight);
    }
    
    public Rectangle getRightBounds() {
    	float platWidth = f_texture.getWidth() * 0.9f;
    	return new Rectangle(f_x + platWidth, f_y + 10, f_width - platWidth - 10, f_height - 20);
    }
    
    public Rectangle getLeftBounds() {
    	float platWidth = f_texture.getWidth() * 0.9f;
    	return new Rectangle(f_x + 10, f_y + 10, f_width - platWidth - 10, f_height - 20);
    }
   
    
    public float getPlatY() {
    	return f_y;
    }
}
