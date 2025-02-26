package com.namiya.store.project.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGenerator {
    public static String Generator(String name){
        if(name!=null){
            return UUID.nameUUIDFromBytes(name.getBytes()).toString();
        }
        else{
            return UUID.randomUUID().toString();
        }
    }
}
