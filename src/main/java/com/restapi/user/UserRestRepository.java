package com.restapi.user;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRestRepository extends PagingAndSortingRepository<UserDetails, Long> {

	
	List<UserDetails> findUserByRole(String role);
}
