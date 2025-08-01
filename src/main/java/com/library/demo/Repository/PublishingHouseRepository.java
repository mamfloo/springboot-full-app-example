package com.library.demo.Repository;

import com.library.demo.Entity.PublishingHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublishingHouseRepository extends JpaRepository<PublishingHouse, Long> {

    Optional<PublishingHouse> findByName(String name);
}
