/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.datamining.datasetgenerator;

import java.io.IOException;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RenameDirectories {

    public static void main(String[] args) {
        String rootDirectoryPath = "data/raw/stock/old";

        try {
            renameDirectories(rootDirectoryPath);
            System.out.println("Directories renamed successfully.");
        } catch (IOException | ParseException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void renameDirectories(String rootDirectoryPath) throws IOException, ParseException {
        Path rootDirectory = Paths.get(rootDirectoryPath);

        if (Files.exists(rootDirectory) && Files.isDirectory(rootDirectory)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootDirectory)) {
                for (Path directory : directoryStream) {
                    if (Files.isDirectory(directory)) {
                        // Parse the current directory name
                        String currentDirectoryName = directory.getFileName().toString();
                        Date currentDate = parseDate(currentDirectoryName);

                        // Format the new directory name
                        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyyMMdd");
                        String newDirectoryName = newDateFormat.format(currentDate);

                        // Rename the directory
                        Path newDirectoryPath = directory.getParent().resolveSibling(newDirectoryName);
                        Files.move(directory, newDirectoryPath);
                    }
                }
            }
        } else {
            throw new IOException("Invalid directory path: " + rootDirectoryPath);
        }
    }

    private static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
        return dateFormat.parse(dateString);
    }
}

