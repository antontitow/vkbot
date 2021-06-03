package ru.kkb.bot.vk.dao;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Marchenko_DS in 31/05/2021 - 17:51
 */
@Setter
@Getter
public class Message {

    private String from;
    private String to;
    private String text;

}
