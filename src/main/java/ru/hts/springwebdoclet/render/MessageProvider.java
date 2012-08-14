package ru.hts.springwebdoclet.render;

import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * Bean for template internationalization using specified locale
 *
 * @author Ivan Sungurov
 */
public class MessageProvider {
    private MessageSource messageSource;
    private Locale locale;

    public MessageProvider(MessageSource messageSource, Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    public String getText(String code) {
        return messageSource.getMessage(code, null, locale);
    }
}
