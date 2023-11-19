/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.datamining.datasetgenerator;

import com.datamining.datasetgenerator.model.KAPModel;
import com.datamining.datasetgenerator.model.StockModel;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Asus
 */
public class KAPHtmlParser {
    private static final String ROOT_DIR_PATH = "data/raw/kap/html/tr/";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    
    public static void main(String[] args) {
        File directory = new File(ROOT_DIR_PATH);
        traverseDirectory(directory);
        
    }

    private static void traverseDirectory(File directory) {
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

    private static void parseHTMLFile(File input) {
        try {
            // Parse the HTML document using JSoup
            Document doc = Jsoup.parse(input);

            // Company Name
            String companyName = doc.select("#disclosureContent div.type-medium.bi-sky-black a").text();
            System.out.println("Company Name: " + companyName);

            // Stock Names
            String stockNames = doc.select("#disclosureContent div.type-medium.bi-dim-gray").text().replace(",", "");
            System.out.println("Stock Names: " + stockNames);

            // Attachments
            String attachments = doc.select("#disclosureContent div.w-clearfix.modal-attachments a.modal-attachment").text();
            System.out.println("Attachments: " + attachments);

            // Date, Notification Type, Year, Period
            Elements briefSumCols = doc.select("#disclosureContent div.w-col.w-col-3.modal-briefsumcol");
            
            Date date;
            try {
                date = DATE_FORMAT.parse(briefSumCols.get(0).select("div.type-medium.bi-sky-black").text());
            } catch (ParseException ex) {
                Logger.getLogger(KAPHtmlParser.class.getName()).log(Level.SEVERE, null, ex);
                date = null;
            }
            String notificationType = briefSumCols.get(1).select("div.type-medium.bi-sky-black").text();
            String year = briefSumCols.get(2).select("div.type-medium.bi-sky-black").text();
            String period = briefSumCols.get(3).select("div.type-medium.bi-sky-black").text();
            System.out.println("Date: " + date);
            System.out.println("Notification Type: " + notificationType);
            System.out.println("Year: " + year);
            System.out.println("Period: " + period);
            
            // Title
            String title = "\"" + doc.select("#disclosureContent div.modal-info h1").text().replace("A+ A-", "").trim().split("    ")[0] + "\"";
            System.out.println("Title: " + title);

            // Description
            String description = "\"" + doc.select("#disclosureContent div.modal-info div.modal-infosub").text().replace("İmza {{$index + 1}} {{signature.name}} {{signature.title}} {{signature.company}} {{signature.signDate}} {{signature.explanation}}", "").trim() + "\"";
            System.out.println("Description: " + description);
            
            KAPModel kap = new KAPModel(companyName, stockNames, attachments, date, notificationType, year, period, title, description);
            
            writeKAPIntoFile(kap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void writeKAPIntoFile(KAPModel kap) throws IOException {
        
        String[] labels = extractStockNames(kap);
        
        if(labels==null) {
            writeJSON(kap,"NULL");
            writeCSV(kap,"NULL");
        }
        else{
            for (String label : labels) {
                writeJSON(kap,label);
                writeCSV(kap,label);
            }
        }
        
    }
    
    private static void writeCSV(KAPModel kap, String filename) throws IOException {
        String content = kap.csv();
        String dirPath = ROOT_DIR_PATH + "../../csv/";
        String filePath  = dirPath + filename + ".csv";
        Path path = Paths.get(filePath);

        // Check if the file exists
        if (!Files.exists(path)) {
            // If the file doesn't exist, create a new file and write content
            Files.createFile(path);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,Charset.forName("ISO-8859-9")))) {
                writer.write(kap.csvHeader() + "\n");
                writer.write(content + "\n");
            }
        } else {
            // If the file already exists, append content to the existing file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,Charset.forName("ISO-8859-9"), true))) {
                // 'true' parameter in the FileWriter constructor enables append mode
                writer.write(content + "\n");
            }
        }
    }

    private static String[] extractStockNames(KAPModel kap) {
        
        if(kap.getStockNames().isBlank()){
            
            //Pattern pattern = Pattern.compile("Related Companies \\[([A-Z]{4,5}(?:, [A-Z]{4,5})*)\\]");
            // Define the pattern for multiple labels with or without square brackets
            //Pattern pattern = Pattern.compile("(?:Related Companies|İlgili Şirketler) \\[([A-Z]{4,5}(?:, [A-Z]{4,5})*)\\]|(?:İlgili Şirketler) ([A-Z]{4,5}(?:, [A-Z]{4,5})*)");
            Pattern pattern = Pattern.compile("(?:Related Companies|İlgili Şirketler) \\[([A-Z0-9]{3,5}(?:, [A-Z0-9]{3,5})*)\\]|(?:İlgili Şirketler) ([A-Z0-9]{3,5}(?:, [A-Z0-9]{3,5})*)");

            // Create a matcher
            Matcher matcher = pattern.matcher(kap.getDescription());

            // Check if the pattern is found
            if (matcher.find()) {
                // Extract and print the labels
                String labelsGroup = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
                String[] labels = labelsGroup.split(", ");

                System.out.println("Labels found: " + String.join(", ", labels));
                return labels;
            } else {
                System.out.println("No labels found in the input: " + kap.getDescription());
                return null;
            }
        }
        else{
            return kap.getStockNames().toUpperCase().split(" ");
        }
        
    }

    private static void writeJSON(KAPModel kap, String filename) throws IOException {
        Gson gson = new Gson();
        String content = gson.toJson(kap);
        String dirPath = ROOT_DIR_PATH + "../../json/";
        String filePath = dirPath + filename + ".json";
        
        Path path = Paths.get(filePath);

        // Check if the file exists
        if (!Files.exists(path)) {
            // If the file doesn't exist, create a new file and write content
            Files.createFile(path);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,Charset.forName("ISO-8859-9")))) {
                writer.write(content + "\n");
            }
        } else {
            // If the file already exists, append content to the existing file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,Charset.forName("ISO-8859-9"), true))) {
                // 'true' parameter in the FileWriter constructor enables append mode
                writer.write(content + "\n");
            }
        }
    }
}
