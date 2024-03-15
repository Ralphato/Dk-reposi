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
    private Platform f_currentPlatform; 
    private float PlatformY;
    private Rectangle f_playerBounds, f_legBounds, f_bodyBounds;
    private boolean f_onPlatform, f_contactPlatform;

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
        // Add platforms with varying positions and textures
        f_platforms.add(new Platform(0, 0, 100, 50, platformTexture3));
        // More platforms added here..
        f_platforms.add(new Platform(100, 0, 100, 50,platformTexture2));
        f_platforms.add(new Platform(200, 0, 100, 50,platformTexture3));
        f_platforms.add(new Platform(300, 0, 100, 50,platformTexture2));
        f_platforms.add(new Platform(400, 0, 100, 50,platformTexture1));
        f_platforms.add(new Platform(500, 0, 100, 50,platformTexture3));
        f_platforms.add(new Platform(600, 0, 100, 50,platformTexture1));
        f_platforms.add(new Platform(100, 50, 100, 50,platformTexture3));
        f_platforms.add(new Platform(200, 100, 100, 50,platformTexture1));
        f_platforms.add(new Platform(300, 150, 100, 50,platformTexture3));
        f_platforms.add(new Platform(400, 200, 100, 50,platformTexture2));
        f_platforms.add(new Platform(500, 250, 100, 50,platformTexture3));}

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
        
        f_batch.begin();
        renderPlatforms();
        f_player.draw(f_batch);
        f_batch.end();
        checkPlayerPlatformCollisions();
        
        //debugging purposes
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for (Platform platform : f_platforms) {
            Rectangle Upperbounds = platform.getUpperBounds();
            shapeRenderer.rect(Upperbounds.x, Upperbounds.y, Upperbounds.width, Upperbounds.height);
            Rectangle bounds = platform.getBounds();
            shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
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
        	if(f_bodyBounds.overlaps(platform.getBounds())) {
        			System.out.println("Body is colliding with platform side");
        			float newX = platform.getBounds().x - 10;
        			f_player.setPosition(newX - 70, f_player.getPositionY()); //instead of newX put newX - f_player.width
        			//f_player.stopMovement();
        			
        			
        	
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
