package com.mobiquityinc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Element implements Comparable<Element>{

    private Integer id;
    private Double weight;
    private Integer cost;

    @Override
    public int compareTo(Element o) {
        return o.getCost().compareTo(this.cost);
    }
}
