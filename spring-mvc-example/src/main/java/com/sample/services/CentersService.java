package com.sample.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.sample.model.Status;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Service;

import com.sample.model.Centers;



@Service
public class CentersService {

    public Centers prepareCenters(String reagion, Status status) throws IOException {
        System.out.println("B"+status.before_disaster);
        System.out.println("A"+status.after_disaster);
        Integer after_disaster=status.after_disaster;

        Centers centers=new Centers();
        centers.setReagions(reagion);
        centers.after_disaster=after_disaster;

        String answer_string = reagion;
        String[] myArray = answer_string.split(",");
        List<String> selectedreagions = Arrays.asList(myArray);

        //dialysis_scanner.useDelimiter(";");   //sets the delimiter pattern
        ArrayList<Integer> selected_dialysis = new ArrayList<>();
        ArrayList<Integer> selected_all = new ArrayList<>();
        ArrayList<Integer> machine = new ArrayList<>();
        ArrayList<Integer> patient_number = new ArrayList<>();
        ArrayList<Integer> backup_machine = new ArrayList<>();
        ArrayList<Integer> risk_point = new ArrayList<>();
        ArrayList<Integer> dialysis_all_num = new ArrayList<>();

        String hospital_coordinates="";
        String hospital_names="";
        String dialysis_coordinates="";
        String dialysis_names="";
        ArrayList<String> all_coordinate_list=new ArrayList<>();
        for (int j = 0; j < 50; j++) {
            selected_dialysis.add(0);
            machine.add(0);
            patient_number.add(0);
            backup_machine.add(0);
            risk_point.add(0);
            selected_all.add(0);
            all_coordinate_list.add("");
        }
        Integer total_patient = 0;
        Integer num_dialysis = 0;
        Integer num_hospital = 0;

        Scanner dialysis_scanner = new Scanner(new File("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\dialysis_centers.csv"));
        //"dialysis_centers.csv"
        dialysis_scanner.nextLine();
        while (dialysis_scanner.hasNext()) {
            String current_line = dialysis_scanner.nextLine();
            String[] lineArray = current_line.split(";");
            int last_index = Integer.parseInt(lineArray[0]);
            dialysis_all_num.add(last_index);
            String last_reagion = lineArray[2];
            int is_selected = 0;
            for (int i = 0; i < selectedreagions.size(); i++) {
                if (selectedreagions.get(i).equals(last_reagion)) {
                    selected_dialysis.set(last_index, 1);
                    selected_all.set(last_index, 1);
                    is_selected = 1;
                    num_dialysis += 1;
                }
            }
            int last_machine = Integer.parseInt(lineArray[3]);
            machine.set(last_index, last_machine);
            int last_patient = (int) (last_machine * 0.8 * 2 * 2);
            patient_number.set(last_index, last_patient);
            if (is_selected == 1) {
                if(dialysis_names.length()==0){
                    dialysis_names+=lineArray[1];
                    dialysis_coordinates+=lineArray[6];
                    all_coordinate_list.add(last_index,lineArray[6]);
                }else {
                    dialysis_names += "*"+lineArray[1] ;
                    dialysis_coordinates += "*"+lineArray[6] ;
                    all_coordinate_list.add(last_index,lineArray[6]);
                }
               // System.out.println("D"+last_index+" "+last_patient);
                total_patient += last_patient;
            }else{
                all_coordinate_list.add(last_index,"B");
            }
            backup_machine.set(last_index, Integer.parseInt(lineArray[4]));
            risk_point.set(last_index, Integer.parseInt(lineArray[5]));

            //dialysis_scanner.nextLine();

        }
        dialysis_scanner.close();  //closes the scanner


        Scanner hospital_scanner = new Scanner(new File("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\hospitals.csv"));
        hospital_scanner.nextLine();
        while (hospital_scanner.hasNext()) {
            String current_line = hospital_scanner.nextLine();

            String[] lineArray = current_line.split(";");
            int last_index = Integer.parseInt(lineArray[0]);
            String last_reagion = lineArray[2];
            int is_selected = 0;
            for (int i = 0; i < selectedreagions.size(); i++) {
                if (selectedreagions.get(i).equals(last_reagion)) {
                    selected_all.set(last_index, 1);
                    is_selected = 1;
                    num_hospital += 1;
                }
            }
            int last_machine = Integer.parseInt(lineArray[3]);
            machine.set(last_index, last_machine);
            int last_patient = (int) (last_machine * 0.8 * 2 * 2);
            patient_number.set(last_index, last_patient);
            if (is_selected == 1) {
                if(hospital_names.length()==0){
                    hospital_names+=lineArray[1];
                    hospital_coordinates+=lineArray[4];
                    all_coordinate_list.add(last_index,lineArray[4]);
                }else {
                    hospital_names +="*"+ lineArray[1];
                    hospital_coordinates += "*"+lineArray[4] ;
                    all_coordinate_list.add(last_index,lineArray[4]);
                }
                //System.out.println("H"+last_index+" "+last_patient);
                total_patient += last_patient;
            }else{
                all_coordinate_list.add(last_index,"B");
            }

        }
        String all_coordinates= StringUtils.join(all_coordinate_list, '*');
        centers.AllCoordinates=all_coordinates;
        centers.selected_all=selected_all;
        centers.selected_dialysis=selected_dialysis;
        centers.num_dialysis=num_dialysis;

        Integer total_user_patient=0;

        if(status.before_disaster==1){
            Scanner user_scanner = new Scanner(new File("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\Afet_once.txt"));
            patient_number.clear();

            int last_i=0;
            while (user_scanner.hasNext()) {
                String current_line = user_scanner.nextLine();
                int current_patient_number=Integer.valueOf(current_line);
                if(selected_all.get(last_i)==1) {
                    total_user_patient += current_patient_number;
                    patient_number.add(current_patient_number);
                }
                last_i++;
            }

        }
        if(status.before_disaster==1) {
            centers.total_patient = total_user_patient;
            System.out.println("eeee: "+total_user_patient);
        }else{
            centers.total_patient=total_patient;
        }
        centers.patient_number=patient_number;

          System.out.println("Total: "+centers.total_patient);
        dialysis_scanner.close();  //closes the scanner

        centers.AddMessage(reagion);
        centers.AddMessage(String.valueOf(centers.total_patient));
        centers.AddMessage(String.valueOf(num_dialysis));
        centers.AddMessage(String.valueOf(num_hospital));


        ArrayList<ArrayList<Double>> ratio_matrix = new ArrayList<>();
        Scanner table1_matrix_scanner = new Scanner(new File("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\inverted_table1.csv"));
        table1_matrix_scanner.nextLine();
        while (table1_matrix_scanner.hasNext()) {
            String current_line = table1_matrix_scanner.nextLine();
            String[] lineArray = current_line.split(";");
            ArrayList<Double> current_ratio = new ArrayList<>();
            for (int j = 0; j < lineArray.length; j++) {
                current_ratio.add(Double.parseDouble(lineArray[j]));
            }
            ratio_matrix.add(current_ratio);
        }
        table1_matrix_scanner.close();
        ArrayList<ArrayList<Integer>> scenario_info = new ArrayList<>();
        Scanner table2_matrix_scanner = new Scanner(new File("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\table2.csv"));
        table2_matrix_scanner.nextLine();
        while (table2_matrix_scanner.hasNext()) {
            String current_line = table2_matrix_scanner.nextLine();
            String[] lineArray = current_line.split(";");
            ArrayList<Integer> current_info = new ArrayList<>();
            for (int j = 1; j < lineArray.length; j++) {
                current_info.add(Integer.parseInt(lineArray[j]));
            }
            scenario_info.add(current_info);
        }
        table1_matrix_scanner.close();
        table2_matrix_scanner.close();
        ArrayList<ArrayList<Integer>> after_machine = new ArrayList<>();
        for (int i = 0; i < scenario_info.get(0).size(); i++) {
            ArrayList<Integer> current_machine = new ArrayList<>();
            for (int j = 0; j < machine.size(); j++) {
                int machine_count = machine.get(j);
                int new_risk_point = risk_point.get(j) - 1;
                if (new_risk_point == -1) {
                    machine_count = 0;
                } else if (ratio_matrix.get(new_risk_point).get(i) == 1) {
                    machine_count += backup_machine.get(j) * scenario_info.get(0).get(new_risk_point);
                }
                current_machine.add(machine_count);
            }
            after_machine.add(current_machine);
        }
        ArrayList<ArrayList<Integer>> after_session = new ArrayList<>();
        for (int i = 0; i < scenario_info.get(0).size(); i++) {
            ArrayList<Integer> current_session = new ArrayList<>();
            for (int j = 0; j < machine.size(); j++) {
                int session_count = 0;
                int new_risk_point = risk_point.get(j) - 1;
                if (new_risk_point == -1) {
                    session_count = 0;

                } else {
                    session_count += scenario_info.get(0).get(i) + scenario_info.get(1).get(i);
                }
                current_session.add(session_count);
            }
            after_session.add(current_session);
        }
        ArrayList<ArrayList<Integer>> after_total_session = new ArrayList<>();
        for (int i = 0; i < after_machine.size(); i++) {
            ArrayList<Integer> currentline = new ArrayList<>();
            for (int j = 0; j < after_machine.get(i).size(); j++) {
                double session_count_double = (double) after_session.get(i).get(j) * after_machine.get(i).get(j);
                int new_risk_point = risk_point.get(j) - 1;
                if (new_risk_point == -1) {
                    session_count_double = 0;

                } else {
                    session_count_double *= ratio_matrix.get(new_risk_point).get(i);
                }
                int total_session_count = (int) session_count_double;
                currentline.add(total_session_count);
            }
            after_total_session.add(currentline);
        }
        Integer[] after_total_patient = new Integer[6];
        if(after_disaster==1) {
            ArrayList<Integer> dialysis_user_values = new ArrayList<>();
            for(int m=0;m<50;m++){
                dialysis_user_values.add(-1);
            }
            Scanner user_after_scanner = new Scanner(new File("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\Afet_sonrası.txt"));
            int k=0;
            while (user_after_scanner.hasNext()) {
                String current_line = user_after_scanner.nextLine();
                dialysis_user_values.set(dialysis_all_num.get(k), Integer.valueOf(current_line));
                k++;
            }
            int total_value=0;
            ArrayList<Integer> current_values = new ArrayList<>();
            for(int i=0;i<selected_dialysis.size();i++){

                    current_values.add(dialysis_user_values.get(i));
                    total_value+=dialysis_user_values.get(i);

            }

            after_total_session.add(current_values);
            after_total_patient[5]=total_value;
        }
         for(int i=0;i<=4;i++){
             after_total_patient[i]=0;
         }
        for(int i=0;i<selected_dialysis.size();i++){
            if (selected_dialysis.get(i) == 1) {
                after_total_patient[0] += after_total_session.get(0).get(i);
                after_total_patient[1] += after_total_session.get(1).get(i);
                after_total_patient[2] += after_total_session.get(2).get(i);
                after_total_patient[3] += after_total_session.get(3).get(i);
                if(after_disaster==1){
                    after_total_patient[4] += after_total_session.get(4).get(i);
                }

            }
        }
        System.out.println("after "+after_total_patient[4]);
        centers.after_total_patient=after_total_patient;
        centers.after_total_session=after_total_session;
        centers.DialysisNames=dialysis_names;
        centers.DialysisCoordinates=dialysis_coordinates;
        centers.HosptialNames=hospital_names;
        centers.HospitalCoordinates=hospital_coordinates;
        if(after_disaster==1) {
            centers.setDataString(after_total_patient[0] + "," + after_total_patient[1] + "," + after_total_patient[2] + "," + after_total_patient[3]+ "," + after_total_patient[4]);
            System.out.println(centers.DataString);

        }else {
            centers.setDataString(after_total_patient[0] + "," + after_total_patient[1] + "," + after_total_patient[2] + "," + after_total_patient[3]);
            System.out.println(centers.DataString);
        }
        centers.setDataArray();
        centers.AddMessage(String.valueOf(after_total_patient[0]));
        centers.AddMessage(String.valueOf(after_total_patient[1]));
        centers.AddMessage(String.valueOf(+ after_total_patient[2]));
        centers.AddMessage(String.valueOf(+ after_total_patient[3]));
        if(after_disaster==1) {
            centers.AddMessage(String.valueOf(+after_total_patient[4]));
        }

        table1_matrix_scanner.close();
        table2_matrix_scanner.close();
       String patient_string="";
       for(int i=0;i<patient_number.size();i++){
           if(i==0){
               patient_string= String.valueOf(patient_number.get(0));
           }else{
               patient_string+="*"+patient_number.get(i);
           }
       }
       centers.Patient_number_string=patient_string;
        String selectedall_string="";

        for(int i=0;i<selected_all.size();i++){
            if(i==0){
                selectedall_string= String.valueOf(selected_all.get(0));
            }else{
                selectedall_string+="*"+selected_all.get(i);
            }
        }
        centers.Selected_all_string=selectedall_string;

        return centers;
    }

    public static void CreateTextFiles(Centers centers,String number_of_days,String max_distance,String max_short_service) throws IOException {
        FileWriter karamsar = new FileWriter("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\Karamsar.txt");
        FileWriter orta = new FileWriter("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\Orta.txt");
        FileWriter iyimser = new FileWriter("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\İyimser.txt");
        FileWriter ideal = new FileWriter("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\İdeal.txt");
        FileWriter kullanıcı = new FileWriter("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\Kullanıcı.txt");


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

        for (int i = 0; i < 50; i++) {
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

    public static void saveBefore(String [] BeforeArray) throws IOException {
        FileWriter once = new FileWriter("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\Afet_once.txt");

        for(int i=0;i<BeforeArray.length;i++){

            once.write(BeforeArray[i]);
            once.write("\r\n");

        }

once.close();
    }
    public static void saveAfter(String [] AfterArray) throws IOException {
        FileWriter once = new FileWriter("C:\\Users\\kapak\\IdeaProjects\\spring-framework-examples-main\\spring-mvc-example\\src\\main\\java\\com\\sample\\services\\Afet_sonrası.txt");

        for(int i=0;i<AfterArray.length;i++){

            once.write(AfterArray[i]);
            once.write("\r\n");

        }

        once.close();
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
public Status reloadStatus(String status_text){
        Status status=new Status();
    List<String> status_string = new ArrayList<String>(Arrays.asList(status_text.split(",")));
    status.before_disaster= Integer.valueOf(status_string.get(0));
    status.after_disaster= Integer.valueOf(status_string.get(1));
        return status;

}

    public Status reloadStatus_S(String status_text){
        Status status=new Status();
        List<String> status_string = new ArrayList<String>(Arrays.asList(status_text.split(",")));
        status.before_disaster= Integer.valueOf(status_string.get(0));
        status.after_disaster= Integer.valueOf(status_string.get(1));
        status.scenario= status_string.get(2);
        return status;

    }

    public  Status reloadStatus_O(String status_text){
        Status status=new Status();
        status.before_disaster= Integer.valueOf(status_text);
        return status;
    }

}

