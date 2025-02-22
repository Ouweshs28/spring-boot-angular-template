package com.project.template;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class PriceSerializer extends JsonSerializer<Double> {
    @Override
    public void serialize(Double price, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        Locale locale = LocaleContextHolder.getLocale();
        NumberFormat formatter = NumberFormat.getNumberInstance(locale);
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        String formattedPrice = formatter.format(price);
        jsonGenerator.writeString(formattedPrice);
    }
}