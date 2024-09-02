package com.works.archisproject.service.impl;

import com.works.archisproject.dto.ApplicationDto;
import com.works.archisproject.entity.Application;
import com.works.archisproject.exception.applicationException.ApplicationAlreadyExistsException;
import com.works.archisproject.exception.applicationException.ApplicationNotFoundException;
import com.works.archisproject.repository.ApplicationRepository;
import com.works.archisproject.service.ApplicationService;
import com.works.archisproject.util.AppLogger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final AppLogger appLogger;
    private final ModelMapper modelMapper;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, AppLogger appLogger, ModelMapper modelMapper) {
        this.applicationRepository = applicationRepository;
        this.appLogger = appLogger;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApplicationDto save(ApplicationDto applicationDto) {
        Application application = modelMapper.map(applicationDto, Application.class);
        Optional<Application> byNameEqualsIgnoreCase = applicationRepository.findByNameEqualsIgnoreCase(application.getName());
        if (byNameEqualsIgnoreCase.isPresent()) {
            appLogger.logError("This application " + application.getName() + "is already exists.");
            throw new ApplicationAlreadyExistsException("This application " + application.getName() + "is already exists.");
        }else {
            Application save = applicationRepository.save(application);
            ApplicationDto applicationDto1 = modelMapper.map(save, ApplicationDto.class);
            appLogger.logInfo("Application added successfully.");
            return applicationDto1;
        }
    }

    @Override
    public List<ApplicationDto> getAllApplication() {
        List<Application> all = applicationRepository.findAll();
        if (all.size()>0) {
            List<ApplicationDto> collect = all.stream().map(application -> modelMapper.map(application, ApplicationDto.class)).collect(Collectors.toList());
            appLogger.logInfo("Applications are successfully listed.");
            return collect;
        }else {
            appLogger.logError("There is no application in database.");
            throw new ApplicationNotFoundException("There is no application in database.");
        }
    }

    @Override
    public ApplicationDto getApplicationByName(String name) {
        Optional<Application> byNameEqualsIgnoreCase = applicationRepository.findByNameEqualsIgnoreCase(name);
        if (byNameEqualsIgnoreCase.isPresent()) {
            Application application = byNameEqualsIgnoreCase.get();
            ApplicationDto applicationDto = modelMapper.map(application, ApplicationDto.class);
            appLogger.logInfo("This application is successfully listed with this name: " + name);
            return applicationDto;
        }else {
            appLogger.logError("There is no application with this name: " + name);
            throw new ApplicationNotFoundException("There is no application with this name: " + name);
        }
    }

    @Override
    public ApplicationDto getApplicationById(Long id) {
        Optional<Application> getApplicationById = applicationRepository.findById(id);
        if (getApplicationById.isPresent()) {
            ApplicationDto applicationDto = modelMapper.map(getApplicationById.get(), ApplicationDto.class);
            appLogger.logInfo("This application is successfully listed by this id: " + id);
            return applicationDto;
        }else {
            appLogger.logError("There no application with this id: " + id);
            throw new ApplicationNotFoundException("There no application with this id: " + id);
        }
    }

    @Override
    public String deleteApplicationById(Long id) {
        Optional<Application> byId = applicationRepository.findById(id);
        if (byId.isPresent()) {
            applicationRepository.deleteById(id);
            appLogger.logInfo("This application delete by this id: " + id);
            return "This application delete by this id: " + id;
        }else {
            appLogger.logError("This application is not found with this id: " + id);
            throw new ApplicationNotFoundException("This application is not found with this id: " + id);
        }
    }

    @Override
    public ApplicationDto update(ApplicationDto applicationDto) {
        Application application = modelMapper.map(applicationDto, Application.class);
        Optional<Application> byId = applicationRepository.findById(application.getId());
        if (byId.isPresent()) {
            Application application1 = applicationRepository.saveAndFlush(application);
            ApplicationDto applicationDto1 = modelMapper.map(application1, ApplicationDto.class);
            appLogger.logInfo("Application is updated with this id: " + application.getId());
            return applicationDto1;
        }else {
            appLogger.logError("This application is not found with this id: " + application.getId());
            throw new ApplicationNotFoundException("This application is not found with this id: " + application.getId());
        }
    }
}
