import java.util.Scanner;

/**
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 */
public class Main extends IKernel {
    public static void main(String[] args) {

        /* System Configuration and Initialization */

        // initialize Main Memory to this size (in MB)
        setSizeOfMainMemory(512);
        setSizeOfFrame(8);
        setMaxInstructionsPerProcess(5);
        CPU CURRENT_CPU = new CPU();
        MassStorage CURRENT_HDD = new MassStorage();
        MainMemory CURRENT_RAM = new MainMemory();
        IOController CURRENT_IO_CONTROLLER = new IOController();
        // TODO allow user to select scheduler
        sRoundRobin CURRENT_SCHEDULER = new sRoundRobin();
        Dispatcher CURRENT_DISPATCHER = new Dispatcher(CURRENT_CPU, CURRENT_HDD, CURRENT_RAM, CURRENT_IO_CONTROLLER);
        CURRENT_DISPATCHER.setLocalScheduler(CURRENT_SCHEDULER);

        Scanner userInput = new Scanner(System.in);
        boolean run = true; // shell control var
        System.out.println("Sam's CPU Emulator");
        // interactive shell until user select exit
        while (run) {
            System.out.println("-- Select Action --");
            System.out.println("     1. Automatic Mode");
            System.out.println("     2. Generate New Processes from Templates");
            System.out.println("     3. List Processes in Schedule");
            System.out.println("     4. Manual-Start Simulation");
            System.out.println("     5. Clear Schedule");
            System.out.println("     9. Exit");

            // parse input
            int menu;
            try { menu = Integer.parseInt(userInput.nextLine()); }
            catch (Exception e) { menu =-1; }

            //switch statement to catch inputs
            switch(menu) {
                case -1:
                    System.out.println("Invalid input!\n");
                    break;
                case 1:
                    // automatic trial run
                    auto(CURRENT_DISPATCHER);
                    break;
                case 2:
                    // create new processes
                    System.out.println("Enter a number of processes to create:");
                    int userNum = Integer.parseInt(userInput.nextLine());
                    build(userNum, CURRENT_HDD);
                    break;
                case 3:
                    // display scheduled processes
                    if (CURRENT_SCHEDULER.isEmpty()) {
                        System.out.println("Schedule is empty.\n");
                    }
                    else {
                        for (int i = 0; i < CURRENT_SCHEDULER.size()-1;i++) {
                            ProcessDisplay.display(CURRENT_SCHEDULER.referenceInstruction(i).getParentProcess());
                        }
                    }
                    break;
                case 4:
                    // start simulation on CPU
                    startSimulation(CURRENT_DISPATCHER);
                    break;
                case 5:
                    CURRENT_SCHEDULER.clear();
                    System.out.println("Schedule is empty.\n");
                    break;
                case 9: // exit case
                    run = false;
                    break;
                default: // default catch for errors
                    System.out.println("You input " + menu + ". Please input a valid integer.\n");
                    break;
            } // end switch

        } //end while control loop
        System.out.println("Closing Application");
    } // end main

    public static void auto(Dispatcher currentDispatcher)
    {
        // do all the steps automatically/dynamically
        System.out.println("Automatic Mode");
        System.out.println("Generating random number of processes");
        int numProcs = (int) (Math.random() * 100) + 1;
        System.out.println("Building " + numProcs + " new processes..");
        build(numProcs, currentDispatcher.getLocalHDD());
        System.out.println("Begin Dispatching..");
        startSimulation(currentDispatcher);
    } // end auto

    public static void build(int num, MassStorage current_HDD) {
        // TODO correct xml file templates
        try {
            ProcessBuilder.build(num, current_HDD);
        }
        catch (Exception e) {
            System.out.println("There was a problem while trying to build processes. \n");
        }
    } // end build

    public static void startSimulation(Dispatcher currentDispatcher){
        // TODO implement number of cycles before pausing
        while (!currentDispatcher.getLocalScheduler().isEmpty()) {
            currentDispatcher.loadProcess();
        }
        if (currentDispatcher.getLocalScheduler().isEmpty()) {
            System.out.println("Schedule Complete. Terminated Processes (in order): ");
        }
        // TODO for each process terminated
        System.out.println("\n");
    } // end startSimulation

} //end class Main

