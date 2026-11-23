package services;

import datastructure.LinkedList;
import models.*;
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

    //bookings
    // adding a new booking
    public Booking addBooking(Guest guest, CampingArea area, LocalDate start, LocalDate end, BookingPreferences prefs){
        if(guest == null || area == null || start == null || end == null || prefs ==null){
            return null;
        }
        if(!end.isAfter(start) || start.isBefore(LocalDate.now())){
            return null;
        }

        LinkedList.Node<Plot> current = area.getPlots().getHead();
        while(current != null){
            Plot plot = current.getData();
            if(plot.matches(prefs, start, end)){
                Booking booking = new Booking(start, end, area.getAreaidentifier(), guest, plot, prefs);
                plot.getBookings().add(booking);
                guest.getBookings().add(booking);
                return booking;
            }
            current = current.getNext();
        }
        return null; // walked through whole list and didnt find anything suitable
    }

    //remove a booking
    public boolean cancelBooking(Booking booking){
        if(booking == null){
            return false;
        }
        boolean removedFromPlot = booking.getPlot().getBookings().remove(booking);
        boolean removedFromGuest = booking.getGuest().getBookings().remove(booking);
        return removedFromGuest && removedFromPlot;
    }

    // view pending bookings
    //count
    private int countPending(Plot plot){
        int count = 0;
        LinkedList.Node<Booking> current = plot.getBookings().getHead();
        while(current != null){
            if (current.getData().isPending()){
                count++;
            }
            current = current.getNext();
        }
        return count;
    }

    //no. of bookings in a whole site
    public int countPendingBookings(Campsite campsite){
        int count = 0;
        LinkedList.Node<CampingArea> areaNode = campsite.getAreas().getHead();
        while(areaNode != null){
            LinkedList.Node<Plot> plotNode = areaNode.getData().getPlots().getHead();
            while(plotNode != null){
                count += countPending(plotNode.getData());
                plotNode = plotNode.getNext();
            }
            areaNode = areaNode.getNext();
        }
        return count;
    }

    //total cost of all pending bookings
    public double totalPendingCost(Campsite campsite){
        double total = 0;
        LinkedList.Node<CampingArea> areaNode = campsite.getAreas().getHead();
        while (areaNode != null){
            LinkedList.Node<Plot> plotNode = areaNode.getData().getPlots().getHead();
            while(plotNode != null){
                LinkedList.Node<Booking> bookingNode = plotNode.getData().getBookings().getHead();
                while (bookingNode != null) {
                    if(bookingNode.getData().isPending()){
                        total += bookingNode.getData().getTotalCost();
                    }
                    bookingNode = bookingNode.getNext();
                }
                plotNode = plotNode.getNext();
            }
            areaNode = areaNode.getNext();
        }
        return total;
    }

    //build report for one campsite, area then plot
    private void campsiteReport(StringBuilder sb, Campsite campsite){
        sb.append("=== ").append(campsite.getName()).append(" (").append(campsite.getStars()).append(" stars) ===\n");

        LinkedList.Node<CampingArea> areaNode = campsite.getAreas().getHead();
        while(areaNode != null){
            CampingArea area = areaNode.getData();
            sb.append("Area: ").append(area.getAreaidentifier()).append("\n");

            LinkedList.Node<Plot> plotNode = area.getPlots().getHead();
            while(plotNode != null){
                Plot plot = plotNode.getData();
                if(countPending(plot) > 0){
                    sb.append("  Plot ").append(plot.getPlotIdentifier()).append(" (").append(plot.getType()).append(")\n");

                    LinkedList.Node<Booking> bookingNode = plot.getBookings().getHead();
                    while(bookingNode != null){
                        Booking b = bookingNode.getData();
                        if(b.isPending()){
                            sb.append("    ").append(b.getGuest().getName()).append(" (guest ").append(b.getGuest().getGuestIdentifier()).append(")")
                                    .append(" | ").append(b.getStartDate()).append(" to ").append(b.getEndDate())
                                    .append(" | ").append(b.getNights()).append(" nights")
                                    .append(" | EUR ").append(b.getTotalCost()).append("\n");
                        }
                        bookingNode = bookingNode.getNext();
                    }
                }
                plotNode = plotNode.getNext();
            }
            areaNode = areaNode.getNext();
        }
        sb.append("Pending bookings: ").append(countPendingBookings(campsite))
                .append(" | Total: EUR ").append(totalPendingCost(campsite)).append("\n");
    }

    //pending bookings for one campsite, organised by plot after area
    public String getPendingBookingsReport(Campsite campsite) {
        if(campsite == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        campsiteReport(sb, campsite);
        return sb.toString();
    }

    //pending bookings for the entire system, campsite > area > plot
    public String getAllPendingBookingsReport(){
        StringBuilder sb = new StringBuilder();
        int totalCount = 0;
        double totalCost = 0;

        LinkedList.Node<Campsite> campsiteNode = campsites.getHead();
        while(campsiteNode != null){
            Campsite campsite = campsiteNode.getData();
            campsiteReport(sb, campsite);
            sb.append("\n");
            totalCount += countPendingBookings(campsite);
            totalCost += totalPendingCost(campsite);
            campsiteNode = campsiteNode.getNext();
        }
        sb.append("system TOTAL: ").append(totalCount)
                .append(" pending bookings | EUR ").append(totalCost).append("\n");
        return sb.toString();
    }

    //guest record
    private void appendBookingLine(StringBuilder sb, Booking b){
        sb.append("  Area ").append(b.getIdentifier())
                .append(" | Plot ").append(b.getPlot().getPlotIdentifier())
                .append(" (").append(b.getPlot().getType()).append(")")
                .append(" | ").append(b.getStartDate()).append(" to ").append(b.getEndDate())
                .append(" | ").append(b.getNights()).append(" nights")
                .append(" | EUR ").append(b.getTotalCost()).append("\n");
    }

    //details, pending bookings, past bookings - null if no guest exists
    public String getGuestRecord(int guestIdentifier){
        Guest guest = findGuest(guestIdentifier);
        if(guest == null){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== Guest ").append(guest.getGuestIdentifier()).append(": ").append(guest.getName()).append("===\n");
        sb.append("Date of birth: ").append(guest.getDateOfBirth()).append("\n");
        sb.append("Address: ").append(guest.getAddress()).append("\n");
        sb.append("Phone: ").append(guest.getPhone()).append("\n");
        sb.append("Email: ").append(guest.getEmail()).append("\n");
        sb.append("Notes: ").append(guest.getNotes()).append("\n\n");

        // first check pending bookings
        sb.append("Pending bookings:\n");
        int pendingCount = 0;
        double pendingTotal = 0;
        LinkedList.Node<Booking> current = guest.getBookings().getHead();
        while(current != null){
            Booking b = current.getData();
            if(b.isPending()){
                appendBookingLine(sb, b);
                pendingCount++;
                pendingTotal += b.getTotalCost();
            }
            current = current.getNext();
        }
        if(pendingCount == 0){
            sb.append("  none\n");
        } else {
            sb.append("Pending total: EUR ").append(pendingTotal).append("\n");
        }

        // second check past bookings
        sb.append("\nPast bookings:\n");
        int pastCount = 0;
        current = guest.getBookings().getHead();
        while(current != null){
            Booking b = current.getData();
            if(!b.isPending()){
                appendBookingLine(sb, b);
                pastCount++;
            }
            current = current.getNext();
        }
        if(pastCount == 0){
            sb.append("  none\n");
        }
        return sb.toString();
    }

    // smart find
    // to check if a campsite has every facility in the list
    private boolean campsiteHasAll(Campsite campsite, LinkedList<String> required){
        if(required == null){
            return true;
        }
        LinkedList.Node<String> node = required.getHead();
        while(node != null){
            if(!campsite.hasFacility(node.getData())){
                return false;
            }
            node = node.getNext();
        }
        return true;
    }

    //check if a plot has all facilities
    private boolean plotHasAll(Plot plot, LinkedList<String> required){
        if(required == null){
            return true;
        }
        LinkedList.Node<String> node = required.getHead();
        while(node != null){
            if(!plot.hasFacility(node.getData())){
                return false;
            }
            node = node.getNext();
        }
        return true;
    }

    //searches all campsites, areas and plots for the cheapest options for specified dates
    private SmartFindResults findCheapest(BookingPreferences prefs, int minStars, LocalDate start, LocalDate end, LinkedList<String> campsiteFacilities, LinkedList<String> plotFacilities){
        SmartFindResults best = null;

        LinkedList.Node<Campsite> campsiteNode = campsites.getHead();
        while(campsiteNode != null){
            Campsite campsite = campsiteNode.getData();
            if(campsite.getStars() >= minStars && campsiteHasAll(campsite, campsiteFacilities)){
                LinkedList.Node<CampingArea> areaNode = campsite.getAreas().getHead();
                while(areaNode != null){
                    CampingArea area = areaNode.getData();
                    LinkedList.Node<Plot> plotNode = area.getPlots().getHead();
                    while(plotNode != null){
                        Plot plot = plotNode.getData();
                        if(plotHasAll(plot, plotFacilities) && plot.matches(prefs, start, end)){
                            SmartFindResults potential = new SmartFindResults(campsite, area, plot, start, end);
                            if(best == null || potential.getTotalCost() < best.getTotalCost()){
                                best = potential;
                            }
                        }
                        plotNode = plotNode.getNext();
                    }
                    areaNode = areaNode.getNext();
                }
            }
            campsiteNode = campsiteNode.getNext();
        }
        return best;
    }

    //smart find feature, if requested dates dont work, shifts the whole stay forward a day
    //continues until something to the guests liking is found
    public SmartFindResults smartFind(BookingPreferences prefs, int minStars, LocalDate start, LocalDate end, LinkedList<String> campsiteFacilities, LinkedList<String> plotFacilities, int flexDays){
        if(prefs == null || start == null || end == null || !end.isAfter(start)){
            return null;
        }
        for(int diff = 0; diff <= flexDays; diff++){
            LocalDate st = start.plusDays(diff);
            LocalDate en = end.plusDays(diff);
            if(st.isBefore(LocalDate.now())){
                continue;   // cant book in the past
            }
            SmartFindResults found = findCheapest(prefs, minStars, st, en,
                    campsiteFacilities, plotFacilities);
            if(found != null){
                return found;
            }
        }
        return null;
    }

    // reset method
    // clears every piece of data on the system
    public void reset(){
        campsites.clear();
        guests.clear();
        nextGuestId = 1;
    }


    //getters
    public LinkedList<Campsite> getCampsites() { return campsites; }
    public LinkedList<Guest> getGuests() { return guests; }
    public int getNextGuestId() { return nextGuestId; }
    public void setNextGuestId(int id) { this.nextGuestId = id; }

}
