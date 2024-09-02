package com.works.archisproject.service.impl;

import com.works.archisproject.dto.SkillDto;
import com.works.archisproject.entity.Skill;
import com.works.archisproject.exception.skillException.SkillAlreadyExistsException;
import com.works.archisproject.exception.skillException.SkillNotFoundException;
import com.works.archisproject.repository.SkillRepository;
import com.works.archisproject.service.SkillService;
import com.works.archisproject.util.AppLogger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    private final ModelMapper modelMapper;
    private final AppLogger appLogger;

    public SkillServiceImpl(SkillRepository skillRepository, ModelMapper modelMapper, AppLogger appLogger) {
        this.skillRepository = skillRepository;
        this.modelMapper = modelMapper;
        this.appLogger = appLogger;
    }

    @Override
    public SkillDto save(SkillDto skillDto) {
        Skill skill = modelMapper.map(skillDto, Skill.class);
        Optional<Skill> byNameEqualsIgnoreCase = skillRepository.findByNameEqualsIgnoreCase(skill.getName());
        if (byNameEqualsIgnoreCase.isPresent()) {
            appLogger.logError("This skill " + skill.getName() + "is already exists.");
            throw new SkillAlreadyExistsException("This skill " + skill.getName() + "is already exists.");
        }else {
            Skill save = skillRepository.save(skill);
            SkillDto skillDto1 = modelMapper.map(save, SkillDto.class);
            appLogger.logInfo("Skill added successfully.");
            return skillDto1;
        }
    }

    @Override
    public List<SkillDto> getAllSkill() {
        List<Skill> all = skillRepository.findAll();
        if (all.size()>0) {
            List<SkillDto> collect = all.stream().map(skill -> modelMapper.map(skill, SkillDto.class)).collect(Collectors.toList());
            appLogger.logInfo("Skills are successfully listed.");
            return collect;
        }else {
            appLogger.logError("There is no skill in database.");
            throw new SkillNotFoundException("There is no skill in database.");
        }
    }

    @Override
    public SkillDto getSkillByName(String name) {
        Optional<Skill> byNameEqualsIgnoreCase = skillRepository.findByNameEqualsIgnoreCase(name);
        if (byNameEqualsIgnoreCase.isPresent()) {
            SkillDto skillDto = modelMapper.map(byNameEqualsIgnoreCase.get(), SkillDto.class);
            appLogger.logInfo("This skill is successfully listed with this name: " + name);
            return skillDto;
        }else {
            appLogger.logError("There is no skill with this name: " + name);
            throw new SkillNotFoundException("There is no skill with this name: " + name);
        }
    }

    @Override
    public SkillDto getSkillById(Long id) {
        Optional<Skill> getSkillById = skillRepository.findById(id);
        if (getSkillById.isPresent()) {
            SkillDto skillDto = modelMapper.map(getSkillById.get(), SkillDto.class);
            appLogger.logInfo("This skill is successfully listed by this id: " + id);
            return skillDto;
        }else {
            appLogger.logError("There no skill with this id: " + id);
            throw new SkillNotFoundException("There no skill with this id: " + id);
        }
    }

    @Override
    public String deleteSkillById(Long id) {
        Optional<Skill> byId = skillRepository.findById(id);
        if (byId.isPresent()) {
            skillRepository.deleteById(id);
            appLogger.logInfo("This skill delete by this id: " + id);
            return "This skill delete by this id: " + id;
        }else {
            appLogger.logError("This skill is not found with this id: " + id);
            throw new SkillNotFoundException("This skill is not found with this id: " + id);
        }
    }

    @Override
    public SkillDto update(SkillDto skillDto) {

        Skill skill = modelMapper.map(skillDto, Skill.class);
        Optional<Skill> byId = skillRepository.findById(skill.getId());
        if (byId.isPresent()) {
            Skill skill1 = skillRepository.saveAndFlush(skill);
            SkillDto skillDto1 = modelMapper.map(skill1, SkillDto.class);
            appLogger.logInfo("Skill is updated with this id: " + skill.getId());
            return skillDto1;
        }else {
            appLogger.logError("This skill is not found with this id: " + skill.getId());
            throw new SkillNotFoundException("This skill is not found with this id: " + skill.getId());
        }
    }
}
