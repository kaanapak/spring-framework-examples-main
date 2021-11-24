package com.sample.model;
import java.util.ArrayList;
public class Centers {
    private String reagions;
    private ArrayList<String> messageList=new ArrayList<>();
    public String DataString;
    public String DialysisNames;
    public String DialysisCoordinates;
    public String HosptialNames;
    public String HospitalCoordinates;
    public String AllCoordinates;
    public String Patient_number_string;
    public String Selected_all_string;
    public String [] Before_Disaster;
    public Integer [] DataArray;
    public Integer total_patient;
    public Integer num_dialysis;
    public ArrayList<Integer> selected_all=new ArrayList<>();
    public ArrayList<Integer> patient_number=new ArrayList<>();
    public ArrayList<Integer> selected_dialysis=new ArrayList<>();
    public Integer after_disaster;
    public Integer [] after_total_patient;
    public ArrayList<ArrayList<Integer>> after_total_session=new ArrayList<>();


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

    public String getText(){
        String string=after_disaster+"K"+total_patient+"K"+num_dialysis+"K";
        for(int i=0;i<selected_all.size();i++){
           string+= selected_all.get(i);
           if(i!=selected_all.size()-1){
               string+=",";
           }
        }
string+="K";
        for(int i=0;i<patient_number.size();i++){
            string+= patient_number.get(i);
            if(i!=patient_number.size()-1){
                string+=",";
            }
        }
        string+="K";
        for(int i=0;i<selected_dialysis.size();i++){
            string+= selected_dialysis.get(i);
            if(i!=selected_dialysis.size()-1){
                string+=",";
            }
        }
        string+="K";
        for(int i=0;i<after_total_session.size();i++){
            for(int j=0;j<after_total_session.get(i).size();j++) {
                string += after_total_session.get(i).get(j);
                if (j != after_total_session.get(i).size() - 1) {
                    string += ",";
                }
            }
            if (i != after_total_session.size() - 1) {
                string += "M";
            }
        }

        return string;
    }
}
