package com.easyflight.flight.service;

import com.easyflight.flight.entity.User;
import com.easyflight.flight.enums.ErrorCodes;
import com.easyflight.flight.exception.DuplicateException;
import com.easyflight.flight.repository.UserRepository;
import com.easyflight.flight.request.UserRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Victor.Ikoro on 8/8/2017.
 */
@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String idOrEmail) {
        List result = this.userRepository.findByEmailOrId(idOrEmail, idOrEmail);
        return result.size() > 0 ? (User) result.get(0) : null;
    }


    public User createUser(UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);

        try {
            return userRepository.save(user);
        } catch (DuplicateKeyException ex) {
            throw new DuplicateException(ErrorCodes.USER_ALREADY_EXISTS.name(), "User already exists", ex);
        }

    }


}
