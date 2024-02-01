package com.gamingroom.gameauth;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.ws.rs.client.Client;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gamingroom.gameauth.auth.GameAuthenticator;
import com.gamingroom.gameauth.auth.GameAuthorizer;
import com.gamingroom.gameauth.auth.GameUser;

import com.gamingroom.gameauth.controller.GameUserRESTController;
import com.gamingroom.gameauth.controller.RESTClientController;

import com.gamingroom.gameauth.healthcheck.AppHealthCheck;
import com.gamingroom.gameauth.healthcheck.HealthCheckController;


public class GameAuthApplication extends Application<Configuration> {
	private static final Logger LOGGER = LoggerFactory.getLogger(GameAuthApplication.class); 
	 
	@Override
	public void initialize(Bootstrap<Configuration> b) {
	}

	@Override
	public void run(Configuration c, Environment e) throws Exception 
	{

		// register GameUserRESTController (based on BasicAuth Security Example)
		LOGGER.info("Registering REST resources");
		e.jersey().register(new GameUserRESTController(e.getValidator()));
	    
//		Create io.dropwizard.client.JerseyClientBuilder instance named "DemoRESTClient" 
//	    and give it io.dropwizard.setup.Environment reference (based on BasicAuth Security Example)
	    LOGGER.info("Registering Jersey Client");
	    final Client client = new JerseyClientBuilder(e)
	    		  .build("DemoRESTClient");
	    		e.jersey().register(new RESTClientController(client));

		// Application health check
	    LOGGER.info("Registering Application Health Check");
		e.healthChecks().register("APIHealthCheck", new AppHealthCheck(client));

		// Run multiple health checks
		e.jersey().register(new HealthCheckController(e.healthChecks()));
		
		//Setup Basic Security
		e.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<GameUser>()
                .setAuthenticator(new GameAuthenticator())
                .setAuthorizer(new GameAuthorizer())
                .setRealm("App Security")
                .buildAuthFilter()));
        e.jersey().register(new AuthValueFactoryProvider.Binder<>(GameUser.class));
        e.jersey().register(RolesAllowedDynamicFeature.class);
	}

	/**
	 * How to start the GameAuth application 1.Run mvn clean install to build your
	 * application 2.Start application with java -jar
	 * target/gameauth-0.0.1-SNAPSHOT.jar server config.yml 3.To check that your
	 * application is running enter url http://localhost:8080/gameusers
	 */
	public static void main(String[] args) throws Exception {
		new GameAuthApplication().run(args);
	}
}