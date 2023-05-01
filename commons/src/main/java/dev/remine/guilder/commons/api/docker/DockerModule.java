package dev.remine.guilder.commons.api.docker;

import com.google.inject.*;
import com.google.inject.Module;

public class DockerModule implements Module {

    @Override
    public void configure(Binder binder)
    {
        binder.bind(DockerService.class).to(DockerClientImpl.class).in(Scopes.SINGLETON);
    }

}
