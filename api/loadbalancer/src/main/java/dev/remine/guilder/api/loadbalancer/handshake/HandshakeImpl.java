package dev.remine.guilder.api.loadbalancer.handshake;

import dev.remine.guilder.rpc.loadbalancer.HandshakeGrpc;
import dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass;
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
        List<HandshakeOuterClass.ClientService> clientServices = new ArrayList<>();
        clientServices.add(
                HandshakeOuterClass.ClientService.newBuilder()
                        .setIp("127.0.0.1")
                        .setPort(25565)
                        .build()
        );

        HandshakeOuterClass.ClientHandshakeReply reply = HandshakeOuterClass.ClientHandshakeReply.newBuilder()
                .setId("Welcome")
                .setAuthKey("NAH")
                .addAllServices(clientServices)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }



}
