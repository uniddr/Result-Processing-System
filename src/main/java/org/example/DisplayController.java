package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DisplayController {
    public BorderPane borderpane;
    public Button personal,grades,registered,GoBack;
    public Label getID;
    public Label splitlabel;

    public void set_id(String i) {
        getID.setText(i);
    }
    public void setComponent() {
        getID.setVisible(false);
    }

    public void setpersonal() throws Exception {
        Pane p=loadPersonalInfo();
        borderpane.setCenter(p);
    }
    public void setgrades() throws Exception {
        Pane p=loadGradeInfo();
        borderpane.setCenter(p);
    }
    public void setregistered() throws Exception {
        Pane p=loadCourseInfo();
        borderpane.setCenter(p);
    }
    public Pane loadPersonalInfo() throws Exception {
        FXMLLoader fxml=new FXMLLoader(getClass().getResource("pi.fxml"));
        Pane p = fxml.load();
        PiController pcl=fxml.getController();
        pcl.setPersonalInfo(getID.getText());
        return p;
    }
    public Pane loadGradeInfo() throws Exception {
        FXMLLoader fxml=new FXMLLoader(getClass().getResource("grade.fxml"));
        Pane p = fxml.load();
        PiController pcl=fxml.getController();
        pcl.setGradesTable(getID.getText());
        return p;
    }
    public Pane loadCourseInfo() throws Exception {
        FXMLLoader fxml=new FXMLLoader(getClass().getResource("courses.fxml"));
        Pane p = fxml.load();
        PiController pcl=fxml.getController();
        pcl.setCourseTable(getID.getText());
        return p;
    }
    public void GoBackHandle() throws Exception {
        FXMLLoader fxml=new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root=fxml.load();
        Window w=GoBack.getScene().getWindow();
        Stage st=(Stage)w;
        Scene sc=new Scene(root);
        st.setTitle("Login");
        st.setScene(sc);
        st.setTitle("Login");
        st.setResizable(false);
        st.show();
    }
}
