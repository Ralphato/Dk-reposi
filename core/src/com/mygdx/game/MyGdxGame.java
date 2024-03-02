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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.Player;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Player player; // Add player variable
    private AssetManager assetManager;
    Music BGM;
    private ArrayList<Platform> platforms;
    private Rectangle playerBounds;
    private boolean onPlatform, contactPlatform;
    public void create () {
        batch = new SpriteBatch();
        player = new Player(50,50,0.02f,0.02f); // Initialize the player
        assetManager = new AssetManager();
        assetManager.load("DKMusic1.mp3", Music.class); // Load music file
        assetManager.finishLoading(); // Block until all assets are loaded
        BGM = assetManager.get("DKMusic1.mp3", Music.class); // Retrieve loaded music
        BGM.setVolume(0.4f);
        BGM.play();
        BGM.setLooping(true);
        platforms = new ArrayList<Platform>();
       playerBounds= player.getBoundingBox();

        // Create platforms
        Texture platformTexture = new Texture(Gdx.files.internal("PlainDessertPlat.png"));
        Texture platformTexture2 = new Texture(Gdx.files.internal("RustPlat1.png"));
        Texture platformTexture3 = new Texture(Gdx.files.internal("RustPlat2.png"));
        platforms.add(new Platform(0, 0, 100, 50,platformTexture3));
        platforms.add(new Platform(100, 0, 100, 50,platformTexture2));
        platforms.add(new Platform(200, 0, 100, 50,platformTexture3));
        platforms.add(new Platform(300, 0, 100, 50,platformTexture2));
        platforms.add(new Platform(400, 0, 100, 50,platformTexture));
        platforms.add(new Platform(500, 0, 100, 50,platformTexture3));
        platforms.add(new Platform(600, 0, 100, 50,platformTexture));
        platforms.add(new Platform(100, 50, 100, 50,platformTexture3));
        platforms.add(new Platform(200, 100, 100, 50,platformTexture));
        platforms.add(new Platform(300, 150, 100, 50,platformTexture3));
        platforms.add(new Platform(400, 200, 100, 50,platformTexture2));
        platforms.add(new Platform(500, 250, 100, 50,platformTexture3));
    }

    @Override
    public void render () {
        // Existing code for clearing screen and other stuff
        
    	Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1); // Set the clear color (black, in this case)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        // Update player
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        player.update(Gdx.graphics.getDeltaTime());

        // Start SpriteBatch
        batch.begin();

        // Draw player
        player.draw(batch);

        // End SpriteBatch
        batch.end();
        
        batch.begin();

        // Render platforms
        for (Platform platform : platforms) {
            platform.render(batch);
        }

        batch.end();
        for (Platform platform : platforms) {
        	if (playerBounds.overlaps(platform.getBounds())) {
                // Resolve collision
        		//onPlatform=true;
                if ((player.getYVelocity() < 0 && player.getY() > platform.getBounds().getY())) {
                    // Player is falling and above the platform
                    // Adjust player's position to land on the top surface of the platform
                    player.setY(platform.getBounds().getY() + platform.getBounds().getHeight());
                    // Reset player's vertical velocity
                    player.setYVelocity(0);
                    player.setJumping(false);
                  }
               
                /*if(player.getX()+player.getWidth()<=(platform.getBounds().getX()+platform.getBounds().getHeight())&& player.getY()<=platform.getBounds().getY()) {
                	player.changeX(-5);
                 }
                if(player.getX()>=(platform.getBounds().getX()+platform.getBounds().getHeight())&& player.getY()<=platform.getBounds().getY()) {
                	player.changeX(5);
                }
              }*/
        		/*if(player.getX()>platform.getBounds().getX()+platform.getBounds().getWidth()||player.getX()+player.getWidth()>platform.getBounds().getX()){
        			onPlatform=false;
            	  if(!onPlatform&& player.getY()==platform.getBounds().getY()) {
            		  player.setJumping(true);
            	  }
              }*/
        	
        	
        	}
        	}
        
       
     }
    

    @Override
    public void dispose () {
        batch.dispose();
        player.dispose(); // Dispose player assets
        BGM.dispose();
        
    }
}
