package br.upf.pos.jogosandroid.a14bis.layer;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.events.CCTouchHandler;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.ccColor3B;

import br.upf.pos.jogosandroid.a14bis.delegate.ButtonDelegate;

/**
 * Created by backes on 6/11/17.
 */

public class Button extends CCLayer {

    private CCSprite buttonSprite;

    private ButtonDelegate delegate;

    public Button(String img) {
        setIsTouchEnabled(true);
        buttonSprite = CCSprite.sprite(img);
        addChild(buttonSprite);
    }

    public void setDelegate(ButtonDelegate delegate) {
        this.delegate = delegate;
    }

    public void setColor(ccColor3B color) {
        buttonSprite.setColor(color);
    }

    @Override
    public void setScale(float s) {
        buttonSprite.setScale(s);
    }

    @Override
    protected void registerWithTouchDispatcher() {
        CCTouchDispatcher.sharedDispatcher().addTargetedDelegate(this, 0, false);
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        int index = MotionEventCompat.getActionIndex(event);
        float x = MotionEventCompat.getAxisValue(event, MotionEventCompat.AXIS_X, index);
        float y = MotionEventCompat.getAxisValue(event, MotionEventCompat.AXIS_Y, index);

        CGPoint touchLocation = CGPoint.make(x, y);
        touchLocation = CCDirector.sharedDirector().convertToGL(touchLocation);
        touchLocation = convertToNodeSpace(touchLocation);

        if (CGRect.containsPoint(buttonSprite.getBoundingBox(), touchLocation))
            delegate.buttonPress(this);

        return super.ccTouchesBegan(event);
    }

    @Override
    public boolean ccTouchesEnded(MotionEvent event) {
        int index = MotionEventCompat.getActionIndex(event);
        float x = MotionEventCompat.getAxisValue(event, MotionEventCompat.AXIS_X, index);
        float y = MotionEventCompat.getAxisValue(event, MotionEventCompat.AXIS_Y, index);

        CGPoint touchLocation = CGPoint.make(x, y);
        touchLocation = CCDirector.sharedDirector().convertToGL(touchLocation);
        touchLocation = convertToNodeSpace(touchLocation);

        if (CGRect.containsPoint(buttonSprite.getBoundingBox(), touchLocation))
            delegate.buttonRelease(this);

        return super.ccTouchesEnded(event);
    }
}
