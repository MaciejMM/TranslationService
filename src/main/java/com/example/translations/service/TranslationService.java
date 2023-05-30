package com.example.translations.service;

import com.example.translations.exception.ApiRequestException;
import com.example.translations.model.entity.Country;
import com.example.translations.model.entity.Translation;
import com.example.translations.model.request.TranslationRequest;
import com.example.translations.model.response.TranslationResponse;
import com.example.translations.repository.CountryRepository;
import com.example.translations.repository.TranslationRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class TranslationService {

    private static final String EMPTY_STRING = "";
    private final CountryRepository countryRepository;
    private final TranslationRepository translationRepository;

    public TranslationService(final CountryRepository countryRepository, final TranslationRepository translationRepository) {
        this.countryRepository = countryRepository;
        this.translationRepository = translationRepository;
    }


    public List<Translation> getTranslations(final TranslationRequest translationRequest) {
        Country countries = getCountries(translationRequest);
        List<String> collect = getIncludedWords(translationRequest);
        return countries.getTranslationList()
                .stream()
                .filter(word -> isWordIncluded(collect, word))
                .toList();
    }


    public List<TranslationResponse> createTranslations(final TranslationRequest translationRequest) {
        getCountries(translationRequest);
        List<Translation> translations = mapTranslationList(translationRequest);
        translationRepository.saveAll(translations);
        return mapTranslationResponse(translations);

    }


    public void updateTranslations(final Long id, final TranslationRequest translationRequest) {
        Translation translationRecord = getCountries(translationRequest)
                .getTranslationList()
                .stream()
                .filter(translation -> translation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException("Record for given id was not found!"));
        translationRepository.save(translationBuilder(translationRequest, translationRecord));
    }


    public void deleteTranslations(final Long id) {
        Translation translation = translationRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Resource for id " + id + " is not existing"));
        translationRepository.deleteById(translation.getId());
    }

    private List<String> getIncludedWords(final TranslationRequest translationRequest) {
        return Optional.ofNullable(translationRequest.searchWords())
                .orElse(Collections.emptyList())
                .stream()
                .map(TranslationRequest.SearchWords::word)
                .filter(word -> !word.equals(EMPTY_STRING))
                .collect(toList());
    }

    private boolean isWordIncluded(final List<String> collect, final Translation word) {
        if (collect.isEmpty()) {
            return true;
        }
        return collect.stream()
                .anyMatch(collectedWord -> word.getTranslationText().contains(collectedWord) || word.getKey().contains(collectedWord));
    }

    private Country getCountries(final TranslationRequest translationRequest) {
        return countryRepository
                .findByCountryCodeAndLocaleCode(translationRequest.countryCode(), translationRequest.locale())
                .orElseThrow(() -> new ApiRequestException("Record with countryCode " + translationRequest.countryCode() + " and localeCode " + translationRequest.locale() + " not found."));
    }


    private List<TranslationResponse> mapTranslationResponse(final List<Translation> translations) {
        return translations
                .stream()
                .map(this::mapTranslationResponse)
                .toList();

    }

    private TranslationResponse mapTranslationResponse(final Translation translation) {
        return new TranslationResponse(
                translation.getCountryCode(),
                translation.getLocaleCode(),
                translation.getKey(),
                translation.getTranslationText(),
                translation.getCreateDate(),
                translation.getUpdateDate());
    }

    private List<Translation> mapTranslationList(final TranslationRequest translationRequest) {
        return translationRequest
                .translations().stream()
                .map(translation -> buildTranslation(translationRequest, translation))
                .collect(toList());
    }
    private Translation buildTranslation(TranslationRequest translationRequest, TranslationRequest.Translation translation) {
        return Translation
                .builder()
                .withCountryCode(translationRequest.countryCode())
                .withLocaleCode(translationRequest.locale())
                .withKey(translation.key())
                .withTranslationText(translation.translationText())
                .withCreateDate(LocalDateTime.now())
                .build();
    }

    private Translation translationBuilder(TranslationRequest translationRequest, Translation translationRecord) {
        String translationText = Optional.ofNullable(getTranslationDetails(translationRequest))
                .map(TranslationRequest.Translation::translationText)
                .orElse(translationRecord.getTranslationText());
        String translationKey = Optional.ofNullable(getTranslationDetails(translationRequest))
                .map(TranslationRequest.Translation::key)
                .orElse(translationRecord.getKey());

        return Translation
                .builder()
                .withId(translationRecord.getId())
                .withCountryCode(translationRecord.getCountryCode())
                .withLocaleCode(translationRecord.getLocaleCode())
                .withKey(translationKey)
                .withTranslationText(translationText)
                .withCreateDate(translationRecord.getCreateDate())
                .withUpdateDate(LocalDateTime.now())
                .build();
    }


    private TranslationRequest.Translation getTranslationDetails(final TranslationRequest translationRequest) {
        return translationRequest.translations().get(0);
    }
}
