package services;

import datastructure.LinkedList;
import models.CampingArea;
import models.Campsite;
import models.Guest;
import models.Plot;
import java.time.LocalDate;


public class CampsiteMainSystem {
    private LinkedList<Campsite> campsites = new LinkedList<>();
    private LinkedList<Guest> guests = new LinkedList<>();
    private int nextGuestId = 1; //auto guest id

    // campsite functionality
    //return campsite with said name, null if not
    public Campsite findCampsite(String name){
        LinkedList.Node<Campsite> current = campsites.getHead();
        while(current != null){
            if(current.getData().getName().equalsIgnoreCase(name)){
                return current.getData();
            }
            current = current.getNext();
        }
        return null;
    }

    //creates and stores a campsite, returns null if invalid or already exists
    public Campsite addCampsite(String name, String address, int stars, String contactinformation) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        if (stars < 1 || stars > 5) {
            return null;
        }
        if (findCampsite(name.trim()) != null) {
            return null;
        }
        Campsite campsite = new Campsite(name.trim(), address, stars, contactinformation);
        campsites.add(campsite);
        return campsite;
    }

    // plots and areas
    // false if identifier is used or if it already exists
    public boolean addArea(Campsite campsite, String identifier, String description) {
        if (campsite == null || identifier == null || identifier.trim().isEmpty()) {
            return false;
        }
        return campsite.addArea(new CampingArea(identifier.trim(), description));
    }

    //false if the plot is invalid or if its identifier is already used
    public boolean addPlot(CampingArea area, Plot plot){
        if(area == null || plot == null){
            return false;
        }
        if(plot.getCostPerNight() < 0 || plot.getSize() <= 0){
            return false;
        }
        return area.addPlot(plot);
    }

    //guests
    //creates a guest with next valid id and then stores it
    public Guest addGuest(String name, LocalDate dateOfBirth, String address, String phone, String email, String notes){
        if(name == null || name.trim().isEmpty()){
            return null;
        }
        if(dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())){
            return null;
        }
        Guest guest = new Guest(nextGuestId, name.trim(), dateOfBirth, address, phone, email, notes);
        nextGuestId ++;
        guests.add(guest);
        return guest;
    }

    // return guest with the given Id, null if doesnt exist
    public Guest findGuest(int guestIdentifier){
        LinkedList.Node<Guest> current = guests.getHead();
        while(current != null){
            if(current.getData().getGuestIdentifier() == guestIdentifier){
                return current.getData();
            }
            current = current.getNext();
        }
        return null;
    }

    //getters
    public LinkedList<Campsite> getCampsites() { return campsites; }
    public LinkedList<Guest> getGuests() { return guests; }
    public int getNextGuestId() { return nextGuestId; }
    public void setNextGuestId(int id) { this.nextGuestId = id; }

}
