package com.example.translations.repository;

import com.example.translations.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByCountryCodeAndLocaleCode(String countryCode, String localeCode);


}
