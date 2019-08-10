package com.wizag.unicorn.model;

public class BookingsModel {

    String car, pickup_location,
            dropoff_location, pickup_date, pickup_time,
            dropoff_date, dropoff_time,
            status, days, days_cost, amount;


    public BookingsModel(String car, String pickup_date, String pickup_time, String dropoff_date, String dropoff_time) {
        this.car = car;
        this.pickup_date = pickup_date;
        this.pickup_time = pickup_time;
        this.dropoff_date = dropoff_date;
        this.dropoff_time = dropoff_time;
    }

    public String getCar() {
        return car;
    }

    public String getPickup_location() {
        return pickup_location;
    }

    public String getDropoff_location() {
        return dropoff_location;
    }

    public String getPickup_date() {
        return pickup_date;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public String getDropoff_date() {
        return dropoff_date;
    }

    public String getDropoff_time() {
        return dropoff_time;
    }

    public String getStatus() {
        return status;
    }

    public String getDays() {
        return days;
    }

    public String getDays_cost() {
        return days_cost;
    }

    public String getAmount() {
        return amount;
    }
}
