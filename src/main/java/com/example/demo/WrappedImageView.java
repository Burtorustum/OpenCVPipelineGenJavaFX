package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WrappedImageView extends ImageView {

  public WrappedImageView() {
    setPreserveRatio(false);
  }

  @Override
  public double minWidth(double height) {
    return 40;
  }

  @Override
  public double prefWidth(double height) {
    Image I = getImage();
    if (I == null) {
      return minWidth(height);
    }
    return I.getWidth();
  }

  @Override
  public double maxWidth(double height) {
    return 16384;
  }

  @Override
  public double minHeight(double width) {
    return 40;
  }

  @Override
  public double prefHeight(double width) {
    Image I = getImage();
    if (I == null) {
      return minHeight(width);
    }
    return I.getHeight();
  }

  @Override
  public double maxHeight(double width) {
    return 16384;
  }

  @Override
  public boolean isResizable() {
    return true;
  }

  @Override
  public void resize(double width, double height) {
    setFitWidth(width);
    setFitHeight(height);

    Image img = this.getImage();
    if (img != null) {

      double ratioX = this.getFitWidth() / img.getWidth();
      double ratioY = this.getFitHeight() / img.getHeight();

      double reducCoeff = Math.min(ratioX, ratioY);

      double w = img.getWidth() * reducCoeff;
      double h = img.getHeight() * reducCoeff;

      this.setTranslateX((this.getFitWidth() - w) / 2);
      this.setTranslateY((this.getFitHeight() - h) / 2);

    }
  }
}