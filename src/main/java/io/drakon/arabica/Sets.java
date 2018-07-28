package io.drakon.arabica;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import lombok.experimental.UtilityClass;
import org.apiguardian.api.API;

/**
 * Helpers for working with Sets.
 */
@UtilityClass
@API(status = API.Status.STABLE)
public class Sets {

    /**
     * Converts a given array of items into a new {@link Set}.
     *
     * @param items The items to add to the set.
     * @param <T>   The type of item in the set.
     * @return A {@link Set} of items.
     */
    @SafeVarargs
    public <T> Set<T> asSet(final T... items) {
        return new HashSet<>(Arrays.asList(items));
    }

    /**
     * Creates a new {@link Set} as the union of other {@link Set} values.
     *
     * @param sets The sets to union.
     * @param <T>  The type within the sets.
     * @return The union of all provided sets.
     */
    @SafeVarargs
    public <T> Set<T> union(final Set<T>... sets) {
        Set<T> set = new HashSet<>();
        for (Set<T> it : sets) {
            set.addAll(it);
        }
        return set;
    }

    /**
     * Creates a new {@link Set} with the intersection (common values) of two {@link Set} values.
     *
     * @param a   The first set.
     * @param b   The second set.
     * @param <T> The type contained in the sets.
     * @return The intersection of both sets.
     */
    public <T> Set<T> intersection(final Set<T> a, final Set<T> b) {
        Set<T> intersection = new HashSet<>(a);
        intersection.retainAll(b);
        return intersection;
    }

    /**
     * Creates a new {@link Set} with the values forming a symmetric difference between two {@link Set} values.
     *
     * @param a   The first set.
     * @param b   The second set.
     * @param <T> The type contained in the sets.
     * @return The symmetric difference of both sets.
     */
    public <T> Set<T> symmetricDifference(final Set<T> a, final Set<T> b) {
        Set<T> intersect = intersection(a, b);
        Set<T> difference = union(a, b);
        difference.removeAll(intersect);
        return difference;
    }

}
