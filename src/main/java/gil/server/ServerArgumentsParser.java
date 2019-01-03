package gil.server;

public class ServerArgumentsParser {
    String PORT_FLAG = "port";

    public Boolean portIsValid(String port) {
        try {
            int portNumber = Integer.parseInt(port);
            return (portNumber >= 1025 && portNumber <= 48999 ) ? true : false;
        } catch (NumberFormatException e) {
          return false;
        }
    }

    public Boolean hasPortFlag(String[] args) {
        for (String arg : args) {
            if (arg.equals(PORT_FLAG)) return true;
        }
        return false;
    }

    public int getPortFlagIndexNumber(String[] args) throws ServerMissingPortFlagException {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(PORT_FLAG)) return i;
        }
        throw new ServerMissingPortFlagException();
    }

    public int getPortFlagValue(String[] args) throws ServerMissingPortFlagException {
            int portFlagIndex = getPortFlagIndexNumber(args);
            int portFlagValueIndex = portFlagIndex + 1;

            if (args.length <= portFlagValueIndex || !(portIsValid(args[portFlagValueIndex]))) {
                throw new ServerMissingPortFlagException();
            } else {
                return Integer.parseInt(args[portFlagValueIndex]);
            }
    }
 }
