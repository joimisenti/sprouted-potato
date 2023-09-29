package com.bpCapstone.daybreakv3.controllers;

//import com.bpCapstone.daybreakv3.dtos.LoadoutCreateRequest;
import com.bpCapstone.daybreakv3.dtos.LoadoutDto;
import com.bpCapstone.daybreakv3.dtos.PerkDto;
import com.bpCapstone.daybreakv3.entities.Loadout;
import com.bpCapstone.daybreakv3.entities.Perk;
import com.bpCapstone.daybreakv3.services.LoadoutService;
import com.bpCapstone.daybreakv3.services.PerkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/loadouts")
public class LoadoutController {
    @Autowired
    private LoadoutService loadoutService;

    @Autowired
    private PerkService perkService;

    @GetMapping("/user/{userId}")
    public List<LoadoutDto> getLoadoutsByUser(Long userId) {
        return loadoutService.getAllLoadoutsByUserId(userId);
    }

    @PostMapping("/user/{userId}")
    public void addLoadout(@RequestBody LoadoutDto loadoutDto, @PathVariable Long userId){
        loadoutService.addLoadout(loadoutDto, userId);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createLoadout(@RequestBody LoadoutDto loadoutDto) {
        // Convert the selected perk IDs to Perk entities
        List<Perk> selectedPerks = perkService.getPerksByIds(loadoutDto.getPerksIds());

//        System.out.println(selectedPerks);
//
        // Create a new Loadout entity
        Loadout loadout = new Loadout();
        loadout.setPerks(new HashSet<>(selectedPerks));

        // Save the Loadout to the database
        loadoutService.saveLoadout(loadout);

        return ResponseEntity.ok("Loadout created successfully");
    }

//    @PostMapping("/create")
//    public ResponseEntity<?> createLoadoutWithPerks(@RequestBody LoadoutCreateRequest request) {
//        // Extract user ID and selected perk IDs from the request
//        Long userId = request.getUserId();
//        List<Long> selectedPerkIds = request.getSelectedPerkIds();
//
//        System.out.println("User ID: " + userId);
//        System.out.println("Selected Perk Ids: " + selectedPerkIds);
//
//        // Create a new loadout and associate selected perks
//        Long newLoadout = loadoutService.createLoadoutWithPerks(userId, selectedPerkIds);
//
//        // Return the newly created loadout
//        return ResponseEntity.ok(newLoadout);
//    }

    @DeleteMapping("/{loadoutId}")
    public void deleteLoadoutById(@PathVariable Long loadoutId) {
        loadoutService.deleteLoadoutById(loadoutId);
    }
    @PutMapping
    public void updateLoadout(@RequestBody LoadoutDto loadoutDto) {
        loadoutService.updateLoadoutById((loadoutDto));
    }

    @GetMapping("/{loadoutId}")
    public Optional<LoadoutDto> getLoadoutById(@PathVariable Long loadoutId) {
        return loadoutService.getLoadoutById(loadoutId);
    }

    @GetMapping
    public List<LoadoutDto> getAllLoadouts() {
        return loadoutService.getAllLoadouts();
    }
}
