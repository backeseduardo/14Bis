package br.upf.pos.jogosandroid.a14bis.sprite;

import org.cocos2d.nodes.CCSprite;

import java.util.Random;

import br.upf.pos.jogosandroid.a14bis.delegate.AmmunitionDelegate;
import br.upf.pos.jogosandroid.a14bis.util.GameStatus;

import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenHeight;
import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenWidth;

/**
 * Created by backes on 6/17/17.
 */

public class Ammunition extends CCSprite {

    public enum Type {
        BULLET,
        GUN1,
        GUN2
    }

    private AmmunitionDelegate delegate;

    private float x, y;
    private float speed;
    private Type type;

    public Ammunition(String img, Type type) {
        super(img);

        x = new Random().nextInt(Math.round(screenWidth()));
        y = screenHeight();

        this.type = type;

        switch (type) {
            case BULLET:
                speed = 2;
                break;

            default:
                speed = 3;
                break;
        }

        setScale(.3f);
    }

    public void setDelegate(AmmunitionDelegate delegate) {
        this.delegate = delegate;
    }

    public Type getType() {
        return type;
    }

    public void start() {
        schedule("update");
    }

    public void update(float dt) {
        if (GameStatus.getInstance().isPlaying()) {
            y -= speed;
            setPosition(x, y);

            if (y < 20) {
                removeMe();
            }
        }
    }

    public void removeMe() {
        delegate.removeAmmunition(this);
        unschedule("update");

        removeFromParentAndCleanup(true);
    }

}
