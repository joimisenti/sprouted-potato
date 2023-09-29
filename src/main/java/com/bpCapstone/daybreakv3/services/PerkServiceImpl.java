package com.bpCapstone.daybreakv3.services;

import com.bpCapstone.daybreakv3.dtos.PerkDto;
import com.bpCapstone.daybreakv3.entities.Perk;
import com.bpCapstone.daybreakv3.repositories.LoadoutRepository;
import com.bpCapstone.daybreakv3.repositories.PerkRepository;
import com.bpCapstone.daybreakv3.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PerkServiceImpl implements PerkService {

    @Autowired
    private PerkRepository perkRepository;
    @Autowired
    private LoadoutRepository loadoutRepository;
    @Autowired
    private UserRepository userRepository;

    // Get all Perks
    @Override
    public List<PerkDto> getAllPerks() {
        List<Perk> perks = perkRepository.findAll();
        List<PerkDto> perkDtos = new ArrayList<>();
        for (Perk perk : perks) {
            perkDtos.add(new PerkDto(perk));
        }
        return perkDtos;
    }

    // Get a Perk by the Perk id
    @Override
    public Optional<PerkDto> getPerkById(Long perkId) {
        Optional<Perk> perkOptional = perkRepository.findById(perkId);
        if (perkOptional.isPresent()) {
            return Optional.of(new PerkDto(perkOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<Perk> getPerksByIds(List<Long> perkIds) {
        return perkRepository.findAllById(perkIds);
    }

    // Save a Perk being loaded from JSON file
    @Transactional
    public void savePerk(Perk perk) {
        perkRepository.save(perk);
    }
}
