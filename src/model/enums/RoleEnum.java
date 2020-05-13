package model.enums;

public enum RoleEnum {

    ADMIN(1, "ADMIN"),
    PERSONNEL_MEDICAL(2, "PMEDICAL"),
    FOURNISSEUR(3, "FOURNISSEUR"),
    PHARMACIEN(4, "PHARMACIEN"),
    PATIENT(5, "PATIENT"),
    INEXISTANT(6, "INEXISTANT");

    private int identifiant;
    private String label;

    RoleEnum(int identifiant, String label){
        this.label = label;
        this.identifiant = identifiant;
    }

    public static RoleEnum getRoleById(int identifiant){
        for(RoleEnum roleEnum : RoleEnum.values()){
            if(roleEnum.getIdentifiant() == identifiant){
                return roleEnum;
            }
        }
        return INEXISTANT;
    }

    public String getLabel() {
        return label;
    }

    public int getIdentifiant() {
        return identifiant;
    }
}
