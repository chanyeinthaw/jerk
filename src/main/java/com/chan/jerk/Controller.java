package com.chan.jerk;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

abstract public class Controller {
    public interface Result {
        void onResult(Object result);
    }

    boolean isModel = false;
    protected Result result;
    protected Stage currentStage;

    void setStage(Stage stage) {
        currentStage = stage;
    }

    void setResult(Result result) {
        this.result = result;
    }

    public void onLoaded(Parent root) {
        currentStage.setScene(new Scene(root, currentStage.getWidth(), currentStage.getHeight()));
    }

    public void show() {
        currentStage.addEventHandler(WindowEvent.WINDOW_HIDING, windowEvent -> {
            onPausing();
        });

        currentStage.addEventHandler(WindowEvent.WINDOW_HIDDEN, windowEvent -> {
            onPause();
        });

        currentStage.addEventHandler(WindowEvent.WINDOW_SHOWING, windowEvent -> {
            onResuming();
        });

        currentStage.addEventHandler(WindowEvent.WINDOW_SHOWN, windowEvent -> {
            onResume();
        });

        currentStage.setOnCloseRequest(windowEvent -> {
            onClose();
        });

        if (isModel) currentStage.showAndWait();
        else currentStage.show();
    }

    public void passExtras(Object ...extras) {};

    public void onClose() {}

    public void onPausing() {};

    public void onPause() {};

    public void onResuming() {};

    abstract public void onResume();
}
