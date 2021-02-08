package org.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.sql.*;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.passay.*;


public class LoginController {
    @FXML
    public Button LoginButton;
    @FXML
    public Button FPass;
    @FXML
    public PasswordField LoginUID;
    @FXML
    public PasswordField LoginPWD;
    @FXML
    public Label LoginNameLabel;
    @FXML
    public Label LoginPassLabel;
    @FXML
    public Label LoginTitle;
    @FXML
    public AnchorPane Login;

    @FXML
    public void LoginButtonHandle(ActionEvent actionEvent) {
        try{
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sakila","root","DartrixDDR4L");
            String uid=LoginUID.getText();
            String pwd=LoginPWD.getText();
            Statement stmt=con.createStatement();
            if(uid.equals("") || pwd.equals("")) {
                Parent WEUPParent = FXMLLoader.load(getClass().getResource("WarnEmptyUsernamePassword.fxml"));
                Stage newstage=new Stage();
                newstage.setTitle("Warning!");
                newstage.setScene(new Scene(WEUPParent));
                newstage.initModality(Modality.APPLICATION_MODAL);
                newstage.setResizable(false);
                /*newstage.maximizedProperty().addListener(((observable, oldValue, newValue) -> {
                    if(newValue) {
                        newstage.setMaximized(false);
                    }
                }));*/
                newstage.show();
                con.close();
                return;
            }
            String query="select * from testrun where `Username`='" + uid + "' and `Password`='" + pwd + "'";
            ResultSet rs=stmt.executeQuery(query);
            if(!rs.isBeforeFirst()) {
                Parent WIUPParent = FXMLLoader.load(getClass().getResource("WarnInvalidUsernamePassword.fxml"));
                Stage newstage=new Stage();
                newstage.setTitle("Warning!");
                newstage.setScene(new Scene(WIUPParent));
                newstage.initModality(Modality.APPLICATION_MODAL);
                newstage.setResizable(false);
                newstage.show();
                con.close();
                return;
            }
            if(rs.next()) {
                int ID=rs.getInt(5);
                FXMLLoader fxml=new FXMLLoader(getClass().getResource("StudentPanel.fxml"));
                Parent root=fxml.load();
                DisplayController d=fxml.getController();
                d.set_id(String.valueOf(ID));
                d.setComponent();
                Window w=LoginButton.getScene().getWindow();
                Stage st=(Stage)w;
                Scene sc=new Scene(root);
                st.setTitle("Student Information Panel");
                st.setScene(sc);
                st.show();
            }

            /*while(rs.next()) {
                System.out.println(rs.getString(1) +"  "+ rs.getString(2) +"  "+ rs.getString(3));
            }*/
            con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void LoginPWDHandle(ActionEvent actionEvent) {

    }

    @FXML
    public void LoginUIDHandle(ActionEvent actionEvent) {

    }

    @FXML
    public void FPassHandle(ActionEvent actionEvent) {

        try{
            String name=LoginUID.getText();
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sakila","root","DartrixDDR4L");
            Statement stmt=con.createStatement();
            if(name.equals("")) {
                con.close();
                Parent WEUParent = FXMLLoader.load(getClass().getResource("WarnEmptyUsername.fxml"));
                Stage newstage=new Stage();
                newstage.setTitle("Warning!");
                newstage.setScene(new Scene(WEUParent));
                newstage.initModality(Modality.APPLICATION_MODAL);
                newstage.setResizable(false);
                newstage.show();
                return;
            }
            String queryname="select * from testrun where `Username`='" + name + "'";
            ResultSet rs1=stmt.executeQuery(queryname);
            if(!rs1.isBeforeFirst()) {
                con.close();
                Parent WIUParent = FXMLLoader.load(getClass().getResource("WarnInvalidUsername.fxml"));
                Stage newstage=new Stage();
                newstage.setTitle("Warning!");
                newstage.setScene(new Scene(WIUParent));
                newstage.initModality(Modality.APPLICATION_MODAL);
                newstage.setResizable(false);
                newstage.show();
                return;
            }
            String mail=null;
            String pass=null;
            while(rs1.next()) {
                pass=rs1.getString(3);
                mail=rs1.getString(4);
            }
            if(mail==null) {
                con.close();
                Parent WNEFUParent = FXMLLoader.load(getClass().getResource("WarnNoEmailForUsername.fxml"));
                Stage newstage=new Stage();
                newstage.setTitle("Warning!");
                newstage.setScene(new Scene(WNEFUParent));
                newstage.initModality(Modality.APPLICATION_MODAL);
                newstage.setResizable(false);
                newstage.show();
                return;
            }
            PasswordGenerator gen=new PasswordGenerator();
            CharacterData uc=EnglishCharacterData.UpperCase,lc=EnglishCharacterData.LowerCase;
            CharacterRule ucr=new CharacterRule(uc),lcr=new CharacterRule(lc);
            ucr.setNumberOfCharacters(1);
            lcr.setNumberOfCharacters(1);

            CharacterData alph=EnglishCharacterData.Alphabetical,dig=EnglishCharacterData.Digit;
            CharacterRule alphr=new CharacterRule(alph),digr=new CharacterRule(dig);
            alphr.setNumberOfCharacters(3);
            digr.setNumberOfCharacters(1);

            CharacterData spc=new CharacterData() {
                @Override
                public String getErrorCode() {
                    return null;
                }

                @Override
                public String getCharacters() {
                    return "`~!@#$%^&*()-_=+;'[]{}:\",./<>?|";
                }
            };
            CharacterRule spcr=new CharacterRule(spc);
            spcr.setNumberOfCharacters(2);
            String newpass=gen.generatePassword(8,ucr,lcr,alphr,digr,spcr);
            String queryresetpass="update `sakila`.`testrun` set `Password`='" +newpass+ "' where (`Password`='" +pass+ "')";
            int rs2=stmt.executeUpdate(queryresetpass);
            if(rs2==1) {
                System.out.println("Successfully updated database!");
            }
            con.close();

            String username = "hmmwellnvm@gmail.com";
            String password = "oELp732-";

            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true"); //TLS
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Session session = Session.getInstance(prop,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("hmmwellnvm@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(mail)
            );
            message.setSubject("Reset Password");
            message.setText("Dear " + name + "," + "\n\nYour new password is: " + newpass
                    + "\nUse this password to login.");

            Transport.send(message);

            Parent MSSParent = FXMLLoader.load(getClass().getResource("MailSendSuccess.fxml"));
            Stage newstage=new Stage();
            newstage.setTitle("Successfully Sent!");
            newstage.setScene(new Scene(MSSParent));
            newstage.initModality(Modality.APPLICATION_MODAL);
            newstage.setResizable(false);
            newstage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
