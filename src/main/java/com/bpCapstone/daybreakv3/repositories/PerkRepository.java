package com.bpCapstone.daybreakv3.repositories;

import com.bpCapstone.daybreakv3.entities.Perk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerkRepository extends JpaRepository<Perk, Long> {

}
