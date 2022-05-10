package com.example.recipes.persistence;

import com.example.recipes.businesslayer.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "select u.id, u.email, u.password from users u where u.email = :email",
            nativeQuery = true)
    User findUserByEmail(@Param("email") String email);
}
