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

	public static void main(String[] args) {
		SpringApplication.run(ConsumingWebServiceApplication.class, args);
	}

    @Bean
    CommandLineRunner lookup(WineClient wineClient) {
        return args -> {
            Scanner in = new Scanner(System.in);

            while (true) {
                System.out.println("Enter an operation number:\n\t1.Get wine\n\t2.Create wine\n\t3.Update wine\n\t4.Delete wine\n\t5.Find wines\n\t6.Exit");
                switch (in.nextLine()) {
                    case "1": {
                        Wine wine = new Wine();
                        BigInteger id = BigInteger.valueOf(getConsoleInt("Enter a wine id:"));
                        wine.setId(id);
                        GetWineResponse response = wineClient.getWine(wine);

                        List<Wine> wines = response.getWine();
                        if (wines.isEmpty()) {
                            System.out.println("Wine is not found!");
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
                        System.out.println("Incorrect operation! Try again!");
                        break;
                }
            }
        };
    }

    private int getConsoleInt(String msg) {
        Scanner in = new Scanner(System.in);
	    String line;

	    while (true) {
	        System.out.println(msg);
	        try {
                line = in.nextLine();
                return Integer.parseInt(line);
            } catch(Exception e) {

            }
        }
    }

    private void printWine(Wine wine) {
        System.out.println("\tid:\t\t" + wine.getId());
        System.out.println("\tname:\t" + wine.getName());
        System.out.println("\tcolor:\t" + wine.getColor());
        System.out.println("\tsugar:\t" + wine.getSugar());
        System.out.println("\trating:\t" + wine.getRating());
    }
}
