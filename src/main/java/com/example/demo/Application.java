package com.example.demo;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("primary.fxml"));

    VBox vBox = fxmlLoader.load();
    Scene scene = new Scene(vBox);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

    stage.setTitle("OpenCV Test");
    stage.setScene(scene);
    stage.show();

    FXController controller = fxmlLoader.getController();
    stage.setOnCloseRequest(we -> controller.setClosed());
  }

  public static void main(String[] args) {
    nu.pattern.OpenCV.loadLocally();
    launch(args);
  }
}