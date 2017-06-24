package br.upf.pos.jogosandroid.a14bis.layer;

import org.cocos2d.layers.CCLayer;

import java.util.Random;

import br.upf.pos.jogosandroid.a14bis.delegate.MeteorDelegate;
import br.upf.pos.jogosandroid.a14bis.sprite.Meteor;
import br.upf.pos.jogosandroid.a14bis.util.Assets;
import br.upf.pos.jogosandroid.a14bis.util.GameStatus;

/**
 * Created by backes on 6/11/17.
 */

public class MeteorsEngine extends CCLayer {

    private MeteorDelegate delegate;

    public MeteorsEngine() {
        schedule("meteorsEngine", 0.1f);
    }

    public void setDelegate(MeteorDelegate delegate) {
        this.delegate = delegate;
    }

    public void meteorsEngine(float dt) {
        if (GameStatus.getInstance().isPlaying()) {

            if (GameStatus.getInstance().getScore() > 15) {
                if (new Random().nextInt(30) == 0) {
                    delegate.createMeteor(new Meteor(Assets.METEOR, 3));
                }
            }

            if (GameStatus.getInstance().getScore() > 5) {
                if (new Random().nextInt(25) == 0) {
                    delegate.createMeteor(new Meteor(Assets.METEOR, 2));
                }
            }

            if (new Random().nextInt(20) == 0) {
                delegate.createMeteor(new Meteor(Assets.METEOR, 1));
            }

        }
    }

}
