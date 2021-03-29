package com.hasnain.HibernateDemo.models;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		// Just return a string representing the username
		return Optional.ofNullable("Hasnain").filter(s -> !s.isEmpty());
	}

}
