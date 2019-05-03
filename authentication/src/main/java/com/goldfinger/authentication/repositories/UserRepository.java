package com.goldfinger.authentication.repositories;


import com.goldfinger.authentication.models.JwtUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<JwtUserDetails, Integer> {
    List<JwtUserDetails> findByUsername(String username);
}
