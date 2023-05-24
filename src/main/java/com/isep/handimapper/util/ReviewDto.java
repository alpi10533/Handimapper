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
public class ReviewDto {
    private int idReview;

    @NotEmpty(message = "Le commentaire n'est pas valide!")
    private String review;
}
