package com.dankan.userservice.service.univ;

import com.dankan.userservice.dto.response.univ.UnivListResponseDto;
import com.dankan.userservice.repository.UnivRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class UnivServiceImpl implements UnivService {
    private final UnivRepository univRepository;

    @Override
    public List<UnivListResponseDto> findAll() {
        return univRepository.findUnivList();
    }
}
