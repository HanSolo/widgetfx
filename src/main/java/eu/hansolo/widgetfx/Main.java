package eu.hansolo.widgetfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    /*
    System.out.println("1) Info");
        Helper.sendInfo("Java-Demo", "Connection established");
        Thread.sleep(2000);

        System.out.println("2) Progress 30% …");
        Helper.sendProgress("Upload", "File will be transferred…", 0.30);
        Thread.sleep(2500);

        System.out.println("3) Progress 70%");
        Helper.sendProgress("Upload", "File will be transferred…", 0.70);
        Thread.sleep(2500);

        System.out.println("4) Success");
        Helper.sendSuccess("Upload finished", "All files transferred");
        Thread.sleep(2000);

        System.out.println("5) Metric");
        Helper.sendMetric("Temperature", "Server room rack 3", 38.7, "°C");
        Thread.sleep(2000);

        System.out.println("6) Warning");
        Helper.sendWarning("Memory low", "Less than 10% available");
        Thread.sleep(2000);

        System.out.println("7) Error");
        Helper.sendError("DB-Connection failed", "Host db.local:5432 not reachable");
        Thread.sleep(2000);

        System.out.println("8) Custom color");
        Helper.show(Widget.create()
                          .title("Custom Color")
                          .message("RGB-Color directly set from Java")
                          .color(new WidgetColor(1.0, 0.8, 0.0))
                          .icon(SFSymbol0.paintpaletteFill.icon())
                          .style("info")
                          .timestamp());

        System.out.println("Demo finished");
     */

    private VBox mainPane;
    private Button infoButton;
    private Button progressButton;
    private Button successButton;
    private Button metricButton;
    private Button warningButton;
    private Button errorButton;


    @Override public void init() {
        infoButton     = new Button("Info");
        infoButton.setPrefWidth(150);

        progressButton = new Button("Progress");
        progressButton.setPrefWidth(150);

        successButton  = new Button("Success");
        successButton.setPrefWidth(150);

        metricButton   = new Button("Metric");
        metricButton.setPrefWidth(150);

        warningButton  = new Button("Warning");
        warningButton.setPrefWidth(150);

        errorButton    = new Button("Error");
        errorButton.setPrefWidth(150);

        mainPane = new VBox(20, infoButton, progressButton, successButton, metricButton, warningButton, errorButton);

        registerListeners();
    }

    private void registerListeners() {
        infoButton.setOnAction(evt -> {
            try { Helper.sendInfo("Java-Demo", "Connection established"); } catch (IOException e) { throw new RuntimeException(e); }
        });
        progressButton.setOnAction(evt -> {
            try { Helper.sendProgress("Upload", "File will be transferred…", 0.30); } catch (IOException e) { throw new RuntimeException(e); }
        });
        successButton.setOnAction(evt -> {
            try { Helper.sendSuccess("Upload finished", "All files transferred"); } catch (IOException e) { throw new RuntimeException(e); }
        });
        metricButton.setOnAction(evt -> {
            try { Helper.sendMetric("Temperature", "Server room rack 3", 38.7, "°C"); } catch (IOException e) { throw new RuntimeException(e); }
        });
        warningButton.setOnAction(evt -> {
            try { Helper.sendWarning("Memory low", "Less than 10% available"); } catch (IOException e) { throw new RuntimeException(e); }
        });
        errorButton.setOnAction(evt -> {
            try { Helper.sendError("DB-Connection failed", "Host db.local:5432 not reachable"); } catch (IOException e) { throw new RuntimeException(e); }
        });
    }

    @Override public void start(final Stage stage) {
        StackPane pane  = new StackPane(mainPane);
        pane.setPadding(new Insets(10));

        Scene     scene = new Scene(pane);

        stage.setTitle("WidgetFX");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    @Override public void stop() {
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
