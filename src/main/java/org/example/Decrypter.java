package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.JCommander;
import java.util.Map;
import java.util.BitSet;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;



class Decrypter {

    @Parameter(names={"--input", "-i"})
    String input;

    @Parameter(names={"--output", "-o"})
    String output;

    public static void main(String[] argv) throws IOException {
        Decrypter main = new Decrypter();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(argv);
        main.decrypt();
    }

    public void decrypt() throws IOException {
        Map<Character, Character> rus_to_eng = new HashMap<>();
        Map<Character, Character> eng_to_rus = new HashMap<>();

        rus_to_eng.put('а', 'a');
        rus_to_eng.put('е', 'e');
        rus_to_eng.put('Е', 'E');
        rus_to_eng.put('Т', 'T');
        rus_to_eng.put('у', 'y');
        rus_to_eng.put('О', 'O');
        rus_to_eng.put('о', 'o');
        rus_to_eng.put('р', 'p');
        rus_to_eng.put('Р', 'P');
        rus_to_eng.put('А', 'A');
        rus_to_eng.put('Н', 'H');
        rus_to_eng.put('К', 'K');
        rus_to_eng.put('х', 'x');
        rus_to_eng.put('Х', 'X');
        rus_to_eng.put('с', 'c');
        rus_to_eng.put('С', 'C');
        rus_to_eng.put('В', 'B');
        rus_to_eng.put('М', 'M');

        assert(Character.isLowerCase('а'));
        assert(Character.isUpperCase('А'));

        for (Map.Entry<Character, Character> p : rus_to_eng.entrySet()) {
            assert('а' <= p.getKey() && p.getKey() <= 'я' || 'А' <= p.getKey() && p.getKey() <= 'Я');
            assert('a' <= p.getValue() && p.getValue() <= 'z' || 'A' <= p.getValue() && p.getValue() <= 'Z');
            assert(Character.isLowerCase(p.getKey()) == Character.isLowerCase(p.getValue()));
            eng_to_rus.put(p.getValue(), p.getKey());
        }

        BitSet st = new BitSet();

        StringBuilder input_text = new StringBuilder(new String(Files.readAllBytes(Paths.get(input)), StandardCharsets.UTF_8));

        int pos = 0;
        for (char ch : input_text.toString().toCharArray()) {
            if (!eng_to_rus.containsKey(ch) && !rus_to_eng.containsKey(ch)) continue;
            st.set(pos++, eng_to_rus.containsKey(ch));
        }


        pos = pos / 16 * 16;

        StringBuilder output_text = new StringBuilder();

        for (int i = 0; i < pos / 16; ++i) {
            char ch = 0;
            for (int j = 0; j < 16; ++j) {
                if (st.get(i * 16 + j)) {
                    ch |= (char) (1 << j);
                }
            }
            output_text.append(ch);
        }

        while (output_text.charAt(output_text.length() - 1) == (char)0) {
            output_text.setLength(output_text.length() - 1);
        }

        System.out.println((int)output_text.charAt(output_text.length() - 1));

        Files.write( Paths.get(output), output_text.toString().getBytes());
    }
}
