package com.example.demo.dao;

import com.example.demo.model.TUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource(path = "user")
public interface UserDao extends JpaRepository<TUser, Long>, JpaSpecificationExecutor<TUser> {
    Optional<TUser> findById(Long id);

    Page<TUser> findAll(Pageable pageable, Sort sort);

    @Query("SELECT f FROM Foo f WHERE LOWER(f.name) = LOWER(:name)")
    TUser retrieveByName(@Param("name") String name);

    @Modifying
    @Query("update User u set u.status = :status where u.name = :name")
    int updateUserSetStatusForName(@Param("status") Integer status,
                                   @Param("name") String name);

    @Modifying
    @Query(value = "insert into Users (name, age, email, status) values (:name, :age, :email, :status)",
            nativeQuery = true)
    void insertUser(@Param("name") String name, @Param("age") Integer age,
                    @Param("status") Integer status, @Param("email") String email);

    long count(Specification<TUser> conn);
}