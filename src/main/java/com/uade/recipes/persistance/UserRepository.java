package com.uade.recipes.persistance;


import com.uade.recipes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,Integer> {
    User findByEmail(String email);
    User findByUserName(String userName);

}
