package gui.instrument;

import java.util.HashMap;
import java.util.Map;

public class ChartManager {
    private Map<String, Integer> elementCounts;

    public ChartManager() {
        this.elementCounts = new HashMap<>();
    }

    public void countType(String type) {
        elementCounts.put(type, elementCounts.getOrDefault(type, 0) + 1);
    }

    public void registerHeuristicByStep(int heuristic) {
        // Pour A*, pourrait être utilisé pour tracer un graphique
        System.out.println("Heuristic registered: " + heuristic);
    }

    public Map<String, Integer> getElementCounts() {
        return elementCounts;
    }

    public void reset() {
        elementCounts.clear();
    }
}