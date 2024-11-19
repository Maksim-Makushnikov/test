package com.cgvsu.rasterization.objs;

import com.cgvsu.rasterization.DrawbleObj;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class IntrplTriangle implements DrawbleObj {
    private int[] A = new int[2];
    private int[] B = new int[2];
    private int[] C = new int[2];
    private int[] P = new int[2];
    private double w1;
    private double w2;
    private double w3;
    private double det;
    private Color colorA;
    private Color colorB;
    private Color colorC;
    private int[] Rectangle = new int[4];

    private static double getW1(int x1, int y1, int x2, int y2, int x3, int y3, double det, int px, int py) {
        return ((x2 * y3 - x3 * y2) + px * (y2 - y3) + py * (x3 - x2)) / det;
    }

    private static double getW2(int x1, int y1, int x2, int y2, int x3, int y3, double det, int px, int py) {
        return ((x3 * y1 - x1 * y3) + px * (y3 - y1) + py * (x1 - x3)) / det;
    }

    private static double getW3(int x1, int y1, int x2, int y2, int x3, int y3, double det, int px, int py) {
        return ((x1 * y2 - x2 * y1) + px * (y1 - y2) + py * (x2 - x1)) / det;
    }

    private static int[] getRectangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        int[] result = new int[4];

        result[0] = Math.min(Math.min(x1, x2), x3);
        result[1] = Math.min(Math.min(y1, y2), y3);
        result[2] = Math.max(Math.max(x1, x2), x3);
        result[3] = Math.max(Math.max(y1, y2), y3);
        return result;
    }

    public IntrplTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.A[0] = x1;
        this.A[1] = y1;
        this.B[0] = x2;
        this.B[1] = y2;
        this.C[0] = x3;
        this.C[1] = y3;
        this.Rectangle = getRectangle(x1, y1, x2, y2, x3, y3);
        this.det = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
        colorA = Color.rgb(255, 0, 0);
        colorB = Color.rgb(0, 255, 0);
        colorC = Color.rgb(0, 0, 255);
    }

    public IntrplTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color colorA, Color colorB, Color colorC) {
        this.A[0] = x1;
        this.A[1] = y1;
        this.B[0] = x2;
        this.B[1] = y2;
        this.C[0] = x3;
        this.C[1] = y3;
        this.Rectangle = getRectangle(x1, y1, x2, y2, x3, y3);
        this.det = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
        this.colorA = colorA;
        this.colorB = colorB;
        this.colorC = colorC;
    }


    @Override
    public void paint(GraphicsContext graphicsContext) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();


        for (int py = Rectangle[1]; py <= Rectangle[3]; py++) {
            for (int px = Rectangle[0]; px <= Rectangle[2]; px++) {
                w1 = getW1(A[0], A[1], B[0], B[1], C[0], C[1], det, px, py);
                w2 = getW2(A[0], A[1], B[0], B[1], C[0], C[1], det, px, py);
                w3 = getW3(A[0], A[1], B[0], B[1], C[0], C[1], det, px, py);
                if (w1 <= 1 && w1 >= 0 && w2 <= 1 && w2 >= 0 && w3 <= 1 && w3 >= 0) {
                    int Red = (int)Math.round((colorA.getRed() * w1 + colorB.getRed() * w2 + colorC.getRed()*w3)*255);
                    int Green = (int)Math.ceil((colorA.getGreen() * w1 + colorB.getGreen() * w2 + colorC.getGreen()*w3)*255);
                    int Blue = (int)Math.ceil((colorA.getBlue() * w1 + colorB.getBlue() * w2 + colorC.getBlue()*w3)*255);
                    Color color = Color.rgb(Red, Green, Blue);
                    pixelWriter.setColor(px, py, color);
                }
            }
        }
    }
}
