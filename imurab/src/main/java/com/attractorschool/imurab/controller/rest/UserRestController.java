package com.attractorschool.imurab.controller.rest;

import com.attractorschool.imurab.dto.user.UserDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.User;
import com.attractorschool.imurab.mapper.user.UserMapper;
import com.attractorschool.imurab.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UserDto>> getUsers(@RequestParam(name = "page") int page,
                                                  @RequestParam("orderBy") String orderBy,
                                                  @RequestParam("order") String order,
                                                  @RequestParam(value = "search", required = false) String search) {
        if (nonNull(search)) {
            return ResponseEntity.ok().body(userService.findAllBySearch(search, PageRequest.of(page, 5,
                    Sort.by(Sort.Direction.fromString(order), orderBy))).map(userMapper::convertEntityDto));
        }
        return ResponseEntity.ok().body(userService.findAll(PageRequest.of(page, 10,
                Sort.by(Sort.Direction.fromString(order), orderBy))).map(userMapper::convertEntityDto));
    }

    @GetMapping(value = "/avp/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UserDto>> getUsersAvp(@PathVariable(value = "id") AVP avp,
                                                     @RequestParam(value = "search", required = false) String search,
                                                     Pageable pageable) {
        if (nonNull(search)) {
            return ResponseEntity.ok().body(userService.findAllByAvpAndSearch(pageable, avp, search).map(userMapper::convertEntityDto));
        }

        return ResponseEntity.ok().body(userService.findAllByAvp(pageable, avp).map(userMapper::convertEntityDto));
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") User user) {
        return ResponseEntity.ok().body(userMapper.convertEntityDto(userService.delete(user)));
    }

    @PutMapping(value = "/activation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> activationUser(@PathVariable("id") User user) {
        return ResponseEntity.ok().body(userMapper.convertEntityDto(userService.activation(user)));
    }

    @PutMapping(value = "/deactivation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> deactivationUser(@PathVariable("id") User user) {
        return ResponseEntity.ok().body(userMapper.convertEntityDto(userService.deactivation(user)));
    }
}
