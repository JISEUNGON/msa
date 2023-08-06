package com.dankan.userservice.service.univ;


import com.dankan.userservice.dto.response.univ.UnivListResponseDto;

import java.util.List;

public interface UnivService {
    public List<UnivListResponseDto> findAll();
}
