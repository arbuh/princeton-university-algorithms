import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    public KdTree() {
        // construct an empty set of points
    }

    public boolean isEmpty() {
        return true;
    }

    public int size() {
        return 0;
    }

    public void insert(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
    }

    public boolean contains(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        return false;
    }

    public void draw() {

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        return null;
    }

    public Point2D nearest(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        return null;
    }

    public static void main(String[] args) {

    }
}
