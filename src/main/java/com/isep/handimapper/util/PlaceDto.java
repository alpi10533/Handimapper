package com.isep.handimapper.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {

    private String starMean;

    private List<ReviewDto> reviews;

    private List<EquipmentDto> equipments;

    private String googleDetails;

}
