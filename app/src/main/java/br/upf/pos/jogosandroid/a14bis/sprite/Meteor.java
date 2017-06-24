package br.upf.pos.jogosandroid.a14bis.sprite;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.ccColor3B;

import java.util.Random;

import br.upf.pos.jogosandroid.a14bis.R;
import br.upf.pos.jogosandroid.a14bis.delegate.MeteorDelegate;
import br.upf.pos.jogosandroid.a14bis.util.Assets;
import br.upf.pos.jogosandroid.a14bis.util.GameStatus;
import br.upf.pos.jogosandroid.a14bis.util.Sound;

import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenWidth;
import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenHeight;

/**
 * Created by backes on 6/11/17.
 */

public class Meteor extends CCSprite {

    private MeteorDelegate delegate;

    private float x, y;
    private int speed;
    private int resistence;
    private int level;

    public Meteor(String img, int level) {
        super(img);

        if (level == 1) {
            resistence = 1;
            speed = 1;
        } else if (level == 2) {
            resistence = 3;
            speed = 1;
            setColor(ccColor3B.ccYELLOW);
        } else if (level == 3) {
            resistence = 5;
            speed = 2;
            setColor(ccColor3B.ccRED);
        }

        this.level = level;

        x = new Random().nextInt(Math.round(screenWidth()));
        y = screenHeight();
    }

    public void setDelegate(MeteorDelegate delegate) {
        this.delegate = delegate;
    }

    public void start() {
        schedule("update");
    }

    public void update(float dt) {
        if (GameStatus.getInstance().isPlaying()) {
            y -= speed;
            setPosition(x, y);

            if (y < 20) {
                explode();
            }
        }
    }

    public boolean exploded() {
        return resistence == 0;
    }

    public void shooted() {
        resistence -= 1;

        Sound.playEffect(R.raw.bang);

        if (resistence == 0)
            explode();
    }

    public void explode() {
        delegate.removeMeteor(this);
        unschedule("update");

        float dt = .2f;
        CCScaleBy a1 = CCScaleBy.action(dt, .5f);
        CCFadeOut a2 = CCFadeOut.action(dt);
        CCSpawn s1 = CCSpawn.actions(a1, a2);
        CCCallFunc c1 = CCCallFunc.action(this, "removeMe");
        runAction(CCSequence.actions(s1, c1));
    }

    public void removeMe() {
        removeFromParentAndCleanup(true);
    }

    public int getLevel() {
        return level;
    }

}
