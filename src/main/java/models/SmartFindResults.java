package models;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SmartFindResults {
    private Campsite campsite;
    private CampingArea area;
    private Plot plot;
    private LocalDate start;
    private LocalDate end;

    public SmartFindResults(Campsite campsite, CampingArea area, Plot plot, LocalDate start, LocalDate end){
        this.campsite = campsite;
        this.area = area;
        this.plot = plot;
        this.start = start;
        this.end = end;
    }

    public double getTotalCost(){
        return ChronoUnit.DAYS.between(start, end) * plot.getCostPerNight();
    }

    // getters
    public Campsite getCampsite() { return campsite; }
    public CampingArea getArea() { return area; }
    public Plot getPlot() { return plot; }
    public LocalDate getStart() { return start; }
    public LocalDate getEnd() { return end; }
}
