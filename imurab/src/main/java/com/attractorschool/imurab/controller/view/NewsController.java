package com.attractorschool.imurab.controller.view;

import com.attractorschool.imurab.dto.news.CreateNewsDto;
import com.attractorschool.imurab.dto.news.EditNewsDto;
import com.attractorschool.imurab.dto.news.avp.CreateNewsAvpDto;
import com.attractorschool.imurab.dto.news.avp.EditNewsAvpDto;
import com.attractorschool.imurab.entity.News;
import com.attractorschool.imurab.entity.NewsAvp;
import com.attractorschool.imurab.mapper.avp.AvpMapper;
import com.attractorschool.imurab.mapper.news.NewsMapper;
import com.attractorschool.imurab.mapper.news.avp.NewsAvpMapper;
import com.attractorschool.imurab.service.avp.AvpService;
import com.attractorschool.imurab.service.file.FileUploadService;
import com.attractorschool.imurab.service.news.NewsService;
import com.attractorschool.imurab.service.news.avp.NewsAvpService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    private final NewsAvpService newsAvpService;
    private final NewsMapper newsMapper;
    private final AvpService avpService;
    private final AvpMapper avpMapper;
    private final NewsAvpMapper newsAvpMapper;
    private final FileUploadService uploadService;

    @GetMapping
    public ModelAndView indexPage() {
        return new ModelAndView("news/index");
    }

    @GetMapping("/{id}")
    public ModelAndView newsPage(@PathVariable(value = "id") News news) {
        return new ModelAndView("news/news")
                .addObject("newsDto", newsMapper.convertEntityToDto(news));
    }

    @GetMapping("/create")
    public ModelAndView createPage(@ModelAttribute("newsDto") CreateNewsDto newsDto) {
        return new ModelAndView("news/create");
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute("newsDto") @Valid CreateNewsDto newsDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("news/create", result.getModel());
        }
        newsService.create(newsMapper.convertDtoToEntity(newsDto));
        if (nonNull(newsDto.getImage()) && Strings.isNotEmpty(newsDto.getImage().getOriginalFilename()))
            uploadService.uploadFile(newsDto.getImage(), "news");
        return new ModelAndView("news/create");
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editPage(@PathVariable(value = "id") News news) {
        return new ModelAndView("news/edit")
                .addObject("newsDto", newsMapper.convertEntityToDto(news));
    }

    @PutMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable(value = "id") News news,
                             @Valid @ModelAttribute("newsDto") EditNewsDto newsDto,
                             BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("news/edit", result.getModel());
        }
        newsService.update(news, newsDto);
        if (nonNull(newsDto.getImage()) && Strings.isNotEmpty(newsDto.getImage().getOriginalFilename()))
            uploadService.uploadFile(newsDto.getImage(), "news");

        return new ModelAndView("redirect:/news");
    }

    @GetMapping("/avp")
    public ModelAndView indexNewsAvpPage() {
        return new ModelAndView("news/avp/index");
    }

    @GetMapping("/avp/{id}")
    public ModelAndView NewsAvpPage(@PathVariable(value = "id") NewsAvp newsAvp) {
        return new ModelAndView("news/avp/news")
                .addObject("newsDto", newsAvpMapper.convertToNewsAvpDto(newsAvp));
    }

    @GetMapping("/avp/create")
    public ModelAndView newsAvpCreatePage(@ModelAttribute("newsDto") CreateNewsAvpDto newsAvpDto) {
        return new ModelAndView("news/avp/create")
                .addObject("avp", avpMapper.convertEntityToDto(avpService.findAll()));
    }

    @PostMapping("/avp/create")
    public ModelAndView newsAvpCreate(@ModelAttribute("newsDto") @Valid CreateNewsAvpDto newsAvpDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("news/avp/create")
                    .addObject(result.getModel())
                    .addObject("avp", avpMapper.convertEntityToDto(avpService.findAll()));
        }
        newsAvpService.create(newsAvpMapper.convertToEntity(newsAvpDto));
        uploadService.uploadFile(newsAvpDto.getImage(), "news");
        return new ModelAndView("redirect:/news/avp");
    }

    @GetMapping("/avp/{id}/edit")
    public ModelAndView newsAvpEditPage(@PathVariable(value = "id") NewsAvp newsAvp) {
        return new ModelAndView("news/avp/edit")
                .addObject("newsDto", newsAvpMapper.convertToEditNewsAvpDto(newsAvp))
                .addObject("avp", avpMapper.convertEntityToDto(avpService.findAll()));
    }

    @PutMapping("/avp/{id}/edit")
    public ModelAndView newsAvpEdit(@PathVariable(value = "id") NewsAvp newsAvp,
                                    @ModelAttribute("newsDto") @Valid EditNewsAvpDto newsAvpDto,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("news/avp/edit")
                    .addObject(result.getModel())
                    .addObject("avp", avpMapper.convertEntityToDto(avpService.findAll()));
        }
        newsAvpService.update(newsAvp, newsAvpDto);
        if (nonNull(newsAvpDto.getImage()) && Strings.isNotEmpty(newsAvpDto.getImage().getOriginalFilename()))
            uploadService.uploadFile(newsAvpDto.getImage(), "news");
        return new ModelAndView("redirect:/news/avp");
    }
}
