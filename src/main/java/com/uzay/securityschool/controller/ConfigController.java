package com.uzay.securityschool.controller;

//hatırlatma amaçlı

import com.uzay.securityschool.config.ConfigProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ConfigController {

    private final ConfigProperty configProperty;

    public ConfigController(ConfigProperty configProperty) {
        this.configProperty = configProperty;
    }

    @GetMapping("/admin/config-yazdir")
    public ResponseEntity<String> getAdminConfigYazdir() {
        Map<String,String> listemap = new HashMap<>();
        listemap.put("isim", configProperty.getIsim());
        listemap.put("soyad", configProperty.getSoyad());
        List<String> ulkeler = configProperty.getUlkeler();
       ulkeler.stream().forEach(item ->
               listemap.put("liste elamanı", item));
       return ResponseEntity.ok().body(listemap.toString());
    }

}
