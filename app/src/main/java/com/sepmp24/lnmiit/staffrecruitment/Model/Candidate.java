package com.sepmp24.lnmiit.staffrecruitment.Model;

/**
 * Created by Nikunj on 08-11-2015.
 */
public class Candidate {

    private int id;
    private String name;
    private String email;
    private String mobile;
    private String fatherName;
    private String dob;
    private String password;
    private String postalAddress;
    private String eduQua;
    private String extraCurr;

    public void ApplyForVacancy(int vacancyID)
    {
        //needs to implement
    }

    //first basic constructor
    public Candidate(int id, String n ,String em , String mob , String fname , String dob , String pwd  )
    {
        this.id = id;
        this.name = n;
        this.email = em;
        this.mobile = mob;
        this.fatherName = fname;
        this.dob = dob;
        this.password = pwd;
    }




}
