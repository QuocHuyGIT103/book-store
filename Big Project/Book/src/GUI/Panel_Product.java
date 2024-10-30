/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;


import dao.NhaCungCap_DAO;
import dao.SanPham_DAO;
import entity.KhuyenMai;
import entity.NhaCungCap;
import entity.SanPham;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Asus
 */
public class Panel_Product extends javax.swing.JPanel {
    private SanPham_DAO sp_dao = new SanPham_DAO();
    private NhaCungCap_DAO ncc_dao = new NhaCungCap_DAO();
    private ArrayList<SanPham> dsSP = sp_dao.getDSSP();
    /**
     * Creates new form Panel_Product
     */
    public Panel_Product() {
        initComponents();
        DocLieuLenTableSanPham();
        DocDuLieuLenCBoBoxNCC();

    }
    
    public void DocDuLieuLenCBoBoxNCC() {
        ArrayList<NhaCungCap> dsNCC = ncc_dao.getAllNCC();
        
        for(NhaCungCap ncc : dsNCC) {
            cbo_NCC_field.addItem(ncc);
            cbo_LocTheoNCC.addItem(ncc.getTenNCC());
        }
    }
    
    public void LocTheoLSP() {
        int lsp_selected = cbo_LocTheoLoai.getSelectedIndex();
        ArrayList<SanPham> locTheoLSP = new ArrayList<>();
        
         for(SanPham sp : dsSP) {
           if(Integer.parseInt(sp.getLoaiSP().charAt(sp.getLoaiSP().length() - 1) + "") == lsp_selected) {
               locTheoLSP.add(sp);
           }
        }
         
         XoaDuLieuTableSP();
         DefaultTableModel temp = (DefaultTableModel) tbl_sanPham.getModel();
        for(SanPham sp : locTheoLSP){
            Object[] obj = {sp.getMaSP(), sp.getTenSP(), sp.getLoaiSP(), sp.getGiaNhapHang(), sp.getGiaBan(), sp.getSoLuongTonKho(), sp.getnCC().getMaNCC(), sp.getkM().getMaKM(), sp.getSoLuongBayBan()};
            temp.addRow(obj);
        }      
    }
    
    public void LocTheoNCC() {
        int ncc_selected = cbo_LocTheoNCC.getSelectedIndex();
        ArrayList<SanPham> locTheoNCC = new ArrayList<>();
        
        for(SanPham sp : dsSP) {
           if(Integer.parseInt(sp.getnCC().getMaNCC().charAt(sp.getnCC().getMaNCC().length() - 1) + "") == ncc_selected) {
               locTheoNCC.add(sp);
           }
        }
        
        
        
        XoaDuLieuTableSP();
        
        if(locTheoNCC.size() != 0) {
            DefaultTableModel temp = (DefaultTableModel) tbl_sanPham.getModel();
            for(SanPham sp : locTheoNCC){
                Object[] obj = {sp.getMaSP(), sp.getTenSP(), sp.getLoaiSP(), sp.getGiaNhapHang(), sp.getGiaBan(), sp.getSoLuongTonKho(), sp.getnCC().getMaNCC(), sp.getkM().getMaKM(), sp.getSoLuongBayBan()};
                temp.addRow(obj);
            }       
        } else {
            System.out.println("GUI.Panel_Product.LocTheoNCC()");
        }
    }
    
    public void DocDuLieuLenCBoBoxLoaiSP() {
        ArrayList<SanPham> dsSP = sp_dao.getDSSP();
        
        for(SanPham sp : dsSP) {
            cbo_loaiSP_field.addItem(sp.getLoaiSP());
        }
    }
    
    public boolean validData_SanPham(){
        String maSP = maSP_field.getText().toString().trim();
        String tenSP = tenSP_field.getText().toString().trim();
        String giaNhap = giaNhap_field.getText().toString().trim();
        String giaBan = giaBan_field.getText().toString().trim();
        String tonKho = tonKho_field.getText().toString().trim();
        String khuyenMai = khuyenMai_field.getText().toString().trim();
        String bayBan = bayban_field.getText().toString().trim();
        
        if(maSP.isEmpty() || (!maSP.matches("^SP\\d{3}$"))){
            JOptionPane.showMessageDialog(this, "Mã sản phẩm phải theo mẫu SP001");
            return false;
        }
        if(tenSP.isEmpty()){
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được rỗng!");
            return false;
        }
        double giaNhapHang = Double.parseDouble(giaNhap);
        if(giaNhapHang <= 0 || giaNhap.isEmpty()){
            JOptionPane.showMessageDialog(this, "Gía nhập hàng phải lớn hơn 0");
            return false;
        }
        double giaBanHang = Double.parseDouble(giaBan);
        if(giaBanHang <= 0  || giaBan.isEmpty()){
            JOptionPane.showMessageDialog(this, "Gía bán hàng phải lớn hơn 0");
            return false;
        }
        int slBayBan = bayBan.length() == 0 ? 0 : Integer.parseInt(bayBan);
        if(slBayBan <= 0){
            JOptionPane.showMessageDialog(this, "Số lượng bày bán phải lớn hơn 0");
            return false;
        }
        int slTonKho = Integer.parseInt(tonKho);
        if(slTonKho <= 0){
            JOptionPane.showMessageDialog(this, "Số lượng tồn kho phải lớn hơn 0");
            return false;
        }
        return true;
    }
    public void DocLieuLenTableSanPham(){
        dsSP = sp_dao.getDSSP();
        DefaultTableModel temp = (DefaultTableModel) tbl_sanPham.getModel();
        
        for(SanPham sp : dsSP){
            Object[] obj = {sp.getMaSP(), sp.getTenSP(), sp.getLoaiSP(), sp.getGiaNhapHang(), sp.getGiaBan(), sp.getSoLuongTonKho(), sp.getnCC().getMaNCC(), sp.getkM().getMaKM(), sp.getSoLuongBayBan()};
            temp.addRow(obj);
        }         
    }
    
    public void XoaDuLieuTableSP(){
        DefaultTableModel temp = (DefaultTableModel) tbl_sanPham.getModel();
        temp.getDataVector().removeAllElements();
    }
    
    public SanPham createSP(){
        String maSP = maSP_field.getText();
        String tenSP = tenSP_field.getText();
//        String cbo_loaiSP = String.valueOf(cbo_loaiSP_field.getSelectedItem());
        String cbo_loaiSP = "LSP00" + (cbo_loaiSP_field.getSelectedIndex() + 1);
        double giaNhap = Double.parseDouble(giaNhap_field.getText());
        double giaBan = Double.parseDouble(giaBan_field.getText());
        int tonKho = Integer.parseInt(tonKho_field.getText());
//        String cbo_NCC = String.valueOf(cbo_NCC_field.getSelectedItem());
        String cbo_NCC = "NCC00" + (cbo_NCC_field.getSelectedIndex() + 1);
        String khuyenMai = khuyenMai_field.getText();
        int bayBan = Integer.parseInt(bayban_field.getText());
        
        SanPham sp = new SanPham(maSP, khuyenMai.equals("") ? null : new KhuyenMai(khuyenMai), 
                new NhaCungCap(cbo_NCC), tenSP, cbo_loaiSP,giaNhap, 
                giaBan, bayBan, tonKho, 0);
        return sp;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        pnl_sanPham = new javax.swing.JPanel();
        pnl_Top = new javax.swing.JPanel();
        pnl_info = new javax.swing.JPanel();
        pnl_info1 = new javax.swing.JPanel();
        pnl_maSP = new javax.swing.JPanel();
        lbl_masp = new javax.swing.JLabel();
        maSP_field = new javax.swing.JTextField();
        pnl_NCC = new javax.swing.JPanel();
        lbl_NCC = new javax.swing.JLabel();
        cbo_NCC_field = new javax.swing.JComboBox<>();
        pnl_TonKho = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tonKho_field = new javax.swing.JTextField();
        pnl_info2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tenSP_field = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        giaNhap_field = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        khuyenMai_field = new javax.swing.JTextField();
        pnl_info3 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        lbl_loai = new javax.swing.JLabel();
        cbo_loaiSP_field = new javax.swing.JComboBox<>();
        jPanel15 = new javax.swing.JPanel();
        lbl_giaBan = new javax.swing.JLabel();
        giaBan_field = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        lbl_Bayban = new javax.swing.JLabel();
        bayban_field = new javax.swing.JTextField();
        pnl_btn = new javax.swing.JPanel();
        pnl_btn_them_xoa_sua = new javax.swing.JPanel();
        btn_themMoi = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btn_xoaTrang = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btn_capNhat = new javax.swing.JButton();
        pnl_Tim = new javax.swing.JPanel();
        maSP_txt = new javax.swing.JTextField();
        btn_timKiem = new javax.swing.JButton();
        pnl_Center = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        pn_LocTheNCC = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lbl_LocTheoNCC = new javax.swing.JLabel();
        cbo_LocTheoNCC = new javax.swing.JComboBox<>();
        pnl_LocTheoLoai = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lbl_LocTheoLoai = new javax.swing.JLabel();
        cbo_LocTheoLoai = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_sanPham = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        pnl_sanPham.setLayout(new java.awt.BorderLayout());

        pnl_Top.setPreferredSize(new java.awt.Dimension(0, 280));
        pnl_Top.setLayout(new java.awt.BorderLayout());

        pnl_info.setBackground(new java.awt.Color(255, 255, 255));
        pnl_info.setForeground(new java.awt.Color(30, 30, 30));
        pnl_info.setPreferredSize(new java.awt.Dimension(0, 180));
        pnl_info.setLayout(new java.awt.GridLayout(1, 1));

        pnl_info1.setLayout(new java.awt.GridLayout(3, 1));

        pnl_maSP.setPreferredSize(new java.awt.Dimension(370, 45));

        lbl_masp.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_masp.setText("Mã SP: ");
        lbl_masp.setPreferredSize(new java.awt.Dimension(75, 25));
        pnl_maSP.add(lbl_masp);

        maSP_field.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        maSP_field.setEnabled(false);
        maSP_field.setPreferredSize(new java.awt.Dimension(250, 40));
        maSP_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maSP_fieldActionPerformed(evt);
            }
        });
        pnl_maSP.add(maSP_field);

        pnl_info1.add(pnl_maSP);

        pnl_NCC.setPreferredSize(new java.awt.Dimension(370, 45));

        lbl_NCC.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_NCC.setText("NCC:");
        lbl_NCC.setPreferredSize(new java.awt.Dimension(75, 25));
        pnl_NCC.add(lbl_NCC);

        cbo_NCC_field.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cbo_NCC_field.setPreferredSize(new java.awt.Dimension(250, 40));
        cbo_NCC_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_NCC_fieldActionPerformed(evt);
            }
        });
        pnl_NCC.add(cbo_NCC_field);

        pnl_info1.add(pnl_NCC);

        pnl_TonKho.setPreferredSize(new java.awt.Dimension(370, 45));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Tồn kho:");
        pnl_TonKho.add(jLabel1);

        tonKho_field.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        tonKho_field.setPreferredSize(new java.awt.Dimension(250, 40));
        tonKho_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tonKho_fieldActionPerformed(evt);
            }
        });
        pnl_TonKho.add(tonKho_field);

        pnl_info1.add(pnl_TonKho);

        pnl_info.add(pnl_info1);

        pnl_info2.setLayout(new java.awt.GridLayout(3, 1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Tên SP:");
        jLabel3.setPreferredSize(new java.awt.Dimension(106, 25));
        jPanel8.add(jLabel3);

        tenSP_field.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        tenSP_field.setPreferredSize(new java.awt.Dimension(250, 40));
        tenSP_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenSP_fieldActionPerformed(evt);
            }
        });
        jPanel8.add(tenSP_field);

        pnl_info2.add(jPanel8);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Giá nhập:");
        jLabel4.setPreferredSize(new java.awt.Dimension(106, 25));
        jPanel10.add(jLabel4);

        giaNhap_field.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        giaNhap_field.setPreferredSize(new java.awt.Dimension(250, 40));
        giaNhap_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giaNhap_fieldActionPerformed(evt);
            }
        });
        jPanel10.add(giaNhap_field);

        pnl_info2.add(jPanel10);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Khuyến mãi:");
        jPanel13.add(jLabel5);

        khuyenMai_field.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        khuyenMai_field.setPreferredSize(new java.awt.Dimension(250, 40));
        jPanel13.add(khuyenMai_field);

        pnl_info2.add(jPanel13);

        pnl_info.add(pnl_info2);

        pnl_info3.setPreferredSize(new java.awt.Dimension(350, 150));
        pnl_info3.setLayout(new java.awt.GridLayout(3, 1));

        lbl_loai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_loai.setText("Loại:");
        lbl_loai.setPreferredSize(new java.awt.Dimension(74, 25));
        jPanel14.add(lbl_loai);

        cbo_loaiSP_field.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cbo_loaiSP_field.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SGK", "Truyện", "Tiểu thuyết", "Văn phòng phẩm", "Dụng cụ học tập" }));
        cbo_loaiSP_field.setPreferredSize(new java.awt.Dimension(250, 40));
        jPanel14.add(cbo_loaiSP_field);

        pnl_info3.add(jPanel14);

        lbl_giaBan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_giaBan.setText("Giá bán:");
        lbl_giaBan.setPreferredSize(new java.awt.Dimension(74, 25));
        jPanel15.add(lbl_giaBan);

        giaBan_field.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        giaBan_field.setPreferredSize(new java.awt.Dimension(250, 40));
        giaBan_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giaBan_fieldActionPerformed(evt);
            }
        });
        jPanel15.add(giaBan_field);

        pnl_info3.add(jPanel15);

        lbl_Bayban.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_Bayban.setText("Bày bán:");
        jPanel16.add(lbl_Bayban);

        bayban_field.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        bayban_field.setPreferredSize(new java.awt.Dimension(250, 40));
        bayban_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayban_fieldActionPerformed(evt);
            }
        });
        jPanel16.add(bayban_field);

        pnl_info3.add(jPanel16);

        pnl_info.add(pnl_info3);

        pnl_Top.add(pnl_info, java.awt.BorderLayout.NORTH);

        pnl_btn.setPreferredSize(new java.awt.Dimension(1112, 50));
        pnl_btn.setLayout(new java.awt.GridLayout(1, 2));

        pnl_btn_them_xoa_sua.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));
        pnl_btn_them_xoa_sua.setPreferredSize(new java.awt.Dimension(1020, 50));

        btn_themMoi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_themMoi.setText("Thêm mới");
        btn_themMoi.setPreferredSize(new java.awt.Dimension(120, 50));
        btn_themMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_themMoiMouseClicked(evt);
            }
        });
        btn_themMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themMoiActionPerformed(evt);
            }
        });
        pnl_btn_them_xoa_sua.add(btn_themMoi);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        pnl_btn_them_xoa_sua.add(jPanel2);

        btn_xoaTrang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_xoaTrang.setText("Xóa trắng");
        btn_xoaTrang.setPreferredSize(new java.awt.Dimension(120, 50));
        btn_xoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaTrangActionPerformed(evt);
            }
        });
        pnl_btn_them_xoa_sua.add(btn_xoaTrang);

        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        pnl_btn_them_xoa_sua.add(jPanel7);

        btn_capNhat.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_capNhat.setText("Cập nhật");
        btn_capNhat.setPreferredSize(new java.awt.Dimension(120, 50));
        btn_capNhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_capNhatMouseClicked(evt);
            }
        });
        btn_capNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_capNhatActionPerformed(evt);
            }
        });
        pnl_btn_them_xoa_sua.add(btn_capNhat);

        pnl_btn.add(pnl_btn_them_xoa_sua);

        pnl_Tim.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));
        pnl_Tim.setLayout(new java.awt.BorderLayout());

        maSP_txt.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        maSP_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maSP_txtActionPerformed(evt);
            }
        });
        pnl_Tim.add(maSP_txt, java.awt.BorderLayout.CENTER);

        btn_timKiem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_timKiem.setText("Tìm kiếm");
        btn_timKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_timKiemMouseClicked(evt);
            }
        });
        btn_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timKiemActionPerformed(evt);
            }
        });
        pnl_Tim.add(btn_timKiem, java.awt.BorderLayout.EAST);

        pnl_btn.add(pnl_Tim);

        pnl_Top.add(pnl_btn, java.awt.BorderLayout.CENTER);

        pnl_sanPham.add(pnl_Top, java.awt.BorderLayout.NORTH);

        pnl_Center.setLayout(new java.awt.BorderLayout());
        pnl_sanPham.add(pnl_Center, java.awt.BorderLayout.CENTER);

        add(pnl_sanPham, java.awt.BorderLayout.PAGE_START);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel9.setPreferredSize(new java.awt.Dimension(1000, 200));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc"));
        jPanel11.setPreferredSize(new java.awt.Dimension(940, 150));
        jPanel11.setLayout(new java.awt.GridLayout(1, 3));

        pn_LocTheNCC.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 30));
        pn_LocTheNCC.setLayout(new java.awt.BorderLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel5.setLayout(new java.awt.GridLayout(2, 1));

        lbl_LocTheoNCC.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_LocTheoNCC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_LocTheoNCC.setText("Lọc Theo NCC");
        jPanel5.add(lbl_LocTheoNCC);

        cbo_LocTheoNCC.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cbo_LocTheoNCC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Chọn NCC --" }));
        cbo_LocTheoNCC.setPreferredSize(new java.awt.Dimension(150, 35));
        cbo_LocTheoNCC.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbo_LocTheoNCCItemStateChanged(evt);
            }
        });
        cbo_LocTheoNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_LocTheoNCCActionPerformed(evt);
            }
        });
        jPanel5.add(cbo_LocTheoNCC);

        pn_LocTheNCC.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel11.add(pn_LocTheNCC);

        pnl_LocTheoLoai.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 30));
        pnl_LocTheoLoai.setLayout(new java.awt.BorderLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel6.setLayout(new java.awt.GridLayout(2, 1));

        lbl_LocTheoLoai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_LocTheoLoai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_LocTheoLoai.setText("Lọc theo Loại");
        jPanel6.add(lbl_LocTheoLoai);

        cbo_LocTheoLoai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cbo_LocTheoLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Chọn loại sản phẩm --", "SGK", "Truyện", "Tiểu thuyết", "Văn phòng phẩm", "Dụng cụ học tập" }));
        cbo_LocTheoLoai.setPreferredSize(new java.awt.Dimension(150, 35));
        cbo_LocTheoLoai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbo_LocTheoLoaiItemStateChanged(evt);
            }
        });
        jPanel6.add(cbo_LocTheoLoai);

        pnl_LocTheoLoai.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel11.add(pnl_LocTheoLoai);

        jPanel9.add(jPanel11, java.awt.BorderLayout.NORTH);

        jPanel12.setPreferredSize(new java.awt.Dimension(940, 200));
        jPanel12.setLayout(new java.awt.BorderLayout());

        tbl_sanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tbl_sanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "Loại SP", "Giá Nhập", "Giá Bán", "Tồn kho", "Nhà Cung Cấp", "Khuyến Mãi", "Bày bán"
            }
        ));
        tbl_sanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_sanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_sanPham);

        jPanel12.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel9, java.awt.BorderLayout.CENTER);

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void maSP_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maSP_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maSP_fieldActionPerformed

    private void cbo_NCC_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_NCC_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_NCC_fieldActionPerformed

    private void giaNhap_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giaNhap_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_giaNhap_fieldActionPerformed

    private void tenSP_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenSP_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tenSP_fieldActionPerformed

    private void btn_capNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_capNhatActionPerformed
        // TODO add your handling code here:    
    }//GEN-LAST:event_btn_capNhatActionPerformed

    private void btn_timKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_timKiemActionPerformed

    private void tonKho_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tonKho_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tonKho_fieldActionPerformed

    private void maSP_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maSP_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maSP_txtActionPerformed

    private void btn_themMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themMoiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_themMoiActionPerformed

    private void btn_xoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaTrangActionPerformed
        // TODO add your handling code here:
        maSP_field.setText("");
        tenSP_field.setText("");
        giaNhap_field.setText("");
        giaBan_field.setText("");
        tonKho_field.setText("");
        khuyenMai_field.setText("");
        bayban_field.setText("");
    }//GEN-LAST:event_btn_xoaTrangActionPerformed

    private void bayban_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayban_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bayban_fieldActionPerformed

    private void giaBan_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giaBan_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_giaBan_fieldActionPerformed

    private void tbl_sanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_sanPhamMouseClicked
        // TODO add your handling code here:
        int r = tbl_sanPham.getSelectedRow();
        
        if(r >= 0){
            maSP_field.setText(tbl_sanPham.getValueAt(r, 0).toString());
            tenSP_field.setText(tbl_sanPham.getValueAt(r, 1).toString());
            giaNhap_field.setText(tbl_sanPham.getValueAt(r, 3).toString());
            giaBan_field.setText(tbl_sanPham.getValueAt(r, 4).toString());
            tonKho_field.setText(tbl_sanPham.getValueAt(r, 5).toString());
            khuyenMai_field.setText(tbl_sanPham.getValueAt(r, 7) == null 
                    ? "" : tbl_sanPham.getValueAt(r, 7).toString());
            bayban_field.setText(tbl_sanPham.getValueAt(r, 8).toString());
            cbo_NCC_field.setSelectedIndex(
                    Integer.parseInt(tbl_sanPham.getValueAt(r, 6).toString().substring(3, 6)) - 1);
            cbo_loaiSP_field.setSelectedIndex(
                    Integer.parseInt(tbl_sanPham.getValueAt(r, 2).toString().substring(3, 6)) - 1);
        }else{
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng muốn thao tác!");
        }
    }//GEN-LAST:event_tbl_sanPhamMouseClicked

    private void btn_themMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_themMoiMouseClicked
        // TODO add your handling code here:
        if(validData_SanPham()){
            SanPham sp = createSP();
            ArrayList<SanPham> dsSP = sp_dao.getDSSP();
            
            if(true){
                if(sp_dao.themSanPham(sp)){
                    XoaDuLieuTableSP();
                    DocLieuLenTableSanPham();
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                }else{
                    JOptionPane.showMessageDialog(this, "Thêm thất bại! Có lỗi xảy ra");
                }
            }else{
                JOptionPane.showMessageDialog(this, "Sản phẩm đã tồn tại! Vui lòng kiểm tra lại");
            }
        }
    }//GEN-LAST:event_btn_themMoiMouseClicked

    private void btn_timKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_timKiemMouseClicked
        // TODO add your handling code here:
        String maSP_searched = maSP_txt.getText().toString();
        
        if(!maSP_searched.trim().equals("")){
            XoaDuLieuTableSP();
            
            SanPham sp = sp_dao.getSP_TheoMa(maSP_searched);
            DefaultTableModel temp = (DefaultTableModel) tbl_sanPham.getModel();
            
            Object[] obj = {sp.getMaSP(), sp.getTenSP(), sp.getLoaiSP(), sp.getGiaNhapHang(), sp.getGiaBan(), sp.getSoLuongTonKho(), sp.getnCC().getMaNCC(), sp.getkM().getMaKM(), sp.getSoLuongBayBan()};
            temp.addRow(obj);
        }else{
            XoaDuLieuTableSP();
            DocLieuLenTableSanPham();
        }
            
    }//GEN-LAST:event_btn_timKiemMouseClicked

    private void btn_capNhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_capNhatMouseClicked
        // TODO add your handling code here:
        
        
        int r = tbl_sanPham.getSelectedRow();
        if(r < 0){
            JOptionPane.showMessageDialog(this, "Cần chọn sản phẩm cần cập nhật!");
        }
        
        if(validData_SanPham()){
            String maSP = maSP_field.getText();
            String tenSP = tenSP_field.getText();
            String cbo_loaiSP = "LSP00" + (cbo_loaiSP_field.getSelectedIndex() + 1);
            double giaNhap = Double.parseDouble(giaNhap_field.getText());
            double giaBan = Double.parseDouble(giaBan_field.getText());
            int tonKho = Integer.parseInt(tonKho_field.getText());
            String cbo_NCC = "NCC00" + (cbo_NCC_field.getSelectedIndex() + 1);
            String khuyenMai = khuyenMai_field.getText();
            int bayBan = Integer.parseInt(bayban_field.getText());

            SanPham sp = new SanPham(maSP, new KhuyenMai(khuyenMai), new NhaCungCap(cbo_NCC), tenSP, 
                                    cbo_loaiSP,giaNhap, giaBan, bayBan, tonKho, 0);
            
            if(sp_dao.updateSP(sp)){
                DefaultTableModel temp = (DefaultTableModel) tbl_sanPham.getModel();
                temp.removeRow(r);
                Object[] obj = {sp.getMaSP(), sp.getTenSP(), sp.getLoaiSP(), sp.getGiaNhapHang(), sp.getGiaBan(), sp.getSoLuongTonKho(), sp.getnCC().getMaNCC(), sp.getkM().getMaKM(), sp.getSoLuongBayBan()};
                temp.insertRow(r, obj);
                JOptionPane.showMessageDialog(this, "Cập nhật thành công sản phẩm có mã" + maSP);
            }else{
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!!Có lỗi xảy ra!!");
            }
        }
    }//GEN-LAST:event_btn_capNhatMouseClicked

    private void cbo_LocTheoNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_LocTheoNCCActionPerformed
        // TODO add your handling code here:
//        LocTheoNCC();
//System.out.println("GUI.Panel_Product.cbo_LocTheoNCCActionPerformed()");
    }//GEN-LAST:event_cbo_LocTheoNCCActionPerformed

    private void cbo_LocTheoNCCItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbo_LocTheoNCCItemStateChanged
        // TODO add your handling code here:
        if(cbo_LocTheoNCC.getSelectedIndex() != 0) {
            LocTheoNCC();
        }
    }//GEN-LAST:event_cbo_LocTheoNCCItemStateChanged

    private void cbo_LocTheoLoaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbo_LocTheoLoaiItemStateChanged
        // TODO add your handling code here:
        LocTheoLSP();
    }//GEN-LAST:event_cbo_LocTheoLoaiItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bayban_field;
    private javax.swing.JButton btn_capNhat;
    private javax.swing.JButton btn_themMoi;
    private javax.swing.JButton btn_timKiem;
    private javax.swing.JButton btn_xoaTrang;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbo_LocTheoLoai;
    private javax.swing.JComboBox<String> cbo_LocTheoNCC;
    private javax.swing.JComboBox<NhaCungCap> cbo_NCC_field;
    private javax.swing.JComboBox<String> cbo_loaiSP_field;
    private javax.swing.JTextField giaBan_field;
    private javax.swing.JTextField giaNhap_field;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField khuyenMai_field;
    private javax.swing.JLabel lbl_Bayban;
    private javax.swing.JLabel lbl_LocTheoLoai;
    private javax.swing.JLabel lbl_LocTheoNCC;
    private javax.swing.JLabel lbl_NCC;
    private javax.swing.JLabel lbl_giaBan;
    private javax.swing.JLabel lbl_loai;
    private javax.swing.JLabel lbl_masp;
    private javax.swing.JTextField maSP_field;
    private javax.swing.JTextField maSP_txt;
    private javax.swing.JPanel pn_LocTheNCC;
    private javax.swing.JPanel pnl_Center;
    private javax.swing.JPanel pnl_LocTheoLoai;
    private javax.swing.JPanel pnl_NCC;
    private javax.swing.JPanel pnl_Tim;
    private javax.swing.JPanel pnl_TonKho;
    private javax.swing.JPanel pnl_Top;
    private javax.swing.JPanel pnl_btn;
    private javax.swing.JPanel pnl_btn_them_xoa_sua;
    private javax.swing.JPanel pnl_info;
    private javax.swing.JPanel pnl_info1;
    private javax.swing.JPanel pnl_info2;
    private javax.swing.JPanel pnl_info3;
    private javax.swing.JPanel pnl_maSP;
    private javax.swing.JPanel pnl_sanPham;
    private javax.swing.JTable tbl_sanPham;
    private javax.swing.JTextField tenSP_field;
    private javax.swing.JTextField tonKho_field;
    // End of variables declaration//GEN-END:variables
}
