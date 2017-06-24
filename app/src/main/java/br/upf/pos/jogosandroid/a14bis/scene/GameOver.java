package br.upf.pos.jogosandroid.a14bis.scene;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import br.upf.pos.jogosandroid.a14bis.delegate.ButtonDelegate;
import br.upf.pos.jogosandroid.a14bis.layer.Button;
import br.upf.pos.jogosandroid.a14bis.util.Assets;

import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenHeight;
import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenWidth;

/**
 * Created by backes on 6/11/17.
 */

public class GameOver extends CCScene implements ButtonDelegate {

    private CCSprite background;
    private CCSprite logo;

    private Button btnRestart;
    private Button btnExit;

    public GameOver() {
        createBackground();
        createLogo();

        btnRestart = new Button(Assets.PLAY);
        btnRestart.setDelegate(this);
        btnRestart.setPosition(screenWidth()/2f, 150);
        addChild(btnRestart);

        btnExit = new Button(Assets.EXIT);
        btnExit.setDelegate(this);
        btnExit.setPosition(screenWidth()/2f, 100);
        addChild(btnExit);
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
        logo = new CCSprite(Assets.GAMEOVER);
        logo.setPosition(
                screenWidth() / 2.0f,
                screenHeight() / 2.0f
        );
        addChild(logo);
    }

    @Override
    public void buttonPress(Button button) {
        if (button.equals(btnRestart)) {
            CCDirector.sharedDirector().replaceScene(
                    CCFadeTransition.transition(1.0f, new LevelScene())
            );
        }

        if (button.equals(btnExit)) {
            CCDirector.sharedDirector().replaceScene(
                    CCFadeTransition.transition(1.0f, new MenuScene())
            );
        }
    }

    @Override
    public void buttonRelease(Button button) {

    }
}
