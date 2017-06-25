package com.kozakteam.asteroids;



/**
 * Created by User on 15.06.2017.
 */

class GameManager {

    TallyIcon[] tallyIcons;
    int numLives = 3;
    LifeIcon[] lifeIcons;

    int mapWidth = 600;
    int mapHeight = 600;
    private boolean playing = false;

    SpaceShip spaceShip;
    Border border;
    Star[] stars;
    int starsNumber = 200;
    Bullet[] bullets;
    int bulletsNumber = 20;
    Asteroid[] asteroids;
    int asteroidsNumber;
    int remainingAsteroidsNumber;
    int baseAsteroidsNumber = 10;
    int levelNumber = 1;

    int screenWidth;
    int screenHeight;

    //jak dużo metrów świata gry będzie pokazane na ekranie
    int mToShowX = 390;
    int mToShowY = 220;


    public GameManager(int x, int y) {
        screenWidth = x;
        screenHeight = y;
        asteroids = new Asteroid[500];
        lifeIcons = new LifeIcon[50];
        tallyIcons = new TallyIcon[500];

    }

    public void switchPlayingStatus() {
        playing = !playing;
    }

    public boolean isPlaying() {
        return playing;
    }

}
