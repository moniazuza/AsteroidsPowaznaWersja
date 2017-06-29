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
            double radianAngle1 = ((cp1.facingAngle / 180) *
                    Math.PI);
            double cosAngle1 = Math.cos(radianAngle1);
            double sinAngle1 = Math.sin(radianAngle1);
            double radianAngle2 = ((cp2.facingAngle / 180) *
                    Math.PI);
            double cosAngle2 = Math.cos(radianAngle2);
            double sinAngle2 = Math.sin(radianAngle2);
            int numCrosses = 0; // The number of times we crossa side
            float worldUnrotatedX;
            float worldUnrotatedY;

            for (int i = 0; i < cp1.vertexListLength; i++) {
                worldUnrotatedX = cp1.worldLocation.x + cp1.vertexList[i].x;
                worldUnrotatedY = cp1.worldLocation.y + cp1.vertexList[i].y;
            // Now rotate the newly updated point, stored in currentPoint
            // around the centre point of the object (worldLocation)
                cp1.currentPoint.x = cp1.worldLocation.x +
                        (int) ((worldUnrotatedX - cp1.worldLocation.x)
                                * cosAngle1 - (worldUnrotatedY - cp1.worldLocation.y) *
                                sinAngle1);
                cp1.currentPoint.y = cp1.worldLocation.y +
                        (int) ((worldUnrotatedX - cp1.worldLocation.x)
                                * sinAngle1 + (worldUnrotatedY - cp1.worldLocation.y) *
                                cosAngle1);
            // cp1.currentPoint now hold the x/y
            // world coordinates of the first point to test
            // Use two vertices at a time to represent the line we are testing
            // We don't test the last vertex because we are testing pairs
            // and the last vertex of cp2 is the padded extra vertex.
            // It will form part of the last side when we test vertexList[5]
                for (int j = 0; j < cp2.vertexListLength - 1; j++) {
            // Now we get the rotated coordinates of
            // BOTH the current 2 points being
            // used to form a side from cp2 (the asteroid)
            // First we need to rotate the model-space
            // coordinate we are testing
            // to its current world position
            // First update the regular un-rotated model space coordinates
            // relative to the current world location (centre of object)
                    worldUnrotatedX = cp2.worldLocation.x + cp2.vertexList[j].x;
                    worldUnrotatedY = cp2.worldLocation.y + cp2.vertexList[j].y;
            // Now rotate the newly updated point, stored inworldUnrotatedX/y
            // around the centre point of the object (worldLocation)
                    cp2.currentPoint.x = cp2.worldLocation.x +
                            (int) ((worldUnrotatedX - cp2.worldLocation.x)
                                    * cosAngle2 - (worldUnrotatedY - cp2.worldLocation.y) *
                                    sinAngle2);
                    cp2.currentPoint.y = cp2.worldLocation.y +
                            (int) ((worldUnrotatedX - cp2.worldLocation.x)
                                    * sinAngle2 + (worldUnrotatedY - cp2.worldLocation.y) *
                                    cosAngle2);
            // cp2.currentPoint now hold the x/y world coordinates
            // of the first point that
            // will represent a line from the asteroid
            // Now we can do exactly the same for the
            // second vertex and store it in
            // currentPoint2. We will then have a point and a line (two
            // vertices)we can use the
            // crossing number algorithm on.
                    worldUnrotatedX = cp2.worldLocation.x + cp2.vertexList[i +
                            1].x;
                    worldUnrotatedY = cp2.worldLocation.y + cp2.vertexList[i +
                            1].y;
            // Now rotate the newly updated point, stored inworldUnrotatedX/Y
            // around the centre point of the object (worldLocation)
                    cp2.currentPoint2.x = cp2.worldLocation.x +
                            (int) ((worldUnrotatedX - cp2.worldLocation.x)
                                    * cosAngle2 - (worldUnrotatedY - cp2.worldLocation.y) *
                                    sinAngle2);
                    cp2.currentPoint2.y = cp2.worldLocation.y +
                            (int) ((worldUnrotatedX - cp2.worldLocation.x)
                                    * sinAngle2 + (worldUnrotatedY - cp2.worldLocation.y) *
                                    cosAngle2);

            // And now we can test the rotated point from cp1 against the
            // rotated points which form a side from cp2
                    if (((cp2.currentPoint.y > cp1.currentPoint.y) !=
                            (cp2.currentPoint2.y > cp1.currentPoint.y)) &&
                            (cp1.currentPoint.x < (cp2.currentPoint2.x -
                                    cp2.currentPoint2.x) *(cp1.currentPoint.y -
                                    cp2.currentPoint.y) / (cp2.currentPoint2.y -
                                    cp2.currentPoint.y) + cp2.currentPoint.x)){
                        numCrosses++;
                    }
                }
            }
            // So do we have a collision?
            if (numCrosses % 2 == 0) {
            // even number of crosses(outside asteroid)
                collided = false;
            } else {
            // odd number of crosses(inside asteroid)
                collided = true;
            }
        }// end if



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
            double radianAngle = ((cp.facingAngle/180)*Math.PI);
            double cosAngle = Math.cos(radianAngle);
            double sinAngle = Math.sin(radianAngle);

            //Rotate each and every vertex then check for a collision
            // If just one is then we have a collision.
            // Once we have a collision no need to check further
            for (int i = 0 ; i < cp.vertexListLength; i++){
            // First update the regular un-rotated model spacecoordinates
            // relative to the current world location (centre ofobject)
                float worldUnrotatedX =
                        cp.worldLocation.x + cp.vertexList[i].x;
                float worldUnrotatedY =
                        cp.worldLocation.y + cp.vertexList[i].y;
            // Now rotate the newly updated point, stored incurrentPoint
            // around the centre point of the object (worldLocation)
                cp.currentPoint.x = cp.worldLocation.x + (int)
                        ((worldUnrotatedX - cp.worldLocation.x)
                                * cosAngle - (worldUnrotatedY - cp.worldLocation.y)
                                * sinAngle);
                cp.currentPoint.y = cp.worldLocation.y + (int)
                        ((worldUnrotatedX - cp.worldLocation.x)
                                * sinAngle+(worldUnrotatedY - cp.worldLocation.y)
                                * cosAngle);

                // Check the rotated vertex for a collision
                if (cp.currentPoint.x < 0) {
                    return true;
                } else if (cp.currentPoint.x > mapWidth) {
                    return true;
                } else if (cp.currentPoint.y < 0) {
                    return true;
                } else if (cp.currentPoint.y > mapHeight) {
                    return true;
                }
            }

        }
        return false; // No collision
    }
}
