package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.*;

public class PiController {
    public Label ID;
    public Label ChooseSem;
    public AnchorPane PIAnchor;
    public Label PIName,PIID,PIDepartment,PIAY,PICN,PIEmail,PIProgram;
    public Label GradeName,GradeID,GradeDepartment,GradeProgram,GradeSem;

    Connection con = Sqlconnection.connect();

    public Label name,id,email,phone,dept,year,prog;
    public AnchorPane gradeAnchorpane,courseAnchorpane;
    public Button reset;
    public ChoiceBox<String>sem;

    public void setId(String i) {
        id.setText(i);
    }

    public void setName() {
        try {
            Statement q=con.createStatement();
            String s="select * from r_info where `ID`='" +id.getText()+ "'";
            ResultSet r=q.executeQuery(s);
            while(r.next()) {
                name.setText(r.getString("Name"));
            }
            q.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setYear() {
        int y=Integer.MIN_VALUE;
        try {
            Statement q=con.createStatement();
            String s="select * from r_info where `ID`='" +id.getText()+ "'";
            ResultSet r=q.executeQuery(s);
            while(r.next()) {

                if(r.getInt("Year")>y) {
                    y=r.getInt("Year");
                }
            }
            q.close();
            year.setText(String.valueOf(y));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setDept() {
        try {
            Statement q=con.createStatement();
            String s="select * from r_info where ID='" +id.getText()+ "'";
            ResultSet r=q.executeQuery(s);
            while(r.next()) {
                dept.setText(r.getString("Department"));
            }
            q.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setPhone() {
        try {
            Statement q=con.createStatement();
            String s="select * from testrun where ID='" +id.getText()+ "'";
            ResultSet r=q.executeQuery(s);
            while(r.next()) {
                phone.setText(r.getString("Phone Number"));
            }
            q.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setEmail() {
        try {
            Statement q=con.createStatement();
            String s="select * from testrun where ID='" +id.getText()+ "'";
            ResultSet r=q.executeQuery(s);
            while(r.next()) {
                email.setText(r.getString("Email Address"));
            }
            q.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setProg() {
        try {
            Statement q=con.createStatement();
            String s="select * from testrun where ID='" +id.getText()+ "'";
            ResultSet r=q.executeQuery(s);
            while(r.next()) {
                prog.setText(r.getString("Program"));
            }
            q.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setPersonalInfo(String i) {
        setId(i);
        setName();
        setDept();
        setYear();
        setPhone();
        setEmail();
        setProg();
    }

    public void setCourse() {
        sem.getItems().addAll("1","2","3","4","5","6","7","8");
        sem.setOnAction((Event)-> {
            TableView<course>course_table=new TableView<>();
            TableColumn<course,String>coursename=new TableColumn<>("Course Name");
            TableColumn<course,String>courseID=new TableColumn<>("Course ID");
            TableColumn<course,String>teacher=new TableColumn<>("Course Teacher");
            course_table.setPrefWidth(610);
            course_table.setPrefHeight(390);
            course_table.setLayoutX(70);
            course_table.setLayoutY(156);
            coursename.setPrefWidth(240);
            courseID.setPrefWidth(140);
            teacher.setPrefWidth(242);
            courseAnchorpane.getChildren().add(course_table);
            coursename.setCellValueFactory(new PropertyValueFactory<>("sub"));
            courseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
            teacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
            course_table.setItems(getCourseTable());
            course_table.getColumns().addAll(coursename,courseID,teacher);
        } );
    }

    public void setCourseTable(String i) {
        setId(i);
        setCourse();
    }

    public void setSem() {
        sem.getItems().addAll("1","2","3","4","5","6","7","8");
        sem.setOnAction((Event)-> {
            TableView<course>grade_table=new TableView<>();
            TableColumn<course,String>sub=new TableColumn<>("Subject");
            TableColumn<course,String>grade=new TableColumn<>("Grade");
            TableColumn<course,Double>gpa=new TableColumn<>("GPA");
            TableColumn<course,Integer>yearcol=new TableColumn<>("Year");
            grade_table.setPrefWidth(613);
            grade_table.setPrefHeight(385);
            grade_table.setLayoutX(68);
            grade_table.setLayoutY(160);
            sub.setPrefWidth(240);
            gpa.setPrefWidth(103);
            grade.setPrefWidth(140);
            yearcol.setPrefWidth(130);
            gradeAnchorpane.getChildren().add(grade_table);
            sub.setCellValueFactory(new PropertyValueFactory<>("sub"));
            grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
            gpa.setCellValueFactory(new PropertyValueFactory<>("gpa"));
            yearcol.setCellValueFactory(new PropertyValueFactory<>("year"));
            grade_table.setItems(getGradesTable());
            grade_table.getColumns().addAll(sub,gpa,grade,yearcol);
        } );
    }

    public void setGradesTable(String i) {
        setId(i);
        setName();
        setDept();
        setProg();
        setSem();
    }


    public ObservableList<course> getGradesTable() {
        ObservableList<course> obl= FXCollections.observableArrayList();
        try {
            Statement p=con.createStatement();
            String string="select * from r_info where ID='" +id.getText()+ "' and `Semester`='" +sem.getValue()+ "'";
            ResultSet r=p.executeQuery(string);
            while(r.next()) {
                obl.add(new course(r.getString("Course Name"),r.getFloat("GPA"),r.getString("Grade"),r.getInt("Year")));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return obl;
    }

    public ObservableList<course>getCourseTable() {
        ObservableList<course> obl=FXCollections.observableArrayList();
        try {
            Statement p=con.createStatement();
            String string="select * from r_info where ID='" +id.getText()+ "' and `Semester`='" +sem.getValue()+ "'";
            ResultSet r=p.executeQuery(string);
            while(r.next()) {
                obl.add(new course(r.getString("Course Name"),r.getString("Course ID"),r.getString("Course Teacher")));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return obl;
    }

    public void setreset() throws Exception {
        FXMLLoader fxml=new FXMLLoader(getClass().getResource("ResetPassword.fxml"));
        Parent root=fxml.load();
        ResetPasswordController p=fxml.getController();
        p.setID(id.getText());
        Stage st=(Stage) reset.getScene().getWindow();
        st.setTitle("Update Password");
        Scene sc=new Scene(root);
        st.setScene(sc);
        st.show();
    }
}
