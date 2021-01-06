package com.example.demo.service;

import com.example.demo.DimensionsBean;
import com.opencsv.CSVReader;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Service {
    Logger logger = LoggerFactory.getLogger(Service.class);
    public static HashMap<Integer, DimensionsBean> object_diam = new HashMap<>();

    public Service() {
        if (object_diam.size() == 0) {
            loadData();
        }
    }

    public boolean does_it_fit(double object_id, double length, double width, double height, double diameter, double depth) {
        DimensionsBean db = object_diam.get(object_id);
        return db != null && db.getHeight() == height && db.getLength() == length && db.getWidth() == width  && db.getDiameter() == diameter  && db.getDepth() == depth;
    }

    public void loadData() {
        ClassLoader classLoader = getClass().getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("static/MetObjects.csv")) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            try {
                List<String[]> list = readAll(reader);
                for (String[] line : list) {
                    try {
//                        System.out.println("Object_id: " + line[4]);
//                        System.out.println("Dimensions: " + line[32]);
                        object_diam.put(Integer.parseInt(line[4]), new DimensionsBean(line[32]));
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(e.getMessage());
                    }

                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }


        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    public List<String[]> readAll(Reader reader) throws Exception {
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list = new ArrayList<>();
        list = csvReader.readAll();
        reader.close();
        csvReader.close();
        list.remove(0); // remove header
        return list;
    }

    public static void main(String[] args) {
        Service s = new Service();
    }
}
