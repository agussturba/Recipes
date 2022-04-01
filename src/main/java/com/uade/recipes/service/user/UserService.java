package com.uade.recipes.service.user;

import com.uade.recipes.model.User;
import com.uade.recipes.vo.UserVo;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByEmail(String email);

    User getUserByUserName(String userName);

    User getUserById(Integer idClient);

    User saveOrUpdateUser(UserVo user, String role) throws ValidationException;

}
