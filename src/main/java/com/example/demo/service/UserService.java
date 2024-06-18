package com.example.demo.service;

import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.Jwt.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail = repository.findByName(username);

        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public boolean addUser(UserInfo userInfo) {
        if(repository.findByName(userInfo.getName()).isEmpty()){
            userInfo.setPassword(encoder.encode(userInfo.getPassword()));
            repository.save(userInfo);
            return true;


        }
        return false;


    }
    public Optional<UserInfo> findByUsername(String username)  {

        return repository.findByName(username);
    }

    public boolean setUsername(String username,String info1, String info2) {
        Optional<UserInfo> s= repository.findByName(username);
        if (s.isPresent()) {
            UserInfo userInfo = s.get();
            userInfo.setName(info1);
            userInfo.setEmail(info2);
            repository.save(userInfo);
            return true; // E-posta başarıyla güncellendiğinde true döndürülür
        } else {
            return false; // Kullanıcı bulunamazsa false döndürülür
        }

    }
    public boolean setPassword(String username, String info2) {
        Optional<UserInfo> s= repository.findByName(username);
        s.ifPresent(userInfo -> {
            userInfo.setPassword(info2);
            repository.save(userInfo);
        });
        return false;
    }


    public boolean deleteUser(String username) {
        Optional<UserInfo> s= repository.findByName(username);
        if (s.isPresent()){
            UserInfo userInfo = s.get();
            repository.deleteById((long) userInfo.getId());
            return  true;
        }
        else{
        return false;
        }
    }
}