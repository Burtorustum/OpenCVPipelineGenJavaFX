package com.example.demo.CVStages;

import com.example.demo.WrappedImageView;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.opencv.core.Mat;

public abstract class CVStage {

  public final WrappedImageView imageView;

  public final Label label;

  public CVStage(String name) {
    this.imageView = new WrappedImageView();
    this.imageView.setPreserveRatio(true);

    this.label = new Label(name);
    this.label.setFont(new Font(24));
  }

  public abstract void apply(Mat src, Mat dst);

}
