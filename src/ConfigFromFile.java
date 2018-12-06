import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigFromFile {
    private final static String confPath = "resources/config.conf";

    public static void readConfig() throws IOException {
        double[] values = new double[3];
        Properties props = new Properties();
        props.load(new FileInputStream(new File(confPath)));

        Config.LINEWIDTH = Double.valueOf(props.getProperty("LINEWIDTH", "3.0"));
        Config.DRAWING_LINEWIDTH = Double.valueOf(props.getProperty("DRAWING_LINEWIDTH", "3.0"));

        try {
            String[] parts = props.getProperty("LINE_COLOR").split(";");
            if (parts.length == 3) {
                for (int i = 0; i < 3; i++) {
                    values[i] = Double.valueOf(parts[i]);
                    values[i] = values[i] > 1.0 ? 1.0 : values[i];
                    values[i] = values[i] < 0.0 ? 0.0 : values[i];
                }
                Config.LINE_COLOR = Color.color(values[0], values[1], values[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String[] parts = props.getProperty("HOVER_LINE_COLOR").split(";");
            if (parts.length == 3) {
                for (int i = 0; i < 3; i++) {
                    values[i] = Double.valueOf(parts[i]);
                    values[i] = values[i] > 1.0 ? 1.0 : values[i];
                    values[i] = values[i] < 0.0 ? 0.0 : values[i];
                }
                Config.HOVER_LINE_COLOR = Color.color(values[0], values[1], values[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String[] parts = props.getProperty("DRAWING_LINE_COLOR").split(";");
            if (parts.length == 3) {
                for (int i = 0; i < 3; i++) {
                    values[i] = Double.valueOf(parts[i]);
                    values[i] = values[i] > 1.0 ? 1.0 : values[i];
                    values[i] = values[i] < 0.0 ? 0.0 : values[i];
                }
                Config.DRAWING_LINE_COLOR = Color.color(values[0], values[1], values[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String[] parts = props.getProperty("INTERSECTION_COLOR").split(";");
            if (parts.length == 3) {
                for (int i = 0; i < 3; i++) {
                    values[i] = Double.valueOf(parts[i]);
                    values[i] = values[i] > 1.0 ? 1.0 : values[i];
                    values[i] = values[i] < 0.0 ? 0.0 : values[i];
                }
                Config.INTERSECTION_COLOR = Color.color(values[0], values[1], values[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String[] parts = props.getProperty("SOLUTION_LINE_COLOR").split(";");
            if (parts.length == 3) {
                for (int i = 0; i < 3; i++) {
                    values[i] = Double.valueOf(parts[i]);
                    values[i] = values[i] > 1.0 ? 1.0 : values[i];
                    values[i] = values[i] < 0.0 ? 0.0 : values[i];
                }
                Config.SOLUTION_LINE_COLOR = Color.color(values[0], values[1], values[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
