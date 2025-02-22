package com.project.template;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class PriceDeserializer extends JsonDeserializer<Double> {

    @Override
    public Double deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException {
        String priceStr = jsonParser.getText();
        Locale locale = LocaleContextHolder.getLocale();

        if (locale.getLanguage().equals("en") && priceStr.contains(",")) {
            throw new IllegalArgumentException("Invalid decimal separator for English locale. Expected '.' but found ','");
        }
        if (locale.getLanguage().equals("fr") && priceStr.contains(".")) {
            throw new IllegalArgumentException("Invalid decimal separator for French locale. Expected ',' but found '.'");
        }

        NumberFormat formatter = NumberFormat.getNumberInstance(locale);
        try {
            return formatter.parse(priceStr).doubleValue();
        } catch (ParseException e) {
            throw new IOException("Failed to parse price: " + priceStr, e);
        }
    }
}