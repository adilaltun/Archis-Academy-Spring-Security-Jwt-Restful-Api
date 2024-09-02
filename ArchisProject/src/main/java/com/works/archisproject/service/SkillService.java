package com.works.archisproject.service;

import com.works.archisproject.dto.SkillDto;

import java.util.List;

public interface SkillService {

    SkillDto save(SkillDto skillDto);

    List<SkillDto> getAllSkill();

    SkillDto getSkillByName(String name);

    SkillDto getSkillById(Long id);

    String deleteSkillById(Long id);

    SkillDto update(SkillDto skillDto);

}
