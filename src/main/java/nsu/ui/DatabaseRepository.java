package nsu.ui;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class DatabaseRepository implements TestRepository {
    private final Connection connection;
    public static final String url = "jdbc:mysql://localhost:3306/platform";
    public static final String user = "root";
    public static final String pwd = "32232";
    private static Statement statement;
    private static ResultSet result;

    public DatabaseRepository() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, pwd);
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

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
                //tests.setTeacher(testTeacher.);
                tests.setQuestion(result.getString(3));
                //tests.setTestName(result.);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test;
    }


        @Override
        public Tests save(Tests test) throws SQLException {
            String testName = "'" + test.getTestName() + "'";
            String question = "'" + test.getQuestion() + "'";
            try (Statement st = connection.createStatement()) {
                st.executeUpdate(String.format("INSERT into test(test_name, question) " +
                                        "VALUES ('%s', '%s', '%s', '%s', '%s')", testName, question));
                try (ResultSet rs2 = st.executeQuery("select id from test where test_name = " + testName + " and question = " + question)) {
                    Long id = test.getId();
                    if (id == null) {
                        while (rs2.next()) {
                            test.setId(rs2.getLong(1));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
