package com.example.translations.model.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;


public record TranslationRequest(
        @NotBlank
        String countryCode,
        @NotBlank
        String locale,
        List<Translation> translations,
        List<SearchWords> searchWords
) {

    public record Translation(
            @NotBlank
            String key,
            @NotBlank
            String translationText
    ) {
    }

    public record SearchWords(
            String word
    ) {
    }
}
