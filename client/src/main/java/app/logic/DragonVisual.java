package app.logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class DragonVisual {
    private static double canvasWidth;
    private static double canvasHeight;



    public static void draw(GraphicsContext gc, double size, double x, double y, Color color) {

        double centerX;
        double centerY;

        double scaleFactor = size / 100.0;
        double bodyWidth = canvasWidth * 0.1 * scaleFactor;
        double bodyHeight = canvasHeight * 0.1 * scaleFactor;

        double arcWidth = bodyWidth * 0.1;
        double arcHeight = bodyHeight * 0.1;

        gc.setFill(color);
        gc.fillRoundRect(x - bodyWidth, y - bodyHeight / 2, bodyWidth, bodyHeight , arcWidth, arcHeight);
        gc.fillRoundRect(x - bodyWidth - bodyWidth / 3, y + bodyHeight / 2, bodyWidth * 2.0, bodyHeight / 4, arcWidth, arcHeight);
        gc.fillRoundRect(x - bodyWidth - bodyWidth / 4, y, bodyWidth, bodyHeight / 6, arcWidth, arcHeight);
        gc.fillRoundRect(x - bodyWidth - bodyWidth / 3, y - bodyHeight + bodyHeight / 3.0, bodyWidth * 1.32, bodyHeight / 2, arcWidth, arcHeight);
        gc.setFill(Color.BLACK);
        gc.fillOval(x - 0.7 * bodyWidth, y - 0.5 * bodyHeight, bodyWidth / 10.0, bodyWidth / 10.0);
        gc.setFill(color.darker());

        double angle = -35;

        gc.save();

        double rectX = x - 0.2 * bodyWidth;
        double rectY = y - bodyHeight / 3;
        double rectWidth = bodyWidth * 0.5;
        double rectHeight = bodyHeight * 0.7;

        centerX = rectX + rectWidth / 2;
        centerY = rectY + rectHeight / 2;

        gc.translate(centerX, centerY);

        gc.rotate(angle);

        gc.fillRoundRect(
                -rectWidth / 2,
                -rectHeight / 2,
                rectWidth,
                rectHeight,
                arcWidth,
                arcHeight
        );
        gc.restore();
    }

    public static void setCanvasWidth(double canvasWidth) {
        DragonVisual.canvasWidth = canvasWidth;
    }

    public static void setCanvasHeight(double canvasHeight) {
        DragonVisual.canvasHeight = canvasHeight;
    }
}