package com.bpCapstone.daybreakv3.dtos;

import com.bpCapstone.daybreakv3.entities.Loadout;
import com.bpCapstone.daybreakv3.entities.Perk;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadoutDto implements Serializable {
    private Long id;
    private String buildType;
    private Set<PerkDto> perkDtoSet = new HashSet<>();
    private List<Long> perksIds;
    private String summary;
    private UserDto userDto;

    public LoadoutDto(Loadout loadout) {
        if (loadout.getId() != null) {
            this.id = loadout.getId();
        }
        if (loadout.getBuildType() != null) {
            this.buildType = loadout.getBuildType();
        }
        if (loadout.getSummary() != null) {
            this.summary = loadout.getSummary();
        }
        // Populate perkIds from associated Perk entities
        if (loadout.getPerks() != null) {
            this.perksIds = loadout.getPerks().stream()
                    .map(Perk::getId)
                    .collect(Collectors.toList());
        }
    }

}
