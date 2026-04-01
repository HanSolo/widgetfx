package eu.hansolo.widgetfx;

import java.time.Instant;


public class Widget {
    public String      title;
    public String      message;
    public Double      value;
    public String      unit;
    public WidgetColor color;
    public String      style;      // info | success | warning | error | progress | metric
    public Double      progress;   // 0.0 – 1.0
    public String      icon;       // SF-Symbol-Name
    public String      badge;
    public Double      timestamp;  // Unix epoch (seconds)


    public static Widget create() { return new Widget(); }

    public Widget title(final String title) {
        this.title = title;
        return this;
    }

    public Widget message(final String message)  {
        this.message = message;
        return this;
    }

    public Widget value(final double value) {
        this.value = value;
        return this;
    }

    public Widget unit(final String unit) {
        this.unit = unit;
        return this;
    }

    public Widget color(final WidgetColor color) {
        this.color = color;
        return this;
    }

    public Widget style(final WidgetStyle style) { return this.style(style.name); }
    public Widget style(final String styleName) {
        this.style = styleName;
        return this;
    }

    public Widget progress(final double progress) {
        this.progress  = progress;
        return this;
    }

    public Widget icon(final SFSymbol0 symbol) { return this.icon(symbol.icon()); }
    public Widget icon(final String iconName) {
        this.icon = iconName;
        return this;
    }

    public Widget badge(final String badge) {
        this.badge = badge;
        return this;
    }

    public Widget timestamp() { return this.timestamp(Instant.now()); }
    public Widget timestamp(final Instant timestamp) {
        this.timestamp = Double.valueOf(timestamp.getEpochSecond());
        return this;
    }
}
