package com.uade.recipes.persistance;


import com.uade.recipes.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);

    User findByUserName(String userName);

    User findByEmailAndPassword(String email, String password);

}
