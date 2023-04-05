package com.attractorschool.imurab.controller.view;

import com.attractorschool.imurab.dto.avp.AvpCreateDto;
import com.attractorschool.imurab.dto.avp.AvpDto;
import com.attractorschool.imurab.dto.avp.AvpUpdateDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.mapper.avp.AvpMapper;
import com.attractorschool.imurab.service.avp.AvpService;
import com.attractorschool.imurab.util.validator.avp.AvpUpdateValidator;
import com.attractorschool.imurab.util.validator.avp.AvpValidator;
import com.attractorschool.imurab.util.validator.avp.CreateAvpValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/avp")
public class AvpController {
    private final AvpService avpService;
    private final AvpValidator validator;
    private final CreateAvpValidator createAvpValidator;
    private final AvpUpdateValidator updateValidator;
    private final AvpMapper avpMapper;

    @GetMapping()
    public ModelAndView indexPage() {
        return new ModelAndView("avp/index");
    }

    @PostMapping("/season/create")
    public ModelAndView createSeason(@RequestBody @Valid AvpUpdateDto avpUpdateDto, BindingResult result) {
        avpUpdateDto.setEnd(false);
        validator.validate(avpUpdateDto, result);
        if (result.hasErrors()) {
            return new ModelAndView("avp/", result.getModel());
        }
        avpService.setSeasonStart(avpUpdateDto);
        return new ModelAndView("avp/");
    }

    @PostMapping("/season/close")
    public ModelAndView closeSeason(@RequestBody @Valid AvpUpdateDto avpUpdateDto, BindingResult result) {
        avpUpdateDto.setEnd(true);
        validator.validate(avpUpdateDto, result);
        if (result.hasErrors()) {
            return new ModelAndView("avp/", result.getModel());
        }
        avpService.setSeasonEnd(avpUpdateDto);
        return new ModelAndView("avp/");
    }

    @PostMapping("/create")
    public ModelAndView createAvp(@ModelAttribute("avpCreateDto") @Valid AvpCreateDto avpCreateDto, BindingResult result) {
        createAvpValidator.validate(avpCreateDto, result);
        if (result.hasErrors()) {
            return new ModelAndView("avp/create", result.getModel());
        }
        avpService.create(avpMapper.convertAvpCreateDtoToAvp(avpCreateDto));
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/create")
    public ModelAndView createPage(@ModelAttribute("avpCreateDto") AvpCreateDto avpCreateDto) {
        return new ModelAndView("avp/create");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editPage(@PathVariable(value = "id") AVP avp) {
        return new ModelAndView("avp/edit")
                .addObject("avpDto", avpMapper.convertEntityToFrontDto(avp));
    }

    @PutMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable("id") AVP avp, @Valid @ModelAttribute AvpDto avpDto, BindingResult result) {
        updateValidator.validate(avpDto, result);
        if (result.hasErrors()) {
            return new ModelAndView("avp/edit", result.getModel());
        }
        avpService.updateAvp(avp, avpDto);
        return new ModelAndView("redirect:/avp/");
    }
}
