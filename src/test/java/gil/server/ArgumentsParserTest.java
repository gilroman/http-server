package gil.server;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArgumentsParserTest {
    ArgumentsParser argumentsParser = new ArgumentsParser();

    @Test
    public void testPortIsValid() {
        assertFalse(argumentsParser.portIsValid(""));
        assertFalse(argumentsParser.portIsValid("a"));
        assertFalse(argumentsParser.portIsValid("1024"));
        assertFalse(argumentsParser.portIsValid("49000"));
        assertTrue(argumentsParser.portIsValid("1025"));
        assertTrue(argumentsParser.portIsValid("48999"));
    }

    @Test
    public void testHasPortFlag() {
        String[] hasPortFlag = {"a", "0", "port", "4040"};
        String[] missingPortFlag = {"a", "0", "4040"};
        assertTrue(argumentsParser.hasPortFlag(hasPortFlag));
        assertFalse(argumentsParser.hasPortFlag(missingPortFlag));
    }

    @Test
    public void testGetPFlagIndexNumber() throws Exception {
        String[] hasPortFlag = {"a", "0", "port", "4040"};
        assertEquals(2, argumentsParser.getPortFlagIndexNumber(hasPortFlag));
    }

    @Test
    public void testGetPortFlagValue() throws Exception {
        String[] hasPortFlagValue = {"a", "0", "port", "4040"};
        String[] hasPortFlagValueOnly = {"port", "4040"};
        assertEquals(4040, argumentsParser.getPortFlagValue(hasPortFlagValue));
        assertEquals(4040, argumentsParser.getPortFlagValue(hasPortFlagValueOnly));
    }

    @Test(expected = Exception.class)
    public void shouldThrowAnExceptionWhenThereIsNoPortValue() throws Exception {
        String[] missingPortFlagValue = {"port"};
        argumentsParser.getPortFlagValue(missingPortFlagValue);
    }

    @Test(expected = Exception.class)
    public void shouldThrowAnExceptionWhenThereIsNoPortFlag() throws Exception {
        String[] missingPortFlag = {"a", "0", "4040"};
        argumentsParser.getPortFlagValue(missingPortFlag);
    }
}
