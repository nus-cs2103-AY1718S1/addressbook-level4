# Collate all codes written under main_module
java -jar Collate-TUI.jar collate from src/main to collated/main include java, fxml, css
# Collate all codes written under test_module
java -jar Collate-TUI.jar collate from src/test to collated/test include java
# Collate all unused codes
java -jar Collate-TUI.jar collate from unused to collated/unused include java, fxml, css
