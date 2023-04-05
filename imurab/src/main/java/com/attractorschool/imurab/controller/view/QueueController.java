package com.attractorschool.imurab.controller.view;

import com.attractorschool.imurab.mapper.avp.AvpMapper;
import com.attractorschool.imurab.mapper.department.DepartmentMapper;
import com.attractorschool.imurab.service.avp.AvpService;
import com.attractorschool.imurab.service.department.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/queue")
@RequiredArgsConstructor
public class QueueController {
    private final AvpService avpService;
    private final DepartmentService departmentService;
    private final AvpMapper avpMapper;
    private final DepartmentMapper departmentMapper;

    @GetMapping
    public ModelAndView index(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("AVPs", avpService.findAll());
        return new ModelAndView("queue/queue");
    }

    @GetMapping("/review")
    public ModelAndView reviewPage() {
        return new ModelAndView("queue/review")
                .addObject("avp", avpMapper.convertEntityToDto(avpService.findAll()))
                .addObject("departments", departmentMapper.convertToDepartmentDto(departmentService.findAll()));
    }
}