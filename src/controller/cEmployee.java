/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import utils.Constants;
import utils.DBConnection;

/**
 *
 * @author Omar
 */
public class cEmployee {

    public Optional<Employee> getByID(int id) {
        final String GET_BY_ID_SQL = "SELECT * FROM employee WHERE id=?";
        Optional<Employee> employee = Optional.empty();
        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(GET_BY_ID_SQL)) {
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();

            if (rs.first()) {
                Employee emp = new Employee();
                rs.beforeFirst();
                while (rs.next()) {
                    emp.setID(id);
                    emp.setName(rs.getString("name"));
                    emp.setPhoto(rs.getString("photo"));
                    emp.setDepartment(rs.getString("department"));
                    emp.setTitle(rs.getString("title"));
                    emp.setNationalID(rs.getString("national_id"));
                    emp.setInsuranceNo(rs.getString("insurance_num"));
                    emp.setHiringDate(rs.getDate("hiring_date").toLocalDate());
                    emp.setBasicSalary(rs.getDouble("basic_salary"));
                    emp.setGrossSalary(rs.getDouble("gross_salary"));
                    emp.setInsDeduction(rs.getDouble("insurance_deduction"));
                    emp.setHealthInsDeduction(rs.getDouble("health_ins_deduction"));
                    emp.setOtherDeduction(rs.getDouble("other_deduction"));
                }

                employee = Optional.ofNullable(emp);
                rs.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(cEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }

        return employee;
    }

    public Optional<Employee> getByName(String name) {
        final String GET_BY_Name_SQL = "SELECT * FROM employee WHERE name=?";
        Optional<Employee> employee = Optional.empty();
        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(GET_BY_Name_SQL)) {
            prep.setString(1, name);
            ResultSet rs = prep.executeQuery();

            if (rs.first()) {
                Employee emp = new Employee();
                rs.beforeFirst();
                while (rs.next()) {
                    emp.setID(rs.getInt("id"));
                    emp.setName(rs.getString("name"));
                    emp.setPhoto(rs.getString("photo"));
                    emp.setDepartment(rs.getString("department"));
                    emp.setTitle(rs.getString("title"));
                    emp.setNationalID(rs.getString("national_id"));
                    emp.setInsuranceNo(rs.getString("insurance_num"));
                    emp.setHiringDate(rs.getDate("hiring_date").toLocalDate());
                    emp.setBasicSalary(rs.getDouble("basic_salary"));
                    emp.setGrossSalary(rs.getDouble("gross_salary"));
                    emp.setInsDeduction(rs.getDouble("insurance_deduction"));
                    emp.setHealthInsDeduction(rs.getDouble("health_ins_deduction"));
                    emp.setOtherDeduction(rs.getDouble("other_deduction"));
                }

                employee = Optional.ofNullable(emp);
                rs.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(cEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }

        return employee;
    }

    public List<Employee> getAll() throws SQLException {
        final String GET_ALL_SQL = "SELECT * FROM employee";
        List<Employee> employees = new ArrayList<>();

        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(GET_ALL_SQL)) {

            ResultSet rs = prep.executeQuery();

            if (rs.first()) {

                rs.beforeFirst();
                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setID(rs.getInt("id"));
                    emp.setName(rs.getString("name"));
                    emp.setPhoto(rs.getString("photo"));
                    emp.setDepartment(rs.getString("department"));
                    emp.setTitle(rs.getString("title"));
                    emp.setNationalID(rs.getString("national_id"));
                    emp.setInsuranceNo(rs.getString("insurance_num"));
                    emp.setHiringDate(rs.getDate("hiring_date").toLocalDate());
                    emp.setBasicSalary(rs.getDouble("basic_salary"));
                    emp.setGrossSalary(rs.getDouble("gross_salary"));
                    emp.setInsDeduction(rs.getDouble("insurance_deduction"));
                    emp.setHealthInsDeduction(rs.getDouble("health_ins_deduction"));
                    emp.setOtherDeduction(rs.getDouble("other_deduction"));

                    employees.add(emp);
                }

                rs.close();
                DBConnection.close();
            }

        } catch (SQLException ex) {
            throw ex;
        }

        return employees;
    }

    public int insert(Employee employee) {
        final String INSERT_QUERY = "INSERT INTO employee(name, photo, department, "
                + "title, national_id, insurance_num, hiring_date, basic_salary, "
                + "gross_salary, insurance_deduction, health_ins_deduction, "
                + "other_deduction) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(INSERT_QUERY)) {

            prep.setString(1, employee.getName());
            prep.setString(2, employee.getPhoto());
            prep.setString(3, employee.getDepartment());
            prep.setString(4, employee.getTitle());
            prep.setString(5, employee.getNationalID());
            prep.setString(6, employee.getInsuranceNum());
            prep.setDate(7, new Date(employee.getHiringDate().toEpochDay()));
            prep.setDouble(8, employee.getBasicSalary());
            prep.setDouble(9, employee.getGrossSalary());
            prep.setDouble(10, employee.getInsDeduction());
            prep.setDouble(11, employee.getHealthInsDeduction());
            prep.setDouble(12, employee.getOtherDeduction());

            prep.executeUpdate();

            int id = getLastID();
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(cEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public int update(Employee employee) {
        final String UPDATE_QUERY = "UPDATE employee SET name = ?, photo = ?,"
                + "department = ?, title = ?, national_id = ?, insurance_num = ?,"
                + "hiring_date = ?, basic_salary = ?, gross_salary = ?,"
                + " insurance_deduction = ?, health_ins_deduction = ?, "
                + "other_deduction = ? WHERE id = ?";

        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(UPDATE_QUERY)) {

            prep.setString(1, employee.getName());
            prep.setString(2, employee.getPhoto());
            prep.setString(3, employee.getDepartment());
            prep.setString(4, employee.getTitle());
            prep.setString(5, employee.getNationalID());
            prep.setString(6, employee.getInsuranceNum());
            prep.setDate(7, Date.valueOf(employee.getHiringDate()));
            prep.setDouble(8, employee.getBasicSalary());
            prep.setDouble(9, employee.getGrossSalary());
            prep.setDouble(10, employee.getInsDeduction());
            prep.setDouble(11, employee.getHealthInsDeduction());
            prep.setDouble(12, employee.getOtherDeduction());

            prep.setInt(13, employee.getID());

            return prep.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(cEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public int delete(int id) {
        final String DELETE_QUERY = "DELETE FROM employee WHERE id = ?";

        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(DELETE_QUERY)) {

            prep.setInt(1, id);
            
            return prep.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(cEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public int getLastID() {
        final String LAST_ID_SQL = "SELECT id FROM employee ORDER BY id DESC LIMIT 1";
        try (PreparedStatement prep = DBConnection.getConnection().prepareStatement(LAST_ID_SQL)) {
            ResultSet rs = prep.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(cEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}
