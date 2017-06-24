package br.upf.pos.jogosandroid.a14bis.scene;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import br.upf.pos.jogosandroid.a14bis.delegate.ButtonDelegate;
import br.upf.pos.jogosandroid.a14bis.layer.Button;
import br.upf.pos.jogosandroid.a14bis.layer.HUD;
import br.upf.pos.jogosandroid.a14bis.sprite.Ammunition;
import br.upf.pos.jogosandroid.a14bis.util.Assets;
import br.upf.pos.jogosandroid.a14bis.util.ScoreUtil;
import br.upf.pos.jogosandroid.a14bis.util.Sound;

import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenHeight;
import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenWidth;

/**
 * Created by backes on 6/22/17.
 */

public class HighScoreScene extends CCScene implements ButtonDelegate {

    private CCSprite background;
    private CCLabel lblHighScore;

    public HighScoreScene() {
        background = CCSprite.sprite(Assets.BACKGROUND);
        background.setPosition(
                screenWidth() / 2,
                screenHeight() / 2
        );
        addChild(background);

        CCSprite logo = new CCSprite(Assets.LOGO);
        logo.setPosition(
                screenWidth() / 2.0f,
                screenHeight() - 100
        );
        addChild(logo);

        CCLabel label = CCLabel.makeLabel("Highscore", "UniSansBold", 28f);
        label.setPosition(
                screenWidth() / 2,
                screenHeight() - 220
        );
        addChild(label);

        lblHighScore = CCLabel.makeLabel(" ", "UniSansBold", 36f);
        lblHighScore.setPosition(
                screenWidth() / 2,
                screenHeight() - 270
        );
        addChild(lblHighScore);

        Button btnVoltar = new Button(Assets.EXIT);
        btnVoltar.setDelegate(this);
        btnVoltar.setPosition(
                screenWidth() / 2,
                150
        );
        addChild(btnVoltar);
    }

    @Override
    public void onEnter() {
        super.onEnter();

        int highscore = ScoreUtil.getInstance().getHighScore();
        lblHighScore.setString(String.valueOf(highscore));
    }

    @Override
    public void buttonPress(Button button) {
        CCDirector.sharedDirector().replaceScene(
                CCFadeTransition.transition(1.0f, new MenuScene())
        );
    }

    @Override
    public void buttonRelease(Button button) {

    }
}
