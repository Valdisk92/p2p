package com.p2p;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Application {

    public static void main(String[] args) throws IOException {


        ResourceConfig resourceConfig = new ResourceConfig()
                .packages("com.p2p");

        JdkHttpServerFactory.createHttpServer(URI.create("http://localhost:8080/"), resourceConfig);
    }

}
