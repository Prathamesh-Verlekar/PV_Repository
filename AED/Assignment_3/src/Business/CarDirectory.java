/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;
import java.util.ArrayList;
/**
 *
 * @author Prathamesh
 */

public class CarDirectory {
    private ArrayList<CarDetails> carDetailsList;

    public CarDirectory() {
        this.carDetailsList = new ArrayList<CarDetails>();
    }

    public ArrayList<CarDetails> getCarDetailsList() {
        return this.carDetailsList;
    }

    public void setCarDetailsList(ArrayList<CarDetails> carDetailsList) {
        this.carDetailsList = carDetailsList;
    }

    public CarDetails addCarDetails(){
        CarDetails carDetails = new CarDetails();
        carDetailsList.add(carDetails);
        return carDetails;
    }
    
    public void deleteCarDetails(CarDetails carDetails){
        carDetailsList.remove(carDetails);
    }
    
    public ArrayList<CarDetails> getCarDetailsList(String availableCity)
    {
        ArrayList<CarDetails> newlist = new ArrayList<CarDetails>();
        for (CarDetails carDetails : this.getCarDetailsList())
        {
            if(carDetails.getAvailableCity().equalsIgnoreCase(availableCity) && carDetails.getAvailablity().equalsIgnoreCase("Yes"))
            {
                newlist.add(carDetails);
            }
        }
        return newlist;
    }
    
    public ArrayList<CarDetails> getCarDetailsList1(String manufacturedBy)
    {
        ArrayList<CarDetails> newlist = new ArrayList<CarDetails>();
        for (CarDetails carDetails : this.getCarDetailsList())
        {
            if(carDetails.getManufacturedBy().equalsIgnoreCase(manufacturedBy))
            {
                newlist.add(carDetails);
            }
        }
        return newlist;
    }
    
    public ArrayList<CarDetails> getYearCarDetailsList(int manufactureYear)
    {
        ArrayList<CarDetails> newlist = new ArrayList<CarDetails>();
        for (CarDetails carDetails : this.getCarDetailsList())
        {
            if(carDetails.getManufactureYear()==(manufactureYear))
            {
                newlist.add(carDetails);
            }
        }
        return newlist;
    }
    
    public ArrayList<CarDetails> getSerialNumberCarDetailsList(String serialNumber)
    {
        ArrayList<CarDetails> newlist = new ArrayList<CarDetails>();
        for (CarDetails carDetails : this.getCarDetailsList())
        {
            if(carDetails.getSerialNumber().equalsIgnoreCase(serialNumber))
            {
                newlist.add(carDetails);
            }
        }
        return newlist;
    }
    
    public ArrayList<CarDetails> getModelNumberCarDetailsList(String modelNumber)
    {
        ArrayList<CarDetails> newlist = new ArrayList<CarDetails>();
        for (CarDetails carDetails : this.getCarDetailsList())
        {
            if(carDetails.getModelNumber().equalsIgnoreCase(modelNumber))
            {
                newlist.add(carDetails);
            }
        }
        return newlist;
    }
    
    public ArrayList<CarDetails> getUsedByUberCarDetailsList(String usedByUber)
    {
        ArrayList<CarDetails> newlist = new ArrayList<CarDetails>();
        for (CarDetails carDetails : this.getCarDetailsList())
        {
            if(carDetails.getUsedByUber().equalsIgnoreCase(usedByUber))
            {
                newlist.add(carDetails);
            }
        }
        return newlist;
    }
    
    public ArrayList<CarDetails> getExpiredCarDetailsList(String maintenance_certificate)
    {
        ArrayList<CarDetails> newlist = new ArrayList<CarDetails>();
        for (CarDetails carDetails : this.getCarDetailsList())
        {
            if(carDetails.getMaintenance_certificate().equalsIgnoreCase(maintenance_certificate))
            {
                newlist.add(carDetails);
            }
        }
        return newlist;
    }
    
    public ArrayList<CarDetails> getSeatsCarDetailsList(int min_seats, int max_seats)
    {
        ArrayList<CarDetails> newlist = new ArrayList<CarDetails>();
        for (CarDetails carDetails : this.getCarDetailsList())
        {
            if(carDetails.getNo_seats() < max_seats && carDetails.getNo_seats() > min_seats && carDetails.getAvailablity().equalsIgnoreCase("Yes"))
            {
                newlist.add(carDetails);
            }
        }
        return newlist;
    }
    
    public ArrayList<CarDetails> getFirstCarDetailsList(String availablity)
    {
        ArrayList<CarDetails> newlist = new ArrayList<CarDetails>();
        for (CarDetails carDetails : this.getCarDetailsList())
        {
            if(carDetails.getAvailablity().equalsIgnoreCase("Yes"))
            {
                newlist.add(carDetails);
                break;
            }
        }
        return newlist;
    }
    
    public ArrayList<CarDetails> getAvailableCarDetailsList(String availablity)
    {
        ArrayList<CarDetails> newlist = new ArrayList<CarDetails>();
        for (CarDetails carDetails : this.getCarDetailsList())
        {
            if(carDetails.getAvailablity().equalsIgnoreCase("Yes"))
            {
                newlist.add(carDetails);
            }
        }
        return newlist;
    }
    
    public ArrayList<CarDetails> getUnavailableCarDetailsList(String availablity)
    {
        ArrayList<CarDetails> newlist = new ArrayList<CarDetails>();
        for (CarDetails carDetails : this.getCarDetailsList())
        {
            if(carDetails.getAvailablity().equalsIgnoreCase("No"))
            {
                newlist.add(carDetails);
            }
        }
        return newlist;
    }
    
}
