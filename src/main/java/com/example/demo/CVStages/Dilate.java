package com.example.demo.CVStages;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class Dilate extends CVStage {

  private int iterations;
  private final Mat kernel;
  private final Point anchor;

  public Dilate(Mat kernel, Point anchor, int iterations) {
    super("dilate");

    this.kernel = kernel;
    this.anchor = anchor;
    this.iterations = iterations;
  }

  @Override
  public void apply(Mat src, Mat dst) {
    Imgproc.dilate(src, dst, this.kernel, this.anchor, this.iterations);
  }

  public void setIterations(int iterations) {
    this.iterations = iterations;
  }
}
