package dev.remine.guilder.api.loadbalancer.handshake;

import dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass;

import java.util.List;

public class HandshakeController implements HandshakeService {

    @Override
    public List<HandshakeOuterClass.ClientService> getClientServices(String token) {

        return null;
    }

}
