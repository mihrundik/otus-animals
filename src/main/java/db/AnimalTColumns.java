package db;

public enum AnimalTColumns {
    ANIMAL_ID("animal_id"),
    TYPE("type"),
    NAME("name"),
    AGE("age"),
    WEIGHT("weight"),
    COLOR("color");

    private final String fieldName;

    AnimalTColumns(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

}
