package br.upf.pos.jogosandroid.a14bis.scene;

import android.content.Context;
import android.os.Vibrator;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import br.upf.pos.jogosandroid.a14bis.util.Assets;

import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenHeight;
import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenWidth;

/**
 * Created by backes on 6/14/17.
 */

public class LevelTwo extends CCScene {

    private CCSprite background;

    public LevelTwo() {
        background = CCSprite.sprite(Assets.BACKGROUND);
        background.setPosition(
                screenWidth() / 2.0f,
                screenHeight() / 2.0f
        );
        addChild(background);

//        MeteorTwo meteorTwo = new MeteorTwo();
//        meteorTwo.setPosition(
//                screenWidth() / 2.0f,
//                screenHeight() / 2.0f
//        );
//        addChild(meteorTwo);

        CCSpriteSheet spriteSheet = CCSpriteSheet.spriteSheet("aeronave.png");
        addChild(spriteSheet);

        CCSprite sprite = CCSprite.sprite(spriteSheet.getTexture(), CGRect.make(0, 0, 180, 65));
        sprite.setPosition(
                screenWidth() / 2.0f,
                100
        );
        spriteSheet.addChild(sprite);

        CCAnimation animation = CCAnimation.animation("opa", 0.8f);

        CCSpriteFrame frame = CCSpriteFrame.frame(spriteSheet.getTexture(), CGRect.make(0, 0, 64, 63), CGPoint.ccp(0, 0));
        animation.addFrame(frame);
        frame = CCSpriteFrame.frame(spriteSheet.getTexture(), CGRect.make(0, 60, 64, 63), CGPoint.ccp(0, 0));
        animation.addFrame(frame);
        frame = CCSpriteFrame.frame(spriteSheet.getTexture(), CGRect.make(0, 116, 64, 63), CGPoint.ccp(0, 0));
        animation.addFrame(frame);
//
////        int frameCount = 0;
////        for (int y = 0; y < 4; y++) {
////            for (int x = 0; x < 5; x++) {
////                CCSpriteFrame frame = CCSpriteFrame.frame(spriteSheet.getTexture(), CGRect.make(x*65, y*65, 60, 60), CGPoint.ccp(5, 5));
////                animation.addFrame(frame);
////
////                frameCount++;
////
////                if (frameCount == 19)
////                    break;
////            }
////        }
//
        CCAnimate animationAction = CCAnimate.action(animation);
        CCRepeatForever repeat = CCRepeatForever.action(animationAction);
        sprite.runAction(repeat);

    }

    @Override
    public void onEnter() {
        super.onEnter();

        Vibrator v = (Vibrator) CCDirector.sharedDirector().getActivity().getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
    }

}
