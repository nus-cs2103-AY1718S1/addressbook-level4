###### \unused\MainWindow.java
``` java

/**
 * Changes the stylesheet used by GUI.
 */
public void changeTheme(int theme) {
    String brightTheme = "view/BrightTheme.css";
    String darkTheme = "view/DarkTheme.css";
    if (theme == 0) {
        getRoot().getStylesheets().remove(darkTheme);
        getRoot().getStylesheets().add(brightTheme);
    } else {
        getRoot().getStylesheets().remove(brightTheme);
        getRoot().getStylesheets().add(darkTheme);
    }
}
```
