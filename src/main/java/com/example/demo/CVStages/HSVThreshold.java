package com.example.demo.CVStages;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class HSVThreshold extends CVStage {

  private final Scalar min;
  private final Scalar max;

  public HSVThreshold(Scalar min, Scalar max) {
    super("hsv threshold");

    this.min = min;
    this.max = max;
  }

  public HSVThreshold(int minH, int minS, int minV, int maxH, int maxS, int maxV) {
    this(new Scalar(minH, minS, minV), new Scalar(maxH, maxS, maxV));
  }

  @Override
  public void apply(Mat src, Mat dst) {
    Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2HSV);
    Core.inRange(src, new Scalar(50, 50, 50), new Scalar(150, 150, 150), dst);
  }

}
