//@@author yewshengkai-unused
/*Future implementation.*/
/**
 * Open a file
 */
@FXML
private void handleImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import from...");
        fileChooser.showOpenDialog(primaryStage);
        }

/**
 * Save file to specific format
 */
@FXML
private void handleExport() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter excelFilter = new FileChooser.ExtensionFilter("Excel Workbook (*.xlsx)", "*.xlsx");
        FileChooser.ExtensionFilter pdfFilter = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
        FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter("XML Data (*.xml)", "*.xml");
        FileChooser.ExtensionFilter textFilter = new FileChooser.ExtensionFilter("Text Documents (*.txt)", "*.txt");

        fileChooser.getExtensionFilters().add(excelFilter);
        fileChooser.getExtensionFilters().add(pdfFilter);
        fileChooser.getExtensionFilters().add(xmlFilter);
        fileChooser.getExtensionFilters().add(textFilter);

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Export to..");
        fileChooser.setInitialFileName("iungoAB");
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
        String content = "Prep_data_save_to_excel";
        try {
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
        } catch (Exception ex) {
        System.out.println(ex.toString());
        }

        }
        }
//@@author
