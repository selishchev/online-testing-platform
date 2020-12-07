package nsu.ui;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class DatabaseRepository implements TestRepository {

    private static Connection connection;
    public static final String url = "jdbc:mysql://localhost:3306/platform";
    public static final String user = "root";
    public static final String pwd = "654";
    private static Statement statement;
    private static ResultSet result;

    public DatabaseRepository() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pwd);
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("Не удалось подключиться к базе данных. ");
        }
    }

    public void closeConnection() {
        try {
            connection.close();
            statement.close();
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
                if (!testNames.contains(result.getString(1))) {
                    testNames.add(result.getString(1));
                }
            }

            // Формирование объектов Tests
            for (String testName : testNames) {
                String selectForTest = "SELECT id from test WHERE test_name = '" + testName + "'";
                result = statement.executeQuery(selectForTest);
                while (result.next()) {
                    test = new Tests();
                    test.setId(result.getLong(1));
                    test.setTestName(testName);
                }
                if (test != null) {
                    String selectQuestions = "SELECT question from test WHERE test_name = '" + testName + "'";
                    result = statement.executeQuery(selectQuestions);
                    while (result.next()) {
                        test.setQuestionToList(result.getString(1));
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
        String insertSql = "INSERT INTO test(test_name, question) values('" + test.getTestName() + "', '"
                + test.getQuestion() + "')";
        try {
            statement.executeUpdate(insertSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test;
    }

    @Override
    public Tests findTest(Long id) {
        String query = String.format("SELECT * FROM test WHERE id = '%s'", id);
        Tests test = new Tests();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                test.setId(rs.getLong(1));
                test.setTestName(rs.getString(2));
                test.setQuestion(rs.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return test;
    }
}
