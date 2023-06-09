package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Avion extends Game {
    SpriteBatch batch;
    BitmapFont smallFont, bigFont;
    AssetManager manager;
    int topScore;
    int lastScore;

    public void create() {
        manager = new AssetManager();
        manager.load("avion.png", Texture.class);
        manager.load("enemigos.png", Texture.class);
        manager.load("background.png", Texture.class);

        manager.finishLoading();
        batch = new SpriteBatch();


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("8bitOperatorPlus-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 22;
        params.borderWidth = 2;
        params.borderColor = Color.BLACK;
        params.color = Color.WHITE;
        smallFont = generator.generateFont(params);
        params.size = 50;
        params.borderWidth = 5;
        bigFont = generator.generateFont(params);
        generator.dispose();
        this.setScreen(new MainMenuScreen(this));
        topScore = 0;
        lastScore = 0;

    }

    public void render() {
        super.render();
    }

    public void dispose() {
    }
}
