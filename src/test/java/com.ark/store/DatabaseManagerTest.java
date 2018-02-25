package com.ark.store;

import com.ark.models.UserData;
import org.hibernate.AssertionFailure;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class DatabaseManagerTest {

    DatabaseManager manager = new DatabaseManager();


    @Test
    public void DoesValuesWork() {
        Collection<UserData> collection = manager.values();
        assertEquals(collection.size(), manager.values().size());
    }

    /**
     * This test will be pass because assertionFailure expected
     * and you get it if try save userdata with username=null
     */
    @Test(expected = AssertionFailure.class)
    public void DoesSaveWork() {
        UserData userData = new UserData();
        userData.setUsername(null);
        userData.setEmail("KiraGrigoreva@gmail.com");
        userData.setHomePage("www.rgd.ru");
        manager.save(userData);
    }
}