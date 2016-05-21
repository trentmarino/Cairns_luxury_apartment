package com.example.trentmarino.cairns_luxury_apartment;

/**
 * Created by trentmarino on 18/05/16.
 */
public class NavagationSingleTon {
    private String propertyLocationID;
    private String propertyLocationName;
    private String roomName;
    private int numberOfRooms;
    private String type;
    private String content;
    private String order;
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



    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

    public String getRoomName() {
        return roomName;
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

}
