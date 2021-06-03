package ru.kkb.bot.vk.component;

import com.vk.api.sdk.objects.messages.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Marchenko_DS in 03/06/2021 - 18:48
 */
@Component
public class LogicComponent {
    private Random random = new Random();

    /**
     * Get Random id
     * @return
     */
    public Integer getRandomId(){
        return random.nextInt(10000);
    }


    /**
     * Answer logic
     * @param message
     * @return
     */
    public String answer(Message message) {
        if (message.getText().equals("Привет")) {
            return "Привет!";
        } else if (message.getText().equals("Кнопки")) {
            return "А вот и они";
        }
        return "";
    }

    /**
     * Get keyBoard
     * @param labels
     * @return
     */
    public Keyboard getKeyboard(List<String> labels) {
        Keyboard keyboard = new Keyboard();
        List<List<KeyboardButton>> allKey = new ArrayList<>();
        List<KeyboardButton> line1 = new ArrayList<>();
        labels.forEach(s -> {
            line1.add(new KeyboardButton()
                    .setAction(new KeyboardButtonAction().setLabel(s).setType(KeyboardButtonActionType.TEXT))
                    .setColor(KeyboardButtonColor.POSITIVE));

        });
        allKey.add(line1);
        keyboard.setButtons(allKey);
        return keyboard;
    }
}
