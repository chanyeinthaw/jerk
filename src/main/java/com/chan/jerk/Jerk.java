package com.chan.jerk;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Jerk {
    private String layoutDir;
    private Class<?> controller;
    private Stage stage;

    private boolean isModal = false;

    public Jerk(Class<?> controller) {
        this(controller, "layouts/", new Stage());
    }

    public Jerk(Class<?> controller, Stage stage) {
        this(controller, "layouts/", stage);
    }

    public Jerk(Class<?> controller, String layoutDir, Stage stage) {
        this.controller = controller;
        this.layoutDir = layoutDir;
        this.stage = stage;
    }

    public void makeModelWindow(Stage owner) {
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);

        isModal = true;
    }

    public Controller start() throws IOException {
        FXMLLoader fxmlLoader = _loadFxml();
        Parent root = fxmlLoader.load();

        Controller controller = fxmlLoader.getController();
        controller.isModel = isModal;
        controller.setStage(stage);
        controller.onLoaded(root);

        return controller;
    }

    public Controller start(Controller.Result result) throws IOException {
        Controller controller = start();
        controller.isModel = isModal;
        controller.setResult(result);

        return controller;
    }

    public Controller startInPane(Pane pane) throws IOException {
        FXMLLoader fxmlLoader = _loadFxml();

        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.isModel = isModal;
        controller.setStage(stage);
        controller.onLoaded(root);

        pane.getChildren().addAll(root);

        return controller;
    }

    private FXMLLoader _loadFxml() {
        String fxmlFile = layoutDir + controller.getAnnotation(Layout.class).value() + ".fxml";
        return new FXMLLoader(ResourceLoader.getResource(fxmlFile));
    }

    public static void applyKillHandler(Stage stage) {
        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });
    }
}
