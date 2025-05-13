/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package detaithltm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 
 */
public class TongCau {
    // Sử dụng List<CauHoi> vì bạn đã định nghĩa lớp CauHoi
    List<CauHoi> cauhoi; 

    public TongCau() {
        cauhoi = new ArrayList<>(); // Khởi tạo danh sách câu hỏi
        loadQuestionsFromDatabase();
    }

    private void loadQuestionsFromDatabase() {
        try (Connection conn = KetNoi.getConnection(); // Đảm bảo sử dụng tên phương thức đúng
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM cau_hoi")) // Đảm bảo đúng tên bảng
        { 
            while (rs.next()) 
            {
                String decauhoi = rs.getString("cau_hoi");
                String[] tuychon = 
                {
                    rs.getString("phuong_an_A"),
                    rs.getString("phuong_an_B"),
                    rs.getString("phuong_an_C"),
                    rs.getString("phuong_an_D")
                };
                String dapandung = rs.getString("dap_an_dung"); // Sửa lại tên biến dap

                // Tạo đối tượng CauHoi và thêm vào danh sách
                CauHoi cau = new CauHoi(decauhoi, tuychon, dapandung); 
                cauhoi.add(cau); 
            }
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải câu hỏi từ cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public List<CauHoi> getCauHoi() {
        return cauhoi;
    }

    public int size() {
        return cauhoi.size(); 
    }
}
