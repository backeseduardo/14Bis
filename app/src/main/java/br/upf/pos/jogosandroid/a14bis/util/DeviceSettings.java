package br.upf.pos.jogosandroid.a14bis.util;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

/**
 * Created by backes on 6/11/17.
 */

public class DeviceSettings {

    public static CGPoint screenResolution(CGPoint cgPoint) {
        return cgPoint;
    }

    public static float screenWidth() {
        return winSize().getWidth();
    }

    public static float screenHeight() {
        return winSize().getHeight();
    }

    public static CGSize winSize() {
        return CCDirector.sharedDirector().winSize();
    }

}
