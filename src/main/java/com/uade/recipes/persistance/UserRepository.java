package com.uade.recipes.persistance;


import com.uade.recipes.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);

    Optional<User> findByEmailAndPassword(String email,String password);

    Optional<User> findByEmailAndUserName(String email, String alias);

    Optional<User> findByEmailAndRegistrationTimestampGreaterThanAndRegistrationTimestampLessThanEqual(String email, LocalDateTime yesterday, LocalDateTime now);
}
