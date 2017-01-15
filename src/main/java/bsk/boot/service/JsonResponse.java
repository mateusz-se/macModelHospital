package bsk.boot.service;

import bsk.boot.util.HibernateProxyTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by Mateusz-PC on 07.01.2017.
 */
@Component
public class JsonResponse implements ResponseBuilder {
    @Override
    public String build(Object object) {
        GsonBuilder b = new GsonBuilder();
        b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        Gson gson = b.create();
        return gson.toJson(object);
    }
}
