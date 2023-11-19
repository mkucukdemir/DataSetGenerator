/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.datamining.datasetgenerator;

import com.datamining.datasetgenerator.pojo.MemberDisclosureQueryPayload;
import com.datamining.datasetgenerator.pojo.MemberDisclosureQueryResponse;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author Asus
 */
public class DataSetGenerator {

    public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
        //fetchHTMLDocs();
        fetchBIST();
    }
    
    public static String fetchHtml(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to GET
        connection.setRequestMethod("GET");

        // Get the response code
        int responseCode = connection.getResponseCode();

        // Check if the request was successful (HTTP 200)
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response content
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder htmlContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                htmlContent.append(line);
            }

            reader.close();

            return htmlContent.toString();
        } else {
            throw new IOException("Failed to fetch HTML. Response code: " + responseCode);
        }
    }
    
    public static void saveHtmlToFile(String htmlContent, String fileName) throws IOException {
        try (Writer writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(htmlContent);
        }
    }

    private static List<MemberDisclosureQueryResponse> getMemberDisclosureQueryResponseList() throws MalformedURLException, IOException {
        MemberDisclosureQueryPayload payload = new MemberDisclosureQueryPayload(
                "2022-11-15",
                "2023-11-15",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                new String[0],
                new String[]{
                    "4028e4a241558bd9014155dde60a05e7",
                    "4028e4a241a25fcb0141a276cda10189",
                    "4028e4a140f2ed7201412ac9ca5d06e6",
                    "8acae2c578080f31017811f4dca6032d",
                    "4028e4a140ee35c00140ee5d194a0055",
                    "8acae2c57afb2d70017bdf2f5af067ed",
                    "8acae2c47f7cb49201802cedfbb46d26",
                    "8acae2c452495e0b0152ef57ae190f85",
                    "4028e4a1416e696301416ec5743e289d",
                    "4028e4a140f2ed720141080991640264",
                    "4028e4a1415f4d9b0141603d6cec410f",
                    "4028e4a246b4c5a90147f8ed32b0722f",
                    "4028e4a14184e9c9014198033c094251",
                    "4028e4a246b4c5a90147fcd473d37f85",
                    "4028e4a2415f4f8401415ff644c5341c",
                    "4028e4a140f2ed7201410821418e027d",
                    "4028e4a240f2ef4c01412ad919a504dd",
                    "4028e4a241a25fcb0141a7c3f3302405",
                    "4028e4a140f2ed7201411682b0cb05c6"
                },
                new String[0],
                new String[0],
                "",
                "",
                "",
                "IGS",
                "N",
                "",
                new String[0]);

        Gson gson = new Gson();
        URL url = new URL("https://www.kap.org.tr/tr/api/memberDisclosureQuery");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");

        con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        con.setRequestProperty("Accept", "application/json, text/plain, */*");

        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = gson.toJson(payload).getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        List<MemberDisclosureQueryResponse> qResponse;
        Type listType = new TypeToken<ArrayList<MemberDisclosureQueryResponse>>() {
        }.getType();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
            qResponse = gson.fromJson(response.toString(), listType);
        }
        return qResponse;
    }

    private static void fetchHTMLDocs() throws IOException, InterruptedException {
        System.out.println("fetchHTMLDocs is being started...");
        List<MemberDisclosureQueryResponse> qResponse = getMemberDisclosureQueryResponseList();
        
        for (MemberDisclosureQueryResponse memberDisclosureQueryResponse : qResponse) {
            Path filePath = Paths.get("data/raw/tr/", memberDisclosureQueryResponse.getDisclosureIndex() + ".html");

            if (Files.exists(filePath)) {
                continue;
            }
            
            Thread.sleep(4000);
            String address = "https://www.kap.org.tr/tr/Bildirim/" + memberDisclosureQueryResponse.getDisclosureIndex();
            String doc = fetchHtml(address);
            saveHtmlToFile(doc, "data/raw/tr/" +memberDisclosureQueryResponse.getDisclosureIndex() + ".html");
        }

        System.out.println("...fetchHTMLDocs is done!");
    }

    private static void fetchBIST() throws InterruptedException, IOException {
        System.out.println("fetchBIST is being started...");
        
        // Set the initial date
        int year = 2022;
        int month = 11;
        int day = 14;

        // Set the final date
        int endYear = 2023;
        int endMonth = 11;
        int endDay = 15;

        // Traverse the days
        while (year<endYear || (year == endYear && month < endMonth) || (year==endYear && month==endMonth && day<=endDay)) {
            // Process the current date (replace this with your logic)
            System.out.printf("%d-%02d-%02d%n", year, month, day);

            // Move to the next day
            day++;
            if (day > daysInMonth(year, month)) {
                day = 1;
                month++;
                if (month > 12) {
                    month = 1;
                    year++;
                }
            }
            createDirectoryIfNotExists("data/raw/stock/" + day + "_" + month + "_" + year);
            for(int pagenum=1;pagenum<13;pagenum++) {
                Path filePath = Paths.get("data/raw/stock/" + day + "_" + month + "_" + year + "/" + pagenum + ".html");

                if (Files.exists(filePath)) {
                    continue;
                }
                Thread.sleep(4000);
                String address = "https://uzmanpara.milliyet.com.tr/borsa/gecmis-kapanislar/?Pagenum=" + pagenum + "&tip=Hisse&gun=" + day + "&ay=" + month + "&yil=" + year + "&Harf=-1";
                String doc = fetchHtml(address);
                saveHtmlToFile(doc, "data/raw/stock/" + day + "_" + month + "_" + year + "/" + pagenum + ".html");
            }
            
        }
        
        System.out.println("...fetchBIST is done!");
    }

    // Helper method to get the number of days in a month
    private static int daysInMonth(int year, int month) {
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
            default:
                return 31;
        }
    }
    
    private static void createDirectoryIfNotExists(String directoryPath) {
        Path path = Paths.get(directoryPath);

        // Check if the directory already exists
        if (!Files.exists(path)) {
            try {
                // Create the directory and its parent directories if necessary
                Files.createDirectories(path);
                System.out.println("Directory created: " + directoryPath);
            } catch (IOException e) {
                // Handle directory creation failure
                e.printStackTrace();
            }
        } else {
            System.out.println("Directory already exists: " + directoryPath);
        }
    }
}
