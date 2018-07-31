import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints {
    private LineSegment[] colliners;
    private int number;

    public FastCollinearPoints(Point[] points){
        if (points == null) {
            throw new IllegalArgumentException("argument is null");
        }
        Point[] inputPoint = new Point[points.length];
        for(int i = 0; i < points.length; i++){
            if(points[i] == null)
                throw new IllegalArgumentException("argument is null");
            inputPoint[i] = points[i];
        }
        Arrays.sort(inputPoint);
        for(int i = 1; i < inputPoint.length; i++){
            if(inputPoint[i].compareTo(inputPoint[i-1]) == 0){
                throw new IllegalArgumentException("argument is null");
            }
        }
        int count = 0;
        double tempSlope = 0;
        number = 0;
        Point[] tempPoint;
        LineSegment[] temp = new LineSegment[10 * inputPoint.length];
        for(int i = 0; i < points.length; i++){
            Arrays.sort(inputPoint, points[i].slopeOrder());
            if(inputPoint.length > 1)
            tempSlope = inputPoint[0].slopeTo(inputPoint[1]);
            count = 1;
            for(int j = 2; j < inputPoint.length; j++){
                if(tempSlope == inputPoint[0].slopeTo(inputPoint[j])){
                    count++;
                    if(j == inputPoint.length - 1 && count > 2){
                        tempPoint = new Point[count + 1];
                        for(int k = 0; k < count; k++){
                            tempPoint[k] = inputPoint[j - k];
                        }
                        tempPoint[count] = inputPoint[0];
                        Arrays.sort(tempPoint);
                        temp[number++] = new LineSegment(tempPoint[0], tempPoint[count]);
                        for(int l = 0; l < number - 1; l++){
                            if(temp[l].toString().equals(temp[number - 1].toString())){
                                number--;
                                break;
                            }
                        }
                    }
                }else{
                    if(count > 2){
                        tempPoint = new Point[count + 1];
                        for(int k = 0; k < count; k++){
                            tempPoint[k] = inputPoint[j - 1 - k];
                        }
                        tempPoint[count] = inputPoint[0];
                        Arrays.sort(tempPoint);
                        temp[number++] = new LineSegment(tempPoint[0], tempPoint[count]);
                        for(int l = 0; l < number - 1; l++){
                            if(temp[l].toString().equals(temp[number - 1].toString())){
                                number--;
                                break;
                            }
                        }
                    }
                    tempSlope = inputPoint[0].slopeTo(inputPoint[j]);
                    count = 1;
                }
            }

        }
        colliners = new LineSegment[number];
        for(int i = 0; i < number; i++){
            colliners[i] = temp[i];
        }
    }     // finds all line segments containing 4 or more points


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
    Arrays.sort(points, points[5].slopeOrder());
    double tempSlope = points[0].slopeTo(points[1]);
            int count = 1;
            for(int j = 2; j < points.length; j++){
                if(tempSlope == points[0].slopeTo(points[j])){
                    count++;
                }else{
                    if(count > 2){
                        // tempPoint = new Point[count + 1];
                        // for(int k = 0; k < count; k++){
                        //     tempPoint[k] = points[j - 1 - k];
                        // }
                        // tempPoint[count] = points[0];
                        // Arrays.sort(tempPoint);
                        // temp[number++] = new LineSegment(tempPoint[0], tempPoint[count]);
                        // for(int l = 0; l < number - 1; l++){
                        //     if(temp[l].toString().equals(temp[number - 1].toString())){
                        //         number--;
                        //         break;
                        //     }
                        // }
                        Point[] tempPoint = new Point[count + 1];
                        for(int k = 0; k < count; k++){
                            tempPoint[k] = points[j - 1 - k];
                        }
                        tempPoint[count] = points[0];
                        Arrays.sort(tempPoint);
                        StdOut.println("find");
                        StdOut.println(tempPoint[0]);
                        StdOut.println(tempPoint[count]);
                    }
                    tempSlope = points[0].slopeTo(points[j]);
                    count = 1;
                }
            }
    for (Point p : points) {
        StdOut.println(p);
        StdOut.println(points[0].slopeTo(p));
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
    StdOut.println(collinear.numberOfSegments());
}
}
