package com.example.translations.controller;

import com.example.translations.model.entity.Translation;
import com.example.translations.model.request.TranslationRequest;
import com.example.translations.model.response.TranslationResponse;
import com.example.translations.service.TranslationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/v1/translations")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }


    @GetMapping
    public ResponseEntity<Object> getTranslations(@Valid @RequestBody TranslationRequest translationRequest) {
        List<Translation> translations = translationService.getTranslations(translationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(translations);
    }


    @GetMapping("{id}")
    public ResponseEntity<Object> getTranslationById(@Valid @RequestBody TranslationRequest translationRequest,@PathVariable final Long id) {
        List<Translation> translations = translationService.getTranslations(translationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(translations);
    }


    @PostMapping()
    public ResponseEntity<Object> createTranslations(@Valid @RequestBody TranslationRequest translationRequest) {
        List<TranslationResponse> translationList = translationService.createTranslations(translationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(translationList);
    }


    @PutMapping("{id}")
    public ResponseEntity<Object> updateTranslations(
            @Valid @RequestBody final TranslationRequest translationRequest,
            @PathVariable final Long id) {
        translationService.updateTranslations(id, translationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Resource for id: " + id + " was successfully updated."
        ));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteTranslation(@PathVariable Long id) {
        translationService.deleteTranslations(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Resource for id: " + id + " was successfully deleted."
        ));
    }

}
