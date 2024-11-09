package com.mycompany.knightsoul;

import javax.swing.ImageIcon;

public class StateCtrl {
    private int stageCount;
    // private ImageIcon[] backgrounds; 

    public StateCtrl() {
        this.stageCount = 1;
        // backgrounds = new ImageIcon[]{
        //         new ImageIcon(getClass().getResource("/img/background1.jpg")),
        //         new ImageIcon(getClass().getResource("/img/background2.jpg")),
        //         new ImageIcon(getClass().getResource("/img/background3.jpg"))
        // };
    }

    public void increaseStage() {
        stageCount++;
    }

    public int getStageCount() {
        return stageCount;
    }

    //Method to get the background based on stage count
    // public ImageIcon getBackground() {
    //     return backgrounds[stageCount % backgrounds.length];
    // }
}
