package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Element;
import com.mobiquityinc.util.PackerHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Packer {

    public static final String PACKAGE_DIVISOR = ":";

    public static String pack(String fileName) throws APIException {

        if ("".equals(fileName)) {
            throw new APIException("File name can't be empty");
        }

        StringBuilder result = new StringBuilder();
        PackerHelper packerHelper = new PackerHelper();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(lineInput -> {

                String[] lineData = lineInput.split(PACKAGE_DIVISOR);

                String[] lineValue = lineData[1].replaceFirst(" ", "").split(" ");

                Integer packageLimit = Integer.valueOf(lineData[0].trim());


                List<Element> elements = new ArrayList<>();

                for (String line : lineValue) {

                    packerHelper.convertLineToElement(elements, line);
                }

                Collections.sort(elements);

                packerHelper.checkWeightForEqualCost(elements, packageLimit);

                packerHelper.packageItems(result, packageLimit, elements);
            });

        } catch (Exception e) {
            throw new APIException("Error reading file", e);
        }

        return result.toString();
    }

}
