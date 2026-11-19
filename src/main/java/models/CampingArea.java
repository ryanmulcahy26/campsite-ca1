package models;

import datastructure.LinkedList;

public class CampingArea {
    private String identifier;
    private String description;
    private LinkedList<Plot> plots = new LinkedList<>();

    public CampingArea(String identifier, String description){
        this.identifier = identifier;
        this.description = description;
    }

    //return plot with identifier, null if there isnt one
    public Plot findPlot(String plotidentifier){
        LinkedList.Node<Plot> current = plots.getHead();
        while(current != null){
            if(current.getData().getPlotIdentifier().equalsIgnoreCase(plotidentifier)){
                return current.getData();
            }
            current = current.getNext();
        }
        return null;
    }

    //add plot unless one with same identifier exists in the same area
    public boolean addPlot(Plot plot){
        if(findPlot(plot.getPlotIdentifier()) != null){
            return false;
        }
        plots.add(plot);
        return true;
    }

    // getters
    public String getIdentifier() { return identifier; }
    public String getDescription() { return description; }
    public LinkedList<Plot> getPlots() { return plots; }
}
