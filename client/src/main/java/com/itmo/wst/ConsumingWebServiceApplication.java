package com.itmo.wst;

import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.itmo.wst.wsdl.*;

@SpringBootApplication
public class ConsumingWebServiceApplication {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\033[1;30m";
    private static final String ANSI_RED = "\033[1;31m";
    private static final String ANSI_GREEN = "\033[1;32m";

    public static void main(String[] args) {
        SpringApplication.run(ConsumingWebServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(WineClient wineClient) {
        return args -> {
            Scanner in = new Scanner(System.in);

            while (true) {
                printRegular("Enter an operation number:\n\t1.Get wine\n\t2.Create wine\n\t3.Update wine\n\t4.Delete wine\n\t5.Find wines\n\t6.Exit");
                switch (in.nextLine()) {
                    case "1": {
                        Wine wine = new Wine();
                        BigInteger id = BigInteger.valueOf(getConsoleInt("Enter a wine id:"));
                        wine.setId(id);
                        GetWineResponse response = wineClient.getWine(wine);

                        List<Wine> wines = response.getWine();
                        if (wines.isEmpty()) {
                            printRed("Wine is not found!");
                        } else {
                            printWine(wines.get(0));
                        }
                        break;
                    }
                    case "2":
                        break;
                    case "3":
                        break;
                    case "4":
                        break;
                    case "5":
                        break;
                    case "6":
                        return;
                    default:
                        printRed("Incorrect operation! Try again!");
                        break;
                }
            }
        };
    }

    private int getConsoleInt(String msg) {
        Scanner in = new Scanner(System.in);
        String line;

        while (true) {
            printRegular(msg);
            try {
                line = in.nextLine();
                return Integer.parseInt(line);
            } catch(Exception e) {

            }
        }
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
