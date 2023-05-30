package com.example.translations.repository;

import com.example.translations.model.entity.Country;
import com.example.translations.model.entity.Translation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private TranslationRepository translationRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void itShouldSaveTranslationRequestInDatabase() {
        //given
        List<Translation> translationList = new ArrayList<>();
        translationList.add(Translation.builder()
                .withCountryCode("DE")
                .withLocaleCode("de_DE")
                .withKey("checkout.thank_you_message")
                .withTranslationText("Thank you for your order")
                .withCreateDate(LocalDateTime.now())
                .withUpdateDate(null)
                .build());

        translationList.add(Translation.builder()
                .withCountryCode("ES")
                .withLocaleCode("es_ES")
                .withKey("checkout.thank_you_message")
                .withTranslationText("Gracias for your order")
                .withCreateDate(LocalDateTime.now())
                .withUpdateDate(null)
                .build());

        Country country = Country.builder()
                .withCountryCode("DE")
                .withLocaleCode("de_DE")
                .withTranslationList(translationList)
                .build();
        //when
        countryRepository.save(country);

        //then
        assertEquals(1,countryRepository.findAll().size());

    }




}