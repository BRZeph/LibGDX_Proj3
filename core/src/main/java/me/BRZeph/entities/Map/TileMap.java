package me.BRZeph.entities.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.BRZeph.Main;
import me.BRZeph.utils.Constants;
import me.BRZeph.utils.GlobalUtils;
import me.BRZeph.utils.pathFinding.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class TileMap {
    private Tile[][] map; // 2D array for the tile map
    private String filePath;
    private int tileWidth;
    private int tileHeight;

    private int pathStartPointX;
    private int pathStartPointY;
    private int pathEndPointX;
    private int pathEndPointY;
    private List<Node> path;

    public TileMap(String filePath, int tileWidth, int tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.filePath = filePath;
    }

    private void loadMap(String filePath) {
        List<Tile[]> rows = new ArrayList<>();
        try (BufferedReader reader = Gdx.files.internal(filePath).reader(512)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                Tile[] row = new Tile[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    int tileTypeIndex = Integer.parseInt(tokens[i]);
                    if (tileTypeIndex >= 0 && tileTypeIndex < TileType.values().length) {
                        TileType tileType = TileType.values()[tileTypeIndex];
                        row[i] = new Tile(tileType);
                    }
                }
                rows.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        map = new Tile[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            map[i] = rows.get(i);
        }
    }

    public void loadMapAndFindPath(String filePath) {
        loadMap(filePath);

        int startX = -1, startY = -1;
        int endX = -1, endY = -1;

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                Tile tile = map[y][x];
//                if (tile.isStartingPoint()) {

                //inverting the end point and start point, otherwise the path starts at the end point

                if (tile.isEndingPoint()) {
                    pathStartPointX = x;
                    pathStartPointY = y;
                    startX = x;
                    startY = y;
//                } else if (tile.isEndingPoint()) {
                } else if (tile.isStartingPoint()) {
                    pathEndPointX = x;
                    pathEndPointY = y;
                    endX = x;
                    endY = y;
                }
            }
        }

        // Check if starting and ending points were found
        if (startX != -1 && startY != -1 && endX != -1 && endY != -1) {
            path = findPath();
            if (path != null) {
                // Process the found path (e.g., mark the tiles)
                // Flips Y coordinates
                for (Node node : path) {
                    System.out.println("Path Tile: (" + node.x + ", " + node.y + ")");
                    node.y = getHeight() / Constants.AssetsTiles.TILE_HEIGHT - node.y - 1;
                }
            } else {
                // Call pathNotFound() method if no path was found
                pathNotFound();
            }
        } else {
            System.out.println("Starting or ending point not found in the map.");
        }
    }

    // Placeholder method to be implemented later
    private void pathNotFound() {
        GlobalUtils.consoleLog("INVALID PATH");
        GlobalUtils.consoleLog("INVALID PATH");
        GlobalUtils.consoleLog("INVALID PATH");
        GlobalUtils.consoleLog("INVALID PATH");
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                Tile tile = map[y][x];
                if (tile != null) {
                    Texture texture = tile.getTexture();
                    // Flipping the Y coordinate for rendering
                    int flippedY = map.length - 1 - y; // Calculate the flipped Y position
                    spriteBatch.draw(texture, x * tileWidth, flippedY * tileHeight, tileWidth, tileHeight);
                }
            }
        }
        spriteBatch.end();
    }

    public int getWidth() {
        return map[0].length * tileWidth;
    }

    public int getHeight() {
        return map.length * tileHeight;
    }

    public Tile getTileAt(int x, int y) {
        if (x < 0 || x >= map[0].length || y < 0 || y >= map.length) {
            return null; // Out of bounds
        }
        return map[y][x]; // Return the tile at the specified coordinates
    }

    public List<Node> findPath() {
        int startX = pathStartPointX;
        int startY = pathStartPointY;
        int endX = pathEndPointX;
        int endY = pathEndPointY;

        Node startNode = new Node(startX, startY, map[startY][startX].isWalkable());
        Node endNode = new Node(endX, endY, map[endY][endX].isWalkable());

        if (!startNode.walkable || !endNode.walkable) {
            return null; // Cannot start or end on non-walkable tiles
        }

        List<Node> openSet = new ArrayList<>();
        List<Node> closedSet = new ArrayList<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>((a, b) -> Float.compare(a.fCost, b.fCost));

        openSet.add(startNode);
        priorityQueue.add(startNode);

        int max_iterations = 100000;
        int iterations = 0;

        while (!priorityQueue.isEmpty()) {
            if (iterations > max_iterations){
                Main.getScreenManager().showMainMenu();
                GlobalUtils.consoleLog("took too long");
                return null;
            }
            iterations++;
            Node currentNode = priorityQueue.poll();
            if (currentNode.x == endX && currentNode.y == endY) {
                return buildPath(currentNode);
            }

            openSet.remove(currentNode);
            closedSet.add(currentNode);

            for (Node neighbor : getNeighbors(currentNode)) {
                if (!neighbor.walkable || closedSet.contains(neighbor)) {
                    continue; // Skip non-walkable or already evaluated nodes
                }

                float newCostToNeighbor = currentNode.gCost + 1; // Assume uniform cost for neighbors
                if (newCostToNeighbor < neighbor.gCost || !openSet.contains(neighbor)) {
                    neighbor.gCost = newCostToNeighbor;
                    neighbor.hCost = heuristic(neighbor, endNode);
                    neighbor.calculateFCost();
                    neighbor.parent = currentNode;

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                        priorityQueue.add(neighbor);
                    }
                }
            }
        }

        return null; // No path found
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} }; // Up, Right, Down, Left

        for (int[] direction : directions) {
            int checkX = node.x + direction[0];
            int checkY = node.y + direction[1];

            if (checkX >= 0 && checkX < map[0].length && checkY >= 0 && checkY < map.length) {
                neighbors.add(new Node(checkX, checkY, map[checkY][checkX].isWalkable()));
            }
        }
        return neighbors;
    }

    private float heuristic(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y); // Manhattan distance
    }

    private List<Node> buildPath(Node endNode) {
        List<Node> path = new ArrayList<>();
        Node current = endNode;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        return path; // Return path in reverse order
    }

    public int getPathStartPointX() {
        return pathStartPointX;
    }

    public int getPathStartPointY() {
        return pathStartPointY;
    }

    public int getPathEndPointX() {
        return pathEndPointX;
    }

    public int getPathEndPointY() {
        return pathEndPointY;
    }

    public List<Node> getPath() {
        return path;
    }
}
