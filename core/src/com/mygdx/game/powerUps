package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class powerUps extends Player {
	private int f_usesLeft;
	private float f_itemX;
	private float f_itemY;
	private float f_itemHeight;
	private float f_itemWidth;
	private Texture f_texture;
	private int f_powerNum;
	private float f_time;
	private int INITALXPOS;
	private int INITALYPOS;
	private Rectangle f_bounds;
	private Player f_player;
	
	public powerUps(float xPosition, float yPosition, float height, float width, int powerUpNum) {
		this.f_itemX=xPosition;
		this.f_itemY=yPosition;
		this.f_itemHeight=height;
		this.f_itemWidth=width;
		this.f_powerNum=powerUpNum;
		this.INITALXPOS=(int)xPosition;
		this.INITALYPOS=(int)yPosition;
		this.f_bounds = new Rectangle (f_itemX+10,f_itemY,f_itemWidth-10,f_itemHeight);
		texturize();
	}
	public void updateBounds() {
		this.f_bounds = new Rectangle (f_itemX+10,f_itemY,f_itemWidth-10,f_itemHeight);
	}
	public Rectangle getBounds() {
		return f_bounds;
	}
	public void texturize() {
			
			if(this.f_powerNum==1) {
				this.f_texture= new Texture("1.png");
			}else if (this.f_powerNum==2) {
				this.f_texture = new Texture("2.png");
			}else if (this.f_powerNum==3) {
				this.f_texture = new Texture("badlogic.jpg");
			}
	}
	public void collide() {
		
			if(this.f_powerNum==1) {
				numReturn();
				
			}else if (this.f_powerNum==2) {
				numReturn();
			}else if (this.f_powerNum==3) {
				System.out.println("Heheheha\nHeheheha\nHeheheha");
			
			}
		
		
	}
	public void render(SpriteBatch batch) {
	
        batch.draw(f_texture, f_itemX , f_itemY ,f_itemHeight,f_itemWidth);
    }
	public float getXPosition() {
		return f_itemX;
	}
	public float getYPosition() {
		return f_itemY;
	}
	int numReturn() {
		 return this.f_powerNum;
		}
		
		public void changeYPos(int change) {
			this.f_itemY+=change;
		}
		public void changeXPos(int change) {
			this.f_itemX+=change;
		}

		public void dispose() {
			f_texture.dispose();
		}
}
