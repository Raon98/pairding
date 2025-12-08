package com.pairding.global.core.tsid;

import io.hypersistence.tsid.TSID;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class TsidGenerator {

    private static final TSID.Factory TSID_FACTORY;

    static {
        int nodeId = Integer.getInteger("node.id", ThreadLocalRandom.current().nextInt(0, 256));
        TSID_FACTORY = TSID.Factory.builder()
                .withNodeBits(8)
                .withNode(nodeId)
                .build();
    }

    public static TSID nextTsid() {
        return TSID_FACTORY.generate();
    }

    public static Long nextId() {
        return TSID_FACTORY.generate().toLong();
    }

    public static String nextString() {
        return TSID_FACTORY.generate().toString();
    }
}