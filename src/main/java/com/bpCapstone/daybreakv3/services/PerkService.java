package com.bpCapstone.daybreakv3.services;

import com.bpCapstone.daybreakv3.dtos.PerkDto;
import com.bpCapstone.daybreakv3.entities.Perk;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PerkService {
    // Get all Perks
    List<PerkDto> getAllPerks();

    // Get a Perk by the Perk id
    Optional<PerkDto> getPerkById(Long perkId);

    // Get a list of Perks by their Perk IDs
    List<Perk> getPerksByIds(List<Long> perkIds);

    @Transactional
    void savePerk(Perk perk);
}
