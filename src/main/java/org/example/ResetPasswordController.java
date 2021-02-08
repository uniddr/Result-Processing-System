package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class ResetPasswordController {
    public Label RPCurP;
    public Label RPNewP;
    public Label RPConP;
    Connection con=Sqlconnection.connect();
    public TextField current,New,confirm;
    public Button /*currentbutton,*/confirmbutton;
    public Label getID,show;
    public void setID(String i) {
        getID.setText(i);
        getID.setVisible(false);
    }
    /*public void setcurrent() {
        try {
            Statement ps=con.createStatement();
            String s="select * from testrun where `Password`='" +current.getText()+ "' and `ID`='" +getID.getText()+ "'";
            ResultSet r=ps.executeQuery(s);
            if(r.next()) {
                System.out.println("Match found");
                New.setVisible(true);
                confirm.setVisible(true);
                confirmbutton.setVisible(true);
            }
            else {
                New.setVisible(false);
                confirm.setVisible(false);
                confirmbutton.setVisible(false);
                show.setText("Invalid Password!");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }*/
    public void setconfirm() {
        try {
            Statement ps = con.createStatement();
            String s="select * from testrun where `Password`='" +current.getText()+ "' and `ID`='" +getID.getText()+ "'";
            ResultSet r=ps.executeQuery(s);
            if(r.next()) {
                New.setVisible(true);
                confirm.setVisible(true);
                confirmbutton.setVisible(true);
            }
            else {
                New.setText("");
                confirm.setText("");
                New.setVisible(false);
                confirm.setVisible(false);
                //confirmbutton.setVisible(false);
                show.setText("Invalid Current Password!");
                return;
            }

            if(!(New.getText().equals("")) && New.getText().equals(confirm.getText())) {
                try {
                    Statement p=con.createStatement();
                    String sp="update testrun set `Password`='" +New.getText()+ "' where `ID`='" +getID.getText()+ "'";
                    p.executeUpdate(sp);
                    FXMLLoader fxml=new FXMLLoader(getClass().getResource("StudentPanel.fxml"));
                    Parent root=fxml.load();
                    DisplayController d=fxml.getController();
                    d.set_id(getID.getText());
                    d.setComponent();
                    Stage st=(Stage) show.getScene().getWindow();
                    st.setTitle("Display Information");
                    Scene sc=new Scene(root);
                    st.setScene(sc);
                    st.show();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
            else if(New.getText().equals("")) {
                show.setText("Please enter a new password");
            }
            else {
                show.setText("New and confirmed passwords don't match!");
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
