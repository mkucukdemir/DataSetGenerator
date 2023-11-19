/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.datamining.datasetgenerator;

import com.datamining.datasetgenerator.model.StockModel;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Asus
 */
public class StockHtmlParser {
    private static final String ROOT_DIR_PATH = "data/raw/stock/html/";

    public static void main(String[] args) throws IOException, ParseException {
        File rootDirectory = new File(ROOT_DIR_PATH);
        traverseDirectory(rootDirectory);
    }

    private static void traverseDirectory(File directory) throws IOException, ParseException {
        // List all files in the current directory
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Recursively traverse subdirectories
                    traverseDirectory(file);
                } else {
                    parseHTMLFile(file);
                }
            }
        }
    }

    private static void parseHTMLFile(File file) throws IOException, ParseException {
        Gson gson = new Gson();
        Document jDocument = Jsoup.parse(file,"UTF-8");
        // Select the table with classes 'table3' and 'table4'
            Element selectedTable = jDocument.select("table.table3.table4").first();

            if (selectedTable != null) {
                // Find the <tbody> and iterate over <tr> elements
                Elements rows = selectedTable.select("tbody tr");

                for (int i = 1; i < rows.size(); i++) {
                    Element row = rows.get(i);
                    
                    Elements cells = row.select("td");
                    
                    String abbreviation = cells.get(0).select("a").first().text();
                    String change = "unknown";
                    if(cells.get(0).hasClass("currency-up")) {
                        change = "up";
                    }
                    if(cells.get(0).hasClass("currency-down")) {
                        change = "down";
                    }
                    if(cells.get(0).hasClass("currency-flat")) {
                        change = "flat";
                    }
                    try {
                        Double closingPrice = Double.parseDouble(cells.get(1).text().replace(".", "").replace(',', '.'));
                        Double yesterdayClosingPrice = Double.parseDouble(cells.get(2).text().replace(".", "").replace(',', '.'));
                        Double percentageChange = Double.parseDouble(cells.get(3).text().replace(".", "").replace(',', '.'));
                        Double topPrice = Double.parseDouble(cells.get(4).text().replace(".", "").replace(',', '.'));
                        Double bottomPrice = Double.parseDouble(cells.get(5).text().replace(".", "").replace(',', '.'));
                        Double weightedAverage = Double.parseDouble(cells.get(6).text().replace(".", "").replace(',', '.'));
                        Long volumeInLot = Long.parseLong(cells.get(7).text().replace(".", ""));
                        Double volumeInKTL = Double.parseDouble(cells.get(8).text().replace(".", "").replace(',', '.'));
                        
                        StockModel stock = new StockModel(getDateFromFile(file), abbreviation, change, closingPrice, yesterdayClosingPrice, percentageChange, topPrice, bottomPrice, weightedAverage, volumeInLot, volumeInKTL);
                    
                        System.out.println(gson.toJson(stock));
                    
                        writeStockIntoJSONFile(stock);
                    } catch (java.lang.NumberFormatException nfe) {
                        nfe.printStackTrace();
                        System.err.println(cells.get(1).text());
                        System.err.println(cells.get(8).text());
                        System.err.println("exc");
                    }
                }
            } else {
                System.out.println("Table not found");
            }
    }

    private static Date getDateFromFile(File file) throws ParseException {
        String dirName = file.getParentFile().getName();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        try {
            // Parse the date from the file name
            return dateFormat.parse(dirName);
        } catch (ParseException e) {
            throw new ParseException("Error parsing date from file name", 0);
        }
    }

    private static void writeStockIntoJSONFile(StockModel stock) throws IOException {
        Gson gson = new Gson();
        String content = gson.toJson(stock);
        String dirPath = ROOT_DIR_PATH + "../json/";
        String filePath = dirPath + stock.getAbbreviation().toUpperCase() + ".json";
        Path path = Paths.get(filePath);

        // Check if the file exists
        if (!Files.exists(path)) {
            // If the file doesn't exist, create a new file and write content
            Files.createFile(path);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(content + "\n");
            }
        } else {
            // If the file already exists, append content to the existing file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                // 'true' parameter in the FileWriter constructor enables append mode
                writer.write(content + "\n");
            }
        }
    }
    
    private static void writeStockIntoCSVFile(StockModel stock) throws IOException {
        String content = stock.csv();
        String dirPath = ROOT_DIR_PATH + "../csv/";
        String filePath = dirPath + stock.getAbbreviation().toUpperCase() + ".csv";
        Path path = Paths.get(filePath);

        // Check if the file exists
        if (!Files.exists(path)) {
            // If the file doesn't exist, create a new file and write content
            Files.createFile(path);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(stock.csvHeader() + "\n");
                writer.write(content + "\n");
            }
        } else {
            // If the file already exists, append content to the existing file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                // 'true' parameter in the FileWriter constructor enables append mode
                writer.write(content + "\n");
            }
        }
    }
}
