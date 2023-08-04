package com.dankan.userservice.repository;

import com.dankan.userservice.domain.User;
import com.dankan.userservice.dto.response.user.UserResponseDto;
import com.dankan.userservice.vo.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select new com.dankan.userservice.dto.response.user.UserResponseDto(user.userId, user.email, user .nickname, user.gender, user.phoneNum, " +
            "user.profileImg, user.univEmail) from User user " +
            "where user.userId = :id")
    public Optional<UserResponseDto> findByUserId(@Param("id") Long id);

    @Query(value = "select new com.dankan.userservice.dto.response.user.UserResponseDto(user.userId, user.email, user .nickname, user.gender, user.phoneNum, " +
            "user.profileImg, user.univEmail) from User user " +
            "where user.nickname = :name")
    public Optional<UserResponseDto> findByNickname(@Param("name") String name);

    public Optional<User> findUserByNickname(String name);

    public Optional<User> findByEmail(String email);

    public Optional<User> findByPhoneNum(String phoneNum);

    public Optional<User> findUserByUserId(Long uuid);

    @Query(value = "select new com.dankan.userservice.dto.response.user.UserResponseDto(user.userId, user.email, user .nickname, user.gender, user.phoneNum, " +
            "user.profileImg, user.univEmail) from User user")
    public List<UserResponseDto> findUserList();

    @Query(value = "select new com.dankan.userservice.vo.UserInfo(user.nickname, user.profileImg) from User user where  user.userId = :id")
    public Optional<UserInfo> findName(@Param("id") Long id);
}
