package me.BRZeph.core.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.BRZeph.TowerDefenseGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static me.BRZeph.utils.Constants.Constants.Paths.TileValues.TILE_HEIGHT;
import static me.BRZeph.utils.Constants.Constants.Paths.TileValues.TILE_WIDTH;
import static me.BRZeph.utils.GlobalUtils.consoleLog;

public class TileMap {
    private Tile[][] map;
    private String filePath;
    private static final int tileWidth = TILE_WIDTH;
    private static final int tileHeight = TILE_HEIGHT;

    private int pathStartPointX;
    private int pathStartPointY;
    private int pathEndPointX;
    private int pathEndPointY;
    private List<Node> path;
    private List<Tile> buildableTiles;
    private List<Tile> walkableTiles;

    public TileMap(String filePath) {
        this.filePath = filePath;
    }

    public void changeTile(int x, int y, TileType tileType){
        Tile oldTile = map[y][x]; // This is not wrong, it should ALWAYS be map[y][x] and NOT map[x][y].
        map[y][x] = new Tile(tileType); // This is not wrong, it should ALWAYS be map[y][x] and NOT map[x][y].
        if (oldTile.isWalkable()){
            createPath();
        }
    }

    private void loadMap(String filePath) {
        consoleLog("Loading map ...");
        List<Tile[]> rows = new ArrayList<>();
        try (BufferedReader reader = Gdx.files.internal(filePath).reader(512)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                Tile[] row = new Tile[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    int tileTypeIndex = Integer.parseInt(tokens[i]);
                    if (tileTypeIndex >= 0) {
                        TileType tileType = TileType.getTileByNumber(tileTypeIndex);
                        row[i] = new Tile(tileType);
                    }
                }
                rows.add(row);
            }
        } catch (IOException e) {
            consoleLog("Error while loading map");
            e.printStackTrace();
        }

        map = new Tile[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            map[i] = rows.get(rows.size() - 1 - i); // Invert Y-axis by reversing row order
        }
        consoleLog("Map loaded");
    }

    public void loadMapAndFindPath() {
        loadMap(filePath);
        initializeBuildableTiles();
        createPath();
    }

    private void createPath() {
        consoleLog("Finding starting and ending points for the path...");
        int startX = -1, startY = -1;
        int endX = -1, endY = -1;

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                Tile tile = map[y][x]; // This is not wrong, it should ALWAYS be map[y][x] and NOT map[x][y].
                //inverting the end point and start point, otherwise the path starts at the end point
                tile.setX(x); // Initializing variables.
                tile.setY(y); // Initializing variables.

                if (tile.isEndingPoint()) {
                    pathStartPointX = x;
                    pathStartPointY = y;
                    startX = x;
                    startY = y;
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
            consoleLog("Successfully found starting and ending point");
            path = findPath();
            if (path == null) {
                pathNotFound();
            }
        } else {
            consoleLog("Starting or ending point not found in the map.\n" +
                "startPos -> " + startX + ";" + startY + "\n" +
                "endPos -> " + endX + ";" + endY);
        }
    }

    // Placeholder method to be implemented later
    private void pathNotFound() {
        consoleLog("INVALID PATH");
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                Tile tile = map[y][x]; // This is not wrong, it should ALWAYS be map[y][x] and NOT map[x][y].
                if (tile != null) {
                    Texture texture = tile.getTexture();
                    spriteBatch.draw(texture, x * tileWidth, y * tileHeight, tileWidth, tileHeight);
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

    public Tile getTileAtMap(int x, int y) { // x and y are map coordinates.
        if (x < 0 || x >= map[0].length || y < 0 || y >= map.length) {
            return null;
        }
        return map[y][x]; // This is not wrong, it should ALWAYS be map[y][x] and NOT map[x][y].
    }

    public Tile getTileAtScreen(int screenX, int screenY){ // x and y are screen coordinates.
        if (screenX < 0 || screenX >= map[0].length*tileWidth || screenY < 0 || screenY >= map.length*tileHeight){
            return null;
        } else {
            int tileX = (int)(screenX/tileWidth);
            int tileY = (int)(screenY/tileHeight);
            return getTileAtMap(tileX, tileY);
        }
    }

    public List<Node> findPath() {
        consoleLog("Finding path...");
        int startX = pathStartPointX;
        int startY = pathStartPointY;
        int endX = pathEndPointX;
        int endY = pathEndPointY;

        Node startNode = new Node(startX, startY, map[startY][startX].isWalkable());
        Node endNode = new Node(endX, endY, map[endY][endX].isWalkable());

        if (!startNode.walkable || !endNode.walkable) {
            consoleLog("Unwalkable starting or ending point");
            return null; // Cannot start or end on non-walkable tiles
        }

        // PriorityQueue uses Node's compareTo method to order nodes based on fCost.
        PriorityQueue<Node> openList = new PriorityQueue<>();
        startNode.gCost = 0;
        startNode.hCost = heuristic(startNode, endNode);
        startNode.calculateFCost();
        openList.add(startNode);

        List<Node> closedList = new ArrayList<>();
        int maxIterations = 1000000;
        int iterations = 0;

        while (!openList.isEmpty()) {
            if (iterations > maxIterations) {
                TowerDefenseGame.getScreenManager().showMainMenu();
                consoleLog("Error while loading path, took too long");
                return null;
            }
            iterations++;

            if (iterations % 1000 == 1){
                consoleLog("Iteration number : " + iterations);
            }

            Node currentNode = openList.poll();
            closedList.add(currentNode);

            // If we reached the end node, reconstruct the path.
            if (currentNode.equals(endNode)) {
                consoleLog("Successfully built path");
                return buildPath(currentNode);
            }

            for (Node neighbor : getNeighbors(currentNode)) {
                if (closedList.contains(neighbor) || !neighbor.walkable) {
                    continue; // Skip non-walkable or already evaluated nodes
                }

                float newCostToNeighbor = currentNode.gCost + getMovementCost(currentNode, neighbor);
                if (newCostToNeighbor < neighbor.gCost || !openList.contains(neighbor)) {
                    neighbor.gCost = newCostToNeighbor;
                    neighbor.hCost = heuristic(neighbor, endNode);
                    neighbor.calculateFCost();
                    neighbor.parent = currentNode;

                    openList.add(neighbor);
                }
            }
        }

        consoleLog("Error while loading path, no path found");
        return null; // No path found
    }

    private float getMovementCost(Node current, Node neighbor) {
        // Example of adding diagonal movement cost
        if (Math.abs(current.x - neighbor.x) + Math.abs(current.y - neighbor.y) == 2) {
            return 1.4f; // Diagonal cost (approximates sqrt(2) for diagonal moves)
        }
        return 1f; // Default cost for straight movement
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {
            {0, 1},   // Up
            {1, 0},   // Right
            {0, -1},  // Down
            {-1, 0}   // Left
        };

        for (int[] direction : directions) {
            int checkX = node.x + direction[0];
            int checkY = node.y + direction[1];

            if (checkX >= 0 && checkX < map[0].length && checkY >= 0 && checkY < map.length) {
                // Add only walkable neighbors
                if (map[checkY][checkX].isWalkable()) {
                    neighbors.add(new Node(checkX, checkY, map[checkY][checkX].isWalkable()));
                }
            }
        }
        return neighbors;
    }


    private float heuristic(Node a, Node b) {
        // Use Euclidean distance for more accurate heuristic.
        return (float) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
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

    private void initializeBuildableTiles() {
        buildableTiles = new ArrayList<>();
        walkableTiles = new ArrayList<>();
        for (Tile[] tiles : map) {
            for (Tile tile : tiles) {
                if (tile == null) {
                    continue;
                }
                if (tile.getType().getOriginalType().isBuildable){
                    buildableTiles.add(tile);
                } else {
                    walkableTiles.add(tile);
                }
            }
        }
    }

    public List<Tile> getBuildableTiles(){
        initializeBuildableTiles();
        return buildableTiles;
    }

    public List<Tile> getWalkableTiles(){
        initializeBuildableTiles();
        return walkableTiles;
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

    public Tile[][] getMap() {
        return map;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }
}
