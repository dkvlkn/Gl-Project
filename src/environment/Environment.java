package environment;

import java.util.List;

public class Environment {
    private Grid grid;
    private List<Obstacle> obstacles;
    private List<Resource> resources;

    public Environment(Grid grid, List<Obstacle> obstacles, List<Resource> resources) {
        this.grid = grid;
        this.obstacles = obstacles;
        this.resources = resources;
    }

    public Grid getGrid() { return grid; }
    public List<Obstacle> getObstacles() { return obstacles; }
    public List<Resource> getResources() { return resources; }
}