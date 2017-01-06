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
import model.Timesheet;
import utils.DBConnection;

/**
 *
 * @author Omar
 */
public class cProject {

    public Optional<Timesheet.Project> getByID(int id) {
        final String GET_BY_ID_SQL = "SELECT * FROM project AS proj "
                + "INNER JOIN employee AS emp ON(proj.pm_id = emp.id) "
                + "WHERE proj.id=?";
        Optional<Timesheet.Project> optProject = Optional.empty();
        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(GET_BY_ID_SQL)) {
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();

            if (rs.first()) {
                Employee emp = new Employee();
                Timesheet.Project proj = new Timesheet().new Project();
                rs.beforeFirst();
                while (rs.next()) {
                    emp.setID(rs.getInt("emp.id"));
                    emp.setName(rs.getString("emp.name"));
                    emp.setPhoto(rs.getString("emp.photo"));
                    emp.setDepartment(rs.getString("emp.department"));
                    emp.setTitle(rs.getString("emp.title"));
                    emp.setNationalID(rs.getString("emp.national_id"));
                    emp.setInsuranceNo(rs.getString("emp.insurance_num"));
                    emp.setHiringDate(rs.getDate("emp.hiring_date").toLocalDate());
                    emp.setBasicSalary(rs.getDouble("emp.basic_salary"));
                    emp.setGrossSalary(rs.getDouble("emp.gross_salary"));
                    emp.setInsDeduction(rs.getDouble("emp.insurance_deduction"));
                    emp.setHealthInsDeduction(rs.getDouble("emp.health_ins_deduction"));
                    emp.setOtherDeduction(rs.getDouble("emp.other_deduction"));

                    proj.setID(id);
                    proj.setName(rs.getString("proj.name"));
                    proj.setCustomer(rs.getString("proj.customer"));
                    proj.setOperator(rs.getString("proj.operator"));
                    proj.setPM(emp);
                }

                optProject = Optional.ofNullable(proj);
                rs.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(cEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }

        return optProject;
    }

    public Optional<Timesheet.Project> getByName(String name) {
        final String GET_BY_Name_SQL = "SELECT * FROM project AS proj"
                + "INNER JOIN employee AS emp ON(proj.pm_id = emp.id) "
                + "WHERE proj.name=?";
        Optional<Timesheet.Project> optProject = Optional.empty();
        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(GET_BY_Name_SQL)) {
            prep.setString(1, name);
            ResultSet rs = prep.executeQuery();

            if (rs.first()) {
                Timesheet.Project proj = new Timesheet().new Project();
                Employee emp = new Employee();
                rs.beforeFirst();
                while (rs.next()) {
                    emp.setID(rs.getInt("emp.id"));
                    emp.setName(rs.getString("emp.name"));
                    emp.setPhoto(rs.getString("emp.photo"));
                    emp.setDepartment(rs.getString("emp.department"));
                    emp.setTitle(rs.getString("emp.title"));
                    emp.setNationalID(rs.getString("emp.national_id"));
                    emp.setInsuranceNo(rs.getString("emp.insurance_num"));
                    emp.setHiringDate(rs.getDate("emp.hiring_date").toLocalDate());
                    emp.setBasicSalary(rs.getDouble("emp.basic_salary"));
                    emp.setGrossSalary(rs.getDouble("emp.gross_salary"));
                    emp.setInsDeduction(rs.getDouble("emp.insurance_deduction"));
                    emp.setHealthInsDeduction(rs.getDouble("emp.health_ins_deduction"));
                    emp.setOtherDeduction(rs.getDouble("emp.other_deduction"));

                    proj.setID(rs.getInt("proj.id"));
                    proj.setName(rs.getString("proj.name"));
                    proj.setCustomer(rs.getString("proj.customer"));
                    proj.setOperator(rs.getString("proj.operator"));
                    proj.setPM(emp);
                }

                optProject = Optional.ofNullable(proj);
                rs.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(cEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }

        return optProject;
    }

    public List<Timesheet.Project> getAll() throws SQLException {
        final String GET_ALL_SQL = "SELECT * FROM project AS proj"
                + "INNER JOIN employee AS emp ON(proj.pm_id = emp.id)";
        List<Timesheet.Project> projects = new ArrayList<>();

        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(GET_ALL_SQL)) {

            ResultSet rs = prep.executeQuery();

            if (rs.first()) {

                rs.beforeFirst();
                while (rs.next()) {
                    Timesheet.Project proj = new Timesheet().new Project();
                    Employee emp = new Employee();
                    
                    emp.setID(rs.getInt("emp.id"));
                    emp.setName(rs.getString("emp.name"));
                    emp.setPhoto(rs.getString("emp.photo"));
                    emp.setDepartment(rs.getString("emp.department"));
                    emp.setTitle(rs.getString("emp.title"));
                    emp.setNationalID(rs.getString("emp.national_id"));
                    emp.setInsuranceNo(rs.getString("emp.insurance_num"));
                    emp.setHiringDate(rs.getDate("emp.hiring_date").toLocalDate());
                    emp.setBasicSalary(rs.getDouble("emp.basic_salary"));
                    emp.setGrossSalary(rs.getDouble("emp.gross_salary"));
                    emp.setInsDeduction(rs.getDouble("emp.insurance_deduction"));
                    emp.setHealthInsDeduction(rs.getDouble("emp.health_ins_deduction"));
                    emp.setOtherDeduction(rs.getDouble("emp.other_deduction"));

                    proj.setID(rs.getInt("proj.id"));
                    proj.setName(rs.getString("proj.name"));
                    proj.setCustomer(rs.getString("proj.customer"));
                    proj.setOperator(rs.getString("proj.operator"));
                    proj.setPM(emp);

                    projects.add(proj);
                }

                rs.close();
                DBConnection.close();
            }

        } catch (SQLException ex) {
            throw ex;
        }

        return projects;
    }

    public int insert(Timesheet.Project proj) {
        //TODO when needed
        return -1;
    }

    public int update(Employee employee) {
        //TODO when needed
        return -1;
    }

    public int delete(int id) {
        final String DELETE_QUERY = "DELETE FROM project WHERE id = ?";

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
        final String LAST_ID_SQL = "SELECT id FROM project ORDER BY id DESC LIMIT 1";
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
