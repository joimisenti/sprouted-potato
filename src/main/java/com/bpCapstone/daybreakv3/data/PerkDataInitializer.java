//package com.bpCapstone.daybreakv3.data;
//
//import com.bpCapstone.daybreakv3.entities.Perk;
//import com.bpCapstone.daybreakv3.repositories.PerkRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PerkDataInitializer {
//
//    // Inject the Perk repository to populate with data
//    // Useful for manual insertions of data by me
//    @Autowired
//    private PerkRepository perkRepository;
//
//    // Method to save instances of Perk entity using the repository
//    @PostConstruct
//    public void initializePerkData() {
//        // Create and save instances of Perk
//        Perk perk1 = new Perk();
//        perk1.setName("New Perk 1");
//        perkRepository.save(perk1);
//
//        Perk perk2 = new Perk();
//        perk2.setName("New Perk 2");
//        perkRepository.save(perk2);
//    }
//}
