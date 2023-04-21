package com.example.AdminPanel.Repo;

import com.example.AdminPanel.Entity.Driver;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DriverRepo extends JpaRepository<Driver,Integer> {
//    @Query(value = "Select * from Driver driver ORDER BY :orderBy ASC limit :limit offset :skip", nativeQuery = true)
    @Query("Select driver from Driver driver")
    List<Driver> getDriverList(Pageable pageable);

    Driver findDriverById(int id);
    int deleteById(int id);
    List<Driver> findDriverByEmail(String email);
    @Query("Select driver from Driver driver where driver.firstName = :email OR driver.lastName = :email OR driver.email = :email OR driver.Status = :email")
    List<Driver> findDriverByCred(String email);
    @Transactional
    @Modifying
    @Query("update Driver u set u.softDelete=?2 where u.id=?1")
    void softDeleteDriver(int id,boolean flg);
    @Transactional
    @Modifying
    @Query("update Driver u set u.Status=?2 where u.id=?1")
    void updateDriverStatus(int id,String Status);
    @Query(value = "Select count(*) from Driver driver",nativeQuery = true)
    int countDriver();
}
