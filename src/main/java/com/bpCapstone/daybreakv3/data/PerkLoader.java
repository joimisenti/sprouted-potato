package com.bpCapstone.daybreakv3.data;

import com.bpCapstone.daybreakv3.entities.Perk;
import com.bpCapstone.daybreakv3.services.PerkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class PerkLoader implements CommandLineRunner {

    @Autowired
    private PerkService perkService;
    public void run(String... args) throws Exception{
        //Create instance of PerkService

        // Implement the logic to load data from JSON files and save to database
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Perk[] perks = objectMapper.readValue(new File("/Users/jmisenti/Desktop/Java-Specs/daybreakBackup/src/main/java/com/bpCapstone/daybreakBackup/assets/SurvivorPerkArray.json"), Perk[].class);

            // Iterate through loaded perks and save them to the database
            for (Perk perk: perks) {
                perkService.savePerk(perk);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

