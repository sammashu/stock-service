package com.alexson.stock.controllers;


import com.alexson.stock.models.DowJonesDataSet;
import com.alexson.stock.models.DowJonesDataSetDTO;
import com.alexson.stock.services.AsyncStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/stock")
public class StockController {

    private final AsyncStockService asyncStockService;

    @Autowired
    public StockController(AsyncStockService asyncStockService){
        this.asyncStockService = asyncStockService;
    }

    @PostMapping("/upload")
    public CompletableFuture<ResponseEntity<String>> uploadStock(@RequestParam("file") MultipartFile file) {
        try {
            String result = asyncStockService.uploadingStockToDb(file).get();
            return CompletableFuture.supplyAsync(() -> ResponseEntity.ok().body(result));
        } catch (Exception e) {
            String message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return CompletableFuture.supplyAsync(() ->
                    ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message));
        }
    }

    @GetMapping("/findStockTickerByQuarter")
    public CompletableFuture<ResponseEntity<List<DowJonesDataSet>>> getDataForStockTickerByQuarter(@RequestParam("stockTicker") String stockTicker, @RequestParam("quarter") Integer quarter) {
        final List<DowJonesDataSet> results;
        try {
            results = asyncStockService.getDataForStockTicker(stockTicker, quarter).get();
            return CompletableFuture.supplyAsync(() -> ResponseEntity.ok().body(results));
        }catch(Exception e){
            return CompletableFuture.supplyAsync(() ->
                    ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ArrayList<>()));
        }

    }

    @PostMapping("/newStockTicker")
    public CompletableFuture<ResponseEntity<DowJonesDataSet>> insertNewStockTicker(@RequestBody DowJonesDataSetDTO dowJonesDataSetDTO){
        final DowJonesDataSet result;
        try {
            result = asyncStockService.insertNewStock(dowJonesDataSetDTO.convertFromDto(dowJonesDataSetDTO)).get();
            return CompletableFuture.supplyAsync(() -> ResponseEntity.ok().body(result));
        }catch(Exception e){
            return CompletableFuture.supplyAsync(() ->
                    ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new DowJonesDataSet()));
        }
    }

}
