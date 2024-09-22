package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.JCommander;
import org.example.utils.LanguageConverter;

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
        LanguageConverter languageConverter = new LanguageConverter();

        BitSet st = new BitSet();

        StringBuilder input_text = new StringBuilder(new String(Files.readAllBytes(Paths.get(input)), StandardCharsets.UTF_8));

        int pos = 0;
        for (char ch : input_text.toString().toCharArray()) {
            if (!languageConverter.is_russian(ch) && !languageConverter.is_english(ch)) continue;
            st.set(pos++, languageConverter.is_english(ch));
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
