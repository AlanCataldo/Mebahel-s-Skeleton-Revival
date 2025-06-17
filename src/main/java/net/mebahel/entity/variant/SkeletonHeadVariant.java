package net.mebahel.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum SkeletonHeadVariant {
    SKELETON(0),
    WITHER_SKELETON(1);

    private static final SkeletonHeadVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(SkeletonHeadVariant::getId)).toArray(SkeletonHeadVariant[]::new);
    private final int id;

    SkeletonHeadVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static SkeletonHeadVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

