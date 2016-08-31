package org.petru.syncregitry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@Import(SyncRegitryConfiguration.class)
public class SyncRegitryServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "sync-registry-server");

		SpringApplication.run(SyncRegitryServer.class, args);
	}
}
