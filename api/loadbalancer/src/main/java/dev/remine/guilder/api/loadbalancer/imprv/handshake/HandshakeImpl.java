package dev.remine.guilder.api.loadbalancer.imprv.manager.handshake;

import com.google.inject.Guice;
import dev.remine.guilder.api.loadbalancer.LoadBalancer;
import dev.remine.guilder.api.loadbalancer.server.ServerService;
import dev.remine.guilder.rpc.loadbalancer.HandshakeGrpc;
import dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass;
import dev.remine.guilder.rpc.types.Services;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class HandshakeImpl extends HandshakeGrpc.HandshakeImplBase {

    //TODO: get real information XD
    //SETUP: Development and testing, this is NOT production code.

    /**
     * Data Needed:
     *  - AuthKey for Client (auth)
     *  - Id for Client (manager/client)
     *  - List of MicroServices based on AuthKey (manager/services)
     * @param handshakeRequest
     * @param responseObserver
     */
    @Override
    public void handshakeClient(HandshakeOuterClass.HandshakeRequest handshakeRequest, StreamObserver<HandshakeOuterClass.ClientHandshakeReply> responseObserver)
    {
        List<Services.Service> clientServices = new ArrayList<>();
        clientServices.add(
                Services.Service.newBuilder()
                        .setServiceType(Services.ServiceType.ADMIN)
                        .setIp(handshakeRequest.getId())
                        .setPort(25565)
                        .build()
        );

        HandshakeOuterClass.ClientHandshakeReply reply = HandshakeOuterClass.ClientHandshakeReply.newBuilder()
                .addAllServices(clientServices)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }



}
