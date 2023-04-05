package com.attractorschool.imurab.controller.view;

import com.attractorschool.imurab.dto.department.DepartmentCreateDto;
import com.attractorschool.imurab.dto.department.DepartmentDto;
import com.attractorschool.imurab.entity.Department;
import com.attractorschool.imurab.mapper.avp.AvpMapper;
import com.attractorschool.imurab.mapper.department.DepartmentMapper;
import com.attractorschool.imurab.service.avp.AvpService;
import com.attractorschool.imurab.service.department.DepartmentService;
import com.attractorschool.imurab.util.validator.department.DepartmentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;
    private final AvpService avpService;
    private final AvpMapper avpMapper;
    private final DepartmentValidator createDepartmentValidator;

    @GetMapping("/create")
    public ModelAndView createPage(@ModelAttribute("departmentCreateDto") DepartmentCreateDto departmentCreateDto) {
        return new ModelAndView("department/create")
                .addObject("avp", avpMapper.convertEntityToDto(avpService.findAll()));

    }

    @PostMapping("/create")
    public ModelAndView createDepartment(@ModelAttribute("departmentCreateDto") @Valid DepartmentCreateDto departmentCreateDto, BindingResult result) {
        createDepartmentValidator.validate(departmentCreateDto, result);
        if (result.hasErrors()) {
            return new ModelAndView("department/create", result.getModel())
                    .addObject("avp", avpMapper.convertEntityToDto(avpService.findAll()));
        }
        departmentService.create(departmentMapper.convertDepartmentCreateDtoToEntity(departmentCreateDto));
        return new ModelAndView("redirect:/dep");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editPage(@PathVariable(value = "id") Department department) {
        return new ModelAndView("department/edit")
                .addObject("departmentDto", departmentMapper.convertToDepartmentDto(department))
                .addObject("avp", avpMapper.convertEntityToDto(avpService.findAll()));
    }

    @PutMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Department department, @Valid @ModelAttribute DepartmentDto departmentDto, BindingResult result) {
        createDepartmentValidator.validate(departmentDto, result);
        if (result.hasErrors()) {
            return new ModelAndView("department/edit", result.getModel())
                    .addObject("departmentDto", departmentMapper.convertToDepartmentDto(department))
                    .addObject("avp", avpMapper.convertEntityToDto(avpService.findAll()));
        }
        departmentService.updateDepartment(department, departmentDto);
        return new ModelAndView("redirect:/department/");
    }

    @GetMapping()
    public ModelAndView indexPage() {
        return new ModelAndView("department/index");
    }
}
