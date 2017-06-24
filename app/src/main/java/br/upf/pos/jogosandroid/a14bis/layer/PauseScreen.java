package br.upf.pos.jogosandroid.a14bis.layer;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.ccColor4B;

import br.upf.pos.jogosandroid.a14bis.delegate.ButtonDelegate;
import br.upf.pos.jogosandroid.a14bis.delegate.PauseDelegate;
import br.upf.pos.jogosandroid.a14bis.util.Assets;

import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenWidth;
import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenHeight;

/**
 * Created by backes on 6/14/17.
 */

public class PauseScreen extends CCLayer implements ButtonDelegate {

    private PauseDelegate delegate;

    private Button btnResume;
    private Button btnExit;

    private CCColorLayer background;

    public PauseScreen() {
        setIsTouchEnabled(true);

        background = CCColorLayer.node(ccColor4B.ccc4(0, 0, 0, 175),
                screenWidth(),
                screenHeight()
        );
        addChild(background);

        CCSprite logo = CCSprite.sprite(Assets.LOGO);
        logo.setPosition(
                screenWidth() / 2,
                screenHeight() - 130
        );
        addChild(logo);

        btnResume = new Button(Assets.PLAY);
        btnResume.setDelegate(this);
        btnResume.setPosition(
                screenWidth() / 2,
                screenHeight() - 250
        );
        addChild(btnResume);

        btnExit = new Button(Assets.EXIT);
        btnExit.setDelegate(this);
        btnExit.setPosition(
                screenWidth() / 2,
                screenHeight() - 300
        );
        addChild(btnExit);
    }

    public void setDelegate(PauseDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void buttonPress(Button button) {
        if (button.equals(btnResume)) {
            delegate.resumeGame();
            removeFromParentAndCleanup(true);
        }

        if (button.equals(btnExit)) {
            delegate.quitGame();
        }
    }

    @Override
    public void buttonRelease(Button button) {

    }

}
