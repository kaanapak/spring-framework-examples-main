package com.sample.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sample.model.Centers;
import com.sample.model.Status;
import org.springframework.stereotype.Service;

import com.sample.model.Solution;
@Service
public class SolutionService {
    public Solution prepareSolution(String scenario) throws IOException {
        System.out.println("Algoritma çalışıyor! Senaryo: "+scenario);
        Solution solution=new Solution();
        return solution;
    }

    public Status reloadStatus_S(String status_text){
        Status status=new Status();
        List<String> status_string = new ArrayList<String>(Arrays.asList(status_text.split(",")));
        status.before_disaster= Integer.valueOf(status_string.get(0));
        status.after_disaster= Integer.valueOf(status_string.get(1));
        status.scenario= status_string.get(2);
        return status;

    }



    public static void CreateTextFiles(Centers centers,String number_of_days,String max_distance,String max_short_service) throws IOException {
        FileWriter karamsar = new FileWriter("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\Karamsar.txt");
        FileWriter orta = new FileWriter("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\Orta.txt");
        FileWriter iyimser = new FileWriter("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\İyimser.txt");
        FileWriter ideal = new FileWriter("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\İdeal.txt");
        FileWriter kullanıcı = new FileWriter("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\Kullanıcı.txt");
       System.out.println("Selected all size "+centers.patient_number.size());
       System.out.println("Sonuç: "+ centers.getText());

        writeall(centers.total_patient.toString(), karamsar, orta, iyimser,ideal,kullanıcı);
        writeall(centers.num_dialysis.toString(), karamsar, orta, iyimser,ideal,kullanıcı);
        writeall(number_of_days, karamsar, orta, iyimser,ideal,kullanıcı);
        writeall("2", karamsar, orta, iyimser,ideal,kullanıcı);
        writeall(max_distance, karamsar, orta, iyimser,ideal,kullanıcı);
        writeall(max_short_service, karamsar, orta, iyimser,ideal,kullanıcı);
        writeall("", karamsar, orta, iyimser,ideal,kullanıcı);
        writeall("3", karamsar, orta, iyimser,ideal,kullanıcı);
        writeall("2", karamsar, orta, iyimser,ideal,kullanıcı);
        writeall("", karamsar, orta, iyimser,ideal,kullanıcı);

        for (int i = 0; i <centers.patient_number.size() ; i++) {
            if (centers.selected_all.get(i) == 1) {
                int count_numbers = centers.patient_number.get(i);
                while (count_numbers != 0) {
                    writeall("1", karamsar, orta, iyimser,ideal,kullanıcı);
                    count_numbers -= 1;
                    if (count_numbers == 0) {
                        break;
                    } else {
                        writeall("2", karamsar, orta, iyimser,ideal,kullanıcı);
                        count_numbers -= 1;
                    }
                    if (count_numbers == 0) {
                        break;
                    } else {
                        writeall("3", karamsar, orta, iyimser,ideal,kullanıcı);
                        count_numbers -= 1;
                    }
                }
            }
        }
        writeall("", karamsar, orta, iyimser,ideal);

        ArrayList<ArrayList<Integer>> min_matrix = new ArrayList<>();
        Scanner min_matrix_scanner = new Scanner(new File("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\min_matrix.csv"));
        min_matrix_scanner.nextLine();
        while (min_matrix_scanner.hasNext()) {
            String current_line = min_matrix_scanner.nextLine();
            String[] lineArray = current_line.split(";");
            ArrayList<Integer> currentpoint = new ArrayList<>();
            for (int i = 0; i < lineArray.length; i++) {
                currentpoint.add(Integer.parseInt(lineArray[i]));
            }
            min_matrix.add(currentpoint);
        }
        for (int i = 0; i < centers.selected_all.size(); i++) {
            if (centers.selected_all.get(i) == 1) {
                for (int j = 0; j < centers.patient_number.get(i); j++) {
                    String current_line = "";
                    Random rand = new Random();
                    Double random = rand.nextDouble();
                    for (int k = 0; k < centers.selected_dialysis.size(); k++) {
                        if (centers.selected_dialysis.get(k) == 1) {
                            Double currentnumber = min_matrix.get(i).get(k) + random;
                            current_line += currentnumber + " ";
                        }
                    }
                    writeall(current_line, karamsar, orta, iyimser,ideal,kullanıcı);
                }
            }

        }
        min_matrix_scanner.close();
        writeall("", karamsar, orta, iyimser,ideal,kullanıcı);
        String t_line = "";
        for (int j = 0; j < centers.num_dialysis; j++) {
            t_line += "10 ";
        }
        for (int i = 0; i < centers.total_patient; i++) {
            writeall(t_line, karamsar, orta, iyimser,ideal,kullanıcı);
        }
        writeall("", karamsar, orta, iyimser,ideal,kullanıcı);
        for (int i = 0; i < centers.total_patient; i++) {
            writeall("200", karamsar, orta, iyimser,ideal,kullanıcı);
        }
        writeall(" ",karamsar,orta,iyimser,ideal,kullanıcı);

        for(int i=0;i<centers.selected_dialysis.size();i++){
            if (centers.selected_dialysis.get(i) == 1) {
                write_cycle(String.valueOf(centers.after_total_session.get(0).get(i)),karamsar,14);
                write_cycle(String.valueOf(centers.after_total_session.get(1).get(i)),orta,14);
                write_cycle(String.valueOf(centers.after_total_session.get(2).get(i)),iyimser,14);
                write_cycle(String.valueOf(centers.after_total_session.get(3).get(i)),ideal,14);
                if(centers.after_disaster==1){
                    write_cycle(String.valueOf(centers.after_total_session.get(4).get(i)),kullanıcı,14);
                }

            }
        }

        karamsar.close();
        orta.close();
        iyimser.close();
        ideal.close();
        kullanıcı.close();
    }

    public static void writeall(String text,FileWriter karamsar,FileWriter orta,FileWriter iyimser,FileWriter ideal) throws IOException {
        karamsar.write(text);
        orta.write(text);
        iyimser.write(text);
        ideal.write(text);
        karamsar.write("\r\n");
        orta.write("\r\n");
        iyimser.write("\r\n");
        ideal.write("\r\n");
    }

    public static void writeall(String text,FileWriter karamsar,FileWriter orta,FileWriter iyimser,FileWriter ideal,FileWriter kullanıcı) throws IOException {
        karamsar.write(text);
        orta.write(text);
        iyimser.write(text);
        ideal.write(text);
        kullanıcı.write(text);
        karamsar.write("\r\n");
        orta.write("\r\n");
        iyimser.write("\r\n");
        ideal.write("\r\n");
        kullanıcı.write("\r\n");
    }

    public static void write_cycle(String text,FileWriter writer,int cycle) throws IOException {
        String cycled_text=" ";
        for(int i=0;i<cycle;i++){
            cycled_text+=text+" ";
        }
        writer.write(cycled_text);
        writer.write("\r\n");
    }

    public Centers reloadCenter(String centerText) {
        Centers centers=new Centers();
        List<String> main_list = new ArrayList<String>(Arrays.asList(centerText.split("K")));
        centers.after_disaster= Integer.valueOf(main_list.get(0));
        centers.total_patient= Integer.valueOf(main_list.get(1));
        centers.num_dialysis= Integer.valueOf(main_list.get(2));
        System.out.println("S1: "+main_list.get(3));
        System.out.println("S2: "+main_list.get(4));
        System.out.println("S3: "+main_list.get(5));
        System.out.println("S4: "+main_list.get(6));

        List<String> selected_all_string_list = new ArrayList<String>(Arrays.asList(main_list.get(3).split(",")));
        ArrayList<Integer> selected_all=new ArrayList<>();
        for(int i=0;i<selected_all_string_list.size();i++){
            selected_all.add(Integer.valueOf(selected_all_string_list.get(i)));
        }
        centers.selected_all=selected_all;

        List<String> patient_number_string_list = new ArrayList<String>(Arrays.asList(main_list.get(4).split(",")));
        ArrayList<Integer> patient_number=new ArrayList<>();
        for(int i=0;i<patient_number_string_list.size();i++){
            patient_number.add(Integer.valueOf(patient_number_string_list.get(i)));
        }
        centers.patient_number=patient_number;

        List<String> selected_dialysis_string_list = new ArrayList<String>(Arrays.asList(main_list.get(5).split(",")));
        ArrayList<Integer> selected_dialysis=new ArrayList<>();
        for(int i=0;i<selected_dialysis_string_list.size();i++){
            selected_dialysis.add(Integer.valueOf(selected_dialysis_string_list.get(i)));
        }
        centers.selected_dialysis=selected_dialysis;

        List<String> scenario_list = new ArrayList<String>(Arrays.asList(main_list.get(6).split("M")));
        System.out.println("S4.1: "+scenario_list.get(0));
        System.out.println("S4.2: "+scenario_list.get(1));
        System.out.println("S4.3: "+scenario_list.get(2));

        ArrayList<ArrayList<Integer>> after_total_session=new ArrayList<>();
        for(int i=0;i<scenario_list.size();i++){
            List<String> current_list_string = new ArrayList<String>(Arrays.asList(scenario_list.get(i).split(",")));
            ArrayList<Integer> current_list=new ArrayList<>();
            for(int j=0;j<current_list_string.size();j++){
                current_list.add(Integer.valueOf(current_list_string.get(j)));
            }
            after_total_session.add(current_list);
        }
        centers.after_total_session=after_total_session;
        System.out.println(centers.getText());
        return centers;
    }
}