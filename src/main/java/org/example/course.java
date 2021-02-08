package org.example;

public class course {
    private String sub,grade,courseID,teacher;
    private float gpa;
    private int year;
    course() {

    }
    course(String s,float g,String gr,int y) {
        setSub(s);
        setGrade(gr);
        setGpa(g);
        setYear(y);
    }
    course(String s,String i,String t) {
        setSub(s);
        setCourseID(i);
        setTeacher(t);
    }
    public void setSub(String s) {
        sub=s;
    }
    public void setCourseID(String s) {
        courseID=s;
    }
    public void setTeacher(String s) {
        teacher=s;
    }
    public void setGrade(String g) {
        grade=g;
    }
    public void setGpa(float g) {
        gpa=g;
    }
    public void setYear(int y) {
        year=y;
    }
    public String getSub() {
        return sub;
    }
    public String getGrade() {
        return grade;
    }
    public float getGpa() {
        return gpa;
    }
    public int getYear() {
        return year;
    }
    public String getCourseID() {
        return courseID;
    }
    public String getTeacher() {
        return teacher;
    }
}
