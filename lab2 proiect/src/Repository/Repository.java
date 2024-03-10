package Repository;

import java.util.LinkedList;
import java.util.List;
import Model.ADTs.PrgState;
import Model.Exceptions.myException;

import java.io.*;

public class Repository implements IRepository{
    private List<PrgState> programs;
    private final String logFilePath;

    public Repository (PrgState prg,  String logFilePath)
    {
        this.programs = new LinkedList<>();
        this.logFilePath = logFilePath;
        addState(prg);
    }

    @Override
    public List<PrgState> getProgramList() {
        return programs;
    }

    @Override
    public void setProgramList(List<PrgState> newProgramList) {
        this.programs = newProgramList;
    }

    public int getRepoSize()
    {
        return programs.size();
    }
    @Override
    public void logPrgStateExec(PrgState state) throws myException, IOException {
        File f = new File(logFilePath);
        f.createNewFile();
        try {
            FileWriter fileWriter = new FileWriter(logFilePath, true);
            fileWriter.append(state.toString()).append("\n");
            fileWriter.close();
        }
        catch (IOException m)
        {
            throw new myException(m.getMessage());
        }
    }

    @Override
    public void addState(PrgState state) {
        programs.add(state);
    }
}
