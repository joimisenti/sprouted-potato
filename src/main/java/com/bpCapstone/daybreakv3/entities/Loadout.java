package com.bpCapstone.daybreakv3.entities;

import com.bpCapstone.daybreakv3.dtos.LoadoutDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Loadouts")
public class Loadout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(40)")
    private String buildType;


    // Create the Many-to-Many relationship with the Loadout_Perks Association table
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Loadout_Perks",
            joinColumns = { @JoinColumn(name = "loadout_id") },
            inverseJoinColumns = { @JoinColumn(name = "perk_id") }
    )
    private Set<Perk> perks = new HashSet<>();

    @Column(columnDefinition = "text")
    private String summary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuildType() {
        return buildType;
    }

    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }

    public Set<Perk> getPerks() {
        return perks;
    }

    public void setPerks(Set<Perk> perks) {
        this.perks = perks;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    // No-arguments Constructor
    public Loadout() {
    }

    // All-arguments Constructor

    public Loadout(Long id, String buildType, Set<Perk> perks, String summary, User user) {
        this.id = id;
        this.buildType = buildType;
        this.perks = perks;
        this.summary = summary;
        this.user = user;
    }

    // Create the Many-to-One relationship to the Users table
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Loadout(LoadoutDto loadoutDto) {
        if (loadoutDto.getBuildType() != null) {
            this.buildType = loadoutDto.getBuildType();
        }
        if (loadoutDto.getSummary() != null) {
            this.summary = loadoutDto.getSummary();
        }
    }

}
