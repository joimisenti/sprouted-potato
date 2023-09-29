package com.bpCapstone.daybreakv3.entities;

import com.bpCapstone.daybreakv3.dtos.PerkDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Perks")
public class Perk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(50)")
    private String name;

    @Column
    private String image;

    @Column(columnDefinition = "varchar(60)")
    private String teachable;

    @Column
    private String survivor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTeachable() {
        return teachable;
    }

    public void setTeachable(String teachable) {
        this.teachable = teachable;
    }

    public String getSurvivor() {
        return survivor;
    }

    public void setSurvivor(String survivor) {
        this.survivor = survivor;
    }

    // No-arguments Constructor
    public Perk() {
    }

    // All-arguments Constructor
    public Perk(Long id, String name, String image, String teachable, String survivor) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.teachable = teachable;
        this.survivor = survivor;
    }

    // Create the Many-to-Many relationship with the Loadouts table
    @ManyToMany(mappedBy = "perks")
    @JsonBackReference
    private Set<Loadout> loadouts = new HashSet<>();

    public Perk(PerkDto perkDto) {
        if (perkDto.getName() != null) {
            this.name = perkDto.getName();
        }
        if (perkDto.getImage() != null) {
            this.name = perkDto.getImage();
        }
        if (perkDto.getTeachable() != null) {
            this.name = perkDto.getTeachable();
        }
        if (perkDto.getSurvivor() != null) {
            this.survivor = perkDto.getSurvivor();
        }
    }
}
