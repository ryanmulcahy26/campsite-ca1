package models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking {
    private LocalDate startDate;
    private LocalDate endDate;
    private String identifier;
    private Guest guest;
    private Plot plot;
    private BookingPreferences preferences;

    public Booking(LocalDate startDate, LocalDate endDate, String identifier,
                   Guest guest, Plot plot, BookingPreferences preferences) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.identifier = identifier;
        this.guest = guest;
        this.plot = plot;
        this.preferences = preferences;
    }

    // two bookings collide if one starts when another is ongoing
    public boolean collisions(LocalDate otherStart, LocalDate otherEnd){
        return startDate.isBefore(otherEnd) && otherStart.isBefore(endDate);
    }

    // waiting for checkout to pass
    public boolean isPending(){
        return !endDate.isBefore(LocalDate.now());
    }

    //check number of days stayed
    public long getNights(){
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    //total cost of the whole stay (euros)
    public double getTotalCost(){
        return getNights() * plot.getCostPerNight();
    }

    //getters
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getIdentifier() { return identifier; }
    public Guest getGuest() { return guest; }
    public Plot getPlot() { return plot; }
    public BookingPreferences getPreferences() { return preferences;}

    //some setters
    public void setPlot(Plot plot){
        this.plot = plot;
    }

    public void setIdentifier(String id){
        this.identifier = id;
    }

}
