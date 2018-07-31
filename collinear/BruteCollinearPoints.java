import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] colliners;
    private int number;

    public BruteCollinearPoints(Point[] points){
        if (points == null) {
            throw new IllegalArgumentException("argument is null");
        }
        Point[] tempPoint = new Point[points.length];
        for(int i = 0; i < points.length; i++){
            if(points[i] == null)
                throw new IllegalArgumentException("argument is null");
            tempPoint[i] = points[i];
        }
        Arrays.sort(tempPoint);
        for(int i = 1; i < tempPoint.length; i++){
            if(tempPoint[i].compareTo(tempPoint[i-1]) == 0 || tempPoint[i] == null){
                throw new IllegalArgumentException("argument is null");
            }
        }
        number = 0;
        LineSegment[] temp = new LineSegment[tempPoint.length];
        for(int i = 0; i < tempPoint.length; i++){
            for(int j = i + 1; j < tempPoint.length; j++){
                double slope1 = tempPoint[i].slopeTo(tempPoint[j]);
                for(int k = j + 1; k < tempPoint.length; k++){
                    double slope2 = tempPoint[j].slopeTo(tempPoint[k]);
                    if(slope1 == slope2){
                        for(int l = k + 1; l < tempPoint.length; l++){
                            double slope3 = tempPoint[k].slopeTo(tempPoint[l]);
                            if(slope3 == slope1){
                                temp[number++] = new LineSegment(tempPoint[i], tempPoint[l]);
                                // StdOut.println("point:");
                                // StdOut.println(points[i]);
                                // StdOut.println(slope1);
                                // StdOut.println(points[j]);
                                // StdOut.println(slope2);
                                // StdOut.println(points[k]);
                                // StdOut.println(slope3);
                                // StdOut.println(points[l]);
                            }
                        }
                    }
                }
            }
        }
        colliners = new LineSegment[number];
        for(int i = 0; i < number; i++){
            colliners[i] = temp[i];
        }
    }    // finds all line segments containing 4 points


    public int numberOfSegments(){
        return number;
    }        // the number of line segments


    public LineSegment[] segments(){
        LineSegment[] tempcolliners = new LineSegment[number];
        for(int i = 0; i < number; i++){
            tempcolliners[i] = colliners[i];
        }
        return tempcolliners;
    }                // the line segments

    public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    // Arrays.sort(points);
    // Arrays.sort(points, points[1].slopeOrder());
    for (Point p : points) {
        StdOut.println(p);
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (Point p : points) {
        StdOut.println(p);
        p.draw();
    }
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();

    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    Point test1 = new Point(6000, 7000);
    Point test2 = new Point(5000, 7000);
    // double out1 = 1.0/2.0;
    // // double out1 = -1.15 / 2;
    Point test3 = new Point(4000, 7000);
    StdOut.println(test1.slopeTo(test2));
}

}
