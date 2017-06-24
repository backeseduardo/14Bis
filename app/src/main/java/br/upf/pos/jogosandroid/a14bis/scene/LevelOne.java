package br.upf.pos.jogosandroid.a14bis.scene;

import android.content.Context;
import android.os.Vibrator;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGRect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.upf.pos.jogosandroid.a14bis.R;
import br.upf.pos.jogosandroid.a14bis.delegate.AmmunitionDelegate;
import br.upf.pos.jogosandroid.a14bis.delegate.ButtonDelegate;
import br.upf.pos.jogosandroid.a14bis.delegate.GunChooseDelegate;
import br.upf.pos.jogosandroid.a14bis.delegate.HUDDelegate;
import br.upf.pos.jogosandroid.a14bis.delegate.MeteorDelegate;
import br.upf.pos.jogosandroid.a14bis.delegate.PauseDelegate;
import br.upf.pos.jogosandroid.a14bis.delegate.ShootDelegate;
import br.upf.pos.jogosandroid.a14bis.layer.AmmunitionEngine;
import br.upf.pos.jogosandroid.a14bis.layer.Button;
import br.upf.pos.jogosandroid.a14bis.layer.HUD;
import br.upf.pos.jogosandroid.a14bis.layer.MeteorsEngine;
import br.upf.pos.jogosandroid.a14bis.layer.PauseScreen;
import br.upf.pos.jogosandroid.a14bis.sprite.Ammunition;
import br.upf.pos.jogosandroid.a14bis.sprite.Meteor;
import br.upf.pos.jogosandroid.a14bis.sprite.Player;
import br.upf.pos.jogosandroid.a14bis.sprite.Shoot;
import br.upf.pos.jogosandroid.a14bis.util.Assets;
import br.upf.pos.jogosandroid.a14bis.util.GameStatus;
import br.upf.pos.jogosandroid.a14bis.util.ScoreUtil;
import br.upf.pos.jogosandroid.a14bis.util.Sound;

import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenHeight;
import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenWidth;

/**
 * Created by backes on 6/11/17.
 */

public class LevelOne extends CCScene
        implements HUDDelegate, MeteorDelegate, ShootDelegate, PauseDelegate, AmmunitionDelegate, GunChooseDelegate {

    public final String TAG = "MenuScene";

    private CCSprite background;
    private HUD hud;

    private CCLayer objectsLayer;

    private Player player;
    private List<Player> playersArray;
    private String playerMovement = "STOPPED";

    private List<Shoot> shootsArray;

    private MeteorsEngine meteorsEngine;
    private List<Meteor> meteorsArray;

    private AmmunitionEngine ammunitionEngine;
    private List<Ammunition> ammunitionArray;

    private PauseScreen pauseScreen;

    private Vibrator vibrator;

    public LevelOne() {
        GameStatus.getInstance().setScore(0);

        createBackground();
        createGameObjects();

        createHUD();

        vibrator = (Vibrator) CCDirector.sharedDirector().getActivity().getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void createBackground() {
        background = CCSprite.sprite(Assets.BACKGROUND);
        background.setPosition(
                screenWidth() / 2.0f,
                screenHeight() / 2.0f
        );
        addChild(background);
    }

    private void createHUD() {
        hud = new HUD();
        hud.setDelegate(this);
        hud.setPauseDelegate(this);
        hud.setGunChooseDelegate(this);
        addChild(hud);

        player.setHud(hud);
    }

    private void createGameObjects() {
        objectsLayer = CCLayer.node();
        addChild(objectsLayer);

        playersArray = new ArrayList<>();
        shootsArray = new ArrayList<>();
        meteorsArray = new ArrayList<>();
        ammunitionArray = new ArrayList<>();

        player = new Player();
        player.setDelegate(this);
        playersArray.add(player);
        objectsLayer.addChild(player);
    }

    @Override
    public void onEnter() {
        super.onEnter();

        GameStatus.getInstance().setIsPlaying(true);

        schedule("update");

        meteorsEngine = new MeteorsEngine();
        meteorsEngine.setDelegate(this);
        addChild(meteorsEngine);

        ammunitionEngine = new AmmunitionEngine();
        ammunitionEngine.setDelegate(this);
        addChild(ammunitionEngine);
    }

    @Override
    public void startMovementLeft() {
        playerMovement = "LEFT";
        player.lean(Player.PlayerLean.LEFT);
    }

    @Override
    public void stopMovementLeft() {
        playerMovement = "STOPPPED";
        player.lean(Player.PlayerLean.NONE);
    }

    @Override
    public void startMovementRight() {
        playerMovement = "RIGHT";
        player.lean(Player.PlayerLean.RIGHT);
    }

    @Override
    public void stopMovementRight() {
        playerMovement = "STOPPPED";
        player.lean(Player.PlayerLean.NONE);
    }

    @Override
    public boolean shoot() {
        player.shoot();
        return true;
    }

    @Override
    public void createMeteor(Meteor meteor) {
        objectsLayer.addChild(meteor);
        meteor.setDelegate(this);
        meteor.start();
        meteorsArray.add(meteor);
    }

    @Override
    public void removeMeteor(Meteor meteor) {
        meteorsArray.remove(meteor);
        objectsLayer.removeChild(meteor, true);
    }

    @Override
    public void createShoot(Shoot shoot) {
        objectsLayer.addChild(shoot);
        shoot.setDelegate(this);
        shootsArray.add(shoot);
    }

    @Override
    public void removeShoot(Shoot shoot) {
        shootsArray.remove(shoot);
    }

    public void update(float dt) {
        checkRadiusHitsOfArray(meteorsArray, shootsArray, this, "meteorHit");
        checkRadiusHitsOfArray(meteorsArray, playersArray, this, "playerHit");
        checkRadiusHitsOfArray(ammunitionArray, playersArray, this, "ammunationHit");

        if (playerMovement.equals("RIGHT")) {
            player.moveRight();
        }
        if (playerMovement.equals("LEFT")) {
            player.moveLeft();
        }
    }

    public CGRect getBoarders(CCSprite object) {
        CGRect rect = object.getBoundingBox();
        return rect;
    }

    private boolean checkRadiusHitsOfArray(List<? extends CCSprite> array1,
                                           List<? extends CCSprite> array2,
                                           LevelOne levelOne,
                                           String hit) {
        boolean result = false;
        for (int i = 0; i < array1.size(); i++) {
            // Pega objeto do primeiro array
            CGRect rect1 = getBoarders(array1.get(i));
            for (int j = 0; j < array2.size(); j++) {
                // Pega objeto do segundo array
                CGRect rect2 = getBoarders(array2.get(j));
                // Verifica colisão
                if (CGRect.intersects(rect1, rect2)) {
                    result = true;

                    Method method;
                    try {
                        method = LevelOne.class.getMethod(hit, CCSprite.class, CCSprite.class);
                        method.invoke(levelOne, array1.get(i), array2.get(j));
                    } catch (SecurityException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchMethodException e1) {
                        e1.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    public void meteorHit(CCSprite meteor, CCSprite shoot) {
        ((Meteor) meteor).shooted();
        ((Shoot) shoot).explode();

        if (((Meteor) meteor).exploded()) {
            hud.increaseScore(((Meteor) meteor).getLevel());

//            if (hud.getScore() > 2) {
//                CCDirector.sharedDirector().replaceScene(
//                        CCFadeTransition.transition(1.0f, new LevelTwo())
//                );
//            }
        }
    }

    public void playerHit(CCSprite meteor, CCSprite player) {
        ((Meteor) meteor).shooted();
        ((Player) player).explode();

        matchEnd();
    }

    public void ammunationHit(CCSprite ammunition, CCSprite player) {
        ((Ammunition) ammunition).removeMe();
        ((Player) player).addMunition(((Ammunition) ammunition));
        ((Player) player).updateHud();

        hud.setGunVisible(((Ammunition) ammunition).getType(), true);
        vibrator.vibrate(500);
    }

    private void pauseGame() {
        if (GameStatus.getInstance().isPlaying()) {
            GameStatus.getInstance().setIsPlaying(false);
        }
    }

    private void matchEnd() {
        vibrator.vibrate(1500);
        Sound.stopBackgroundMusic();
        Sound.playEffect(R.raw.over);

        int highscore = GameStatus.getInstance().getScore();
        boolean gameover = true;

        if (highscore > ScoreUtil.getInstance().getHighScore()) {
            gameover = false;
        }

        ScoreUtil.getInstance().setHighScore(
                GameStatus.getInstance().getScore()
        );

        if (gameover) {
            CCDirector.sharedDirector().replaceScene(
                    CCFadeTransition.transition(1.0f, new GameOver())
            );
        } else {
            CCDirector.sharedDirector().replaceScene(
                    CCFadeTransition.transition(1.0f, new NewHighscoreScene())
            );
        }
    }

    @Override
    public void resumeGame() {
        if (!GameStatus.getInstance().isPlaying()) {
            pauseScreen = null;
            GameStatus.getInstance().setIsPlaying(true);
        }
    }

    @Override
    public void quitGame() {
        Sound.stopBackgroundMusic();
        CCDirector.sharedDirector().replaceScene(new MenuScene());
    }

    @Override
    public void pauseGameAndShowLayer() {
        if (GameStatus.getInstance().isPlaying()) {
            pauseGame();
        }
        if (!GameStatus.getInstance().isPlaying() &&
                pauseScreen == null) {
            pauseScreen = new PauseScreen();
            pauseScreen.setDelegate(this);
            addChild(pauseScreen);
        }
    }

    @Override
    public void createAmmunition(Ammunition ammunition) {
        objectsLayer.addChild(ammunition);
        ammunition.setDelegate(this);
        ammunition.start();
        ammunitionArray.add(ammunition);
    }

    @Override
    public void removeAmmunition(Ammunition ammunition) {
        ammunitionArray.remove(ammunition);
        objectsLayer.removeChild(ammunition, true);
    }

    @Override
    public void selectGun(Ammunition.Type gun) {
        player.setSelectedWeapon(gun);
        hud.setGunSelected(gun);
    }
}