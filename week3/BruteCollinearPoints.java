import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private static final double EPSILON = 1E-10;

    private int numberOfSegments;
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("argument to BruteCollinearPoints constructor cannot be null");
        }

        for (int i = 0; i < points.length; i++) {
            Point current = points[i];

            if (current == null) {
                throw new IllegalArgumentException(
                        "argument to BruteCollinearPoints constructor cannot contain a null item");
            }

            for (int j = i + 1; j < points.length; j++) {
                if (current.compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException(
                            "argument to BruteCollinearPoints constructor cannot contain a repeating point");
                }
            }
        }

        this.numberOfSegments = 0;
        this.segments = new LineSegment[0];
        this.segments = bruteforceSegments(points);
    }

    public int numberOfSegments() {
        return this.numberOfSegments;
    }

    public LineSegment[] segments() {
        return this.segments;
    }

    private LineSegment[] bruteforceSegments(Point[] points) {

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];

            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];
                double slopePQ = p.slopeTo(q);

                for (int k = j + 1; k < points.length; k++) {
                    Point r = points[k];
                    double slopePR = p.slopeTo(r);

                    if (areSlopesEqual(slopePQ, slopePR)) {
                        for (int l = k + 1; l < points.length; l++) {
                            Point s = points[l];
                            double slopePS = p.slopeTo(s);

                            if (areSlopesEqual(slopePQ, slopePS)) {
                                this.addSegment(new LineSegment(p, s));
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean areSlopesEqual(double s0, double s1) {
        return Math.abs(s0 - s1) < EPSILON;
    }

    private void addSegment(LineSegment segment) {
        int newNumber = this.numberOfSegments + 1;
        LineSegment[] newSegments = new LineSegment[newNumber];

        for (int i = 0; i < this.numberOfSegments; i++) {
            newSegments[i] = this.segments[i];
        }
        newSegments[newNumber - 1] = segment;

        this.numberOfSegments = newNumber;
        this.segments = newSegments;
    }

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
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
