import java.util.TreeSet;
import java.util.Iterator;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private TreeSet<Point2D> points;


    public         PointSET(){
        points = new TreeSet<Point2D>();
    }                               // construct an empty set of points


    public           boolean isEmpty(){
        return points.isEmpty();
    }                      // is the set empty?


    public               int size(){
        return points.size();
    }                         // number of points in the set


    public              void insert(Point2D p){
        points.add(p);
    }              // add the point to the set (if it is not already in the set)


    public           boolean contains(Point2D p){
        return points.contains(p);
    }            // does the set contain point p?


    public              void draw(){
        StdDraw.clear();
        for (Iterator<Point2D> iter = points.iterator(); iter.hasNext();) {
            Point2D p = iter.next();
            p.draw();
        }
    }                         // draw all points to standard draw


    public Iterable<Point2D> range(RectHV rect){
        TreeSet<Point2D> out = new TreeSet<Point2D>();
        for (Iterator<Point2D> iter = points.iterator(); iter.hasNext();) {
            Point2D p = iter.next();
            if(rect.contains(p)){
                out.add(p);
            }
        }
        return out;
    }             // all points that are inside the rectangle (or on the boundary)


    public           Point2D nearest(Point2D p){
        double minDistance = Double.POSITIVE_INFINITY ;
        Point2D output = null;
        for (Iterator<Point2D> iter = points.iterator(); iter.hasNext();) {
            Point2D point = iter.next();
            if(point.distanceTo(p) < minDistance){
                minDistance = point.distanceTo(p);
                output = point;
            }
        }
        return output;
    }             // a nearest neighbor in the set to point p; null if the set is empty


    public static void main(String[] args){

    }                  // unit testing of the methods (optional)
}
