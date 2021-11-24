package com.sample.controller;

import com.sample.model.Centers;
import com.sample.model.Solution;
import com.sample.model.Status;
import com.sample.services.CentersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sample.services.SolutionService;

import java.io.IOException;

@Controller
public class SolutionController {
    @Autowired
    SolutionService service;


    @PostMapping("/solution")
    public String solution(Model model,@RequestParam(name="status", required=false) String status_string,@RequestParam(name="centers", required=false) String centers_string,String number_of_days,String max_distance,String max_short_service) throws IOException {
         System.out.println("Last string "+ centers_string);
        Status status=service.reloadStatus_S(status_string);
        Centers centers=service.reloadCenter(centers_string);
        service.CreateTextFiles(centers,number_of_days,max_distance,max_short_service);
        Solution solution=service.prepareSolution(status.scenario);
        model.addAttribute("centers", solution);


        return "solution";
    }
}
