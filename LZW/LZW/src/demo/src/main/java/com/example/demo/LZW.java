package com.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.*;

// Lempel-Ziv(LZW) compression/uncompression algorithm


public class LZW extends Application  {

    private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("LZW Algorithm");
        Button btn = new Button();
        btn.setText("Start");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String compress1 = "BAABABBBAABBBBAA";
                String compress2 = "BAABABBBAABBBBAABBBBABABBABBBBBBABBABABABBBBBABABABBABABDBABBBABBAAAAABBBBABABABABABBBABAABBABABAABBBBAAAAABBBBBAAABBBAAB";
                String compress3 = "BAAABBBABABABBABBABABBBAAAAABBBBAAABBBABABBBABABBBAAABBABBBABABBBABABBAABBBAABABABBBABBABABBBABBBABBABABBBABBBABABABABBABBABABABABABABABBBBABABABABAABABBABBAAABBABBBABBBABABABBABABABBBBABABABBABABABABBBBABBABABABBBBBABBABBABABABBABABBABABBABABBBABABBABABABBABABABABBABABBABABABABBABABABABABABABBABBBBBABABABBAAABBABABABBBABBABABBBBAABABABABBBBABABBAAABBBBABBABBBBABBBABBABABABABBABABABABABBBABBABABBBBAABAAABBBABBBABBABABBBABBAABBABBABABAABBABAABBAABBBABBABBABABABABABABBABABBABABBBABBBBBABABABBBBAABBABABBBBABABABABABABBABABBBBBBBBAAAAAAABBBABBABBBAABBABAABABBBBBABBABBABABABBABABBABABBABABBBABABBABABABBABABABABBABABBABABABABBABABABABABABABBABBBBBABABABBAAABBABABABBBABBABABBBBAABABABABBBBABABBAAABBBBABBABBBBABBBABBABABABABBABABABABABBBABBABABBBBAABAAABBBABBBABBABABBBABBAABBABBABABAABBABAABBAABBBABBABBABABABABABABBABABBABABBBABBBBBABABABBBBAABBABABBBBABABABABABABBABABBBBBBBBAAAAAAABBBABBABBBAABBABAABABBBBBABBABBABABABBABABBABABBABABBBABABBABABABBABABABABBABABBABABABABBABABABABABABABBABBBBBABABABBAAABBABABABBBABBABABBBBAABABABABBBBABABBAAABBBBABBABBBBABBBABBABABABABBABABABABABBBABBABABBBBAABAAABBBABBBABBABABBBABBAABBABBABABAABBABAABBAABBBABBABBABABABABABABBABABBABABBBABBBBBABABABBBBAABBABABBBBABABABABABABBABABBBBBBBBAAAAAAABBBABBABBBAABBABAAABAAABBBABBBABBABABBBABBAABBABBABABAABBABAABBAABBBABBABBABABABABABABBABABBABABBBABBBBBABABABBBBAABBABABBBBABABABABABABBABABBBBBBBBAAAAAAABBBABBABBBAABBABAABABBBBBABBABBABABABBABABBABABBABABBBABABBABABABAABAAABBBABBBABBABABBBABBAABBABBABABAABBABAABBAABBBABBABBABABABABABABBABABBABABBBABBBBBABABABBBBAABBABABBBBABABABABABABBABABBBBBBBBAAAAAAABBBABBABBBAABBABAABABBBBBABBABBABABABBABABBABABBABABBBABABBABABABABBBABBABABBBBAABAAABBBABBBABBABABBBABBAABBABBABABAABBABAABBAABBBABBAB";
                Performtest(compress1);
                Performtest(compress2);
                Performtest(compress3);

            }
        });
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();

    }

    private static void Performtest(String compress)
    {
        System.out.println("\nText to compress: "+ compress);
        long startcompression = System.nanoTime();
        ArrayList<String> compressed = Compress(compress);
        long compresssiontime = System.nanoTime() - startcompression;
        System.out.println("Compression time: "+ compresssiontime);
        System.out.println("Compressed text: " + compressed);
        System.out.println("Compressed array length: " + compressed.size());

        long startuncompression = System.nanoTime();
        StringBuffer uncompressed = Uncompress(compressed);
        long uncompresssiontime = System.nanoTime() - startuncompression;
        System.out.println("Uncompressed text: " + uncompressed);
        System.out.println("Uncompression time: "+ uncompresssiontime);

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory in bytes: " + memory);
        System.out.println("Used memory in megabytes: "
                + bytesToMegabytes(memory));
    }

    private static ArrayList<String> Compress(String uncompressed) {
        Hashtable<String, Integer> dictionary = new Hashtable<>();
        int index = 1;
        String storeKey = "";
        String character = "";
        ArrayList<String> result = new ArrayList<>();
        for (char add : uncompressed.toCharArray()) {
            String characterplussone = character + add;
            if (dictionary.containsKey(characterplussone)){
                character = characterplussone;
                storeKey = characterplussone;
            }
            else {
                if(dictionary.containsKey(storeKey))
                    result.add("" + dictionary.get(storeKey) + "" + add);
                else
                    result.add("" + add);
                dictionary.put(characterplussone, index);
                characterplussone = "";
                character= "";
                index++;
            }
        }
        if (character != ""){
            result.add("" + dictionary.get(character));}
        //System.out.println(Arrays.asList(dictionary));
        return result;
    }

    private static StringBuffer Uncompress(List<String> compressed) {
        HashMap<Integer, String> dictionary = new HashMap<>();
        int index = 1;
        String store = "";
        StringBuffer result =new StringBuffer();

        for (String s : compressed) {
            String[] part = s.split("[^A-Z0-9]+|(?<=[A-Z])(?=[0-9])|(?<=[0-9])(?=[A-Z])");
            if(isInteger(part[0])) {
                int check = Integer.parseInt(part[0]);
                if (dictionary.containsKey(check)) {
                    try {
                        store = dictionary.get(check) + part[1];
                    } catch (Exception e) {
                        store = dictionary.get(check);
                    }
                }
                }
            else
                store = store + s;
            result.append(store);
            dictionary.put(index, store);
            index++;
            store = "";
        }
        //System.out.println(Arrays.asList(dictionary));
        return result;
    }
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }
}
