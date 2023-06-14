package com.example.demo.CVStages;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class HSVThreshold extends CVStage {

  private Slider minHSlider;
  private Slider minSSlider;
  private Slider minVSlider;

  private Slider maxHSlider;
  private Slider maxSSlider;
  private Slider maxVSlider;

  public HSVThreshold() {
    super("hsv threshold");
  }

  @Override
  public void apply(Mat src, Mat dst) {
    Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2HSV);
    Core.inRange(src,
        new Scalar(minHSlider.getValue(), minSSlider.getValue(), minVSlider.getValue()),
        new Scalar(maxHSlider.getValue(), maxSSlider.getValue(), maxVSlider.getValue()), dst);
  }

  @Override
  protected Control[] setupControls() {

    this.minHSlider = new Slider(0, 179, 0);
    this.minHSlider.setShowTickLabels(true);
    Label minHSliderLabel = new Label();
    minHSliderLabel.textProperty().bind(
        Bindings.format(
            "Min H: %.2f",
            this.minHSlider.valueProperty()
        )
    );

    this.minSSlider = new Slider(0, 255, 0);
    this.minSSlider.setShowTickLabels(true);
    Label minSSliderLabel = new Label();
    minSSliderLabel.textProperty().bind(
        Bindings.format(
            "Min S: %.2f",
            this.minSSlider.valueProperty()
        )
    );

    this.minVSlider = new Slider(0, 255, 0);
    this.minVSlider.setShowTickLabels(true);
    Label minVSliderLabel = new Label();
    minVSliderLabel.textProperty().bind(
        Bindings.format(
            "Min V: %.2f",
            this.minVSlider.valueProperty()
        )
    );

    this.maxHSlider = new Slider(0, 179, 179);
    this.maxHSlider.setShowTickLabels(true);
    Label maxHSliderLabel = new Label();
    maxHSliderLabel.textProperty().bind(
        Bindings.format(
            "Max H: %.2f",
            this.maxHSlider.valueProperty()
        )
    );

    this.maxSSlider = new Slider(0, 255, 255);
    this.maxSSlider.setShowTickLabels(true);
    Label maxSSliderLabel = new Label();
    maxSSliderLabel.textProperty().bind(
        Bindings.format(
            "Max S: %.2f",
            this.maxSSlider.valueProperty()
        )
    );

    this.maxVSlider = new Slider(0, 255, 255);
    this.maxVSlider.setShowTickLabels(true);
    Label maxVSliderLabel = new Label();
    maxVSliderLabel.textProperty().bind(
        Bindings.format(
            "Max V: %.2f",
            this.maxVSlider.valueProperty()
        )
    );

    return new Control[]{
        minHSliderLabel,
        this.minHSlider,
        minSSliderLabel,
        this.minSSlider,
        minVSliderLabel,
        this.minVSlider,
        maxHSliderLabel,
        this.maxHSlider,
        maxSSliderLabel,
        this.maxSSlider,
        maxVSliderLabel,
        this.maxVSlider};
  }

}
