import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;


public class Drawer extends Application {

    private Double windowMinHeight = Config.WINDOW_MIN_HEIGHT;
    private Double windowMinWidth = Config.WINDOW_MIN_WIDTH;
    private Stage mainStage;
    private Double defaultCanvasWidth = Config.WINDOW_MIN_WIDTH;
    private Double defaultCanvasHeight = Config.WINDOW_MIN_HEIGHT - Config.ICONS_HEIGHT - Config.WINDOW_HEIGHT_DELTA;
    private Canvas mainCanvas;

    private Double x0 = 0.0;
    private Double y0 = 0.0;
    private Double x1 = 0.0;
    private Double y1 = 0.0;
    private Boolean hoveredLine = false;
    private Lines lines = new Lines();

    private final FileChooser fileChooser = new FileChooser();

    private FileInputStream input0;
    {
        try {
            input0 = new FileInputStream(Config.MAIN_ICON);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private FileInputStream input1;
    {
        try {
            input1 = new FileInputStream(Config.SAVE_BUTTON_ICON);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private FileInputStream input2;
    {
        try {
            input2 = new FileInputStream(Config.OPEN_BUTTON_ICON);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private FileInputStream input3;
    {
        try {
            input3 = new FileInputStream(Config.CLEAR_ALL_BUTTON_ICON);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private FileInputStream input4;
    {
        try {
            input4 = new FileInputStream(Config.INFO_BUTTON_ICON);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private FileInputStream input5;
    {
        try {
            input5 = new FileInputStream(Config.SOLVE_BUTTON_ICON);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    private Image mainIcon = new Image(Objects.requireNonNull(input0));
    private Image image1 = new Image(Objects.requireNonNull(input1));
    private ImageView buttonSaveImageView = new ImageView(image1);
    private Image image2 = new Image(Objects.requireNonNull(input2));
    private ImageView buttonOpenFilesView = new ImageView(image2);
    private Image image3 = new Image(Objects.requireNonNull(input3));
    private ImageView buttonClearAllView = new ImageView(image3);
    private Image image4 = new Image(Objects.requireNonNull(input4));
    private ImageView buttonInfoView = new ImageView(image4);
    private Image image5 = new Image(Objects.requireNonNull(input5));
    private ImageView buttonSolveView = new ImageView(image5);

    public static void main(String[] args) {
        try {
            ConfigFromFile.readConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        primaryStage.setTitle(Config.MAIN_WINDOW_NAME);
        Group root = new Group();

        Double canvasWidth = defaultCanvasWidth;
        Double canvasHeight = defaultCanvasHeight;
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        Canvas draftCanvas = new Canvas(canvasWidth, canvasHeight);
        mainCanvas = canvas;

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MyFormat", "*.mf"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        buttonSaveImageView.setFitHeight(Config.ICONS_HEIGHT);
        buttonSaveImageView.setFitWidth(Config.ICONS_HEIGHT);
        Button saveButton = new Button("", buttonSaveImageView);
        saveButton.setTooltip(new Tooltip("Save"));
        saveButton.setOnAction(event -> {
            configureFileChooserSave(fileChooser);
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                try {
                    Boolean saveRes = lines.saveToFile(file);
                    if(!saveRes) {
                        ErrorWindow.display("Can not save.");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        buttonOpenFilesView.setFitHeight(Config.ICONS_HEIGHT);
        buttonOpenFilesView.setFitWidth(Config.ICONS_HEIGHT);
        Button openButton = new Button("", buttonOpenFilesView);
        openButton.setTooltip(new Tooltip("Open file"));
        openButton.setOnAction(event -> {
            configureFileChooserOpen(fileChooser);
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                lines.clearAll(canvas);
                try {
                    lines = new Lines(file);
                } catch (Exception e) {
                    ErrorWindow.display(e.getMessage());
                    return;
                }
                resizeOnOpenFile(canvas, draftCanvas);
                lines.drawLinesOnCanvas(canvas);
            }
        });
        buttonClearAllView.setFitHeight(Config.ICONS_HEIGHT);
        buttonClearAllView.setFitWidth(Config.ICONS_HEIGHT);
        Button clearAllButton = new Button("", buttonClearAllView);
        clearAllButton.setTooltip(new Tooltip("Clear all"));
        clearAllButton.setOnAction(event -> {
            lines.clearAll(canvas);
            windowMinHeight = Config.WINDOW_MIN_HEIGHT;
            windowMinWidth = Config.WINDOW_MIN_WIDTH;
            primaryStage.setMinWidth(windowMinWidth);
            primaryStage.setMinHeight(windowMinHeight);

        });
        buttonInfoView.setFitHeight(Config.ICONS_HEIGHT);
        buttonInfoView.setFitWidth(Config.ICONS_HEIGHT);
        Button infoButton = new Button("", buttonInfoView);
        infoButton.setOnAction(event -> InfoWindow.display());
        infoButton.setTooltip(new Tooltip("Info"));
        buttonSolveView.setFitHeight(Config.ICONS_HEIGHT);
        buttonSolveView.setFitWidth(Config.ICONS_HEIGHT);
        Button solveButton = new Button("", buttonSolveView);
        solveButton.setTooltip(new Tooltip("Solve"));
        solveButton.setOnAction(event -> solveTask());


        BorderPane borderPane = new BorderPane();
        HBox topBox = new HBox();
        topBox.setSpacing(5);
        topBox.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

        borderPane.setTop(topBox);
        topBox.getChildren().addAll(openButton, saveButton, clearAllButton, infoButton, solveButton);
        borderPane.getTop().setStyle("-fx-border-width: 1;" +
                "-fx-border-color: grey;" +
                "-fx-padding: 4;" +
                "-fx-border-radius: 5");


        addEventsForCanvases(canvas, draftCanvas);
        Pane pane = new Pane();
        pane.getChildren().addAll(canvas, draftCanvas);
        draftCanvas.toFront();
        borderPane.setCenter(pane);
        root.getChildren().add(borderPane);
        root.setOnKeyPressed(this::keyHandler);
        Scene scene = new Scene(root, Color.WHITESMOKE);
        primaryStage.setScene(scene);

        primaryStage.setResizable(true);
        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                canvas.setWidth(number2.doubleValue());
                draftCanvas.setWidth(number2.doubleValue());
            }
        });
        primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                canvas.setHeight(number2.doubleValue() - Config.ICONS_HEIGHT - Config.WINDOW_HEIGHT_DELTA - 37.0);
                draftCanvas.setHeight(number2.doubleValue() - Config.ICONS_HEIGHT - Config.WINDOW_HEIGHT_DELTA - 37.0);
            }
        });

        primaryStage.setMinHeight(windowMinHeight);
        primaryStage.setMinWidth(windowMinWidth);
        primaryStage.getIcons().add(mainIcon);
        primaryStage.show();

    }

    private void solveTask() {
        ResultWindow resultWindow = new ResultWindow();
        resultWindow.init();

        Thread taskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Task task = new Task(lines);
                Integer result = task.solve();
                Boolean[] deletedLines = task.getDeletedLines();
                Lines resultLines = new Lines();
                int counter = 0;
                for (Line line: lines.getLines()) {
                    if (!deletedLines[counter]) {
                        resultLines.getLines().add(line);
                    }
                    counter++;
                }
                resultWindow.setResultLines(resultLines);
                Platform.runLater(() -> {
                    resultWindow.setResult(result);
                    drawSolutionLines(deletedLines);
                });
            }

        });

        taskThread.start();
    }

    private void drawSolutionLines(Lines lines) {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.clearRect(0,0, mainCanvas.getWidth(), mainCanvas.getHeight());

        for (Line line: lines.getLines()) {
            gc.setStroke(Config.SOLUTION_LINE_COLOR);
            gc.setLineWidth(Config.LINEWIDTH);
            gc.strokeLine(line.getX0(), line.getY0(), line.getX1(), line.getY1());
        }
    }

    private void drawSolutionLines(Boolean[] deletedLines) {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.clearRect(0,0, mainCanvas.getWidth(), mainCanvas.getHeight());
//        lines.drawLinesOnCanvas(mainCanvas);

        int counter = 0;
        for (Line line: lines.getLines()) {
            if (!deletedLines[counter]) {
                gc.setStroke(Config.SOLUTION_LINE_COLOR);
                gc.setLineWidth(Config.LINEWIDTH);
                gc.strokeLine(line.getX0(), line.getY0(), line.getX1(), line.getY1());
            }
            counter++;
        }
    }

    private void resizeOnOpenFile(Canvas canvas, Canvas draftCanvas) {
        Double maxX = 0.0;
        Double maxY = 0.0;
        for (Line line: lines.getLines()) {
            if(line.getX0() > maxX)
                maxX = line.getX0();
            if(line.getX1() > maxX)
                maxX = line.getX1();
            if(line.getY0() > maxY)
                maxY = line.getY0();
            if(line.getY1() > maxY)
                maxY = line.getY1();
        }

        if(maxX > windowMinWidth) {
            windowMinWidth = maxX;
            canvas.setWidth(windowMinWidth);
            draftCanvas.setWidth(windowMinWidth);
            mainStage.setMinWidth(windowMinWidth);
        }

        if(maxY > windowMinHeight - Config.WINDOW_HEIGHT_DELTA - Config.ICONS_HEIGHT) {
            windowMinHeight = maxY + Config.ICONS_HEIGHT + Config.WINDOW_HEIGHT_DELTA;
            mainStage.setMinHeight(windowMinHeight+Config.WINDOW_HEIGHT_DELTA);
        }
    }

    private void keyHandler (KeyEvent event) {
        if(event.getCode() == KeyCode.F1) {
            InfoWindow.display();
        }
    }

    private void addEventsForCanvases(Canvas canvas, Canvas draftCanvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gc2 = draftCanvas.getGraphicsContext2D();

        draftCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                x0 = event.getX();
                y0 = event.getY();
            }
        });

        draftCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.SECONDARY) {
                Line line = lines.findNearLineByCoordinate(event.getX(), event.getY());
                if (line != null) {
                    lines.removeLine(line);
                    canvas.getGraphicsContext2D().clearRect(0,0,canvas.getWidth(),canvas.getHeight());
                    lines.drawLinesOnCanvas(canvas);
                }
            }
        });

        draftCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                Double x, y;
                x = event.getX();
                y = event.getY();
                if(x <= 0) x = 0.0;
                else if(x >= canvas.getWidth()) x = canvas.getWidth();
                if(y <= 0) y = 0.0;
                else if(y >= canvas.getHeight()) y = canvas.getHeight();
                x1 = x;
                y1 = y;
                gc2.setFill(Color.WHITE);
                gc2.clearRect(0, 0, gc2.getCanvas().getWidth(), gc2.getCanvas().getHeight());
                gc2.setStroke(Config.DRAWING_LINE_COLOR);
                gc2.setLineWidth(Config.DRAWING_LINEWIDTH);
                gc2.strokeLine(x0,y0,x1,y1);
            }
        });

        draftCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            x1 = event.getX();
            y1 = event.getY();
            if(event.getButton() == MouseButton.PRIMARY) {
                gc2.clearRect(0, 0, draftCanvas.getWidth(), draftCanvas.getHeight());
                if (!x0.equals(x1) || !y0.equals(y1)) {
                    gc.setStroke(Config.LINE_COLOR);
                    gc.setLineWidth(Config.LINEWIDTH);
                    gc.strokeLine(x0, y0, x1, y1);
                    lines.addLine(new Line(x0, y0, x1, y1));
                    Double max_x = x0 > x1 ? x0 : x1;
                    Double max_y = y0 > y1 ? y0 : y1;
                    if (max_x > windowMinWidth) {
                        windowMinWidth = max_x;
                        mainStage.setMinWidth(windowMinWidth);
                    }
                    if (max_y > windowMinHeight - Config.WINDOW_HEIGHT_DELTA - Config.ICONS_HEIGHT) {
                        windowMinHeight = max_y + Config.ICONS_HEIGHT + Config.WINDOW_HEIGHT_DELTA;
                        mainStage.setMinHeight(windowMinHeight);
                    }
                }
            }
        });

        draftCanvas.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            lines.drawLinesOnCanvas(canvas);
        });

        draftCanvas.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            Line line = lines.findNearLineByCoordinate(event.getX(), event.getY());
            if (hoveredLine) {
                lines.drawLinesOnCanvas(canvas);
            }
            if (line != null) {
                hoveredLine = true;

                for (Line ln: lines.getLines()) {
                    if (line.checkIntersection(ln) && ln != line) {
                        gc.setStroke(Config.INTERSECTION_COLOR);
                        gc.setLineWidth(Config.LINEWIDTH);
                        gc.strokeLine(ln.getX0(), ln.getY0(), ln.getX1(), ln.getY1());
                    }
                }

                gc.setStroke(Config.HOVER_LINE_COLOR);
                gc.setLineWidth(Config.LINEWIDTH);
                gc.strokeLine(line.getX0(), line.getY0(), line.getX1(), line.getY1());
            } else {
                hoveredLine = false;
            }
        });
    }

    private static void configureFileChooserOpen(final FileChooser fileChooser){
        fileChooser.setTitle("Open file");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }

    private static void configureFileChooserSave(final FileChooser fileChooser){
        fileChooser.setTitle("Save file");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.setInitialFileName("lines.mf");
    }
}
