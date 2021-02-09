package com.rumblesoftware.cat.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!webtest")
public class HybernatePosInIConfig implements CommandLineRunner {

	/**
	 * Injecting dataSource for the Flyway workaround purpose
	 */
	@Autowired
	DataSource dataSource;

	

	/**
	 * When using flyway + Hibernate we have a boot issue during initialization, what happens is that
	 * flyway tries to insert data on the database and hibernate had not created it. This way we can use
	 * the following workaround to solve the problem:
	 * 
	 *  1 - Create the first migration script with a higher version (cuz normally the V1 is initialized during boot) 
	 *  2 - Stop the Flyway initialization on application.properties
	 *  3 - Force the Flyway initialization after all (using the method below)
	 */
	@Override
	public void run(String... args) throws Exception {
		Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();
	}
}
