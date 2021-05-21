package br.com.pokeapi.PokeAPI.controller;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class RequestController {
    DataConsumer data = new DataConsumer();
    @RequestMapping("/")
    public List<JSONObject> empty(){
        return data.getData();
    }

}
