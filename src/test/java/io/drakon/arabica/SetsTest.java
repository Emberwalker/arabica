package io.drakon.arabica;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SetsTest {

    @Test
    void testUnionEmptySets() {
        assertThat(Sets.union(Sets.asSet(), Sets.asSet())).isEmpty();
    }

    @Test
    void testUnionOneSided() {
        assertThat(Sets.union(Sets.asSet("test"), Sets.asSet())).as("left").containsExactly("test");
        assertThat(Sets.union(Sets.asSet(), Sets.asSet("test"))).as("right").containsExactly("test");
    }

    @Test
    void testUnionBothSides() {
        assertThat(Sets.union(Sets.asSet(1), Sets.asSet(2))).containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void testIntersectionEmptySets() {
        assertThat(Sets.intersection(Sets.asSet(), Sets.asSet())).isEmpty();
    }

    @Test
    void testIntersectionOneSided() {
        assertThat(Sets.intersection(Sets.asSet("test"), Sets.asSet())).as("left").isEmpty();
        assertThat(Sets.intersection(Sets.asSet(), Sets.asSet("test"))).as("right").isEmpty();
    }

    @Test
    void testIntersectionBothEqual() {
        Set<String> set = Sets.asSet("test");
        assertThat(Sets.intersection(set, set)).containsExactly("test");
    }

    @Test
    void testIntersectionBothDifferent() {
        assertThat(Sets.intersection(Sets.asSet(1), Sets.asSet(2))).isEmpty();
    }

    @Test
    void testIntersectionSomeDifferent() {
        assertThat(Sets.intersection(Sets.asSet(0, 1), Sets.asSet(1, 2))).containsExactly(1);
    }

    @Test
    void testSymmetricDifferenceEmptySets() {
        assertThat(Sets.symmetricDifference(Sets.asSet(), Sets.asSet())).isEmpty();
    }

    @Test
    void testSymmetricDifferenceOneSided() {
        assertThat(Sets.symmetricDifference(Sets.asSet("test"), Sets.asSet())).as("left").containsExactly("test");
        assertThat(Sets.symmetricDifference(Sets.asSet(), Sets.asSet("test"))).as("right").containsExactly("test");
    }

    @Test
    void testSymmetricDifferenceBothEqual() {
        Set<String> set = Sets.asSet("test");
        assertThat(Sets.symmetricDifference(set, set)).isEmpty();
    }

    @Test
    void testSymmetricDifferenceBothDifferent() {
        assertThat(Sets.symmetricDifference(Sets.asSet(1), Sets.asSet(2))).containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void testSymmetricDifferenceSomeDifferent() {
        assertThat(Sets.symmetricDifference(Sets.asSet(0, 1), Sets.asSet(1, 2))).containsExactlyInAnyOrder(0, 2);
    }

}