package nyc.c4q.huilin.ancientocean;

/**
 * Created by huilin on 3/25/17.
 */

class People {
    private String id;
    private String name;
    private String favoriteCity;

    public People(String id, String name, String favoriteCity) {
        this.id = id;
        this.name = name;
        this.favoriteCity = favoriteCity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFavoriteCity() {
        return favoriteCity;
    }

    public void setFavoriteCity(String favoriteCity) {
        this.favoriteCity = favoriteCity;
    }
}
