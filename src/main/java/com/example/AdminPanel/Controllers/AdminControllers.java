package com.example.AdminPanel.Controllers;

import com.example.AdminPanel.Entity.Admin;
import com.example.AdminPanel.Entity.Driver;
import com.example.AdminPanel.Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jdk.jshell.Snippet;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class AdminControllers {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public AdminControllers(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/create/admin")
    public Admin createAdmin(@Valid @RequestBody Admin admin){
        return userService.createAdmin(admin);
    }
    @GetMapping("/final/result")
    public String getMessage(HttpSession httpSession){
        return (String) httpSession.getAttribute("userName");
    }
    @GetMapping("/hello")
    public String HelloMsg(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("userName","abhi@gmail.com");
        return "Hello";
    }
    @PostMapping("/add/driver")
    public Driver addDriver(@RequestBody Driver driver){
      //  System.out.println(driver.toString());
        return userService.saveDriver(driver);
    }
    @DeleteMapping("/delete/driver")
    public int deleteUser(@RequestParam String uid){
        return userService.deleteById(Integer.parseInt(uid));
    }
    @PatchMapping("/suspend/driver")
    public String softDeleteDriver(@RequestParam Integer uid){
         userService.softDeleteDriver(uid);
         return "SuccessFull";
    }
    @PatchMapping("/update/driver")
    public Driver updateDriver(@RequestParam Integer id,HttpServletRequest request){
        Driver driver = userService.findDriverById(id);
        try {
            System.out.println("Control Reaches Inside!!!");
            Driver updatedDriver = objectMapper.readerForUpdating(driver).readValue(request.getReader());
            System.out.println(updatedDriver);
            return userService.updateDriver(updatedDriver);
            //return updatedDriver;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PatchMapping("/update/status")
    public String updateStatus(@RequestParam Integer uid,@RequestParam String status){
        userService.updateDriverStatus(uid,status);
        return "SuccessFull";
    }
    @GetMapping("/driver/count")
    public int driverCount(){
        return userService.getDriverCount();
    }
    @PostMapping("/upload/image")
    public String uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String folder = "/springBoot projects/AdminPanel/src/main/java/images/";
        byte[] bytes = file.getBytes();
        // Path path = Paths.get(folder + file.getOriginalFilename());
        Path path = Paths.get(folder + "admin.jpg");
        Files.write(path,bytes);
        return "😎😎😎😎";
    }
    @GetMapping(value = "show/image/{name}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] downloadImage(@PathVariable String name) throws IOException {
        String filePath = "/springBoot projects/AdminPanel/src/main/java/images/" + name + ".jpg";
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }
//    @GetMapping("/driver/list/{type}")
//    public List<Driver> getList(@PathVariable String type){
//        return userService.getDriverList(type);
//    }
}
