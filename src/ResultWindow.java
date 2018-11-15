import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ResultWindow {
    private Stage window = new Stage();

    public void init() {
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Result");
        VBox vBox = new VBox(20);
        Text text = new Text("");

        text.setText("Wait solving...");
        text.setFont(Font.font(20));

        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null )));
        vBox.getChildren().addAll(text);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 300, 200, Color.WHITE);
        window.setScene(scene);
        window.show();
    }

    public void setResult(Double result) {
        VBox vBox = new VBox(20);
        Text text = new Text("");
        text.setText("Result: "+result.toString());
        text.setFont(Font.font(20));
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null )));
        vBox.getChildren().add(text);
        vBox.setAlignment(Pos.CENTER);
        Button buttonOK = new Button("OK");
        buttonOK.setOnAction(event -> window.close());
        vBox.getChildren().add(buttonOK);
        Scene scene = new Scene(vBox, 300, 200, Color.WHITE);
        window.setScene(scene);
        window.show();
    }

    //  remove
    public void setOnClick(Double res) {
        window.setOnCloseRequest(event -> {
            event.consume();
            setResult(res);
        });
    }
}
