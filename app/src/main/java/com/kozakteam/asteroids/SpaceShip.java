package com.kozakteam.asteroids;

import android.graphics.PointF;

/**
 * Created by User on 15.06.2017.
 */

class SpaceShip extends GameObject {

    boolean thrusting;
    private boolean pressingRight = false;
    private boolean pressingLeft = false;

    public SpaceShip(float locationX, float locationY) {
        super();
        //ustawiamy typ, żeby metoda draw z gameobject wiedziała, co rysować
        setType(Type.SPACESHIP);
        setLocation(locationX, locationY);
        float width = 15;
        float length = 20;
        setSize(width, length);
        setMaxSpeed(150);
        //żeby nie zawracać sobie dupy ciągłym dzieleniem
        float halfWidth = width / 2;
        float halfLength = length / 2;
        //definiujemy kształt statku jako trójkąt
        //punkt po punkcie, przeciwnie do ruchu wskazówek zegara
        float[] spaceShipVertices = new float[]{
                -halfWidth, -halfLength, 0,
                halfWidth, -halfLength, 0,
                0, 0 + halfLength, 0
        };
        setVertices(spaceShipVertices);
    }

    public void update(long fps) {
        float speed = getSpeed();
        if (thrusting) {
            if (speed < getMaxSpeed()) {
                setSpeed(speed + 5);
            }
        } else {
            if (speed > 0) {
                setSpeed(speed - 3);
            } else {
                setSpeed(0);
            }
        }
        setxVelocity((float) (speed * Math.cos(Math.toRadians(getFacingAngle() + 90))));
        setyVelocity((float) (speed * Math.sin(Math.toRadians(getFacingAngle() + 90))));
        if (pressingLeft) {
            setRotationRate(360);
        } else if (pressingRight) {
            setRotationRate(-360);
        } else {
            setRotationRate(0);
        }
        move(fps);
    }

    public boolean pullTrigger(){
        return true;
    }

    public void setPressingRight(boolean pressingRight){
        this.pressingRight=pressingRight;
    }

    public void setPressingLeft(boolean pressingLeft){
        this.pressingLeft=pressingLeft;
    }

    public void toogleThrust(){
        thrusting=!thrusting;
    }
}
