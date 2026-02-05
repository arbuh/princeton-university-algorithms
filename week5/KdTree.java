import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int size;

    public KdTree() {
        this.root = null;
    }

    private enum Alignment {
        VERTICAL, HORIZONTAL
    }

    private static final class Node {
        Point2D value;
        Alignment alignment;

        Node left;
        Node right;
        // TODO: remove if no use
        Node parent;

        Node(Point2D point, Alignment alignment) {
            this.value = point;
            this.alignment = alignment;
        }
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return 0;
    }

    public void insert(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }

        if (this.root == null) {
            this.root = new Node(point, Alignment.VERTICAL);
        }

        insertRec(point, root);
        this.size++;
    }

    private void insertRec(Point2D point, Node node) {
        double coordPoint, coordNode;
        Alignment nextAlighment;

        if (node.alignment == Alignment.VERTICAL) {
            coordPoint = point.x();
            coordNode = node.value.x();
            nextAlighment = Alignment.HORIZONTAL;
        }
        else {
            coordPoint = point.y();
            coordNode = node.value.y();
            nextAlighment = Alignment.VERTICAL;
        }

        if (coordPoint < coordNode) {
            if (node.left == null) {
                node.left = new Node(point, nextAlighment);
            }
            else {
                insertRec(point, node.left);
            }
        }
        else {
            if (node.right == null) {
                node.right = new Node(point, nextAlighment);
            }
            else {
                insertRec(point, node.right);
            }
        }
    }

    // TODO
    public boolean contains(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        return false;
    }

    public void draw() {
        drawRec(root);
    }

    private void drawRec(Node node) {
        if (node == null) {
            return;
        }

        drawRec(node.left);
        StdDraw.point(node.value.x(), node.value.y());
        drawRec(node.right);
    }

    // TODO
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        return null;
    }

    // TODO
    public Point2D nearest(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        return null;
    }

    public static void main(String[] args) {

    }
}
