package com.github.fernvndomatos.RelogioApi.entity.enums;

public enum MaterialCaixa {
    ACO, TITANIO, RESINA, BRONZE, CERAMICA;

    public static MaterialCaixa fromApi(String valor){
        if (valor == null || valor.isBlank()) return null;
        return switch (valor) {
            case "steel" -> ACO;
            case "titanum" -> TITANIO;
            case "resin" -> RESINA;
            case "bronze" -> BRONZE;
            case "ceramic" -> CERAMICA;
            default -> throw new IllegalArgumentException("Material da Caixa Inválido: " + valor);
        };
    }

    public String toApi(){
        return switch (this){
            case ACO -> "steel";
            case TITANIO -> "titanium";
            case RESINA -> "resin";
            case BRONZE -> "bronze";
            case CERAMICA -> "ceramic";
        };
    }
}
