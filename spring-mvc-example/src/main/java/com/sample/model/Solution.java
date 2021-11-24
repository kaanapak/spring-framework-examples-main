package com.sample.model;

import java.util.ArrayList;

public class Solution {
    private String reagions;
    private ArrayList<String> messageList=new ArrayList<>();
    public String DataString;
    public String DialysisNames;
    public String DialysisCoordinates;
    public String HosptialNames;
    public String HospitalCoordinates;
    public Integer [] DataArray;


    public String getReagions() {
        return reagions;
    }
    public void setReagions(String city) {
        this.reagions = reagions;
    }
    public ArrayList<String> getMessageList() {
        return messageList;
    }

    public void AddMessage(String message) {
        messageList.add(message);
    }


    public void setDataString(String DataString) {
        this.DataString = DataString;
    }
    public String getDataString(){
        return DataString;
    }

    public void setDataArray() {
        DataArray = new Integer[]{3, 5, 7};
    }
}
