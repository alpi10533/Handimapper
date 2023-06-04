package com.isep.handimapper.business;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "place")
public class PlaceEntity {

    @Id
    @Column(name = "id_place", unique = true, nullable = false)
    private String idPlace;

}
