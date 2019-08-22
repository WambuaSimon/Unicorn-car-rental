package com.wizag.unicorn.model;

public class BookingsModel {

    String car, pickup_location,
            dropoff_location, pickup_date, pickup_time,
            dropoff_date, dropoff_time,
            status, days, days_cost, amount;

    String make,model,driverCost;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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


    public void setCar(String car) {
        this.car = car;
    }

    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public void setDropoff_location(String dropoff_location) {
        this.dropoff_location = dropoff_location;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public void setPickup_time(String pickup_time) {
        this.pickup_time = pickup_time;
    }

    public void setDropoff_date(String dropoff_date) {
        this.dropoff_date = dropoff_date;
    }

    public void setDropoff_time(String dropoff_time) {
        this.dropoff_time = dropoff_time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public void setDays_cost(String days_cost) {
        this.days_cost = days_cost;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
