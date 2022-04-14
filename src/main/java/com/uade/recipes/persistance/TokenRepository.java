package com.uade.recipes.persistance;

import com.uade.recipes.model.Token;
import com.uade.recipes.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, Integer> {

    Optional<Token> findByUserAndTimestampGreaterThanEqualAndTimestampLessThanEqual(User user, Timestamp yesterday, Timestamp now);
}
