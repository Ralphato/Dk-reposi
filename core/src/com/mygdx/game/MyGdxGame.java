package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
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

    /**
     * Initializes the game components.
     */
    @Override
    public void create() {
        f_batch = new SpriteBatch();
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

        // First row of platforms
        f_platforms.add(new Platform(0, 50, 100, 50, platformTexture1));
        f_platforms.add(new Platform(100, 50, 100, 50, platformTexture2));
        f_platforms.add(new Platform(200, 50, 100, 50, platformTexture3));
        f_platforms.add(new Platform(300, 50, 100, 50, platformTexture1));
        f_platforms.add(new Platform(400, 50, 100, 50, platformTexture2));
        f_platforms.add(new Platform(500, 50, 100, 50, platformTexture3));
        f_platforms.add(new Platform(600, 50, 100, 50, platformTexture1));
        
        f_platforms.add(new Platform(200, 100, 100, 50, platformTexture1));
        
        f_ladders.add(new Ladder(400,100,200,150,ladderTexture));
        

       
   

        // Third row of platforms, continuing the ascending pattern
        f_platforms.add(new Platform(0, 250, 100, 50, platformTexture3));
        f_platforms.add(new Platform(100, 250, 100, 50, platformTexture1));
        f_platforms.add(new Platform(200, 250, 100, 50, platformTexture2));
        f_platforms.add(new Platform(300, 250, 100, 50, platformTexture3));
        f_platforms.add(new Platform(400, 250, 100, 50, platformTexture1));
        f_platforms.add(new Platform(500, 250, 100, 50, platformTexture2));
        f_platforms.add(new Platform(600, 250, 100, 50, platformTexture3));

    }

    /**
     * Updates the game logic and renders the game components.
     */
    @Override
    public void render() {
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
    private void checkPlayerLadderCollisions() {
    	for(Ladder ladder: f_ladders) {
    		if(isWithinLadder(f_player.getBounds(), ladder.getBounds()) && !f_jumping) { // and if not jumping
    			f_isClimbing = true;
    			f_player.checkLadder(f_isClimbing);
    			 if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
    				 f_climbingUp = true;
    			 }
    			 else {
    				 f_climbingUp = false;
    			 }
    			
    			if(isWithinLadder(f_player.getBounds(), ladder.getMovementBoundsUp()) || isWithinLadder(f_player.getBounds(), ladder.getMovementBoundsDown())) {
        			f_canMove = true;
        			f_player.canMove(f_canMove);
        			f_finishedClimbing = false;
        			//System.out.println("Can move");
        		
        			
        			if(f_player.getBodyBounds().y < ladder.getMovementBoundsDown().y + 20) {
        				
        	    		float newY = ladder.getMovementBoundsDown().y + 5;
         	    		//System.out.println(f_player.getBodyBounds().y);
        	    		//System.out.println(ladder.getMovementBoundsDown().y);
        	    		f_player.setPosition(f_player.getPositionX(), newY);
        	    		}
        		}
        		else {
        			f_canMove = false;
        			f_player.canMove(f_canMove);
//        			/System.out.println("Cannot move");
        		}
    		}
    		 else {
    		    f_isClimbing = false;
    		    f_player.checkLadder(f_isClimbing);
    		}
    		
    		if(((f_player.getBodyBounds().y + f_player.getBodyBounds().height)  > ladder.getMovementBoundsUp().y + 10) && isWithinLadderX(f_player.getBounds(), ladder.getMovementBoundsUp()) && f_climbingUp){
				System.out.println(f_player.getBodyBounds().y + f_player.getBodyBounds().height);
				System.out.println(ladder.getMovementBoundsUp().y);
				System.out.println("Finished climbing");
	    		float newY = ladder.getBounds().height - 5;
	    		f_player.setPosition(f_player.getPositionX(), newY);
	    		
	    		
	    		}
    				
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
