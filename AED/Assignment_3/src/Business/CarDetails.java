/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.Date;

/**
 *
 * @author Prathamesh
 */
public class CarDetails {
    private String carType;
    private String manufacturedBy;
    private String modelNumber;
    private String serialNumber;
    private String color;
    private String availableCity;
    private String engineNumber;
    private int odometerReading;
    private int min_seats;
    private int max_seats;
    private String maintenance_certificate;
    private int manufactureYear;
    private String availablity;
    private double price;
    private Date lastUpdate;
    private String usedByUber;
    private int no_seats;
    private String Date;

    public CarDetails(String carType, String manufacturedBy, String modelNumber, String serialNumber, String color, String availableCity, String engineNumber, int odometerReading, int min_seats, int max_seats, String maintenance_certificate, int manufactureYear, String availablity, double price, String usedByUber, int no_seats)
    {
        this.carType = carType;
        this.manufacturedBy = manufacturedBy;
        this.modelNumber = modelNumber;
        this.serialNumber = serialNumber;
        this.color = color;
        this.availableCity = availableCity;
        this.engineNumber = engineNumber;
        this.odometerReading = odometerReading;
        this.min_seats = min_seats;
        this.max_seats = max_seats;
        this.maintenance_certificate = maintenance_certificate;
        this.manufactureYear = manufactureYear;
        this.availablity = availablity;
        this.price = price;
        this.usedByUber = usedByUber;
        this.no_seats = no_seats;
    }

    public int getNo_seats() {
        return no_seats;
    }

    public void setNo_seats(int no_seats) {
        this.no_seats = no_seats;
    }
    
    public String getUsedByUber() {
        return usedByUber;
    }

    public void setUsedByUber(String usedByUber) {
        this.usedByUber = usedByUber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getManufacturedBy() {
        return manufacturedBy;
    }

    public void setManufacturedBy(String manufacturedBy) {
        this.manufacturedBy = manufacturedBy;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAvailableCity() {
        return availableCity;
    }

    public void setAvailableCity(String availableCity) {
        this.availableCity = availableCity;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public int getOdometerReading() {
        return odometerReading;
    }

    public void setOdometerReading(int odometerReading) {
        this.odometerReading = odometerReading;
    }

    public int getMin_seats() {
        return min_seats;
    }

    public void setMin_seats(int min_seats) {
        this.min_seats = min_seats;
    }

    public int getMax_seats() {
        return max_seats;
    }

    public void setMax_seats(int max_seats) {
        this.max_seats = max_seats;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMaintenance_certificate() {
        return maintenance_certificate;
    }

    public void setMaintenance_certificate(String maintenance_certificate) {
        this.maintenance_certificate = maintenance_certificate;
    }

    public String getAvailablity() {
        return availablity;
    }

    public void setAvailablity(String availablity) {
        this.availablity = availablity;
    }
    
    public CarDetails() {
        this.lastUpdate = new Date();
    }
    
    public Date getLastUpdate()  {
        return lastUpdate;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
    
    
    
    @Override
    public String toString(){
       return manufacturedBy;
      //return this.getCarType()+" "+this.getManufacturedBy()+" "+this.getModelNumber()+" "+this.getSerialNumber()+" "+this.getColor()+" "+this.getAvailableCity()+" "+this.getEngineNumber()+" "+this.getOdometerReading()+" "+this.getMin_seats()+" "+this.getMax_seats()+" "+this.getManufactureYear()+" "+getMaintenance_certificate()+" "+getAvailablity()+" "+getPrice()+" "+this.getUsedByUber();
    }
}
