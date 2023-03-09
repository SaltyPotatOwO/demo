/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dbConnect.DBContext;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import model.User;



/**
 *
 * @author Inspiron
 */
public class UserDAO {

    public void InsertUser(User user) {
        try {
            DBContext db = new DBContext();
            Connection con = db.getConnection();
            if (con != null) {
                String sql = "{call insertUser(?,?,?,?,?,?,?)}";
                CallableStatement call = con.prepareCall(sql);
                call.setString(2, user.getPass());
                call.setString(3, user.getName());
                call.setString(4, user.getUname());
                if (user.isIsAdmin()) {
                    call.setInt(5, 1);
                } else {
                    call.setInt(5, 0);
                }
                call.setString(6, user.getGender());
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String strDate = df.format(user.getDob());
                call.setString(6, strDate);
                call.registerOutParameter(1, java.sql.Types.INTEGER);
                call.executeUpdate();
                System.out.println(call.getInt(1));
                call.close();
                con.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
//
//    public User Login(String username) {
//        User new_user = new User();
//        try {
//            DBContext db = new DBContext();
//            Connection con = db.getConnection();
//            if (con != null) {
//                String sql = "Select * from UserS where Username = " + "'"+;
//                Statement call = con.createStatement();
//                ResultSet rs = call.executeQuery(sql);
//                while (rs.next()) {             //needed even if just 1 row       
//                    boolean isAdmin = false;
//                    if (rs.getInt("id_Admin") == 1) {
//                        isAdmin = true;
//                    }
//                    new_user = new User(id, rs.getString("PASSWORD"), rs.getNString("User_name"), rs.getString("Username"), rs.getNString("Gender"), isAdmin, rs.getDate("dob"));
//                }
//                call.close();
//                con.close();
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return new_user;
//    }

    public boolean CheckDuplicate(String username) {
        try {
            DBContext db = new DBContext();
            Connection con = db.getConnection();
            if (con != null) {
                String sql = "{call checkDuplicate(?)}";
                CallableStatement call = con.prepareCall(sql);
                call.setNString(1, username);
                if (call.executeUpdate() > 0) {
                    throw new Exception();
                }
                call.close();
                con.close();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public User getUser(int id) {
        User new_user = new User();
        try {
            DBContext db = new DBContext();
            Connection con = db.getConnection();
            if (con != null) {
                String sql = "Select * from UserS where User_id =" + id;
                Statement call = con.createStatement();
                ResultSet rs = call.executeQuery(sql);
                while (rs.next()) {             //needed even if just 1 row       
                    boolean isAdmin = false;
                    if (rs.getInt("id_Admin") == 1) {
                        isAdmin = true;
                    }
                    new_user = new User(id, rs.getString("PASSWORD"), rs.getNString("User_name"), rs.getString("Username"), rs.getNString("Gender"), isAdmin, rs.getDate("dob"));
                }
                call.close();
                con.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new_user;
    }

    public HashMap<Integer, User> getAllUser() {
        HashMap<Integer, User> list = new HashMap<>();
        try {
            DBContext db = new DBContext();
            Connection con = db.getConnection();
            if (con != null) {
                String sql = "Select * from UserS";
                Statement call = con.createStatement();
                ResultSet rs = call.executeQuery(sql);
                while (rs.next()) {             //needed even if just 1 row       
                    boolean isAdmin = false;
                    if (rs.getInt("id_Admin") == 1) {
                        isAdmin = true;
                    }
                    User new_user = new User(rs.getInt("User_id"), rs.getString("PASSWORD"), rs.getNString("User_name"), rs.getString("Username"), rs.getNString("Gender"), isAdmin, rs.getDate("dob"));
                    list.put(new_user.getId(), new_user);
                }
                call.close();
                con.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public boolean delUser(int id) {
        try {
            DBContext db = new DBContext();
            Connection con = db.getConnection();
            if (con != null) {
                String sql = "{call delUser(?)}";
                CallableStatement call = con.prepareCall(sql);
                call.setInt(1, id);
                if (call.executeUpdate() <= 0) {
                    throw new Exception();
                }
                call.close();
                con.close();
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    //trong servlet, thg nao de la null thi set thuoc tinh doi tg gui vao nhu cu
    public boolean updateUser(User user) {//nut bam
        try {
            DBContext db = new DBContext();
            Connection con = db.getConnection();
            if (con != null) {
                String sql = "{call updateUser(?,?,?,?,?,?)}";
                CallableStatement call = con.prepareCall(sql);
                call.setInt(1, user.getId());
                call.setString(2, user.getPass());
                call.setNString(3, user.getName());
                call.setString(4, user.getUname());
                call.setNString(5, user.getGender());
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String strDate = df.format(user.getDob());
                call.setString(6, strDate);
                if (call.executeUpdate() <= 0) {
                    throw new Exception();
                }
                call.close();
                con.close();
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
//    public ArrayList<News> GetAllAdminNews(int admin_id) {
//        ArrayList<News> list = new ArrayList<>();
//        try {
//            DBContext db = new DBContext();
//            Connection con = db.getConnection();
//            if (con != null) {
//                String sql = "SELECT * INTO Admintab FROM dbo.UserS WHERE id_Admin = 1\n"
//                        + "SELECT * FROM dbo.Admintab a, dbo.News n\n"
//                        + "WHERE a.User_id = n.User_id AND a.User_id =" +admin_id;
//                Statement call = con.createStatement();
//                ResultSet rs = call.executeQuery(sql);
//                while (rs.next()) {             //needed even if just 1 row       
//                    boolean isAdmin = false;
//                    if (rs.getInt("id_Admin") == 1) {
//                        isAdmin = true;
//                    }
//                    User new_user = new User(rs.getInt("User_id"), rs.getString("PASSWORD"), rs.getNString("User_name"), rs.getString("Username"), rs.getNString("Gender"), isAdmin, rs.getDate("dob"));
//                    list.add(new_user);
//                }
//                call.close();
//                con.close();
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return list;
//    }

}
