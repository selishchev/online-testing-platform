package nsu.ui;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class DatabaseRepository implements TestRepository {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet result;

    public DatabaseRepository() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/platform",
                    "root", "654");
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("Не удалось подключиться к базе данных. ");
        }
    }

    @Override
    public ArrayList<Tests> findAll() {
        ArrayList<Tests> tests = new ArrayList<>();
        ArrayList<String> testNames = new ArrayList<>();
        ArrayList<String> testQuestions = new ArrayList<>();
        HashMap<Long, Teachers> testTeacher = new HashMap<>();
        Tests test = null;

        try {
            // Получаем список всех названий тестов
            String selectNames = "SELECT test_name from test";
            result = statement.executeQuery(selectNames);
            while (result.next()) {
                if(!testNames.contains(result.getString(1))) {
                    testNames.add(result.getString(1));
                }
            }

            // Формирование объектов Tests
            for(String testName : testNames) {
                String selectForTest = "SELECT id from test WHERE test_name = '" + testName + "'";
                result = statement.executeQuery(selectForTest);
                while (result.next()) {
                    test = new Tests();
                    test.setId(result.getLong(1));
                    test.setTestName(testName);
                }
                if(test != null) {
                    String selectQuestions = "SELECT question from test WHERE test_name = '" + testName + "'";
                    result = statement.executeQuery(selectQuestions);
                    while (result.next()) {
                        test.setQuestion(result.getString(1));
                    }
                    tests.add(test);
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка Select");
        }
        return tests;
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
