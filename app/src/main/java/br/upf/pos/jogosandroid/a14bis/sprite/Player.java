package br.upf.pos.jogosandroid.a14bis.sprite;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import java.util.ArrayList;
import java.util.List;

import br.upf.pos.jogosandroid.a14bis.R;
import br.upf.pos.jogosandroid.a14bis.delegate.ShootDelegate;
import br.upf.pos.jogosandroid.a14bis.layer.HUD;
import br.upf.pos.jogosandroid.a14bis.scene.GameOver;
import br.upf.pos.jogosandroid.a14bis.util.Assets;
import br.upf.pos.jogosandroid.a14bis.util.GameStatus;
import br.upf.pos.jogosandroid.a14bis.util.Sound;

import static br.upf.pos.jogosandroid.a14bis.util.DeviceSettings.screenWidth;

/**
 * Created by backes on 6/11/17.
 */

public class Player extends CCSprite {

    public enum PlayerLean {
        LEFT,
        RIGHT,
        NONE
    }

    private ShootDelegate delegate;

    private HUD hud;
    private int munition;
    private Ammunition.Type[] weapons;
    private Ammunition.Type selectedWeapon;

    float positionX = screenWidth() / 2;
    float positionY = 125;

    CCSprite spriteCenter, spriteLeft, spriteRight;

    public Player() {
        super(Assets.NAVE);

        spriteCenter = sprite(Assets.NAVE);
        spriteLeft = sprite(Assets.NAVELEFT);
        spriteRight = sprite(Assets.NAVERIGHT);

        weapons = new Ammunition.Type[2];
        weapons[0] = Ammunition.Type.GUN1;
        selectedWeapon = weapons[0];

        munition = 100;

        setPosition(positionX, positionY);
    }

    public void setDelegate(ShootDelegate delegate) {
        this.delegate = delegate;
    }

    public void setHud(HUD hud) {
        this.hud = hud;
        hud.updateMuniton(munition);
    }

    public void setSelectedWeapon(Ammunition.Type selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
//        hud.setSelectedGun(selectedWeapon);

    }

    public void updateHud() {
        hud.updateMuniton(munition);
    }

    public void addMunition(Ammunition ammunition) {
        munition += 10;

        switch (ammunition.getType()) {
            case GUN1:
//                hud.setGunVisible(ammunition.getType(), true);
                weapons[0] = Ammunition.Type.GUN1;
                break;

            case GUN2:
//                hud.setGunVisible(ammunition.getType(), true);
                weapons[1] = Ammunition.Type.GUN2;
                break;
        }
    }

    public void moveLeft() {
        if (GameStatus.getInstance().isPlaying()) {
            if (positionX > 30) {
                positionX -= 2;
            }
            setPosition(positionX, positionY);
        }
    }

    public void moveRight() {
        if (GameStatus.getInstance().isPlaying()) {
            if (positionX < screenWidth() - 30) {
                positionX += 2;
            }
            setPosition(positionX, positionY);
        }
    }

    public void lean(PlayerLean lean) {
        switch (lean) {
            case LEFT:
                setTexture(spriteLeft.getTexture());
                setTextureRect(spriteLeft.getTextureRect());
                break;

            case RIGHT:
                setTexture(spriteRight.getTexture());
                setTextureRect(spriteRight.getTextureRect());
                break;

            default:
                setTexture(spriteCenter.getTexture());
                setTextureRect(spriteCenter.getTextureRect());
        }
    }

    public void shoot() {
        if (GameStatus.getInstance().isPlaying()) {
            if (munition > 0) {
                switch (selectedWeapon) {
                    case GUN1:
                        delegate.createShoot(new Shoot(positionX, positionY, 2, null));
                        munition--;
                        break;

                    case GUN2:
                        if (munition > 0) {
                            delegate.createShoot(new Shoot(positionX, positionY, 3, null));
                            munition--;
                        }
                        if (munition > 0) {
                            delegate.createShoot(new Shoot(positionX, positionY, 3, "left"));
                            munition--;
                        }
                        if (munition > 0) {
                            delegate.createShoot(new Shoot(positionX, positionY, 3, "right"));
                            munition--;
                        }
                        break;
                }
            }

            updateHud();
        }
    }

    public void explode() {
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
