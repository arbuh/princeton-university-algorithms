import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> set;

    public PointSET() {
        this.set = new SET<>();
    }

    public boolean isEmpty() {
        return this.set.isEmpty();
    }

    public int size() {
        return this.set.size();
    }

    public void insert(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }

        this.set.add(point);
    }

    public boolean contains(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }

        return this.set.contains(point);
    }

    public void draw() {
        for (Point2D point : this.set) {
            StdDraw.point(point.x(), point.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        SET<Point2D> inside = new SET<>();
        for (Point2D point : this.set) {
            if (rect.contains(point)) {
                inside.add(point);
            }
        }

        return inside;
    }

    public Point2D nearest(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }

        if (this.set.isEmpty()) {
            return null;
        }

        Point2D floor = this.set.floor(point);
        Point2D ceiling = this.set.ceiling(point);

        if (point.distanceTo(floor) < point.distanceTo(ceiling)) {
            return floor;
        }
        else {
            return ceiling;
        }
    }

    public static void main(String[] args) {

    }
}
