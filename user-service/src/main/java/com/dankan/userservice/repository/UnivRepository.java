package com.dankan.userservice.repository;

import com.dankan.userservice.domain.Univ;
import com.dankan.userservice.dto.response.univ.UnivListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnivRepository extends JpaRepository<Univ, Long> {
    @Query(value = "select new com.dankan.userservice.dto.response.univ.UnivListResponseDto(univ.emailDomain, univ.univName)" +
            "from Univ univ")
    public List<UnivListResponseDto> findUnivList();

}
