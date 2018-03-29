package com.hwr.cookbook;

/**
 * Created by Sidney on 22.03.2018.
 */

public class Plan extends IdentifiableElement {

    public RecipeMarker[] Markers;

    public Plan () {
        super();
    }

    public Plan (RecipeMarker[] Markers) {
        super();
        this.Markers = Markers;
    }

}
