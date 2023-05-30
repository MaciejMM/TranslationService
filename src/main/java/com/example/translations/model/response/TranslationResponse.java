package com.example.translations.model.response;

import java.time.LocalDateTime;

public record TranslationResponse (
        String countryCode,
        String localeCode,
        String key,
        String translationText,
        LocalDateTime createDate,
        LocalDateTime updateDate
) {
}
