package entities;

import java.time.Duration;
import java.util.List;

public class ExecutiveSynthesizer implements ISynthesizer{

    @Override
    public String synthesize(List<Object> objects) throws SabanaResearchException {
        String summary = null;

        for (Object o: objects){
            if (o instanceof Iteration){
                Iteration i = ((Iteration)o);
                summary = (i.getDuration().toString() + i.getGoal()).concat(summary);
            }
        }

        return summary;
    }
}
