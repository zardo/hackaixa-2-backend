package io.zardo.hackaixa;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class Hackaixa extends Application {


    public Hackaixa() {
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<Object>();
        singletons.add(new DialogflowRest());
        singletons.add(new CharacterEncodingFilter());
        return singletons;
    }

}
