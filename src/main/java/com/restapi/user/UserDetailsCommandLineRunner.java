package com.restapi.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner{
	private Logger logger = LoggerFactory.getLogger(getClass());
	private UserDetailsRepository repo;

	public UserDetailsCommandLineRunner(UserDetailsRepository repo) {
		super();
		this.repo = repo;
	}

	@Override
	public void run(String... args) throws Exception {
		repo.save(new UserDetails("Curt", "ADMIN"));
		repo.save(new UserDetails("Bob", "USER"));
		repo.save(new UserDetails("Filbert", "ADMIN"));
		repo.save(new UserDetails("Ronald", "USER"));
		repo.save(new UserDetails("Jose", "ADMIN"));
		
		List<UserDetails> users = repo.findUserByRole("admin");
		users.stream().forEach(user -> logger.info(user.toString()));
	}

}
