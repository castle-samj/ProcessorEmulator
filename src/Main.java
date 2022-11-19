import java.util.Scanner;

/**
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
public class Main extends IKernel {
    public static void main(String[] args) {

        /* System Configuration and Initialization */

        // initialize Main Memory to this size (in MB)
        setSize_of_main_memory(512);
        setSizeOfFrame(8);
        setMax_instructions_per_process(5);
        CPU currentCPU = new CPU();
        MassStorage currentHDD = new MassStorage();
        MainMemory currentRAM = new MainMemory();
        IOController currentIOController = new IOController();
        // TODO allow user to select scheduler
        sRoundRobin currentScheduler = new sRoundRobin();
        Dispatcher currentDispatcher = new Dispatcher(currentCPU, currentHDD, currentRAM, currentIOController);
        currentDispatcher.setLocalScheduler(currentScheduler);

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
                case 1: // automatic trial run
                    auto(currentScheduler, currentDispatcher);
                    break;
                case 2: // create new processes
                    System.out.println("Enter a number of processes to create:");
                    int userNum = Integer.parseInt(userInput.nextLine());
                    build(currentScheduler, currentDispatcher, userNum);
                    break;
                case 3: // display scheduled processes
                    if (currentScheduler.isEmpty()) {
                        System.out.println("Schedule is empty.\n");
                    }
                    else {
                        for (int i = 0; i < currentScheduler.size()-1;i++) {
                            procDisplay.processDisplay(currentScheduler.get(i));
                        }
                    }
                    break;
                case 4:
                    // start simulation on CPU
                    startSimulation(currentScheduler, currentDispatcher);
                    break;
                case 5:
                    currentScheduler.clear();
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

    public static void auto(sRoundRobin currentSchedule, Dispatcher currentDispatcher)
    {
        // do all the steps automatically/dynamically
        System.out.println("Automatic Mode");
        System.out.println("Generating random number of processes");
        int numProcs = (int) (Math.random() * 100) + 1;
        System.out.println("Building " + numProcs + " new processes..");
        build(numProcs);
        System.out.println("Begin Dispatching..");
        startSimulation(currentSchedule, currentDispatcher);
    } // end auto

    public static void build(int num, MassStorage current_HDD) {
        // TODO correct xml file templates
        try {
            procBuilder.processBuilder(num, current_HDD);
        }
        catch (Exception e) {
            System.out.println("Hmm.. I didn't like that input. Try again. \n");
        }
    } // end build

    public static void startSimulation(sRoundRobin currentSchedule, Dispatcher currentDispatcher){
        // TODO implement number of cycles before pausing
        currentDispatcher.loadSchedule(currentSchedule);
        if (currentSchedule.isEmpty()) {
            System.out.println("Schedule Complete. Terminated Processes (in order): ");
        }
        currentDispatcher.TERMINATED.forEach((e) -> System.out.print(e.getPID() + ". "));
        System.out.println("\n");
    } // end startSimulation

} //end class Main

