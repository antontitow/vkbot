package ru.kkb.bot.vk.service;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.http.ResponseEntity;
import ru.kkb.bot.vk.dao.SendPackage;

/**
 * @author Marchenko_DS in 01/06/2021 - 11:19
 */
public interface ISendService {
    ResponseEntity<String> sendMessage(SendPackage sendPackage);

    void checkLongPool() throws ClientException, ApiException;
}
