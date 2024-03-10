package View;

import Controller.Controller;
import Model.Exceptions.myException;

import java.io.IOException;

public class RunExample extends Command {
    private final Controller controller;

    public RunExample(String key, String desc, Controller ctr) {
        super(key, desc);
        this.controller = ctr;
    }

    @Override
    public void execute() throws myException, IOException {
        try {
            controller.allStep();
        } catch (myException | IOException | RuntimeException | InterruptedException m) {
            System.out.println(m.getMessage());
        }
    }
}
