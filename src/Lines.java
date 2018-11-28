import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Lines {
    private List<Line> lines;

    public Lines() {
        this.lines = new ArrayList<>();
    }

    public Lines(List<Line> lines) {
        this.lines = lines;
    }

    public Lines(File file) throws Exception {
        lines = new ArrayList<Line>();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String str = sc.nextLine();
                Scanner strSc = new Scanner(str).useLocale(Locale.US);
                Double coords[] = new Double[4];
                for (int i = 0; i < 4 ; i++) {
                    coords[i] = strSc.nextDouble();
                }
                if (strSc.hasNextDouble()) {
                    System.out.println("strSc.hasNext()");
                    throw new Exception("Incorrect file format");
                }
                strSc.close();
                lines.add(new Line(coords[0], coords[1], coords[2], coords[3]));
            }
            sc.close();
        }  catch (Exception e) {
            e.printStackTrace();
            lines = null;
            System.out.println("Incorrect format");
            throw new Exception("Incorrect format");
        }
    }

    public List<Line> getLines() {
        return lines;
    }

    public void addLine (Line line) {
        if (this.lines == null) {
            this.lines = new ArrayList<>();
        }
        lines.add(line);
    }

    public void drawLinesOnCanvas (Canvas canvas) {
        if (lines == null) return;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Config.LINE_COLOR);
        gc.setLineWidth(Config.LINEWIDTH);

        for(Line line : this.lines) {
            gc.strokeLine(line.getX0(), line.getY0(), line.getX1(), line.getY1());
        }
    }

    public Boolean saveToFile (File file) {
        if (this.lines == null) {
            this.lines = new ArrayList<>();
        }
        if (file.exists()) {
            file.delete();
        }

        List<String> linesToSave = new ArrayList<>();
        for (Line line : this.lines) {
            linesToSave.add(line.getX0()+" "+line.getY0()+" "+line.getX1()+" "+line.getY1());
        }
        Path filePath = Paths.get(file.getAbsolutePath());
        try {
            Files.write(filePath, linesToSave, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clearAll (Canvas canvas) {
        lines.clear();
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private Double distance(Line line, Double x, Double y) {
        double A = (line.getY0() - line.getY1());
        double B = (line.getX1()- line.getX0());
        double C = (line.getX0()*line.getY1() - line.getX1()*line.getY0());
        return Math.abs((A*x + B*y + C)/Math.sqrt(A*A + B*B));
    }

    private Boolean inXLimits(Line line, Double x, Double y) {
        if((line.getX0() <= x) && (line.getX1() >= x)) {
            return true;
        }
        return (line.getX1() <= x) && (line.getX0() >= x);
    }

    public Line findNearLineByCoordinate(Double x, Double y) {
        if (lines == null) return null;
        for(Line line : lines) {
            if (inXLimits (line, x, y) && (this.distance(line, x, y) < Config.MAX_DELTA_FOR_LINE_DELETE)) {
                return line;
            }
        }
        return null;
    }

    public void removeLine(Line line) {
        this.lines.remove(line);
    }

}
