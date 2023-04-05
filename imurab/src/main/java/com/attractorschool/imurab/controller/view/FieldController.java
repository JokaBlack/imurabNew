package com.attractorschool.imurab.controller.view;

import com.attractorschool.imurab.dto.field.CreateFieldDto;
import com.attractorschool.imurab.dto.field.EditFieldDto;
import com.attractorschool.imurab.entity.Field;
import com.attractorschool.imurab.mapper.avp.AvpMapper;
import com.attractorschool.imurab.mapper.department.DepartmentMapper;
import com.attractorschool.imurab.mapper.field.FieldMapper;
import com.attractorschool.imurab.mapper.fieldCrops.FieldCropsMapper;
import com.attractorschool.imurab.security.UserPrincipal;
import com.attractorschool.imurab.service.avp.AvpService;
import com.attractorschool.imurab.service.department.DepartmentService;
import com.attractorschool.imurab.service.field.FieldService;
import com.attractorschool.imurab.service.fieldCrops.FieldCropsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/fields")
@RequiredArgsConstructor
public class FieldController {
    private final FieldService fieldService;
    private final FieldCropsService cropsService;
    private final DepartmentService departmentService;
    private final AvpService avpService;
    private final FieldCropsMapper fieldCropsMapper;
    private final DepartmentMapper departmentMapper;
    private final FieldMapper fieldMapper;
    private final AvpMapper avpMapper;

    @GetMapping
    public ModelAndView indexPage() {
        return new ModelAndView("fields/index");
    }

    @GetMapping("/{id}")
    public ModelAndView fieldPage(@PathVariable(value = "id") Long id) {
        return new ModelAndView("fields/field")
                .addObject("fieldDto", fieldMapper.convertToFieldDto(fieldService.findById(id)));
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editPage(@PathVariable(value = "id") Long id) {
        return new ModelAndView("fields/edit")
                .addObject("fieldDto", fieldMapper.convertToEditFieldDto(fieldService.findById(id)))
                .addObject("cultures", fieldCropsMapper.convertEntityToDto(cropsService.findAll()))
                .addObject("departments", departmentMapper.convertToDepartmentDto(departmentService.findAll()));
    }

    @PutMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable(value = "id") Long id,
                             @ModelAttribute("fieldDto") @Valid EditFieldDto fieldDto,
                             BindingResult result) {
        Field field = fieldService.findById(id);
        if (result.hasErrors()) {
            return new ModelAndView("fields/edit", result.getModel())
                    .addObject("fieldDto", fieldMapper.convertToEditFieldDto(field))
                    .addObject("cultures", fieldCropsMapper.convertEntityToDto(cropsService.findAll()))
                    .addObject("departments", departmentMapper.convertToDepartmentDto(departmentService.findAll()));
        }
        fieldService.update(field, fieldDto);
        return new ModelAndView("redirect:/fields");
    }

    @GetMapping("/create")
    public ModelAndView createPage(@ModelAttribute("fieldDto") CreateFieldDto fieldDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return new ModelAndView("fields/create")
                .addObject("cultures", fieldCropsMapper.convertEntityToDto(cropsService.findAll()))
                .addObject("departments", departmentMapper.convertToDepartmentDto(departmentService.findAllByAvp(userPrincipal.getUser().getAvp())));
    }

    @PostMapping("/create")
    public ModelAndView create(@AuthenticationPrincipal UserPrincipal userPrincipal,
                               @ModelAttribute("fieldDto") @Valid CreateFieldDto fieldDto,
                               BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("fields/create")
                    .addObject("cultures", fieldCropsMapper.convertEntityToDto(cropsService.findAll()))
                    .addObject("departments", departmentMapper.convertToDepartmentDto(departmentService.findAllByAvp(userPrincipal.getUser().getAvp())));
        }
        fieldService.create(fieldMapper.convertFieldDtoToEntity(fieldDto, userPrincipal.getUser()));
        return new ModelAndView("redirect:/fields");
    }

    @GetMapping("/review")
    public ModelAndView reviewPage() {
        return new ModelAndView("fields/review")
                .addObject("avp", avpMapper.convertEntityToDto(avpService.findAll()))
                .addObject("departments", departmentMapper.convertToDepartmentDto(departmentService.findAll()));
    }
}