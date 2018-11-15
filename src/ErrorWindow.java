import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorWindow {
    public static void display(String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Error");
        window.setMinWidth(500);
        window.setMaxWidth(1000);
        window.setMinHeight(50);

        Label label = new Label("Error: ");
        label.setFont(Font.font(24));
        Text text = new Text(message);
        text.setFont(Font.font(16));

        HBox hBox = new HBox(20);
        hBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null )));
        hBox.getChildren().addAll(label, text);
        hBox.setAlignment(Pos.CENTER);
        hBox.setStyle("-fx-padding: 20");
        Scene scene = new Scene(hBox, Color.WHITE);
        window.setScene(scene);
        window.show();

    }
}
