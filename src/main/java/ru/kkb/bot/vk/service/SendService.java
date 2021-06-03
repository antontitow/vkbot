package ru.kkb.bot.vk.service;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.kkb.bot.vk.component.ApiConnection;
import ru.kkb.bot.vk.dao.SendPackage;
import ru.kkb.bot.vk.config.BotApiConfig;

import java.util.List;

/**
 * @author Marchenko_DS in 31/05/2021 - 17:51
 */
@Service
public class SendService implements ISendService {



    private final ApiConnection apiConnection;
    private final BotLogicService botLogicService;

    @Autowired
    public SendService(ApiConnection apiConnection,BotLogicService botLogicService) {
        this.apiConnection = apiConnection;
        this.botLogicService = botLogicService;
    }


    /**
     * Send message to Api
     *
     * @param sendPackage
     * @return
     */
    @Override
    public ResponseEntity<String> sendMessage(SendPackage sendPackage) {
        return null;
    }

    /**
     *
     * @throws ClientException
     * @throws ApiException
     */
    @Override
    public void checkLongPool() throws ClientException, ApiException {
        MessagesGetLongPollHistoryQuery historyQuery = apiConnection.getVk().messages().getLongPollHistory(apiConnection.getActor()).ts(apiConnection.getTs());
        List<Message> messages = historyQuery.execute().getMessages().getItems();
        if (!messages.isEmpty()) {
            messages.forEach(message -> {
                System.out.println(message.toString());
                botLogicService.answer(message);

            });

            apiConnection.setTs(apiConnection.getVk().messages().getLongPollServer(apiConnection.getActor()).execute().getTs());
        }
    }


}
