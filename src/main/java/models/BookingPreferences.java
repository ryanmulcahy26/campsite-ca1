package models;

public class BookingPreferences{
    private PlotType type;
    private int numGuests;
    private boolean needsPower;
    private double minSize;
    private int minBedrooms;
    private double budget; // total budget in euro for whole stay

    public BookingPreferences(PlotType type, int numGuests, boolean needsPower,
                              double minSize, int minBedrooms, double budget) {
        this.type = type;
        this.numGuests = numGuests;
        this.needsPower = needsPower;
        this.minSize = minSize;
        this.minBedrooms = minBedrooms;
        this.budget = budget;
    }

    // getters
    public PlotType getType() { return type; }
    public int getNumGuests() { return numGuests; }
    public boolean needsPower() { return needsPower; }
    public double getMinSize() { return minSize; }
    public int getMinBedrooms() { return minBedrooms; }
    public double getBudget() { return budget; }
}
