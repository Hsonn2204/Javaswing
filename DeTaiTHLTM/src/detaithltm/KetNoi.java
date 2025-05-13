/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package detaithltm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *

 */
public class KetNoi 
{
    public static Connection getConnection() 
    {
        try 
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizz","root","");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Ket noi co so du lieu thanh cong!");
            return con;            
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(null, "Loi ket noi co so du lieu: " + e.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            
        } 
        catch (ClassNotFoundException e) 
        {
            JOptionPane.showMessageDialog(null, "Khong tim thay driver MySQL.", "Loi", JOptionPane.ERROR_MESSAGE);
            
        }       
        return null;
    }
}
