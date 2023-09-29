package com.bpCapstone.daybreakv3.dtos;

import com.bpCapstone.daybreakv3.entities.Perk;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerkDto implements Serializable {

    private Long id;
    private String name;
    private String image;
    private String teachable;
    private String survivor;

    public PerkDto(Perk perk) {
        if (perk.getId() != null) {
            this.id = perk.getId();
        }
        if (perk.getName() != null) {
            this.name = perk.getName();
        }
        if (perk.getImage() != null) {
            this.image = perk.getImage();
        }
        if (perk.getTeachable() != null) {
            this.teachable = perk.getTeachable();
        }
        if (perk.getSurvivor() != null) {
            this.survivor = perk.getSurvivor();
        }
    }
}
