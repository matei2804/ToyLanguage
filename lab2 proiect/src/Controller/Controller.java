package Controller;

import Model.ADTs.MyList;
import Model.ADTs.PrgState;
import Model.Exceptions.myException;
import Repository.*;
import Model.Value.*;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    IRepository repository;
    ExecutorService executor;

    public Controller(IRepository repo)
    {
        this.repository = repo;
    }

    public List<PrgState> removeCompletedPrograms(List<PrgState> prgList){
        return prgList.stream().filter(PrgState::isNotCompleted).collect(Collectors.toList());
    }

    void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException, myException {
        //before the execution, print the PrgState List into the log file
        prgList.forEach(prg ->
        {   try
            {
                repository.logPrgStateExec(prg);
            }
            catch (myException | IOException m)
            {
                try {
                    throw new myException(m.getMessage());
                } catch (myException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //RUN concurrently one step for each of the existing PrgStates
        //-----------------------------------------------------------------------
        //prepare the list of callables
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());

        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)
        try{
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future ->
                    { try
                        {
                            return future.get();
                        }
                        catch(InterruptedException | ExecutionException e)
                        {
                            try {
                                throw new myException(e.getMessage());
                            } catch (myException ex) {
                                throw new RuntimeException(ex.getMessage());
                            }
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
            //add the new created threads to the list of existing threads
            prgList.addAll(newPrgList);
        }
        catch (InterruptedException m) {
            throw  new myException(m.getMessage());
        }

        //after the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try
            {
                repository.logPrgStateExec(prg);
            }
            catch (myException | IOException m)
            {
                try {
                    throw new myException(m.getMessage());
                } catch (myException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        //Save the current programs in the repository
        repository.setProgramList(prgList);
    }

    public void allStep() throws myException, IOException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState> prgList = removeCompletedPrograms(repository.getProgramList());
        while(!prgList.isEmpty()){

            goodGarbageCollector(prgList);

            oneStepForAllPrg(prgList);

            prgList = removeCompletedPrograms(repository.getProgramList());
        }

        executor.shutdownNow();
        repository.setProgramList(prgList);
    }


    public void oneStepGUI(List<PrgState> programs) throws InterruptedException
    {
        this.executor = Executors.newFixedThreadPool(2);

        programs.forEach(prg ->
        {   try
        {
            repository.logPrgStateExec(prg);
        }
        catch (myException | IOException m)
        {
            try {
                throw new myException(m.getMessage());
            } catch (myException e) {
                throw new RuntimeException(e);
            }
        }
        });

        programs = removeCompletedPrograms(programs);

        List<Callable<PrgState>> callList = programs.stream()
                .map(program -> (Callable<PrgState>)(program::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newProgramsList = this.executor.invokeAll(callList).stream()
                .map(future -> {
                    try
                    {
                        return future.get();
                    }
                    catch(InterruptedException | ExecutionException e)
                    {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        goodGarbageCollector(newProgramsList);
        programs.addAll(newProgramsList);

        programs.forEach(prg ->
        {   try
        {
            repository.logPrgStateExec(prg);
        }
        catch (myException | IOException m)
        {
            try {
                throw new myException(m.getMessage());
            } catch (myException e) {
                throw new RuntimeException(e);
            }
        }
        });

        this.repository.setProgramList(programs);
    }

    void goodGarbageCollector(List<PrgState> prgList) {
        for(PrgState prg : prgList)
        {
            prg.getHeap().setContent(GarbageCollector(getAddrFromSymTable(prg.getSymTable().getContent().values()), prg.getHeap().getContent().keySet().stream().toList(), prg.getHeap().getContent()));
        }
    }

    Map<Integer, Value> GarbageCollector(List<Integer> addresses, List<Integer> heapAddrs, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(elem -> addresses.contains(elem.getKey()) || heapAddrs.contains(elem.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {
                    ReferenceValue v1 = (ReferenceValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    public int getProgramsSize()
    {
        return repository.getRepoSize();
    }

    public List<PrgState> getProgramList() {
        return  repository.getProgramList();
    }

    public void setProgramListController(List<PrgState> programs)  {
        this.repository.setProgramList(programs);
    }
}
