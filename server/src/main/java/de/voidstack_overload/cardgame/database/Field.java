package de.voidstack_overload.cardgame.database;

public class Field {
    private final String name;
    private final FieldType type;
    private final Boolean isPrimary;
    private final Boolean isAutoIncrement;
    private final Boolean isNotNull;

    public Field(String name, FieldType type, Boolean isPrimary, boolean isAutoIncrement, Boolean isNotNull) {
        this.name = name;
        this.type = type;
        this.isPrimary = isPrimary;
        this.isAutoIncrement = isAutoIncrement;
        this.isNotNull = isNotNull;
    }

    public String getName() {
        return name;
    }

    public FieldType getType() {
        return type;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public boolean getAutoIncrement() {
        return isAutoIncrement;
    }
    public Boolean getNotNull() {
        return isNotNull;
    }


}
