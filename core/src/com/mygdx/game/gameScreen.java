package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class gameScreen implements Screen {
    private OrthographicCamera camera;
    private MyGdxGame game; // Reference to the main game class if needed
    private final int worldWidth = 1250; // Width of the world
    private final int worldHeight = 1000; // Height of the world

    
    public gameScreen(MyGdxGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, worldWidth, worldHeight); // Example dimensions
    }

    @Override
    public void render(float delta) {
        camera.update();
        game.getSpriteBatch().setProjectionMatrix(camera.combined);

        // Additional rendering code
    }
    
    // Implement other required methods like resize, show, hide, pause, resume, and dispose
    
    public OrthographicCamera getCamera() {
        return camera;
    }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
    
    // Other camera-related methods
}
