import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class ResultWindow {
    private Stage window = new Stage();
    private Lines resultLines = null;
    private final FileChooser fileChooser = new FileChooser();


    public void init() {
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Результат");
        VBox vBox = new VBox(20);
        Text text = new Text("");

        text.setText("Подождите...");
        text.setFont(Font.font(20));

        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null )));
        vBox.getChildren().addAll(text);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 300, 200, Color.WHITE);
        window.setScene(scene);
        window.show();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MyFormat", "*.mf"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
    }

    public void setResult(Integer result) {
        VBox vBox = new VBox(20);
        Text text = new Text("");
        text.setText("Результат: "+result.toString());
        text.setFont(Font.font(20));
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null )));
        vBox.getChildren().add(text);
        vBox.setAlignment(Pos.CENTER);
        Button buttonOK = new Button("OK");
        buttonOK.setOnAction(event -> window.close());
        Button buttonSaveRes = new Button("Save Result");
        buttonSaveRes.setOnAction(event -> this.saveResult());
        vBox.getChildren().addAll(buttonOK, buttonSaveRes);
        Scene scene = new Scene(vBox, 300, 200, Color.WHITE);
        window.setScene(scene);
        window.show();
    }

    public void setResultLines(Lines lines) {
        this.resultLines = lines;
    }

    private void saveResult() {
        if (resultLines == null || resultLines.getLines().size() < 1) {
            MessageWindow.display("Nothing to save.");
            return;
        }
        configureFileChooserSave(fileChooser);
        File file = fileChooser.showSaveDialog(window);
        if (file != null) {
            try {
                boolean saveRes = resultLines.saveToFile(file);
                if(!saveRes) {
                    ErrorWindow.display("Can not save.");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void configureFileChooserSave(final FileChooser fileChooser){
        fileChooser.setTitle("Save result");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.setInitialFileName("lines_res.mf");
    }

}
