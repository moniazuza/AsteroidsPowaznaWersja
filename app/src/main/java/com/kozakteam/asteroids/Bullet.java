package com.kozakteam.asteroids;

import android.graphics.PointF;

/**
 * Created by User on 21.06.2017.
 */

public class Bullet extends GameObject {

    private boolean inFlight = false;

    public Bullet(float spaceShipX, float spaceShipY) {
        super();
        setType(Type.BULLET);
        setLocation(spaceShipX, spaceShipY);
        float[] bulletVertices = new float[]{0, 0, 0};
        setVertices(bulletVertices);
    }

    public void shoot(float spaceShipFacingAngle) {
        setFacingAngle(spaceShipFacingAngle);
        inFlight = true;
        setSpeed(300);
    }

    public void resetBullet(PointF spaceShipLocation) {
        inFlight = false;
        setxVelocity(0);
        setyVelocity(0);
        setSpeed(0);
        setLocation(spaceShipLocation.x, spaceShipLocation.y);
    }

    public boolean isInFlight() {
        return inFlight;
    }

    public void update(long fps, PointF spaceShipLocation) {
        if (inFlight) {
            setxVelocity((float) (getSpeed() * Math.cos(Math.toRadians(getFacingAngle() + 90))));
            setyVelocity((float) (getSpeed() * Math.sin(Math.toRadians(getFacingAngle() + 90))));
        } else {
            setLocation(spaceShipLocation.x, spaceShipLocation.y);
        }
        move(fps);
    }
}
