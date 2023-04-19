package dev.remine.guilder.api.loadbalancer.handshake;

import dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass;

import java.util.List;

public interface HandshakeService {

    List<HandshakeOuterClass.ClientService> getClientServices(String token);

}
