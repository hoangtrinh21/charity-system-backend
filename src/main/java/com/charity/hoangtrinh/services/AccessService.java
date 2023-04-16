package com.charity.hoangtrinh.services;

import com.charity.hoangtrinh.config.CacheConfig;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.User;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.RoleRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AccessService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    public int checkAccessToken(Integer userId, String role, String token) {
        Map<String, String> roleAndToken = CacheConfig.accessToken.get(userId);

        Optional<User> userOptional = userRepository.findById(userId);
        if (roleAndToken == null || !userOptional.isPresent())
            return HttpStatus.FORBIDDEN.value();


        if (!roleAndToken.get("role").equals(role) || !roleAndToken.get("token").equals(token))
            return HttpStatus.UNAUTHORIZED.value();
        return HttpStatus.OK.value();
    }
}
