import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.daogenerator.*;

/**
 * Created by wv on 2015/8/11.
 */


public class ExampleDaoGenerator {
    static final String PHONE_REGEX = "^[0-9a-zA-Z\\W]\\S{5,20}$";
    static final String CHINESE = "^(?![a-z]+$)(?![A-Z]+$)(?![0-9]+$)[0-9a-zA-Z\\W]\\S{6,20}$";
    // ^(?![a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$).{6,20}$
    // ^(?!^(\d+|[a-za-z]+|[~!@#$%^&*?]+)$)^[\w~!@#$%\^&*?]+$
    // ^(?![a-z]+$)(?![A-Z]+$)(?![0-9]+$)[0-9a-zA-Z\W]\S{6,18}$



    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "com.wv.timedtoggle.database");
        addTask(schema);
        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }


    private static void addTask(Schema schema) {
        Entity note = schema.addEntity("Task");
        note.addIdProperty().autoincrement();
        note.addStringProperty("task").notNull();
        note.addStringProperty("date").notNull();
        note.addStringProperty("time").notNull();
        note.addStringProperty("enable").notNull();
    }

}
