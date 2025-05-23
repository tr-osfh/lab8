package app.logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class DragonVisual {
    private static final Color GREEN = Color.web("#00FF7F");
    private static final Color YELLOW = Color.web("#FFD700");
    private static final Color BLACK = Color.BLACK;
    private static double canvasWidth;
    private static double canvasHeight;



    public static void draw(GraphicsContext gc, double size, double x, double y) {

        double centerX = canvasWidth / 2;
        double centerY = canvasHeight / 2;

        double scaleFactor = size / 100.0;
        double bodyWidth = canvasWidth * 0.1 * scaleFactor;
        double bodyHeight = canvasHeight * 0.1 * scaleFactor;


        gc.setFill(GREEN);
        gc.fillRect(x - bodyWidth, y - bodyHeight / 2, bodyWidth, bodyHeight);
        gc.fillRect(x - bodyWidth - bodyWidth / 3, y + bodyHeight / 2, bodyWidth * 2.0, bodyHeight / 5);
        gc.fillRect(x - bodyWidth - bodyWidth / 3, y - bodyHeight + bodyHeight / 3.0, bodyWidth * 2.0, bodyHeight / 2);

    }

    public static void setCanvasWidth(double canvasWidth) {
        DragonVisual.canvasWidth = canvasWidth;
    }

    public static void setCanvasHeight(double canvasHeight) {
        DragonVisual.canvasHeight = canvasHeight;
    }
}