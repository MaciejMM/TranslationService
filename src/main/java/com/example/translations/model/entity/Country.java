package com.example.translations.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(
        name = "country",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"countryCode", "localeCode"})
})
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 2)
    private String countryCode;

    @NotBlank
    @Valid
    @Column(length = 5)
    @Pattern(regexp = "^[a-z]{2}_[A-Z]{2}$",message = "Invalid localCode. Valid example - 'en_US'")
    private String localeCode;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "countryCode", referencedColumnName = "countryCode"),
            @JoinColumn(name = "localeCode", referencedColumnName = "localeCode"),
    })
    private List<Translation> translationList;

}
