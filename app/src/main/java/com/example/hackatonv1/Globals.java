package com.example.hackatonv1;

public class Globals {
    private static String name = new String();
    private static String Id = new String();
    // Getters setters

    public static String getName(){
        return name;
    }

    public static void setName(String instance){
        Globals.name = instance;
    }

    private Globals(){

    }

    private String pictureId;

    public static String getId(){
        return Id;
    }
    public static void setId(String Id){ Id=Id;
    }
}
