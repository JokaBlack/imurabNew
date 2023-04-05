package com.attractorschool.imurab.controller.view;

import com.attractorschool.imurab.dto.user.CreateUserDto;
import com.attractorschool.imurab.dto.user.UserEmailDto;
import com.attractorschool.imurab.dto.user.UserPasswordDto;
import com.attractorschool.imurab.entity.PasswordToken;
import com.attractorschool.imurab.mapper.avp.AvpMapper;
import com.attractorschool.imurab.mapper.token.PasswordTokenMapper;
import com.attractorschool.imurab.mapper.user.UserMapper;
import com.attractorschool.imurab.service.avp.AvpService;
import com.attractorschool.imurab.service.file.FileUploadService;
import com.attractorschool.imurab.service.mail.MailService;
import com.attractorschool.imurab.service.token.PasswordTokenService;
import com.attractorschool.imurab.service.user.UserService;
import com.attractorschool.imurab.util.validator.user.CreateUserValidator;
import com.attractorschool.imurab.util.validator.user.UserEmailValidator;
import com.attractorschool.imurab.util.validator.user.UserPasswordValidator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final MailService mailService;
    private final PasswordTokenService tokenService;
    private final CreateUserValidator userValidator;
    private final UserEmailValidator emailValidator;
    private final UserPasswordValidator passwordValidator;
    private final UserMapper usermapper;
    private final PasswordTokenMapper tokenMapper;
    private final AvpService avpService;
    private final AvpMapper avpMapper;
    private final FileUploadService uploadService;

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("auth/login");
    }

    @GetMapping("/registration")
    public ModelAndView registrationPage(@ModelAttribute("userDto") CreateUserDto userDto) {
        return new ModelAndView("auth/registration")
                .addObject("avpDto", avpMapper.convertEntityToDto(avpService.findAll()));
    }

    @PostMapping("/registration")
    public ModelAndView registration(@ModelAttribute("userDto") @Valid CreateUserDto userDto, BindingResult result) {
        userValidator.validate(userDto, result);
        if (result.hasErrors()) {
            return new ModelAndView("auth/registration", result.getModel())
                    .addObject("avpDto", avpMapper.convertEntityToDto(avpService.findAll()));
        }
        userService.create(usermapper.convertDtoToEntity(userDto));
        if (nonNull(userDto.getImage()) && isNotEmpty(userDto.getImage().getOriginalFilename()))
            uploadService.uploadFile(userDto.getImage(), "users");
        return new ModelAndView("redirect:/auth/login?success");
    }

    @GetMapping("/reset-password")
    public ModelAndView resetPasswordPage(@ModelAttribute("emailDto") UserEmailDto emailDto) {
        return new ModelAndView("auth/resetPassword");
    }

    @PostMapping("/reset-password")
    public ModelAndView resetPassword(@ModelAttribute("emailDto") @Valid UserEmailDto emailDto, BindingResult result,
                                      HttpServletRequest request, RedirectAttributes redirectAttributes) {
        emailValidator.validate(emailDto, result);
        if (result.hasErrors()) {
            return new ModelAndView("auth/resetPassword", result.getModel());
        }
        PasswordToken token = tokenService.create(tokenMapper.convertDtoToEntity(emailDto));

        Map<String, Object> variables = new HashMap<>();
        variables.put("url", String.format("%s/%s", request.getRequestURL(), "confirm"));
        variables.put("token", token.getToken());
        mailService.sendHtml("password", variables);

        redirectAttributes.addFlashAttribute("emailDto", emailDto);
        return new ModelAndView("redirect:/auth/reset-password");
    }

    @GetMapping("/resend-password")
    public ModelAndView resendPasswordPage() {
        return new ModelAndView("auth/resendPassword");
    }

    @PutMapping("/resend-password/{id}")
    public String resendPassword(@PathVariable("id") PasswordToken token, HttpServletRequest request) {
        tokenService.refreshToken(token);

        Map<String, Object> variables = new HashMap<>();
        variables.put("url", String.format("%s/%s", request.getRequestURL(), "confirm"));
        variables.put("token", token.getToken());

        mailService.sendHtml("password", variables);
        return "redirect:/auth/resend-password";
    }

    @GetMapping("/reset-password/confirm")
    public ModelAndView resetPasswordConfirmPage(@ModelAttribute("passwordDto") UserPasswordDto passwordDto, @RequestParam(value = "token", required = false) String token) {
        PasswordToken passwordToken = tokenService.findByToken(token);
        return new ModelAndView("auth/confirmPassword")
                .addObject("token", nonNull(passwordToken) ? tokenMapper.convertEntityToDto(passwordToken) : null);
    }

    @PostMapping("/reset-password/confirm")
    public ModelAndView resetPassword(@ModelAttribute("passwordDto") @Valid UserPasswordDto passwordDto, BindingResult result,
                                      @RequestParam(value = "token") String token) {
        passwordValidator.validate(passwordDto, result);
        if (result.hasErrors()) {
            PasswordToken passwordToken = tokenService.findByToken(token);
            return new ModelAndView("auth/confirmPassword", result.getModel())
                    .addObject("token", tokenMapper.convertEntityToDto(passwordToken));
        }

        PasswordToken passwordToken = tokenService.findByToken(token);
        passwordToken.setConfirmedAt(LocalDateTime.now());
        userService.updatePassword(passwordToken.getUser(), passwordDto.getPassword());
        return new ModelAndView(String.format("redirect:/auth/reset-password/confirm?token=%s", token));
    }
}
