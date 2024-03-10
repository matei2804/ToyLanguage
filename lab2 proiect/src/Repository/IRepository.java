package Repository;

import Model.ADTs.*;
import Model.Exceptions.myException;
import java.io.IOException;
import java.util.List;

public interface IRepository {
    public List<PrgState> getProgramList();
    public void setProgramList(List<PrgState> newProgramList);
    void addState(PrgState state);
    public int getRepoSize();
    void logPrgStateExec(PrgState state) throws myException, IOException;
}
