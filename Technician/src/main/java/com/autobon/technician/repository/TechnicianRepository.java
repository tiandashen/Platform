package com.autobon.technician.repository;

import com.autobon.technician.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dave on 16/2/13.
 */
@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Integer> {
    Technician getByPhone(String phone);
}
