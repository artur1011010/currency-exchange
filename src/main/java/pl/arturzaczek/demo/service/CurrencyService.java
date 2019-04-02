package pl.arturzaczek.demo.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.arturzaczek.demo.Currency;
import pl.arturzaczek.demo.Table;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CurrencyService {

    private static final String API_URL = "http://api.nbp.pl/api";
    private RestTemplate restTemplate;


    @Autowired
    public CurrencyService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private Table[] responseToArray(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Table[].class);
    }

    private Table[] getCurrencyTable() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Currency> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(API_URL + "/exchangerates/tables/A?format=json", HttpMethod.GET, request, String.class);
        String body = response.getBody();
        return responseToArray(body);
    }

    public List<Currency> getCurrencyList() {
        Table[] table = getCurrencyTable();
        Currency[] currencies = table[0].getRates();
        return Arrays.asList(currencies);
    }

    public List<String> getCurrencyNames() {
        List<Currency> c = getCurrencyList();
        return c.stream().map(a -> a.getCurrency()).collect(Collectors.toList());
    }

    public String countRate(String name, String amount) {
        StringBuilder sb = new StringBuilder();
        List<Currency> list = getCurrencyList();
        Currency currency = list.stream().filter(a -> a.getCurrency().equals(name)).findFirst().get();
        Float floatResult = Float.parseFloat(amount) / currency.getMid();
        sb.append(floatResult).append(" ").append(currency.getCode());
        return sb.toString();
    }

    public String getTableDate (){
        Table[] table = getCurrencyTable();
        String result = "Table date: "  + table[0].getEffectiveDate();
        return result;
    }
}
