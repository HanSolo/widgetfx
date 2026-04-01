module eu.hansolo.widgetfx {
    // Java
    requires java.desktop;

    // Java-FX
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;

    // 3rd party
    requires com.google.gson;

    exports eu.hansolo.widgetfx;
    exports eu.hansolo.widgetfx.demo;
}