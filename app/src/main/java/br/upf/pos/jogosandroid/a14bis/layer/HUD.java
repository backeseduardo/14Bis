package br.upf.pos.jogosandroid.a14bis.layer;

import android.util.Log;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.ccColor3B;

import br.upf.pos.jogosandroid.a14bis.delegate.ButtonDelegate;
import br.upf.pos.jogosandroid.a14bis.delegate.GunChooseDelegate;
import br.upf.pos.jogosandroid.a14bis.delegate.HUDDelegate;
import br.upf.pos.jogosandroid.a14bis.delegate.PauseDelegate;
import br.upf.pos.jogosandroid.a14bis.sprite.Ammunition;
import br.upf.pos.jogosandroid.a14bis.sprite.Player;
import br.upf.pos.jogosandroid.a14bis.util.Assets;
import br.upf.pos.jogosandroid.a14bis.util.GameStatus;

import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenWidth;
import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenHeight;

/**
 * Created by backes on 6/11/17.
 */

public class HUD extends CCLayer implements ButtonDelegate {

    private Button btnLeft;
    private Button btnRight;
    private Button btnShoot;
    private Button btnPause;

    private CCLabel scoreLabel;

    private CCSprite munitionSprite;
    private CCLabel munitionLabel;

    private Button gun1Sprite, gun2Sprite;

    private HUDDelegate delegate;
    private PauseDelegate pauseDelegate;
    private GunChooseDelegate gunChooseDelegate;

    public HUD() {
        setIsTouchEnabled(true);
        addButtons();

        scoreLabel = CCLabel.makeLabel("Score: "+String.valueOf(GameStatus.getInstance().getScore()), "UniSansBold", 28f);
        scoreLabel.setPosition(
                screenWidth()-70,
                screenHeight()-25
        );
        addChild(scoreLabel);

        munitionSprite = CCSprite.sprite(Assets.BULLET);
        munitionSprite.setScale(.3f);
        munitionSprite.setPosition(
                screenWidth() - 60,
                screenHeight() - 60
        );
        addChild(munitionSprite);

        munitionLabel = CCLabel.makeLabel("0", "UniSansBold", 14f);
        munitionLabel.setPosition(
                screenWidth() - 30,
                screenHeight() - 60
        );
        addChild(munitionLabel);
    }

    private void addButtons() {
        btnLeft = new Button(Assets.LEFTBUTTON);
        btnLeft.setDelegate(this);
        btnLeft.setPosition(
                40, 40
        );
        addChild(btnLeft);

        btnRight = new Button(Assets.RIGHTBUTTON);
        btnRight.setDelegate(this);
        btnRight.setPosition(
                100, 40
        );
        addChild(btnRight);

        btnShoot = new Button(Assets.SHOOTBUTTON);
        btnShoot.setDelegate(this);
        btnShoot.setPosition(
                280,
                40
        );
        addChild(btnShoot);

        btnPause = new Button(Assets.PAUSE);
        btnPause.setDelegate(this);
        btnPause.setPosition(
                25,
                screenHeight() - 20
        );
        addChild(btnPause);

        gun1Sprite = new Button(Assets.GUN1);
        gun1Sprite.setDelegate(this);
        gun1Sprite.setScale(.5f);
        gun1Sprite.setColor(ccColor3B.ccYELLOW);
        gun1Sprite.setPosition(
                screenWidth() - 35,
                screenHeight() - 100
        );
        addChild(gun1Sprite);

        gun2Sprite = new Button(Assets.GUN2);
        gun2Sprite.setDelegate(this);
        gun2Sprite.setScale(.7f);
        gun2Sprite.setPosition(
                screenWidth() - 50,
                screenHeight() - 150
        );
        gun2Sprite.setVisible(false);
        addChild(gun2Sprite);
    }

    public void setDelegate(HUDDelegate delegate) {
        this.delegate = delegate;
    }

    public void setPauseDelegate(PauseDelegate pauseDelegate) { this.pauseDelegate = pauseDelegate; }

    public void setGunChooseDelegate(GunChooseDelegate gunChooseDelegate) {
        this.gunChooseDelegate = gunChooseDelegate;
    }

    @Override
    public void buttonPress(Button button) {
        if (!isTouchEnabled())
            return;

        if (button.equals(btnLeft)) {
            delegate.startMovementLeft();
        }
        if (button.equals(btnRight)) {
            delegate.startMovementRight();
        }
        if (button.equals(btnShoot)) {
            delegate.shoot();
        }
        if (button.equals(btnPause)) {
            pauseDelegate.pauseGameAndShowLayer();
        }
        if (button.equals(gun1Sprite)) {
            gunChooseDelegate.selectGun(Ammunition.Type.GUN1);
        }
        if (button.equals(gun2Sprite)) {
            gunChooseDelegate.selectGun(Ammunition.Type.GUN2);
        }
    }

    @Override
    public void buttonRelease(Button button) {
        if (!isTouchEnabled())
            return;

        if (button.equals(btnLeft)) {
            delegate.stopMovementLeft();
        }
        if (button.equals(btnRight)) {
            delegate.stopMovementRight();
        }
//        if (button.equals(btnShoot)) {
//            Log.d("HUD", "shoot released");
//        }
    }

    public void increaseScore(int points) {
        GameStatus.getInstance().increaseScore(points);
        scoreLabel.setString("Score: "+String.valueOf(GameStatus.getInstance().getScore()));
    }

    public void updateMuniton(int munition) {
        munitionLabel.setString(String.valueOf(munition));
    }

    public void setGunVisible(Ammunition.Type gun, boolean visible) {
        if (gun == Ammunition.Type.GUN1)
            gun1Sprite.setVisible(visible);
        if (gun == Ammunition.Type.GUN2)
            gun2Sprite.setVisible(visible);
    }

    public void setGunSelected(Ammunition.Type gun) {
        if (gun == Ammunition.Type.GUN1 && gun1Sprite.getVisible()) {
            gun1Sprite.setColor(ccColor3B.ccYELLOW);
            gun2Sprite.setColor(ccColor3B.ccWHITE);
        }
        if (gun == Ammunition.Type.GUN2 && gun2Sprite.getVisible()) {
            gun2Sprite.setColor(ccColor3B.ccYELLOW);
            gun1Sprite.setColor(ccColor3B.ccWHITE);
        }
    }

}
