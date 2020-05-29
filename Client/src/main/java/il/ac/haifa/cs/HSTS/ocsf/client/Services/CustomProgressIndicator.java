package il.ac.haifa.cs.HSTS.ocsf.client.Services;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class CustomProgressIndicator {
    ArrayList<Node> nodesToDisable;
    Pane mainPane;
    VBox indicatorVbox;

    public CustomProgressIndicator(Pane mainPane) {
        ObservableList<Node> disable = mainPane.getChildren();
        this.nodesToDisable = new ArrayList<>(disable.size());
        this.mainPane = mainPane;

        for (Node region : disable)
            this.nodesToDisable.add(region);
    }

    public void start() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxWidth(100);
        progressIndicator.setMaxHeight(100);
        progressIndicator.setPrefSize(100,100);
        progressIndicator.setBlendMode(BlendMode.DARKEN);
        indicatorVbox = new VBox(progressIndicator);

        ColorAdjust adj = new ColorAdjust();
        GaussianBlur blur = new GaussianBlur(4); // 55 is just to show edge effect more clearly.
        adj.setInput(blur);

        for (Node pane : nodesToDisable) {
            pane.setEffect(adj);
            pane.setDisable(true);
        }
        mainPane.getChildren().add(indicatorVbox);
        indicatorVbox.setDisable(false);
        progressIndicator.setDisable(false);
        indicatorVbox.setAlignment(Pos.BASELINE_CENTER);
        indicatorVbox.prefHeightProperty().bind(mainPane.heightProperty());
        indicatorVbox.prefWidthProperty().bind(mainPane.widthProperty());
        indicatorVbox.setPadding(new Insets(270,0,0,0));
    }

    public void stop() {
        ColorAdjust adj = new ColorAdjust();
        GaussianBlur blur = new GaussianBlur(0); // 55 is just to show edge effect more clearly.
        adj.setInput(blur);
        mainPane.setDisable(false);
        for (Node pane: nodesToDisable) {
            pane.setEffect(null);
            pane.setDisable(false);
        }
        indicatorVbox.setVisible(false);

    }
}
