package com.kozakteam.asteroids;

import android.graphics.PointF;

/**
 * Created by User on 15.06.2017.
 */

class SpaceShip extends GameObject {

    boolean thrusting;
    private boolean pressingRight = false;
    private boolean pressingLeft = false;
    CollisionPackage cp;


    public SpaceShip(float locationX, float locationY) {
        super();
        PointF[] points;
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

        // inicjalizacja paczki kolizji
        //liczba listy obiektow, najwiekszy promien

        points = new PointF[6];
        points[0] = new PointF(- halfWidth, - halfLength);
        points[2] = new PointF(halfWidth, - halfLength);
        points[4] = new PointF(0, 0 + halfLength);
        points[1] = new PointF(points[0].x +
                points[2].x/2,(points[0].y + points[2].y)/2);
        points[3] = new PointF((points[2].x + points[4].x)/2,
                (points[2].y + points[4].y)/2);
        points[5] = new PointF((points[4].x + points[0].x)/2,
                (points[4].y + points[0].y)/2);
        cp = new CollisionPackage(points, getLocation(),
                length/2, getFacingAngle());
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

        // update paczki kolizji
        cp.facingAngle = getFacingAngle();
        cp.worldLocation = getLocation();
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
