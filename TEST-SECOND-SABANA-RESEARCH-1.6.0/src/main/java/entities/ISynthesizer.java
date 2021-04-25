package entities;

import java.util.List;

public interface ISynthesizer {
    public abstract String synthesize(List<Object> objects) throws SabanaResearchException;
}
