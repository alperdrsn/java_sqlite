
public class SQLitePOJO {

    int id = 0;
    String name = "";

    public SQLitePOJO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
