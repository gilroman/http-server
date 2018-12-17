package gil.server;

public class ArgumentsParser {
    String portFlag = "port";

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
            if (arg.equals(portFlag)) return true;
        }
        return false;
    }

    public int getPortFlagIndexNumber(String[] args) throws MissingPortFlagException {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(portFlag)) return i;
        }
        throw new MissingPortFlagException();
    }

    public int getPortFlagValue(String[] args) throws MissingPortFlagException {
            int portFlagIndex = getPortFlagIndexNumber(args);
            int portFlagValueIndex = portFlagIndex + 1;

            if (args.length <= portFlagValueIndex || !(portIsValid(args[portFlagValueIndex]))) {
                throw new MissingPortFlagException();
            } else {
                return Integer.parseInt(args[portFlagValueIndex]);
            }
    }
 }
