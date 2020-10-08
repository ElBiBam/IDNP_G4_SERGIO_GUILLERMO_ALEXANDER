package com.practica03.musicapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
    public static String readStringFromStream(InputStream wordListSteam) {
        BufferedReader r = new BufferedReader(new InputStreamReader(wordListSteam));
        StringBuilder total = new StringBuilder();
        try {
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return total.toString();
    }
}
