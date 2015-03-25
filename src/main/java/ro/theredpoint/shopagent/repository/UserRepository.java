package ro.theredpoint.shopagent.repository;

import org.springframework.data.repository.Repository;

import ro.theredpoint.shopagent.domain.User;

public interface UserRepository extends Repository<User, Long> {

	public User findByUsername(String username);
}