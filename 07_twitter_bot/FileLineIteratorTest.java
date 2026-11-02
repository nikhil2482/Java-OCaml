package org.cis1200;

import org.junit.jupiter.api.Test;
import java.io.StringReader;
import java.io.BufferedReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for FileLineIterator */
public class FileLineIteratorTest {

    /*
     * Here's a test to help you out, but you still need to write your own.
     */

    @Test
    public void testHasNextAndNext() {

        // Note we don't need to create a new file here in order to test out our
        // FileLineIterator if we do not want to. We can just create a
        // StringReader to make testing easy!
        String words = "0, The end should come here.\n"
                + "1, This comes from data with no duplicate words!";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        FileLineIterator li = new FileLineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("0, The end should come here.", li.next());
        assertTrue(li.hasNext());
        assertEquals("1, This comes from data with no duplicate words!", li.next());
        assertFalse(li.hasNext());
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */

    @Test
    public void testEmptyFile() {
        String words = ""; // Empty file
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        FileLineIterator li = new FileLineIterator(br);
        assertFalse(li.hasNext());
        assertThrows(NoSuchElementException.class, li::next);
    }



    @Test
    public void testSingleLineFile() {
        String words = "1, This is the only line."; // File with only one line
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        FileLineIterator li = new FileLineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("1, This is the only line.", li.next());
        assertFalse(li.hasNext());
    }

    @Test
    public void testFileWithBlankLines() {
        String words = "1, This is the first line.\n\n2, This is a blank line above me.\n\n\n3, " +
                "This is the last line."; // File with blank lines
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        FileLineIterator li = new FileLineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("1, This is the first line.", li.next());
        assertTrue(li.hasNext());
        assertEquals("2, This is a blank line above me.", li.next());
        assertTrue(li.hasNext());
        assertEquals("3, This is the last line.", li.next());
        assertFalse(li.hasNext());
    }

    @Test
    public void testEmptyFileNew() {
        String words = ""; // empty file
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        FileLineIterator li = new FileLineIterator(br);
        assertFalse(li.hasNext());
        assertThrows(NoSuchElementException.class, li::next);
    }

    @Test
    public void testMultipleLines() {
        String words = "1, Line 1\n"
                + "2, Line 2\n"
                + "3, Line 3";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        FileLineIterator li = new FileLineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("1, Line 1", li.next());
        assertTrue(li.hasNext());
        assertEquals("2, Line 2", li.next());
        assertTrue(li.hasNext());
        assertEquals("3, Line 3", li.next());
        assertFalse(li.hasNext());
    }

    @Test
    public void testSpecialCharacters() {
        String words = "1, Line with special characters: !@#$%^&*()\n"
                + "2, Another line with special characters: <>,.?/{}[]|\\";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        FileLineIterator li = new FileLineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("1, Line with special characters: !@#$%^&*()", li.next());
        assertTrue(li.hasNext());
        assertEquals("2, Another line with special characters: <>,.?/{}[]|\\", li.next());
        assertFalse(li.hasNext());
    }


    @Test
    public void testFileToReaderNullFile() {
        assertThrows(IllegalArgumentException.class, () -> FileLineIterator.fileToReader(null));
    }
}
