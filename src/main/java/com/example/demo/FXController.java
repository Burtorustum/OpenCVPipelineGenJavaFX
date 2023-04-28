package com.example.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class FXController {

  @FXML
  public ToggleButton cameraToggle;

  @FXML
  private ImageView currentFrame;

  private ScheduledExecutorService timer;
  private final VideoCapture capture = new VideoCapture();
  private boolean cameraActive = false;

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
          Mat frame = grabFrame();
          // convert and show the frame
          Image imageToShow = Utils.mat2Image(frame);
          updateImageView(currentFrame, imageToShow);
        };

        this.timer = Executors.newSingleThreadScheduledExecutor();
        this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

        // update the button content
      } else {
        // log the error
        System.err.println("Failed to open webcam");
      }
    } else {
      // the camera is not active at this point
      this.cameraActive = false;

      // stop the timer
      this.stopAcquisition();
    }
  }

  // TODO: below can be modified to return Mat[] which includes all steps of pipeline

  /**
   * Get a frame from the opened video stream (if any)
   *
   * @return the {@link Mat} to show
   */
  private Mat grabFrame() {
    // init everything
    Mat frame = new Mat();

    // check if the capture is open
    if (this.capture.isOpened()) {
      try {
        // read the current frame
        this.capture.read(frame);

        // if the frame is not empty, process it
        if (!frame.empty()) {
          //Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
        }

      } catch (Exception e) {
        // log the error
        System.err.println("Exception during the image elaboration: " + e);
      }
    }

    return frame;
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