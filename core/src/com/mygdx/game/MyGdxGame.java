package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.Gdx;
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
    @Override
    public void create () {
        batch = new SpriteBatch();
        player = new Player(); // Initialize the player
        assetManager = new AssetManager();
        assetManager.load("DKMusic1.mp3", Music.class); // Load music file
        assetManager.finishLoading(); // Block until all assets are loaded
        BGM = assetManager.get("DKMusic1.mp3", Music.class); // Retrieve loaded music
        BGM.play();
        BGM.setLooping(true);

    }

    @Override
    public void render () {
        // Existing code for clearing screen and other stuff
        
    	Gdx.gl.glClearColor(0, 0, 0, 1); // Set the clear color (black, in this case)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        // Update player
        player.update(Gdx.graphics.getDeltaTime());

        // Start SpriteBatch
        batch.begin();

        // Draw player
        player.draw(batch);

        // End SpriteBatch
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        player.dispose(); // Dispose player assets
        BGM.dispose();
    }
}
