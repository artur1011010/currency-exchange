package pl.arturzaczek.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.arturzaczek.demo.service.CurrencyService;


@Controller
public class MainController {

    private CurrencyService currencyService;

    @Autowired
    public MainController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = {"/", "/index", "/home"})
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/info")
    public String getCurrencyInfo() {
        return "info";
    }

    @GetMapping("/exchange")
    public String goToExchangeMenu(Model model) {
        model.addAttribute("currencyList", currencyService.getCurrencyNames());
        return "exchange";
    }

    @PostMapping("/get-rate")
    public String getExchangeRates(@RequestParam String currency, @RequestParam String amount, Model model) {
        System.out.println(currency + " " + amount);
        model.addAttribute("currencyList", currencyService.getCurrencyNames());
        model.addAttribute("result", currencyService.countRate(currency, amount));
        return "exchange";
    }

    @GetMapping("/table")
    public String getTables(Model model) {
        model.addAttribute("date", currencyService.getTableDate());
        model.addAttribute("table", currencyService.getCurrencyList());
        return "table";
    }

}
