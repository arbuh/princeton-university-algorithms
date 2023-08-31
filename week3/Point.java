public class Point implements Comparable<Point> {
    private int x;
    private int y;

    public Point(int x, int y) {
        if (x < 0 || x > 32767 || y < 0 || y > 32767) {
            throw new IllegalArgumentException("x and y must be between 0 and 32767");
        }
        x = x;
        y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public   void draw()                               // draws this point

    public   void drawTo(Point that)                   // draws the line segment from this point to that point

    public String toString()                           // string representation

    public int compareTo(Point that){
        if ((y < that.getY()) || (y == that.getY() && x < that.getX())){
            return -1;
        }
        else if (y == that.getY() && x == that.getX(){
            return 0;
        }
        else {
            return -1;
        }
    }

    public double slopeTo(Point that){
        if (y == that.getY() && x == that.getX()){
            return Double.NEGATIVE_INFINITY;
        }
        else if (y == that.getY()){
            return 0;
        }
        else if (x == that.getX()){
            return Double.POSITIVE_INFINITY;
        }
        else {
            return (double) (that.getY() − y) / (double) (that.getY() − x);
        }

    }

    public Comparator<Point> slopeOrder()              // compare two points by slopes they make with this point
}
