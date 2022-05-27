/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LZW;
import java.util.*;

import java.util.List;

// Lempel-Ziv(LZW) compression/uncompression algorithm

public class Main {

    public static void main(String[] args) {
        String compress1 ="BAABABBBAABBBBAA";
        String compress2 ="WEEWWWEWWWE";
        String compress3 ="AAABBBABABABBABBABABBBAAAAABBBBAAABBBABABBBABABBBAAABBABBBABABBBABABBAABBBAABABABBBABBABABBBABBBABBABABBBABBBABABABABBABBABABABABABABABBBBABABABABAABABBABBA";
        long speed1 = Performtest(compress1);
        long speed2 = Performtest(compress2);
        long speed3 = Performtest(compress3);
        System.out.println("\n" + speed1);
        System.out.println("" + speed2);
        System.out.println("" + speed3);

    }

    private static long Performtest(String compress)
    {
        long start = System.nanoTime();
        System.out.println("\nText to compress: "+ compress);
        ArrayList<String> compressed = Compress(compress);
        System.out.println("Compressed text: " + compressed);
        StringBuffer uncompressed = Uncompress(compressed);
        System.out.println("Uncompressed text: " + uncompressed);
        return System.nanoTime() - start;

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
                characterplussone= "";
                character= "";
                index++;
            }
        }
        if (character != ""){
            result.add("" + dictionary.get(character));}
        System.out.println(Arrays.asList(dictionary));
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
        System.out.println(Arrays.asList(dictionary));
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
