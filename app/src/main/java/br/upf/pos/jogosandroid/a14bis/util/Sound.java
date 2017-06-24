package br.upf.pos.jogosandroid.a14bis.util;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;

import br.upf.pos.jogosandroid.a14bis.R;

/**
 * Created by backes on 6/12/17.
 */

public class Sound {

    public static void preloadSounds() {
        SoundEngine.sharedEngine().preloadSound(
                CCDirector.sharedDirector().getActivity(),
                R.raw.music
        );
        SoundEngine.sharedEngine().preloadEffect(
                CCDirector.sharedDirector().getActivity(),
                R.raw.shoot
        );
        SoundEngine.sharedEngine().preloadEffect(
                CCDirector.sharedDirector().getActivity(),
                R.raw.bang
        );
        SoundEngine.sharedEngine().preloadEffect(
                CCDirector.sharedDirector().getActivity(),
                R.raw.over
        );
    }

    public static void playBackgroundMusic() {
        SoundEngine.sharedEngine().playSound(
                CCDirector.sharedDirector().getActivity(),
                R.raw.music,
                true
        );
    }

    public static void stopBackgroundMusic() {
        SoundEngine.sharedEngine().pauseSound();
    }

    public static void playEffect(int resId) {
        SoundEngine.sharedEngine().playEffect(
                CCDirector.sharedDirector().getActivity(),
                resId
        );
    }

}
