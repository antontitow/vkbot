package ru.kkb.bot.vk.dao;

import com.vk.api.sdk.objects.messages.Message;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marchenko_DS in 31/05/2021 - 17:51
 */
@Setter
@Getter
@Builder
public class SendPackage {
    private Integer userId;
    private String text;
}
