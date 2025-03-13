package ru.leonchik.transformers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import spark.ResponseTransformer;

/**
 * Project: cinema
 * Package: ru.leonchik.transformers
 * <p>
 * User: alexey
 * Date: чт 13 март 2025
 */
public final class JsonTransformer implements ResponseTransformer {

    public static final JsonTransformer JSON = new JsonTransformer();

    @Override
    public String render(Object response) throws Exception {
        try {
            return (new ObjectMapper()).writeValueAsString(response);
        } catch (JsonProcessingException exc) {
            return null;
        }
    }
}
