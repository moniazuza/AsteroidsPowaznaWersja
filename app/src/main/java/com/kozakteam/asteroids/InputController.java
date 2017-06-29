package com.kozakteam.asteroids;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by User on 21.06.2017.
 */

class InputController {

    private int currentBullet;
    Rect left, right, thrust, shoot, pause;

    public InputController(int x, int y) {
        //przyciski do sterowania
        int buttonWidth = x / 8;
        int buttonHeight = y / 7;
        int buttonPadding = x / 80;

        left = new Rect(buttonPadding,
                y - buttonHeight - buttonPadding,
                buttonWidth,
                y - buttonPadding);


        right = new Rect(buttonWidth + buttonPadding,
                y - buttonHeight - buttonPadding,
                buttonWidth + buttonPadding + buttonWidth,
                y - buttonPadding);

        thrust = new Rect(x - buttonWidth - buttonPadding,
                y - buttonHeight - buttonPadding - buttonHeight - buttonPadding,
                x - buttonPadding,
                y - buttonPadding - buttonHeight - buttonPadding);


         shoot = new Rect(x - buttonWidth - buttonPadding,
                y - buttonHeight - buttonPadding,
                x - buttonPadding,
                y - buttonPadding);

        pause = new Rect(x - buttonPadding - buttonWidth,
                buttonPadding,
                x - buttonPadding,
                buttonPadding + buttonHeight);
    }

    public ArrayList getButtons() {
        ArrayList<Rect> currentButtonList = new ArrayList<>();
        currentButtonList.add(left);
        currentButtonList.add(right);
        currentButtonList.add(thrust);
        currentButtonList.add(shoot);
        currentButtonList.add(pause);
        return currentButtonList;
    }

    public void handleInput(MotionEvent motionEvent, GameManager gameManager) {
        int pointerCount = motionEvent.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {

            int x = (int) motionEvent.getX(i);
            int y = (int) motionEvent.getY(i);

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    if (right.contains(x, y)) {
                        gameManager.spaceShip.setPressingRight(true);
                        gameManager.spaceShip.setPressingLeft(false);
                    } else if (left.contains(x, y)) {
                        gameManager.spaceShip.setPressingLeft(true);
                        gameManager.spaceShip.setPressingRight(false);
                    } else if (thrust.contains(x, y)) {
                        gameManager.spaceShip.toogleThrust();
                    } else if (shoot.contains(x, y)) {
                        if (gameManager.spaceShip.pullTrigger()) {
                            gameManager.bullets[currentBullet].shoot(gameManager.spaceShip.getFacingAngle());
                            currentBullet++;
                            if (currentBullet == gameManager.bulletsNumber) {
                                currentBullet = 0;
                            }
                        }
                    } else if (pause.contains(x, y)) {
                        gameManager.switchPlayingStatus();
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    if (right.contains(x, y)) {
                        gameManager.spaceShip.setPressingRight(false);
                    } else if (left.contains(x, y)) {
                        gameManager.spaceShip.setPressingLeft(false);
                    }
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    if (right.contains(x, y)) {
                        gameManager.spaceShip.setPressingRight(true);
                        gameManager.spaceShip.setPressingLeft(false);
                    } else if (left.contains(x, y)) {
                        gameManager.spaceShip.setPressingLeft(true);
                        gameManager.spaceShip.setPressingRight(false);
                    } else if (thrust.contains(x, y)) {
                        gameManager.spaceShip.toogleThrust();
                    } else if (shoot.contains(x, y)) {
                        if (gameManager.spaceShip.pullTrigger()) {
                            gameManager.bullets[currentBullet].shoot(gameManager.spaceShip.getFacingAngle());
                            currentBullet++;
                            if (currentBullet == gameManager.bulletsNumber) {
                                currentBullet = 0;
                            }
                        }
                    } else if (pause.contains(x, y)) {
                        gameManager.switchPlayingStatus();
                    }
                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    if (right.contains(x, y)) {
                        gameManager.spaceShip.setPressingRight(false);
                    } else if (left.contains(x, y)) {
                        gameManager.spaceShip.setPressingLeft(false);
                    }
                    break;
            }
        }
    }
}
