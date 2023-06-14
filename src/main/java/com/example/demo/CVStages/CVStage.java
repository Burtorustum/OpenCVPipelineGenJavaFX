package com.example.demo.CVStages;

import com.example.demo.WrappedImageView;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.opencv.core.Mat;

public abstract class CVStage {

  public final WrappedImageView imageView;

  public final Label label;

  // TODO: replace control[] with custom control object that will go in separate part of window
  //  to do controls for each stage and allow connections like GRIP
  public final Control[] controls;

  public CVStage(String name) {
    this.imageView = new WrappedImageView();
    this.imageView.setPreserveRatio(true);

    this.label = new Label(name);
    this.label.setFont(new Font(24));

    this.controls = this.setupControls();
  }

  public abstract void apply(Mat src, Mat dst);

  protected abstract Control[] setupControls();

}
