package com.sepmp24.lnmiit.staffrecruitment.Model;

/**
 * Created by Nikunj on 05-11-2015.
 */
public class Vacancy {

    private int vacancyId;
    private String title;
    private int depNo;
    private String activationDate;
    private String deadline;
    private int seats;
    private String status;

    public Vacancy(int vid,String t,int d,String ad,String deadline , int s,String status)
    {
        vacancyId=vid;
        title = t;
        depNo = d;
        activationDate = ad;
        this.deadline = deadline;
        seats =  s;
        this.status = status;
    }

    public void ActivateVacancy()
    {
        this.status = "ACTIVE";
    }

    public String checkStatus()
    {
       return this.status;
    }
    public void DisplayVacancy()
    {

    }

    public void DeActivateVacancy()
    {
        this.status = "CLOSED";
    }


}
