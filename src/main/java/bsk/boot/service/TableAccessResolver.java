package bsk.boot.service;

/**
 * Created by Mateusz-PC on 06.01.2017.
 */
public interface TableAccessResolver {
    boolean hasSaveAccess(int userAccessLabel, String... tableNames);
}
