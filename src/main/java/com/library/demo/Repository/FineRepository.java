package com.library.demo.Repository;

import com.library.demo.Entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine,Integer> {

    @Query("SELECT f from Fine f where f.user.id = :userId")
    List<Fine> findFineByUserId(Long userId);
}
