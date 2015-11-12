package com.isdust.www.datatype;

/**
 * Created by wzq on 15/10/16.
 */
public class ScheduleInformation {
    int mzhoushu;
    int mxinqi;
    int mjieci;
    String mlocation;
    String mcourse;
    String mteacher;
    public ScheduleInformation(String location, int zhoushu, int xingqi, int jieci){
        mlocation=location;
        mzhoushu=zhoushu;
        mxinqi=xingqi;
        mjieci=jieci;

    }
    public ScheduleInformation(String location, int zhoushu, int xingqi, int jieci, String course, String teacher){
        mlocation=location;
        mzhoushu=zhoushu;
        mxinqi=xingqi;
        mjieci=jieci;
        mcourse=course;
        mteacher=teacher;
    }
    public String getlocation(){
        return mlocation;
    }
    public void setlocation(String location){
        mlocation=location;

    }
    public String getcourse(){
        return mcourse;
    }
    public void setcourse(String course){
        mcourse=course;

    }
    public String getteacher(){
        return mteacher;
    }
    public void setteacher(String teacher){
        mteacher=teacher;

    }

    public int getzhoushu(){
        return mzhoushu;
    }
    public void setzhoushu(int zhoushu){
        mzhoushu=zhoushu;
    }

    public int getjieci(){
        return mjieci;
    }
    public void setjieci(int jieci){
        mjieci=jieci;
    }

    public int getxinqi(){
        return mxinqi;
    }
    public void setxinqi(int xinqi){
        mxinqi=xinqi;
    }
}
