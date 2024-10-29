package me.BRZeph.utils.pathFinding;

public class Node {
    public int x, y;
    public boolean walkable;
    public Node parent;
    public float gCost; // Cost from the start node
    public float hCost; // Heuristic cost to the end node
    public float fCost; // Total cost (g + h)

    public Node(int x, int y, boolean walkable) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
    }

    public void calculateFCost() {
        this.fCost = gCost + hCost;
    }
}
