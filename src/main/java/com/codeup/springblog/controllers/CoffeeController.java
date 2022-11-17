package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Coffee;
import com.codeup.springblog.repositories.CoffeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/coffee")
public class CoffeeController {

//    Dependency injection is when we use a constructor to stick an object into another object every time that object
//    is created. Notice, here we have overridden the coffee controller constructor, and each time it is instantiated,
//    the CoffeeRepository
    private final CoffeeRepository coffeeDao;

    public CoffeeController(CoffeeRepository coffeeDao) {
        this.coffeeDao = coffeeDao;
    }

    @GetMapping
    public String coffee(){
        return "coffee";
    }
//How to kill a process that is running in the background
//    Open terminal
//    type in : sudo lsof -i :8080
//    type in your computer password
//    Look at the number below where it says PID
//    then type in: kill -9 ##### (<- here is where you would put in the number)
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


    @GetMapping("/new")
    public String addCoffeeForm(){
        return "create-coffee";
    }

    @PostMapping("/new")
    public String addCoffee(@RequestParam(name="roast") String roast, @RequestParam(name="origin") String origin,
                            @RequestParam(name="brand") String brand){
        Coffee coffee = new Coffee(roast, origin, brand);
        coffeeDao.save(coffee);
        return "redirect:/coffee/all-coffees";
    }

    @GetMapping("/all-coffees")
    public String allCoffees(Model model){
        List<Coffee> coffees = coffeeDao.findAll();
        model.addAttribute("coffees", coffees);
        return "all-coffees";
    }


    @PostMapping
    public String signUp(@RequestParam(name="email") String email, Model model){
        model.addAttribute("email", email);
        return "coffee";
    }


}
