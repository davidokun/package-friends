package com.mobiquityinc;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Packer;


public class App 
{
    public static void main( String[] args ) throws APIException {

        System.out.println(Packer.pack("C:\\temp\\packages.txt"));
    }
}
