package com.isep.handimapper.util;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDto {

    @NotEmpty(message = "Le type n''est pas valide !")
    private String type;

    @NotEmpty(message = "Le statut n''est pas valide !")
    private String status;

}