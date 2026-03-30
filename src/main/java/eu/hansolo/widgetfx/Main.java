package eu.hansolo.widgetfx;


public class Main {

    public static void main(String[] args) throws Exception {

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
    }
}
