package com.sample.controller;

import com.sample.model.Centers;
import com.sample.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sample.services.CentersService;

import java.io.IOException;
import java.util.Objects;

@Controller
public class CenterController {
    @Autowired
    CentersService service;

    @PostMapping("/centers")
    public String centers(@ModelAttribute("status") String status_string,@RequestParam(name="reagion", required=false) String reagion,Integer before,Integer after,Integer check,Model model) throws IOException {
        System.out.println("Sonuç sayfasındasın");
        Status status=service.reloadStatus(status_string);
       if(Objects.isNull(reagion)){
           reagion="Ataşehir,Beykoz,Çekmeköy,Kadıköy,Kartal,Maltepe,Pendik,Sultanbeyli,Şile,Tuzla,Ümraniye,Üsküdar";
       }
       //System.out.println(status.after_disaster);
        Centers centers=service.prepareCenters(reagion,status);
        model.addAttribute("centers", centers);
        model.addAttribute("status", status);
        return "result";
    }
     @PostMapping("/bolge_sec")
    public String bolge_sec(@RequestParam(name="status", required=false) String status_string,Integer before_disaster,Integer after_disaster,Integer from_process,Model model,@RequestParam(name="sonuc[]", required=false) String [] sonuc) throws IOException {


        if(Objects.isNull(status_string)){
            if(Objects.isNull(before_disaster)){
                before_disaster=0;
            }
            if(Objects.isNull(after_disaster)){
                after_disaster=0;
            }
           // System.out.println("null object");
            Status status=new Status();
            status.before_disaster=before_disaster;
            //System.out.println(status.before_disaster);
            model.addAttribute("status",status);

        }else{
            Status status=service.reloadStatus_O(status_string);
            System.out.println(status_string);
            //System.out.println(status.before_disaster);
            if(after_disaster==1) {
                service.saveAfter(sonuc);
            }
            model.addAttribute("status",status);
        }

        ((Status) model.getAttribute("status")).set_after(after_disaster);



        return "bolge_sec";
    }



    @PostMapping("/afet_oncesi")
    public String afet_oncesi(Model model) throws IOException {
        return "afet_oncesi";
    }

    @PostMapping("/afet_sonrası")
    public String afet_sonrası(Integer before_disaster,Model model, @RequestParam(name="sonuc[]", required=false) String [] sonuc) throws IOException {

        Status status=new Status();
        status.before_disaster=before_disaster;
        model.addAttribute("status",status);
        if(before_disaster==1) {
            service.saveBefore(sonuc);
        }
        return "afet_sonrası";
    }
    @PostMapping("/parametre")
    public String parametre(Model model,String scenario,@RequestParam(name="status", required=false) String status_string,@RequestParam(name="centers", required=false) String centers_string) throws IOException {
        System.out.println(centers_string);
        System.out.println("Parametre sayfasındasın");
        Status status=service.reloadStatus(status_string);
        Centers centers=service.reloadCenter(centers_string);

        status.scenario=scenario;
        model.addAttribute("status",status);
        model.addAttribute("centers", centers);
        return "parametre";
    }



}
