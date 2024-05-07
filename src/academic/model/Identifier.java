package academic.model;

/**
 * @author 12S22001 Winfrey Nainggolan
 * @author 12S22012 Reinhard Batubara
 */
public class Identifier {
    protected String id;
    protected String name;

// ACCESSOR
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

// MUTATOR
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.id + "|" + this.name;
    }
}
