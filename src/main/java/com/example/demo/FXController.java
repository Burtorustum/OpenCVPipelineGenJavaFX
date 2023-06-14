package com.example.demo;

import com.example.demo.CVStages.Blur;
import com.example.demo.CVStages.CVStage;
import com.example.demo.CVStages.Dilate;
import com.example.demo.CVStages.Erode;
import com.example.demo.CVStages.HSVThreshold;
import com.example.demo.CVStages.WebcamInput;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.videoio.VideoCapture;

public class FXController {

  @FXML
  public ToggleButton cameraToggle;

  private final ArrayList<CVStage> stages = new ArrayList<>();
  private ScheduledExecutorService timer;
  private final VideoCapture capture = new VideoCapture();
  private boolean cameraActive = false;

  public void setup(HBox hBox) {

    this.stages.add(new WebcamInput());
    this.stages.add(new Blur());
    this.stages.add(new Erode(new Mat(), new Point()));
    this.stages.add(new Dilate(new Mat(), new Point()));
    this.stages.add(new HSVThreshold());

    for (CVStage stage : stages) {
      VBox wrap = new VBox(stage.imageView, stage.label);
      for (Control c : stage.controls) {
        wrap.getChildren().add(c);
      }
      wrap.setAlignment(Pos.CENTER);
      hBox.getChildren().add(wrap);
    }
  }

  /**
   * The action triggered by pushing the button on the GUI
   */
  @FXML
  protected void startCamera() {
    if (!this.cameraActive) {
      // start the video capture
      // the id of the camera to be used
      int cameraId = 0;
      this.capture.open(cameraId);

      // is the video stream available?
      if (this.capture.isOpened()) {
        this.cameraActive = true;

        // grab a frame every 33 ms (30 frames/sec)
        Runnable frameGrabber = () -> {
          // effectively grab and process a single frame
          Mat[] frames = grabFrame();
          // convert and show the frame
          for (int i = 0; i < this.stages.size(); i++) {
            Image img = Utils.mat2Image(frames[i]);
            updateImageView(stages.get(i).imageView, img);
          }
        };

        this.timer = Executors.newSingleThreadScheduledExecutor();
        this.timer.scheduleWithFixedDelay(frameGrabber, 0, 1, TimeUnit.MILLISECONDS);

      } else {
        System.err.println("Failed to open webcam");
      }
    } else {
      // the camera is not active at this point
      this.cameraActive = false;
      this.stopAcquisition();
    }
  }

  /**
   * Get a frame from the opened video stream (if any)
   *
   * @return the {@link Mat} to show
   */
  private Mat[] grabFrame() {
    // init everything
    Mat[] frames = new Mat[this.stages.size()];
    Mat camCapture = new Mat();

    // check if the capture is open
    if (this.capture.isOpened()) {
      try {
        // read the current frame
        this.capture.read(camCapture);

        // if the frame is not empty, process it
        if (!camCapture.empty()) {
          for (int i = 0; i < this.stages.size(); i++) {
            frames[i] = new Mat();
            stages.get(i).apply(camCapture, frames[i]);
            frames[i].copyTo(camCapture);
          }
        }

      } catch (Exception e) {
        // log the error
        System.err.println("Exception during the image elaboration: " + e);
      }
    }

    return frames;
  }

  /**
   * Stop the acquisition from the camera and release all the resources
   */
  private void stopAcquisition() {
    if (this.timer != null && !this.timer.isShutdown()) {
      try {
        // stop the timer
        this.timer.shutdown();
        this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
      } catch (InterruptedException e) {
        // log any exception
        System.err.println(
            "Exception in stopping the frame capture, trying to release the camera now... " + e);
      }
    }

    if (this.capture.isOpened()) {
      // release the camera
      this.capture.release();
    }
  }

  /**
   * Update the {@link ImageView} in the JavaFX main thread
   *
   * @param view  the {@link ImageView} to update
   * @param image the {@link Image} to show
   */
  private void updateImageView(ImageView view, Image image) {
    Utils.onFXThread(view.imageProperty(), image);
  }

  /**
   * On application close, stop the acquisition from the camera
   */
  protected void setClosed() {
    this.stopAcquisition();
  }
}