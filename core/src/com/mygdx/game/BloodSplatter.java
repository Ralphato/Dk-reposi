package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BloodSplatter {
    private static final int FIRST_FRAME_1 = 32;
    private static final int LAST_FRAME_1 = 46;
    private static final int FIRST_FRAME_2 = 18;
    private static final int LAST_FRAME_2 = 30;
    
    private Texture[] bloodTextures1;
    private Texture[] bloodTextures2;
    private Animation<TextureRegion> bloodAnimation1;
    private Animation<TextureRegion> bloodAnimation2;
    private float animationTime = 0;
    private boolean isActive = false;
    private Animation<TextureRegion> currentAnimation; // Hold the current animation

    public BloodSplatter(float frameDuration, int animationChoice) {
        // Load first animation textures
        int frameCount1 = LAST_FRAME_1 - FIRST_FRAME_1 + 1;
        bloodTextures1 = new Texture[frameCount1];
        TextureRegion[] bloodFrames1 = new TextureRegion[frameCount1];
        for (int i = 0; i < frameCount1; i++) {
            String frameName = String.format("Blood1/bloodsplats_%04d.png", FIRST_FRAME_1 + i);
            bloodTextures1[i] = new Texture(Gdx.files.internal(frameName));
            bloodFrames1[i] = new TextureRegion(bloodTextures1[i]);
        }
        bloodAnimation1 = new Animation<>(frameDuration, bloodFrames1);
        
        // Load second animation textures
        int frameCount2 = LAST_FRAME_2 - FIRST_FRAME_2 + 1;
        bloodTextures2 = new Texture[frameCount2];
        TextureRegion[] bloodFrames2 = new TextureRegion[frameCount2];
        for (int i = 0; i < frameCount2; i++) {
            String frameName = String.format("Blood2/bloodsplats_%04d.png", FIRST_FRAME_2 + i);
            bloodTextures2[i] = new Texture(Gdx.files.internal(frameName));
            bloodFrames2[i] = new TextureRegion(bloodTextures2[i]);
        }
        bloodAnimation2 = new Animation<>(frameDuration, bloodFrames2);
        
        // Set the current animation based on the provided choice
        currentAnimation = animationChoice == 1 ? bloodAnimation1 : bloodAnimation2;
    }

    public void trigger() {
        isActive = true;
        animationTime = 0; // Reset the animation time whenever triggered
    }

    public void update(float delta) {
        if (isActive) {
            animationTime += delta;
            if (currentAnimation.isAnimationFinished(animationTime)) {
                isActive = false;
            }
        }
    }

    public void draw(SpriteBatch batch, float x, float y) {
        if (!isActive) return;

        TextureRegion currentFrame = currentAnimation.getKeyFrame(animationTime);
        batch.draw(currentFrame, x, y);
      
    }

    public void dispose() {
        disposeAnimationTextures(bloodTextures1);
        disposeAnimationTextures(bloodTextures2);
    }

    private void disposeAnimationTextures(Texture[] textures) {
        for (Texture texture : textures) {
            if (texture != null) {
                texture.dispose();
            }
        }
    }
}
