package com.kozakteam.asteroids;

import android.graphics.PointF;

/**
 * Created by user on 25.06.2017.
 */

public class CollisionDetection {

    // tworzenie obiektu
    private static PointF rotatedPoint = new PointF();


    public static boolean detect(CollisionPackage cp1,
                                 CollisionPackage cp2) {
        boolean collided = false;
     // sprawdzanie kolizji miedzy dwoma obiektami
        float distanceX = (cp1.worldLocation.x)
                - (cp2.worldLocation.x);
        // sprawdzanie dystansku miedzy 2 obiekatmi
        float distanceY = (cp1.worldLocation.y)
                - (cp2.worldLocation.y);
        // liczenie dystansu miedzy jednym obiektem a drugim
        double distance = Math.sqrt
                (distanceX * distanceX + distanceY * distanceY);

        if (distance < cp1.radius + cp2.radius) {
            // Log.e("Circle collision:","true");
            // todo  Eventually we will add the
            // more accurate code here
            // todo and delete the line below.
            collided = true;
        }
        return collided;
    }


    public static boolean contain(float mapWidth, float mapHeight,
                                  CollisionPackage cp) {
        boolean possibleCollision = false;

        // sprawdzanie czy wszystkie kwadraty kolizji sa na swoim miejscu
        if (cp.worldLocation.x - cp.radius < 0) {
            possibleCollision = true;
        } else if (cp.worldLocation.x + cp.radius > mapWidth) {
            possibleCollision = true;
        } else if (cp.worldLocation.y - cp.radius < 0) {
            possibleCollision = true;
        } else if (cp.worldLocation.y + cp.radius > mapHeight) {
            possibleCollision = true;
        }
        if (possibleCollision) {
            // todo For now we return true
            return true;
        }
        return false; // No collision
    }
}
