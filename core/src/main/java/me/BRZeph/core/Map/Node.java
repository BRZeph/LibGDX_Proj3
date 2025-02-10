package me.BRZeph.core.Map;

public class Node implements Comparable<Node> {
    public int x, y;
    public boolean walkable;
    public float gCost; // Cost from start node
    public float hCost; // Heuristic cost to end node
    public float fCost; // Total cost (gCost + hCost)
    public Node parent;

    // Constructor
    public Node(int x, int y, boolean walkable) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
    }

    // Calculate fCost
    public void calculateFCost() {
        this.fCost = gCost + hCost;
    }

    // Compare nodes based on fCost
    @Override
    public int compareTo(Node other) {
        // Compare nodes based on fCost
        return Float.compare(this.fCost, other.fCost);
    }

    // Optionally override equals() and hashCode() for proper comparisons in sets and queues
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return 31 * (31 + x) + y;
    }
}
