import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

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

        insertRec(point, this.root);
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

    public boolean contains(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }

        return containsRec(point, this.root);
    }

    private boolean containsRec(Point2D point, Node node) {
        if (node == null) {
            return false;
        }

        if (node.value.equals(point)) {
            return true;
        }

        double coordPoint, coordNode;
        if (node.alignment == Alignment.VERTICAL) {
            coordPoint = point.x();
            coordNode = node.value.x();
        }
        else {
            coordPoint = point.y();
            coordNode = node.value.y();
        }

        if (coordPoint < coordNode) {
            return containsRec(point, node.left);
        }
        else {
            return containsRec(point, node.right);
        }
    }

    public void draw() {
        drawRec(this.root);
    }

    private void drawRec(Node node) {
        if (node == null) {
            return;
        }

        drawRec(node.left);
        StdDraw.point(node.value.x(), node.value.y());
        drawRec(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        return rangeRec(rect, this.root);
    }

    private ArrayList<Point2D> rangeRec(RectHV rect, Node node) {
        ArrayList<Point2D> list = new ArrayList<>();

        if (node == null) {
            return list;
        }

        if (rect.contains(node.value)) {
            list.add(node.value);
        }

        double rectMin, rectMax, nodeCoord;
        if (node.alignment == Alignment.VERTICAL) {
            rectMin = rect.xmin();
            rectMax = rect.xmax();
            nodeCoord = node.value.x();
        }
        else {
            rectMin = rect.ymin();
            rectMax = rect.ymax();
            nodeCoord = node.value.y();
        }

        if (rectMin < nodeCoord && rectMax > nodeCoord) {
            ArrayList<Point2D> left = rangeRec(rect, node.left);
            list.addAll(left);
            ArrayList<Point2D> right = rangeRec(rect, node.right);
            list.addAll(right);
        }
        else if (rectMax < nodeCoord) {
            ArrayList<Point2D> left = rangeRec(rect, node.left);
            list.addAll(left);
        }
        else {
            ArrayList<Point2D> right = rangeRec(rect, node.right);
            list.addAll(right);
        }

        return list;
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
