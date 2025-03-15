package process.astar;

import data.astar.ACell;
import data.astar.AGrid;
import data.astar.Queue;
import gui.instrument.ChartManager;
import data.astar.CellIsWallException;

import java.util.ArrayList;

public class AStarCore {
    private Queue openList;
    private ArrayList<ACell> closedList;
    private AGrid grid;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public ChartManager chartManager = new ChartManager();

    public AStarCore() {
        openList = new Queue();
        closedList = new ArrayList<>();
        grid = new GridFactory().BuildGrid();
        if (grid.getStartingCell() != null) {
            openList.getQueue().add(grid.getStartingCell());
        } else {
            throw new IllegalStateException("Starting cell is null in AStarCore default constructor");
        }
    }

    public AStarCore(int n) {
        openList = new Queue();
        closedList = new ArrayList<>();
        grid = new GridFactory().BuildGrid(n);
        if (grid.getStartingCell() != null) {
            openList.getQueue().add(grid.getStartingCell());
        } else {
            throw new IllegalStateException("Starting cell is null in AStarCore constructor with size " + n);
        }
    }

    public void process() {
        if (!openList.getQueue().isEmpty()) {
            ACell cell = openList.handle();
            updateOpenList(cell);
            closedList.add(cell);
        }
    }

    public boolean isEnded() {
        return closedList.contains(grid.getEndingCell());
    }

    public boolean queueIsEmpty() {
        return openList.getQueue().isEmpty();
    }

    public boolean workFinished() {
        return isEnded() || queueIsEmpty();
    }

    public void updateOpenList(ACell cell) {
        for (int i = 0; i < 4; i++) {
            ACell cellDaughter;
            try {
                switch (i) {
                    case 0: cellDaughter = grid.getUp(cell); break;
                    case 1: cellDaughter = grid.getDown(cell); break;
                    case 2: cellDaughter = grid.getRight(cell); break;
                    default: cellDaughter = grid.getLeft(cell); break;
                }
                if (cellDaughter != null && !closedList.contains(cellDaughter) && !openList.getQueue().contains(cellDaughter)) {
                    cellDaughter.setParent(cell);
                    updateCosts(cellDaughter);
                    openList.getQueue().add(cellDaughter);
                }
            } catch (CellIsWallException | IndexOutOfBoundsException ignored) {
            }
        }
    }

    public void updateCosts(ACell cell) {
        cell.calculateCost();
        grid.calculateHeuristicCost(cell);
        chartManager.registerHeuristicByStep((int) cell.getCost());
    }

    public String showPath(ACell cell) {
        String res = "";
        for (int k = 0; k < grid.getSize(); k++) {
            for (int m = 0; m < grid.getSize(); m++) {
                res += "|" + (closedList.contains(grid.getGrid()[k][m])
                        ? (cell.getGenealogy().contains(grid.getGrid()[k][m]) ? ANSI_BLUE + "2" + ANSI_RESET
                        : ANSI_RED + "2" + ANSI_RESET)
                        : (grid.getGrid()[k][m].isCanAccess() ? ANSI_GREEN + "0" + ANSI_RESET : "1")) + "|";
            }
            res += "\n";
        }
        return res;
    }

    public Queue getOpenList() {
        return openList;
    }

    public AGrid getGrid() {
        return grid;
    }

    public ArrayList<ACell> getClosedList() {
        return closedList;
    }

    public int getClosedListSize() {
        return closedList.size();
    }

    public int getCurrentPathSize() {
        return closedList.isEmpty() ? 0 : closedList.get(closedList.size() - 1).getGenealogy().size();
    }
}