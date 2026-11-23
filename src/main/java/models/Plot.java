package models;

import datastructure.LinkedList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Plot {
    private String plotIdentifier;
    private PlotType type;
    private boolean hasPower;
    private double size;
    private int bedrooms;
    private int beds;
    private double costPerNight;
    private String photoUrl;
    private LinkedList<String> facilities = new LinkedList<>(); // will be able to add facilities and remove
    private LinkedList<Booking> bookings = new LinkedList<>(); // will be able to add and remove bookings

    public Plot(String identifier, PlotType type, boolean hasPower, double size, int bedrooms,
                int beds, double costPerNight, String photoUrl){
        this.plotIdentifier = identifier;
        this.type = type;
        this.hasPower = type.isStatic() ? true : hasPower; // all static plots have power
        this.size = size;
        // only static plots will have beds and bedrooms
        this.bedrooms = type.isStatic() ? bedrooms : 0;
        this.beds = type.isStatic() ? beds : 0;
        this.costPerNight = costPerNight;
        this.photoUrl = photoUrl;
    }

    // add a facility to the plot
    public void addFacility(String facility){
        facilities.add(facility);
    }

    //check if plot offers a facility
    public boolean hasFacility(String facility){
        LinkedList.Node<String> current = facilities.getHead();
        while(current != null){
            if(current.getData().equalsIgnoreCase(facility)){
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    // true if no existing booking clashes with the dates given
    public boolean isAvailable(LocalDate start, LocalDate end){
        LinkedList.Node<Booking> current = bookings.getHead();
        while(current != null){
            if(current.getData().collisions(start, end)){
                return false;    //clashes
            }
            current = current.getNext();
        }
        return true;  //managed to walk whole list without any clashes
    }

    // check if the plot matches all the preference criteria given
    public boolean matches(BookingPreferences prefs, LocalDate start, LocalDate end){
        //check if stay is more than 1 night
        if(!end.isAfter(start)){
            return false;
        }
        if(prefs.needsPower() && !hasPower){
            return false;
        }
        if(size < prefs.getMinSize()){
            return false;
        }
        if(bedrooms < prefs.getMinBedrooms()){
            return false;
        }
        if(type != prefs.getType()){
            return false;
        }
        if(type.isStatic() && beds < prefs.getNumGuests()){
            return false;
        }
        //budget = total for whole stay, compare against cost * nights
        long nights = ChronoUnit.DAYS.between(start, end);
        if(costPerNight * nights > prefs.getBudget()){
            return false;
        }
        return isAvailable(start, end);
    }

    // getters
    public String getPlotIdentifier() {
        return plotIdentifier;
    }

    public PlotType getType(){
        return type;
    }

    public boolean getHasPower() {
        return hasPower;
    }

    public double getSize(){
        return size;
    }

    public int getBedrooms(){
        return bedrooms;
    }

    public int getBeds(){
        return beds;
    }

    public double getCostPerNight() {
        return costPerNight;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public LinkedList<String> getFacilities() {
        return facilities;
    }

    public LinkedList<Booking> getBookings() {
        return bookings;
    }
}
