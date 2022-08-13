package ecommerce;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ProductInfoClient {
    public static void main(String[] args) {
        // grpc 채널 생성
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        ProductInfoGrpc.ProductInfoBlockingStub stub = ProductInfoGrpc.newBlockingStub(channel);

        ProductInfoOuterClass.ProductID productId = stub.addProduct(ProductInfoOuterClass.Product.newBuilder()
                .setName("Apple iPhone 11")
                .setDescription("Meet apple iPhone 11. " + "with All-new camera.")
                .build()
        );

        System.out.println(productId.getValue());

        ProductInfoOuterClass.Product product = stub.getProduct(productId);
        System.out.println(product.toString());

        channel.shutdown();
    }
}
