package com.dankan.userservice.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id",nullable = false, columnDefinition = "bigint")
    private Long userId;

    @Column(name = "date_id", nullable = false, columnDefinition = "int")
    private Long dateId;

    @Column(nullable = false, unique = true,length = 16,columnDefinition = "varchar")
    private String nickname;

    @Column(nullable = false, unique = true,length = 40,columnDefinition = "varchar")
    private String email;

    @Column(nullable = false,columnDefinition = "text")
    private String profileImg;

    @Column(unique = true,length = 15,columnDefinition = "varchar")
    private String phoneNum;

    @Column(nullable = false,columnDefinition = "int")
    private Long userType;

    @Column(columnDefinition = "bool")
    private Boolean gender;

    @Column(length = 36, columnDefinition = "varchar")
    private String univEmail;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private List<Authority> authorities;
}
