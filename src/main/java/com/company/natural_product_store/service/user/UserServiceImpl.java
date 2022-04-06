package com.company.natural_product_store.service.user;

import com.company.natural_product_store.entity.AppUser;
import com.company.natural_product_store.entity.security.CustomUserDetails;
import com.company.natural_product_store.exception.DataNotFoundException;
import com.company.natural_product_store.exception.InvalidDataException;
import com.company.natural_product_store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    public AppUser save(AppUser user) {
        try {
            if (user.getCreatedAt() == null) {
                user.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            }

            System.out.println(user.getId());
            return userRepository.save(user);
        } catch (Exception e) {
            throw new InvalidDataException("Invalid user!");
        }
    }

    public void deleteById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataNotFoundException("Data not found!");
        }
    }

    public void deleteAll() {
        try {
            userRepository.deleteAll();
        } catch (Exception e) {
            throw new DataNotFoundException("Data not found!");
        }
    }

    @Override
    public AppUser findByUsername(String username) {
        AppUser user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Could not find user")
        );

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        System.out.println(username);
        AppUser user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Could not find user")
        );
        System.out.println(user.getUsername());

        CustomUserDetails customUserDetails = new CustomUserDetails(user, user.getRole().getGrantedAuthorities());

        return customUserDetails;
    }
}
