package com.example.translations.service;

import com.example.translations.model.request.TranslationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class FileService {

    public String uploadFile(){
        return "File uploaded!";
    }

}
