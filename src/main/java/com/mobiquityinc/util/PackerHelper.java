package com.mobiquityinc.util;

import com.mobiquityinc.model.Element;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class with specific logic to pack items
 */
public class PackerHelper {

    public void packageItems(StringBuilder result, Integer packageLimit, List<Element> elements) {
        StringBuilder temp = new StringBuilder();

        Double currentWeight = 0.0;

        for (Element element : elements) {

            Double weightAvailable = packageLimit - currentWeight;

            if (currentWeight <= packageLimit
                    && element.getWeight() < weightAvailable) {
                if (!"".equals(temp.toString())) {
                    temp.append(",");
                }
                temp.append(element.getId());
                currentWeight += element.getWeight();
            }
        }

        if ("".equals(temp.toString())) {
            temp.append("-");
        }

        result.append(temp).append("\n");
    }

    public void convertLineToElement(final List<Element> elements, final String line) {

        String[] ele = line.trim().replaceAll("([()])", "").split(",");

        int id = Integer.valueOf(ele[0]);
        double weight = Double.valueOf(ele[1]);
        int cost = Integer.valueOf(ele[2].replace("€", ""));

        elements.add(new Element(id, weight, cost));
    }

    public void checkWeightForEqualCost(List<Element> elemets, double packageLimit) {

        List<Element> collect = new ArrayList<>();

        elemets.stream()
                .collect(Collectors.groupingBy(Element::getCost))
                .forEach((k,v) -> {
                    if (v.size() > 1) {
                        v.stream()
                                .filter(e -> e.getCost().equals(k))
                                .forEach(collect::add);
                    }
                });

        double sum = collect.stream().mapToDouble(Element::getWeight).sum();

        if (sum > packageLimit) {
            collect.stream()
                    .min(Comparator.comparing(Element::getWeight))
                    .ifPresent(e -> collect.remove(collect.indexOf(e)));

            collect.forEach(e -> elemets.remove(elemets.indexOf(e)));
        }
    }
}
