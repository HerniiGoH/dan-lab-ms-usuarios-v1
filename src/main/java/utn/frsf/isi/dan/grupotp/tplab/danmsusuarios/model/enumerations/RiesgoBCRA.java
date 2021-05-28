package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.enumerations;

public enum RiesgoBCRA {
    NORMAL("Normal"),
    RIESGO_BAJO("Riesgo Bajo"),
    RIESGO_MEDIO("Riesgo Medio"),
    RIESGO_ALTO("Riesgo Alto"),
    IRRECUPERABLE("Irrecuperable"),
    IRRECUPERABLE_DISP_TEC("Irrecuperable Por Dispocicion Tecnica");

    private String name;

    RiesgoBCRA(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }
}
