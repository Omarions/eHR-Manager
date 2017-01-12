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
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Omar
 */
public final class Employee {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty photo;
    private StringProperty dept;
    private StringProperty title;
    private StringProperty national_ID;
    private StringProperty insuranceNum;
    private ObjectProperty<LocalDate> hiringDate;
    private DoubleProperty basicSalary;
    private DoubleProperty grossSalary;
    private DoubleProperty insDeduction;
    private DoubleProperty healthInsDeduction;
    private ReadOnlyDoubleWrapper incomeTaxDeduction = new ReadOnlyDoubleWrapper(this, "IncomeTaxDeduction", 00.00);
    private DoubleProperty otherDeduction;

    public Employee() {
    }

    public Employee(int id, String name, String photo, String dept, String title,
            String nationalID, String insuranceNo, LocalDate hiringDate, 
            double basicSalary, double grossSalary, double insDeduction, 
            double healthInsDeduction, double otherDedcution) {
        setID(id);
        setName(name);
        setPhoto(photo);
        setDepartment(dept);
        setTitle(title);
        setNationalID(nationalID);
        setInsuranceNo(insuranceNo);
        setHiringDate(hiringDate);
        setBasicSalary(basicSalary);
        setGrossSalary(grossSalary);
        setInsDeduction(insDeduction);
        setHealthInsDeduction(healthInsDeduction);
        setOtherDeduction(otherDedcution);
        
        
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
     * 
     * @return photo property
     */
    public StringProperty photoProperty() {
        return photo;
    }

    /**
     * 
     * @return employee photo file name.
     */
    public String getPhoto(){
        return photo.get();
    }
    
    /**
     * @param photo
     */
    public void setPhoto(String photo) {
        if(this.photo == null){
            this.photo = new SimpleStringProperty(photo);
        } else{
            this.photo.set(photo);
        }
    }
    
    /**
     * @return the name
     */
    public StringProperty departmentProperty() {
        return dept;
    }

    /**
     * 
     * @return employee department
     */
    public String getDepartment(){
        return dept.get();
    }
    
    /**
     * @param dept
     */
    public void setDepartment(String dept) {
        if(this.dept == null){
            this.dept = new SimpleStringProperty(dept);
        } else{
            this.dept.set(dept);
        }
    }
    
    /**
     * @return the name
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * 
     * @return employee title
     */
    public String getTitle(){
        return title.get();
    }
    
    /**
     * @param title
     */
    public void setTitle(String title) {
        if(this.title == null){
            this.title = new SimpleStringProperty(title);
        } else{
            this.title.set(title);
        }
    }
    
        /**
     * @return the name
     */
    public StringProperty nationalIDProperty() {
        return national_ID;
    }

    /**
     * 
     * @return employee national ID
     */
    public String getNationalID(){
        return national_ID.get();
    }
    
    /**
     * @param nationalID
     */
    public void setNationalID(String nationalID) {
        if(this.national_ID == null){
            this.national_ID = new SimpleStringProperty(nationalID);
        } else{
            this.national_ID.set(nationalID);
        }
    }
    
     /**
     * @return the insurance number
     */
    public StringProperty insuranceNumProperty() {
        return insuranceNum;
    }

    public String getInsuranceNum(){
        return insuranceNum.get();
    }
    
    /**
     * @param insNo
     */
    public void setInsuranceNo(String insNo) {
        if(this.insuranceNum == null){
            this.insuranceNum = new SimpleStringProperty(insNo);
        } else{
            this.insuranceNum.set(insNo);
        }
    }
    
    /**
     * @return the name
     */
    public ObjectProperty<LocalDate> hiringDateProperty() {
        return hiringDate;
    }

    /**
     * Get Hiring Date of employee
     * @return the local date object of hiring employee
     */
    public LocalDate getHiringDate(){
        return hiringDate.get();
    }
    
    /**
     * @param hiringDate
     */
    public void setHiringDate(LocalDate hiringDate) {
        if(this.hiringDate == null){
            this.hiringDate = new SimpleObjectProperty<>(hiringDate);
        } else{
            this.hiringDate.set(hiringDate);
        }
    }
    
    /**
     * @return the name
     */
    public DoubleProperty basicSalaryProperty() {
        return basicSalary;
    }

    public double getBasicSalary(){
        return basicSalary.get();
    }
    
    /**
     * @param basicSalary
     */
    public void setBasicSalary(double basicSalary) {
        if(this.basicSalary == null){
            this.basicSalary = new SimpleDoubleProperty(basicSalary);
            setIncomeTaxDeduction();
        } else{
            this.basicSalary.set(basicSalary);
            setIncomeTaxDeduction();
        }
    }
    
    /**
     * @return the name
     */
    public DoubleProperty GrossSalaryProperty() {
        return grossSalary;
    }

    public double getGrossSalary(){
        return grossSalary.get();
    }
    
    /**
     * @param grossSalary
     */
    public void setGrossSalary(double grossSalary) {
        if(this.grossSalary == null){
            this.grossSalary = new SimpleDoubleProperty(grossSalary);
        } else{
            this.grossSalary.set(grossSalary);
        }
    }

    public DoubleProperty insDeductionProperty() {
        return insDeduction;
    }

    public double getInsDeduction(){
        return insDeduction.get();
    }
    
    /**
     * @param insDeduction
     */
    public void setInsDeduction(double insDeduction) {
        if(this.insDeduction == null){
            this.insDeduction = new SimpleDoubleProperty(insDeduction);
        } else{
            this.insDeduction.set(insDeduction);
        }
    }
    
    public DoubleProperty healthInsDeductionProperty() {
        return healthInsDeduction;
    }

    public double getHealthInsDeduction(){
        return healthInsDeduction.get();
    }
    
    /**
     * @param healthInsDeduction
     */
    public void setHealthInsDeduction(double healthInsDeduction) {
        if(this.healthInsDeduction == null){
            this.healthInsDeduction = new SimpleDoubleProperty(healthInsDeduction);
        } else{
            this.healthInsDeduction.set(healthInsDeduction);
        }
    }
    
    public ReadOnlyDoubleProperty incomeTaxDeductionProperty(){
        return incomeTaxDeduction;
    }
    
    public double getIncomeTaxDeduction(){
        return incomeTaxDeduction.get();
    }
    
    private void setIncomeTaxDeduction(){
        if(basicSalary.get() <= 1000)
            incomeTaxDeduction.set(00.20 * basicSalary.get());
        else{
            incomeTaxDeduction.set(00.40 * basicSalary.get());
        }
    }
    
    public DoubleProperty otherDeductionProperty() {
        return otherDeduction;
    }

    public double getOtherDeduction(){
        return otherDeduction.get();
    }
    
    /**
     * @param otherDeduction
     */
    public void setOtherDeduction(double otherDeduction) {
        if(this.otherDeduction == null){
            this.otherDeduction = new SimpleDoubleProperty(otherDeduction);
        } else{
            this.otherDeduction.set(otherDeduction);
        }
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
        
        final Employee other = (Employee) obj;
        return (Objects.equals(this.id, other.id));
           
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
    
    
}
