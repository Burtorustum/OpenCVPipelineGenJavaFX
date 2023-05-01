package com.example.demo;

import java.util.function.BiConsumer;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public enum PipelineSteps {


  INPUT(Mat::copyTo),
  BLUR((Mat src, Mat dst) -> {
    Imgproc.GaussianBlur(src, dst, new Size(15, 15), 0);
  }),
  ERODE((Mat src, Mat dst) -> {
    Imgproc.erode(src, dst, new Mat(), new Point(), 5);
  }),
  DILATE((Mat src, Mat dst) -> {
    Imgproc.dilate(src, dst, new Mat(), new Point(), 5);
  }),
  THRESHOLD((Mat src, Mat dst) -> {
    Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2HSV);
    Core.inRange(src, new Scalar(50, 50, 50), new Scalar(150, 150, 150), dst);
  }),
  ;

  final BiConsumer<Mat, Mat> execute;

  PipelineSteps(BiConsumer<Mat, Mat> execute) {
    this.execute = execute;
  }
}
