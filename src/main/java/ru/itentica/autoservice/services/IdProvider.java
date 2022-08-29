package ru.itentica.autoservice.services;


public class IdProvider{
    private static long id = 1L;

    public static int getNextIntId() {
        if ((int)id == Integer.MAX_VALUE)
            throw new RuntimeException("Integer ID can't be received");

        return (int) id++;
    }

    public static long getNextLongId() {
        if (id == Long.MAX_VALUE)
            throw new RuntimeException("Long ID can't be received");
        return id++;
    }
}
