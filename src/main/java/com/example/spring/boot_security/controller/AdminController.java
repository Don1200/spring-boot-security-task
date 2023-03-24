package com.example.spring.boot_security.controller;


import com.example.spring.boot_security.model.Role;
import com.example.spring.boot_security.model.User;
import com.example.spring.boot_security.service.RoleService;
import com.example.spring.boot_security.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@RequestMapping(value = "/admin")
@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping()
    public String getAllUsers(Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList); // userList - название атрибута, которое мы передаем дальше
        // хтмл, а второе это значение, это то что  что мы получили: userList = userService.getAllUsers();
        return "admin";
    }

    @GetMapping(value = "/add")
    public String newUser(Model model) {
        User user = new User();
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "add";
    }

    @PostMapping(value = "/save")
    public String addUser(@ModelAttribute("addUser") User user,
                          @RequestParam(value = "roles", required = false) String[] roles) {
        Set<Role> rolesSet = new HashSet<>();
        for (String s : roles) {
            rolesSet.add(roleService.findRoleByName(s));
        }

        user.setRoles(rolesSet);
        String newPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(newPass);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.findAllRoles());
        return "edit";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user,
                           @RequestParam(value = "roles", required = false) String[] roles) {
        Set<Role> rolesSet = new HashSet<>();
        for (String s : roles) {
            rolesSet.add(roleService.findRoleByName(s));
        }
        user.setRoles(rolesSet);
        String newPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(newPass);
        userService.editUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(userService.getUserById(id));
        return "redirect:/admin";
    }


}
