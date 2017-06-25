package com.kozakteam.asteroids;

import android.graphics.PointF;

import java.util.Random;

/**
 * Created by User on 22.06.2017.
 */

public class Asteroid extends GameObject {

    PointF[] points;
    CollisionPackage cp;

    public Asteroid(int levelNumber, int mapWidth, int mapHeight) {
        super();
        Random random = new Random();

        setRotationRate(random.nextInt(50 * levelNumber) + 10);
        setTravellingAngle(random.nextInt(360));

        int x = random.nextInt(mapWidth - 100) + 50;
        int y = random.nextInt(mapHeight - 100) + 50;
        if (x > 250 && x < 350) {
            x = x + 100;
        }
        if (y > 250 && y < 350) {
            y = y + 100;
        }
        setLocation(x, y);

        setSpeed(random.nextInt(25 * levelNumber) + 1);
        setMaxSpeed(140);
        if (getSpeed() > getMaxSpeed()) {
            setSpeed(getMaxSpeed());
        }

        setType(Type.ASTEROID);
        generatePoints();
        cp = new CollisionPackage(points, getLocation(), 25, getFacingAngle());
    }

    public void generatePoints() {
        points = new PointF[7];
        Random random = new Random();
        int i;

        //pierwszy punkt położony blisko środka, poniżej 0
        points[0] = new PointF();
        i = random.nextInt(10) + 1;
        if (i % 2 == 0)
            i = -i;
        points[0].x = i;
        i = -(random.nextInt(20) + 5);
        points[0].y = i;

        //drugi punkt poniżej środka, ale lekko w prawo i w górę
        points[1] = new PointF();
        i = random.nextInt(14) + 11;
        points[1].x = i;
        i = -(random.nextInt(12) + 1);
        points[1].y = i;

        //nad 0, po prawej
        points[2] = new PointF();
        i = random.nextInt(14) + 11;
        points[2].x = i;
        i = random.nextInt(12) + 1;
        points[2].y = i;

        //mniej więcej nad 0
        points[3] = new PointF();
        i = random.nextInt(10) + 1;
        if (i % 2 == 0)
            i = -i;
        points[3].x = i;
        i = random.nextInt(20) + 5;
        points[3].y = i;

        //po lewej nad 0
        points[4] = new PointF();
        i = -(random.nextInt(14) + 11);
        points[4].x = i;
        i = random.nextInt(12) + 1;
        points[4].y = i;

        //po lewej, poniżej 0
        points[5] = new PointF();
        i = -(random.nextInt(14) + 11);
        points[5].x = i;
        i = -(random.nextInt(12) + 1);
        points[5].y = i;

        // //pierwszy punkt położony blisko środka, poniżej 0
        points[6] = new PointF();
        points[6].x = points[0].x;
        points[6].x = points[0].x;

        float[] asteroidVertices = new float[]{
                // 1-2
                points[0].x, points[0].y, 0,
                points[1].x, points[1].y, 0,
                // 2-3
                points[1].x, points[1].y, 0,
                points[2].x, points[2].y, 0,
                // 3-4
                points[2].x, points[2].y, 0,
                points[3].x, points[3].y, 0,
                // 4-5
                points[3].x, points[3].y, 0,
                points[4].x, points[4].y, 0,
                // 5-6
                points[4].x, points[4].y, 0,
                points[5].x, points[5].y, 0,
                // 6-1
                points[5].x, points[5].y, 0,
                points[0].x, points[0].y, 0,
        };
        setVertices(asteroidVertices);
    }

    public void update(float fps) {
        setxVelocity((float) (getSpeed() * Math.cos(Math.toRadians(getTravellingAngle() + 90))));
        setyVelocity((float) (getSpeed() * Math.sin(Math.toRadians(getTravellingAngle() + 90))));
        move(fps);
        // Update the collision package
        cp.facingAngle = getFacingAngle();
        cp.worldLocation = getLocation();
    }

    public void bounce() {
        // kurs latania
        if (getTravellingAngle() >= 180) {
            setTravellingAngle(getTravellingAngle() - 180);
        } else {
            setTravellingAngle(getTravellingAngle() + 180);
        }
        // jak utkna to wywalenie
        setLocation((getLocation().x + -getxVelocity() / 3),
                (getLocation().y + -getyVelocity() / 3));
        // zwiekszenie speed 10%
        setSpeed(getSpeed() * 1.1f);
        // nie za szybko
        if (getSpeed() > getMaxSpeed()) {
            setSpeed(getMaxSpeed());
        }
    }
}
