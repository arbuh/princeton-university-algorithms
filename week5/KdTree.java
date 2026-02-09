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

        Node(Point2D point, Alignment alignment) {
            this.value = point;
            this.alignment = alignment;
        }
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void insert(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }

        boolean inserted;
        if (this.root == null) {
            this.root = new Node(point, Alignment.VERTICAL);
            inserted = true;
        }
        else {
            inserted = insertRec(point, this.root);
        }

        if (inserted) {
            this.size++;
        }
    }

    private boolean insertRec(Point2D point, Node node) {
        // We skip duplicated points
        if (point.equals(node.value)) {
            return false;
        }

        double pointCoord, nodeCoord;
        Alignment nextAlighment;

        if (node.alignment == Alignment.VERTICAL) {
            pointCoord = point.x();
            nodeCoord = node.value.x();
            nextAlighment = Alignment.HORIZONTAL;
        }
        else {
            pointCoord = point.y();
            nodeCoord = node.value.y();
            nextAlighment = Alignment.VERTICAL;
        }

        if (pointCoord < nodeCoord) {
            if (node.left == null) {
                node.left = new Node(point, nextAlighment);
                return true;
            }
            else {
                return insertRec(point, node.left);
            }
        }
        else {
            if (node.right == null) {
                node.right = new Node(point, nextAlighment);
                return true;
            }
            else {
                return insertRec(point, node.right);
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

        double pointCoord, nodeCoord;
        if (node.alignment == Alignment.VERTICAL) {
            pointCoord = point.x();
            nodeCoord = node.value.x();
        }
        else {
            pointCoord = point.y();
            nodeCoord = node.value.y();
        }

        if (pointCoord < nodeCoord) {
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

    public Point2D nearest(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        return nearestRec(point, this.root);
    }

    private Point2D nearestRec(Point2D point, Node node) {
        if (node == null) {
            return null;
        }

        Point2D result = node.value;
        double distance = point.distanceSquaredTo(node.value);

        // We choose coordinates to check based on the node's alignment
        double pointCoord, nodeCoord;
        if (node.alignment == Alignment.VERTICAL) {
            pointCoord = point.x();
            nodeCoord = node.value.x();
        }
        else {
            pointCoord = point.y();
            nodeCoord = node.value.y();
        }

        // We decide where to go first based on the proximity of sub-planes to the target point
        Node first = null;
        Node second = null;
        if (pointCoord < nodeCoord) {
            first = node.left;
            second = node.right;
        }
        else {
            first = node.right;
            second = node.left;
        }

        // We obtain a candidate from the first checked plane
        Point2D firstSubtreeResult = nearestRec(point, first);

        // We update the result if the point from the first checked subtree is closer to the target point
        if (firstSubtreeResult != null) {
            double firstSubtreeDistance = point.distanceSquaredTo(firstSubtreeResult);
            if (firstSubtreeDistance < distance) {
                result = firstSubtreeResult;
                distance = firstSubtreeDistance;
            }
        }

        // We decide if we can stop here without checking the second plane
        double distanceToPlane = Math.abs(pointCoord - nodeCoord);
        if (distanceToPlane > distance) {
            return result;
        }

        // Continue, if there could be a closer point in the second plane
        Point2D secondSubtreeResult = nearestRec(point, second);

        // Update the result if the point from the second subtree is closer to the target point
        if (secondSubtreeResult != null) {
            double secondSubtreeDistance = point.distanceSquaredTo(secondSubtreeResult);
            if (secondSubtreeDistance < distance) {
                result = secondSubtreeResult;
            }
        }

        return result;
    }

    public static void main(String[] args) {

    }
}
