package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private static final int FRAME_COUNT = 3;
	private static final int FRAME_COUNT_COIN = 6;
	private float[] frameDurations = {0.2f, 0.5f, 0.4f, 0.2f, 0.2f, 0.2f, 2.5f};
	private Texture[] f_shieldTexture;
	private Texture[] f_CoinTexture;
	private Animation<TextureRegion> f_ShieldAnimation;
	private Animation<TextureRegion> f_CoinAnimation;
	private int currentFrameIndex = 0;
	private int CoinFrameIndex = 0;
	private float f_stateTimeBubble;
	private float f_stateTimeCoin;
	
	public powerUps(float xPosition, float yPosition, float height, float width, int powerUpNum) {
		this.f_itemX=xPosition;
		this.f_itemY=yPosition;
		this.f_itemHeight=height;
		this.f_itemWidth=width;
		this.f_powerNum=powerUpNum;
		this.INITALXPOS=(int)xPosition;
		this.INITALYPOS=(int)yPosition;
		this.f_bounds = new Rectangle (f_itemX+10,f_itemY,f_itemWidth-10,f_itemHeight);
		
        f_shieldTexture = new Texture[FRAME_COUNT ];
        TextureRegion[] ShieldFrames = new TextureRegion[FRAME_COUNT];

        for (int i = 0; i < FRAME_COUNT; i++) {
        	
            String frameName = "Bubble" + (i + 1) + ".png";
            f_shieldTexture[i] = new Texture(frameName);
            ShieldFrames[i] = new TextureRegion(f_shieldTexture[i]);
        	
        }
        
        f_ShieldAnimation = new Animation<TextureRegion>(0.4f, ShieldFrames);
        
        f_CoinTexture = new Texture[FRAME_COUNT_COIN];
        TextureRegion[] CoinFrames = new TextureRegion[FRAME_COUNT_COIN];

        for (int i = 0; i < FRAME_COUNT_COIN; i++) {
        	
            String frameName = "Coin" + (i + 1) + ".png";
            f_CoinTexture[i] = new Texture(frameName);
            CoinFrames[i] = new TextureRegion(f_CoinTexture[i]);
        	
        }
        
        f_CoinAnimation = new Animation<TextureRegion>(0.8f, CoinFrames);
        f_stateTimeBubble = 0f;
        f_stateTimeCoin = 0f;
		
		
		}	
		public void update(float delta) {
            f_stateTimeBubble += delta; //change here to make it faster/slower
            f_stateTimeCoin += delta;
            
            // Check if we should change to the next frame
            if(f_stateTimeBubble >= frameDurations[currentFrameIndex]) {
                f_stateTimeBubble -= frameDurations[currentFrameIndex];
                currentFrameIndex++;
                
                // Loop back to the first frame or stop the animation as needed
                if (currentFrameIndex >= FRAME_COUNT) {
                    currentFrameIndex = 0; // or set isActive to false if the animation should stop
                }
            }
            
            if(f_stateTimeCoin >= frameDurations[CoinFrameIndex]) {
                f_stateTimeCoin -= frameDurations[CoinFrameIndex];
                CoinFrameIndex++;
                
                // Loop back to the first frame or stop the animation as needed
                if (CoinFrameIndex >= FRAME_COUNT_COIN) {
                    CoinFrameIndex = 0; // or set isActive to false if the animation should stop
                }
            }
   	 }
	public void updateBounds() {
		this.f_bounds = new Rectangle (f_itemX+10,f_itemY,f_itemWidth-10,f_itemHeight);
	}
	public Rectangle getBounds() {
		return f_bounds;
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
	public void draw(SpriteBatch batch) {
		Texture currentTexture = f_shieldTexture[currentFrameIndex];
		Texture currentTextureCoin = f_CoinTexture[CoinFrameIndex];
		if (this.f_powerNum==1) {
        batch.draw(currentTextureCoin, f_itemX , f_itemY ,f_itemHeight,f_itemWidth);}
		else if (this.f_powerNum==2) {
		batch.draw(currentTexture, f_itemX , f_itemY ,f_itemHeight,f_itemWidth);
			}
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
