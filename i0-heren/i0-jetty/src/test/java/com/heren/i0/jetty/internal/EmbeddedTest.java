package com.heren.core.jetty.internal;

import com.google.inject.servlet.ServletModule;
import com.heren.core.config.HttpConfiguration;
import com.heren.core.core.ServletContainer;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.junit.After;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.heren.core.config.Configuration.config;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EmbeddedTest {
    public static final int PORT = 8051;
    private ServletContainer server;

    @After
    public void after() throws Exception {
        if (server != null) server.stop();
    }

    @Test
    public void should_configure_server_as_http_service() throws Exception {
        startServer(config().http().port(PORT).build());
        assertThat(get("http://localhost:8051/message"), is("message"));
    }

    @Test
    public void should_configure_server_as_https_service() throws Exception {
        startServer(config().http().port(PORT)
                .ssl()
                .keyStore(getClass().getResource("test.keystore").getPath(), "password")
                .trustStore(getClass().getResource("test.keystore").getPath(), "password")
                .end().build());
        assertThat(get("https://localhost:8051/message"), is("message"));
    }

    private void startServer(HttpConfiguration configuration) throws Exception {
        server = new Embedded(configuration);
        server.addServletContext("/", true, new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/message").with(new HttpServlet() {
                    @Override
                    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                        resp.getWriter().append("message");
                    }
                });
            }
        });
        server.start(false);
    }

    public static String get(String url) throws Exception {
        HttpClient client = new HttpClient(new SslContextFactory());
        client.start();
        try {
            return new String(client.GET(url).getContent());
        } finally {
            client.stop();
        }
    }
}

