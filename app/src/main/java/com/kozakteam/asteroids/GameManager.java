package com.kozakteam.asteroids;

/**
 * Created by User on 15.06.2017.
 */

class GameManager {

    int mapWidth = 600;
    int mapHeight = 600;
    private boolean playing = false;

    SpaceShip spaceShip;
    Border border;
    Star[] stars;
    int starsNumber = 200;
    Bullet[] bullets;
    int bulletsNumber = 20;

    int screenWidth;
    int screenHeight;

    //jak dużo metrów świata gry będzie pokazane na ekranie
    int mToShowX = 390;
    int mToShowY = 220;

    public GameManager(int x, int y) {
        screenWidth = x;
        screenHeight = y;
    }

    public void switchPlayingStatus() {
        playing = !playing;
    }

    public boolean isPlaying() {
        return playing;
    }
}
