package com.ark.store;

import com.ark.models.UserData;

import java.util.Collection;

/**
 * Base interface with only to methods
 */
public interface Base {

    Collection<UserData> values();

    int save(UserData userData);

}
