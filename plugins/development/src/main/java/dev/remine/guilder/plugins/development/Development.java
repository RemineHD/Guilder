package dev.remine.guilder.plugins.development;

import dev.remine.guilder.rpc.loadbalancer.ClientHandshakeReply;
import dev.remine.guilder.rpc.loadbalancer.HandshakeGrpc;
import dev.remine.guilder.rpc.loadbalancer.HandshakeRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Development extends JavaPlugin implements Listener {

    private HandshakeGrpc.HandshakeBlockingStub handshakeBlockingStub;

    @Override
    public void onEnable() {
        String target = "localhost:50051";
        Bukkit.getPluginManager().registerEvents(this, this);
        System.out.println("Plugin Enabled! ZarozSpigot::Prod188");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
        handshakeBlockingStub = HandshakeGrpc.newBlockingStub(channel);
    }

    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent playerChatEvent)
    {
        if (playerChatEvent.getMessage().equalsIgnoreCase("request1"))
        {
            playerChatEvent.setCancelled(true);

            long epoch = System.currentTimeMillis();
            HandshakeRequest request = HandshakeRequest.newBuilder().setId("1212").build();
            ClientHandshakeReply handshakeReply;
            try {
                handshakeReply = handshakeBlockingStub.handshakeClient(request);
                playerChatEvent.getPlayer().sendMessage(handshakeReply.toString() + "Latency: " + (System.currentTimeMillis() - epoch) + "ms");
                return;
            } catch (Exception exception)
            {
                exception.printStackTrace();
            }
            playerChatEvent.getPlayer().sendMessage("ERROR!");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
