package com.lazicoder.office;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MainDialog extends Application {

    private TextField txtSelectedFile;
    private GridPane gridPane;
    private static final String titleTxt = "^_^''";
    private List<String> holderList = new ArrayList<>();
    private StringBuilder content = new StringBuilder();

    public static void main(String[] args) {
        launch(args);
    }

    @Autowired
    public static MessageSource resources;

    private Locale locale = new Locale("vi");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(resources.getMessage("app.name", null, locale));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(resources.getMessage("file.chooser.title", null, locale));

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(resources.getMessage("file.chooser.desc", null, locale), "*.html", "*.htm");
        fileChooser.getExtensionFilters().add(extFilter);
        // Text field
        txtSelectedFile = new TextField();
        txtSelectedFile.setMinHeight(30.0);
        txtSelectedFile.setPromptText(resources.getMessage("file.chooser.promp", null, locale));
        txtSelectedFile.setPrefColumnCount(30);
        txtSelectedFile.setDisable(true);


        // Buttons
        Button selectFileButton = new Button(resources.getMessage("button.select.file", null, locale));
        selectFileButton.setPrefWidth(150);
        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                holderList.clear();
                content.setLength(0);

                File file = fileChooser.showOpenDialog(primaryStage);
                List<String> holderLines = new ArrayList<>();
                if (file != null) {
                    try {
                        int[] idx = {0};
                        Stream<String> lines = Files.lines(file.toPath(), Charset.forName("UTF-8"));
                        lines.forEach(line -> {
                            content.append(line);
                            Pattern p = Pattern.compile("\\{\\{(.*?)\\}\\}");
                            Matcher m = p.matcher(line);
                            while (m.find()) {
                                String token = m.group(1);
                                String tokenHolder = "{{" + token + "}}";
                                if (!holderList.contains(tokenHolder)) {
                                    holderList.add(tokenHolder);
                                    TextField txtField = new TextField();
                                    txtField.setPrefColumnCount(40);
                                    Label label = new Label(token);
                                    gridPane.addRow(idx[0]++, label, txtField);
                                }
                            }

                        });

                        txtSelectedFile.setText(file.getAbsolutePath());
                        GridPane.setMargin(gridPane, new Insets(5, 5, 5, 5));
                    } catch (Exception ex) {
                        error(ex);
                    }
                }
            }
        });

        Button savebtn = new Button(resources.getMessage("button.load.server", null, locale));
        Button clearbtn = new Button(resources.getMessage("button.save", null, locale));
        savebtn.setPrefWidth(150);
        clearbtn.setPrefWidth(80);

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(txtSelectedFile, selectFileButton, savebtn, clearbtn);

        // Vbox
        VBox vbox = new VBox(30);
        vbox.setPadding(new Insets(25, 25, 25, 25));

        //gridPane
        gridPane = new GridPane();

        HBox hbox1 = new HBox();
        Button export = new Button("Export");
        export.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    try {
                        ObservableList<Node> children = gridPane.getChildren();
                        String[] replaceArr = new String[holderList.size()];
                        int idx = 0;
                        for (Node child : children) {
                            if (child instanceof TextField) {
                                replaceArr[idx++] = ((TextField) child).getText();
                            }
                        }
                        String newContent = StringUtils.replaceEach(content.toString(), holderList.toArray(new String[holderList.size()]), replaceArr);
                        Files.write(file.toPath(), newContent.getBytes());
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setContentText("Success");
                        alert.showAndWait();
                    } catch (Exception e) {
                        error(e);
                    }
                }

            }
        });
        hbox1.setSpacing(10);
        hbox1.getChildren().

                addAll(export);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        vbox.getChildren().

                addAll(hbox, scrollPane, hbox1);

        // Scene
        Scene scene = new Scene(vbox, 800, 600); // w x h
        primaryStage.setScene(scene);
        primaryStage.show();

        selectFileButton.requestFocus();
    }

    private void error(Exception ex) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setWidth(400);
        alert.setContentText(ExceptionUtils.getFullStackTrace(ex));
        alert.showAndWait();
    }

    private class InfoButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {


            // Show info alert dialog

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(titleTxt);
            alert.setHeaderText("Information Alert");
            String s = "This is an example of JavaFX 8 Dialogs. " +
                    "This is an Alert Dialog of Alert type - INFORMATION." + " \n \n" +
                    "Other Alert types are: CONFIRMATION, ERROR, NONE and WARNING.";
            alert.setContentText(s);

            alert.show();
        }
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            // Show error alert dialog

            String txt = txtSelectedFile.getText().trim();
            String msg = "Text saved: ";
            boolean valid = true;

            if ((txt.isEmpty()) || (txt.length() < 5)) {

                valid = false;
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(titleTxt);
                String s = "Text should be at least 5 characters long. " +
                        "Enter valid text and save. ";
                alert.setContentText(s);

                alert.showAndWait();
                msg = "Invalid text entered: ";
            }


            if (!valid) {

                txtSelectedFile.requestFocus();
            }
        }
    }

    private class ClearButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            // Show confirm alert dialog

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(titleTxt);
            String s = "Confirm to clear text in text field ! ";
            alert.setContentText(s);

            Optional<ButtonType> result = alert.showAndWait();

            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

                txtSelectedFile.setText("");
                txtSelectedFile.requestFocus();
            }
        }
    }
}