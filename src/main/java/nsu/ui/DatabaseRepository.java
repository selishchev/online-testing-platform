package nsu.ui;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class DatabaseRepository implements TestRepository {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet result;

    @Override
    public ArrayList<Tests> findAll() {
        ArrayList<Tests> test = new ArrayList<Tests>();
        ArrayList<Tests> testNames = new ArrayList<>();
        HashMap<Long, String> testTeacher = new HashMap<Long, String>();
        String selectSQL = "SELECT id, second_name from teachers";
        try {
            result = statement.executeQuery(selectSQL);
            while (result.next()) {
                testTeacher.put(result.getLong(1), result.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String selectSql = "SELECT test_name from tests";

        try {
            result = statement.executeQuery(selectSql);
        } catch (SQLException e) {
            System.out.println("Ошибка Select");
        }
        try {
            while (result.next()) {
                Tests tests = new Tests();
                tests.setId(result.getLong(1));
                tests.setTeacher(testTeacher.);
                tests.setQuestion(result.getString(3));
                tests.setTestName(result.);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test;
    }


        @Override
    public Tests save(Tests test) {
        return null;
    }

    @Override
    public Tests findTest(Long id) {
        return null;
    }
}
