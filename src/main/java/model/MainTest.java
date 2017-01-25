package model;


import java.io.IOException;

import static model.Graph.formTheData;

public class MainTest {

    public static void main(String[] args) throws IOException {
        System.out.println(formTheData("{\"locations\":{\"Miskolc, Magyarország, HU\":3," +
                "\"Krakow, Poland, PO\":6,\"Budapest, Magyarország, HU\":1," +
                "\"PolishCity, Poland, PO\":6,\"Bugyi, Magyarország, HU\":12" +
                "},\"webshop\":\"yourWebshop\"}"));
    }
}
