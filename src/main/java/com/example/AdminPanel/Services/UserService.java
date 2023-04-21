package com.example.AdminPanel.Services;

import com.example.AdminPanel.Entity.Admin;
import com.example.AdminPanel.Entity.Driver;
import com.example.AdminPanel.Repo.AdminRepo;
import com.example.AdminPanel.Repo.DriverRepo;
//import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final AdminRepo adminRepo;
    private final DriverRepo driverRepo;

    public UserService(AdminRepo adminRepo, DriverRepo driverRepo) {
        this.adminRepo = adminRepo;
        this.driverRepo = driverRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String email = Optional.ofNullable(username).orElse("abcd");
        Admin admin  = adminRepo.findAdminByEmail(email);

        if (email==null||admin==null) {
            // System.out.println("load User  by name method is called!!");
            //log.error("User can not be found in database");
            throw new UsernameNotFoundException("Username not found");
        }
        Collection<SimpleGrantedAuthority> authorites=new ArrayList<>();
        return new User(admin.getEmail(),  admin.gtPassword(), authorites);
    }
    //method responsible for creation of admin
    public Admin createAdmin(Admin admin){
        return adminRepo.save(admin);
    }

    public Admin findAdminByEmail(String email){ return  adminRepo.findAdminByEmail(email);}
    public List<Driver> getDriverList(int page,int size,String type){
        Pageable pageable = PageRequest.of(page,size,Sort.Direction.ASC, type);
//        String sqlCommand=String.format("Select * from Driver order by %s limit %s offset %s",type,limit,skip);
        return driverRepo.getDriverList(pageable);
        //
    }
    public Driver saveDriver(Driver driver){
        return driverRepo.save(driver);
    }
    public  Driver findDriverById(int uid){
        return driverRepo.findDriverById(uid);
    }
    public int deleteById(int id){
        return driverRepo.deleteById(id);
    }
    public List<Driver> findDriverByCred(String email){
        return driverRepo.findDriverByCred(email);
       // driverRepo.getOne(id);
    }
    public Driver updateDriver(Driver driver){
        return driverRepo.saveAndFlush(driver);
    }
    public void softDeleteDriver(int uid){
        driverRepo.softDeleteDriver(uid,true);
    }
    public void updateDriverStatus(int uid,String Status){
        driverRepo.updateDriverStatus(uid, Status);
    }
    public int getDriverCount(){
        return driverRepo.countDriver();
    }
}
