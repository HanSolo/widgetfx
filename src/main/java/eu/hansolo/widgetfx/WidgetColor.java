package eu.hansolo.widgetfx;

public record WidgetColor(double red, double green, double blue, double alpha) {
    WidgetColor(double red, double green, double blue) {
        this(red, green, blue, 1.0);
    }
}
