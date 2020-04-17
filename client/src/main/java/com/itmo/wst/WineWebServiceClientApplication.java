package com.itmo.wst;

import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.itmo.wst.wsdl.*;

@SpringBootApplication
public class WineWebServiceClientApplication {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\033[1;30m";
    private static final String ANSI_RED = "\033[1;31m";
    private static final String ANSI_GREEN = "\033[1;32m";

    public static void main(String[] args) {
        SpringApplication.run(WineWebServiceClientApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(WineClient wineClient) {
        return args -> {
            Scanner in = new Scanner(System.in);

            while (true) {
                printRegular("Enter an operation number:\n\t1.Get wine\n\t2.Create wine\n\t3.Update wine\n\t4.Delete wine\n\t5.Find wines\n\t6.Exit");
                switch (in.nextLine() ) {
                    case "1": {
                        try {
                            Wine wine = new Wine();
                            BigInteger id = getConsoleId("Enter a wine id:", true);
                            wine.setId(id);
                            GetWineResponse response = wineClient.getWine(wine);

                            List<Wine> wines = response.getWine();
                            if (wines.isEmpty()) {
                                printRed("Wine is not found!");
                            } else {
                                printWine(wines.get(0));
                            }
                        } catch (SoapFaultClientException e){
                            printRed(e.getFaultStringOrReason());
                        }
                        break;
                    }
                    case "2": {
                        try {
                            Wine wine = getConsoleWine(false, true);
                            CreateWineResponse response = wineClient.createWine(wine);
                            printGreen("Wine's id: " + response.getId());
                        } catch (SoapFaultClientException e){
                            printRed(e.getFaultStringOrReason());
                        }
                        break;
                    }
                    case "3": {
                        try {
                            Wine wine = getConsoleWine(true, false);
                            Status status = wineClient.updateWine(wine).getStatus();

                            if (status == Status.SUCCESS) {
                                printGreen("Success");
                            } else {
                                printRed("Failed");
                            }
                        } catch (SoapFaultClientException e){
                            printRed(e.getFaultStringOrReason());
                        }
                        break;
                    }
                    case "4": {
                        try {
                            BigInteger id = getConsoleId("Enter a wine id:", true);
                            Status status = wineClient.deleteWine(id).getStatus();

                            if (status == Status.SUCCESS) {
                                printGreen("Success");
                            } else {
                                printRed("Failed");
                            }
                        } catch (SoapFaultClientException e){
                            printRed(e.getFaultStringOrReason());
                        }
                        break;
                    }
                    case "5": {
                        try {
                            Wine wine = getConsoleWine(true, false);
                            List<Wine> wines = wineClient.getWine(wine).getWine();

                            if (wines.isEmpty()) {
                                printRed("Wine is not found!");
                            } else {
                                for (Wine wine_i : wines) {
                                    printWine(wine_i);
                                    printRegular("");
                                }
                            }
                        } catch (SoapFaultClientException e){
                            printRed(e.getFaultStringOrReason());
                        }
                        break;
                    }
                    case "6": {
                        return;
                    }
                    default: {
                        printRed("Incorrect operation! Try again!");
                        break;
                    }
                }
            }
        };
    }

    private BigInteger getConsoleId(String msg, Boolean isRequired) {
        Scanner in = new Scanner(System.in);
        String line;

        while (true) {
            printRegular(msg);
            try {
                line = in.nextLine();

                if (isRequired || !line.isEmpty()) {
                    return BigInteger.valueOf(Integer.parseInt(line));
                } else {
                    return null;
                }
            } catch(Exception ignored) {
            }
        }
    }

    private String getConsoleName(Boolean isRequired) {
        Scanner in = new Scanner(System.in);
        String line;

        while (true) {
            printRegular("Enter a name:");
            line = in.nextLine();

            if (!line.isEmpty()) {
                return line;
            } else if (!isRequired) {
                return null;
            }
        }
    }

    private Color getConsoleColor(Boolean isRequired) {
        Scanner in = new Scanner(System.in);
        String line;

        while (true) {
            printRegular("Enter a color (ROSE, WHITE, RED):");
            line = in.nextLine().toUpperCase();

            if (line.equals("RED")) return Color.RED;
            if (line.equals("ROSE")) return Color.ROSE;
            if (line.equals("WHITE")) return Color.WHITE;
            if (!isRequired && line.isEmpty()) return null;
        }
    }

    private Sugar getConsoleSugar(Boolean isRequired) {
        Scanner in = new Scanner(System.in);
        String line;

        while (true) {
            printRegular("Enter a sugar (DRY, SEMI_DRY, SEMI_SWEET, SWEET):");
            line = in.nextLine().toUpperCase();

            if (line.equals("DRY")) return Sugar.DRY;
            if (line.equals("SWEET")) return Sugar.SWEET;
            if (line.equals("SEMI_DRY")) return Sugar.SEMI_DRY;
            if (line.equals("SEMI_SWEET")) return Sugar.SEMI_SWEET;
            if (!isRequired && line.isEmpty()) return null;
        }
    }

    private Float getConsoleRating(Boolean isRequired){
        Scanner in = new Scanner(System.in);
        String line;

        while (true) {
            printRegular("Enter a rating [0;5]:");
            try {
                line = in.nextLine();

                if (isRequired || !line.isEmpty()) {
                    Float rating = Float.parseFloat(line);
                    if (0 <= rating && rating <= 5)
                        return rating;
                } else {
                    return null;
                }
            } catch(Exception ignored) {
            }
        }
    }

    private Wine getConsoleWine(Boolean withId, Boolean isRequired) {
        Wine wine = new Wine();

        if (withId) {
            BigInteger id = getConsoleId("Enter an id:", isRequired);
            if (id != null)
                wine.setId(id);
        }
        String name = getConsoleName(isRequired);
        if (name != null)
            wine.setName(name);
        Sugar sugar = getConsoleSugar(isRequired);
        if (sugar != null)
            wine.setSugar(sugar);
        Color color = getConsoleColor(isRequired);
        if (color != null)
            wine.setColor(color);
        Float rating = getConsoleRating(isRequired);
        if (rating != null)
            wine.setRating(rating);

        return wine;
    }

    private void printWine(Wine wine) {
        printGreen("id:\t\t" + wine.getId());
        printGreen("name:\t" + wine.getName());
        printGreen("color:\t" + wine.getColor());
        printGreen("sugar:\t" + wine.getSugar());
        printGreen("rating:\t" + wine.getRating());
    }

    private void printRegular(String line) {
        System.out.println(ANSI_BLACK + line + ANSI_RESET);
    }

    private void printRed(String line) {
        System.out.println(ANSI_RED + line + ANSI_RESET);
    }

    private void printGreen(String line) {
        System.out.println(ANSI_GREEN + line + ANSI_RESET);
    }
}
