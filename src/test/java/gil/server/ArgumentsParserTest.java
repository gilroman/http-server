package gil.server;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class ArgumentsParserTest {
    ServerArgumentsParser serverArgumentsParser = new ServerArgumentsParser();

    @Test
    public void testPortIsValid() {
        assertFalse(serverArgumentsParser.portIsValid(""));
        assertFalse(serverArgumentsParser.portIsValid("a"));
        assertFalse(serverArgumentsParser.portIsValid("1024"));
        assertFalse(serverArgumentsParser.portIsValid("49000"));
        assertTrue(serverArgumentsParser.portIsValid("1025"));
        assertTrue(serverArgumentsParser.portIsValid("48999"));
    }

    @Test
    public void testHasPortFlag() {
        String[] hasPortFlag = {"a", "0", "port", "4040"};
        String[] missingPortFlag = {"a", "0", "4040"};
        assertTrue(serverArgumentsParser.hasPortFlag(hasPortFlag));
        assertFalse(serverArgumentsParser.hasPortFlag(missingPortFlag));
    }

    @Test
    public void testGetPFlagIndexNumber() throws ServerMissingPortFlagException {
        String[] hasPortFlag = {"a", "0", "port", "4040"};
        assertEquals(2, serverArgumentsParser.getPortFlagIndexNumber(hasPortFlag));
    }

    @Test
    public void testGetPortFlagValue() throws ServerMissingPortFlagException {
        String[] hasPortFlagValue = {"a", "0", "port", "4040"};
        String[] hasPortFlagValueOnly = {"port", "4040"};
        assertEquals(4040, serverArgumentsParser.getPortFlagValue(hasPortFlagValue));
        assertEquals(4040, serverArgumentsParser.getPortFlagValue(hasPortFlagValueOnly));
    }

    @Test(expected = ServerMissingPortFlagException.class)
    public void shouldThrowAnExceptionWhenThereIsNoPortValue() throws Exception {
        String[] missingPortFlagValue = {"port"};
        serverArgumentsParser.getPortFlagValue(missingPortFlagValue);
    }

    @Test(expected = ServerMissingPortFlagException.class)
    public void shouldThrowAnExceptionWhenThereIsNoPortFlag() throws Exception {
        String[] missingPortFlag = {"a", "0", "4040"};
        serverArgumentsParser.getPortFlagValue(missingPortFlag);
    }
}
