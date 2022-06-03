package com.uade.recipes.persistance;

import com.uade.recipes.model.Token;
import com.uade.recipes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByUserAndCodeAndTimestampGreaterThanEqualAndTimestampLessThanEqual(User user, Integer code, Timestamp yesterday, Timestamp now);

    Optional<Token> findFirstByUserAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(User user, Timestamp yesterday, Timestamp now);
}
