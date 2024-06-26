package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class donkeyKong {
	
	private static final int FRAME_COUNT = 6;
	private Texture f_texture;
	private Texture f_textureFalling;
	private Texture f_textureThrowing;
	private Texture f_textureLaughing;
	private Texture f_hitTexture1, f_hitTexture2, f_hitTexture3, f_hitTexture4;   
	private float f_scaleX = 5;
	private float f_scaleY = 5;
	private int f_x, f_y;
	private boolean f_throwing;	
	private Texture[] f_animationTexture, f_animationTexture2, f_animationTexture3;
	private Animation<TextureRegion> f_gorillaAnimation, f_stanceAnimation, f_stomachAnimation;
	private boolean f_runAnimation = true;
	private boolean f_startLaugh = false;
	private int health = 3;
	private boolean gotDamaged;
	private boolean fightingStance = false; //true for testing
	private boolean startFalling = true;
	private boolean shakeScreen = false;
	private boolean hitStomach = false;
	private boolean start_game = false;
	private gameScreen screen;
	
	float targetWidth = 136;
	float targetHeight = 136;

	float scaleX1 = targetWidth / 71f; // For hit1's original width
	float scaleY1 = targetHeight / 107f; // For hit1's original height

	float scaleX2 = targetWidth / 71f; // For hit2's original width
	float scaleY2 = targetHeight / 136f; // For hit2's original height (already the target)

	float scaleX3 = targetWidth / 71f; // For hit3's original width
	float scaleY3 = targetHeight / 119f; // For hit3's original height

	float scaleX4 = targetWidth / 89f; // For hit4's original width
	float scaleY4 = targetHeight / 131f; // For hit4's original height


	

	private float initialY;  // Starting Y-coordinate (off-screen)
	private int targetY, targetY2;   // Ending Y-coordinate (on-screen)
	private boolean isFalling = true;  // Condition to check if DK should still fall
	
	private boolean isJumping = false;
	private float jumpSpeed = 200; // Initial speed of the jump
	private float currentJumpSpeed;
	private float gravity = 400; // Gravity pulling down after the jump
	
	private float f_stateTime;
	
	public donkeyKong(gameScreen screen) {
	    this.screen = screen;
		f_texture = new Texture("DonkeyIdle.png");
		f_textureThrowing = new Texture("throwingBarrel.png");
		f_textureLaughing = new Texture("laugh2.png");
		
		//getting hit textures
		f_hitTexture1 = new Texture("hitTextures/FirstHitDK.png");
		f_hitTexture2 = new Texture("hitTextures/SecondHitDK.png");
		f_hitTexture3 = new Texture("hitTextures/FinalHitDK.png");
		
		//f_hitTexture4 = new Texture("hitTextures/hit4.png");
		
		f_textureFalling = new Texture("AngryDonkeyKongFinal.png");
		
		f_x = 100;
		f_y = 1080;
		 initialY = 1400;  // Example starting Y, off-screen above
		 targetY = 1080;   // Example target Y, on the platform
		 targetY2 = 1030;   // Example target Y, on the platform
		
		f_animationTexture = new Texture[FRAME_COUNT ];
        TextureRegion[] runFrames = new TextureRegion[FRAME_COUNT];
        int j = 3;
        for (int i = 0; i < FRAME_COUNT; i++) {
        	if( i < 3) {
            String frameName = "test" + (i + 1) + ".png";
            f_animationTexture[i] = new Texture(frameName);
            runFrames[i] = new TextureRegion(f_animationTexture[i]);
        	}
        	else {
        		//when i = 4
        		String frameName = "test" + (j) + ".png";
                f_animationTexture[i] = new Texture(frameName);
                runFrames[i] = new TextureRegion(f_animationTexture[i]);
                j--;
        	}
        }
        
        f_gorillaAnimation = new Animation<TextureRegion>(0.1f, runFrames);
        
        
        //make another animation for stance1 2 3 
        
        f_animationTexture2 = new Texture[3];
        TextureRegion[] runFrames2 = new TextureRegion[3];
       
        for (int i = 0; i < 3; i++) {
        	
            String frameName = "stance" + (i + 2) + ".png";
            f_animationTexture2[i] = new Texture(frameName);
            runFrames2[i] = new TextureRegion(f_animationTexture2[i]);
        }
        f_stanceAnimation = new Animation<TextureRegion>(0.1f, runFrames2);
        
        //animation for hitting stomach
        f_animationTexture3 = new Texture[2];
        TextureRegion[] runFrames3 = new TextureRegion[2];
       
        for (int i = 0; i < 2; i++) {
        	
            String frameName = "DonkeyChestBeat" + (i + 1) + ".png";
            f_animationTexture3[i] = new Texture(frameName);
            runFrames3[i] = new TextureRegion(f_animationTexture3[i]);
        }
        f_stomachAnimation = new Animation<TextureRegion>(0.1f, runFrames3);
        
       
        
	}
	
	public void punchStomach() {
	    // This timer task will set hitStomach to true and then schedule another task to set it to false after 3 seconds
		if(start_game) {
	    Timer.schedule(new Task(){
	        @Override
	        public void run() {
	            hitStomach = true; // Set the hitStomach flag to true

	            // Schedule another task to set hitStomach to false after 3 seconds
	            Timer.schedule(new Task() {
	                @Override
	                public void run() {
	                    hitStomach = false; // Set the hitStomach flag back to false after 3 seconds
	                }
	            }, 2.5f); // Delay in seconds before the flag is set back to false
	        }
	    }, 1.5f, 16, 20); // Start delay (10 seconds), interval between triggers (20 seconds), number of repetitions 
		}
	}
	
	
	public void toggleValue(boolean animation) {
		if(animation = true) {
			f_runAnimation = false;
		}
		else if(animation = false) {
			f_runAnimation = true;
		}
	}
	
	
	public void endGame(boolean startLaugh) {
		f_startLaugh = true;
	}
	
	public void draw(SpriteBatch batch) {
		 if(!f_startLaugh) {
		if(isFalling) {
			batch.draw(f_textureFalling, f_x - 100, f_y, f_textureThrowing.getWidth() * f_scaleX * 2, f_textureThrowing.getHeight() * f_scaleY * 2);
			System.out.println("DRAWING");
		}
		else {
			
		
		if(!gotDamaged) {
			
			
		 if(!fightingStance) {
			if (!f_startLaugh) {
				 if (f_throwing) {
					 batch.draw(f_textureThrowing, f_x, f_y, f_textureThrowing.getWidth() * f_scaleX, f_textureThrowing.getHeight() * f_scaleY);
					 hitStomach = false; //so it doesnt run after it mid animation
				 }
				 else if(hitStomach) { //placed here so that it only runs when f_throwing is not running
						TextureRegion currentFrame3 = f_stomachAnimation.getKeyFrame(f_stateTime / 3, true);
						batch.draw(currentFrame3, f_x, 1060, f_texture.getWidth()/6, f_texture.getHeight()/6);
					}
				 else {
				 batch.draw(f_texture, f_x, f_y - 15, f_textureThrowing.getWidth() * f_scaleX * 1.15f, f_textureThrowing.getHeight() * f_scaleY * 1.15f);
				 }
			}			 
			
		 }
		 
		 else if(fightingStance) {
			 //make animation
			 TextureRegion currentFrame2 = f_stanceAnimation.getKeyFrame(f_stateTime / 5, true);
			 batch.draw(currentFrame2, f_x, 1090, f_texture.getWidth()/6, f_texture.getHeight()/6);
		 }
		 
		
			
		}
		
		else if(gotDamaged) {
			switch(health) {
			 case 1:
				 	batch.draw(f_hitTexture3, f_x, f_y + 15, 89 * scaleX4 * 2, 131 * scaleY4 * 2);
				 	
			        break;
			        
			    case 2:
			    	batch.draw(f_hitTexture2, f_x, f_y + 15, 71 * scaleX3 * 2, 119 * scaleY3 * 2);
			        break;
			        
			    case 3:
			        batch.draw(f_hitTexture1, f_x, f_y + 15, 71 * scaleX2 * 2, 136 * scaleY2 * 2);
			        break;
			        
			    
			}
			
			
			
		}
		}
		 }
		 
		 else if(f_startLaugh) {
				if(f_runAnimation) {
					batch.draw(f_textureLaughing, f_x - 50, 750, f_texture.getWidth() / 4.15f, f_texture.getHeight() / 4.15f);
					Timer.schedule(new Task() {
			            @Override
			            public void run() {
			            
			            	f_runAnimation = false; //waits 1 second and then calls this
			            
			            	
			            }
			        }, 1); 	
				}
				
				else {
				TextureRegion currentFrame = f_gorillaAnimation.getKeyFrame(f_stateTime /1, true);
			    batch.draw(currentFrame, f_x - 50, 750, f_texture.getWidth() / 4.15f, f_texture.getHeight() / 4.15f);
				}
			}
	    

	}
	
	public void update(float delta) {
		
		 if (isJumping) {
		        // Update the y position based on the current jump speed
		        f_y += currentJumpSpeed * delta;
		        // Apply gravity to the jump speed for the next frame
		        currentJumpSpeed -= gravity * delta;

		        // Check if Donkey Kong has returned to the starting position or lower
		        if (f_y <= targetY2) {
		            f_y = targetY2; // Reset y to the starting position
		            isJumping = false; // Stop the jump
		            System.out.println("triggered");
		        }
		    }
		 
		 if (startFalling) {
			 System.out.println("FALLING");
			 f_y = (int) initialY;   // Set initial position off-screen
			 isFalling = true;
			 startFalling = false;
			 
	        }
		 
		 if(gotDamaged) { //instead if beating stomach
			 //shakeScreen = true;
			 
		 }
		 if (isFalling) {
		        float fallSpeed = 500 * delta;  // Control the speed of the fall
		        f_y -= fallSpeed;  // Decrease Y position to simulate falling
		        if (f_y <= targetY) {
		            f_y = (int) targetY;  // Ensure DK does not fall below the target
		            isFalling = false;  // Stop falling
		            screen.shakeScreen(2000.0f, 1.0f);  // Trigger screen shake when landing
		        }
		    }
		 if(shakeScreen) {
			 screen.shakeScreen(200.0f, 0.5f);  // Trigger screen shake when damaged
			 shakeScreen = false;
			 
		 }
		 punchStomach();
		 start_game = false;

		 //start_game = false; //only make 1 task scheduler from hitting stomach
		 f_stateTime += delta;

	}
	
	public void jump() {
	    if (!isJumping) { // Start the jump only if not already jumping
	        isJumping = true;
	        currentJumpSpeed = jumpSpeed; // Set the initial jump speed
	    }
	}
	
	public boolean throwing(boolean f_throw) {
		return f_throwing = f_throw;
	}
	
	public void setPositionX(int newX) {
		f_x = newX;
	}
	
	public void setPositionY(int newY) {
		f_y = newY;
	}
	
	public void start(boolean startGame) {
		start_game = startGame;
	}
	 public Rectangle getBounds() {
	    	
	        return new Rectangle(f_x + 60, f_y - 100, 275 , f_texture.getHeight() );
	    }
	 
	 public void startFighting(boolean fight) {
		 fightingStance = fight;
	 }
	 
	  public int getHealth() {
		  return health;
	  }
	  
	  public void decreaseHealth() {
		  health --;
		  jump();
		  gotDamaged = true;
	  }
	
	//if playerlose start laughing
	//after falling hit his stomach
	
	public void dispose() {
		f_texture.dispose();
		for(TextureRegion frame : f_gorillaAnimation.getKeyFrames()) {
			frame.getTexture().dispose();
		}
		
		for(Texture texture : f_animationTexture) {
			if(texture!=null) {
				texture.dispose();
			}
		}
	}
}
