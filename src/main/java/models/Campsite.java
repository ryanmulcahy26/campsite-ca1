package models;

import datastructure.LinkedList;

public class Campsite {
    private String name;
    private String address;
    private int stars;
    private String contactInformation;
    private LinkedList<String> facilities = new LinkedList<>();
    private LinkedList<CampingArea> areas = new LinkedList<>();

    public Campsite(String name, String address, int stars, String contactInformation){
        this.name = name;
        this.address = address;
        this.stars = stars;
        this.contactInformation = contactInformation;
    }

    //return area with a given identifier, null if there isnt one
    public CampingArea findArea(String areaIdentifier){
        LinkedList.Node<CampingArea> current = areas.getHead();
        while(current != null){
            if(current.getData().getAreaidentifier().equalsIgnoreCase(areaIdentifier)){
                return current.getData();
            }
            current = current.getNext();
        }
        return null;
    }

    // area identifier must be unique
    public boolean addArea(CampingArea area){
        if(findArea(area.getAreaidentifier()) != null){
            return false; //this already exists
        }
        areas.add(area);
        return true;
    }

    public void addFacility(String facility){
        facilities.add(facility);
    }

    //does the campsite offer this facility
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

    // getters
    public String getName() { return name; }
    public String getAddress() { return address; }
    public int getStars() { return stars; }
    public String getContactInformation() { return contactInformation; }
    public LinkedList<String> getFacilities() { return facilities; }
    public LinkedList<CampingArea> getAreas() { return areas; }
}

