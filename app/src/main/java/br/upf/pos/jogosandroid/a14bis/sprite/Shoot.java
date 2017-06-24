package br.upf.pos.jogosandroid.a14bis.sprite;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCSprite;

import br.upf.pos.jogosandroid.a14bis.R;
import br.upf.pos.jogosandroid.a14bis.delegate.ShootDelegate;
import br.upf.pos.jogosandroid.a14bis.util.Assets;
import br.upf.pos.jogosandroid.a14bis.util.GameStatus;
import br.upf.pos.jogosandroid.a14bis.util.Sound;

import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenHeight;

/**
 * Created by backes on 6/11/17.
 */

public class Shoot extends CCSprite {

    private ShootDelegate delegate;

    float x, y;
    private float speed;
    private String direction;

    public Shoot(float x, float y, int speed, String direction) {
        super(Assets.SHOOT);
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
        setPosition(x, y);
        schedule("update");

        Sound.playEffect(R.raw.shoot);
    }

    public void setDelegate(ShootDelegate delegate) {
        this.delegate = delegate;
    }

    public void update(float dt) {
        if (GameStatus.getInstance().isPlaying()) {
            if (direction == "left")
                x -= 1;
            if (direction == "right")
                x += 1;

            y += speed;
            setPosition(x, y);

            if (y > screenHeight()) {
                delegate.removeShoot(this);
                unschedule("update");
                removeMe();
            }
        }
    }

    public void explode() {
        delegate.removeShoot(this);
        unschedule("update");
        float dt = 0.2f;
        CCScaleBy a1 = CCScaleBy.action(dt, 2f);
        CCFadeOut a2 = CCFadeOut.action(dt);
        CCSpawn s1 = CCSpawn.actions(a1, a2);
        // Função a ser executada após efeito
        CCCallFunc c1 = CCCallFunc.action(this, "removeMe");
        // Roda efeito
        runAction(CCSequence.actions(s1, c1));
    }

    public void removeMe() {
        removeFromParentAndCleanup(true);
    }

}
