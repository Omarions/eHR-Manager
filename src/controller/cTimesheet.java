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
import java.time.LocalDate;
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
public class cTimesheet {

    public Optional<Timesheet> getByID(int id) {
        final String GET_BY_ID_SQL = "SELECT ts.*, emp.* "
                + "FROM timesheet as ts "
                + "INNER JOIN project as proj ON (ts.project_id = proj.id) "
                + "INNER JOIN employee as emp ON(ts.emp_id = emp.id) "
                + "WHERE id=?";
        Optional<Timesheet> optTimesheet = Optional.empty();
        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(GET_BY_ID_SQL)) {
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();

            if (rs.first()) {
                Employee emp = new Employee();
                Timesheet ts = new Timesheet();
                int projectID = 0;

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

                    ts.setID(id);
                    ts.setTSDate(rs.getDate("ts.ts_date").toLocalDate());
                    ts.setEmployee(emp);
                    projectID = rs.getInt("ts.project_id");
                    ts.setZone(rs.getString("zone"));
                    ts.setSiteName(rs.getString("ts.site_name"));
                    ts.setDescription(rs.getString("ts.description"));
                    ts.setTransportAllowance(rs.getDouble("ts.transport_allowance"));
                    ts.setAllowance(rs.getDouble("ts.allowance"));
                    ts.setBonus(rs.getDouble("ts.bonus"));
                    ts.setBonusReason(rs.getString("ts.bonus_reason"));
                    ts.setPenalty(rs.getDouble("ts.penalty"));
                    ts.setPenaltyReason(rs.getString("ts.penalty_reason"));
                }
                new cProject().getByID(projectID).ifPresent((p) -> ts.setProject(p));
                optTimesheet = Optional.ofNullable(ts);
                rs.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(cEmployee.class.getName()).log(Level.SEVERE, null, ex);

        }

        return optTimesheet;
    }

        public Optional<Timesheet> getEmpTSByDay(LocalDate date, int empID) {
        //the function EXTRACT(YEAR_MONTH FROM "yyyymmdd") which "yyyymmdd" 
        //is date formate in digits as string and it extracts only year and month 
        //as digits from the date given like (yyyymm).
        //e.g if the input is (2016-11-15) the output is 201611.
        final String GET_BY_Name_SQL = "SELECT ts.*, emp.* FROM timesheet AS ts "
                + "INNER JOIN employee AS emp ON(ts.emp_id = emp.id) "
                + "WHERE "
                + "ts.ts_date= ?"
                + "AND emp_id= ?";

        Optional<Timesheet> optTimesheet = Optional.empty();

        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(GET_BY_Name_SQL)) {

            prep.setString(1, date.toString());
            prep.setInt(2, empID);

            ResultSet rs = prep.executeQuery();

            if (rs.first()) {
                Employee emp = new Employee();
                Timesheet ts = new Timesheet();
                int projectID = 0;
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

                    ts.setID(rs.getInt("ts.id"));
                    ts.setTSDate(rs.getDate("ts.ts_date").toLocalDate());
                    ts.setEmployee(emp);
                    projectID = rs.getInt("ts.project_id");
                    ts.setZone(rs.getString("zone"));
                    ts.setSiteName(rs.getString("ts.site_name"));
                    ts.setDescription(rs.getString("ts.description"));
                    ts.setTransportAllowance(rs.getDouble("ts.transport_allowance"));
                    ts.setAllowance(rs.getDouble("ts.allowance"));
                    ts.setBonus(rs.getDouble("ts.bonus"));
                    ts.setBonusReason(rs.getString("ts.bonus_reason"));
                    ts.setPenalty(rs.getDouble("ts.penalty"));
                    ts.setPenaltyReason(rs.getString("ts.penalty_reason"));
                }
                new cProject().getByID(projectID).ifPresent((p) -> ts.setProject(p));

                optTimesheet = Optional.ofNullable(ts);
                rs.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(cEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }

        return optTimesheet;
    }
    
    public Optional<Timesheet> getEmpTSByMonth(LocalDate date, int empID) {
        //the function EXTRACT(YEAR_MONTH FROM "yyyymmdd") which "yyyymmdd" 
        //is date formate in digits as string and it extracts only year and month 
        //as digits from the date given like (yyyymm).
        //e.g if the input is (2016-11-15) the output is 201611.
        final String GET_BY_Name_SQL = "SELECT ts.*, emp.* FROM timesheet AS ts "
                + "INNER JOIN employee AS emp ON(ts.emp_id = emp.id) "
                + "WHERE "
                + "EXTRACT(YEAR_MONTH FROM ts.ts_date)=EXTRACT(YEAR_MONTH FROM ?)"
                + "AND emp_id= ?";

        Optional<Timesheet> optTimesheet = Optional.empty();

        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(GET_BY_Name_SQL)) {

            prep.setString(1, date.toString());
            prep.setInt(2, empID);

            ResultSet rs = prep.executeQuery();

            if (rs.first()) {
                Employee emp = new Employee();
                Timesheet ts = new Timesheet();
                int projectID = 0;
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

                    ts.setID(rs.getInt("ts.id"));
                    ts.setTSDate(rs.getDate("ts.ts_date").toLocalDate());
                    ts.setEmployee(emp);
                    projectID = rs.getInt("ts.project_id");
                    ts.setZone(rs.getString("zone"));
                    ts.setSiteName(rs.getString("ts.site_name"));
                    ts.setDescription(rs.getString("ts.description"));
                    ts.setTransportAllowance(rs.getDouble("ts.transport_allowance"));
                    ts.setAllowance(rs.getDouble("ts.allowance"));
                    ts.setBonus(rs.getDouble("ts.bonus"));
                    ts.setBonusReason(rs.getString("ts.bonus_reason"));
                    ts.setPenalty(rs.getDouble("ts.penalty"));
                    ts.setPenaltyReason(rs.getString("ts.penalty_reason"));
                }
                new cProject().getByID(projectID).ifPresent((p) -> ts.setProject(p));

                optTimesheet = Optional.ofNullable(ts);
                rs.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(cEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }

        return optTimesheet;
    }

    public List<Timesheet> getAllTSByDate(LocalDate date) {
        //the function EXTRACT(YEAR_MONTH FROM "yyyymmdd") which "yyyymmdd" 
        //is date formate in digits as string and it extracts only year and month 
        //as digits from the date given like (yyyymm).
        //e.g if the input is (2016-11-15) the output is 201611.
        final String GET_BY_Name_SQL = "SELECT ts.*, emp.* FROM timesheet AS ts "
                + "INNER JOIN employee AS emp ON(ts.emp_id = emp.id) "
                + "WHERE "
                + "EXTRACT(YEAR_MONTH FROM ts.ts_year_month)=EXTRACT(YEAR_MONTH FROM ?)";

        List<Timesheet> timesheets = new ArrayList<>();

        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(GET_BY_Name_SQL)) {

            prep.setString(1, date.toString());

            ResultSet rs = prep.executeQuery();

            if (rs.first()) {
                rs.beforeFirst();
                while (rs.next()) {
                    Employee emp = new Employee();
                    Timesheet ts = new Timesheet();
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

                    ts.setID(rs.getInt("ts.id"));
                    ts.setTSDate(rs.getDate("ts.ts_date").toLocalDate());
                    ts.setEmployee(emp);
                    int projectID = rs.getInt("ts.project_id");
                    ts.setZone(rs.getString("zone"));
                    ts.setSiteName(rs.getString("ts.site_name"));
                    ts.setDescription(rs.getString("ts.description"));
                    ts.setTransportAllowance(rs.getDouble("ts.transport_allowance"));
                    ts.setAllowance(rs.getDouble("ts.allowance"));
                    ts.setBonus(rs.getDouble("ts.bonus"));
                    ts.setBonusReason(rs.getString("ts.bonus_reason"));
                    ts.setPenalty(rs.getDouble("ts.penalty"));
                    ts.setPenaltyReason(rs.getString("ts.penalty_reason"));

                    new cProject().getByID(projectID).ifPresent((p) -> ts.setProject(p));
                    
                    timesheets.add(ts);
                }
                rs.close();
                DBConnection.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(cEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }

        return timesheets;
    }

    public List<Timesheet> getAll() throws SQLException {
        final String GET_ALL_SQL = "SELECT ts.*, emp.* FROM timesheet AS ts "
                + "INNER JOIN employee AS emp ON(ts.emp_id = emp.id)";

        List<Timesheet> timesheets = new ArrayList<>();

        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(GET_ALL_SQL)) {

            ResultSet rs = prep.executeQuery();

            if (rs.first()) {

                rs.beforeFirst();
                while (rs.next()) {
                    Employee emp = new Employee();
                    Timesheet ts = new Timesheet();
                    
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

                    ts.setID(rs.getInt("ts.id"));
                    ts.setTSDate(rs.getDate("ts.ts_date").toLocalDate());
                    ts.setEmployee(emp);
                    int projectID = rs.getInt("ts.project_id");
                    ts.setZone(rs.getString("zone"));
                    ts.setSiteName(rs.getString("ts.site_name"));
                    ts.setDescription(rs.getString("ts.description"));
                    ts.setTransportAllowance(rs.getDouble("ts.transport_allowance"));
                    ts.setAllowance(rs.getDouble("ts.allowance"));
                    ts.setBonus(rs.getDouble("ts.bonus"));
                    ts.setBonusReason(rs.getString("ts.bonus_reason"));
                    ts.setPenalty(rs.getDouble("ts.penalty"));
                    ts.setPenaltyReason(rs.getString("ts.penalty_reason"));

                    new cProject().getByID(projectID).ifPresent((p) -> ts.setProject(p));
                    
                    timesheets.add(ts);
                }

                rs.close();
                DBConnection.close();
            }

        } catch (SQLException ex) {
            throw ex;
        }

        return timesheets;
    }

    public int insert(Timesheet ts) {
        final String INSERT_QUERY = "INSERT INTO timesheet(ts_date, emp_id, "
                + "project_id, zone, site_name, description, transport_allowance,"
                + "allowance, bonus, bonus_reason, penalty, penalty_reason "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement prep
                    = DBConnection.getConnection().prepareStatement(INSERT_QUERY);
            
            prep.setDate(1, new Date(ts.getTSDate().toEpochDay()));
            prep.setInt(2, ts.getEmployee().getID());
            prep.setInt(3, ts.getProject().getID());
            prep.setString(4, ts.getZone());
            prep.setString(5, ts.getSiteName());
            prep.setString(6, ts.getDescription());
            prep.setDouble(7, ts.getTransportAllowance());
            prep.setDouble(8, ts.getAllowance());
            prep.setDouble(9, ts.getBonus());
            prep.setString(10, ts.getBonusReason());
            prep.setDouble(11, ts.getPenalty());
            prep.setString(12, ts.getPenaltyReason());

            prep.executeUpdate();

            int id = getLastID();
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(cEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public int update(Timesheet ts) {
        final String UPDATE_QUERY = "UPDATE timesheet SET ts_date = ?, "
                + "emp_id = ?, project_id = ?, zone = ?, site_name = ?, description = ?, "
                + "transport_allowance = ?, allowance = ?, bonus = ?, bonus_reason = ?, "
                + "penalties = ?, penalty_reason = ? "
                + "WHERE id = ?";

        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(UPDATE_QUERY)) {

            prep.setDate(1, new Date(ts.getTSDate().toEpochDay()));
            prep.setInt(2, ts.getEmployee().getID());
            prep.setInt(3, ts.getProject().getID());
            prep.setString(4, ts.getZone());
            prep.setString(5, ts.getSiteName());
            prep.setString(6, ts.getDescription());
            prep.setDouble(7, ts.getTransportAllowance());
            prep.setDouble(8, ts.getAllowance());
            prep.setDouble(9, ts.getBonus());
            prep.setString(10, ts.getBonusReason());
            prep.setDouble(11, ts.getPenalty());
            prep.setString(12, ts.getPenaltyReason());

            prep.setInt(13, ts.getID());

            return prep.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(cEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public int delete(int id) {
        final String DELETE_QUERY = "DELETE FROM timesheet WHERE id = ?";

        try (PreparedStatement prep
                = DBConnection.getConnection().prepareStatement(DELETE_QUERY)) {

            prep.setInt(1, id);
            int res = prep.executeUpdate();

            return res;

        } catch (SQLException ex) {
            Logger.getLogger(cEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public int getLastID() {
        final String LAST_ID_SQL = "SELECT id FROM timesheet ORDER BY id DESC LIMIT 1";
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
