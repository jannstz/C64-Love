/*
 * Jules Pong
 * jannstz@gmail.com
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.*;


public class JulesPong extends Application {

    static final int WIDTH = 640;   // App window width
    static final int HEIGHT = 480;  // App window height

    private Court court = new Court();
    private ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
    
    public static void main(String[] args) {
        launch(args);
    } // end main()

    public void start(Stage stage) {
        stage.setTitle("Jules Pong");

        // Create group to hold game elements and attach to scene
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.BLACK);
        
        // Register keyboard events
        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case ESCAPE:
                    exit();
                    break;
                case SPACE:
                    court.launchBall();
                    break;
                case Q:
                case A: 
                    court.paddleP1.setDeltaY(0);
                    break;
                case O:
                case L:
                    court.paddleP2.setDeltaY(0); 
                    break;
            }
        });
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case Q:
                    court.paddleP1.setDeltaY(-1);
                    break;
                case A:
                    court.paddleP1.setDeltaY(1);
                    break;
                case O:
                    court.paddleP2.setDeltaY(-1);
                    break;
                case L:
                    court.paddleP2.setDeltaY(1);
            }
        });

        // Add game elements to root group
        root.getChildren().add(court.paddleP1);
        root.getChildren().add(court.paddleP2);
        root.getChildren().add(court.ball);
        root.getChildren().add(court.net);
        root.getChildren().add(court.scoreP1);
        root.getChildren().add(court.scoreP2);

        // Set the stage and make visible
        stage.setScene(scene);
        stage.show();

        // Start game loop
        play();
    } // end start()

    public class Status implements Runnable {
        @Override
        public void run() {
            if (court.hasWon()) stopPlay();
        } // end run()

    } // end Status class

    public void play() {
        // Start runnable thread with game oourt
        long delay = 1000L / 60L;
        pool.scheduleWithFixedDelay(court, 1, delay, TimeUnit.MILLISECONDS);

        Status status = new Status();
        pool.scheduleWithFixedDelay(status, 1, delay, TimeUnit.MILLISECONDS);
    } // end play()

    public void stopPlay() {
        pool.shutdown();
    } // end stopPlay()

    public void exit() {
        pool.shutdownNow();
    } // end exit()

} // end JulesPong class