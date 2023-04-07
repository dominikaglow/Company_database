import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Management {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:C:/Users/Dominika/wakacje/BazaDanych/test.db";
    Statement st;
    Connection con  = null;

    public Management() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Dominika/wakacje/Databse/src/test/java/test.db");
            st = con.createStatement();
        }
        catch (SQLException throwables) {
            throw new Error("Connection failed", throwables);
        }
    }

    public void closeCon(){
        try{
            con.close();
        } catch (SQLException throwables) {
            throw new Error("Closing database failed.", throwables);
        }
    }

    // adding a new location
    public boolean insertLocation(String loc){
        try{
            PreparedStatement prepst = con.prepareStatement("insert into LOCATION values (NULL, ?);");
            prepst.setString(1, loc);
            prepst.executeUpdate();
            prepst.close();
        } catch (SQLException throwables) {
            System.out.println("Inserting new location failed");
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    // returns map with all available locations
    public Map<Integer, String> printLocation(){
        Map<Integer, String> locMap = new HashMap<>();
        try{
            ResultSet res = st.executeQuery("SELECT * FROM LOCATION");
            int num;
            String loc;

            while(res.next()){
                num = res.getInt("LOCNO"); // location id
                loc = res.getString("LOCNAME"); // location
                locMap.put(num, loc);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return locMap;
    }

    // adding a new department
    public boolean insertDepartment(String dep, String number){
        try{
            int num = Integer.parseInt(number);
            Statement st = con.createStatement();
            PreparedStatement prepst = con.prepareStatement("insert into DEPT values (NULL, ?, ?);");
            String location = "SELECT LOCNAME FROM LOCATION WHERE LOCNO = " + num;
            prepst.setString(1, dep);
            prepst.setInt(2, num);
            prepst.executeUpdate();
            prepst.close();
        } catch (SQLException throwables) {
            System.out.println("Inserting new department failed");
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    // returns list with all available departments
    public List<Department> printDep(){
        List<Department> depList = new ArrayList<>();
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM DEPT");
            ResultSet rs2;
            Statement stmt = con.createStatement();
            int locID;
            while(rs.next()){
                Department dep = new Department();
                dep.id = rs.getInt("DEPTNO");
                dep.name = rs.getString("DNAME");
                locID = rs.getInt("DLOCNO");
                rs2 = stmt.executeQuery("SELECT LOCNAME FROM LOCATION WHERE LOCNO = " + locID);
                dep.loc = rs2.getString("LOCNAME");
                depList.add(dep);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return depList;
    }

    // adding a new position
    public boolean insertPosition(String posName){
        try{
            PreparedStatement prepst = con.prepareStatement("insert into POSITION values (NULL, ?);");
            prepst.setString(1, posName);
            prepst.executeUpdate();
            prepst.close();
        } catch (SQLException throwables) {
            System.out.println("Inserting new position failed");
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    // returns map with all available positions
    public Map<String, String> printPos(){
        Map<String, String> posMap = new HashMap<>();
        try{
            ResultSet res = st.executeQuery("SELECT * FROM POSITION");
            String num;
            String pos;

            while(res.next()){
                num = String.valueOf(res.getInt("POSNO")); // position id
                pos = res.getString("POSNAME"); // position name
                posMap.put(num, pos);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return posMap;
    }


    // adding a new employee
    public boolean insertEmployee(String name, String surname, Integer position, String date,
                                  Integer department, String email, String phoneNum){
        try{
            PreparedStatement prepst = con.prepareStatement("insert into EMPLOYEE values (NULL, ?, ?, ?, ?, ?, ?, ?);");
            prepst.setString(1, name);
            prepst.setString(2, surname);
            prepst.setInt(3, position);
            prepst.setString(4, date);
            prepst.setInt(5, department);
            prepst.setString(6, email);
            prepst.setString(7, phoneNum);
            prepst.executeUpdate();
            prepst.close();
        } catch (SQLException throwables) {
            System.out.println("Inserting new location failed");
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    // returns list of all employees
    public List<Employee> printEmp(){
        List<Employee> empList = new ArrayList<>();
        try{
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM EMPLOYEE");
            ResultSet rs2, rs3;
            int position, department;
            while(res.next()){
                Employee e = new Employee();
                e.num = res.getInt("EMPNO"); //id pracownika
                e.name = res.getString("EFIRST");
                e.surname = res.getString("ELAST");
                position = res.getInt("EPOSNO");
                rs2 = st2.executeQuery("SELECT POSNAME FROM POSITION WHERE POSNO = " + position);
                e.pos = rs2.getString("POSNAME");
                e.date = res.getString("EHIREDATE");
                department = res.getInt("EDEPTNO");
                rs3 = st3.executeQuery("SELECT DNAME FROM DEPT WHERE DEPTNO = " + department);
                e.dep = rs3.getString("DNAME");
                e.email = res.getString("EEMAIL");
                e.phoneNum = res.getString("EPHONE");
                empList.add(e);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return empList;
    }
}

class Department{
    int id;
    String name;
    String loc;
}

class Employee{
    int num;
    String name;
    String surname;
    String pos;
    String date;
    String dep;
    String email;
    String phoneNum;
}
