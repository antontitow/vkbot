package ru.kkb.bot.vk.component;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kkb.bot.vk.config.ApiConfig;

import javax.annotation.PostConstruct;


/**
 * Api Connection
 * @author Marchenko_DS in 03/06/2021 - 18:36
 */
@Component
@Getter
@Setter
public class ApiConnector {
    @Autowired
    private ApiConfig botApiConfig;

    private GroupActor actor;
    private VkApiClient vk;
    private Integer ts;

    @PostConstruct
    public void init() throws ClientException, ApiException {
        TransportClient transportClient = new HttpTransportClient();
        vk = new VkApiClient(transportClient);
        actor = new GroupActor(botApiConfig.getKey(), botApiConfig.getToken());
        ts = vk.messages().getLongPollServer(actor).execute().getTs();
    }
}
