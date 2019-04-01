package pl.arturzaczek.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.arturzaczek.demo.service.CurrencyService;

import java.sql.Struct;

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
    @GetMapping ("/info")
    public String getCurrencyInfo(){
        return "info";
    }
    @GetMapping ("/exchange")
    public String goToExchangeMenu(Model model){
        model.addAttribute("currencyList", currencyService.getCurrencyNames());
        return "exchange";
    }
    @PostMapping("/get-rate/{ex}")
    public String getExchangeRates (@PathVariable String ex, @RequestParam String amount, Model model){
        System.out.println(ex + " "+ amount);
        model.addAttribute("result", currencyService.countRate(ex, amount));
        return "exchange-result";
    }
    @GetMapping("/table")
    public String getTables (Model model){
        model.addAttribute("table", currencyService.getCurrencyList());
        return "table";
    }

}
