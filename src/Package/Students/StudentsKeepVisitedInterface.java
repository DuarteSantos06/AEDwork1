package Package.Students;

import Package.Services.Services;


import dataStructures.ListInArray;

public interface StudentsKeepVisitedInterface extends StudentsInterface{

    void addVisited(Services service);

    ListInArray<Services> getVisited();
}
