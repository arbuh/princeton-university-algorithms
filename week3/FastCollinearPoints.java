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
            Arrays.sort(points, current.slopeOrder());

        }

    }

    // This methods works properly only if `points` are sorted by coordinates and the slope with `referencePoint`
    private findSegment(Point referencePoint, Point[] points){
        int nrOfEqualSlopes = 1;
        double referenceSlope;
        Point lastPoint;

        for (Point point: points){
            double slope = point.slopeTo(referencePoint);

           if (areSlopesEqual(slope, referenceSlope)) {
                lastPoint = point;
                nrOfEqualSlopes++;
            } else if (nrOfEqualSlopes >= MIN_SEGMENT_LENGTH) {
                this.addSegment(new LineSegment(referencePoint, lastPoint));
                break;
            } else {
                lastPoint = null;
                nrOfEqualSlopes = 1;
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
}
