package com.alliance.identityServer.repository;

import com.alliance.identityServer.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredentials,Long> {
    Optional<UserCredentials> findByUsername(String username);
}
