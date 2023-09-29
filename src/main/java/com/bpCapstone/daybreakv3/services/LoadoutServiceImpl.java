package com.bpCapstone.daybreakv3.services;

import com.bpCapstone.daybreakv3.dtos.LoadoutDto;
import com.bpCapstone.daybreakv3.entities.Loadout;
import com.bpCapstone.daybreakv3.entities.Perk;
import com.bpCapstone.daybreakv3.entities.User;
import com.bpCapstone.daybreakv3.repositories.LoadoutRepository;
import com.bpCapstone.daybreakv3.repositories.PerkRepository;
import com.bpCapstone.daybreakv3.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoadoutServiceImpl implements LoadoutService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoadoutRepository loadoutRepository;

    @Autowired
    private PerkRepository perkRepository;

//    @Override
//    @Transactional
//    public Long createLoadoutWithPerks(Long userId, List<Long> selectedPerkIds) {
//        // Retrieve the user by ID (validate user existence)
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new org.springframework.data.rest.webmvc.ResourceNotFoundException("User not found"));
//
//        System.out.println("Selected Perk IDs: " + selectedPerkIds);
//        // Retrieve the selected perks by their IDs
//        List<Perk> selectedPerks = perkRepository.findAllById(selectedPerkIds);
//        System.out.println("Selected Perk IDs: " + selectedPerkIds);
//
//        // Create a new loadout
//        Loadout loadout = new Loadout();
//        loadout.setUser(user);
//        loadout.setPerks(new HashSet<>(selectedPerks));
//
//        // Save the loadout to the database
//        loadoutRepository.save(loadout);
//
//        // Retrieve the perk entities by perkIds
////        List<Perk> perks = perkRepository.findAllById(perkIds);
//
//        // Associate the selected perks with the loadout
////        loadout.setPerks(new HashSet<>(perks));
//
//        // Save the updated loadout
////        loadoutRepository.save(loadout);
//
//        // Return the ID of the newly created loadout
//        return loadout.getId();
//    }

    // Creaete a loadout with perks sent by user from the frontend
    @Override
    @Transactional
    public void createLoadout(LoadoutDto loadoutDto, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Loadout loadout = new Loadout(loadoutDto);
        userOptional.ifPresent(loadout::setUser);
        loadoutRepository.saveAndFlush(loadout);
    }

    @Override
    public Loadout saveLoadout(Loadout loadout){
        return loadoutRepository.save(loadout);
    }


    // Add a loadout to the user's list
    @Override
    @Transactional
    public void addLoadout(LoadoutDto loadoutDto, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Loadout loadout = new Loadout(loadoutDto);
        userOptional.ifPresent(loadout::setUser);
        loadoutRepository.saveAndFlush(loadout);
    }

    // Delete a loadout from the user's list
    @Override
    @Transactional
    public void deleteLoadoutById(Long loadoutId) {
        Optional<Loadout> loadoutOptional = loadoutRepository.findById(loadoutId);
        loadoutOptional.ifPresent(loadout -> loadoutRepository.delete(loadout));
    }

    // Update a loadout in the user's list
    @Override
    @Transactional
    public void updateLoadoutById(LoadoutDto loadoutDto) {
        Optional<Loadout> loadoutOptional = loadoutRepository.findById(loadoutDto.getId());
        loadoutOptional.ifPresent(loadout -> {
            loadout.setBuildType(loadoutDto.getBuildType());
//            loadout.setPerks(loadoutDto.getPerks());
            loadout.setSummary(loadoutDto.getSummary());
            loadoutRepository.saveAndFlush(loadout);
        });
    }

    // Finding all loadouts by a user
    @Override
    public List<LoadoutDto> getAllLoadoutsByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<Loadout> loadoutList = loadoutRepository.findAllByUserEquals(userOptional.get());
            return loadoutList.stream().map(loadout -> new LoadoutDto(loadout)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    // Getting a Loadout by the loadout Id
    @Override
    public Optional<LoadoutDto> getLoadoutById(Long loadoutId) {
        Optional<Loadout> loadoutOptional = loadoutRepository.findById(loadoutId);
        if (loadoutOptional.isPresent()) {
            return Optional.of(new LoadoutDto(loadoutOptional.get()));
        }
        return Optional.empty();
    }
}
