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
        window.setTitle("Информация");
        window.setMinWidth(200);
        window.setMinHeight(400);

        Label label = new Label("Информация (F1)");
        label.setFont(Font.font(24));
        Text text = new Text("* Для рисования отрезка используйте левую кнопку мыши.\n\n" +
                "* Для удаления отрезка поднесите к ней курсор и нажмите на ПКМ.\n\n" +
                "* Нажмите кнопку \"Очистить все\" (кисточка), чтобы очистить все.\n\n" +
                "* Нажмите кнопку \"Решить\"(калькулятор), чтобы решить задачу.\n\n" +
                "* Для сохранения файла с отрезками нажмите \"Сохранить\"(диск).\n\n" +
                "* Для открытия сохраненного файла нажмите \"Открыть\"(папка с файлом).");
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
