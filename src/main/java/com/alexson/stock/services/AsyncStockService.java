package com.alexson.stock.services;

import com.alexson.stock.models.DowJonesDataSet;
import com.alexson.stock.models.DowJonesDataSetDTO;
import com.alexson.stock.models.ResponseError;
import com.alexson.stock.models.exceptions.DatabaseException;
import com.alexson.stock.services.repository.DowJonesDataSetRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncStockService {

    private final DowJonesDataSetRepository dowJonesDataSetRepository;

    @Autowired
    public AsyncStockService(DowJonesDataSetRepository dowJonesDataSetRepository){
        this.dowJonesDataSetRepository = dowJonesDataSetRepository;
    }

    @Async("asyncExecutor")
    public CompletableFuture<String> uploadingStockToDb(MultipartFile file) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        ObjectReader objectReader = csvMapper.readerFor(DowJonesDataSetDTO.class).with(schema);
        List<DowJonesDataSet> dataSet = new ArrayList<>();
        String message = "";

        MappingIterator<DowJonesDataSetDTO> mi = objectReader.readValues(file.getInputStream());
        while(mi.hasNext()){
            DowJonesDataSet current = new DowJonesDataSetDTO().convertFromDto(mi.next());
            dataSet.add(current);
        }
        try {
            dowJonesDataSetRepository.saveAll(dataSet);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
        }catch(Exception ex){
            ResponseError error = new ResponseError(ex.getMessage(), file.getOriginalFilename());
            throw new DatabaseException(error);
        }

        return CompletableFuture.completedFuture(message);
    }

    @Async("asyncExecutor")
    public CompletableFuture<List<DowJonesDataSet>> getDataForStockTicker(String stockTicker, Integer quarter){
        List<DowJonesDataSet> results;
        try{
            results = dowJonesDataSetRepository.findByStockAndQuarterOrderById(stockTicker, quarter);
        }catch(Exception ex){
            ResponseError error = new ResponseError(ex.getMessage(), stockTicker);
            throw new DatabaseException(error);
        }
        return CompletableFuture.completedFuture(results);
    }

    @Async("asyncExecutor")
    public CompletableFuture<DowJonesDataSet> insertNewStock(DowJonesDataSet dowJonesDataSet){
        DowJonesDataSet result;
        try {
            result = dowJonesDataSetRepository.save(dowJonesDataSet);
        }catch (Exception ex){
            ResponseError error = new ResponseError(ex.getMessage(), dowJonesDataSet.toString());
            throw new DatabaseException(error);
        }
        return CompletableFuture.completedFuture(result);

    }

}
