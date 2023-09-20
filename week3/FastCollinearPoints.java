import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private static final double EPSILON = 1E-10;
    private static final int MIN_SEGMENT_LENGTH = 4;

    private int numberOfSegments;
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
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
        this.segments = findSegments(points);
    }

    public int numberOfSegments() {
        return this.numberOfSegments;
    }

    public LineSegment[] segments() {
        return this.segments;
    }

    private LineSegment[] findSegments(Point[] points) {
        // First, sort points by coordinates
        Arrays.sort(points);

        for (int i = 0; i < points.length; i++) {
            Point current = points[i];
            Point[] pointsBySlope = copyArray(points);
            Arrays.sort(pointsBySlope, current.slopeOrder());
            findSegment(current, pointsBySlope);
        }

    }

    // This methods works properly only if `points` are sorted by coordinates and then by the slope with `referencePoint`
    private void findSegment(Point referencePoint, Point[] points){
        int nrOfEqualSlopes = 1;
        double referenceSlope;
        Point lastPoint;

        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            double slope = point.slopeTo(referencePoint);

           if (areSlopesEqual(slope, referenceSlope)) {
                // If the reference point goes after the point, we assume it's a duplicated segment,
                // because we work with an array sorted by coordinates and thereby the segment must be found before with the reference to the point
                if (referencePoint.compareTo(point) == 1){
                    break;
                }

                lastPoint = point;
                nrOfEqualSlopes++;
            } else if (nrOfEqualSlopes >= MIN_SEGMENT_LENGTH) {
                this.addSegment(new LineSegment(referencePoint, lastPoint));
                break;
            } else {
                lastPoint = null;
                nrOfEqualSlopes = 1;
            }

            // Add segment if we are in the end of the array and the segment minimal length is fulfilled
            if (i == points.length - 1 && nrOfEqualSlopes >= MIN_SEGMENT_LENGTH){
                this.addSegment(new LineSegment(referencePoint, lastPoint));
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

    private Point[] copyArray(Point[] arr) {
        Point[] copy = new Point[arr.length];
        for (int i = 0; i < arr.length; i++)
            copy[i] = arr[i];
        return copy;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
