package br.com.evandrolacerda.album.repository;

import br.com.evandrolacerda.album.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername( String username );

}
