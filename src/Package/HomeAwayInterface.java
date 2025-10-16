package Package;

import Package.Exceptions.*;
import Package.Services.Services;
import Package.Students.Students;

import dataStructures.Iterator;
import dataStructures.TwoWayIterator;
import Package.Exceptions.Expensive;

public interface HomeAwayInterface {

    void createArea(String name, long minLongitude, long minLatitude, long maxLongitude, long maxLatitude)throws AreaAlreadyExists,InvalidLocation;
    String saveArea() throws NoBoundsInTheSystem;
    String  loadArea(String name)throws NoBoundsInTheSystem;

    // Serviços
    void createEating(long latitude, long longitude, int price, int capacity, String name)throws InvalidPrice,InvalidLocation,InvalidCapacity,ServiceAlreadyExists;
    void createLodging(long latitude, long longitude, int price, int capacity, String name)throws InvalidPrice,InvalidLocation,InvalidCapacity,ServiceAlreadyExists;
    void createLeisure(long latitude, long longitude, int price, int discount, String name)
            throws InvalidPrice,InvalidLocation,InvalidDiscount,ServiceAlreadyExists;

    // Estudantes
    void createStudent(String type, String name, String country, String lodging)throws InvalidStudentType,LodgingNotExists,LodgingIsFull,StudentAlreadyExists;
    void leave(String name) throws StudentNotFound;

    Iterator<Students> listAllStudents()throws NoToList;
    Iterator<Students> listStudentsByCountry(String country)throws NoToList;
    TwoWayIterator<Students> listUsersByOrder(String place)throws ServiceNotExists,InvalidLocation,Empty;

    String go(String name, String location) throws StudentNotFound,InvalidLocation,AlreadyThere, Expensive;
    String move(String name, String location) throws StudentNotFound,InvalidLocation,LodgingIsFull,CantMove;
    Services where(String name)throws StudentNotFound;

    Iterator<Services> getVisited(String name)throws StudentNotFound,InvalidStudentType,NoToList;
    void evaluate(int star, String nameService, String description)throws InvalidStar,ServiceNotExists;
    Iterator<Services> getRanking();
}
