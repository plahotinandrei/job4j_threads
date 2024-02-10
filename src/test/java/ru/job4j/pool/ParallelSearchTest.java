package ru.job4j.pool;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ParallelSearchTest {

    @Test
    public void whenIntegerArrayAndRecursiveThanIndex() {
        Integer[] nums = new Integer[] {4, 6, 2, 7, 3, 9, 11, -2, -15, 0, 32, 11, -4, -6, 13};
        int num = -2;
        assertThat(ParallelSearch.<Integer>search(nums, num)).isEqualTo(7);
    }

    @Test
    public void whenDoubleArrayAndRecursiveThanIndex() {
        Double[] nums = new Double[] {4D, 6D, 2D, 7D, 3D, 9D, 11D, -2D, -15D, 0D, 32D, 11D, -4D, -6D, 13D};
        double num = -2D;
        assertThat(ParallelSearch.<Double>search(nums, num)).isEqualTo(7);
    }

    @Test
    public void whenIntegerArrayAndLinearThanIndex() {
        Integer[] nums = new Integer[] {4, 6, 2, 7, 3, 9, 11, -2};
        int num = 9;
        assertThat(ParallelSearch.<Integer>search(nums, num)).isEqualTo(5);
    }

    @Test
    public void whenLongArrayAndRecursiveThanNotFound() {
        Long[] nums = new Long[] {4L, 6L, 2L, 7L, 3L, 9L, 11L, -2L, -15L, 0L, 32L, 11L, -4L, -6L, 13L};
        long num = -21;
        assertThat(ParallelSearch.<Long>search(nums, num)).isEqualTo(-1);
    }

    @Test
    public void whenLongArrayAndLinearThanNotFound() {
        Long[] nums = new Long[] {4L, 6L, 2L, 7L, 3L};
        long num = -13;
        assertThat(ParallelSearch.<Long>search(nums, num)).isEqualTo(-1);
    }
}