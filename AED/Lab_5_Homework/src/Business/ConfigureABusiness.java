/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Business.Employee.Employee;
import Business.UserAccount.UserAccount;
import Business.Organization.Admin;
import Business.Organization.Doctor;
import Business.Organization.LabAssistant;

/**
 *
 * @author ran
 */
public class ConfigureABusiness {
    
    public static Business configure(){
        // Three roles: LabAssistant, Doctor, Admin
        
        Business business = Business.getInstance();
        
        Admin admin= new Admin();
        business.getOrganizationDirectory().getOrganizationList().add(admin);
        Employee employee= new Employee();
        employee.setName("PV");
        UserAccount account = new UserAccount();
        account.setUsername("admin");
        account.setPassword("admin");
        account.setRole("Admin");
        account.setEmployee(employee);
        
        //Add Employee to Admin
        admin.getEmployeeDirectory().getEmployeeList().add(employee);
        
        //Add Account into Admin
        admin.getUserAccountDirectory().getUserAccountList().add(account);
        
        ///LAB ASSISTANT IMPLEMENTATION 
        
        LabAssistant labAssistant= new LabAssistant();
        business.getOrganizationDirectory().getOrganizationList().add(labAssistant);
        Employee employeeLab= new Employee();
        employeeLab.setName("Ben");
        UserAccount accountLab = new UserAccount();
        accountLab.setUsername("lab");
        accountLab.setPassword("lab");
        accountLab.setRole("Lab Assistant");
        accountLab.setEmployee(employeeLab);
        
        //Add Employee to Admin
        labAssistant.getEmployeeDirectory().getEmployeeList().add(employeeLab);
        
        //Add Account into Admin
        labAssistant.getUserAccountDirectory().getUserAccountList().add(accountLab); 
        
        
        //DOCTOR IMPLEMENTATION
        
        Doctor doctor= new Doctor();
        business.getOrganizationDirectory().getOrganizationList().add(doctor);
        Employee employeeDoctor= new Employee();
        employeeDoctor.setName("Jackie");
        UserAccount accountDoctor = new UserAccount();
        accountDoctor.setUsername("doctor");
        accountDoctor.setPassword("doctor");
        accountDoctor.setRole("Doctor");
        accountDoctor.setEmployee(employeeDoctor);
        
        //Add Employee to Admin
        doctor.getEmployeeDirectory().getEmployeeList().add(employeeDoctor);
        
        //Add Account into Admin
        doctor.getUserAccountDirectory().getUserAccountList().add(accountDoctor);
        
        return business;
    }
    
}
