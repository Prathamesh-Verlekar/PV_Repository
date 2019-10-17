/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Users;

import Business.Abstract.User;
import Business.ProductDirectory;
import java.util.Date;

/**
 *
 * @author AEDSpring2019
 */
public class Customer extends User implements Comparable<Customer>{
    
    private ProductDirectory directory;
    public Date lastupdated;

    public Date getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Date lastupdated) {
        this.lastupdated = lastupdated;
    }
    
    public Customer(String password, String userName) {
        super(password, userName, "CUSTOMER");
        directory = new ProductDirectory();
        lastupdated = new Date(System.currentTimeMillis());
    }

    public ProductDirectory getDirectory() {
        return directory;
    }

    public void setDirectory(ProductDirectory directory) {
        this.directory = directory;
    }
    
    @Override
    public int compareTo(Customer ob) {
        return ob.getUserName().compareTo(this.getUserName());
    }

    @Override
    public String toString() {
        return getUserName(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean verify(String password) {
        if(password.equals(getPassword()))
            return true;
            return false;
    }
    
}
