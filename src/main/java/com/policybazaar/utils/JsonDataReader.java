package com.policybazaar.utils;

import com.google.gson.Gson;
import com.policybazaar.models.CarInsuranceFormData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;


public class JsonDataReader {
    
    private static final Logger logger = LogManager.getLogger(JsonDataReader.class);
    private static final Gson gson = new Gson();
    
   
    public static CarInsuranceFormData readCarInsuranceData(String fileName) {
        try {
            logger.debug("Reading JSON data from file: {}", fileName);
            InputStream inputStream = JsonDataReader.class.getResourceAsStream("/testdata/" + fileName);
            if (inputStream == null) {
                logger.error("JSON file not found: {}", fileName);
                throw new RuntimeException("JSON file not found: " + fileName);
            }
            
            InputStreamReader reader = new InputStreamReader(inputStream);
            CarInsuranceFormData data = gson.fromJson(reader, CarInsuranceFormData.class);
            reader.close();
            
            logger.info("Successfully loaded data from: {}", fileName);
            return data;
            
        } catch (Exception e) {
            logger.error("Error reading JSON file '{}': {}", fileName, e.getMessage(), e);
            throw new RuntimeException("Error reading JSON file: " + fileName, e);
        }
    }
} 