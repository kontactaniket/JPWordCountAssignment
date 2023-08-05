package com.aniket.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class WordCountAssignmentTest {
    private static ByteArrayOutputStream outputStreamCaptor;
    private static String fileName1
            = "D:\\Aniket\\Projects\\JPWordCountAssignment\\src\\test\\resources\\gpl-3.0.txt";
    private static String fileName2
            = "D:\\Aniket\\Projects\\JPWordCountAssignment\\src\\test\\resources\\gpl-3.0.1.txt";

    @BeforeEach
    void setup(){
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void _test_mainWordCountAssignment_emptyArgs() {
        WordCountAssignment.main(new String[]{});
        assertEquals("No filename sent!!!", outputStreamCaptor.toString());
    }

    @Test
    void _test_mainWordCountAssignment_invalidFilePath() {
        WordCountAssignment.main(new String[]{fileName2+1});
        assertEquals("Invalid path received!!!", outputStreamCaptor.toString());
    }

    @Test
    void _test_mainWordCountAssignment_validFilePath1() {
        WordCountAssignment.main(new String[]{fileName2});
        String[] tuples = new String[]{"\"allowed\":1",
                "\"29\":1", "\"but\":1", "\"2007\":2", "\"license\":2"};
        Arrays.asList(tuples).stream().forEach(
                tuple->assertTrue(
                        outputStreamCaptor.toString().contains(tuple)));
    }

    @Test
    void _test_mainWordCountAssignment_validFilePath2() {
        WordCountAssignment.main(new String[]{fileName2});
        String[] tuples = new String[]{"<",">","\":\"","(",")","\",\""};
        Arrays.asList(tuples).stream().forEach(
                tuple->assertFalse(
                        outputStreamCaptor.toString().contains(tuple)));
    }

    @Test
    void _test_countWord() throws IOException {
        List<String> fileLines = Files.readAllLines(Path.of(fileName1));
        Map<String, Integer> wordMap = WordCountAssignment.countWord(fileLines);
        assertEquals(wordMap.size(), 1033);
        assertEquals(wordMap.get("all"), 21);
        assertEquals(wordMap.get("code"), 34);
        assertEquals(wordMap.get("non-exercise"), 1);
        assertEquals(wordMap.get("12"), 1);
    }
}