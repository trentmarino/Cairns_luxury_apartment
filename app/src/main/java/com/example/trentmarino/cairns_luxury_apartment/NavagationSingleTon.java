package com.example.trentmarino.cairns_luxury_apartment;

/**
 * Created by trentmarino on 18/05/16.
 */
public class NavagationSingleTon {
    private String propertyLocationID;
    private String propertyLocationName;
    private String roomNumber;
    private String roomName;
    private String checkIn, checkOut;
    private String price;
    private String custName,custEmail,custPhone,custAddress;
    private String creditNumber, creditExpiry, creditCode;
    private int numberOfRooms;
    private int totalNumGuests;
    private String priceOrder = "default";


    private static NavagationSingleTon ourInstance = new NavagationSingleTon();

    public static NavagationSingleTon getInstance() {
        return ourInstance;
    }

    protected NavagationSingleTon() {
    }

    public int getTotalNumGuests() {
        return totalNumGuests;
    }

    public void setTotalNumGuests(int totalNumGuests) {
        this.totalNumGuests = totalNumGuests;
    }

    public void setPropertyLocationID(String propertyLocationID) {
        this.propertyLocationID = propertyLocationID;
    }

    public void setPropertyLocationName(String propertyLocationName) {
        this.propertyLocationName = propertyLocationName;
    }



    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getPropertyLocationID() {
        return propertyLocationID;
    }

    public String getPropertyLocationName() {
        return propertyLocationName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public String getPriceOrder() {
        return priceOrder;
    }

    public void setPriceOrder(String priceOrder) {
        this.priceOrder = priceOrder;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCreditNumber() {
        return creditNumber;
    }

    public void setCreditNumber(String creditNumber) {
        this.creditNumber = creditNumber;
    }

    public String getCreditExpiry() {
        return creditExpiry;
    }

    public void setCreditExpiry(String creditExpiry) {
        this.creditExpiry = creditExpiry;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }
}
