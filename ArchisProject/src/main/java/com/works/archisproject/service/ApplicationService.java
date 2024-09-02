package com.works.archisproject.service;

import com.works.archisproject.dto.ApplicationDto;

import java.util.List;

public interface ApplicationService {

    ApplicationDto save(ApplicationDto applicationDto);

    List<ApplicationDto> getAllApplication();

    ApplicationDto getApplicationByName(String name);

    ApplicationDto getApplicationById(Long id);

    String deleteApplicationById(Long id);

    ApplicationDto update(ApplicationDto applicationDto);

}
