package entities;

import java.util.List;

public class StudentSynthesizer implements ISynthesizer{

    @Override
    public String synthesize(List<Object> objects) throws SabanaResearchException {
        StringBuilder summary = new StringBuilder();

        for (Object o: objects){
            if (o instanceof Student){
                Student s = ((Student)o);
                summary.insert(0, ("Duration = " + s.getActivitiesDurations().toString() + ", name = " + s.getName() + ". "));
            }
        }

        return summary.toString();
    }

}
