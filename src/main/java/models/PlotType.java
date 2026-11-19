package models;

//using enum as there's only a small amount of named values to choose from
public enum PlotType {
    STATIC_MOBILE_HOME("Static Mobile Home"),
    BUNGALOW("Bungalow"),
    APARTMENT("Apartment"),
    EMPTY_PITCH("Empty Pitch");

    private final String type; // storing text in brackets in variable "type"

    PlotType(String type){
        this.type = type;
    }

    // empty pitch is the only non-static type of plot
    // will use in model for "plot"
    public boolean isStatic(){
        return this != EMPTY_PITCH;
    }

    @Override
    public String toString(){
        return type;
    }
}
