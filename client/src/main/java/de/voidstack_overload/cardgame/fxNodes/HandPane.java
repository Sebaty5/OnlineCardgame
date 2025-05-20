package de.voidstack_overload.cardgame.fxNodes;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class HandPane extends Pane {
    private final double cardWidth;
    private final double minGap = 10;

    public HandPane() {
        this.cardWidth = 128;
    }

    public HandPane(double cardWidth) {
        this.cardWidth = cardWidth;
    }

    @Override
    protected void layoutChildren() {
        int n = getChildren().size();
        if (n == 0) return;

        double available = getWidth();
        double gap = minGap;

        double needed = n * cardWidth + (n - 1) * gap;
        if(needed > available) {
            gap = (available - n * cardWidth) / (n - 1);
            gap = Math.max(gap, -cardWidth * 0.9);
            needed = n * cardWidth + (n - 1) * gap;
        }

        double x = (available - needed) / 2;

        for (Node child : getChildren()) {
            layoutInArea(child, x, 0, cardWidth, child.prefHeight(-1),  0, HPos.CENTER, VPos.TOP);
            x += cardWidth + gap;
        }
    }

    @Override
    protected double computePrefHeight(double w) {
        return getChildren().stream()
                .mapToDouble(c -> c.prefHeight(-1))
                .max()
                .orElse(0);
    }

}
