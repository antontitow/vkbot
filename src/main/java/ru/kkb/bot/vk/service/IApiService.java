package ru.kkb.bot.vk.service;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import ru.kkb.bot.vk.dao.SendPackage;

import java.util.List;

/**
 * Api vk boot service interface
 * @author Marchenko_DS in 01/06/2021 - 11:19
 */
public interface IApiService {

    @SneakyThrows
    ResponseEntity<String> sendMessage(SendPackage sendPackage);

    @SneakyThrows
    ResponseEntity<String> sendMessageWithButtons(SendPackage sendPackage, List<String> strings);

    void checkLongPoll() throws ClientException, ApiException;
}
