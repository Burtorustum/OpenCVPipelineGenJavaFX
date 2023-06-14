package com.example.demo.CVStages;

import javafx.scene.control.Control;
import org.opencv.core.Mat;

public class WebcamInput extends CVStage {

  public WebcamInput() {
    super("webcam");
  }

  @Override
  public void apply(Mat src, Mat dst) {
    src.copyTo(dst);
  }

  @Override
  protected Control[] setupControls() {
    return new Control[0];
  }
}
