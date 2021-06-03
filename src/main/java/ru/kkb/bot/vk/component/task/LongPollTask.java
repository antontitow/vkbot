package ru.kkb.bot.vk.component.task;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.kkb.bot.vk.service.ISendService;

/**
 * @author Marchenko_DS in 31/05/2021 - 17:51
 */
@Component
@Log
public class LongPollTask extends BaseTask {

    private final static String TASK_NAME = "LongPollTask";


    @Autowired
    private final ISendService sendService;

    public LongPollTask(ISendService sendService) {
        this.sendService = sendService;
    }

    @ConditionalOnProperty(value = "scheduling.enabled", havingValue = "true", matchIfMissing = true)
    @Scheduled(fixedDelay = 500)
    public void scheduleTask() throws ClientException, ApiException {
        sendService.checkLongPool();
    }

    @Override
    String getTaskName() {
        return TASK_NAME;
    }
}
