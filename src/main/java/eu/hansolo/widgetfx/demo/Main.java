package eu.hansolo.widgetfx.demo;

import eu.hansolo.widgetfx.Helper;
import eu.hansolo.widgetfx.SFSymbol0;
import eu.hansolo.widgetfx.Widget;
import eu.hansolo.widgetfx.WidgetStyle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Instant;


public class Main extends Application {
    private VBox mainPane;
    private Button infoButton;
    private Button progressButton;
    private Button successButton;
    private Button metricButton;
    private Button warningButton;
    private Button errorButton;
    private Button sendTcpButton;
    private Button sendUdpButton;


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

        sendTcpButton = new Button("Via TCP");
        sendTcpButton.setPrefWidth(150);

        sendUdpButton = new Button("Via UDP");
        sendUdpButton.setPrefWidth(150);

        mainPane = new VBox(20, infoButton, progressButton, successButton, metricButton, warningButton, errorButton, sendTcpButton, sendUdpButton);

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
        sendTcpButton.setOnAction(evt -> {
            final Widget tcpWidget = new Widget().title("TCP Info").message("Info send via TCP socket").style(WidgetStyle.INFO).icon(SFSymbol0.infoCircleFill).timestamp();
            final String json = Helper.toJson(tcpWidget);
            try { Helper.sendViaTcp(json); } catch (Exception e) { throw new RuntimeException(e); }
        });
        sendUdpButton.setOnAction(evt -> {
            final Widget udpWidget = new Widget().title("UDP Info").message("Info send via UDP socket").style(WidgetStyle.INFO).icon(SFSymbol0.infoCircleFill).timestamp();
            final String json = Helper.toJson(udpWidget);
            try { Helper.sendViaUdp(json); } catch (Exception e) { throw new RuntimeException(e); }
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
