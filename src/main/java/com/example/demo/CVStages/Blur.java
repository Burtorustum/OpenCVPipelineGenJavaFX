package com.example.demo.CVStages;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Blur extends CVStage {

  private final Size size;

  public Blur(Size size) {
    super("blur");
    
    this.size = size;
  }

  @Override
  public void apply(Mat src, Mat dst) {
    Imgproc.blur(src, dst, this.size, new Point(-1, -1));
  }
}
