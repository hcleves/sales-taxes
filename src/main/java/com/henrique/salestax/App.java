package com.henrique.salestax;

import org.apache.commons.cli.Options;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.HelpFormatter;

public class App {
    public static void main(String[] args) throws IOException {
        String inputFile = null;
        String outputFile = null;
        Options options = new Options();

        options.addOption("h", "help", false, "Print this message");
        options.addOption("i", "input", true, "Input file");
        options.addOption("o", "output", true, "Output file");

        CommandLineParser parser = new DefaultParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            HelpFormatter formatter = new HelpFormatter();

            if (line.hasOption("help")) {
                formatter.printHelp(
                        "java -jar .\\target\\sales-taxes-1.0-SNAPSHOT-jar-with-dependencies.jar -i <filename> [-o <filename>]",
                        options);
                System.exit(0);
            }

            if (line.hasOption("input")) {
                inputFile = line.getOptionValue("input");
            } else {
                System.out.println("Input file not specified. Use -h for help.");
                System.exit(0);
            }

            if (line.hasOption("output")) {
                outputFile = line.getOptionValue("output");
            }

        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }

        String working_dir = new java.io.File(".").getCanonicalPath();
        String input_path = new java.io.File(inputFile).getCanonicalPath();
        if (!input_path.startsWith(working_dir)) {
            System.out.println("Input file must be in the working directory.");
            System.exit(0);
        }

        if (outputFile != null) {
            String output_path = new java.io.File(outputFile).getCanonicalPath();
            if (!output_path.startsWith(working_dir)) {
                System.out.println("Output file must be in the working directory.");
                System.exit(0);
            }
        }

        String file_content = Files.readString(Paths.get(input_path), StandardCharsets.US_ASCII);

        try {
            Receipt receipt = new Receipt(file_content);
            if (outputFile == null) {
                System.out.println(receipt.toString());
            } else {
                Path output_path = Paths.get(outputFile);
                Files.write(output_path, receipt.toString().getBytes());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input file. Use -h for help.");
            System.exit(0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid input file. Use -h for help.");
            System.exit(0);
        }

    }
}
