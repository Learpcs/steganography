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



class Encrypter {

    @Parameter(names={"--input", "-i"})
    String input;

    @Parameter(names={"--container", "-c"})
    String container;

    @Parameter(names={"--output", "-o"})
    String output;

    public static void main(String[] argv) throws IOException {
        Encrypter main = new Encrypter();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(argv);
        main.encrypt();
    }

    public void encrypt() throws IOException {
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
        StringBuilder container_text = new StringBuilder(new String(Files.readAllBytes(Paths.get(container)), StandardCharsets.UTF_8));

        for (int i = 0; i < input_text.length(); ++i) {
            if (eng_to_rus.containsKey(input_text.charAt(i))) {
                input_text.setCharAt(i, eng_to_rus.get(input_text.charAt(i)));
            }
        }

        int pos = 0;
        for (char ch : input_text.toString().toCharArray()) {
            for (int j = 0; j < 16; ++j) {
                st.set(pos++, ((ch >> j) & 1) == 1);
            }
        }

        int now = 0;

        for (int i = 0; i < container_text.length(); ++i) {
            char ch = container_text.charAt(i);
            if (rus_to_eng.containsKey(ch)) {
                if (st.get(now)) {
                    container_text.setCharAt(i, rus_to_eng.get(ch));
                }
                ++now;
            }
        }
        if (now < pos) {
            throw new RuntimeException("Container is small");
        }
        Files.write( Paths.get(output), container_text.toString().getBytes());
    }
}
