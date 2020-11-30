package nsu.ui;

import java.util.ArrayList;

public class DatabaseRepository implements TestRepository{

    @Override
    public ArrayList<Tests> findAll() {
        ArrayList<Tests> tests = new ArrayList<>();
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
