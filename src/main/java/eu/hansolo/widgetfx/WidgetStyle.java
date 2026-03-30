package eu.hansolo.widgetfx;

public enum WidgetStyle {
    INFO("info"),
    SUCCESS("success"),
    WARNING("warning"),
    ERROR("error"),
    PROGRESS("progress"),
    METRIC("metric");

    public final String name;

    WidgetStyle(final String name) {
        this.name = name;
    }
}
