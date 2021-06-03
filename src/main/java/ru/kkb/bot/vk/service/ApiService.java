package ru.kkb.bot.vk.service;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.kkb.bot.vk.component.ApiConnector;
import ru.kkb.bot.vk.component.LogicComponent;
import ru.kkb.bot.vk.dao.SendPackage;

import java.util.List;

/**
 * Api vk boot service
 * @author Marchenko_DS in 31/05/2021 - 17:51
 */
@Service
public class ApiService implements IApiService {

    private final ApiConnector apiConnection;
    private final LogicComponent logicComponent;

    @Autowired
    public ApiService(ApiConnector apiConnection, LogicComponent botLogicService) {
        this.apiConnection = apiConnection;
        this.logicComponent = botLogicService;
    }


    /**
     * Send message to Api
     *
     * @param sendPackage
     * @return
     */
    @SneakyThrows
    @Override
    public ResponseEntity<String> sendMessage(SendPackage sendPackage) {
        apiConnection.getVk().messages()
                .send(apiConnection.getActor())
                .message(sendPackage.getText())
                .userId(sendPackage.getUserId())
                .randomId(logicComponent.getRandomId())
                .execute();
        return ResponseEntity.ok("OK");
    }

    /**
     * Send message to Api
     *
     * @param sendPackage
     * @return
     */
    @SneakyThrows
    @Override
    public ResponseEntity<String> sendMessageWithButtons(SendPackage sendPackage, List<String> strings) {
        apiConnection.getVk().messages()
                .send(apiConnection.getActor())
                .message(sendPackage.getText())
                .userId(sendPackage.getUserId())
                .randomId(logicComponent.getRandomId())
                .keyboard(logicComponent.getKeyboard(strings)).execute();
        return ResponseEntity.ok("OK");
    }


    /**
     * Check LongPoll
     * @throws ClientException
     * @throws ApiException
     */
    @Override
    public void checkLongPoll() throws ClientException, ApiException {
        MessagesGetLongPollHistoryQuery historyQuery = apiConnection.getVk().messages().getLongPollHistory(apiConnection.getActor()).ts(apiConnection.getTs());
        List<Message> messages = historyQuery.execute().getMessages().getItems();
        if (!messages.isEmpty()) {
            messages.forEach(message -> {
                System.out.println(message.toString());
                sendMessage(SendPackage.builder().userId(message.getFromId()).text(logicComponent.answer(message)).build());

            });
            apiConnection.setTs(apiConnection.getVk().messages().getLongPollServer(apiConnection.getActor()).execute().getTs());
        }
    }


}
