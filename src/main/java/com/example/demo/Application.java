package com.example.demo;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("primary.fxml"));
    VBox vBox = fxmlLoader.load();
    vBox.setPadding(new Insets(5, 0, 5, 0));

    FXController controller = fxmlLoader.getController();
    stage.setOnCloseRequest(we -> controller.setClosed());

    HBox hBox = new HBox(5);
    hBox.setPrefWidth(vBox.getPrefWidth());
    hBox.setPadding(new Insets(0, 5, 0, 5));
    controller.setup(hBox);

    vBox.getChildren().add(hBox);

    Scene scene = new Scene(vBox);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

    stage.setTitle("OpenCV Test");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    nu.pattern.OpenCV.loadLocally();
    launch(args);
  }
}