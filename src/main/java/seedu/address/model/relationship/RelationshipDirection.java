package seedu.address.model.relationship;

public enum RelationshipDirection {

    UNDIRECTED("undirected"),
    DIRECTED("directed");

    public final String directed = "directed";
    private String direction;

    RelationshipDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public boolean isDirected() {
        return this.direction.equals(directed);
    }

    public boolean equals(RelationshipDirection other) {
        return this.direction.equals(other.getDirection());
    }
}
