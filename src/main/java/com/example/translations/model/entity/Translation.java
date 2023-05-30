package com.example.translations.model.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(
        name = "translation",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"countryCode", "localeCode", "key"})
        })
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String countryCode;

    @NotBlank
    private String localeCode;

    @NotBlank
    private String key;

    @NotBlank
    private String translationText;

    @NotNull
    private LocalDateTime createDate;

    @Nullable
    private LocalDateTime updateDate;

}
