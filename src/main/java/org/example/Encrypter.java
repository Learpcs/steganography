package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.example.utils.LanguageConverter;

import javax.security.auth.callback.LanguageCallback;
import java.util.Map;
import java.util.BitSet;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;



class Encrypter {

    @Parameter(names = {"--input", "-i"}, description = "Input file path", required = true)
    String input;

    @Parameter(names = {"--container", "-c"}, description = "Container type", required = false)
    String container;

    @Parameter(names = {"--output", "-o"}, description = "Output file path", required = true)
    String output;

    @Parameter(names = {"--help", "-h"}, help = true)
    private boolean help;

    public static void main(String[] argv) {
        Encrypter main = new Encrypter();
        JCommander commander = JCommander.newBuilder()
                .addObject(main)
                .programName("Encrypter")
                .build();

        try {
            commander.parse(argv);
            if (main.help) {
                commander.usage();
                return;
            }
            main.encrypt();
        } catch (ParameterException | IOException e) {
            System.err.println(e.getMessage());
            commander.usage();
        }
    }

    public void encrypt() throws IOException {
        LanguageConverter languageConverter = new LanguageConverter();

        BitSet st = new BitSet();

        StringBuilder input_text = new StringBuilder(new String(Files.readAllBytes(Paths.get(input)), StandardCharsets.UTF_8));
        StringBuilder container_text = new StringBuilder(new String(Files.readAllBytes(Paths.get(container)), StandardCharsets.UTF_8));

        for (int i = 0; i < input_text.length(); ++i) {
            if (languageConverter.is_english(input_text.charAt(i))) {
                input_text.setCharAt(i, languageConverter.eng_to_rus(input_text.charAt(i)));
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
            if (languageConverter.is_russian(ch) && st.get(now)) {
                container_text.setCharAt(i, languageConverter.rus_to_eng(ch));
            }
            else if(languageConverter.is_english(ch) && !st.get(now)) {
                container_text.setCharAt(i, languageConverter.eng_to_rus(ch));
            }
            if (languageConverter.is_english(ch) || languageConverter.is_russian(ch)) {
                ++now;
            }
        }
        if (now < pos) {
            throw new RuntimeException("Container is small");
        }
        Files.write( Paths.get(output), container_text.toString().getBytes());
    }
}
