package View;

import Controller.*;
import Model.ADTs.MyList;
import Model.ADTs.PrgState;
import Model.Exceptions.myException;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class View {
    private final Controller controller;

    public View(Controller c)
    {
        this.controller = c;
    }

    void printMenu()
    {
        List<PrgState> programs = controller.getProgramList();
        for(int i = 1;i <= controller.getProgramsSize(); i++)
        {
            System.out.println((i) + ": " + programs.get(i-1).toString());
        }
        System.out.println("0: Exit\n");
    }

    public void runMenu()
    {
        List<PrgState> programs = controller.getProgramList();
        Scanner scanner=new Scanner(System.in);
        while(true)
        {
            printMenu();
            System.out.println("> ");
            int input = -1;
            try{
                input = scanner.nextInt();
                if(input == 0)
                    System.exit(0);
                if(input - 1 < programs.size() && input > -1) {
                   // this.controller.setCurrentProgram(input - 1);
                    this.controller.allStep();
                }
                else
                    throw new InputMismatchException("");

            }
            catch (InputMismatchException e)
            {
                System.out.println("Invalid input.");
            }
            catch (myException | IOException | RuntimeException | InterruptedException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}
