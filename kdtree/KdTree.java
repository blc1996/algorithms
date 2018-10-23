import java.util.TreeSet;
import java.util.Iterator;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private KdNode root;
    private int size;

    private class KdNode{
        public int depth;
        public Point2D point;
        // public KdNode parent;
        public KdNode left;
        public KdNode right;

        public KdNode(int d, Point2D point){
            this.point = point;
            depth = d;
            // parent = p;
            left = null;
            right = null;
        }
    }


    public         KdTree(){
        root = null;
        size = 0;
    }                               // construct an empty set of points


    public           boolean isEmpty(){
        return size == 0;
    }                      // is the set empty?


    public               int size(){
        return size;
    }                         // number of points in the set


    public              void insert(Point2D p){
        if(root == null){
            root = new KdNode(1, p);
            size++;
            return;
        }
        KdNode current = root;
        int count = 1;
        while(current != null){
            // System.out.println("Im in");
            count++;
            if(current.depth % 2 == 1){
                if(p.x() >= current.point.x()){
                    if(current.right == null && !current.point.equals(p)){
                        current.right = new KdNode(count, p);
                        size++;
                        return;
                    }
                    current = current.right;
                }else{
                    if(current.left == null && !current.point.equals(p)){
                        current.left = new KdNode(count, p);
                        size++;
                        return;
                    }
                    current = current.left;
                }
            }else{
                if(p.y() >= current.point.y()){
                    if(current.right == null && !current.point.equals(p)){
                        current.right = new KdNode(count, p);
                        size++;
                        return;
                    }
                    current = current.right;
                }else{
                    if(current.left == null && !current.point.equals(p)){
                        current.left = new KdNode(count, p);
                        size++;
                        return;
                    }
                    current = current.left;
                }
            }
            // current = current.left;
        }
    }              // add the point to the set (if it is not already in the set)


    public           boolean contains(Point2D p){
        KdNode current = root;
        while(current != null){
            if(p.equals(current.point))
                return true;
            if(current.depth % 2 ==1){
                if(p.x() >= current.point.x()){
                    current = current.right;
                }else{
                    current = current.left;
                }
            }else{
                if(p.y() >= current.point.y()){
                    current = current.right;
                }else{
                    current = current.left;
                }
            }
        }
        return false;
    }            // does the set contain point p?


    public              void draw(){
        StdDraw.clear();
        // if(root.left == null)
        //     System.out.println("sth wrong!");
        DFSDraw(root);
    }                         // draw all points to standard draw

    private void DFSDraw(KdNode current){
        if(current == null)
            return;
        current.point.draw();
        DFSDraw(current.left);
        DFSDraw(current.right);
    }


    public Iterable<Point2D> range(RectHV rect){
        TreeSet<Point2D> out = new TreeSet<Point2D>();
        rangeSearch(out, root, rect);
        return out;
    }             // all points that are inside the rectangle (or on the boundary)

    private void rangeSearch(TreeSet<Point2D> out, KdNode currentNode, RectHV rect){
        if(currentNode == null)
            return;
        if(rect.contains(currentNode.point)){
            out.add(currentNode.point);
            rangeSearch(out, currentNode.left, rect);
            rangeSearch(out, currentNode.right, rect);
            return;
        }
        if(currentNode.depth % 2 == 1){
            //search according to x
            if(rect.xmax() < currentNode.point.x()){
                rangeSearch(out, currentNode.left, rect);
            }else if(rect.xmin() >= currentNode.point.x()){
                rangeSearch(out, currentNode.right, rect);
            }else{
                rangeSearch(out, currentNode.left, rect);
                rangeSearch(out, currentNode.right, rect);
            }
        }else{
            //search according to y
            if(rect.ymax() < currentNode.point.y()){
                rangeSearch(out, currentNode.left, rect);
            }else if(rect.ymin() >= currentNode.point.y()){
                rangeSearch(out, currentNode.right, rect);
            }else{
                rangeSearch(out, currentNode.left, rect);
                rangeSearch(out, currentNode.right, rect);
            }
        }
    }


    public           Point2D nearest(Point2D p){
        // double minDistance = Double.POSITIVE_INFINITY ;
        return neareatSearch(root, root.point, p);
    }             // a nearest neighbor in the set to point p; null if the set is empty

    private Point2D neareatSearch(KdNode current, Point2D out, Point2D p){
        double minDis = p.distanceTo(out);
        Point2D temp;
        if(current == null)
            return out;
        if(minDis > p.distanceTo(current.point)){
            temp = current.point;
        }else{
            temp = out;
        }
        if(current.depth % 2 == 1){
            if(p.x() < current.point.x()){
                Point2D tempPoint = neareatSearch(current.left, temp, p);
                if(!tempPoint.equals(temp)){
                    if(Math.abs(p.x() - current.point.x()) > p.distanceTo(tempPoint)){
                        return tempPoint;
                    }else{
                        return neareatSearch(current.right, tempPoint, p);
                    }
                }else{
                    return neareatSearch(current.right, tempPoint, p);
                }
            }else{
                Point2D tempPoint = neareatSearch(current.right, temp, p);
                if(!tempPoint.equals(temp)){
                    if(Math.abs(p.x() - current.point.x()) > p.distanceTo(tempPoint)){
                        return tempPoint;
                    }else{
                        return neareatSearch(current.left, tempPoint, p);
                    }
                }else{
                    return neareatSearch(current.left, tempPoint, p);
                }
            }
        }else{
            if(p.y() < current.point.y()){
                Point2D tempPoint = neareatSearch(current.left, temp, p);
                if(!tempPoint.equals(temp)){
                    if(Math.abs(p.y() - current.point.y()) > p.distanceTo(tempPoint)){
                        return tempPoint;
                    }else{
                        return neareatSearch(current.right, tempPoint, p);
                    }
                }else{
                    return neareatSearch(current.right, tempPoint, p);
                }
            }else{
                Point2D tempPoint = neareatSearch(current.right, temp, p);
                if(!tempPoint.equals(temp)){
                    if(Math.abs(p.y() - current.point.y()) > p.distanceTo(tempPoint)){
                        return tempPoint;
                    }else{
                        return neareatSearch(current.left, tempPoint, p);
                    }
                }else{
                    return neareatSearch(current.left, tempPoint, p);
                }
            }
        }
    }


    public static void main(String[] args){

    }                  // unit testing of the methods (optional)
}
