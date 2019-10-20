package edu.kit.informatik;

/**
 * This class contains all constants.
 * 
 * @author 
 * @version 1.0
 */
public class Constant {
    
    // commands for user interaction
    
    /**
     * The addAssembly command, which adds a new assembly to the system.
     */
    public static final String ADD_ASSEMBLY = "addAssembly";
    
    /**
     * The removeAssembly command, which removes an assembly from the system.
     */
    public static final String REMOVE_ASSEMBLY = "removeAssembly";
    
    /**
     * The printAssembly command, which returns the bill of material of an assembly.
     */
    public static final String PRINT_ASSEMBLY = "printAssembly";
    
    /**
     * The getAssembly command, which returns the assemblies from the quantity bill.
     */
    public static final String GET_ASSEMBLIES = "getAssemblies";
    
    /**
     * The getComponents command, which returns the components from the quantity bill.
     */
    public static final String GET_COMPONENTS = "getComponents";
    
    /**
     * The addPart command, which adds a part with a specific amount to an assembly.
     */
    public static final String ADD_PART = "addPart";
    
    /**
     * The removePart command, which removes a part with a specific amount from an assembly.
     */
    public static final String REMOVE_PART = "removePart";
    
    /**
     * The quit command, to end the program.
     */
    public static final String QUIT = "quit";
    
    /**
     * Used if a command without return value is executed properly.
     */
    public static final String OK = "OK";
    
    
    // Error messages
    
    /**
     * Error if the input command doesn't exist.
     */
    public static final String ERR_NO_COMMAND = "No valid command!";
    
    /**
     * Error message if a parameter input doesn't fit the regex-pattern.
     */
    public static final String ERR_INPUT = "incorrect input format. Predicted format: ";
    
    /**
     * Error if the predicted format of the addAssembly command doesn't fit.
     */
    public static final String ERR_ADD_ASM = " <nameAssembly>=<amount1>:<name1>;...;<amountn>:<namen>";
    
    /**
     * Error if the predicted format of the removeAssembly command doesn't fit.
     */
    public static final String ERR_REM_ASM = " <nameAssembly>";
    
    /**
     * Error if the predicted format of the printAssembly command doesn't fit.
     */
    public static final String ERR_PRNT_ASM = " <nameAssembly>";
    
    /**
     * Error if the predicted format of the getAssemblies command doesn't fit.
     */
    public static final String ERR_GET_ASMS = " <nameAssembly>";
    
    /**
     * Error if the predicted format of the getComponents command doesn't fit.
     */
    public static final String ERR_GET_COMPO = " <nameAssembly>";
    
    /**
     * Error if the predicted format of the addPart command doesn't fit.
     */
    public static final String ERR_ADD_PRT = " <nameAssembly>+<amount>:<name>";
    
    /**
     * Error if the predicted format of the removePart command doesn't fit.
     */
    public static final String ERR_REMOVE_PRT = " <nameAssembly>-<amount>:<name>";
    
    /**
     * Error if the command doesn't allow parameters.
     */
    public static final String ERR_NO_PARAM = " without parameters.";
    
    /**
     * Error if the input contains multiple blanks.
     */
    public static final String ERR_BLANK = "no multiple blanks allowed.";
    
    /**
     * Error if the input amount contains a leading zero.
     */
    public static final String ERR_LEADING_ZERO = "no leading zeros allowed";


    // Regex for validation of input format
    
    /**
     * Regex for the name of a assembly. It must contain at least one upper or lower case letter.
     */
    public static final String NAME_FORMAT = "[a-zA-Z]+";
    
    /**
     * Regex for the amount (digit) of a assembly.
     */
    public static final String AMOUNT_FORMAT = "\\d+";
    
    /**
     * Regex for the amount and name tuple.
     */
    public static final String AMOUNT_AND_NAME = AMOUNT_FORMAT + ":" + NAME_FORMAT;
    
    /**
     * Regex for the addAssembly command.
     */
    public static final String ADD_ASM_PATTERN = NAME_FORMAT + "=((" + AMOUNT_AND_NAME + ")|(" 
                                + AMOUNT_AND_NAME + "(;" + AMOUNT_AND_NAME + ")+))";
    
    /**
     * Regex for the addPart command.
     */
    public static final String ADD_PART_PATTERN = NAME_FORMAT + "\\+" + AMOUNT_AND_NAME;
    
    /**
     * Regex for the removeAssembly command.
     */
    public static final String REMOVE_PART_PATTERN = NAME_FORMAT + "-" + AMOUNT_AND_NAME;
}
