package org.example.utils;

import org.example.utils.LanguageConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class LanguageConverterTest {

    private LanguageConverter languageConverter;

    @BeforeEach
    public void setUp() {
        languageConverter = new LanguageConverter();
    }

    @Test
    public void testIsRussian() {
        assertTrue(languageConverter.is_russian('а'));
        assertFalse(languageConverter.is_russian('a'));
    }

    @Test
    public void testIsEnglish() {
        assertTrue(languageConverter.is_english('a'));
        assertFalse(languageConverter.is_english('а'));
    }

    @Test
    public void testRusToEng() {
        assertEquals('a', languageConverter.rus_to_eng('а'));
        assertEquals('e', languageConverter.rus_to_eng('е'));
        assertEquals('E', languageConverter.rus_to_eng('Е'));
        assertEquals('T', languageConverter.rus_to_eng('Т'));
        assertEquals('y', languageConverter.rus_to_eng('у'));
        assertEquals('O', languageConverter.rus_to_eng('О'));
        assertEquals('o', languageConverter.rus_to_eng('о'));
        assertEquals('p', languageConverter.rus_to_eng('р'));
        assertEquals('P', languageConverter.rus_to_eng('Р'));
        assertEquals('A', languageConverter.rus_to_eng('А'));
        assertEquals('H', languageConverter.rus_to_eng('Н'));
        assertEquals('K', languageConverter.rus_to_eng('К'));
        assertEquals('x', languageConverter.rus_to_eng('х'));
        assertEquals('X', languageConverter.rus_to_eng('Х'));
        assertEquals('c', languageConverter.rus_to_eng('с'));
        assertEquals('C', languageConverter.rus_to_eng('С'));
        assertEquals('B', languageConverter.rus_to_eng('В'));
        assertEquals('M', languageConverter.rus_to_eng('М'));
    }

    @Test
    public void testRusToEngException() {
        assertThrows(NoSuchElementException.class, () -> languageConverter.rus_to_eng('z'));
    }

    @Test
    public void testEngToRus() {
        assertEquals('а', languageConverter.eng_to_rus('a'));
        assertEquals('е', languageConverter.eng_to_rus('e'));
        assertEquals('Е', languageConverter.eng_to_rus('E'));
        assertEquals('Т', languageConverter.eng_to_rus('T'));
        assertEquals('у', languageConverter.eng_to_rus('y'));
        assertEquals('О', languageConverter.eng_to_rus('O'));
        assertEquals('о', languageConverter.eng_to_rus('o'));
        assertEquals('р', languageConverter.eng_to_rus('p'));
        assertEquals('Р', languageConverter.eng_to_rus('P'));
        assertEquals('А', languageConverter.eng_to_rus('A'));
        assertEquals('Н', languageConverter.eng_to_rus('H'));
        assertEquals('К', languageConverter.eng_to_rus('K'));
        assertEquals('х', languageConverter.eng_to_rus('x'));
        assertEquals('Х', languageConverter.eng_to_rus('X'));
        assertEquals('с', languageConverter.eng_to_rus('c'));
        assertEquals('С', languageConverter.eng_to_rus('C'));
        assertEquals('В', languageConverter.eng_to_rus('B'));
        assertEquals('М', languageConverter.eng_to_rus('M'));
    }

    @Test
    public void testEngToRusException() {
        assertThrows(NoSuchElementException.class, () -> languageConverter.eng_to_rus('z'));
    }

    @Test
    public void testIsSwappable() {
        assertTrue(languageConverter.is_swappable('а'));
        assertTrue(languageConverter.is_swappable('a'));
        assertFalse(languageConverter.is_swappable('z'));
    }
}