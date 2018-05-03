package com.mobiquityinc.packer;

import com.mobiquityinc.model.Element;
import com.mobiquityinc.model.Package;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Packer {

    public static String pack(String fileName){


        // EXTRACS DATA FROM FILE

        Map<Package, List<Element>> data = new LinkedHashMap<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(line -> {
                String[] lineData = line.split(":");
                Integer packageLimit = Integer.valueOf(lineData[0].trim());

                String[] lineValue = lineData[1].replaceFirst(" ", "").split(" ");

                List<Element> elemets = new ArrayList<>();

                for (String s : lineValue) {

                    String[] ele = s.trim().replaceAll("([()])", "").split(",");

                    elemets.add(new Element(Integer.valueOf(ele[0]), Double.valueOf(ele[1]), Integer.valueOf(ele[2].replace("€", ""))));
                    Collections.sort(elemets);
                }

                data.put(new Package(line.length() + packageLimit , packageLimit) , elemets);
            });

            data.forEach((k,v) -> {
                System.out.println(k + "=" + v);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        // PACKAGING ALGORITHM
        StringBuilder result = new StringBuilder();


        System.out.println("===============================================");
        System.out.println("=====  RESULT  ====");

        for (Map.Entry<Package, List<Element>> element: data.entrySet()) {

            StringBuilder temp = new StringBuilder();
            Integer packageLimit = element.getKey().getWeighLimit();

            Double currentWeiht = 0.0;

            for (Element e : element.getValue()) {

                Double weightAvailable = packageLimit - currentWeiht;

                if (currentWeiht <= packageLimit
                        && e.getWeight() < weightAvailable) {

                    temp.append(e.getId()).append(",");
                    currentWeiht += e.getWeight();
                }
            }

            if ("".equals(temp.toString())) {
                temp.append("-");
            }

            result.append(temp).append("\n");

        }

        return result.toString();

//        4
//        -
//        2,7
//        8,9
    }

}
