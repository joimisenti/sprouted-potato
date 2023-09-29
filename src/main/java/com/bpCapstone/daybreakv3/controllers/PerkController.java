package com.bpCapstone.daybreakv3.controllers;

import com.bpCapstone.daybreakv3.dtos.PerkDto;
import com.bpCapstone.daybreakv3.services.PerkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/perks")
public class PerkController {
    @Autowired
    private PerkService perkService;

    @GetMapping
    public List<PerkDto> getAllPerks() {
        return perkService.getAllPerks();
    }

    @GetMapping("/{perkId}")
    public Optional<PerkDto> getPerkById(@PathVariable Long perkId) {
        return perkService.getPerkById(perkId);
    }
}
