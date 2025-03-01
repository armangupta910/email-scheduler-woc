package com.woc.emailscheduler.controller;
//reg
import com.woc.emailscheduler.EmailViaReg;
import com.woc.emailscheduler.entity.RegistrationDetails;
import com.woc.emailscheduler.model.RegisterDto;
import com.woc.emailscheduler.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.woc.emailscheduler.repository.AppUserRepository;
@Controller
public class RegistrationController {

    @Autowired
    private AppUserRepository repo; // Service to handle registration logic

    @GetMapping("/register")
    public String register(Model model){
        RegisterDto registerDto=new RegisterDto();
        model.addAttribute(registerDto);
        model.addAttribute("success",false);

        return "register";
    }

    @PostMapping("/register")
    public String register(
            Model model,
            @Valid @ModelAttribute RegisterDto registerDto,
            BindingResult result
    ) {
        RegistrationDetails rd=repo.findByEmailId(registerDto.getEmailId());
        if(rd!=null){
            result.addError(new FieldError("registerDto", "EmailId", "This Email Id is already registered."));
        }

        if(result.hasErrors()){
            return "register";
        }

        try{
            //new acc
            var bCryptEncoder=new BCryptPasswordEncoder();
            RegistrationDetails newUser=new RegistrationDetails();
            newUser.setName(registerDto.getName());
            newUser.setPhoneNumber(registerDto.getPhoneNumber());
            newUser.setDesignation(registerDto.getDesignation());
            newUser.setEmailId(registerDto.getEmailId());
            newUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));
           // newUser.setCreatedAt(new Date());
            repo.save(newUser);

            model.addAttribute("registerDto",new RegisterDto());
            model.addAttribute("success",true);

        }
        catch (Exception e){
            result.addError(new FieldError("registerDto", "EmailId", e.getMessage()));
        }

        return "register";
    }

}
