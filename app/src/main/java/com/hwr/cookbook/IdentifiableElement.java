package com.hwr.cookbook;

import java.util.UUID;

/**
 * Created by Sidney on 29.03.2018.
 */

public abstract class IdentifiableElement {

    public String name;
    private String ID;


    public IdentifiableElement () {
        ID = UUID.randomUUID().toString();
    }

    public String getID() { return ID; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
