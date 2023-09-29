package com.bpCapstone.daybreakv3.services;

import com.bpCapstone.daybreakv3.dtos.LoadoutDto;
import com.bpCapstone.daybreakv3.entities.Loadout;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LoadoutService {
    // Add a loadout to the user's list
    @Transactional
    void addLoadout(LoadoutDto loadoutDto, Long userId);

    // Create a loadout by adding perks
    @Transactional
    void createLoadout(LoadoutDto loadoutDto, Long userId);
//    Long createLoadoutWithPerks(Long userId, List<Long> selectedPerkIds);

    // Delete a loadout from the user's list
    @Transactional
    void deleteLoadoutById(Long loadoutId);

    // Update a loadout in the user's list
    @Transactional
    void updateLoadoutById(LoadoutDto loadoutDto);

    // Finding all loadouts by a user
    List<LoadoutDto> getAllLoadoutsByUserId(Long userId);

    // Getting a Loadout by the loadout Id
    Optional<LoadoutDto> getLoadoutById(Long loadoutId);

    Loadout saveLoadout(Loadout loadout);

    List<LoadoutDto> getAllLoadouts();
}
