package br.upf.pos.jogosandroid.a14bis.layer;

import org.cocos2d.layers.CCLayer;

import java.util.Random;

import br.upf.pos.jogosandroid.a14bis.delegate.AmmunitionDelegate;
import br.upf.pos.jogosandroid.a14bis.sprite.Ammunition;
import br.upf.pos.jogosandroid.a14bis.util.Assets;
import br.upf.pos.jogosandroid.a14bis.util.GameStatus;

/**
 * Created by backes on 6/17/17.
 */

public class AmmunitionEngine extends CCLayer {

    private AmmunitionDelegate delegate;

    public AmmunitionEngine() {
        schedule("bonusEngine", 0.1f);
    }

    public void setDelegate(AmmunitionDelegate delegate) {
        this.delegate = delegate;
    }

    public void bonusEngine(float dt) {
        if (GameStatus.getInstance().isPlaying()) {
            if (new Random().nextInt(50) == 0) {
                delegate.createAmmunition(new Ammunition(Assets.BULLET, Ammunition.Type.BULLET));
            }
            if (new Random().nextInt(100) == 0) {
                delegate.createAmmunition(new Ammunition(Assets.GUN1, Ammunition.Type.GUN1));
            }
            if (new Random().nextInt(200) == 0) {
                delegate.createAmmunition(new Ammunition(Assets.GUN2, Ammunition.Type.GUN2));
            }
        }
    }

}
