package com.example.demo.CVStages;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class Erode extends CVStage {

  private Slider iterationsSlider;
  private final Mat kernel;
  private final Point anchor;

  public Erode(Mat kernel, Point anchor) {
    super("erode");

    this.kernel = kernel;
    this.anchor = anchor;
  }

  @Override
  public void apply(Mat src, Mat dst) {
    Imgproc.erode(src, dst, this.kernel, this.anchor, (int) this.iterationsSlider.getValue());
  }

  @Override
  protected Control[] setupControls() {
    this.iterationsSlider = new Slider(1, 25, 3);
    this.iterationsSlider.setSnapToTicks(true);
    this.iterationsSlider.setBlockIncrement(1);
    this.iterationsSlider.setMajorTickUnit(1);
    this.iterationsSlider.setMinorTickCount(0);
    this.iterationsSlider.setShowTickLabels(true);

    Label iterationSliderLabel = new Label();
    iterationSliderLabel.textProperty().bind(
        Bindings.format(
            "Iterations: %.2f",
            this.iterationsSlider.valueProperty()
        )
    );

    return new Control[]{iterationSliderLabel, this.iterationsSlider};
  }
}
