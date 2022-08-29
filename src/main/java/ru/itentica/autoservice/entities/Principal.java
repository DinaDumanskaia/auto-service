package ru.itentica.autoservice.entities;

import java.util.Date;

public class Principal {
    private static final int UNDEFINED_ID = -1;
    private Long id;
    private String name;
    private String phoneNumber;
    private Position position;
    private Date birthDate;
    private String address;

    public Principal(Long id, String name, String phoneNumber, Position position, Date birthDate, String address) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.birthDate = birthDate;
        this.address = address;
    }

    public Principal() {
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Position getPosition() {
        return position;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setId(Long id) {
        if (this.id != null && this.id != UNDEFINED_ID)
            throw new RuntimeException("Id already set to value = " + this.id);

        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", position=" + position +
                ", birthDate=" + birthDate +
                ", address='" + address + '\'' +
                '}';
    }
}
