package br.upf.pos.jogosandroid.a14bis.scene;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import br.upf.pos.jogosandroid.a14bis.delegate.ButtonDelegate;
import br.upf.pos.jogosandroid.a14bis.layer.Button;
import br.upf.pos.jogosandroid.a14bis.util.Assets;
import br.upf.pos.jogosandroid.a14bis.util.Sound;

import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenHeight;
import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenWidth;

/**
 * Created by backes on 6/11/17.
 */

public class MenuScene extends CCScene implements ButtonDelegate {

    public final String TAG = "MenuScene";

    private CCSprite background;
    private CCSprite logo;

    private Button btnPlay;
    private Button btnHighScore;
    private Button btnHelp;
    private Button btnSound;
    private Button btnExit;

    private boolean soundEnabled = true;

    public MenuScene() {
        preloadCache();
        createBackground();
        createLogo();
        createButtons();

        Sound.playBackgroundMusic();
    }

    private void preloadCache() {
        Sound.preloadSounds();
    }

    private void createBackground() {
        background = new CCSprite(Assets.BACKGROUND);
        background.setPosition(
                screenWidth() / 2.0f,
                screenHeight() / 2.0f
        );
        addChild(background);
    }

    private void createLogo() {
        logo = new CCSprite(Assets.LOGO);
        logo.setPosition(
                screenWidth() / 2.0f,
                screenHeight() - 100
        );
        addChild(logo);
    }

    private void createButtons() {
        btnPlay = new Button(Assets.PLAY);
        btnPlay.setDelegate(this);
        btnPlay.setPosition(
                screenWidth() / 2.0f,
                screenHeight() - 220
        );
        addChild(btnPlay);

        btnHighScore = new Button(Assets.HIGHSCORE);
        btnHighScore.setDelegate(this);
        btnHighScore.setPosition(
                screenWidth() / 2.0f,
                screenHeight() - 270
        );
        addChild(btnHighScore);

        btnHelp = new Button(Assets.HELP);
        btnHelp.setDelegate(this);
        btnHelp.setPosition(
                screenWidth() / 2.0f,
                screenHeight() - 320
        );
        addChild(btnHelp);

        btnExit = new Button(Assets.EXIT);
        btnExit.setDelegate(this);
        btnExit.setPosition(
                screenWidth() / 2.0f,
                screenHeight() - 370
        );
        addChild(btnExit);

        btnSound = new Button(Assets.SOUND);
        btnSound.setDelegate(this);
        btnSound.setPosition(
                50,
                50
        );
        addChild(btnSound);
    }

    @Override
    public void buttonPress(Button button) {
        if (button.equals(btnPlay)) {
            CCDirector.sharedDirector().replaceScene(
                    CCFadeTransition.transition(1.0f, new LevelScene())
            );
        } else if (button.equals(btnHighScore)) {
            CCDirector.sharedDirector().replaceScene(
                    CCFadeTransition.transition(1.0f, new HighScoreScene())
            );
        } else if (button.equals(btnHelp)) {
            CCDirector.sharedDirector().replaceScene(
                    CCFadeTransition.transition(1.0f, new HelpScene())
            );
        } else if (button.equals(btnExit)) {
            CCDirector.sharedDirector().end();
            CCDirector.sharedDirector().getActivity().finish();
        } else if (button.equals(btnSound)) {
            soundEnabled = !soundEnabled;

            if (soundEnabled) {
                Sound.playBackgroundMusic();
            } else {
                Sound.stopBackgroundMusic();
            }
        }

    }

    @Override
    public void buttonRelease(Button button) {

    }
}
