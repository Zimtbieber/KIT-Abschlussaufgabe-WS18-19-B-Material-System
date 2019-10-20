package edu.kit.informatik;

import java.util.Scanner;
import java.util.TreeMap;
import edu.kit.informatik.exception.SystemInputException;
import edu.kit.informatik.exception.UserInputException;

/**
 * This class handels and prepare the user input.
 * Also it contains the program loop.
 *
 * @author 
 * @version 1.0
 */
public class UserInterface {

    private boolean runtime = true;
    private String userInput;
    private String inputCommand;
    private Controller controller = new Controller();

    /**
     * Creates the game-loop.
     */
    public void start() {

        while (runtime) {

            userInput = new Scanner(System.in).nextLine();
            inputCommand = checkInputCommand(userInput);

            switch (inputCommand) {

                case Constant.ADD_ASSEMBLY: {
                    addAssembly();
                    break;
                }
                case Constant.REMOVE_ASSEMBLY: {
                    removeAssembly();
                    break;
                }
                case Constant.PRINT_ASSEMBLY: {
                    printAssembly();
                    break;
                }
                case Constant.GET_ASSEMBLIES: {
                    getAssemblies();
                    break;
                }
                case Constant.GET_COMPONENTS: {
                    getComponents();
                    break;
                }
                case Constant.ADD_PART: {
                    addPart();
                    break;
                }
                case Constant.REMOVE_PART: {
                    removePart();
                    break;
                }
                case Constant.QUIT: {
                    quit();
                    break;
                }
                default: {
                    if (runtime) {
                        System.out.println(Constant.ERR_NO_COMMAND);
                    }
                }
            }
        }
    }

    private void addAssembly() {
        try {
            String inputValue = checkValue(userInput, Constant.ADD_ASM_PATTERN, Constant.ERR_ADD_ASM);
            String[] asmAndPrt = inputValue.split("=");
            
            checkAmount(asmAndPrt[0], asmAndPrt[1]);
            
            String[] prtArray = asmAndPrt[1].split(":|;");
            
            TreeMap<String, Integer> prts = new TreeMap<>();
            String nameAsm = asmAndPrt[0];
            
            for (int i = 0; i + 1 < prtArray.length; i += 2) {  
                         
                int prtAmount = Integer.parseInt(prtArray[i]);
                String prtName = prtArray[i + 1];
                
                if (prts.containsKey(prtName)) {
                    System.out.println("the amount for " + prtName + " is already declearted!");
                    return;
                } else if (prtName.equals(nameAsm)) {
                    System.out.println("the specified BOM " + nameAsm + " would create a cycle in "
                                      + "the product structure: " + nameAsm + "-" + nameAsm + ".");
                    return;
                } else {
                    prts.put(prtName, prtAmount);
                }
            }
            
            controller.addAssembly(nameAsm, prts);   
            System.out.println(Constant.OK);
        } catch (UserInputException | SystemInputException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeAssembly() {
        try {
            String inputValue = checkValue(userInput, Constant.NAME_FORMAT, Constant.ERR_REM_ASM);
            controller.removeAssembly(inputValue);
            System.out.println(Constant.OK);
        } catch (UserInputException | SystemInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printAssembly() {
        try {
            String inputValue = checkValue(userInput, Constant.NAME_FORMAT, Constant.ERR_PRNT_ASM);
            System.out.println(controller.printAssembly(inputValue));
        } catch (UserInputException | SystemInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getAssemblies() {
        try {
            String inputValue = checkValue(userInput, Constant.NAME_FORMAT, Constant.ERR_GET_ASMS);
         
            System.out.println(controller.getAssemblies(inputValue));
        } catch (UserInputException | SystemInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getComponents() {
        try {
            String inputValue = checkValue(userInput, Constant.NAME_FORMAT, Constant.ERR_GET_COMPO);

            System.out.println(controller.getComponents(inputValue));
        } catch (UserInputException | SystemInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addPart() {
        try {
            String inputValue = checkValue(userInput, Constant.ADD_PART_PATTERN, Constant.ERR_ADD_PRT);
         
            String[] test = inputValue.split("\\+|:");
            String nameAsm = test[0];
            int amount = Integer.parseInt(test[1]);
            String namePrt = test[2];
            
            if (nameAsm.equals(namePrt)) {
                throw new SystemInputException("adding a part " + namePrt + " to the BOM " 
                                                + nameAsm + "isn't allowed.");
            }
            checkAmount(nameAsm, inputValue.split("\\+")[1]);
            
            controller.addPart(nameAsm, amount, namePrt);
            System.out.println(Constant.OK);
        } catch (UserInputException | SystemInputException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removePart() {
        try {
            String inputValue = checkValue(userInput, Constant.REMOVE_PART_PATTERN, Constant.ERR_REMOVE_PRT);
            String[] test = inputValue.split("-|:");
            String nameAsm = test[0];
            int amount = Integer.parseInt(test[1]);
            String namePrt = test[2];
            
            checkAmount(nameAsm, inputValue.split("-")[1]);
            
            controller.removePart(nameAsm, amount, namePrt);
            
            System.out.println(Constant.OK);
        } catch (UserInputException | SystemInputException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void quit() {
        try {
            withoutParam(Constant.QUIT.length());
            runtime = false;
        } catch (UserInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void withoutParam(final int lengthCommand) throws UserInputException {
        String[] inputValue = userInput.split("\\s");
        if (inputValue.length > 1 || lengthCommand < userInput.length()) {
            throw new UserInputException(Constant.ERR_INPUT + inputValue[0] + Constant.ERR_NO_PARAM);
        }
    }

    private String checkInputCommand(final String userInput) {
        if (userInput.equals(" ")) {
            return "error";
        } else {
            String[] command = userInput.split("\\s");
            return command[0];    
        }
    }

    private String checkValue(final String userInput, final String pattern,
                              final String errorMsg) throws UserInputException {
        // check for blank symbols
        int blankCount = 0;
        for (int i = 0; i < userInput.length(); i++) {
            if (userInput.charAt(i) == ' ') {
                blankCount++;
            }
        }
        if (blankCount > 1) {
            throw new UserInputException(Constant.ERR_BLANK);
        }
        
        String[] inputValue = userInput.split("\\s");
        if (!(inputValue.length > 1 && inputValue[1].matches(pattern))) {
            throw new UserInputException(Constant.ERR_INPUT + inputValue[0] + errorMsg);
        }
        return inputValue[1];
    } 
    
    private void checkAmount(String nameAsm, String parts) throws UserInputException, NumberFormatException {  
        // check if the amount is in range 1 <= amount <= 1000
        String[] test = parts.split(":|;");
        
        for (int i = 0; i + 1 < test.length; i++) {
            // mod 2 because the array has the form of {amount, name,...}
            if (i % 2 == 0) {
                int amount = Integer.parseInt(test[i]);
                if (amount > 0 && amount > 1000) {
                    throw new UserInputException("the amount for the component " + test[i + 1] + " in the BOM " 
                                                    + nameAsm + " is too high: " + test[i] + ".");
                } else if (amount <= 0) {
                    throw new UserInputException("the amount for the component " + test[i + 1] + " in the BOM " 
                            + nameAsm + " has to be bigger than 0.");
                }
            }
        }       
        // check for leading zeros
        String[] componentTuple = parts.split(";");
        for (String amount: componentTuple) {
            if (amount.startsWith("0")) {
                throw new UserInputException(Constant.ERR_LEADING_ZERO);
            }
        }
    }
}
