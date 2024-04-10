package com.juliomesquita.security.infra.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
public enum Permission {

    BACKOFFICE_USER_READ("Usuário Leitura"),
    BACKOFFICE_USER_WRITE("Usuário Escrita"),
    BACKOFFICE_USER_UPDATE("Usuário Atualizar"),
    BACKOFFICE_USER_DELETE("Usuário Deletar"),

    BACKOFFICE_INSTITUTION_READ("Entidade Social Leitura"),
    BACKOFFICE_INSTITUTION_WRITE("Entidade Social Escrita"),
    BACKOFFICE_INSTITUTION_UPDATE("Entidade Social Atualizar"),
    BACKOFFICE_INSTITUTION_DELETE("Entidade Social Deletar"),

    BACKOFFICE_READ("Backoffice Leitura"),
    BACKOFFICE_WRITE("Backoffice Escrita"),
    BACKOFFICE_UPDATE("Backoffice Atualizar"),
    BACKOFFICE_DELETE("Backoffice Deletar");

    private final String description;


    public static Set<Permission> getPermissions(String name){
        Set<Permission> permissionList = new HashSet<>();
        for(Permission key : values()) {
            String nameToLowerCase = key.name().toLowerCase();
            if(nameToLowerCase.contains(name)){
                permissionList.add(key);
            }
        }
        return permissionList;
    }
}
