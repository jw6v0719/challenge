package com.example.demo.api;

import com.example.demo.service.Service;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Api {

    public static Service s = new Service();

    @RequestMapping("/api")
    public boolean does_it_fit(@RequestParam double object_id,
                               @RequestParam double length,
                               @RequestParam double width,
                               @RequestParam double height,
                               @RequestParam double diameter,
                               @RequestParam double depth) {
        return s.does_it_fit(object_id, height, width, length, diameter, depth);
    }

    @RequestMapping("/")
    public String home() {

        return "Usage: /api?object_id={object_if}&length={length}&width={width}&height={height}&diameter={diameter}&depth={depth}";
    }
}
