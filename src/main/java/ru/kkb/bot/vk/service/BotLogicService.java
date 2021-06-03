package ru.kkb.bot.vk.service;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kkb.bot.vk.component.ApiConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Marchenko_DS in 03/06/2021 - 18:48
 */
@Service
public class BotLogicService {

    private final ApiConnection apiConnection;

    @Autowired
    public BotLogicService(ApiConnection apiConnection) {
        this.apiConnection = apiConnection;
    }

    public  void answer(Message message){
        try {
            if (message.getText().equals("Привет")) {
                apiConnection.getVk().messages().send(apiConnection.getActor()).message("Привет!").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
          } else if (message.getText().equals("Кнопки")) {
                apiConnection.getVk().messages().send(apiConnection.getActor()).message("А вот и они").userId(message.getFromId()).randomId(random.nextInt(10000)).keyboard(keyboard).execute();
            }
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }


    private Random random;
    private Keyboard keyboard;

    /**
     *
     */
    private void getKey() {
        random = new Random();
        keyboard = new Keyboard();

        List<List<KeyboardButton>> allKey = new ArrayList<>();
        List<KeyboardButton> line1 = new ArrayList<>();
        line1.add(new KeyboardButton().setAction(new KeyboardButtonAction().setLabel("Привет").setType(KeyboardButtonActionType.TEXT)).setColor(KeyboardButtonColor.POSITIVE));
        allKey.add(line1);
        keyboard.setButtons(allKey);
    }
}
