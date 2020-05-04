package org.superhelt.raidplanner2;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class StandaloneServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        server.setHandler(contextHandler);

        ServletHolder holder =contextHandler.addServlet(ServletContainer.class, "/*");
        holder.setInitParameter("jersey.config.server.provider.packages", "org.superhelt.raidplanner2.resources");

        server.start();
        server.join();
    }
}
