/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbConnect;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Asus
 */
public class TestConnection {

    public static void test() {
        try {
            DBContext db = new DBContext();
            Connection con = db.getConnection();
            if (con != null) {
                System.out.println("Connected");
            } else {
                System.out.println("Not Connected");
            }
        } catch (Exception e) {
            System.out.println("Disconected");
        }

    }
    public static void testCallableStatment(int id){
        try{
            DBContext db = new DBContext();
            Connection con = db.getConnection();
            String sql = "{call getInfo(?,?,?)}";
            CallableStatement st = con.prepareCall(sql);
            //truyen tham so
            st.setInt(1, id);
            //dau ra
            st.registerOutParameter(2, java.sql.Types.NVARCHAR);
            st.registerOutParameter(3, java.sql.Types.NVARCHAR);
            
            st.executeUpdate();
            String name = st.getString(2);
            int nbProduct = st.getInt(3);
        
            System.out.println("id = " + id + ", name = " + name
            + ", number of products = " + nbProduct);
            st.close();
            con.close();
        } catch (Exception e){
            
        }
    }
    public static void main(String[] args) {
        test();
    }
}
