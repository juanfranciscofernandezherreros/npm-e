package com.fernandez.fixtures;

import com.fernandez.fixtures.dao.urls.UrlsDAO;
import com.fernandez.fixtures.service.NpmStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TuControlador {

    @Autowired
    private NpmStartService npmStartService;

    @GetMapping("/findUrlsContainingString/{searchString}")
    public List<UrlsDAO> findUrlsContainingString(@PathVariable String searchString) {
        return npmStartService.findUrlsContainingString(searchString);
    }
}

