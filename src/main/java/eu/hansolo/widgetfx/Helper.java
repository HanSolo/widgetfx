package eu.hansolo.widgetfx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Locale;


public class Helper {
    public static final String keyValue(final String key, final String value) {
        return "  \"" + key + "\": \"" + escape(value) + "\",\n";
    }

    public static final String keyValueNumeric(final String key, final double value) {
        return String.format(Locale.US, "  \"%s\": %.4f,\n", key, value);
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

    public Widget read(final Path jsonPath) throws IOException {
        if (!Files.exists(jsonPath)) { return null; }
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Reader reader = Files.newBufferedReader(jsonPath, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, Widget.class);
        }
    }

    public static final String toJson(final Widget widget) {
        StringBuilder sb = new StringBuilder("{\n");
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
              .append("  },\n");
        }
        sb.append(Constants.FIELD_TIMESTAMP).append(widget.timestamp).append("\n}");
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
