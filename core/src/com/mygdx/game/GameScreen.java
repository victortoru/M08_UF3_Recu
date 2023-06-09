package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import java.util.Iterator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;





public class GameScreen implements Screen {
    final Avion game;
    OrthographicCamera camera;
    Stage stage;
    Player player;
    boolean dead;
    Array<Obstaculos> obstacles;
    long lastObstacleTime;
    float score;
    int nivel;
    int aparicionObstaculos;
    float velocidadObstaculos;
    int nivelScore;

    public GameScreen(final Avion gam) {
        this.game = gam;
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        player = new Player();
        player.setManager(game.manager);
        stage = new Stage();
        stage.getViewport().setCamera(camera);
        stage.addActor(player);
        // create the obstacles array and spawn the first obstacle
        obstacles = new Array<Obstaculos>();
        spawnObstacle();
        score = 0;
        nivel=1;
        aparicionObstaculos = 1500;
        velocidadObstaculos = 300;
        nivelScore = 10;

    }
    @Override
    public void render(float delta) {
        boolean dead = false;

        ScreenUtils.clear(0.3f, 0.8f, 0.8f, 1);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(game.manager.get("background.png", Texture.class), 0,
                0);
        game.batch.end();

        stage.getBatch().setProjectionMatrix(camera.combined);
        stage.draw();

        game.batch.begin();
        game.smallFont.draw(game.batch, "Score: " + (int)score, 10, 470);
        game.batch.end();

        game.batch.begin();
        game.smallFont.draw(game.batch, "Nivel: " + (int)nivel, 650, 470);
        game.batch.end();

        if (Gdx.input.justTouched()) {
            player.impulso();
        }
        stage.act();


        if (player.getBounds().y > 400 - 45) {
            player.setY(400 - 45);
        } else if (player.getBounds().y < 70) {
            player.setY(70);
        }


        if (TimeUtils.millis() - lastObstacleTime > aparicionObstaculos)
            spawnObstacle();

        Iterator<Obstaculos> iter = obstacles.iterator();
        while (iter.hasNext()) {
            Obstaculos pipe = iter.next();
            if (pipe.getBounds().overlaps(player.getBounds())) {
                dead = true;
            }
        }

        iter = obstacles.iterator();
        while (iter.hasNext()) {
            Obstaculos obstaculos = iter.next();
            if(player.getX() > obstaculos.getX() && obstaculos.upsideDown && obstaculos.scoreAdded == false ){
                score += 1;
                obstaculos.scoreAdded=true;
            }

            if (obstaculos.getX() < -64) {
                obstacles.removeValue(obstaculos, true);
            }
        }

        if(dead) {
            game.lastScore = (int)score;
            if(game.lastScore > game.topScore)
                game.topScore = game.lastScore;
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        if(score == nivelScore){
            nivel ++;
            nivelScore += 10;
            velocidadObstaculos+=100;
            aparicionObstaculos-=100;
        }

    }

    private void spawnObstacle() {
        float holey = MathUtils.random(70, 370);
        Obstaculos obstaculos = new Obstaculos();
        obstaculos.setX(800);
        obstaculos.setY(holey);
        obstaculos.setUpsideDown(true);
        obstaculos.setManager(game.manager);
        obstaculos.velocidad = velocidadObstaculos; // Asignar velocidad actualizada
        obstacles.add(obstaculos);
        stage.addActor(obstaculos);
        lastObstacleTime = TimeUtils.millis();


        System.out.println(nivel);
    }


    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void show() {
    }
    @Override
    public void hide() {
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void dispose() {
    }
}