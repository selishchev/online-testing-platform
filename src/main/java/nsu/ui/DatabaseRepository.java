package nsu.ui;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseRepository implements TestRepository {

    private static Connection connection;
    public static final String url = "jdbc:mysql://localhost:3307/platform";
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
        HashMap<Long, User> testTeacher = new HashMap<>();
        Tests test = null;

        try {

            // Получаем список всех названий тестов
            String selectNames = "SELECT test_name from tests";
            result = statement.executeQuery(selectNames);
            while (result.next()) {
                testNames.add(result.getString(1));

            }

            // Формирование объектов Tests
            for (String testName : testNames) {
                String selectForTest = "SELECT id from tests WHERE test_name = '" + testName + "'";
                result = statement.executeQuery(selectForTest);
                while (result.next()) {
                    test = new Tests();
                    test.setId(result.getLong(1));
                    test.setTestName(testName);
                }
                if (test != null) {
                    String selectQuestions = "SELECT question from questions WHERE test_id= " + test.getId();
                    result = statement.executeQuery(selectQuestions);
                    while (result.next()) {
                        test.setQuestionToList(result.getString(1));
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
        String insertSql = "INSERT INTO tests(teacher_id, test_name) values(1, '" + test.getTestName() + "')";
        try {
            statement.executeUpdate(insertSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String selectSql = "select id from tests where test_name = '" + test.getTestName() + "'";
        try {
            result = statement.executeQuery(selectSql);
            while (result.next()) {
                test.setId(result.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return test;
    }

    @Override
    public ArrayList<Questions> findTest(Long id) {
        ArrayList<Questions> questions = new ArrayList<Questions>();
        String selectSql = "select * from questions where test_id = " + id;
        try {
            result = statement.executeQuery(selectSql);
            while (result.next()) {
                Questions question = new Questions();
                question.setId(result.getLong(1));
                question.setTest_id(result.getLong(2));
                question.setQuestion(result.getString(3));
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
    public static Tests getTestById(Long id) {
        Tests test = new Tests();
        String selectSql = "select * from tests where id = " + id;
        try {
            result = statement.executeQuery(selectSql);
            while (result.next()) {
                test.setId(result.getLong(1));
                test.setTestName(result.getString(3));;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return test;
    }
    public static void saveQuestion(String question, Long id){
        String insertSql = "INSERT INTO questions(test_id, question) values("+id+", '"
                + question + "')";
        try {
            statement.executeUpdate(insertSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void removeQuestion(Long id){
        String deleteSql = "DELETE FROM questions where id = " + id;
        try {
            statement.executeUpdate(deleteSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteTest(Long id){
        String deleteSql = "DELETE FROM tests where id = " + id;
        try {
            statement.executeUpdate(deleteSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User findByEmail(String email) {
        User user = new User();
        String selectSql = "select * from user where email = '" + email + "'";
        try {
            result = statement.executeQuery(selectSql);
            while (result.next()) {
                user.setId(result.getLong(1));
                user.setIsTeacher(result.getBoolean(2));
                user.setFirstName(result.getString(3));
                user.setSecondName(result.getString(4));
                user.setLastName(result.getString(5));
                user.setPassword(result.getString(6));
                user.setEmail(result.getString(7));
                }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    public static User saveUser(User user) {
        int isTeacher;
        if (user.getIsTeacher()) {
            isTeacher = 1;
        }
        else {
            isTeacher = 0;
        }

        String insertSql = "INSERT INTO user(is_teacher, first_name, second_name, last_name, password, email) " +
                "values(" + isTeacher + ", '" + user.getFirstName() + "', '" + user.getSecondName() + "', " +
                "'" + user.getLastName() + "', " + "'" + user.getPassword() + "', " + "'" + user.getEmail() + "')";
        try {
            statement.executeUpdate(insertSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return DatabaseRepository.findByEmail(user.getEmail());
    }

    public static boolean checkAndSaveUser(User user) {
        User userFromDB = DatabaseRepository.findByEmail(user.getEmail());

        if (userFromDB == null) {
            return false;
        }
        DatabaseRepository.saveUser(user);
        return true;
    }
    public static User findUserById(Long id) {
        User user = new User();
        String selectSql = "SELECT * from user where id = " + id;
        try {
            result = statement.executeQuery(selectSql);
            while (result.next()) {
                user.setId(result.getLong(1));
                user.setIsTeacher(result.getBoolean(2));
                user.setFirstName(result.getString(3));
                user.setSecondName(result.getString(4));
                user.setLastName(result.getString(5));
                user.setPassword(result.getString(6));
                user.setEmail(result.getString(7));
                }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
}
