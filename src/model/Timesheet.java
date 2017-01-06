/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Omar
 */
public final class Timesheet {
   
    private IntegerProperty id;
    private ObjectProperty<LocalDate> tsDate;
    private ObjectProperty<Employee> employee;
    private ObjectProperty<Project> project;
    private StringProperty zone;
    private StringProperty siteName;
    private StringProperty description;
    private DoubleProperty transportAllowance;
    private DoubleProperty allowance;
    private DoubleProperty bonus;
    private StringProperty bonusReason;
    private DoubleProperty penalty;
    private StringProperty penaltyReason;

    public Timesheet() {
    }

    public Timesheet(int id, LocalDate tsDate, Employee emp, String zone,  double allowances, double bonuses, double penalties) {
        setID(id);
        setTSDate(tsDate);
        setEmployee(emp);
        setZone(zone);
        setAllowance(allowances);
        setBonus(bonuses);
        setPenalty(penalties);
    }
    
    public IntegerProperty idProperty(){
        return id;
    }
    
    public int getID(){
        return id.get();
    }
    
    public void setID(int id){
        if(this.id == null)
            this.id = new SimpleIntegerProperty(id);
        else
            this.id.set(id);
    }
    
    public ObjectProperty<LocalDate> tsDateProperty(){
        return tsDate;
    }
    
    public LocalDate getTSDate(){
        return tsDate.get();
    }
    
    public void setTSDate(LocalDate tsDate){
        if(this.tsDate == null)
            this.tsDate = new SimpleObjectProperty<>(tsDate);
        else
            this.tsDate.set(tsDate);
    }
    
    public ObjectProperty<Employee> employeeProperty(){
        return employee;
    }
    
    public Employee getEmployee(){
        return employee.get();
    }
    
    public void setEmployee(Employee emp){
        if(this.employee == null)
            this.employee = new SimpleObjectProperty<>(emp);
        else
            this.employee.set(emp);
    }
    
    public ObjectProperty<Project> projectProperty(){
        return project;
    }
    
    public Project getProject(){
        return project.get();
    }
    
    public void setProject(Project proj){
        if(this.project == null)
            this.project = new SimpleObjectProperty<>(proj);
        else
            this.project.set(proj);
    }
    
    /**
     * @return the zone
     */
    public StringProperty zoneProperty() {
        return zone;
    }

    /**
     * 
     * @return zone name
     */
    public String getZone(){
        return zone.get();
    }
    
    /**
     * @param zone the zone to set
     */
    public void setZone(String zone) {
        if(this.zone == null){
            this.zone = new SimpleStringProperty(zone);
        } else{
            this.zone.set(zone);
        }
    }
    
    /**
     * @return the zone
     */
    public StringProperty siteNameProperty() {
        return zone;
    }

    /**
     * 
     * @return zone name
     */
    public String getSiteName(){
        return zone.get();
    }
    
    /**
     * @param siteName the site name to set
     */
    public void setSiteName(String siteName) {
        if(this.siteName == null){
            this.siteName = new SimpleStringProperty(siteName);
        } else{
            this.siteName.set(siteName);
        }
    }
    
    /**
     * @return the description
     */
    public StringProperty descriptionProperty() {
        return zone;
    }

    /**
     * 
     * @return description
     */
    public String getDescription(){
        return zone.get();
    }
    
    /**
     * @param description the zone to set
     */
    public void setDescription(String description) {
        if(this.description == null){
            this.description = new SimpleStringProperty(description);
        } else{
            this.description.set(description);
        }
    }
    
    public DoubleProperty transportAllowanceProperty(){
        return transportAllowance;
    }
    
    public double getTransportAllowance(){
        return allowance.get();
    }
    
    public void setTransportAllowance(double transportAllowance){
        if(this.transportAllowance == null)
            this.transportAllowance = new SimpleDoubleProperty(transportAllowance);
        else
            this.transportAllowance.set(transportAllowance);
    }
    
    public DoubleProperty allowanceProperty(){
        return allowance;
    }
    
    public double getAllowance(){
        return allowance.get();
    }
    
    public void setAllowance(double allowance){
        if(this.allowance == null)
            this.allowance = new SimpleDoubleProperty(allowance);
        else
            this.allowance.set(allowance);
    }
    
    public DoubleProperty bonusProperty(){
        return bonus;
    }
    
    public double getBonus(){
        return bonus.get();
    }
    
    public void setBonus(double bonus){
        if(this.bonus == null)
            this.bonus = new SimpleDoubleProperty(bonus);
        else
            this.bonus.set(bonus);
    }
    
    /**
     * @return the bonus reason property
     */
    public StringProperty bonusReasonProperty() {
        return bonusReason;
    }

    /**
     * 
     * @return bonus reason
     */
    public String getBonusReason(){
        return bonusReason.get();
    }
    
    /**
     * @param bonusReason the bonus reason to set
     */
    public void setBonusReason(String bonusReason) {
        if(this.bonusReason == null){
            this.bonusReason = new SimpleStringProperty(bonusReason);
        } else{
            this.bonusReason.set(bonusReason);
        }
    }
    
    public DoubleProperty penaltyProperty(){
        return penalty;
    }
    
    public double getPenalty(){
        return penalty.get();
    }
    
    public void setPenalty(double penalty){
        if(this.penalty == null)
            this.penalty = new SimpleDoubleProperty(penalty);
        else
            this.penalty.set(penalty);
    }
    
    /**
     * @return the penalty's reason property
     */
    public StringProperty penaltyReasonProperty() {
        return penaltyReason;
    }

    /**
     * 
     * @return penalty's reason
     */
    public String getPenaltyReason(){
        return penaltyReason.get();
    }
    
    /**
     * @param penaltyReason the bonus reason to set
     */
    public void setPenaltyReason(String penaltyReason) {
        if(this.penaltyReason == null){
            this.penaltyReason = new SimpleStringProperty(penaltyReason);
        } else{
            this.penaltyReason.set(penaltyReason);
        }
    }

    @Override
    public String toString() {
        return "TimeSheet ID: " + getID() + ", Date: " + getTSDate();
                
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Timesheet other = (Timesheet) obj;
        return (Objects.equals(this.id, other.id));
            
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }   
    
    /**
 *
 * @author Omar
 */
public final class Project {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty customer;
    private StringProperty operator;
    private ObjectProperty<Employee> pm;

    public Project() {
    }

    public Project(int id, String name, String customer, String operator,
            Employee pm) {
        setID(id);
        setName(name);
        setCustomer(customer);
        setOperator(operator);
        setPM(pm);
    }
    
    /**
     * @return the id
     */
    public IntegerProperty idProperty() {
        return id;
    }
    
    /**
     * 
     * @return employee ID
     */
    public int getID(){
        return id.get();
    }

    /**
     * @param id the id to set
     */
    public void setID(int id) {
        if(this.id == null){
            this.id = new SimpleIntegerProperty(id);
        } else{
            this.id.set(id);
        }
    }

    /**
     * @return the name
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * 
     * @return employee name
     */
    public String getName(){
        return name.get();
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        if(this.name == null){
            this.name = new SimpleStringProperty(name);
        } else{
            this.name.set(name);
        }
    }

    /**
     * @return the customer
     */
    public StringProperty customerProperty() {
        return customer;
    }

    /**
     * 
     * @return customer name
     */
    public String getCustomer(){
        return customer.get();
    }
    
    /**
     * @param customer the name to set
     */
    public void setCustomer(String customer) {
        if(this.customer == null){
            this.customer = new SimpleStringProperty(customer);
        } else{
            this.customer.set(customer);
        }
    }

    /**
     * @return the customer
     */
    public StringProperty operatorProperty() {
        return customer;
    }

    /**
     * 
     * @return operator name
     */
    public String getOperator(){
        return operator.get();
    }
    
    /**
     * @param operator to set
     */
    public void setOperator(String operator) {
        if(this.operator == null){
            this.operator = new SimpleStringProperty(operator);
        } else{
            this.operator.set(operator);
        }
    }
    
    public ObjectProperty<Employee> pmProperty(){
        return pm;
    }
    
    public Employee getPM(){
        return pm.get();
    }
    
    public void setPM(Employee pm){
        if(this.pm == null)
            this.pm = new SimpleObjectProperty<>(pm);
        else
            this.pm.set(pm);
    }
    
    @Override
    public String toString() {
        return "(ID: " + getID() + ")" + " " + getName();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Project other = (Project) obj;
        return (Objects.equals(this.id, other.id));
           
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }
}

    
}
