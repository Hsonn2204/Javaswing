/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package detaithltm;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ADMIN
 */
public class ktra extends javax.swing.JFrame {
     private long timebd; // Lưu thời điểm bắt đầu làm bài
    private int socaumax = -1; // Chỉ số câu hỏi xa nhất người dùng đã làm
    private TongCau tongcau; // Ngân hàng câu hỏi
    private int socauhientai = 0; // Chỉ số câu hỏi hiện tại
    private int caudung = 0; // Số câu trả lời đúng
    private int caudalam = 0; // Số câu đã trả lời
    private List<String> dsdapannguoidung = new ArrayList<>(); // Danh sách đáp án của người dùng
    private Timer demtg; // Bộ đếm thời gian
    private int tgconlai = 1000; // Thời gian làm bài (giây)
    private ButtonGroup chon; // Nhóm các nút radio (để chỉ chọn được 1 đáp án)
    /**

     */
    public ktra() {
        initComponents();
        tongcau = new TongCau(); // Tạo ngân hàng câu hỏi

        // Khởi tạo ButtonGroup và thêm các nút radio vào nhóm
        chon = new ButtonGroup();
        chon.add(radA);
        chon.add(radB);
        chon.add(radC);
        chon.add(radD);

        taiCauHoi(); // Tải câu hỏi đầu tiên lên form

        // Khởi tạo bộ đếm thời gian
        demtg = new Timer(1000, new ActionListener() // Timer chạy mỗi 1 giây (1000 milliseconds)
        { 
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (tgconlai > 0)
                {
                    tgconlai--; // Giảm thời gian còn lại
                    txttime.setText("Thời gian: " + tgconlai); // Cập nhật label thời gian
                } 
                else 
                {
                    
                    demtg.stop(); // Dừng bộ đếm khi hết giờ
                    nopBai(); // Tự động nộp bài khi hết giờ
                }
            }
        });
        timebd = System.currentTimeMillis(); // Lưu thời điểm bắt đầu làm bài
        demtg.start(); // Bắt đầu đếm thời gian
    }

    private ktra(String nguoiDung, int caudung, List<CauHoi> cauHoi, List<String> dsdapannguoidung, int timelam) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private void taiCauHoi() 
    {
        if (socauhientai < tongcau.getCauHoi().size()) // Kiểm tra còn câu hỏi hay không
        { 
            CauHoi cauHoi = tongcau.getCauHoi().get(socauhientai); // Lấy câu hỏi hiện tại
            txtCauHoi.setText( "Question "+(socauhientai + 1)  + ": " + cauHoi.getDecauhoi() ); // Hiển thị câu hỏi
            radA.setText(cauHoi.getPhuongAnA()); // Hiển thị phương án A
            radB.setText(cauHoi.getPhuongAnB()); // Hiển thị phương án B
            radC.setText(cauHoi.getPhuongAnC()); // Hiển thị phương án C
            radD.setText(cauHoi.getPhuongAnD()); // Hiển thị phương án D
            txtsocau.setText("Câu đã làm : " + caudalam + "/" + tongcau.size()); // Cập nhật số câu đã làm

            // Khôi phục lựa chọn của người dùng nếu đã làm câu này
            if (dsdapannguoidung.size() > socauhientai) 
            { 
                String dapan = dsdapannguoidung.get(socauhientai);
                switch (dapan) // Chọn lại đáp án đã chọn trước đó (nếu có)
                { 
                    case "A": radA.setSelected(true); break;
                    case "B": radB.setSelected(true); break;
                    case "C": radC.setSelected(true); break;
                    case "D": radD.setSelected(true); break;
                }
            }
            else 
            {
                chon.clearSelection(); // Nếu chưa làm thì bỏ chọn tất cả các phương án
            }
            // Cập nhật thanh tiến trình
            jProgressBar1.setValue((socauhientai + 1) * 100 / tongcau.size()); 
        } 
    }

   // Hàm kiểm tra đáp án của người dùng
    private void checkDapAn(String chondapan) 
    {
        if (dsdapannguoidung.size() <= socauhientai) 
        {
            dsdapannguoidung.add(chondapan); // Thêm đáp án vào danh sách
            caudalam++; // Tăng số câu đã làm
        } 
        else 
        {
            dsdapannguoidung.set(socauhientai, chondapan); // Cập nhật đáp án trong danh sách
        }

        // Kiểm tra xem đây có phải là lần đầu tiên trả lời câu hỏi này hay không
        boolean traloilandau = dsdapannguoidung.size() == socauhientai + 1;

        if (traloilandau && chondapan.equals(tongcau.cauhoi.get(socauhientai).getDapandung())) 
        {
            caudung++; // Tăng số câu đúng nếu lần đầu trả lời đúng
        } 
        else if (!traloilandau && !chondapan.equals(tongcau.cauhoi.get(socauhientai).getDapandung())) 
        {
            // Nếu đã làm câu này rồi và trả lời sai, giảm số câu đúng
            caudung--;
        }
    }
    // Hàm xử lý khi nộp bài
    private void nopBai() 
    {
        long tgketthuc = System.currentTimeMillis(); // Lấy thời điểm kết thúc
        int timelam = (int) ((tgketthuc - timebd) / 1000); // Tính thời gian đã làm
        demtg.stop(); // Dừng bộ đếm thời gian
        ketqua ketqua1 = new ketqua(nguoiDung, caudung, tongcau.getCauHoi(), dsdapannguoidung, timelam); // Tạo form kết quả
        ketqua1.setVisible(true); // Hiển thị form kết quả
        this.dispose(); // Đóng form kiểm tra hiện tại
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        radB = new javax.swing.JRadioButton();
        txthoTen = new javax.swing.JLabel();
        radD = new javax.swing.JRadioButton();
        txtsocau = new javax.swing.JLabel();
        txttime = new javax.swing.JLabel();
        btnnop = new javax.swing.JButton();
        btnsau = new javax.swing.JButton();
        btntruoc = new javax.swing.JButton();
        txtImage = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        radA = new javax.swing.JRadioButton();
        txtCauHoi = new javax.swing.JLabel();
        radC = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Bắt Đầu Kiểm Tra Tiếng Anh");

        radB.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        radB.setText("B");
        radB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radBActionPerformed(evt);
            }
        });

        txthoTen.setBackground(new java.awt.Color(255, 255, 255));
        txthoTen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txthoTen.setText("Họ Tên :");

        radD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        radD.setText("D");
        radD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radDActionPerformed(evt);
            }
        });

        txtsocau.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtsocau.setText("Câu : ");

        txttime.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txttime.setText("Thời gian : ");

        btnnop.setBackground(new java.awt.Color(255, 204, 204));
        btnnop.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnnop.setText("Nộp Bài");
        btnnop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnnopMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnnopMouseExited(evt);
            }
        });
        btnnop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnopActionPerformed(evt);
            }
        });

        btnsau.setBackground(new java.awt.Color(255, 204, 204));
        btnsau.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnsau.setText("Câu Sau");
        btnsau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnsauMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnsauMouseExited(evt);
            }
        });
        btnsau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsauActionPerformed(evt);
            }
        });

        btntruoc.setBackground(new java.awt.Color(255, 204, 204));
        btntruoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btntruoc.setText("Câu Trước");
        btntruoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btntruocMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btntruocMouseExited(evt);
            }
        });
        btntruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntruocActionPerformed(evt);
            }
        });

        txtImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picture/z5495942183160_4b8f3d715132b74ce7df152aa11a7d9e.jpg"))); // NOI18N
        txtImage.setMaximumSize(new java.awt.Dimension(1010, 664));

        jProgressBar1.setBackground(new java.awt.Color(255, 204, 204));

        radA.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        radA.setText("A");
        radA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radAActionPerformed(evt);
            }
        });

        txtCauHoi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        radC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        radC.setText("C");
        radC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radB, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radC, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radA, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btntruoc)
                        .addGap(74, 74, 74)
                        .addComponent(btnnop, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(btnsau, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(radD, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtImage, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(177, 177, 177))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCauHoi, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txthoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtsocau, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(txttime, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(239, 239, 239)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(127, 127, 127))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txthoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtsocau, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txttime, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(txtCauHoi, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(radA, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radB, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radC, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(radD, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btntruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnnop, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnsau, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(txtImage, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(90, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1115, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void radBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radBActionPerformed
        checkDapAn("B");
    }//GEN-LAST:event_radBActionPerformed

    private void radDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radDActionPerformed
        checkDapAn("D");
    }//GEN-LAST:event_radDActionPerformed

    private void btnnopMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnnopMouseEntered
        btnnop.setBackground(new Color(135, 206, 250));
    }//GEN-LAST:event_btnnopMouseEntered

    private void btnnopMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnnopMouseExited
        btnnop.setBackground(UIManager.getColor("Button.background"));
    }//GEN-LAST:event_btnnopMouseExited

    private void btnnopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnopActionPerformed
        if (caudalam < tongcau.getCauHoi().size())
        { // Kiểm tra đã làm hết câu hỏi chưa
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn chưa hoàn thành hết các câu hỏi. Bạn có muốn nộp bài?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION)
            {
                // Điền các câu chưa làm bằng ""
                while (dsdapannguoidung.size() < tongcau.getCauHoi().size())
                {
                    dsdapannguoidung.add("");
                }
                nopBai();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Bạn đã hoàn thành bài kiểm tra!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            nopBai();
        }
    }//GEN-LAST:event_btnnopActionPerformed

    private void btnsauMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsauMouseEntered
        btnsau.setBackground(new Color(135, 206, 250));
    }//GEN-LAST:event_btnsauMouseEntered

    private void btnsauMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsauMouseExited
        btnsau.setBackground(UIManager.getColor("Button.background"));
    }//GEN-LAST:event_btnsauMouseExited

    private void btnsauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsauActionPerformed
        if (socauhientai < tongcau.getCauHoi().size() - 1)
        {
            socauhientai++;
            taiCauHoi();
        }
    }//GEN-LAST:event_btnsauActionPerformed

    private void btntruocMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btntruocMouseEntered
        btntruoc.setBackground(new Color(135, 206, 250));
    }//GEN-LAST:event_btntruocMouseEntered

    private void btntruocMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btntruocMouseExited
        btntruoc.setBackground(UIManager.getColor("Button.background"));
    }//GEN-LAST:event_btntruocMouseExited

    private void btntruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntruocActionPerformed
        if (socauhientai > 0)
        {
            socauhientai--;
            taiCauHoi();
        }
    }//GEN-LAST:event_btntruocActionPerformed

    private void radAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radAActionPerformed
        checkDapAn("A");
    }//GEN-LAST:event_radAActionPerformed

    private void radCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radCActionPerformed
        checkDapAn("C");
    }//GEN-LAST:event_radCActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ktra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ktra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ktra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ktra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ktra().setVisible(true);
            }
        });
    }
     private String nguoiDung;
    void setNguoiDung(String hoTen) 
    {
        this.nguoiDung = hoTen; // Sửa lại dòng này
        txthoTen.setText("Họ Tên : " + hoTen);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnnop;
    private javax.swing.JButton btnsau;
    private javax.swing.JButton btntruoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButton radA;
    private javax.swing.JRadioButton radB;
    private javax.swing.JRadioButton radC;
    private javax.swing.JRadioButton radD;
    private javax.swing.JLabel txtCauHoi;
    private javax.swing.JLabel txtImage;
    private javax.swing.JLabel txthoTen;
    private javax.swing.JLabel txtsocau;
    private javax.swing.JLabel txttime;
    // End of variables declaration//GEN-END:variables
}
