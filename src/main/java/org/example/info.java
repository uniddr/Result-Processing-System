package org.example;

public class info {
    private String name,dept,sub,grade;
    private int id,year;
    private float cgpa;
    info() {

    }
    info(int i,String s,float c,int y,String n,String d,String g) {
        setName(n);
        setId(i);
        setDept(d);
        setYear(y);
        setSub(s);
        setCgpa(c);
        setGrade(g);
    }
    public void setName(String n) {
        name=n;
    }
    public String getName() {
        return name;
    }
    public void setDept(String n) {
        dept=n;
    }
    public String getDept() {
        return dept;
    }
    public void setId(int n) {
        id=n;
    }
    public int getId() {
        return id;
    }
    public void setYear(int n) {
        year=n;
    }
    public int getYear() {
        return year;
    }
    public void setSub(String n) {
        sub=n;
    }
    public String getSub() {
        return sub;
    }
    public void setCgpa(float n) {
        cgpa=n;
    }
    public float getCgpa() {
        return cgpa;
    }
    public void setGrade(String g) {
        grade=g;
    }
    public String getGrade() {
        return grade;
    }
}
