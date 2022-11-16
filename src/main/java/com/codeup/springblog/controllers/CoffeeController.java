package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Coffee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/coffee")
public class CoffeeController {

    @GetMapping
    public String coffee(){
        return "coffee";
    }
//How to kill a process that is running in the background
//    Open terminal
//    type in : sudo lsof -i :8080
//    type in your computer password
//    Look at the number below where it says PID
//    then typle in: kill -9 ##### (<- here is where you would put in the number)
//    If you run sudo lsof -i :8080  again, it should have changed.


//The model object is an objet available throughout your application. Pretty much your whole application has access
// to your model object.If you put some information inside your model object as an attribute, at another point in
// your application you will have access to your model object. You can think of it somewhat as a 'cloud' that stores
// information.
    @GetMapping("/{roast}")
    public String roast(@PathVariable String roast, Model model){
        Coffee selection = new Coffee(roast, "Cool Beans");
        Coffee selection2 = new Coffee(roast, "Glory Groovy");
        selection.setOrigin("Ethiopian");
        selection2.setOrigin("Vietnam");
        List<Coffee> selections = new ArrayList<>(List.of(selection, selection2));
        model.addAttribute("selections", selections);
        return "coffee";
    }

    @PostMapping
    public String signUp(@RequestParam(name="email") String email, Model model){
        model.addAttribute("email", email);
        return "coffee";
    }


}
