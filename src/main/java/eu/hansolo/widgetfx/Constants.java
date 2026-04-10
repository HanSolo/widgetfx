package eu.hansolo.widgetfx;

public class Constants {
    public static final String HOME_FOLDER     = System.getProperty("user.home");
    public static final String JSON_FILENAME   = "widget_data.json";

    public static final String TCP_HOST        = "127.0.01";
    public static final int    TCP_PORT        = 9000;
    public static final String UDP_HOST        = "255.255.255.255";
    public static final int    UDP_PORT        = 9999;
    public static final String FIELD_TITLE     = "title";
    public static final String FIELD_MESSAGE   = "message";
    public static final String FIELD_VALUE     = "value";
    public static final String FIELD_UNIT      = "unit";
    public static final String FIELD_COLOR     = "\"color\":{";
    public static final String FIELD_RED       = "\"red\":%.3f,";
    public static final String FIELD_GREEN     = "\"green\":%.3f,";
    public static final String FIELD_BLUE      = "\"blue\":%.3f,";
    public static final String FIELD_ALPHA     = "\"alpha\":%.3f";
    public static final String FIELD_STYLE     = "style";
    public static final String FIELD_PROGRESS  = "progress";
    public static final String FIELD_ICON      = "icon";
    public static final String FIELD_BADGE     = "badge";
    public static final String FIELD_TIMESTAMP = "\"timestamp\":";
}
