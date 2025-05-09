package de.voidstack_overload.cardgame.fxNodes;

import javafx.beans.InvalidationListener;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CardStackPane extends Pane {

    double widthOffset = 0.35;
    double heightOffset = 0.20;

    public CardStackPane() {
        InvalidationListener relayout = obs -> requestLayout();
        parentProperty().addListener(relayout);
        getChildren().addListener((InvalidationListener) obs -> requestLayout());
    }

    /*────────────────────  layout  ────────────────────*/

    @Override
    protected void layoutChildren() {
        if (getChildren().isEmpty()) return;

        double leftInset   = snappedLeftInset();
        double topInset    = snappedTopInset();

        Node base = getChildren().getFirst();
        double w  = cardWidth(base);
        double h  = cardHeight(base);

        layoutInArea(base, leftInset, topInset, w, h, 0, HPos.LEFT, VPos.TOP);

        if (getChildren().size() > 1) {
            Node top = getChildren().get(1);
            layoutInArea(top,   leftInset + widthOffset* w, topInset + heightOffset * h, w, h, 0, HPos.LEFT, VPos.TOP);
        }
    }

    /*────────────────────  preferred size  ────────────────────*/

    @Override
    protected double computePrefWidth (double w) {
        double inset = snappedLeftInset() + snappedRightInset();
        return firstW() * (1 + widthOffset) + inset;
    }
    @Override
    protected double computePrefHeight(double h) {
        double inset = snappedTopInset() + snappedBottomInset();
        return firstH() * (1 + heightOffset) + inset;
    }

    /*────────────────────  helpers  ────────────────────*/

    private double firstW() { return getChildren().isEmpty() ? 0 : cardWidth (getChildren().getFirst()); }
    private double firstH() { return getChildren().isEmpty() ? 0 : cardHeight(getChildren().getFirst()); }

    private static double cardWidth(Node n) {
        return n instanceof ImageView iv && iv.getFitWidth() > 0 ? iv.getFitWidth()
                : n.prefWidth(-1);
    }
    private static double cardHeight(Node n) {
        return n instanceof ImageView iv && iv.getFitHeight() > 0 ? iv.getFitHeight()
                : n.prefHeight(-1);
    }
}
