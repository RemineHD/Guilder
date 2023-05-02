package dev.remine.guilder.api.loadbalancer.controllers.c2s;

import com.google.inject.Inject;
import dev.remine.guilder.api.loadbalancer.repository.ServicesRepository;
import dev.remine.guilder.commons.api.models.Service;
import dev.remine.guilder.rpc.loadbalancer.ClientHandshakeReply;
import dev.remine.guilder.rpc.loadbalancer.HandshakeGrpc;
import dev.remine.guilder.rpc.loadbalancer.HandshakeRequest;
import dev.remine.guilder.rpc.types.Services;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class HandshakeC2S extends HandshakeGrpc.HandshakeImplBase {

    @Inject ServicesRepository servicesRepository;

    @Override
    public void handshakeClient(HandshakeRequest request, StreamObserver<ClientHandshakeReply> responseObserver)
    {




        /**
         *         servicesRepository.registerService(Service.newBuilder()
         *                 .setId(UUID.randomUUID().toString())
         *                 .setServiceState(ServiceState.RUNNING)
         *                 .setServiceType(ServiceType.ADMIN)
         *                 .setIp("127.0.0.1")
         *                 .setPort(25565)
         *                 .setClients(1)
         *                 .build());
         */

        List<Services.Service> clientServices = new ArrayList<>();

        try {
            //Service service = servicesRepository.getRecommendedServiceByType(Services.ServiceType.PLAYERS).getResult();
            //service.setClients(999);
            //servicesRepository.updateService(service);
            //clientServices.add(service.getAsProto());

            servicesRepository.deleteServicesByType(Services.ServiceType.PLAYERS);
            servicesRepository.deleteServiceById("dev-test-reccomendations-2");

        } catch (Exception exception)
        {
            exception.printStackTrace();
        }





        //clientServices.add(servicesRepository.getServiceById("90196a36-69af-4aa3-b9ba-af112cbf1e18").getResult().getAsProto());
        //clientServices.add(servicesRepository.getServiceById("35cd70d3-097c-4ce0-b940-70442d7b9de5").getResult().getAsProto());

        ClientHandshakeReply reply = ClientHandshakeReply.newBuilder()
                .addAllServices(clientServices)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }


}
