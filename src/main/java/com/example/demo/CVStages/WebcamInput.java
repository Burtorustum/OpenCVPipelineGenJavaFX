package com.example.demo.CVStages;

import org.opencv.core.Mat;

public class WebcamInput extends CVStage {

  public WebcamInput() {
    super("webcam");
  }

  @Override
  public void apply(Mat src, Mat dst) {
    src.copyTo(dst);
  }
}
