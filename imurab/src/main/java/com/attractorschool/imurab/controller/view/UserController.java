package com.attractorschool.imurab.controller.view;

import com.attractorschool.imurab.dto.user.CreateUserDto;
import com.attractorschool.imurab.dto.user.UserDto;
import com.attractorschool.imurab.entity.User;
import com.attractorschool.imurab.mapper.avp.AvpMapper;
import com.attractorschool.imurab.mapper.user.UserMapper;
import com.attractorschool.imurab.security.UserPrincipal;
import com.attractorschool.imurab.service.avp.AvpService;
import com.attractorschool.imurab.service.file.FileUploadService;
import com.attractorschool.imurab.service.user.UserService;
import com.attractorschool.imurab.util.enums.Role;
import com.attractorschool.imurab.util.validator.user.CreateUserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final CreateUserValidator userValidator;
    private final AvpService avpService;
    private final AvpMapper avpMapper;
    private final FileUploadService uploadService;

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("users/index")
                .addObject("avp", avpMapper.convertEntityToDto(avpService.findAll()));
    }

    @GetMapping("/profile")
    public ModelAndView profilePage(@AuthenticationPrincipal UserPrincipal principal) {
        return new ModelAndView("users/profile")
                .addObject("user", userService.findById(principal.getUser().getId()));
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editPage(@PathVariable("id") User user) {
        return new ModelAndView("users/edit")
                .addObject("userDto", userMapper.convertEntityDto(user))
                .addObject("roles", Role.values())
                .addObject("avp", avpMapper.convertEntityToDto(avpService.findAll()));
    }

    @PutMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable("id") User user, @Valid @ModelAttribute UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("users/edit", result.getModel())
                    .addObject("roles", Role.values())
                    .addObject("avp", avpMapper.convertEntityToDto(avpService.findAll()));
        }
        userService.updateUser(user, userDto);
        if (nonNull(userDto.getImage()) && isNotEmpty(userDto.getImage().getOriginalFilename()))
            uploadService.uploadFile(userDto.getImage(), "users");
        return new ModelAndView("redirect:/users");
    }

    @GetMapping("/create")
    public ModelAndView createPage(@ModelAttribute("userDto") CreateUserDto userDto) {
        return new ModelAndView("users/create")
                .addObject("roles", Role.values())
                .addObject("avp", avpMapper.convertEntityToDto(avpService.findAll()));
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute("userDto") @Valid CreateUserDto userDto, BindingResult result) {
        userValidator.validate(userDto, result);
        if (result.hasErrors()) {
            return new ModelAndView("users/create", result.getModel())
                    .addObject("roles", Role.values())
                    .addObject("avp", avpMapper.convertEntityToDto(avpService.findAll()));
        }
        userService.create(userMapper.convertDtoToEntity(userDto));
        if (nonNull(userDto.getImage()) && isNotEmpty(userDto.getImage().getOriginalFilename()))
            uploadService.uploadFile(userDto.getImage(), "users");
        return new ModelAndView("redirect:/users");
    }
}
