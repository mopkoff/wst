package com.itmo.wst;

import com.itmo.wst.wsdl.GetWineResponse;
import com.itmo.wst.wsdl.Wine;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ws.soap.client.SoapFaultClientException;

import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class WineWebServiceJUddiInteractionApplication {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\033[1;30m";
	private static final String ANSI_RED = "\033[1;31m";
	private static final String ANSI_GREEN = "\033[1;32m";

	public static void main(String[] args) {
		SpringApplication.run(WineWebServiceJUddiInteractionApplication.class, args);
	}

	@Bean
	CommandLineRunner lookup(JUDDIClient juddiClient) {
		return args -> {
			Scanner in = new Scanner(System.in);

			while (true) {
				printRegular("Enter an operation number:\n\t1.Register service\n\t2.Get wine with id 'x'\n\t3.Exit");
				switch (in.nextLine() ) {
					case "1":
						registerService(juddiClient);
						break;
					case "2":
						getWine(juddiClient.getWineClient());
						break;
					case "3":
						return;
					default: {
						printRed("Incorrect operation! Try again!");
						break;
					}
				}
			}
		};
	}

	private void registerService(JUDDIClient juddiClient) {
		juddiClient.registerService();
	}

	private void getWine(WineClient wineClient) {
		try {
			Wine wine = new Wine();
			BigInteger id = getConsoleId();
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
	}

	private BigInteger getConsoleId() {
		Scanner in = new Scanner(System.in);
		String line;

		while (true) {
			printRegular("Enter a wine id:");
			try {
				line = in.nextLine();

				if (!line.isEmpty()) {
					return BigInteger.valueOf(Integer.parseInt(line));
				}
			} catch(Exception ignored) {
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
