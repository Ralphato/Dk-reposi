package com.mygdx.game;

import java.util.ArrayList;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.assets.AssetManager;
/**
 * This class represents the main game application.
 * It handles the initialization of game components, game logic updates, and rendering.
 */
public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch f_batch;
    private Player f_player;
    private donkeyKong f_donkey;
    private AssetManager f_assetManager;
    private Music f_BGM;
    private ArrayList<Platform> f_platforms;
    private ArrayList<Platform> f_platformsNoRender;
    private ArrayList<Ladder> f_ladders;
    private ArrayList<Barrel> f_barrels;
    private ArrayList<Barrel2> f_barrels2;
    private ArrayList<Barrel3> f_barrels3;
    private ArrayList<powerUps> f_powerUps;
    powerUps powerUps1;
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
    private boolean teleport=false;
    public static boolean shield=false;
    private OrthographicCamera camera;
    Ladder ladder1, ladder2, ladder3, ladder4, ladder5;
    private gameScreen f_gameScreen;
    private float f_speed;
    private float barrelVelocity = 200;
    Random random = new Random();
    Texture barrel2Texture;
    Texture barrel1Texture;
    private final int worldWidth = 1250; // Width of the world
    private final int worldHeight = 1400; // Height of the world
    private Texture f_backgroundTexture;
    private Texture f_UITexture;
    private Texture f_winUITexture;
    private Texture f_gameOverTexture;
    private Texture f_shieldTexture;
    private Texture f_lifeTexture;
    static boolean f_endGame;
    private boolean f_removeBarrel3 = false;
    Whip playerWhip;
    private float f_whipX, f_whipY;
    private GLProfiler profiler;
    private int f_usesLeft=0, f_hitsLeft=0;
    private boolean f_active=false,f_tActive;
    private  Sound hit, pShield, pTele, teleported, blocked, depleted;
    private BloodSplatter f_blood, f_blood2;
    private int f_randomBlood;
    private boolean f_stopThrowing = false;
    private boolean hurt =false;
    private int oldHealth;
    private boolean f_startGame = false;
    private boolean f_startBarrel = false;
    private boolean f_winUI = false;
    private boolean f_toggle = false;
    Shield playershield;
    private float f_ShieldX, f_ShieldY;
    /**
     * Initializes the game components.
     */
    @Override
    public void create() {
    	
    	
    	
    	//loadPrevious();
        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable(); // Start profiling
        hit = Gdx.audio.newSound(Gdx.files.internal("bonk.mp3"));
        pShield = Gdx.audio.newSound(Gdx.files.internal("shield.mp3"));
        teleported = Gdx.audio.newSound(Gdx.files.internal("teleport.mp3"));
        pTele = Gdx.audio.newSound(Gdx.files.internal("telePickup.mp3"));
        blocked = Gdx.audio.newSound(Gdx.files.internal("blocked.mp3"));
        depleted = Gdx.audio.newSound(Gdx.files.internal("armorBreak.mp3"));
        // The rest of your initialization
        f_batch = new SpriteBatch();
        f_gameScreen = new gameScreen(this);
        f_player = new Player();
        f_donkey = new donkeyKong(f_gameScreen);
        f_assetManager = new AssetManager();
        f_assetManager.load("zehahaha_laugh.mp3", Music.class);
        f_assetManager.finishLoading(); // Typically called in the loading screen logic
        f_assetManager.load("Music/MainMenu.mp3", Music.class);
        f_assetManager.finishLoading();
        f_assetManager.load("Music/StageOneLoop.mp3", Music.class);
        f_assetManager.finishLoading();
        f_assetManager.load("Music/WinSFX.mp3", Music.class);
        f_assetManager.finishLoading();
        f_assetManager.load("Music/LoseSFX.mp3", Music.class);
        f_assetManager.finishLoading();
        f_BGM = f_assetManager.get("Music/MainMenu.mp3", Music.class);
        f_BGM.setVolume(0.3f);
        f_BGM.play();
        f_BGM.setLooping(true);
        f_platforms = new ArrayList<>();
        f_powerUps = new ArrayList<>();
        f_platformsNoRender = new ArrayList<>();
        f_ladders = new ArrayList<>();
        f_barrels = new ArrayList<>();
        f_barrels2 = new ArrayList<>();
        f_barrels3 = new ArrayList<>();
        f_playerBounds = f_player.getBodyBounds();
        f_legBounds = f_player.getLegsBounds();
        f_backgroundTexture = new Texture("background.png");
        f_UITexture = new Texture("UI.png");
        f_winUITexture = new Texture("WinUI.png");
        f_gameOverTexture = new Texture("Lose UI.png");
        f_shieldTexture = new Texture("BlueHeart.png");
        f_lifeTexture = new Texture("HeartFinal.png");
        initialize();
        playerWhip = new Whip(f_player.getPositionX(), f_player.getPositionY(), 50, 5, "whip1.png");
        f_blood = new BloodSplatter(0.1f, 1);
        f_blood2 = new BloodSplatter(0.1f, 2);
        
        playershield = new Shield(f_player.getPositionX(), f_player.getPositionY(), 0, 0);
       // playerWhip = new Whip(f_player.getPositionX(), f_player.getPositionY(), 50, 5, "whip1.png");
       
    }

    /**
     * Initializes platforms with textures.
     */
    private void initialize() {
    	Texture platformTexture1 = new Texture("PlainDessertPlat.png");
        Texture platformTexture2 = new Texture("RustPlat1.png");
        Texture platformTexture3 = new Texture("RustPlat2.png");
        
        Texture ladderTexture = new Texture("ladder.png");
        
        barrel1Texture = new Texture("barrel1.png");
        barrel2Texture = new Texture("barrel2.png");

        // First row of platforms
        for(int i = 0; i <= 1200; i+= 100) {
        f_platforms.add(new Platform(i, 50, 100, 50, platformTexture3));
        }
        
        // Second row of platforms
        for(int i = 0; i <= 1050; i+= 100) {
            f_platforms.add(new Platform(i, 250, 100, 50, platformTexture1));
            }
       // third row
        for(int i = 150; i <= 1200; i+= 100) {
            f_platforms.add(new Platform(i, 450, 100, 50, platformTexture2));
            }
       // fourth row
        for(int i = 0; i <= 1050; i+= 100) {
            f_platforms.add(new Platform(i, 650, 100, 50, platformTexture1));
            }
        
     // fifth row
        for(int i = 150; i <= 1200; i+= 100) {
            f_platforms.add(new Platform(i, 850, 100, 50, platformTexture3));
            }
        // sixth row
        for(int i = 0; i <= 1050; i+= 100) {
            f_platforms.add(new Platform(i, 1050, 100, 50, platformTexture1));
            }
        
        //f_platforms.add(new Platform(200, 100, 100, 50, platformTexture1));
        
     // Define ladder1 and ladder2 as separate variables
        ladder1 = new Ladder(900, 100, 200, 150, ladderTexture);
        ladder2 = new Ladder(200, 300, 200, 150, ladderTexture);    
        ladder3 = new Ladder(900, 500, 200, 150, ladderTexture);
        ladder4 = new Ladder(200, 700, 200, 150, ladderTexture);
        ladder5 = new Ladder(900, 900, 200, 150, ladderTexture);
        //ladder6 = new Ladder(200, 1100, 200, 150, ladderTexture);
       addPowerUps();
        

        //adding barrels
       
       //barrel 1 
        
        //f_barrels.add(new Barrel(barrel1Texture,20,1100,23, 200));
        
        
        
        //barrel spawning
      //scheduleBarrelSpawning();
        //scheduleBarrelSpawning2();
        //scheduleBarrelSpawning3();
  
        //f_barrels2.add(new Barrel2(barrel2Texture,40,1095,23,1));    
        
        // Then add them to your collection of ladders
        f_ladders.add(ladder1);
        f_ladders.add(ladder2);
        f_ladders.add(ladder3);
        f_ladders.add(ladder4);
        f_ladders.add(ladder5);
        

       
   

      
    }
    

    /**
     * Updates the game logic and renders the game components.
     */
    @Override
    public void render() {
    	if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)&&!f_startGame) {
      		f_BGM.stop();
      		f_BGM = f_assetManager.get("Music/StageOneLoop.mp3",Music.class);
      		f_BGM.setVolume(0.3f);
      		f_BGM.play();
      		f_BGM.setLooping(true);
    		f_startGame = true;
      		 f_startBarrel = true;
      		 f_donkey.start(true);
      	 }
    	 profiler.reset();

         
    	
    	f_gameScreen.render(Gdx.graphics.getDeltaTime());
        clearScreen();
        f_player.update(Gdx.graphics.getDeltaTime());
       if(f_startGame) {
        f_donkey.update(Gdx.graphics.getDeltaTime());
       }
        playerWhip.update(Gdx.graphics.getDeltaTime());
        f_blood.update(Gdx.graphics.getDeltaTime());
        f_blood2.update(Gdx.graphics.getDeltaTime());
        //render bounds
        f_playerBounds = f_player.getBodyBounds();
        f_legBounds = f_player.getLegsBounds();
        f_bodyBounds = f_player.getBodyBounds();
        f_jumping = f_player.isJumping();
        f_player.setStart(f_startGame);
        f_player.setEnd(f_endGame);
        f_player.setWin(f_winUI);
        playerWhip.setWEnd(f_endGame);
        playerWhip.setWWin(f_winUI);
        playerWhip.setWStart(f_startGame);
        f_batch.begin();
        if(!f_startGame) {

    		f_batch.draw(f_UITexture, 0, 20, worldWidth, worldHeight);
    		

    	}

       if(f_winUI) {
    	   if(!f_toggle) {
    		   f_BGM.stop();
    		   f_BGM = f_assetManager.get("Music/WinSFX.mp3", Music.class);
    		   f_BGM.setVolume(0.3f);
    		   f_BGM.play();
    		   f_toggle = true;
    	   }
    	   f_batch.draw(f_winUITexture, 0, 20, worldWidth, worldHeight);
    	   f_batch.end();
       }
       else {

        if(f_startGame) {
        f_batch.setColor(0.65f, 0.65f, 0.65f, 1f); // Darkens the color to 50% of the original for all colors drawn afterward
        
        f_batch.draw(f_backgroundTexture, 0, 20, worldWidth, worldHeight);
        f_batch.setColor(1f, 1f, 1f, 1f); // Reset color to normal
        renderPlatforms();
        renderLadders();
      if(f_startGame||f_winUI||f_endGame) {
    	  renderPowerUps(); 
      }
       renderBarrels();
        updateBarrels();
        
        
        
        renderWhip();
        
        if (shield) {
        	playershield.update(Gdx.graphics.getDeltaTime());
        	playershield.setWEnd(f_endGame);
            playershield.setWWin(f_winUI);
            playershield.setWStart(shield = true);
        	renderShield();

        }
        
        f_player.draw(f_batch);
        f_donkey.draw(f_batch);
        f_blood.draw(f_batch, 0, 1000);
        f_blood2.draw(f_batch, 0, 1000);
        lifeSys(oldHealth);
        
        
        if(f_endGame) {
        	 
        	
            f_batch.setColor(1, 1, 1, 1);  // Set semi-transparency for the game over texture
            f_batch.draw(f_gameOverTexture, 0, 0, worldWidth, worldHeight);
            
            f_batch.setColor(1, 1, 1, 1);  // Reset transparency before drawing Donkey Kong
            f_donkey.setPositionX(700);
            f_donkey.setPositionY(1600);
            f_donkey.endGame(true);
            f_donkey.draw(f_batch);  

            f_player.setPosition(-3030, -3000); // Hides player by setting offscreen position
           
          
        }

        
        }
        f_batch.end();
        
        
        checkPlayerLadderCollisions();
        if(f_isClimbing == false) {
        checkPlayerPlatformCollisions();
        }
        
       
        
        checkBarrelPlatformCollisions();
        checkBarrel2PlayerCollisions();
        checkBarrelPlayerCollisions();
        checkPowerUpCollisions();
        checkWhipKongCollisions();
        
        scheduleBarrelSpawning();
        scheduleBarrelSpawning2();
        scheduleBarrelSpawning3();
        f_startBarrel = false;     

        startFighting();
       
        //debugging purposes
        /*ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        Rectangle donkeyBounds = f_donkey.getBounds();
        shapeRenderer.rect(donkeyBounds.x, donkeyBounds.y, donkeyBounds.width, donkeyBounds.height);

        Rectangle donkeyBounds2 = f_donkey.getBounds();
        shapeRenderer.rect(donkeyBounds2.x, donkeyBounds2.y, donkeyBounds2.width, donkeyBounds2.height);

        Rectangle playerBounds = f_player.getBodyBounds();
        shapeRenderer.rect(playerBounds.x, playerBounds.y, playerBounds.width, playerBounds.height);


        shapeRenderer.setColor(Color.GREEN);
        Rectangle whipBounds = playerWhip.getBounds();
        shapeRenderer.rect(whipBounds.x, whipBounds.y, whipBounds.width, whipBounds.height);
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
        
        shapeRenderer.setColor(Color.PINK);
        for(Barrel barrel : f_barrels) {
        	 Circle Barrelbounds = barrel.getBounds();
             shapeRenderer.circle(Barrelbounds.x, Barrelbounds.y, Barrelbounds.radius);
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
        
        shapeRenderer.setColor(Color.BLACK);
        for(Barrel2 barrel : f_barrels2) {
          	 Circle Barrelbounds = barrel.getBounds();
               shapeRenderer.circle(Barrelbounds.x, Barrelbounds.y, Barrelbounds.radius);
          }
        shapeRenderer.setColor(Color.WHITE); 
        Rectangle playerShrinkedBounds = f_player.getShrinkedBounds();
        shapeRenderer.rect(playerShrinkedBounds.x, playerShrinkedBounds.y, playerShrinkedBounds.width, playerShrinkedBounds.height);

        
        shapeRenderer.setColor(Color.WHITE); 
        Rectangle powerBounds = powerUps1.getBounds();
        shapeRenderer.rect(powerBounds.x, powerBounds.y, powerBounds.width, powerBounds.height);
        
       
        
      
    	shapeRenderer.end();
 */
        // Now we can get the number of calls from the GLProfiler instance
        //System.out.println("OpenGL calls this frame: " + profiler.getCalls());
        //System.out.println("Texture bindings this frame: " + profiler.getTextureBindings());
        //System.out.println("Draw calls this frame: " + profiler.getDrawCalls());
    }
    
    }
    private void startFighting() {
    	if(f_player.getPositionY() >= 1100) {
    		f_donkey.startFighting(true);
    	}
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
        	if (platform.getPlatY() != 50) { 
            platform.render(f_batch);
        	}
        }
    }
    
    private void renderLadders() {
    	for(Ladder ladder : f_ladders) {
    		ladder.render(f_batch);
    	}
    }
    
    private void renderBarrels() {
    	for(Barrel barrel: f_barrels) {
    		barrel.render(f_batch);
    	}
    	for(Barrel2 barrel: f_barrels2) {
    		barrel.render(f_batch);
    	}
    	for(Barrel3 barrel: f_barrels3) {
    		barrel.render(f_batch);
    	}
    }
    
    private void renderPowerUps() {
    	for(powerUps pUp: f_powerUps) {
    		pUp.update(Gdx.graphics.getDeltaTime());
    		pUp.draw(f_batch);
    	}
    		    	
    }
    
    private void renderWhip() {
    	
    	
    	f_whipX = f_player.getPositionX();
    	f_whipY = f_player.getPositionY();
    	playerWhip.getPlayerPosition(f_whipX, f_whipY); //send player's positions to whip class
    	playerWhip.getPlayerRightDirection(f_player.rightDirection()); //send if player is in right direction to whip class
    	playerWhip.draw(f_batch);
    	
    }
    
    private void renderShield() {


    	f_ShieldX = f_player.getPositionX();
    	f_ShieldY = f_player.getPositionY();
    	playershield.getPlayerPosition(f_ShieldX, f_ShieldY); //send player's positions to Shield class
    	playershield.draw(f_batch);

    }
    
    private void updateBarrels() {
    	 // Use an Iterator to safely remove elements while iterating
        Iterator<Barrel> barrelIterator = f_barrels.iterator();
        while (barrelIterator.hasNext()) {
            Barrel barrel = barrelIterator.next();
            barrel.update(Gdx.graphics.getDeltaTime());

            
            if (barrel.getPosition().y < 150 && barrel.getPosition().x < 10) {
                barrelIterator.remove(); // This removes the current barrel from the collection
            }
        }

        // Repeat for the second type of barrels if necessary
        Iterator<Barrel2> barrel2Iterator = f_barrels2.iterator();
        while (barrel2Iterator.hasNext()) {
            Barrel2 barrel = barrel2Iterator.next();
            barrel.update(Gdx.graphics.getDeltaTime());

            // Apply any specific condition for Barrel2
            if (barrel.getPosition().y < 100 && barrel.getPosition().x < 5) {
                barrel2Iterator.remove();
            }
        }
        
        Iterator<Barrel3> barrel3Iterator = f_barrels3.iterator();
        while (barrel3Iterator.hasNext()) {
            Barrel3 barrel = barrel3Iterator.next();
            barrel.update(Gdx.graphics.getDeltaTime());
            
            if(f_removeBarrel3) {
            	barrel3Iterator.remove();
            	f_removeBarrel3 = false; //so it only removes 1 barrel
            	
            }
    
        }
    }
    
    public void scheduleBarrelSpawning() {
    	if(f_startBarrel) {
        Timer.schedule(new Task(){
            @Override
            public void run() {
                // Randomize the chance for each spawn
                int chance = random.nextInt(5) + 1;
                // Spawn barrels with varying positions based on chance
                if(!f_endGame&&!f_stopThrowing) {
                    
                    f_barrels2.add(new Barrel2(barrel2Texture,350,1095,23,chance));
                    f_donkey.throwing(true);
                    f_removeBarrel3 = true;
                    // Schedule another task to set throwing to false after a delay
                    Timer.schedule(new Task() {
                        @Override
                        public void run() {
                            f_donkey.throwing(false);
                        }
                    }, 0.7f); // Delay in seconds after which to set throwing to false
                }
            }
        }, 6, 3, 20); // Start delay (0 seconds), interval between spawns (5 seconds), number of repetitions (20)
    }
    	}

    
    public void scheduleBarrelSpawning2() {
    	if(f_startBarrel) {
        Timer.schedule(new Task(){
            @Override
            public void run() {
                // Spawn barrels with varying positions based on chance
            	if(!f_endGame&&!f_stopThrowing&&f_startGame) {
                f_barrels.add(new Barrel(barrel1Texture,50,1100,23, 200));
            	}
                 
            }
        }, 10, 8 , 20); // Start delay (0 seconds), interval between spawns (20 seconds), number of repetitions (20)
    }
    }
    public void scheduleBarrelSpawning3() {
    	if(f_startBarrel) {
        Timer.schedule(new Task(){
            @Override
            public void run() {
                // Randomize the chance for each spawn
                
                // Spawn barrels with varying positions based on chance
                if(!f_endGame && !f_stopThrowing && f_startGame) {
                    f_barrels3.add(new Barrel3(barrel2Texture,35,1100,23));

                    f_removeBarrel3 = false;
                    
                  
                 
                }
            }
        }, 6, 2.99f, 20); //4.99 delay is used here instead of 5 to allow sheduleBarrelSpawning1 to set removeBarrel3 to true
    }
    }
    public void loadPrevious() {
    	GameState gameState = GameUtils.loadGameState();
         if (gameState != null) {
             // Load the game state
             //currentScore = gameState.score;
             f_player.setPosition(gameState.playerPositionX, gameState.playerPositionY);
             // ... more as needed
         }
    }
    
    private void addPowerUps(){
    	 powerUps1= new powerUps(500,100,100,100,1);
         f_powerUps.add(powerUps1);
         for(int i = 1; i<3;i++) {
         	int yVal = 650, xVal = 500;
         	int randomizer=(int)(Math.random()*5)+1;
         	int powerBit = (int)(Math.random()*2)+1;
         	if(randomizer==1) {
         		yVal = 500;
         		xVal = 300;
         	}else if(randomizer==2) {
         		yVal = 700;
         		xVal = 1000;
         	}else if(randomizer==3) {
         		yVal = 900;
         		xVal = 900;
         	}else if(randomizer==4) {
         		yVal = 300;
         		xVal = 700;
         	}else if(randomizer==5) {
         		yVal = 500;
         		xVal = 500;
         	}
         	//parameter corresponds to (x pos, y pos, width, height, texture aka power up)
         	
         if(yVal!=f_powerUps.get(f_powerUps.size()-1).getYPosition()&&xVal!=f_powerUps.get(f_powerUps.size()-1).getXPosition()) {
         	if(f_powerUps.get(f_powerUps.size()-1).numReturn()!=powerBit) {
         	f_powerUps.add(new powerUps(xVal,yVal,100,100,powerBit));
         	
         	System.out.println("Y val:\t"+f_powerUps.get(f_powerUps.size()-1).getYPosition());
         	}else {
         		powerBit = (int)(Math.random()*2)+1;
         		i--;
         	}
         }else if(xVal!=f_powerUps.get(f_powerUps.size()-1).getXPosition()) {
         	if(f_powerUps.get(f_powerUps.size()-1).numReturn()!=powerBit) {
             	f_powerUps.add(new powerUps(xVal,yVal+200,100,100,powerBit));
             	System.out.println("Y val:\t"+f_powerUps.get(f_powerUps.size()-1).getYPosition());
             	}else {
             		powerBit = (int)(Math.random()*2)+1;
             		i--;
             	}
         	
         	
         	System.out.println("Y val:\t"+f_powerUps.get(f_powerUps.size()-1).getYPosition());
         	
         }else if(yVal!=f_powerUps.get(f_powerUps.size()-1).getYPosition()) {
         	if(f_powerUps.get(f_powerUps.size()-1).numReturn()!=powerBit) {
             
             	f_powerUps.add(new powerUps(xVal+100,yVal,100,100,powerBit));
             	System.out.println("Y val:\t"+f_powerUps.get(f_powerUps.size()-1).getYPosition());
             	}else {
             		powerBit = (int)(Math.random()*2)+1;
             		i--;
             		
             	}
         	
         	
         	System.out.println("Y val:\t"+f_powerUps.get(f_powerUps.size()-1).getYPosition());
         	
         }else  {
         	if(f_powerUps.get(f_powerUps.size()-1).numReturn()!=powerBit) {
      
             	f_powerUps.add(new powerUps(xVal+100,yVal+200,100,100,powerBit));
             	System.out.println("Y val:\t"+f_powerUps.get(f_powerUps.size()-1).getYPosition());
             	}else {
             		powerBit = (int)(Math.random()*2)+1;
             		i--;
             	}
         	
         	
         	System.out.println("Y val:\t"+f_powerUps.get(f_powerUps.size()-1).getYPosition());
         	
         }
         	
         	}	
    }
    

    private void checkWhipKongCollisions() {
        Rectangle whipBounds = playerWhip.getBounds();
        Rectangle kongBoundsRight = f_donkey.getBounds();
        Rectangle kongBoundsLeft = f_donkey.getBounds();

        // Check if the rectangles overlap (intersect/touch in any form)
        if(f_player.rightDirection()) {
	        if (whipBounds.overlaps(kongBoundsRight)) {
	            System.out.println("Whip is touching Donkey Kong");
	            bloodSplatter();
	            // You can handle the collision response here
	            f_donkey.decreaseHealth();
	            f_stopThrowing = true;
	            

	        }
        }
        else {
        	if (whipBounds.overlaps(kongBoundsLeft)) {
	            System.out.println("Whip is touching Donkey Kong");
	            bloodSplatter();

	            f_donkey.decreaseHealth();
	            f_stopThrowing = true;
	        }

        }

        if(f_donkey.getHealth() <= 0) {
        	System.out.println("DEADO");
        	f_winUI = true;
        }
    }

    private void bloodSplatter() {
    	f_randomBlood = MathUtils.random(1, 2);

    	switch(f_randomBlood) {
    	case 1:
    	f_blood.trigger();
    	break;
    	case 2:
    	f_blood2.trigger();
    	break;
    	}





    }
    /**
     * Checks for collisions between the player and platforms.
     */
    private void checkPlayerPlatformCollisions() {
    	
        boolean foundCollision = false;
        
        boolean stopsClimb = false;
        

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
                stopsClimb = true;
                break; // This stops checking more platforms after finding the first collision
            }
            else {
            	stopsClimb = false;
            }
        }	
        		
      
        
        // If after checking all platforms, no collision is found, then reset.
        if (!foundCollision) {
        	//System.out.println("No collision");
            //f_currentPlatform = null;
            f_player.setCurrentPlatform(null);
            f_player.checkCollision(foundCollision);
            
            f_player.endOfPlatform(true);
            
        }
        
        if(foundCollision) {
        	//System.out.println(" collision");
        	float y = f_player.getPositionY();
            if(y >= PlatformY) {
        	y += -5;
        	f_player.setPosition(f_player.getPositionX(), y);
        	f_player.endOfPlatform(false);
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
        boolean climbingDown = false;
        // Constructor, getters, and setters might be added as needed
    }
    Map<Ladder, LadderState> ladderStates = new HashMap<>();

    // Your existing method with some modifications
    private void checkPlayerLadderCollisions() {
        boolean anyLadderClimbing = false; // Flag to check if the player is climbing any ladder

        for(Ladder ladder : f_ladders) {
            LadderState state = ladderStates.computeIfAbsent(ladder, k -> new LadderState());
           
            //When player can start climbing the ladder
            if(isWithinLadder(f_player.getBodyBounds(), ladder.getBounds()) && !f_jumping) {
                state.isClimbing = true;
                f_player.checkLadder(state.isClimbing);
                anyLadderClimbing = true; // Player is climbing at least one ladder
                //System.out.println("CAN CLIMB 1!");
                
                
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
        			
        			
        			if(f_player.getBodyBounds().y < ladder.getMovementBoundsDown().y + 20) {
        				
                		
        	    		float newY = ladder.getMovementBoundsDown().y +1;
        	    		
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
            
            //Let the player spawn above the ladder once reached certain height
            if(((f_player.getBodyBounds().y + f_player.getBodyBounds().height)  > ladder.getMovementBoundsUp().y + 10) && isWithinLadderX(f_player.getBounds(), ladder.getMovementBoundsUp()) && state.climbingUp
            		&& isWithinLadder(f_player.getBodyBounds(), ladder.getBounds())){
				
            	
            	//should be 292
	    		float newY = ladder.getBounds().y + 197;
	    		System.out.println("New Y is: " + newY);
	    		f_player.setPosition(f_player.getPositionX(), newY);
	    		//f_finishedClimbing = true;
	    		//f_player.finishedClimbing(f_finishedClimbing);
	    		
	    		
	    		}
    		/*
            // not working because loop checks multiple ladders so state.climbdown will always be false
            System.out.println(ladder.getMovementBoundsDown().y +20);
            if(f_player.getBodyBounds().y < ladder.getMovementBoundsDown().y + 20) {
            	System.out.println("LOOL");
            	state.climbingDown = true;
        		f_player.stopClimbingDown(state.climbingDown);        
        		
        		}
            else {
    		
    		state.climbingDown = false;
    		f_player.stopClimbingDown(state.climbingDown);        
    		
            }
          */
    		
            
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
        
        
        
        
        //individual checking of each ladder
        if(isWithinLadder(f_player.getBodyBounds(), ladder1.getMovementBoundsUp()) || isWithinLadder(f_player.getBodyBounds(), ladder2.getMovementBoundsUp()) ||
           isWithinLadder(f_player.getBodyBounds(), ladder3.getMovementBoundsUp()) || isWithinLadder(f_player.getBodyBounds(), ladder4.getMovementBoundsUp()) ||
           isWithinLadder(f_player.getBodyBounds(), ladder5.getMovementBoundsUp())) {
			f_finishedClimbing = true;
			//System.out.println("Finished climbing the ladder");
    		f_player.finishedClimbing(f_finishedClimbing);
		}
		else {
			f_finishedClimbing = false;
			//System.out.println("Finished climb is falso ");
    		f_player.finishedClimbing(f_finishedClimbing);
		}
        
       
       
        
        if(isWithinLadder2(f_player.getBodyBounds(), ladder1.getMovementBoundsDown()) || isWithinLadder2(f_player.getBodyBounds(), ladder2.getMovementBoundsDown()) || 
           isWithinLadder2(f_player.getBodyBounds(), ladder3.getMovementBoundsDown()) || isWithinLadder2(f_player.getBodyBounds(), ladder4.getMovementBoundsDown()) ||
           isWithinLadder2(f_player.getBodyBounds(), ladder5.getMovementBoundsDown())) {
        	f_player.stopClimbingDown(true);
    		//System.out.println("Can't climb down ladder ");
    		}
        else {
		f_player.stopClimbingDown(false);  
        }
    
		
      
    }
    
    
    
    boolean isWithinLadder(Rectangle player, Rectangle ladder) {
        return player.x >= ladder.x &&
               player.x + player.width <= ladder.x + ladder.width &&
               player.y >= ladder.y &&
               player.y + player.height <= ladder.y + ladder.height;
    }
    
    boolean isWithinLadder2(Rectangle player, Rectangle ladder) {
        return player.x >= ladder.x &&
               player.x + player.width <= ladder.x + ladder.width &&
               player.y >= ladder.y &&
               player.y + player.height <= ladder.y + ladder.height - 14;
    }
    
    boolean isWithinLadder3(Circle barrel, Rectangle ladder) {
        // Checking if all edges of the circle are within the bounds of the rectangle
        return (barrel.x - (barrel.radius * 2) >= ladder.x) && // Left edge of circle within left edge of ladder
               (barrel.x + (barrel.radius * 2) <= ladder.x + ladder.width) && // Right edge of circle within right edge of ladder
               (barrel.y - barrel.radius >= ladder.y) && // Bottom edge of circle within bottom edge of ladder
               (barrel.y + barrel.radius <= ladder.y + ladder.height); // Top edge of circle within top edge of ladder
    }
    
    
    
    boolean isWithinLadderX(Rectangle player, Rectangle ladder) {
        return player.x >= ladder.x &&
               player.x + player.width <= ladder.x + ladder.width;
    }
    
    
    
    
    
    
    
    
    
    
    int newXuX = 150;
    float oldY;
    private void checkBarrelPlatformCollisions() {
    	//barrels type 1
        for(Barrel barrel : f_barrels) {
            Circle barrelBounds = barrel.getBounds(); // Get the circular bounds of the barrel

            for (Platform platform : f_platforms) {
                Rectangle platformBounds = platform.getUpperBounds(); // Assuming this gets the platform's rectangular bounds

                // Use Intersector.overlaps to check if the circle overlaps with the rectangle
                if (Intersector.overlaps(barrelBounds, platformBounds)) {
                	//System.out.println("Colliding with platform");
                	//System.out.println(barrel.getPosition().x);
                    barrel.checkCollisionWithGround(true);
                    barrel.jump(130);
                    
                    //barrel.launch(45, 100);;
                    
                    //barrel.moveX(newXuX);
                    break;
                }
                //if((position.x > leftBound + 2) && (position.x < rightBound - radius - 2)) {
                
                if((barrel.getPosition().x < 2) || (barrel.getPosition().x > 1200)){
                	System.out.println("Triggered");
                	barrel.setVelocityY(-300);
                	barrel.setVelocityX(-900);
                	
                }
            }
            barrel.checkCollisionWithGround(false);
        }
        
        //barrels type 2
        for (Barrel2 barrel : f_barrels2) {
            Circle barrelBounds = barrel.getBounds();
            boolean isOnPlatform = false;

            for (Platform platform : f_platforms) {
              Rectangle platformBounds = platform.getUpperBounds();
                if (Intersector.overlaps(barrelBounds, platformBounds)) {
                    //System.out.println("Colliding with platform");
                    barrel.moveX(barrel.getSpeed()); // Move the barrel if it is on a platform
                    isOnPlatform = true;
                    barrel.checkCollisionWithGround(isOnPlatform);
                    barrel.resetDirectionToggle(); // Reset the toggle since it's on a platform now
                    break;
                }
            }

            if (!isOnPlatform) {
                //System.out.println("Isn't on platform");
                barrel.checkCollisionWithGround(isOnPlatform);
                barrel.toggleDirection(); // This will only toggle once per airborne phase
            }
           
            	//int chance = random.nextInt(5) + 1;
      
            if(barrel.getChance() == 1) {
            	if(isWithinLadder3(barrel.getBounds(), ladder5.getBounds()) || isWithinLadder3(barrel.getBounds(), ladder3.getBounds()) || isWithinLadder3(barrel.getBounds(), ladder1.getBounds())) {
            		//System.out.println("Wihthin ladder");
            		//barrel.ladderGravity(true);
            		oldY = barrel.getPosition().y;
            		float newY = oldY - 10; //skip the platform
            		barrel.setVelocityX(0); //stop the x velocity
            		barrel.setVelocityY(400);
            		barrel.setPositionY(newY);
            		
            	}
            	
            }
            
            else if(barrel.getChance() == 2) {
            	if(isWithinLadder3(barrel.getBounds(), ladder3.getBounds()) || isWithinLadder3(barrel.getBounds(), ladder2.getBounds())) {
            		//System.out.println("Wihthin ladder");
            		//barrel.ladderGravity(true);
            		oldY = barrel.getPosition().y;
            		float newY = oldY - 10; //skip the platform
            		barrel.setVelocityX(0); //stop the x velocity
            		barrel.setVelocityY(400);
            		barrel.setPositionY(newY);
            		
            	}
            	
            	
            }
            
            else if(barrel.getChance() == 3) {
            	if(isWithinLadder3(barrel.getBounds(), ladder4.getBounds())) {
            		//System.out.println("Wihthin ladder");
            		//barrel.ladderGravity(true);
            		oldY = barrel.getPosition().y;
            		float newY = oldY - 10; //skip the platform
            		barrel.setVelocityX(0); //stop the x velocity
            		barrel.setVelocityY(400);
            		barrel.setPositionY(newY);
            		
            	}
            	
            	
            }
            
            
            	
            
        }
            //barrel.checkCollisionWithGround(false);
            //dispose barrel after reaching certain position
            
        
    }
    
    
    private void checkBarrelPlayerCollisions() {
        // Check collisions with Barrel type 1
        Iterator<Barrel2> barrel2Iterator = f_barrels2.iterator();
        while (barrel2Iterator.hasNext()) {
            Barrel2 barrel = barrel2Iterator.next();
            if (Intersector.overlaps(barrel.getBounds(), f_player.getShrinkedBounds())) {
                // Handle the collision: decrease player health, remove the barrel
                if(!shield) {
            	hit.play();
            	f_player.decreaseHealth(1); // Adjust the decrease value as needed
            	
                barrel2Iterator.remove();// Remove the barrel from the list
                }else{
                blocked.play(0.8f);
                f_player.decreaseHealth(1); // Adjust the decrease value as needed
                barrel2Iterator.remove();
                f_hitsLeft--;
                }
                //shakePlayer();
               
                	
                if(f_hitsLeft-1<0){
                	shield= false;
                	f_active = false;
                }
                if(f_player.getHealth() <3) {
                	hurt=true;
                }
                // Check player's health status
                if (f_player.getHealth() <= 0) {
                    //endGame(); // Implement this method based on your game's needs
                	f_endGame = true;
                	changeMusic("zehahaha_laugh.mp3");
                    return; // Exit the method if the player is removed
                }
            }
        }
    }
        private void checkBarrel2PlayerCollisions() {
            // Check collisions with Barrel type 1
            Iterator<Barrel> barrel1Iterator = f_barrels.iterator();
            while (barrel1Iterator.hasNext()) {
                Barrel barrel = barrel1Iterator.next();
                if (Intersector.overlaps(barrel.getBounds(), f_player.getShrinkedBounds())) {
                    // Handle the collision: decrease player health, remove the barrel
                	if(!shield) {
                		hit.play(0.8f);
                		f_player.decreaseHealth(1); // Adjust the decrease value as needed
                        barrel1Iterator.remove();	
                	}else{
                	blocked.play();
                	f_player.decreaseHealth(1); // Adjust the decrease value as needed
                    barrel1Iterator.remove(); // Remove the barrel from the list
                    	f_hitsLeft--;
                	}//shakePlayer();
                  if(f_hitsLeft-1<0){
                    	shield= false;
                    	f_active = false;
                    }
                    if(f_player.getHealth() <3) {
                    	hurt=true;
                    }
                    
                    // Check player's health status
                    if (f_player.getHealth() <= 0) {
                       // endGame(); // Implement this method based on your game's needs
                    	f_endGame=true;
                    	changeMusic("zehahaha_laugh.mp3");
                        return; // Exit the method if the player is removed
                    }
                }
            }
        // Separate loop for Barrel type 2 if needed
        // Iterate through f_barrels2 if you have different logic for Barrel2 objects

        // Other collision checks (e.g., platform collisions) can remain in their respective methods or loops
    }

    private void endGame() {
    	
    	//f_batch.begin();
    	f_batch.draw(f_gameOverTexture, 0, 20, worldWidth, worldHeight);
       // f_batch.end();
        Gdx.app.log("Game", "Game Over");

    }
    
    private void shakePlayer() {
    	System.out.println("SHAKINF");
    	 // Define the intensity of the shake (how far the player moves)
        float shakeIntensity = 300;

        // Calculate random offsets for the shake effect
        float offsetX = (float)Math.random() * shakeIntensity * 2 - shakeIntensity;
        //float offsetY = (float)Math.random() * shakeIntensity * 2 - shakeIntensity;

        // Temporarily adjust the player's position to simulate shaking
        // This assumes you have methods to get and set the player's position
        float originalX = f_player.getPositionX();
        float originalY = f_player.getPositionY();

        // Apply the shake effect by moving the player to a new position
        f_player.setPosition(originalX + offsetX, originalY);

        // Optionally, you might want to immediately return the player to the original position
        // after a very short delay, to make it look like a quick shake
        // This could be handled with a timer if your framework supports scheduling tasks
        // For simplicity, here's a conceptual way to reset the position:
        // (Note: Actual implementation may vary based on your game loop and timing mechanism)
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                f_player.setPosition(originalX, originalY);
            }
        }, 0.05f); // Reset position after 0.05 seconds
    }

    
    //Hunter work on this
    private void checkPowerUpCollisions() {
    	
    	for(powerUps power : f_powerUps) {
    		if(f_player.getBounds().overlaps(power.getBounds())&&power.numReturn()==1) {
    			teleport=true;
    			pTele.play();
    			if(f_usesLeft==0 && !f_tActive) {
        			f_usesLeft=1;
        			power.changeXPos(-3000);
        			power.updateBounds();
        		}
    			
    		}
    		if(f_player.getBounds().overlaps(power.getBounds())&&power.numReturn()==2) {
    			shield=true;
    			oldHealth = f_player.getHealth();
    			if(f_hitsLeft==0 && !f_active) {
        			f_hitsLeft=3;
        			f_active=true;
        			pShield.play(0.7f);
        			power.changeXPos(-3000);
        			power.updateBounds();
        		}
    			
    		}
    		if(teleport) {
    		 if (Gdx.input.isKeyJustPressed(Input.Keys.D)&&!(f_player.getPositionX()+ 200>=1250)) {
    			 System.out.println("LKOOLD");
    			 teleported.play(1.0f);
    			 f_player.setPosition(f_player.getPositionX()+ 200,f_player.getPositionY());
    			 f_usesLeft--;
    		 }
    		 if(Gdx.input.isKeyJustPressed(Input.Keys.A)&&!(f_player.getPositionX()- 200<=0)) {
    			 System.out.println("LKOOLA");
      			 teleported.play(1.0f);  			 
    			 f_player.setPosition(f_player.getPositionX()- 200,f_player.getPositionY());
    			 f_usesLeft--;
    		 }
    		 if(Gdx.input.isKeyJustPressed(Input.Keys.W)&&!(f_player.getPositionY()+200>=1250)) {
    			 System.out.println("LKOOLW");
    			 teleported.play(1.0f);
    			 f_player.setPosition(f_player.getPositionX(),f_player.getPositionY()+200);   			 
    			 f_usesLeft--;
    		 }
    		 if(Gdx.input.isKeyJustPressed(Input.Keys.S)&&!(f_player.getPositionY()-200<=50)) {
    			 System.out.println("LKOOLS");
    			 teleported.play(1.0f);
    			 f_player.setPosition(f_player.getPositionX(),f_player.getPositionY()- 200);
    			 f_usesLeft--;
    		 }
    	}
    		if((shield&&f_active)&&(f_hitsLeft-1<0&&(f_player.getHealth()<=3&&f_player.getHealth()>0))) {
    			shield=false;
    			f_active=false;
    			depleted.play(2.0f);
    		}
    		if(shield&&f_active) {
    			System.out.println(f_player.getHealth());
    			f_player.shielded();
    			f_active=false;
    		}


    		
    	if(f_usesLeft-1<0) {
    		teleport=false;
    		
    	}
    	
    	}
    	}
    
    
    //function to save the functions before disposing
    public void setOldVar() {
    	GameState gameState = new GameState();
         // Set the variables you want to save
         //gameState.score = currentScore;
         gameState.playerPositionX = f_player.getPositionX();
         gameState.playerPositionY = f_player.getPositionY();
         // ... more as needed
         
         GameUtils.saveGameState(gameState);
    }
    private void lifeSys(int oldHP) {
    	 int spacer = 170;
         int oldHealth = oldHP;
     for(int i = 0;f_player.getHealth() >i; i++) {
     	if(!hurt&&shield) {
     		if(i>2) {
     		f_batch.draw(f_shieldTexture, worldWidth-spacer, worldHeight-170, 250, 250);
     		spacer+=80;
     	}else {
     		f_batch.draw(f_lifeTexture, worldWidth-spacer, worldHeight-170, 250, 250);
     		spacer+=80;
     	}
     	}else if (hurt&&shield) {
     		if(i<oldHealth) {
     			f_batch.draw(f_lifeTexture, worldWidth-spacer, worldHeight-170, 250, 250);
         		spacer+=80;
     		}else {
     			f_batch.draw(f_shieldTexture, worldWidth-spacer, worldHeight-170, 250, 250);
         		spacer+=80;
     		}
     	}else if(hurt && !shield) {
     		if(i<=oldHealth+1) {
     			f_batch.draw(f_lifeTexture, worldWidth-spacer, worldHeight-170, 250, 250);
         		spacer+=80;
     		}
     	}else {
     		if(i<=3){
     		f_batch.draw(f_lifeTexture, worldWidth-spacer, worldHeight-170, 250, 250);
     		spacer+=80;
     		}else {
     			f_batch.draw(f_shieldTexture, worldWidth-spacer, worldHeight-170, 250, 250);
         		spacer+=80;
     		}
     	}
     }
    }
    private void changeMusic(String newMusicFilename) {
        if (f_BGM != null) {
            f_BGM.stop(); // Stop current music
            f_BGM.dispose(); // Dispose current music resource
        }
        
        // Load and play new music
        f_BGM = f_assetManager.get(newMusicFilename, Music.class);
        if (f_BGM == null) {
            f_assetManager.load(newMusicFilename, Music.class);
            f_assetManager.finishLoading();
            f_BGM = f_assetManager.get(newMusicFilename, Music.class);
        }
        f_BGM.setVolume(0.4f); // Set to your desired volume
        f_BGM.setLooping(true);
        f_BGM.play();
    }
 
    
    /**
     * Disposes game resources.
     */
    @Override
    public void dispose() {
        f_batch.dispose();
        f_player.dispose();
        f_BGM.dispose();
        blocked.dispose();
        teleported.dispose();
        hit.dispose();
        pShield.dispose();
        depleted.dispose();
        pTele.dispose();
        f_shieldTexture.dispose();
        f_lifeTexture.dispose();
        f_backgroundTexture.dispose();
        f_backgroundTexture.dispose();
        profiler.disable();
        f_blood.dispose();
        f_blood2.dispose();
        //setOldVar();
        //super.dispose();
        
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
