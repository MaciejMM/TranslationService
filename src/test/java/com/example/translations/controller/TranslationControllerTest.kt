package com.example.translations.controller

import com.example.translations.model.request.TranslationRequest
import com.example.translations.model.response.TranslationResponse
import com.example.translations.service.TranslationService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@WebMvcTest(TranslationController::class)
internal class TranslationControllerTest {


    @Autowired
    lateinit var mockMvc: MockMvc

    private val objectMapper = ObjectMapper()

    @MockBean
    lateinit var translationService: TranslationService


    @Test
    fun `should return 200 response`() {
        //given
        val translationRequest = TranslationRequest(
            "FR",
            "fr_FR",
            listOf(
                TranslationRequest.Translation(
                    "welcome.message",
                    "Bonjour"
                ),
                TranslationRequest.Translation(
                    "checkout.message",
                    "Hello"
                )
            ),
            emptyList()
        )

        val response = listOf(
            TranslationResponse("FR", "fr_FR", "welcome.message", "Bonjour!", LocalDateTime.now(), null),
            TranslationResponse("FR", "fr_FR", "checkout.message", "Hello", LocalDateTime.now(), null)
        )

        //when
        `when`(translationService.createTranslations(translationRequest)).thenReturn(response)

        //then
        mockMvc.perform(
            post("/api/v1/translations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(translationRequest))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

    }



}


