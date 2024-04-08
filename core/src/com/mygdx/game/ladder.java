package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ladder {
	private float f_x, f_y;
	private float f_width, f_height;
	private Texture f_texture;
	private Rectangle f_bounds;
	private float scaleWidth = 0.5f;
	private float scaleHeight = 2.0f;
	private float scaleFX = 1.125f;
	private float scaleFY = 2.9f;
	private float scaleX = 0.5f;
	
	public Ladder(float x,float y, float width,float height, Texture texture) {
		 	this.f_x = x;
	        this.f_y = y;
	        this.f_width = width;
	        this.f_height = height;
	        this.f_texture = texture;
	        this.f_bounds = new Rectangle(f_x + 50, f_y - 5, f_width * scaleWidth ,f_height * scaleHeight);
		
	}
	
	public void render(SpriteBatch batch) {
    	batch.draw(f_texture,f_x, f_y, f_width, f_height);
    }
    
    public Rectangle getBounds() {
    	return f_bounds;
    }
    

    //updated 3/18/2024
    //to rescale find a number that divides f_x to equal 50
    public Rectangle getMovementBoundsUp() {
    	//System.out.println("Ladder f_y is: " + f_y);
    	return new Rectangle(f_x + 50, f_y + 190, f_width * scaleWidth, f_height * 0.7f);
    }
    
    public Rectangle getUpperBounds() {
    	//System.out.println("Ladder f_y is: " + f_y);
    	return new Rectangle(f_x + 50, f_y + 400, f_width * scaleWidth, f_height * 0.1f);
    }
    
    public Rectangle getMovementBoundsDown() {
    	 return new Rectangle(f_x + 50, f_y - 5, f_width * scaleWidth, f_height * 0.65f);
    }

}
