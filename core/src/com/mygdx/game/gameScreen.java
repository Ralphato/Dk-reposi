package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class gameScreen implements Screen {
    private OrthographicCamera camera;
    private MyGdxGame game; // Reference to the main game class if needed
    private final int worldWidth = 1250; // Width of the world
    private final int worldHeight = 1400; // Height of the world
    private float shakeTime;
    private float shakeIntensity;
    private boolean isShaking;

    
    public gameScreen(MyGdxGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, worldWidth, worldHeight); // Example dimensions
        
      
    }
    
 // Method to start shaking the screen
    public void shakeScreen(float intensity, float duration) {
        this.shakeIntensity = intensity;
        this.shakeTime = duration;
        this.isShaking = true;
    }

    @Override
    public void render(float delta) {
    	if (isShaking) {
            if (shakeTime > 0) {
                float currentIntensity = shakeIntensity * (shakeTime / shakeIntensity);
                // Apply random shake
                camera.position.x += Math.random() * 2 * currentIntensity - currentIntensity;
                camera.position.y += Math.random() * 2 * currentIntensity - currentIntensity;
                shakeTime -= delta;
            } else {
                camera.position.set(worldWidth / 2f, worldHeight / 2f, 0); // Reset camera position
                isShaking = false;
            }
        }
    	
        camera.update();
        game.getSpriteBatch().setProjectionMatrix(camera.combined);
     
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
