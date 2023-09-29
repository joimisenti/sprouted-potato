package com.bpCapstone.daybreakv3.repositories;

import com.bpCapstone.daybreakv3.entities.Loadout;
import com.bpCapstone.daybreakv3.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoadoutRepository extends JpaRepository<Loadout, Long> {
    List<Loadout> findAllByUserEquals(User user);
}