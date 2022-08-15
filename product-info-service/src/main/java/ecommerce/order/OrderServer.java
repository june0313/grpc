package ecommerce.order;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class OrderServer {
    private static final Logger log = LoggerFactory.getLogger(OrderServer.class);

    private Server server;

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new OrderManagementServiceImpl())
                .build()
                .start();

        log.info("Server started, listening on {}", port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("*** shutting down gRPC server since JVM is shutting down");
            OrderServer.this.stop();
            log.info("*** server shutdown");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        OrderServer server = new OrderServer();
        server.start();
        server.blockUntilShutdown();
    }
}
