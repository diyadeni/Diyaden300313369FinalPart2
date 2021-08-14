//Diyaden Ing 300313369 Final Exam Part2

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
 * Created by JFormDesigner on Fri Aug 13 16:08:11 PDT 2021
 */


/**
 * @author unknown
 */
public class FinalExam extends JFrame {

    Connection1 con = new Connection1();
    Connection conObj = con.connect();

    //showform
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        FinalExam form1 = new FinalExam();

        form1.updateTable();
        form1.setVisible(true);
    }

    //update table
    public void updateTable() throws SQLException {

        String quer1 = "Select * from savingstable";
        PreparedStatement query = conObj.prepareStatement(quer1);

        ResultSet rs = query.executeQuery();

        DefaultTableModel df = (DefaultTableModel) table1.getModel();


        rs.last();

        int z = rs.getRow();

        rs.beforeFirst();


        String [][] array = new String[0][];
        if(z > 0){
            array = new String[z][2];
        }

        int j =0;

        while(rs.next()){
            array[j][0]= rs.getString("custno");
            array[j][1] = rs.getString("custname");
            ++j;

        }


        String[] cols = {"Number", "Name", "Deposits", "Years", "Types of Savings"};

        DefaultTableModel model = new DefaultTableModel(array, cols);
        table1.setModel(model);


    }

    public FinalExam() throws SQLException, ClassNotFoundException {
        initComponents();
    }

    //AddButton Action Listener
    private void btnAddActionPerformed(ActionEvent e) throws SQLException {

        String custno, custname;


        custno = txtcustno.getText();
        custname = txtcustname.getText();


        String query1="Select * from category where custno =?";
        PreparedStatement query = conObj.prepareStatement(query1);

        query.setString(1,custno);

        ResultSet rs = query.executeQuery();

        if(rs.isBeforeFirst()){
            JOptionPane.showMessageDialog(null, "The record is existing");
            //set the textboxes to space
            return;
        }


        String query2 = "Insert into category values (?,?)";
        query= conObj.prepareStatement(query2);

        query.setString(1,custno);
        query.setString(2,custname);

        query.executeUpdate();


        JOptionPane.showMessageDialog(null, "Record added ");
        updateTable();

    }

    private void table1MouseClicked(MouseEvent e) {
        DefaultTableModel df = (DefaultTableModel) table1.getModel();

        int index = table1.getSelectedRow();

        txtcustno.setText(df.getValueAt(index,0).toString());
        txtcustname.setText(df.getValueAt(index,1).toString());

    }

    //Edit Button Action Listener
    private void btnEditActionPerformed(ActionEvent e) throws SQLException {
        DefaultTableModel df = (DefaultTableModel) table1.getModel();

        int index = table1.getSelectedRow();

        String custno, custname;


        custno = txtcustno.getText();
        custname = txtcustname.getText();

        String oldvalue = df.getValueAt(index,0).toString();

        String query = "Update category set custno=?,custname=? where custno =?";
        PreparedStatement query2 =  conObj.prepareStatement(query);

        query2.setString(1,custno);
        query2.setString(2,custname);
        query2.setString(3,oldvalue);

        query2.executeUpdate();

        updateTable();





    }

    //DeleteButtonActionListener
    private void btnDeleteActionPerformed(ActionEvent e) throws SQLException {

        String custno, custname;

        custno = txtcustno.getText();
        custname = txtcustname.getText();


        String query = "Delete from category where custno =?";
        PreparedStatement query2 =  conObj.prepareStatement(query);

        query2.setString(1,custno);


        query2.executeUpdate();

        updateTable();

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label1 = new JLabel();
        txtcustno = new JTextField();
        label2 = new JLabel();
        txtcustname = new JTextField();
        label3 = new JLabel();
        txtindeposit = new JTextField();
        label4 = new JLabel();
        txtyears = new JTextField();
        label5 = new JLabel();
        comboBox1 = new JComboBox();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        btnAdd = new JButton();
        btnEdit = new JButton();
        btnDelete = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //label1
        label1.setText("Enter the Customer Number ");
        contentPane.add(label1, "cell 0 0");

        //txtcustno
        txtcustno.setColumns(20);
        contentPane.add(txtcustno, "cell 1 0");

        // label2
        label2.setText("Enter the Customer Name");
        contentPane.add(label2, "cell 0 1");
        contentPane.add(txtcustname, "cell 1 1");

        // label3
        label3.setText("Enter the Initial Deposit");
        contentPane.add(label3, "cell 0 2");
        contentPane.add(txtindeposit, "cell 1 2");

        // label4
        label4.setText("Enter the number of years");
        contentPane.add(label4, "cell 0 3");
        contentPane.add(txtyears, "cell 1 3");

        //---- label5 ----
        label5.setText("Choose the type of savings ");
        contentPane.add(label5, "cell 0 4");
        contentPane.add(comboBox1, "cell 1 4");


        {


            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    table1MouseClicked(e);
                }
            });
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1, "cell 0 5");

        // btnAdd exception handler
        btnAdd.setText("Add");
        btnAdd.addActionListener(e -> {
            try {
                btnAddActionPerformed(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        contentPane.add(btnAdd, "cell 0 6");

        //  btnAdd exception handler
        btnEdit.setText("Edit");
        btnEdit.addActionListener(e -> {
            try {
                btnEditActionPerformed(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        contentPane.add(btnEdit, "cell 0 6");

        //  btnAdd exception handler
        btnDelete.setText("Delete");
        btnDelete.addActionListener(e -> {
            try {
                btnDeleteActionPerformed(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        contentPane.add(btnDelete, "cell 0 6");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel label1;
    private JTextField txtcustno;
    private JLabel label2;
    private JTextField txtcustname;
    private JLabel label3;
    private JTextField txtindeposit;
    private JLabel label4;
    private JTextField txtyears;
    private JLabel label5;
    private JComboBox comboBox1;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
