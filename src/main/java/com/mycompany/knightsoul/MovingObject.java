package com.mycompany.knightsoul;

public class MovingObject extends Thread {
    private int xStart, yStart, xEnd, yEnd, tempStart;
    private boolean isRunning;
    private boolean condition;
    private Game game;
 
    MovingObject(Game game, int xStart, int xEnd) {
        this.game = game;
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.tempStart = xStart;
        this.isRunning = true;
        this.condition = xStart < xEnd;

        this.start(); 
    }

    @Override
    public void run() {
        moveTo(xEnd);

        this.condition = !this.condition;
        moveTo(tempStart);

        stopRun(); 
    }

    private void moveTo(int targetPosition) {

        while (xStart != targetPosition && isRunning) {
            if (condition) {
                xStart += 10; 
            } else {
                xStart -= 10;
            }

            try {
                Thread.sleep(1000 / 90);  
                game.repaint();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stopRun() {
        this.isRunning = false;
    }

    public int getX() {
        return this.xStart;
    }
}
