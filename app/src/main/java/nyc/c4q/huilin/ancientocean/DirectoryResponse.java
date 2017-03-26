package nyc.c4q.huilin.ancientocean;

import java.util.ArrayList;
import java.util.List;

public class DirectoryResponse {
    List<People> peopleList = new ArrayList<>();

    public List<People> getPeopleList() {
        return peopleList;
    }

    public void setPeopleList(List<People> peopleList) {
        this.peopleList = peopleList;
    }
}
