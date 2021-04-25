package entities;

import java.time.Duration;
import java.util.List;

public class ExecutiveSynthesizer implements ISynthesizer{

    @Override
    public String synthesize(List<Object> objects) throws SabanaResearchException {
        StringBuilder summary = new StringBuilder();

        for (Object o: objects){
            if (o instanceof Iteration){
                Iteration i = ((Iteration)o);
                summary.insert(0, ("Duration = " + i.getDuration().toString() + ", goal = " + i.getGoal() + ". "));
            }
        }

        return summary.toString();
    }
}
