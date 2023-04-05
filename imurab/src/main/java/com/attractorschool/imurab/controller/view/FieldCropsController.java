package com.attractorschool.imurab.controller.view;

import com.attractorschool.imurab.dto.fieldCrops.FieldCropsCreateDto;
import com.attractorschool.imurab.dto.fieldCrops.FieldCropsUpdateDto;
import com.attractorschool.imurab.entity.FieldCrops;
import com.attractorschool.imurab.mapper.fieldCrops.FieldCropsMapper;
import com.attractorschool.imurab.service.fieldCrops.FieldCropsService;
import com.attractorschool.imurab.util.validator.FieldCropsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/field-crops")
public class FieldCropsController {
    private final FieldCropsService fieldCropsService;
    private final FieldCropsValidator fieldCropsValidator;
    private final FieldCropsMapper fieldCropsMapper;

    @GetMapping("/create")
    public ModelAndView createPage(@ModelAttribute("fieldCropsCreateDto") FieldCropsCreateDto fieldCropsCreateDto) {
        return new ModelAndView("fieldCrops/create");
    }

    @PostMapping("/create")
    public ModelAndView createAvp(@ModelAttribute("fieldCropsCreateDto") @Valid FieldCropsCreateDto fieldCropsCreateDto, BindingResult result) {
        fieldCropsValidator.validate(fieldCropsCreateDto, result);
        if (result.hasErrors()) {
            return new ModelAndView("fieldCrops/create", result.getModel());
        }
        fieldCropsService.addFieldCrops(fieldCropsCreateDto);
        return new ModelAndView("redirect:/field-crops");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editPage(@PathVariable(value = "id") FieldCrops fieldCrops) {
        return new ModelAndView("fieldCrops/edit")
                .addObject("fieldCropsDto", fieldCropsMapper.convertEntityToFieldCropsUpdateDto(fieldCrops));
    }

    @PutMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") FieldCrops fieldCrops, @Valid @ModelAttribute FieldCropsUpdateDto fieldCropsUpdateDto, BindingResult result) {
        fieldCropsValidator.validate(fieldCropsUpdateDto, result);
        if (result.hasErrors()) {
            return new ModelAndView("fieldCrops/edit", result.getModel());
        }
        fieldCropsService.updateFieldCrops(fieldCrops, fieldCropsUpdateDto);
        return new ModelAndView("redirect:/field-crops/");
    }

    @GetMapping()
    public ModelAndView indexPage() {
        return new ModelAndView("fieldCrops/index");
    }
}
