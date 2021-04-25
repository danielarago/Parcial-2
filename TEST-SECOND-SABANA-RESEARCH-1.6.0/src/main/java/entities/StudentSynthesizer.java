package entities;

import java.util.List;

public class StudentSynthesizer implements ISynthesizer{

    @Override
    public String synthesize(List<Object> objects) throws SabanaResearchException {
        String summary = null;

        for (Object o: objects){
            if (o instanceof Student){
                Student s = ((Student)o);
                summary = (s.getActivitiesDurations() + s.getName()).concat(summary);
            }
        }

        return summary;
    }

}
