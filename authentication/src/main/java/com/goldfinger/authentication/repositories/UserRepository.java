package com.goldfinger.authentication.repositories;


import com.goldfinger.authentication.models.JwtUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<JwtUserDetails, Integer> {
}
