package ru.kkb.bot.vk.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kkb.bot.vk.audit.AuditLog;
import ru.kkb.bot.vk.service.SendService;
import ru.kkb.bot.vk.dao.SendPackage;


/**
 * Rest Endpoint for api bot
 * @author Marchenko_DS in 31/05/2021 - 17:51
 */

@RestController
@Log
@RequestMapping("/sms")
@Api(value = "Send message controller")

public class SendController {

    @Autowired
    SendService sendService;

    @PostMapping("/send")
    @ApiOperation(value = "Send Message", notes = "Send Message controller", response = String.class)
    public ResponseEntity<String> send(@RequestBody SendPackage sendPackage) throws Exception {
        return sendService.sendMessage(sendPackage);
    }

}
