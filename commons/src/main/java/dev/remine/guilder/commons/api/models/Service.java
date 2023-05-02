package dev.remine.guilder.commons.api.models;

import dev.remine.guilder.rpc.types.Services;
import dev.remine.guilder.rpc.types.State;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import java.util.Comparator;

public class Service {

    public Service()
    {

    }

    public Service(Services.Service service)
    {
        this.Id = service.getId();
        this.serviceType = service.getServiceType();
        this.serviceState = service.getServiceState();
        this.address = service.getIp();
        this.port = service.getPort();
        this.clients = service.getClients();
    }

    @Setter
    @Getter
    private String Id;

    @Setter
    @Getter
    private Services.ServiceType serviceType;

    @Setter
    @Getter
    private State serviceState;

    @Setter
    @Getter
    private String address;

    @Setter
    @Getter
    private Integer port;

    @Setter
    @Getter
    private Integer clients;

    @BsonIgnore
    public static Comparator<Service> instanceNumberComparator = new Comparator<Service>() {
        @Override
        public int compare(Service o1, Service o2) {
            return o1.getClients() - o2.getClients();
        }
    };

    @BsonIgnore
    public Services.Service getAsProto()
    {
        return Services.Service.newBuilder()
                .setId(getId())
                .setServiceType(getServiceType())
                .setServiceState(getServiceState())
                .setIp(getAddress())
                .setPort(getPort())
                .setClients(getClients())
                .build();
    }




}
