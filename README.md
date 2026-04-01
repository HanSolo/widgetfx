## WidgetFX

This app will only work properly on MacOS!
You will need to start the WidgetFX.app first to make the widget work.
Once the WidgetFX.app is running you will see a little "FX" logo in the menu bar
of your MacOS machine. If you click on it, you can choose to either show, hide or
quit the app.

You can also close the widget by clicking on it.
The JavaFX app is just a demo that shows how to make use of the WidgetFX application.
The different buttons in the JavaFX app will show different versions of the widget,
depending on the style you have chosen.

The communication between the JavaFX app and the Swift app mainly happens via a json
document that is in the users home folder. The name of the file is widget_data.json.

The json file has two mandatory fields, title and message, all other fields are optional.
```json
{
  "title":STRING,
  "message":STRING,
  "value":DOUBLE,
  "unit":STRING,
  "color":STRING,
  "style":STRING,
  "timestamp":DOUBLE,
  "progress":DOUBLE,
  "icon":STRING,
  "badge":STRING
}
```
Example:
```json
{
  "icon" : "info.circle.fill",
  "message" : "Info send via TCP socket",
  "style" : "info",
  "timestamp" : 1775042971,
  "title" : "TCP Info"
}
```

If the message is send via tcp, it will be displayed directly and also will be stored to disk, where if only
the json file was updated, the update of the widget might take up to 2 sec before it will show up.

You can also set the widget from a shell script or the shell directly.
First check if the WidgetFX app is listening on port 9000 by using the shell command:
```shell
> lsof -i : 9000
```

Then you can send a message using the shell as follows:
```shell
echo '{"title":"TCP Info","message":"Info send via TCP socket","style":"info","icon":"info.circle.fill","timestamp":1775042971}' | nc 127.0.0.1 9000
```
The widget could have different styles as for example:
- info
- success
- warning
- error
- progress
- metric

The different styles are defined in the WidgetStyle class.
You can set an icon from the SF-Symbol library which is available on MacOS, you will find a string for each
icon in the enums SFSymbol0-3. 

In addition you can also set a specific color by defining a WidgetColor which just
takes the red, green, blue and alpha value (each between 0.0 - 1.0).

In the Helper class you will find some predefined messages that you can use but you can also create your own one.
