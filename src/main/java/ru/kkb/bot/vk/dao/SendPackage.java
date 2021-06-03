package ru.kkb.bot.vk.dao;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marchenko_DS in 31/05/2021 - 17:51
 */
@Setter
@Getter
public class SendPackage {
    List<Message> messages = new ArrayList<>();
}
