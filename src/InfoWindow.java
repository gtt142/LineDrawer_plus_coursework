import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InfoWindow {
    public static void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Information");
        window.setMinWidth(200);
        window.setMinHeight(400);

        Label label = new Label("Information");
        label.setFont(Font.font(24));
        Text text = new Text("* Draw line by mouse\n\n" +
                "* For remove a line use the right mouse button");
        text.setFont(Font.font(16));

        VBox vBox = new VBox(20);
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null )));
        vBox.getChildren().addAll(label, text);
        vBox.setStyle("-fx-padding: 20");
        Scene scene = new Scene(vBox, Color.WHITE);
        window.setScene(scene);
        window.show();

    }
}
