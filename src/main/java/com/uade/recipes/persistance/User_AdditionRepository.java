package com.uade.recipes.persistance;

import com.uade.recipes.model.User;
import com.uade.recipes.model.User_Addition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User_AdditionRepository extends JpaRepository<User_Addition,Integer> {
    User_Addition findByUser(User user);
}
