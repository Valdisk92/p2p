package com.revolut;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Application {

    public static void main(String[] args) throws IOException {


        ResourceConfig resourceConfig = new ResourceConfig()
                .packages("com.revolut");

        JdkHttpServerFactory.createHttpServer(URI.create("http://localhost:8080/"), resourceConfig);
    }

}
