package br.upf.pos.jogosandroid.a14bis.scene;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import br.upf.pos.jogosandroid.a14bis.delegate.ButtonDelegate;
import br.upf.pos.jogosandroid.a14bis.layer.Button;
import br.upf.pos.jogosandroid.a14bis.layer.HUD;
import br.upf.pos.jogosandroid.a14bis.sprite.Ammunition;
import br.upf.pos.jogosandroid.a14bis.util.Assets;
import br.upf.pos.jogosandroid.a14bis.util.Sound;

import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenHeight;
import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenWidth;

/**
 * Created by backes on 6/22/17.
 */

public class HelpScene extends CCScene implements ButtonDelegate {

    private CCSprite background;
    private HUD hud;

    public HelpScene() {
        background = CCSprite.sprite(Assets.BACKGROUND);
        background.setPosition(
                screenWidth() / 2,
                screenHeight() / 2
        );
        addChild(background);

        hud = new HUD();
        hud.setIsTouchEnabled(false);
        hud.setGunVisible(Ammunition.Type.GUN2, true);
        addChild(hud);

        Button btnVoltar = new Button(Assets.EXIT);
        btnVoltar.setDelegate(this);
        btnVoltar.setPosition(
                screenWidth() / 2,
                150
        );
        addChild(btnVoltar);

        createLabels();
    }

    @Override
    public void onEnter() {
        super.onEnter();

        Sound.stopBackgroundMusic();
    }

    public void createLabels() {
        CCLabel label1 = CCLabel.makeLabel("<-- Para pausar o jogo", "UniSansBold", 12f);
        label1.setPosition(
                110,
                screenHeight() - 30
        );
        addChild(label1);

        CCLabel label2 = CCLabel.makeLabel("Munição disponível -->", "UniSansBold", 12f);
        label2.setPosition(
                screenWidth() - 130,
                screenHeight() - 60
        );
        addChild(label2);

        CCLabel label3 = CCLabel.makeLabel("Armas disponíveis -->", "UniSansBold", 12f);
        label3.setPosition(
                screenWidth() - 120,
                screenHeight() - 95
        );
        addChild(label3);

        CCLabel label4 = CCLabel.makeLabel("Toque em cima você pode seleciona-la -->", "UniSansBold", 12f);
        label4.setPosition(
                120,
                screenHeight() - 150
        );
        addChild(label4);
    }

    @Override
    public void buttonPress(Button button) {
        CCDirector.sharedDirector().replaceScene(
                CCFadeTransition.transition(1.0f, new MenuScene())
        );
    }

    @Override
    public void buttonRelease(Button button) {

    }
}
