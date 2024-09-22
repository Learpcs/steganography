package org.example.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class LanguageConverter {
    private final Map<Character, Character> rus_to_eng = new HashMap<>();
    private final Map<Character, Character> eng_to_rus = new HashMap<>();

    public LanguageConverter() {
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

        for (Map.Entry<Character, Character> p : rus_to_eng.entrySet())
            eng_to_rus.put(p.getValue(), p.getKey());
    }

    public boolean is_russian(char ch) {
        return rus_to_eng.containsKey(ch);
    }

    public boolean is_english(char ch) {
        return eng_to_rus.containsKey(ch);
    }

    public char rus_to_eng(char ch) {
        return Optional.ofNullable(rus_to_eng.get(ch)).orElseThrow(NoSuchElementException::new);
    }

    public char eng_to_rus(char ch) {
        return Optional.ofNullable(eng_to_rus.get(ch)).orElseThrow(NoSuchElementException::new);
    }

    boolean is_swappable(char ch) {
        return is_russian(ch) || is_english(ch);
    }
}
