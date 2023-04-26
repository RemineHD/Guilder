package dev.remine.guilder.commons.api.docker;

import com.google.inject.AbstractModule;

public class DockerModule extends AbstractModule {

    @Override
    public void configure()
    {
        System.out.println("Docker Module Called!");
    }

}
