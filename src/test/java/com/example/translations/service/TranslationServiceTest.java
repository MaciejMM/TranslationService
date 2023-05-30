package com.example.translations.service;

import com.example.translations.model.entity.Country;
import com.example.translations.model.entity.Translation;
import com.example.translations.repository.CountryRepository;
import com.example.translations.repository.TranslationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class TranslationServiceTests {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private TranslationRepository translationRepository;

    private TranslationService underTest;
    private Country country;

    @BeforeEach
    void setUp() {
        underTest = new TranslationService(countryRepository, translationRepository);
        List<Translation> translationList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        translationList.add(Translation.builder()
                .withCountryCode("DE")
                .withLocaleCode("de_DE")
                .withKey("checkout.thank_you_message")
                .withTranslationText("Thank you for your order")
                .withCreateDate(now)
                .withUpdateDate(null)
                .build());
         country = Country.builder().withCountryCode("DE").withLocaleCode("de_DE").withTranslationList(translationList).build();

    }


    @Test
    void itShouldCreateTranslations() {

        //when
        countryRepository.save(country);

        //then
        ArgumentCaptor<Country> countryArgumentCaptor = ArgumentCaptor.forClass(Country.class);
        verify(countryRepository)
                .save(countryArgumentCaptor.capture());
        Country capturedCountry = countryArgumentCaptor.getValue();
        assertThat(capturedCountry).isEqualTo(country);
        assertNotNull(capturedCountry);
        assertNotNull(capturedCountry.getTranslationList());
    }

    @Test
    @Disabled
    void itShouldUpdateTranslations() {
        //given

        //when

        //then

    }

    @Test
    @Disabled
    void itShouldDeleteTranslations() {
        //given

        //when

        //then

    }
}