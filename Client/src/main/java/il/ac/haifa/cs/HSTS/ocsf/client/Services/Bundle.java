package il.ac.haifa.cs.HSTS.ocsf.client.Services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Bundle implements Serializable {

    static private Bundle bundle = null;
    private Map<String,Object> map;

    private Bundle() {
        map = new HashMap<String,Object>();
    };

    public static Bundle getInstance() {
        if (bundle == null) {
            bundle = new Bundle();
            System.out.println("Bundle initiated");
        } else
            System.out.println("Bundle is already exists");
        return bundle;
    }

    public void put(String string, Object object){
        this.map.put(string,object);
        System.out.println("Object "+ object + " inserted to bundle under key: "+string);
    }
    public Object get(String string){
        return this.map.get(string);
    }

    public void remove(String string) {
        System.out.println("Object " + this.map.remove(string) + " has been removed");
    }
    public void clear() {
        this.map.clear();
        System.out.println("Bundle has been cleared");
    }

    @Override
    public String toString() {
        return "Bundle{" +
                "map=" + map +
                '}';
    }
}
