package dev.remine.guilder.api.loadbalancer.repository;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class RepositoryModule implements Module {

    @Override
    public void configure(Binder binder)
    {
        binder.bind(ServicesRepository.class).to(ServicesRepositoryImpl.class).in(Scopes.SINGLETON);
    }

}
