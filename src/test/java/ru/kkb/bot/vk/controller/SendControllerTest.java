package ru.kkb.bot.vk.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.kkb.bot.vk.config.ApiConfig;
import ru.kkb.bot.vk.dao.SendPackage;
import ru.kkb.bot.vk.service.IApiService;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test send controller
 * @author Marchenko_DS in 01/06/2021 - 13:28
 */
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"ru.kkb.bot.vk.service"})
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SendControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    //@MockBean //дeргает подмену сервиса
    @Autowired //Дергает реальную реализацию
    private IApiService service;

    @Autowired
    private ApiConfig botApiConfig;

    /**
     * {"result":[{"code":"OK","messageId":"3617113923859110273","segmentsId":null}]}
     */
    @Test
    @Description("Test send message")
    public void testSend() throws Exception {
        mvc.perform(post("/sms/send/")
                .content( objectMapper.writeValueAsString(getTestMessage()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result[0].code").value("OK"));
    }


    /**
     *Test Message
     * @return
     */
    private SendPackage getTestMessage (){
        SendPackage sendPackageTest = new SendPackage();
        List<Message> messageList = new ArrayList<>();
        messageList.add(botApiConfig.getMessage());
        sendPackageTest.setMessages(messageList);
        return sendPackageTest;
    }
}