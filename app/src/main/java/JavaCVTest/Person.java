package JavaCVTest;

import java.util.LinkedList;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Person {

    // Singleton instance
    private static Person instance = null;

    private Rect personRect;
    private String name;

    private LinkedList<Rect> lastRects;
    private double averageMovement = 0;

    private boolean isMoving;

    // Private constructor
    private Person() {
    }

    // Getter for the singleton instance
    public static Person getInstance(String name) {

        if (instance == null) {
            instance = new Person();
            instance.name = name;
            instance.lastRects = new LinkedList<>();
            instance.isMoving = false;
        }

        return instance;
    }

    public void show(Mat frame) {

        if (personRect != null) {

            Mat roi;

            try {

                if (personRect.width >= frame.width() || personRect.height >= frame.height()) {
                    roi = new Mat(frame, new Rect(personRect.x, personRect.y,
                            personRect.width - (Math.abs(frame.width() - personRect.width)),
                            personRect.height - (Math.abs(frame.height() - personRect.height))));
                } else {
                    roi = new Mat(frame, personRect);
                }

                double[] beep = interpolate(personRect.width, lastRects.getLast().width, 2);

                System.out.println(beep[0] + " " + beep[1]);

                // -------------------------------

                // int x = 0;
                // int y = 0;

                // if (lastRects.size() >= 30) {

                //     // x = (int) lastRects.stream().mapToInt(r -> r.x).average().getAsDouble();
                //     // y = (int) lastRects.stream().mapToInt(r -> r.y).average().getAsDouble();

                //     // x = lastRects.getFirst().x;
                //     // y = lastRects.getFirst().y;

                //     double linearInterpolation = y
                //             + (lastRects.getLast().x - x) * ((y - lastRects.getLast().y) / (x - lastRects.getLast().x));

                //     System.out.println(linearInterpolation);

                // }

                // -------------------------------

                // averageMovement = 0;
                // int index = 0;

                // for (Rect rect : lastRects) {

                // averageMovement += rect.width;

                // index++;
                // }

                // averageMovement /= index;
                // averageMovement /= lastRects.getLast().width;

                // if (averageMovement >= 0.60 && averageMovement <= 1.40) {
                // isMoving = false;
                // // System.out.println();
                // } else {
                // instance.isMoving = true;
                // // System.out.println("Moving");
                // }

                // System.out.println("Average movement: " + averageMovement);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void setPersonRect(Rect personRect) {

        this.personRect = personRect;

        if (lastRects.size() > 30) {
            lastRects.removeLast();
        }

        lastRects.addFirst(personRect);

    }

    public Rect getPersonRect() {
        return personRect;
    }

    public String getName() {
        return name;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public LinkedList<Rect> getLastRects() {
        return lastRects;
    }

    // Get average movement
    public double getAverageMovement() {
        return averageMovement;
    }

    public boolean getIsMoving() {
        return isMoving;
    }

    /***
     * Interpolating method
     * 
     * @param start start of the interval
     * @param end   end of the interval
     * @param count count of output interpolated numbers
     * @return array of interpolated number with specified count
     */
    public static double[] interpolate(double start, double end, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("interpolate: illegal count!");
        }
        double[] array = new double[count + 1];

        for (int i = 0; i <= count; ++i) {
            array[i] = start + i * (end - start) / count;
        }
        return array;
    }

}