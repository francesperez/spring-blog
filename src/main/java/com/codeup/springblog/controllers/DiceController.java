package com.codeup.springblog.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/roll-dice")
public class DiceController {

    @GetMapping
    public String rollDice(){
        return "roll-dice";
    }

    @GetMapping("/{guess}")
    public String guessedNumber(@PathVariable int randomNumber, Model model){
    model.addAttribute("randomNumber", (int)(6.0 * Math.random()) + 1);
    return "roll-dice";
    }

    @PostMapping
    public String clickNumber(@RequestParam(name="guess") String guess, Model model){
        model.addAttribute("guess", guess);
        return "roll-dice";
    }



}
