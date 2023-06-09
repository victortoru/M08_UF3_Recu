package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Player extends Actor {
    Rectangle bounds;
    AssetManager manager;
    float speedy, gravity;

    Player(){
        setX(180);
        setY(220);
        setSize(67,45);
        bounds = new Rectangle();
        speedy = 0;
        gravity = 850f;
    }

    @Override
    public void act(float delta) {
        Input input = Gdx.input;

        if (input.isTouched()) {
            float touchX = input.getX();
            float touchY = input.getY();

            float gameHeight = 800;
            float touchYInGame = touchY / Gdx.graphics.getHeight() * gameHeight;


            if (touchYInGame < gameHeight / 2) {
                moveBy(0, 5);
            } else {
                moveBy(0, -5);
            }
        }


        bounds.set(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(manager.get("avion.png", Texture.class),
                getX(), getY());
    }

    void impulso() {
        speedy = 400f;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setManager(AssetManager manager) {
        this.manager = manager;
    }
}