package eu.hansolo.widgetfx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Locale;


public class Helper {
    public static final String keyValue(final String key, final String value) {
        return "\"" + key + "\":\"" + escape(value) + "\",";
    }

    public static final String keyValueNumeric(final String key, final double value) {
        return String.format(Locale.US, "\"%s\":%.4f,", key, value);
    }

    public static final String escape(final String text) {
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    public static final double clamp(final double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }

    public static final void show(final Widget widget) throws IOException {
        show(Path.of(Constants.HOME_FOLDER, Constants.JSON_FILENAME), widget);
    }
    public static final void show(final Path jsonPath, final Widget widget) throws IOException {
        final String json = toJson(widget);
        final Path   tmp  = Files.createTempFile(jsonPath.getParent(), "widget_data", ".json");
        try {
            Files.writeString(tmp, json, StandardCharsets.UTF_8);
            Files.move(tmp, jsonPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException ex) {
            Files.deleteIfExists(tmp);
            throw ex;
        }
    }

    public static final Widget read(final Path jsonPath) throws IOException {
        if (!Files.exists(jsonPath)) { return null; }
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Reader reader = Files.newBufferedReader(jsonPath, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, Widget.class);
        }
    }

    public static final void sendViaTcp(final Widget widget) { sendViaTcp(toJson(widget)); }
    public static final void sendViaTcp(final String json) {
        try (Socket socket = new Socket(Constants.TCP_HOST, Constants.TCP_PORT)) {
            final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)), true);
            out.println(json); // println adds the '\n' delimiter Swift reads
        } catch (ConnectException ex) {
            System.err.println("Could not connect — is the Swift app running and listening on port " + Constants.TCP_PORT + "?");
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static final void sendViaUdp(final Widget widget) { sendViaUdp(toJson(widget)); }
    public static final void sendViaUdp(final String json) {
        try {
            InetAddress    address = InetAddress.getLocalHost(); //InetAddress.getByName("localhost");
            DatagramSocket socket  = new DatagramSocket();
            byte[]         buffer  = json.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet  = new DatagramPacket(buffer, buffer.length, address, Constants.UDP_PORT);
            socket.send(packet);
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static final String toJson(final Widget widget) {
        StringBuilder sb = new StringBuilder("{");
        sb.append(Helper.keyValue(Constants.FIELD_TITLE, widget.title));
        sb.append(Helper.keyValue(Constants.FIELD_MESSAGE, widget.message));
        sb.append(Helper.keyValue(Constants.FIELD_STYLE, widget.style));
        if (widget.value != null) sb.append(Helper.keyValueNumeric(Constants.FIELD_VALUE, widget.value));
        if (widget.unit != null) sb.append(Helper.keyValue(Constants.FIELD_UNIT, widget.unit));
        if (widget.progress != null) sb.append(Helper.keyValueNumeric(Constants.FIELD_PROGRESS, Helper.clamp(widget.progress)));
        if (widget.icon != null) sb.append(Helper.keyValue(Constants.FIELD_ICON, widget.icon));
        if (widget.badge != null) sb.append(Helper.keyValue(Constants.FIELD_BADGE, widget.badge));
        if (widget.color != null) {
            sb.append(Constants.FIELD_COLOR)
              .append(String.format(Locale.US, Constants.FIELD_RED, widget.color.red()))
              .append(String.format(Locale.US, Constants.FIELD_GREEN, widget.color.green()))
              .append(String.format(Locale.US, Constants.FIELD_BLUE, widget.color.blue()))
              .append(String.format(Locale.US, Constants.FIELD_ALPHA, widget.color.alpha()))
              .append("},");
        }
        sb.append(Constants.FIELD_TIMESTAMP).append(widget.timestamp).append("}");
        return sb.toString();
    }


    // ******************** CONVENIENCE METHODS *******************************
    public static final void sendInfo(final String title, final String message) throws IOException {
        sendInfo(Path.of(Constants.HOME_FOLDER, Constants.JSON_FILENAME), title, message);
    }
    public static final void sendInfo(final Path jsonPath, final String title, final String message) throws IOException {
        show(jsonPath, new Widget().title(title).message(message).style(WidgetStyle.INFO).icon(SFSymbol0.infoCircleFill).timestamp(Instant.now()));
    }

    public static final void sendSuccess(final String title, final String message) throws IOException {
        sendSuccess(Path.of(Constants.HOME_FOLDER, Constants.JSON_FILENAME), title, message);
    }
    public static final void sendSuccess(final Path jsonPath, final String title, final String message) throws IOException {
        show(jsonPath, new Widget().title(title).message(message).style(WidgetStyle.SUCCESS).icon(SFSymbol0.checkmarkSealFill).timestamp(Instant.now()));
    }

    public static final void sendWarning(final String title, final String message) throws IOException {
        sendWarning(Path.of(Constants.HOME_FOLDER, Constants.JSON_FILENAME), title, message);
    }
    public static final void sendWarning(final Path jsonPath, final String title, final String message) throws IOException {
        show(jsonPath, new Widget().title(title).message(message).style(WidgetStyle.WARNING).icon(SFSymbol0.exclamationmarkTriangleFill).timestamp(Instant.now()));
    }

    public static final void sendError(final String title, final String message) throws IOException {
        sendError(Path.of(Constants.HOME_FOLDER, Constants.JSON_FILENAME), title, message);
    }
    public static final void sendError(final Path jsonPath, final String title, final String message) throws IOException {
        show(jsonPath, new Widget().title(title).message(message).style(WidgetStyle.ERROR).icon(SFSymbol0.xmarkOctagonFill).badge("!").timestamp(Instant.now()));
    }

    public static final void sendMetric(final String title, final String message, final double value, final String unit) throws IOException {
        sendMetric(Path.of(Constants.HOME_FOLDER, Constants.JSON_FILENAME), title, message, value, unit);
    }
    public static final void sendMetric(final Path jsonPath, final String title, final String message, final double value, final String unit) throws IOException {
        show(jsonPath, new Widget().title(title).message(message).value(value).unit(unit).style(WidgetStyle.METRIC).icon(SFSymbol0.chartBarFill).timestamp(Instant.now()));
    }

    public static final void sendProgress(final String title, final String message, final double percentage) throws IOException {
        sendProgress(Path.of(Constants.HOME_FOLDER, Constants.JSON_FILENAME), title, message, percentage);
    }
    public static final void sendProgress(final Path jsonPath, final String title, final String message, final double percentage) throws IOException {
        show(jsonPath, new Widget().title(title).message(message).progress(percentage).style(WidgetStyle.PROGRESS).icon(SFSymbol0.arrowUpCircleFill).badge((int) Math.round(percentage * 100) + "%").timestamp(Instant.now()));
    }
}
