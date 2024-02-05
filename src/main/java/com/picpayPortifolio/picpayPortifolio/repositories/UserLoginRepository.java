package com.picpayPortifolio.picpayPortifolio.repositories;

import com.picpayPortifolio.picpayPortifolio.domain.user.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserLoginRepository extends JpaRepository<UserLogin,String> {
    UserDetails findByLogin(String login);

}
