package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
/**
 * This class represents the main game application.
 * It handles the initialization of game components, game logic updates, and rendering.
 */
public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch f_batch;
    private Player f_player;
    private AssetManager f_assetManager;
    private Music f_BGM;
    private ArrayList<Platform> f_platforms;
    private ArrayList<Ladder> f_ladders;
    private Platform f_currentPlatform; 
    private float PlatformY;
    private Rectangle f_playerBounds, f_legBounds, f_bodyBounds;
    private boolean f_onPlatform, f_contactPlatform;
    private boolean f_stopMoving;
    private boolean f_isClimbing;
    private boolean f_canMove;
    private boolean f_jumping;
    private boolean f_finishedClimbing;
    private boolean f_climbingUp;
    private OrthographicCamera camera;
    Ladder ladder1;
    Ladder ladder2;
    private gameScreen f_gameScreen;
    private int textNum;
    
   

    /**
     * Initializes the game components.
     */
    @Override
    public void create() {
    	
    	
    	
        
        // The rest of your initialization
        f_batch = new SpriteBatch();
        f_gameScreen = new gameScreen(this);
        f_player = new Player();
        f_assetManager = new AssetManager();
        f_assetManager.load("DKMusic1.mp3", Music.class);
        f_assetManager.finishLoading();
        f_BGM = f_assetManager.get("DKMusic1.mp3", Music.class);
        f_BGM.setVolume(0.4f);
        f_BGM.play();
        f_BGM.setLooping(true);
        f_platforms = new ArrayList<>();
        f_ladders = new ArrayList<>();
        f_playerBounds = f_player.getBodyBounds();
        f_legBounds = f_player.getLegsBounds();
        
        initializePlatforms();
    }

    /**
     * Initializes platforms with textures.
     */
    private void initializePlatforms() {
    	Texture platformTexture1 = new Texture("PlainDessertPlat.png");
        Texture platformTexture2 = new Texture("RustPlat1.png");
        Texture platformTexture3 = new Texture("RustPlat2.png");
        
        Texture ladderTexture = new Texture("ladder.png");

      
        

        //f_platforms.add(new Platform(200, 100, 100, 50, platformTexture1));
        
     // Define ladder1 and ladder2 as separate variables
        ladder1 = new Ladder(400, 100, 200, 150, ladderTexture);
        

        // Then add them to your collection of ladders
        f_ladders.add(ladder1);
       

         // First row of platforms
        for(int i = 0; i<=1200;i+=150) {
           	textNum=(int)(Math.random()*3)+1;
           	if(textNum==1) {
           f_platforms.add(new Platform(i, 0, 150, 50, platformTexture1));
           	}
           	if(textNum==2) {
           f_platforms.add(new Platform(i, 0, 150, 50, platformTexture2));
           	}
           	if(textNum==3) {
           f_platforms.add(new Platform(i, 0, 150, 50, platformTexture3));
           	}
           }
        for(int i = 0; i<=1200;i+=150) {
           	textNum=(int)(Math.random()*3)+1;
           	if(textNum==1) {
           f_platforms.add(new Platform(i, 200, 150, 50, platformTexture1));
           	}
           	if(textNum==2) {
           f_platforms.add(new Platform(i, 200, 150, 50, platformTexture2));
           	}
           	if(textNum==3) {
           f_platforms.add(new Platform(i, 200, 150, 50, platformTexture3));
           	}
           }
        for(int i = 0; i<=1200;i+=150) {
           	textNum=(int)(Math.random()*3)+1;
           	if(textNum==1) {
           f_platforms.add(new Platform(i, 400, 150, 50, platformTexture1));
           	}
           	if(textNum==2) {
           f_platforms.add(new Platform(i, 400, 150, 50, platformTexture2));
           	}
           	if(textNum==3) {
           f_platforms.add(new Platform(i, 400, 150, 50, platformTexture3));
           	}
           }
        for(int i = 0; i<=1200;i+=150) {
        	textNum=(int)(Math.random()*3)+1;
           	if(textNum==1) {
           f_platforms.add(new Platform(i, 600, 150, 50, platformTexture1));
           	}
           	if(textNum==2) {
           f_platforms.add(new Platform(i, 600, 150, 50, platformTexture2));
           	}
           	if(textNum==3) {
           f_platforms.add(new Platform(i, 600, 150, 50, platformTexture3));
           	}
           }
        for(int i = 0; i<=1200;i+=150) {
        	textNum=(int)(Math.random()*3)+1;
           	if(textNum==1) {
           f_platforms.add(new Platform(i, 800, 150, 50, platformTexture1));
           	}
           	if(textNum==2) {
           f_platforms.add(new Platform(i, 800, 150, 50, platformTexture2));
           	}
           	if(textNum==3) {
           f_platforms.add(new Platform(i, 800, 150, 50, platformTexture3));
           	}
           }
    }

    /**
     * Updates the game logic and renders the game components.
     */
    @Override
    public void render() {
    	 
    	
    	
    	f_gameScreen.render(Gdx.graphics.getDeltaTime());
        clearScreen();
        f_player.update(Gdx.graphics.getDeltaTime());
        //render bounds
        f_playerBounds = f_player.getBodyBounds();
        f_legBounds = f_player.getLegsBounds();
        f_bodyBounds = f_player.getBodyBounds();
        f_jumping = f_player.isJumping();
        
        f_batch.begin();
        
        renderPlatforms();
        renderLadders();
        f_player.draw(f_batch);
        f_batch.end();
        
        
        checkPlayerLadderCollisions();
        if(f_isClimbing == false) {
        checkPlayerPlatformCollisions();
        }
        
        
        //debugging purposes
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for (Platform platform : f_platforms) {
            Rectangle Upperbounds = platform.getUpperBounds();
            shapeRenderer.rect(Upperbounds.x, Upperbounds.y, Upperbounds.width, Upperbounds.height);
            Rectangle LowerBounds = platform.getLowerBounds();
            shapeRenderer.rect(LowerBounds.x, LowerBounds.y, LowerBounds.width, LowerBounds.height);
            Rectangle rightBounds = platform.getRightBounds();
            shapeRenderer.rect(rightBounds.x, rightBounds.y, rightBounds.width, rightBounds.height);
            Rectangle leftBounds = platform.getLeftBounds();
            shapeRenderer.rect(leftBounds.x, leftBounds.y, leftBounds.width, leftBounds.height);
            
        
        }
        
        //ladder bounds
        shapeRenderer.setColor(Color.PURPLE);
        for(Ladder ladder: f_ladders) {
        	 Rectangle ladderBounds = ladder.getBounds();
        	 shapeRenderer.rect(ladderBounds.x, ladderBounds.y, ladderBounds.width, ladderBounds.height);
        	 shapeRenderer.setColor(Color.ORANGE);
        	 Rectangle ladderUpperBounds = ladder.getMovementBoundsUp();
        	 shapeRenderer.rect(ladderUpperBounds.x, ladderUpperBounds.y, ladderUpperBounds.width, ladderUpperBounds.height);
        	 Rectangle ladderLowerBounds = ladder.getMovementBoundsDown();
        	 shapeRenderer.rect(ladderLowerBounds.x, ladderLowerBounds.y, ladderLowerBounds.width, ladderLowerBounds.height);
        }
     
        // Draw player bounds
        Rectangle playerBounds = f_player.getBodyBounds();
        shapeRenderer.rect(playerBounds.x, playerBounds.y, playerBounds.width, playerBounds.height);
        shapeRenderer.setColor(Color.GREEN);
        Rectangle playerBoundsLegs = f_player.getLegsBounds();
        shapeRenderer.rect(playerBoundsLegs.x, playerBoundsLegs.y, playerBoundsLegs.width, playerBoundsLegs.height);
        
        shapeRenderer.end();

    }

    public SpriteBatch getSpriteBatch() {
        return f_batch;
    }
    
    /**
     * Clears the screen with a specific color.
     */
    private void clearScreen() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Renders all platforms.
     */
    private void renderPlatforms() {
        for (Platform platform : f_platforms) {
            platform.render(f_batch);
        }
    }
    
    private void renderLadders() {
    	for(Ladder ladder : f_ladders) {
    		ladder.render(f_batch);
    	}
    }

    /**
     * Checks for collisions between the player and platforms.
     */
    private void checkPlayerPlatformCollisions() {
    	
        boolean foundCollision = false;
        // Start with the assumption that there is no collision
        for (Platform platform : f_platforms) {
            if (f_legBounds.overlaps(platform.getUpperBounds())) {
            	//System.out.println("Collision detected");
                f_currentPlatform = platform;
                f_player.setCurrentPlatform(f_currentPlatform);
                PlatformY = f_currentPlatform.getPlatY();
                f_player.checkCollision(foundCollision);
                resolvePlayerPlatformCollision(platform);
                foundCollision = true;
                break; // This stops checking more platforms after finding the first collision
            }
        }
        
        
        
        // If after checking all platforms, no collision is found, then reset.
        if (!foundCollision) {
        	
            //f_currentPlatform = null;
            f_player.setCurrentPlatform(null);
            f_player.checkCollision(foundCollision);
            float y = f_player.getPositionY();
            if(y >= PlatformY) {
            
        	y += -5;
        	f_player.setPosition(f_player.getPositionX(), y);
        	}
        }
        
        if(foundCollision) {
        	float y = f_player.getPositionY();
            if(y >= PlatformY) {
        	y += -5;
        	f_player.setPosition(f_player.getPositionX(), y);
        }
        }
        
        //Stop player when colliding with platform sides with its body
        for(Platform platform : f_platforms) {
        	
        	if(f_bodyBounds.overlaps(platform.getLeftBounds())) { // change this to sides left and right
        			//System.out.println("Body is colliding with platform side");
        			float newX = platform.getBounds().x - 1; //-1 to not let the collision fixed between both
        			f_player.setPosition(newX - 70, f_player.getPositionY()); //instead of newX - 70 put newX - f_player.width
        	
          }
        	if(f_bodyBounds.overlaps(platform.getRightBounds())) { // change this to sides left and right
    			//System.out.println("Body is colliding with platform side");
    			float newX = platform.getBounds().x - 1; //-1 to not let the collision fixed between both
    			f_player.setPosition(newX + 80, f_player.getPositionY()); //instead of newX + 80 put newX + f_player.width
    	
        	}
          
        	if(f_bodyBounds.overlaps(platform.getLowerBounds())) {
        		float newY = platform.getBounds().y -1;
        		f_player.setPosition(f_player.getPositionX(), newY - 84); //80
        	}
        }
    	
        
    }

    /**
     * Resolves collisions between the player and a platform.
     * 
     * @param platform The platform that collides with the player.
     */
    private void resolvePlayerPlatformCollision(Platform platform) {
        float newY = platform.getBounds().y  + platform.getBounds().height; // Subtract a small value to ensure overlap
        f_player.setPosition(f_player.getPositionX(), newY);
    }
    
    
    //updated 3/18/2024
    class LadderState {
        boolean isClimbing = false;
        boolean climbingUp = false;
        boolean canMove = false;
        boolean finishedClimbing = false;
        // Constructor, getters, and setters might be added as needed
    }
    Map<Ladder, LadderState> ladderStates = new HashMap<>();

    // Your existing method with some modifications
    private void checkPlayerLadderCollisions() {
        boolean anyLadderClimbing = false; // Flag to check if the player is climbing any ladder

        for(Ladder ladder : f_ladders) {
            LadderState state = ladderStates.computeIfAbsent(ladder, k -> new LadderState());
            
            if(isWithinLadder(f_player.getBounds(), ladder.getBounds()) && !f_jumping) {
                state.isClimbing = true;
                f_player.checkLadder(state.isClimbing);
                anyLadderClimbing = true; // Player is climbing at least one ladder
                //System.out.println("CAN CLIMB 1!");
                
                // Handle input and movement here as before
                // Note: You may need a more sophisticated system to handle up/down movement across multiple ladders
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
   				 state.climbingUp = true;
   			 }
   			 else {
   				 state.climbingUp = false;
   			 }
                if(isWithinLadder(f_player.getBounds(), ladder.getMovementBoundsUp()) || isWithinLadder(f_player.getBounds(), ladder.getMovementBoundsDown())) {
        			state.canMove = true;
        			f_player.canMove(state.canMove);
        			state.finishedClimbing = false;
        			//System.out.println("Can move");
        		
        			
        			if(f_player.getBodyBounds().y < ladder.getMovementBoundsDown().y + 20) {
        				
        	    		float newY = ladder.getMovementBoundsDown().y + 5;
         	    		//System.out.println(f_player.getBodyBounds().y);
        	    		//System.out.println(ladder.getMovementBoundsDown().y);
        	    		f_player.setPosition(f_player.getPositionX(), newY);
        	    		}
        		
                
            } 
            else {
            	state.canMove = false;
    			f_player.canMove(state.canMove);
    			//System.out.println("Cannot move");
            
         }
            }
            else {
    		    state.isClimbing = false;
    		    f_player.checkLadder(state.isClimbing);
    		}
            
            if(((f_player.getBodyBounds().y + f_player.getBodyBounds().height)  > ladder.getMovementBoundsUp().y + 10) && isWithinLadderX(f_player.getBounds(), ladder.getMovementBoundsUp()) && state.climbingUp){
				System.out.println(f_player.getBodyBounds().y + f_player.getBodyBounds().height);
				System.out.println(ladder.getMovementBoundsUp().y);
				System.out.println("Finished climbing");
	    		float newY = ladder.getBounds().height - 5;
	    		f_player.setPosition(f_player.getPositionX(), newY);
	    		//f_finishedClimbing = true;
	    		//f_player.finishedClimbing(f_finishedClimbing);
	    		
	    		
	    		}
    		
    		
            
            // Update the ladder state in the map
            ladderStates.put(ladder, state);
        }

        // After checking all ladders, update the player's climbing state
        if (anyLadderClimbing) {
            f_isClimbing = true;
        } else {
            f_isClimbing = false;
        }
        f_player.checkLadder(f_isClimbing); // Apply the aggregated climbing state
        
        
        
        if(isWithinLadder(f_player.getBodyBounds(), ladder1.getMovementBoundsUp()) ) {
			f_finishedClimbing = true;
			//System.out.println("Finished climb");
    		f_player.finishedClimbing(f_finishedClimbing);
		}
		else {
			f_finishedClimbing = false;
			//System.out.println("Finished climb is falso ");
    		f_player.finishedClimbing(f_finishedClimbing);
		}
        
		
      
    }
    
    
    
    boolean isWithinLadder(Rectangle player, Rectangle ladder) {
        return player.x >= ladder.x &&
               player.x + player.width <= ladder.x + ladder.width &&
               player.y >= ladder.y &&
               player.y + player.height <= ladder.y + ladder.height;
    }
    
    boolean isWithinLadderX(Rectangle player, Rectangle ladder) {
        return player.x >= ladder.x &&
               player.x + player.width <= ladder.x + ladder.width;
    }
  
    /**
     * Disposes game resources.
     */
    @Override
    public void dispose() {
        f_batch.dispose();
        f_player.dispose();
        f_BGM.dispose();
    }
}




//SuperPower: floating
/* if (!foundCollision && (f_isClimbing == false)) {

//f_currentPlatform = null;
f_player.setCurrentPlatform(null);
f_player.checkCollision(foundCollision);
float y = f_player.getPositionY();
if(y >= PlatformY) {
y += -5;
f_player.setPosition(f_player.getPositionX(), y);
}
}

if(foundCollision && (f_isClimbing == false)) {
float y = f_player.getPositionY();
if(y >= PlatformY) {
y += -5;
f_player.setPosition(f_player.getPositionX(), y);
}
}
*/
