package com.example.demo.CVStages;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Blur extends CVStage {

  private Slider sizeSlider;


  public Blur() {
    super("blur");
  }

  @Override
  public void apply(Mat src, Mat dst) {
    Imgproc.blur(src, dst,
        new Size(this.sizeSlider.getValue(), this.sizeSlider.getValue()),
        new Point(-1, -1));
  }

  @Override
  protected Control[] setupControls() {
    this.sizeSlider = new Slider(1, 25, 3);
    this.sizeSlider.setSnapToTicks(true);
    this.sizeSlider.setBlockIncrement(1);
    this.sizeSlider.setMajorTickUnit(1);
    this.sizeSlider.setMinorTickCount(0);
    this.sizeSlider.setShowTickLabels(true);

    Label sizeSliderLabel = new Label();
    sizeSliderLabel.textProperty().bind(
        Bindings.format(
            "Kernel Size: %.2f",
            this.sizeSlider.valueProperty()
        )
    );

    return new Control[]{sizeSliderLabel, this.sizeSlider};
  }
}
