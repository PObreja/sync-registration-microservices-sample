package org.petru.syncregistry.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMarshalling {
	private JsonMarshalling()
    {
    }
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public static final String toJson(Object object)
    {
        try
        {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public static final <O> O unmarshalling(String json, Class<O> clazz)
    {
        try
        {
            return mapper.readValue(json, clazz);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
