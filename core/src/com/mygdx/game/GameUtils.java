package com.mygdx.game;

// GameUtils.java or GamePersistence.java
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class GameUtils { // or GamePersistence
    private static final String SAVE_FILE = "savegame.json";
    
    public static void saveGameState(GameState gameState) {
        Json json = new Json();
        String saveJson = json.toJson(gameState);
        FileHandle file = Gdx.files.local(SAVE_FILE);
        file.writeString(saveJson, false);
    }

    public static GameState loadGameState() {
        FileHandle file = Gdx.files.local(SAVE_FILE);
        if (file.exists()) {
            Json json = new Json();
            return json.fromJson(GameState.class, file.readString());
        }
        return null;
    }
}
