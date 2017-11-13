@ECHO OFF
ECHO.
ECHO This file collates code from the repository and adds it to the collated folder
ECHO.

PAUSE

ECHO.
ECHO Collating code...
ECHO.

java -jar Collate-TUI.jar collate from src/main to collated/main include java, fxml, css

java -jar Collate-TUI.jar collate from src/test to collated/test include java

java -jar Collate-TUI.jar collate from unused to collated/unused include java, fxml, css

ECHO.
ECHO Code Collated...
ECHO.

ECHO Exiting...

PAUSE

EXIT
