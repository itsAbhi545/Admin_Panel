package com.example.AdminPanel.Controllers;

import com.example.AdminPanel.Entity.Admin;
import com.example.AdminPanel.Entity.Driver;
import com.example.AdminPanel.Services.UserService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
//@RequestMapping("/views")
public class ViewsControllers {
    private final UserService userService;

    public ViewsControllers(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String getMainPage(){
        return "index";
    }
    @GetMapping("/dashboard")
    public String getAdminDashboard(Model model){
        List booklist = new ArrayList<>();
        Admin admin = userService.findAdminByEmail("abhi@gmail.com");
        model.addAttribute("firstName",admin.getFirstName());
        model.addAttribute("lastName",admin.getLastName());
        model.addAttribute("imageUrl","http://localhost:8080/show/image/admin");
        return "dashboard";
    }
    @GetMapping("/drivers")
    public String getDriverList(Model model,@RequestParam(defaultValue = ".") String email,
                                @RequestParam(name="limit",defaultValue = "5") Integer limiT,
                                @RequestParam(name="skip",defaultValue = "0") Integer skiP,
                                @RequestParam(name="sortBy",defaultValue = "id") String type){
        //List driverList = Arrays.asList(new Driver("Subham","Kumar"),new Driver("Harman","Singh"));
        List<Driver> driverList;
        if(email.equals(".")){
            System.out.println("\u001B[34m" +type+"///"+"\u001B[0m");//
            driverList = userService.getDriverList(skiP,limiT,type);
        }else{
            driverList = userService.findDriverByCred(email);
        }
        System.out.println("control reaches here!!");
        Admin admin = userService.findAdminByEmail("abhi@gmail.com");
        model.addAttribute("firstName",admin.getFirstName());
        model.addAttribute("lastName",admin.getLastName());
        model.addAttribute("imageUrl","http://localhost:8080/show/image/admin");
        //driverList = driverList.stream().skip(skiP*limiT).filter(driver->!driver.isSoftDelete()).limit(limiT).collect(Collectors.toList());
        model.addAttribute("driverList",driverList);
        return "drivers";
    }
    @GetMapping("/driverUpdate")
    public String updateDriver(@RequestParam String uid,Model model){
        Driver driver = userService.findDriverById(Integer.parseInt(uid));
        model.addAttribute("firstName",driver.getFirstName());
        model.addAttribute("lastName",driver.getLastName());
        model.addAttribute("phoneNumber",driver.getPhoneNumber());
        model.addAttribute("city",driver.getCity());
        return "updateDriver";
    }
}
