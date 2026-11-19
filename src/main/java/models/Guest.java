package models;

import datastructure.LinkedList;
import java.time.LocalDate;

public class Guest {
    private int guestIdentifier;
    private String name;
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
    private String email;
    private String notes;
    private LinkedList<Booking> bookings = new LinkedList<>();

    public Guest(int guestIdentifier, String name, LocalDate dateOfBirth,
                 String address, String phone, String email, String notes){
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.guestIdentifier = guestIdentifier;
        this.phone = phone;
        this.email = email;
        this.notes = notes;
    }

    //getters
    public int getGuestIdentifier() { return guestIdentifier; }
    public String getName() { return name; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getNotes() { return notes; }
    public LinkedList<Booking> getBookings() { return bookings; }
}
